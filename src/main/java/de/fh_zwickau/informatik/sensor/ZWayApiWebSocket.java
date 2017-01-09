/**
 * Copyright (C) 2016 by Software-Systementwicklung Zwickau Research Group
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package de.fh_zwickau.informatik.sensor;

import java.net.HttpCookie;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.eclipse.jetty.util.ssl.SslContextFactory;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.WebSocketListener;
import org.eclipse.jetty.websocket.client.ClientUpgradeRequest;
import org.eclipse.jetty.websocket.client.WebSocketClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import de.fh_zwickau.informatik.sensor.model.devices.Device;
import de.fh_zwickau.informatik.sensor.model.devices.DeviceListDeserializer;

/**
 * The {@link ZWayApiWebSocket} implements the ZAutomation API. See also:
 * http://docs.zwayhomeautomation.apiary.io/#
 *
 * @author Patrick Hecker - Initial contribution
 */
public class ZWayApiWebSocket extends ZWayApiBase {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private WebSocketClient mClient;
    private ZWayWebSocket mSocket;
    private IZWayApiWebSocketCallbacks mWebSocketListener;

    public ZWayApiWebSocket(String ipAddress, Integer port, String protocol, String username, String password,
            Integer remoteId, Boolean useRemoteService, IZWayApiCallbacks caller,
            IZWayApiWebSocketCallbacks webSocketListener) {
        super(ipAddress, port, protocol, username, password, remoteId, useRemoteService, caller);

        mWebSocketListener = webSocketListener;

        mSocket = new ZWayWebSocket();
    }

    public void connect() {
        if (mUseRemoteService) {
            // TODO - WebSockets not available for remote service
            mCaller.apiError("WebSockets not available for remote service.", true);
            return;
        }

        // Connect to Z-Way via HTTP to get the required cookies
        String protocol = mProtocol.equals("ws") ? "http" : "https";
        IZWayApi zwayAPIHTTP = new ZWayApiHttp(mIpAddress, mPort, protocol, mUsername, mPassword, mRemoteId,
                mUseRemoteService, mCaller);
        if (zwayAPIHTTP.getLogin() == null) {
            logger.warn("Authentication failed!");
            return;
        }

        if (mProtocol.equals("ws")) {
            logger.info("Create WebSocket client");
            mClient = new WebSocketClient();
        } else if (mProtocol.equals("wss")) {
            logger.info("Create WebSocket client with SSL");
            mClient = new WebSocketClient(new SslContextFactory());
        }

        try {
            mClient.start();

            URI destUri = new URI(getTopLevelUrl());

            ClientUpgradeRequest request = new ClientUpgradeRequest();

            List<HttpCookie> cookies = new ArrayList<HttpCookie>();
            cookies.add(new HttpCookie("ZWAYSession", zwayAPIHTTP.getZWaySessionId()));
            if (mUseRemoteService) {
                cookies.add(new HttpCookie("ZBW_SESSID", zwayAPIHTTP.getZWayRemoteSessionId()));
            }

            request.setCookies(cookies);
            mClient.connect(mSocket, destUri, request);
        } catch (Exception e) {
            mCaller.apiError("Z-Way WebSocket connect: " + e.getMessage(), true);
        }
    }

    public void disconnect() {
        try {
            mSocket.awaitClose(5, TimeUnit.SECONDS);

            mClient.stop();
        } catch (Exception e) {
            logger.error("Z-Way WebSocket disconnect: {}", e.getMessage());
            mCaller.apiError("Z-Way WebSocket disconnect: " + e.getMessage(), true);
        }
    }

    private void onMessage(String message) {
        // Request performed successfully: load response body
        // Expected response format: { "type": "...", "data": {...} }
        logger.info("Z-Way WebSocket message: {}", message);

        try {
            Gson gson = new Gson();
            String type = gson.fromJson(message, JsonObject.class).get("type").getAsString();
            logger.info("Z-Way WebSocket message type: {}", type);

            if (type.equals("devices.level_update")) { // TODO only a example
                String deviceAsString = gson.fromJson(message, JsonObject.class).get("data").getAsString();
                Device device = new DeviceListDeserializer()
                        .deserializeDevice(gson.fromJson(deviceAsString, JsonObject.class).getAsJsonObject(), this);
                if (device != null) {
                    mCaller.getDeviceResponse(device);
                }
            }
        } catch (JsonParseException e) {
            logger.warn("Z-Way WebSocket unexpected response format: {}", e.getMessage());
            mCaller.responseFormatError("Z-Way WebSocket unexpected response format: " + e.getMessage(), false);
        }
    }

    public class ZWayWebSocket implements WebSocketListener {
        private final CountDownLatch closeLatch = new CountDownLatch(1);

        public boolean awaitClose(int duration, TimeUnit unit) throws InterruptedException {
            return this.closeLatch.await(duration, unit);
        }

        @Override
        public void onWebSocketClose(int statusCode, String reason) {
            mWebSocketListener.onClose();
            logger.info("Z-Way WebSocket closed: {}", statusCode);
        }

        @Override
        public void onWebSocketConnect(Session session) {
            mWebSocketListener.onConnect();
            logger.info("Z-Way WebSocket connected: {}", session);
        }

        @Override
        public void onWebSocketError(Throwable error) {
            mWebSocketListener.onError(error);
            logger.info("Z-Way WebSocket error: {} - {}", error.getClass().getName(), error.getMessage());
        }

        @Override
        public void onWebSocketBinary(byte[] arg0, int arg1, int arg2) {
            logger.info("Z-Way WebSocket binary");
        }

        @Override
        public void onWebSocketText(String message) {
            logger.info("Z-Way WebSocket message received: {}", message);
            onMessage(message);
        }
    }
}
