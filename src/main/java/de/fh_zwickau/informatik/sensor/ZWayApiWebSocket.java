/**
 * Copyright (C) 2016 by Software-Systementwicklung Zwickau Research Group
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package de.fh_zwickau.informatik.sensor;

import java.net.URI;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

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

    public ZWayApiWebSocket(String ipAddress, Integer port, String protocol, String username, String password,
            IZWayApiCallbacks caller) {
        super(ipAddress, port, protocol, username, password, caller);

        mSocket = new ZWayWebSocket();
    }

    public void connect() {
        mClient = new WebSocketClient();

        try {
            mClient.start();

            URI destUri = new URI("ws://" + mIpAddress + ":" + mPort);

            ClientUpgradeRequest request = new ClientUpgradeRequest();
            mClient.connect(mSocket, destUri, request);
        } catch (Exception e) {
            logger.warn("Z-Way WebSocket connect: {}", e.getMessage());
            mCaller.responseFormatError("Z-Way WebSocket connect: " + e.getMessage(), true);
        }
    }

    public void disconnect() {
        try {
            mSocket.awaitClose(5, TimeUnit.SECONDS);

            mClient.stop();
        } catch (Exception e) {
            logger.warn("Z-Way WebSocket disconnect: {}", e.getMessage());
            mCaller.responseFormatError("Z-Way WebSocket disconnect: " + e.getMessage(), true);
        }
    }

    private void onMessage(String message) {
        // Request performed successfully: load response body
        // Expected response format: { "type": "...", "data": {...} }
        try {
            Gson gson = new Gson();
            String type = gson.fromJson(message, JsonObject.class).get("type").getAsString();
            logger.info("Z-Way WebSocket message type: {}", type);

            if (type.equals("connector.openhab.devices.level_update")) {
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
            logger.info("Z-Way WebSocket closed: {}", statusCode);
        }

        @Override
        public void onWebSocketConnect(Session session) {
            logger.info("Z-Way WebSocket connected");
        }

        @Override
        public void onWebSocketError(Throwable error) {
            logger.info("Z-Way WebSocket error: {}", error.getMessage());
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
