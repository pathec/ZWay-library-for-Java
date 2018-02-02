/**
 * Copyright (C) 2016 by Software-Systementwicklung Zwickau Research Group
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package de.fh_zwickau.informatik.sensor;

import static de.fh_zwickau.informatik.sensor.ZWayConstants.*;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.net.CookieStore;
import java.net.HttpCookie;
import java.net.URI;
import java.net.URLEncoder;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map.Entry;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.jetty.client.HttpClient;
import org.eclipse.jetty.client.HttpResponseException;
import org.eclipse.jetty.client.api.ContentResponse;
import org.eclipse.jetty.client.api.Request;
import org.eclipse.jetty.client.api.Request.FailureListener;
import org.eclipse.jetty.client.api.Result;
import org.eclipse.jetty.client.util.BufferingResponseListener;
import org.eclipse.jetty.client.util.MultiPartContentProvider;
import org.eclipse.jetty.client.util.PathContentProvider;
import org.eclipse.jetty.client.util.StringContentProvider;
import org.eclipse.jetty.http.HttpHeader;
import org.eclipse.jetty.http.HttpMethod;
import org.eclipse.jetty.http.HttpStatus;
import org.eclipse.jetty.util.ssl.SslContextFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;

import de.fh_zwickau.informatik.sensor.model.devicehistory.DeviceHistoryData;
import de.fh_zwickau.informatik.sensor.model.devicehistory.DeviceHistoryList;
import de.fh_zwickau.informatik.sensor.model.devicehistory.DeviceHistoryListDeserializer;
import de.fh_zwickau.informatik.sensor.model.devices.Device;
import de.fh_zwickau.informatik.sensor.model.devices.DeviceCommand;
import de.fh_zwickau.informatik.sensor.model.devices.DeviceList;
import de.fh_zwickau.informatik.sensor.model.devices.DeviceListDeserializer;
import de.fh_zwickau.informatik.sensor.model.icons.IconList;
import de.fh_zwickau.informatik.sensor.model.icons.IconListDeserializer;
import de.fh_zwickau.informatik.sensor.model.instances.Instance;
import de.fh_zwickau.informatik.sensor.model.instances.InstanceList;
import de.fh_zwickau.informatik.sensor.model.instances.InstanceListDeserializer;
import de.fh_zwickau.informatik.sensor.model.locations.LocationList;
import de.fh_zwickau.informatik.sensor.model.locations.LocationListDeserializer;
import de.fh_zwickau.informatik.sensor.model.login.LoginForm;
import de.fh_zwickau.informatik.sensor.model.notifications.NotificationList;
import de.fh_zwickau.informatik.sensor.model.notifications.NotificationListDeserializer;
import de.fh_zwickau.informatik.sensor.model.profiles.Profile;
import de.fh_zwickau.informatik.sensor.model.profiles.ProfileList;
import de.fh_zwickau.informatik.sensor.model.profiles.ProfileListDeserializer;
import de.fh_zwickau.informatik.sensor.model.system.SystemInfo;
import de.fh_zwickau.informatik.sensor.model.zwaveapi.controller.ZWaveController;
import de.fh_zwickau.informatik.sensor.model.zwaveapi.devices.ZWaveDevice;

/**
 * The {@link ZWayApiHttp} implements the ZAutomation API. See also:
 * http://docs.zwayhomeautomation.apiary.io/#
 *
 * @author Patrick Hecker - Initial contribution
 */
public class ZWayApiHttp extends ZWayApiBase {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private final static int HTTP_CLIENT_TIMEOUT = 5000;

    private HttpClient mHttpClient = null;

    /**
     * Setup the Z-Way API with passed values.
     *
     * @param ipAddress ip address
     * @param port port number
     * @param protocol protocol
     * @param username username
     * @param password password
     * @param remoteId remote id
     * @param useRemoteService
     * @param caller receive callbacks
     */
    public ZWayApiHttp(String ipAddress, Integer port, String protocol, String username, String password,
            Integer remoteId, Boolean useRemoteService, IZWayApiCallbacks caller) {

        super(ipAddress, port, protocol, username, password, remoteId, useRemoteService, caller);

        initHttpClient();
    }

    /*
     * (non-Javadoc)
     *
     * @see de.fh_zwickau.informatik.sensor.ZWayApiBase#getLogin()
     */
    @Override
    public synchronized String getLogin() {
        try {
            startHttpClient();

            if (mUseRemoteService) {
                mZWaySessionId = null;
                mZWayRemoteSessionId = null;

                // Build request body
                String body = "act=login&login=" + mRemoteId + "/" + mUsername + "&pass=" + mPassword;

                logger.info("Remote login body: {}", body);
                logger.info("Remote path: {}", getTopLevelUrl() + "/" + REMOTE_PATH_LOGIN);

                Request request = mHttpClient.newRequest(getTopLevelUrl() + "/" + REMOTE_PATH_LOGIN)
                        .method(HttpMethod.POST).header(HttpHeader.CONTENT_TYPE, "application/x-www-form-urlencoded")
                        .content(new StringContentProvider(body), "application/x-www-form-urlencoded");

                ContentResponse response = request.send();

                // Check HTTP status code
                int statusCode = response.getStatus();

                if (statusCode != HttpStatus.OK_200) {
                    String reason = response.getReason();
                    mCaller.httpStatusError(statusCode, reason, true);
                    logger.debug("Communication with Z-Way server failed: {} {}", statusCode, reason);
                }

                CookieStore cookieStore = mHttpClient.getCookieStore();
                List<HttpCookie> cookies = cookieStore.get(URI.create("https://find.z-wave.me/"));
                for (HttpCookie cookie : cookies) {
                    if (cookie.getName().equals("ZWAYSession")) {
                        mZWaySessionId = cookie.getValue();
                    } else if (cookie.getName().equals("ZBW_SESSID")) {
                        mZWayRemoteSessionId = cookie.getValue();
                    }
                    logger.info("HTTP cookie: {} - {}", cookie.getName(), cookie.getValue());
                }

                if (mZWayRemoteSessionId != null && mZWaySessionId != null) {
                    mCaller.getLoginResponse(mZWaySessionId);
                    return mZWaySessionId;
                } else {
                    logger.warn("Response doesn't contain required cookies");
                    mCaller.responseFormatError("Response doesn't contain required cookies", true);
                }
            } else {
                // Build request body
                LoginForm loginForm = new LoginForm(true, mUsername, mPassword, false, 1);

                Request request = mHttpClient.newRequest(getZAutomationTopLevelUrl() + "/" + PATH_LOGIN)
                        .method(HttpMethod.POST).header(HttpHeader.ACCEPT, "application/json")
                        .header(HttpHeader.CONTENT_TYPE, "application/json")
                        .content(new StringContentProvider(new Gson().toJson(loginForm)), "application/json");

                ContentResponse response = request.send();

                // Check HTTP status code
                int statusCode = response.getStatus();
                if (statusCode != HttpStatus.OK_200) {
                    String reason = response.getReason();
                    mCaller.httpStatusError(statusCode, reason, true);
                    logger.debug("Communication with Z-Way server failed: {} {}", statusCode, reason);
                }

                String responseBody = response.getContentAsString();
                try {
                    Gson gson = new Gson();
                    // Response -> String -> Json -> extract data field
                    JsonObject responseDataAsJson = gson.fromJson(responseBody, JsonObject.class).get("data")
                            .getAsJsonObject(); // extract data field

                    mZWaySessionId = responseDataAsJson.get("sid").getAsString(); // extract SID field
                    mCaller.getLoginResponse(mZWaySessionId);
                    return mZWaySessionId;
                } catch (JsonParseException e) {
                    logger.warn("Unexpected response format: {}", e.getMessage());
                    mCaller.responseFormatError("Unexpected response format: " + e.getMessage(), true);
                }
            }
        } catch (Exception e) {
            logger.warn("Request getLogin() failed: {}", e.getMessage());
            mCaller.apiError(e.getMessage(), true);
        } finally {
            stopHttpClient();
        }

        mZWaySessionId = null;
        mZWayRemoteSessionId = null;
        mCaller.authenticationError();
        return null;
    }

    /*
     * (non-Javadoc)
     *
     * @see de.fh_zwickau.informatik.sensor.ZWayApiBase#getCurrentProfile()
     */
    @Override
    public synchronized Profile getCurrentProfile() {
        if (checkLogin()) {
            try {
                startHttpClient();

                // Build request body
                LoginForm loginForm = new LoginForm(true, mUsername, mPassword, false, 1);

                Request request = mHttpClient.newRequest(getZAutomationTopLevelUrl() + "/" + PATH_LOGIN)
                        .method(HttpMethod.POST).header(HttpHeader.ACCEPT, "application/json")
                        .header(HttpHeader.CONTENT_TYPE, "application/json")
                        .content(new StringContentProvider(new Gson().toJson(loginForm)), "application/json");

                if (mUseRemoteService) {
                    request.cookie(new HttpCookie("ZBW_SESSID", mZWayRemoteSessionId));
                }

                ContentResponse response = request.send();

                // Check HTTP status code
                int statusCode = response.getStatus();
                if (statusCode != HttpStatus.OK_200) {
                    // Authentication error - retry login and operation
                    if (statusCode == HttpStatus.UNAUTHORIZED_401) {
                        if (getLogin() == null) {
                            mCaller.authenticationError();
                        } else {
                            return getCurrentProfile();
                        }
                    } else {
                        processResponseStatus(statusCode);
                    }
                } else {
                    String responseBody = response.getContentAsString();
                    try {
                        Gson gson = new Gson();
                        // Response -> String -> Json -> extract data field
                        JsonObject profileAsJson = gson.fromJson(responseBody, JsonObject.class).get("data")
                                .getAsJsonObject(); // extract data field

                        return new ProfileListDeserializer().deserializeProfile(profileAsJson);
                    } catch (JsonParseException e) {
                        logger.warn("Unexpected response format: {}", e.getMessage());
                        mCaller.responseFormatError("Unexpected response format: " + e.getMessage(), true);
                    }
                }
            } catch (Exception e) {
                if (e.getCause() instanceof HttpResponseException) {
                    int statusCode = ((HttpResponseException) e.getCause()).getResponse().getStatus();
                    // Authentication error - retry login and operation
                    if (statusCode == HttpStatus.UNAUTHORIZED_401) {
                        if (getLogin() == null) {
                            mCaller.authenticationError();
                        } else {
                            return getCurrentProfile();
                        }
                    }
                } else {
                    handleException(e, "get current profile");
                }
            } finally {
                stopHttpClient();
            }
        } // no else ... checkLogin() method will invoke the appropriate callback method

        return null;
    }

    /*
     * (non-Javadoc)
     *
     * @see de.fh_zwickau.informatik.sensor.ZWayApiBase#getSystemInfo()
     */
    @Override
    public synchronized SystemInfo getSystemInfo() {
        if (checkLogin()) {
            try {
                startHttpClient();

                Request request = mHttpClient.newRequest(getZAutomationTopLevelUrl() + "/" + PATH_SYSTEM_INFO)
                        .method(HttpMethod.GET).header(HttpHeader.ACCEPT, "application/json")
                        .header(HttpHeader.CONTENT_TYPE, "application/json")
                        .cookie(new HttpCookie("ZWAYSession", mZWaySessionId));

                if (mUseRemoteService) {
                    request.cookie(new HttpCookie("ZBW_SESSID", mZWayRemoteSessionId));
                }

                ContentResponse response = request.send();

                // Check HTTP status code
                int statusCode = response.getStatus();
                if (statusCode != HttpStatus.OK_200) {
                    // Authentication error - retry login and operation
                    if (statusCode == HttpStatus.UNAUTHORIZED_401) {
                        if (getLogin() == null) {
                            mCaller.authenticationError();
                        } else {
                            return getSystemInfo();
                        }
                    } else {
                        processResponseStatus(statusCode);
                    }
                } else {
                    String responseBody = response.getContentAsString();
                    try {
                        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").create();
                        // Response -> String -> Json -> extract data field
                        JsonObject systemInfoAsJson = gson.fromJson(responseBody, JsonObject.class).get("data")
                                .getAsJsonObject(); // extract data field

                        return gson.fromJson(systemInfoAsJson, SystemInfo.class);
                    } catch (JsonParseException e) {
                        logger.warn("Unexpected response format: {}", e.getMessage());
                        mCaller.responseFormatError("Unexpected response format: " + e.getMessage(), true);
                    }
                }
            } catch (Exception e) {
                if (e.getCause() instanceof HttpResponseException) {
                    int statusCode = ((HttpResponseException) e.getCause()).getResponse().getStatus();
                    // Authentication error - retry login and operation
                    if (statusCode == HttpStatus.UNAUTHORIZED_401) {
                        if (getLogin() == null) {
                            mCaller.authenticationError();
                        } else {
                            return getSystemInfo();
                        }
                    }
                } else {
                    handleException(e, "get system info");
                }
            } finally {
                stopHttpClient();
            }
        } // no else ... checkLogin() method will invoke the appropriate callback method

        return null;
    }

    /*
     * (non-Javadoc)
     *
     * @see de.fh_zwickau.informatik.sensor.ZWayApiBase#getSystemInfo(de.fh_zwickau.informatik.sensor.IZWayCallback)
     */
    @Override
    public void getSystemInfo(final IZWayCallback<SystemInfo> callback) {
        if (checkLogin()) {
            try {
                startHttpClient();

                Request request = mHttpClient.newRequest(getZAutomationTopLevelUrl() + "/" + PATH_SYSTEM_INFO)
                        .method(HttpMethod.GET).header(HttpHeader.ACCEPT, "application/json")
                        .header(HttpHeader.CONTENT_TYPE, "application/json")
                        .cookie(new HttpCookie("ZWAYSession", mZWaySessionId))
                        .onRequestFailure(new ZWayFailureListener());

                if (mUseRemoteService) {
                    request.cookie(new HttpCookie("ZBW_SESSID", mZWayRemoteSessionId));
                }

                request.send(new BufferingResponseListener() {
                    @Override
                    public void onComplete(Result result) {
                        int statusCode = result.getResponse().getStatus();
                        if (statusCode != HttpStatus.OK_200) {
                            if (statusCode == HttpStatus.UNAUTHORIZED_401) {
                                if (getLogin() == null) {
                                    mCaller.authenticationError();
                                } else {
                                    getSystemInfo(callback);
                                }
                            } else {
                                processResponseStatus(statusCode);
                            }
                        } else {
                            String responseBody = getContentAsString();
                            try {
                                Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").create();
                                // Response -> String -> Json -> extract data field
                                JsonObject systemInfoAsJson = gson.fromJson(responseBody, JsonObject.class).get("data")
                                        .getAsJsonObject(); // extract data field

                                callback.onSuccess(gson.fromJson(systemInfoAsJson, SystemInfo.class));
                            } catch (JsonParseException e) {
                                logger.warn("Unexpected response format: {}", e.getMessage());
                                mCaller.responseFormatError("Unexpected response format: " + e.getMessage(), true);
                            }
                        }
                    }
                });
            } catch (Exception e) {
                if (e.getCause() instanceof HttpResponseException) {
                    int statusCode = ((HttpResponseException) e.getCause()).getResponse().getStatus();
                    // Authentication error - retry login and operation
                    if (statusCode == HttpStatus.UNAUTHORIZED_401) {
                        if (getLogin() == null) {
                            mCaller.authenticationError();
                        } else {
                            getSystemInfo(callback);
                        }
                    }
                } else {
                    handleException(e, "get system info");
                }
            } finally {
                // do not stop http client for asynchronous call
            }
        } // no else ... checkLogin() method will invoke the appropriate callback method
    }

    /*
     * (non-Javadoc)
     *
     * @see de.fh_zwickau.informatik.sensor.ZWayApiBase#getInstances()
     */
    @Override
    public synchronized InstanceList getInstances() {
        if (checkLogin()) {
            try {
                startHttpClient();

                Request request = mHttpClient.newRequest(getZAutomationTopLevelUrl() + "/" + PATH_INSTANCES)
                        .method(HttpMethod.GET).header(HttpHeader.ACCEPT, "application/json")
                        .header(HttpHeader.CONTENT_TYPE, "application/json")
                        .cookie(new HttpCookie("ZWAYSession", mZWaySessionId));

                if (mUseRemoteService) {
                    request.cookie(new HttpCookie("ZBW_SESSID", mZWayRemoteSessionId));
                }

                ContentResponse response = request.send();

                // Check HTTP status code
                int statusCode = response.getStatus();
                if (statusCode != HttpStatus.OK_200) {
                    // Authentication error - retry login and operation
                    if (statusCode == HttpStatus.UNAUTHORIZED_401) {
                        if (getLogin() == null) {
                            mCaller.authenticationError();
                        } else {
                            return getInstances();
                        }
                    } else {
                        processResponseStatus(statusCode);
                    }
                } else {
                    return parseGetInstances(response.getContentAsString());
                }
            } catch (Exception e) {
                if (e.getCause() instanceof HttpResponseException) {
                    int statusCode = ((HttpResponseException) e.getCause()).getResponse().getStatus();
                    // Authentication error - retry login and operation
                    if (statusCode == HttpStatus.UNAUTHORIZED_401) {
                        if (getLogin() == null) {
                            mCaller.authenticationError();
                        } else {
                            return getInstances();
                        }
                    }
                } else {
                    handleException(e, "get instances");
                }
            } finally {
                stopHttpClient();
            }
        } // no else ... checkLogin() method will invoke the appropriate callback method

        return null;
    }

    /*
     * (non-Javadoc)
     *
     * @see de.fh_zwickau.informatik.sensor.ZWayApiBase#getInstances(de.fh_zwickau.informatik.sensor.IZWayCallback)
     */
    @Override
    public void getInstances(final IZWayCallback<InstanceList> callback) {
        if (checkLogin()) {
            try {
                startHttpClient();

                Request request = mHttpClient.newRequest(getZAutomationTopLevelUrl() + "/" + PATH_INSTANCES)
                        .method(HttpMethod.GET).header(HttpHeader.ACCEPT, "application/json")
                        .header(HttpHeader.CONTENT_TYPE, "application/json")
                        .cookie(new HttpCookie("ZWAYSession", mZWaySessionId))
                        .onRequestFailure(new ZWayFailureListener());

                if (mUseRemoteService) {
                    request.cookie(new HttpCookie("ZBW_SESSID", mZWayRemoteSessionId));
                }

                request.send(new BufferingResponseListener() {
                    @Override
                    public void onComplete(Result result) {
                        int statusCode = result.getResponse().getStatus();
                        if (statusCode != HttpStatus.OK_200) {
                            if (statusCode == HttpStatus.UNAUTHORIZED_401) {
                                if (getLogin() == null) {
                                    mCaller.authenticationError();
                                } else {
                                    getInstances(callback);
                                }
                            } else {
                                processResponseStatus(statusCode);
                            }
                        } else {
                            callback.onSuccess(parseGetInstances(getContentAsString()));
                        }
                    }
                });
            } catch (Exception e) {
                if (e.getCause() instanceof HttpResponseException) {
                    int statusCode = ((HttpResponseException) e.getCause()).getResponse().getStatus();
                    // Authentication error - retry login and operation
                    if (statusCode == HttpStatus.UNAUTHORIZED_401) {
                        if (getLogin() == null) {
                            mCaller.authenticationError();
                        } else {
                            getInstances(callback);
                        }
                    }
                } else {
                    handleException(e, "get instances");
                }
            } finally {
                // do not stop http client for asynchronous call
            }
        } // no else ... checkLogin() method will invoke the appropriate callback method
    }

    private synchronized InstanceList parseGetInstances(String data) {
        // Request performed successfully: load response body
        // Expected response format: { "data": [...] }, ... }
        try {
            Gson gson = new Gson();
            // Response -> String -> Json -> extract data field
            JsonArray instancesAsJson = gson.fromJson(data, JsonObject.class).get("data").getAsJsonArray();

            return new InstanceListDeserializer().deserializeInstanceList(instancesAsJson);
        } catch (JsonParseException e) {
            logger.warn("Unexpected response format: {}", e.getMessage());
            mCaller.responseFormatError("Unexpected response format: " + e.getMessage(), false);
            return null;
        }
    }

    /*
     * (non-Javadoc)
     *
     * @see de.fh_zwickau.informatik.sensor.ZWayApiBase#getLocations()
     */
    @Override
    public synchronized LocationList getLocations() {
        if (checkLogin()) {
            try {
                startHttpClient();

                Request request = mHttpClient.newRequest(getZAutomationTopLevelUrl() + "/" + PATH_LOCATIONS)
                        .method(HttpMethod.GET).header(HttpHeader.ACCEPT, "application/json")
                        .header(HttpHeader.CONTENT_TYPE, "application/json")
                        .cookie(new HttpCookie("ZWAYSession", mZWaySessionId));

                if (mUseRemoteService) {
                    request.cookie(new HttpCookie("ZBW_SESSID", mZWayRemoteSessionId));
                }

                ContentResponse response = request.send();

                // Check HTTP status code
                int statusCode = response.getStatus();
                if (statusCode != HttpStatus.OK_200) {
                    // Authentication error - retry login and operation
                    if (statusCode == HttpStatus.UNAUTHORIZED_401) {
                        if (getLogin() == null) {
                            mCaller.authenticationError();
                        } else {
                            return getLocations();
                        }
                    } else {
                        processResponseStatus(statusCode);
                    }
                } else {
                    return parseGetLocations(response.getContentAsString());
                }
            } catch (Exception e) {
                if (e.getCause() instanceof HttpResponseException) {
                    int statusCode = ((HttpResponseException) e.getCause()).getResponse().getStatus();
                    // Authentication error - retry login and operation
                    if (statusCode == HttpStatus.UNAUTHORIZED_401) {
                        if (getLogin() == null) {
                            mCaller.authenticationError();
                        } else {
                            return getLocations();
                        }
                    }
                } else {
                    handleException(e, "get locations");
                }
            } finally {
                stopHttpClient();
            }
        } // no else ... checkLogin() method will invoke the appropriate callback method

        return null;
    }

    /*
     * (non-Javadoc)
     *
     * @see de.fh_zwickau.informatik.sensor.ZWayApiBase#getLocations(de.fh_zwickau.informatik.sensor.IZWayCallback)
     */
    @Override
    public void getLocations(final IZWayCallback<LocationList> callback) {
        if (checkLogin()) {
            try {
                startHttpClient();

                Request request = mHttpClient.newRequest(getZAutomationTopLevelUrl() + "/" + PATH_INSTANCES)
                        .method(HttpMethod.GET).header(HttpHeader.ACCEPT, "application/json")
                        .header(HttpHeader.CONTENT_TYPE, "application/json")
                        .cookie(new HttpCookie("ZWAYSession", mZWaySessionId))
                        .onRequestFailure(new ZWayFailureListener());

                if (mUseRemoteService) {
                    request.cookie(new HttpCookie("ZBW_SESSID", mZWayRemoteSessionId));
                }

                request.send(new BufferingResponseListener() {
                    @Override
                    public void onComplete(Result result) {
                        int statusCode = result.getResponse().getStatus();
                        if (statusCode != HttpStatus.OK_200) {
                            if (statusCode == HttpStatus.UNAUTHORIZED_401) {
                                if (getLogin() == null) {
                                    mCaller.authenticationError();
                                } else {
                                    getLocations(callback);
                                }
                            } else {
                                processResponseStatus(statusCode);
                            }
                        } else {
                            callback.onSuccess(parseGetLocations(getContentAsString()));
                        }
                    }
                });
            } catch (Exception e) {
                if (e.getCause() instanceof HttpResponseException) {
                    int statusCode = ((HttpResponseException) e.getCause()).getResponse().getStatus();
                    // Authentication error - retry login and operation
                    if (statusCode == HttpStatus.UNAUTHORIZED_401) {
                        if (getLogin() == null) {
                            mCaller.authenticationError();
                        } else {
                            getLocations(callback);
                        }
                    }
                } else {
                    handleException(e, "get locations");
                }
            } finally {
                // do not stop http client for asynchronous call
            }
        } // no else ... checkLogin() method will invoke the appropriate callback method
    }

    private synchronized LocationList parseGetLocations(String data) {
        // Request performed successfully: load response body
        // Expected response format: { "data": [...] }, ... }
        try {
            Gson gson = new Gson();
            // Response -> String -> Json -> extract data field
            JsonArray locationsAsJson = gson.fromJson(data, JsonObject.class).get("data").getAsJsonArray();

            return new LocationListDeserializer().deserializeLocationList(locationsAsJson);
        } catch (JsonParseException e) {
            logger.warn("Unexpected response format: {}", e.getMessage());
            mCaller.responseFormatError("Unexpected response format: " + e.getMessage(), false);
            return null;
        }
    }

    /*
     * (non-Javadoc)
     *
     * @see de.fh_zwickau.informatik.sensor.ZWayApiBase#getDeviceHistories()
     */
    @Override
    public synchronized DeviceHistoryList getDeviceHistories() {
        if (checkLogin()) {
            try {
                startHttpClient();

                Request request = mHttpClient.newRequest(getZAutomationTopLevelUrl() + "/" + PATH_DEVICE_HISTORY)
                        .method(HttpMethod.GET).header(HttpHeader.ACCEPT, "application/json")
                        .header(HttpHeader.CONTENT_TYPE, "application/json")
                        .cookie(new HttpCookie("ZWAYSession", mZWaySessionId));

                if (mUseRemoteService) {
                    request.cookie(new HttpCookie("ZBW_SESSID", mZWayRemoteSessionId));
                }

                ContentResponse response = request.send();

                // Check HTTP status code
                int statusCode = response.getStatus();
                if (statusCode != HttpStatus.OK_200) {
                    // Authentication error - retry login and operation
                    if (statusCode == HttpStatus.UNAUTHORIZED_401) {
                        if (getLogin() == null) {
                            mCaller.authenticationError();
                        } else {
                            return getDeviceHistories();
                        }
                    } else {
                        processResponseStatus(statusCode);
                    }
                } else {
                    return parseGetDeviceHistories(response.getContentAsString());
                }
            } catch (Exception e) {
                if (e.getCause() instanceof HttpResponseException) {
                    int statusCode = ((HttpResponseException) e.getCause()).getResponse().getStatus();
                    // Authentication error - retry login and operation
                    if (statusCode == HttpStatus.UNAUTHORIZED_401) {
                        if (getLogin() == null) {
                            mCaller.authenticationError();
                        } else {
                            return getDeviceHistories();
                        }
                    }
                } else {
                    handleException(e, "get device histories");
                }
            } finally {
                stopHttpClient();
            }
        } // no else ... checkLogin() method will invoke the appropriate callback method

        return null;
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * de.fh_zwickau.informatik.sensor.ZWayApiBase#getDeviceHistories(de.fh_zwickau.informatik.sensor.IZWayCallback)
     */
    @Override
    public void getDeviceHistories(final IZWayCallback<DeviceHistoryList> callback) {
        if (checkLogin()) {
            try {
                startHttpClient();

                Request request = mHttpClient.newRequest(getZAutomationTopLevelUrl() + "/" + PATH_DEVICE_HISTORY)
                        .method(HttpMethod.GET).header(HttpHeader.ACCEPT, "application/json")
                        .header(HttpHeader.CONTENT_TYPE, "application/json")
                        .cookie(new HttpCookie("ZWAYSession", mZWaySessionId))
                        .onRequestFailure(new ZWayFailureListener());

                if (mUseRemoteService) {
                    request.cookie(new HttpCookie("ZBW_SESSID", mZWayRemoteSessionId));
                }

                request.send(new BufferingResponseListener() {
                    @Override
                    public void onComplete(Result result) {
                        int statusCode = result.getResponse().getStatus();
                        if (statusCode != HttpStatus.OK_200) {
                            if (statusCode == HttpStatus.UNAUTHORIZED_401) {
                                if (getLogin() == null) {
                                    mCaller.authenticationError();
                                } else {
                                    getDeviceHistories(callback);
                                }
                            } else {
                                processResponseStatus(statusCode);
                            }
                        } else {
                            callback.onSuccess(parseGetDeviceHistories(getContentAsString()));
                        }
                    }
                });
            } catch (Exception e) {
                if (e.getCause() instanceof HttpResponseException) {
                    int statusCode = ((HttpResponseException) e.getCause()).getResponse().getStatus();
                    // Authentication error - retry login and operation
                    if (statusCode == HttpStatus.UNAUTHORIZED_401) {
                        if (getLogin() == null) {
                            mCaller.authenticationError();
                        } else {
                            getDeviceHistories(callback);
                        }
                    }
                } else {
                    handleException(e, "get device histories");
                }
            } finally {
                // do not stop http client for asynchronous call
            }
        } // no else ... checkLogin() method will invoke the appropriate callback method
    }

    private synchronized DeviceHistoryList parseGetDeviceHistories(String data) {
        // Request performed successfully: load response body
        // Expected response format: { "data": [...] }, ... }
        try {
            Gson gson = new Gson();
            // Response -> String -> Json -> extract data field
            JsonObject responseDataAsJson = gson.fromJson(data, JsonObject.class).get("data").getAsJsonObject();
            // extract history array field
            JsonArray historiesAsJson = responseDataAsJson.get("history").getAsJsonArray();

            return new DeviceHistoryListDeserializer().deserializeDeviceHistoryList(historiesAsJson);
        } catch (JsonParseException e) {
            logger.warn("Unexpected response format: {}", e.getMessage());
            mCaller.responseFormatError("Unexpected response format: " + e.getMessage(), false);
            return null;
        }
    }

    /*
     * (non-Javadoc)
     *
     * @see de.fh_zwickau.informatik.sensor.ZWayApiBase#getDeviceHistory(java.lang.String, java.lang.Long)
     */
    @Override
    public synchronized ArrayList<DeviceHistoryData> getDeviceHistory(String deviceId, Long since) {
        if (checkLogin()) {
            try {
                startHttpClient();

                Request request = mHttpClient
                        .newRequest(getZAutomationTopLevelUrl() + "/" + PATH_DEVICE_HISTORY + "/" + deviceId + "?since="
                                + String.valueOf(since))
                        .method(HttpMethod.GET).header(HttpHeader.ACCEPT, "application/json")
                        .header(HttpHeader.CONTENT_TYPE, "application/json")
                        .cookie(new HttpCookie("ZWAYSession", mZWaySessionId));

                if (mUseRemoteService) {
                    request.cookie(new HttpCookie("ZBW_SESSID", mZWayRemoteSessionId));
                }

                ContentResponse response = request.send();

                // Check HTTP status code
                int statusCode = response.getStatus();
                if (statusCode != HttpStatus.OK_200) {
                    // Authentication error - retry login and operation
                    if (statusCode == HttpStatus.UNAUTHORIZED_401) {
                        if (getLogin() == null) {
                            mCaller.authenticationError();
                        } else {
                            return getDeviceHistory(deviceId, since);
                        }
                    } else {
                        processResponseStatus(statusCode);
                    }
                } else {
                    return parseGetDeviceHistory(response.getContentAsString());
                }
            } catch (Exception e) {
                if (e.getCause() instanceof HttpResponseException) {
                    int statusCode = ((HttpResponseException) e.getCause()).getResponse().getStatus();
                    // Authentication error - retry login and operation
                    if (statusCode == HttpStatus.UNAUTHORIZED_401) {
                        if (getLogin() == null) {
                            mCaller.authenticationError();
                        } else {
                            return getDeviceHistory(deviceId, since);
                        }
                    }
                } else {
                    handleException(e, "get device history");
                }
            } finally {
                stopHttpClient();
            }
        } // no else ... checkLogin() method will invoke the appropriate callback method

        return null;
    }

    /*
     * (non-Javadoc)
     *
     * @see de.fh_zwickau.informatik.sensor.ZWayApiBase#getDeviceHistory(java.lang.String, java.lang.Long,
     * de.fh_zwickau.informatik.sensor.IZWayCallback)
     */
    @Override
    public void getDeviceHistory(final String deviceId, final Long since,
            final IZWayCallback<ArrayList<DeviceHistoryData>> callback) {
        if (checkLogin()) {
            try {
                startHttpClient();

                Request request = mHttpClient
                        .newRequest(getZAutomationTopLevelUrl() + "/" + PATH_DEVICE_HISTORY + "/" + deviceId + "?since="
                                + String.valueOf(since))
                        .method(HttpMethod.GET).header(HttpHeader.ACCEPT, "application/json")
                        .header(HttpHeader.CONTENT_TYPE, "application/json")
                        .cookie(new HttpCookie("ZWAYSession", mZWaySessionId))
                        .onRequestFailure(new ZWayFailureListener());

                if (mUseRemoteService) {
                    request.cookie(new HttpCookie("ZBW_SESSID", mZWayRemoteSessionId));
                }

                request.send(new BufferingResponseListener() {
                    @Override
                    public void onComplete(Result result) {
                        int statusCode = result.getResponse().getStatus();
                        if (statusCode != HttpStatus.OK_200) {
                            if (statusCode == HttpStatus.UNAUTHORIZED_401) {
                                if (getLogin() == null) {
                                    mCaller.authenticationError();
                                } else {
                                    getDeviceHistory(deviceId, since, callback);
                                }
                            } else {
                                processResponseStatus(statusCode);
                            }
                        } else {
                            callback.onSuccess(parseGetDeviceHistory(getContentAsString()));
                        }
                    }
                });
            } catch (Exception e) {
                if (e.getCause() instanceof HttpResponseException) {
                    int statusCode = ((HttpResponseException) e.getCause()).getResponse().getStatus();
                    // Authentication error - retry login and operation
                    if (statusCode == HttpStatus.UNAUTHORIZED_401) {
                        if (getLogin() == null) {
                            mCaller.authenticationError();
                        } else {
                            getDeviceHistory(deviceId, since, callback);
                        }
                    }
                } else {
                    handleException(e, "get device history");
                }
            } finally {
                // do not stop http client for asynchronous call
            }
        } // no else ... checkLogin() method will invoke the appropriate callback method
    }

    private synchronized ArrayList<DeviceHistoryData> parseGetDeviceHistory(String data) {
        // Request performed successfully: load response body
        // Expected response format: { "data": {...} }, ... }
        try {
            Type collectionTypeHistoryData = new TypeToken<Collection<DeviceHistoryData>>() {
            }.getType();

            Gson gson = new Gson();
            // Response -> String -> Json -> extract data field
            JsonObject responseDataAsJson = gson.fromJson(data, JsonObject.class).get("data").getAsJsonObject();
            // extract history array field
            JsonArray historyAsJson = responseDataAsJson.get("deviceHistory").getAsJsonArray();
            return gson.fromJson(historyAsJson, collectionTypeHistoryData);

        } catch (JsonParseException e) {
            logger.warn("Unexpected response format: {}", e.getMessage());
            mCaller.responseFormatError("Unexpected response format: " + e.getMessage(), false);
            return null;
        }
    }

    /*
     * (non-Javadoc)
     *
     * @see de.fh_zwickau.informatik.sensor.ZWayApiBase#getProfiles()
     */
    @Override
    public synchronized ProfileList getProfiles() {
        if (checkLogin()) {
            try {
                startHttpClient();

                Request request = mHttpClient.newRequest(getZAutomationTopLevelUrl() + "/" + PATH_PROFILES)
                        .method(HttpMethod.GET).header(HttpHeader.ACCEPT, "application/json")
                        .header(HttpHeader.CONTENT_TYPE, "application/json")
                        .cookie(new HttpCookie("ZWAYSession", mZWaySessionId));

                if (mUseRemoteService) {
                    request.cookie(new HttpCookie("ZBW_SESSID", mZWayRemoteSessionId));
                }

                ContentResponse response = request.send();

                // Check HTTP status code
                int statusCode = response.getStatus();
                if (statusCode != HttpStatus.OK_200) {
                    // Authentication error - retry login and operation
                    if (statusCode == HttpStatus.UNAUTHORIZED_401) {
                        if (getLogin() == null) {
                            mCaller.authenticationError();
                        } else {
                            return getProfiles();
                        }
                    } else {
                        processResponseStatus(statusCode);
                    }
                } else {
                    return parseGetProfiles(response.getContentAsString());
                }
            } catch (Exception e) {
                if (e.getCause() instanceof HttpResponseException) {
                    int statusCode = ((HttpResponseException) e.getCause()).getResponse().getStatus();
                    // Authentication error - retry login and operation
                    if (statusCode == HttpStatus.UNAUTHORIZED_401) {
                        if (getLogin() == null) {
                            mCaller.authenticationError();
                        } else {
                            return getProfiles();
                        }
                    }
                } else {
                    handleException(e, "get profiles");
                }
            } finally {
                stopHttpClient();
            }
        } // no else ... checkLogin() method will invoke the appropriate callback method

        return null;
    }

    /*
     * (non-Javadoc)
     *
     * @see de.fh_zwickau.informatik.sensor.ZWayApiBase#getProfiles(de.fh_zwickau.informatik.sensor.IZWayCallback)
     */
    @Override
    public void getProfiles(final IZWayCallback<ProfileList> callback) {
        if (checkLogin()) {
            try {
                startHttpClient();

                Request request = mHttpClient.newRequest(getZAutomationTopLevelUrl() + "/" + PATH_PROFILES)
                        .method(HttpMethod.GET).header(HttpHeader.ACCEPT, "application/json")
                        .header(HttpHeader.CONTENT_TYPE, "application/json")
                        .cookie(new HttpCookie("ZWAYSession", mZWaySessionId))
                        .onRequestFailure(new ZWayFailureListener());

                if (mUseRemoteService) {
                    request.cookie(new HttpCookie("ZBW_SESSID", mZWayRemoteSessionId));
                }

                request.send(new BufferingResponseListener() {
                    @Override
                    public void onComplete(Result result) {
                        int statusCode = result.getResponse().getStatus();
                        if (statusCode != HttpStatus.OK_200) {
                            if (statusCode == HttpStatus.UNAUTHORIZED_401) {
                                if (getLogin() == null) {
                                    mCaller.authenticationError();
                                } else {
                                    getProfiles(callback);
                                }
                            } else {
                                processResponseStatus(statusCode);
                            }
                        } else {
                            callback.onSuccess(parseGetProfiles(getContentAsString()));
                        }
                    }
                });
            } catch (Exception e) {
                if (e.getCause() instanceof HttpResponseException) {
                    int statusCode = ((HttpResponseException) e.getCause()).getResponse().getStatus();
                    // Authentication error - retry login and operation
                    if (statusCode == HttpStatus.UNAUTHORIZED_401) {
                        if (getLogin() == null) {
                            mCaller.authenticationError();
                        } else {
                            getProfiles(callback);
                        }
                    }
                } else {
                    handleException(e, "get profiles");
                }
            } finally {
                // do not stop http client for asynchronous call
            }
        } // no else ... checkLogin() method will invoke the appropriate callback method
    }

    private synchronized ProfileList parseGetProfiles(String data) {
        // Request performed successfully: load response body
        // Expected response format: { "data": [...] }, ... }
        try {
            Gson gson = new Gson();
            // Response -> String -> Json -> extract data field
            JsonArray profilesAsJson = gson.fromJson(data, JsonObject.class).get("data").getAsJsonArray();

            return new ProfileListDeserializer().deserializeProfileList(profilesAsJson);
        } catch (JsonParseException e) {
            logger.warn("Unexpected response format: {}", e.getMessage());
            mCaller.responseFormatError("Unexpected response format: " + e.getMessage(), false);
            return null;
        }
    }

    /*
     * (non-Javadoc)
     *
     * @see de.fh_zwickau.informatik.sensor.ZWayApiBase#getNotifications()
     */
    @Override
    public synchronized NotificationList getNotifications(Long since) {
        if (checkLogin()) {
            try {
                startHttpClient();

                Request request = mHttpClient
                        .newRequest(getZAutomationTopLevelUrl() + "/" + PATH_NOTIFICATIONS + "?since=" + since)
                        .method(HttpMethod.GET).header(HttpHeader.ACCEPT, "application/json")
                        .header(HttpHeader.CONTENT_TYPE, "application/json")
                        .cookie(new HttpCookie("ZWAYSession", mZWaySessionId));

                if (mUseRemoteService) {
                    request.cookie(new HttpCookie("ZBW_SESSID", mZWayRemoteSessionId));
                }

                ContentResponse response = request.send();

                // Check HTTP status code
                int statusCode = response.getStatus();
                if (statusCode != HttpStatus.OK_200) {
                    // Authentication error - retry login and operation
                    if (statusCode == HttpStatus.UNAUTHORIZED_401) {
                        if (getLogin() == null) {
                            mCaller.authenticationError();
                        } else {
                            return getNotifications(since);
                        }
                    } else {
                        processResponseStatus(statusCode);
                    }
                } else {
                    return parseGetNotifications(response.getContentAsString());
                }
            } catch (Exception e) {
                if (e.getCause() instanceof HttpResponseException) {
                    int statusCode = ((HttpResponseException) e.getCause()).getResponse().getStatus();
                    // Authentication error - retry login and operation
                    if (statusCode == HttpStatus.UNAUTHORIZED_401) {
                        if (getLogin() == null) {
                            mCaller.authenticationError();
                        } else {
                            return getNotifications(since);
                        }
                    }
                } else {
                    handleException(e, "get notifications");
                }
            } finally {
                stopHttpClient();
            }
        } // no else ... checkLogin() method will invoke the appropriate callback method

        return null;
    }

    /*
     * (non-Javadoc)
     *
     * @see de.fh_zwickau.informatik.sensor.ZWayApiBase#getNotifications(de.fh_zwickau.informatik.sensor.IZWayCallback)
     */
    @Override
    public void getNotifications(final IZWayCallback<NotificationList> callback, final Long since) {
        if (checkLogin()) {
            try {
                startHttpClient();

                Request request = mHttpClient
                        .newRequest(getZAutomationTopLevelUrl() + "/" + PATH_NOTIFICATIONS + "?since=" + since)
                        .method(HttpMethod.GET).header(HttpHeader.ACCEPT, "application/json")
                        .header(HttpHeader.CONTENT_TYPE, "application/json")
                        .cookie(new HttpCookie("ZWAYSession", mZWaySessionId))
                        .onRequestFailure(new ZWayFailureListener());

                if (mUseRemoteService) {
                    request.cookie(new HttpCookie("ZBW_SESSID", mZWayRemoteSessionId));
                }

                request.send(new BufferingResponseListener() {
                    @Override
                    public void onComplete(Result result) {
                        int statusCode = result.getResponse().getStatus();
                        if (statusCode != HttpStatus.OK_200) {
                            if (statusCode == HttpStatus.UNAUTHORIZED_401) {
                                if (getLogin() == null) {
                                    mCaller.authenticationError();
                                } else {
                                    getNotifications(callback, since);
                                }
                            } else {
                                processResponseStatus(statusCode);
                            }
                        } else {
                            callback.onSuccess(parseGetNotifications(getContentAsString()));
                        }
                    }
                });
            } catch (Exception e) {
                if (e.getCause() instanceof HttpResponseException) {
                    int statusCode = ((HttpResponseException) e.getCause()).getResponse().getStatus();
                    // Authentication error - retry login and operation
                    if (statusCode == HttpStatus.UNAUTHORIZED_401) {
                        if (getLogin() == null) {
                            mCaller.authenticationError();
                        } else {
                            getNotifications(callback, since);
                        }
                    }
                } else {
                    handleException(e, "get notifications");
                }
            } finally {
                // do not stop http client for asynchronous call
            }
        } // no else ... checkLogin() method will invoke the appropriate callback method
    }

    private synchronized NotificationList parseGetNotifications(String data) {
        // Request performed successfully: load response body
        // Expected response format: { "data": "notifications": [...], ... }, ... }
        try {
            Gson gson = new Gson();
            // Response -> String -> Json -> extract data field -> extract notifications field
            JsonArray notificationsAsJson = gson.fromJson(data, JsonObject.class).get("data").getAsJsonObject()
                    .get("notifications").getAsJsonArray();

            return new NotificationListDeserializer().deserializeNotificationList(notificationsAsJson);
        } catch (JsonParseException e) {
            logger.warn("Unexpected response format: {}", e.getMessage());
            mCaller.responseFormatError("Unexpected response format: " + e.getMessage(), false);
            return null;
        }
    }

    /*
     * (non-Javadoc)
     *
     * @see de.fh_zwickau.informatik.sensor.ZWayApiBase#getInstance(java.lang.Integer)
     */
    @Override
    public synchronized Instance getInstance(Integer id) {
        if (checkLogin()) {
            try {
                startHttpClient();

                Request request = mHttpClient.newRequest(getZAutomationTopLevelUrl() + "/" + PATH_INSTANCES + "/" + id)
                        .method(HttpMethod.GET).header(HttpHeader.ACCEPT, "application/json")
                        .header(HttpHeader.CONTENT_TYPE, "application/json")
                        .cookie(new HttpCookie("ZWAYSession", mZWaySessionId));

                if (mUseRemoteService) {
                    request.cookie(new HttpCookie("ZBW_SESSID", mZWayRemoteSessionId));
                }

                ContentResponse response = request.send();

                // Check HTTP status code
                int statusCode = response.getStatus();
                if (statusCode != HttpStatus.OK_200) {
                    // Authentication error - retry login and operation
                    if (statusCode == HttpStatus.UNAUTHORIZED_401) {
                        if (getLogin() == null) {
                            mCaller.authenticationError();
                        } else {
                            return getInstance(id);
                        }
                    } else {
                        processResponseStatus(statusCode);
                    }
                } else {
                    return parseGetInstance(response.getContentAsString());
                }
            } catch (Exception e) {
                if (e.getCause() instanceof HttpResponseException) {
                    int statusCode = ((HttpResponseException) e.getCause()).getResponse().getStatus();
                    // Authentication error - retry login and operation
                    if (statusCode == HttpStatus.UNAUTHORIZED_401) {
                        if (getLogin() == null) {
                            mCaller.authenticationError();
                        } else {
                            return getInstance(id);
                        }
                    }
                } else {
                    handleException(e, "get instance");
                }
            } finally {
                stopHttpClient();
            }
        } // no else ... checkLogin() method will invoke the appropriate callback method

        return null;
    }

    /*
     * (non-Javadoc)
     *
     * @see de.fh_zwickau.informatik.sensor.ZWayApiBase#getInstance(java.lang.Integer,
     * de.fh_zwickau.informatik.sensor.IZWayCallback)
     */
    @Override
    public void getInstance(final Integer id, final IZWayCallback<Instance> callback) {
        if (checkLogin()) {
            try {
                startHttpClient();

                Request request = mHttpClient.newRequest(getZAutomationTopLevelUrl() + "/" + PATH_INSTANCES + "/" + id)
                        .method(HttpMethod.GET).header(HttpHeader.ACCEPT, "application/json")
                        .header(HttpHeader.CONTENT_TYPE, "application/json")
                        .cookie(new HttpCookie("ZWAYSession", mZWaySessionId))
                        .onRequestFailure(new ZWayFailureListener());

                if (mUseRemoteService) {
                    request.cookie(new HttpCookie("ZBW_SESSID", mZWayRemoteSessionId));
                }

                request.send(new BufferingResponseListener() {
                    @Override
                    public void onComplete(Result result) {
                        int statusCode = result.getResponse().getStatus();
                        if (statusCode != HttpStatus.OK_200) {
                            if (statusCode == HttpStatus.UNAUTHORIZED_401) {
                                if (getLogin() == null) {
                                    mCaller.authenticationError();
                                } else {
                                    getInstance(id, callback);
                                }
                            } else {
                                processResponseStatus(statusCode);
                            }
                        } else {
                            callback.onSuccess(parseGetInstance(getContentAsString()));
                        }
                    }
                });
            } catch (Exception e) {
                if (e.getCause() instanceof HttpResponseException) {
                    int statusCode = ((HttpResponseException) e.getCause()).getResponse().getStatus();
                    // Authentication error - retry login and operation
                    if (statusCode == HttpStatus.UNAUTHORIZED_401) {
                        if (getLogin() == null) {
                            mCaller.authenticationError();
                        } else {
                            getInstance(id, callback);
                        }
                    }
                } else {
                    handleException(e, "get instance");
                }
            } finally {
                // do not stop http client for asynchronous call
            }
        } // no else ... checkLogin() method will invoke the appropriate callback method
    }

    private synchronized Instance parseGetInstance(String data) {
        // Request performed successfully: load response body
        // Expected response format: { "data": { ... }, "code": 200, "message":
        // "200 - OK", ... }
        try {
            Gson gson = new Gson();
            // Response -> String -> Json -> extract data field
            JsonObject instanceAsJson = gson.fromJson(data, JsonObject.class).get("data").getAsJsonObject();

            return new InstanceListDeserializer().deserializeInstance(instanceAsJson);
        } catch (JsonParseException e) {
            logger.warn("Unexpected response format: {}", e.getMessage());
            mCaller.responseFormatError("Unexpected response format: " + e.getMessage(), false);
            return null;
        }
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * de.fh_zwickau.informatik.sensor.ZWayApiBase#putInstance(de.fh_zwickau.informatik.sensor.model.instances.Instance)
     */
    @Override
    public synchronized Instance putInstance(Instance instance) {
        if (checkLogin()) {
            try {
                startHttpClient();

                Request request = mHttpClient
                        .newRequest(getZAutomationTopLevelUrl() + "/" + PATH_INSTANCES + "/" + instance.getId())
                        .method(HttpMethod.PUT).header(HttpHeader.ACCEPT, "application/json")
                        .header(HttpHeader.CONTENT_TYPE, "application/json")
                        .cookie(new HttpCookie("ZWAYSession", mZWaySessionId))
                        .content(new StringContentProvider(new Gson().toJson(instance)), "application/json");

                if (mUseRemoteService) {
                    request.cookie(new HttpCookie("ZBW_SESSID", mZWayRemoteSessionId));
                }

                ContentResponse response = request.send();

                // Check HTTP status code
                int statusCode = response.getStatus();
                if (statusCode != HttpStatus.OK_200) {
                    // Authentication error - retry login and operation
                    if (statusCode == HttpStatus.UNAUTHORIZED_401) {
                        if (getLogin() == null) {
                            mCaller.authenticationError();
                        } else {
                            return putInstance(instance);
                        }
                    } else {
                        processResponseStatus(statusCode);
                    }
                } else {
                    return parsePutInstance(response.getContentAsString());
                }
            } catch (Exception e) {
                if (e.getCause() instanceof HttpResponseException) {
                    int statusCode = ((HttpResponseException) e.getCause()).getResponse().getStatus();
                    // Authentication error - retry login and operation
                    if (statusCode == HttpStatus.UNAUTHORIZED_401) {
                        if (getLogin() == null) {
                            mCaller.authenticationError();
                        } else {
                            return putInstance(instance);
                        }
                    }
                } else {
                    handleException(e, "put instance");
                }
            } finally {
                stopHttpClient();
            }
        } // no else ... checkLogin() method will invoke the appropriate callback method

        return null;
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * de.fh_zwickau.informatik.sensor.ZWayApiBase#putInstance(de.fh_zwickau.informatik.sensor.model.instances.Instance,
     * de.fh_zwickau.informatik.sensor.IZWayCallback)
     */
    @Override
    public void putInstance(final Instance instance, final IZWayCallback<Instance> callback) {
        if (checkLogin()) {
            try {
                startHttpClient();

                Request request = mHttpClient
                        .newRequest(getZAutomationTopLevelUrl() + "/" + PATH_INSTANCES + "/" + instance.getId())
                        .method(HttpMethod.PUT).header(HttpHeader.ACCEPT, "application/json")
                        .header(HttpHeader.CONTENT_TYPE, "application/json")
                        .cookie(new HttpCookie("ZWAYSession", mZWaySessionId))
                        .content(new StringContentProvider(new Gson().toJson(instance)), "application/json")
                        .onRequestFailure(new ZWayFailureListener());

                if (mUseRemoteService) {
                    request.cookie(new HttpCookie("ZBW_SESSID", mZWayRemoteSessionId));
                }

                request.send(new BufferingResponseListener() {
                    @Override
                    public void onComplete(Result result) {
                        int statusCode = result.getResponse().getStatus();
                        if (statusCode != HttpStatus.OK_200) {
                            if (statusCode == HttpStatus.UNAUTHORIZED_401) {
                                if (getLogin() == null) {
                                    mCaller.authenticationError();
                                } else {
                                    putInstance(instance, callback);
                                }
                            } else {
                                processResponseStatus(statusCode);
                            }
                        } else {
                            callback.onSuccess(parsePutInstance(getContentAsString()));
                        }
                    }
                });
            } catch (Exception e) {
                if (e.getCause() instanceof HttpResponseException) {
                    int statusCode = ((HttpResponseException) e.getCause()).getResponse().getStatus();
                    // Authentication error - retry login and operation
                    if (statusCode == HttpStatus.UNAUTHORIZED_401) {
                        if (getLogin() == null) {
                            mCaller.authenticationError();
                        } else {
                            putInstance(instance, callback);
                        }
                    }
                } else {
                    handleException(e, "put instance");
                }
            } finally {
                // do not stop http client for asynchronous call
            }
        } // no else ... checkLogin() method will invoke the appropriate callback method
    }

    private synchronized Instance parsePutInstance(String data) {
        // Request performed successfully: load response body
        // Expected response format: { "data": { ... }, "code": 200, "message":
        // "200 - OK", ... }
        try {
            Gson gson = new Gson();
            // Response -> String -> Json -> extract data field
            JsonObject instanceAsJson = gson.fromJson(data, JsonObject.class).get("data").getAsJsonObject();

            return new InstanceListDeserializer().deserializeInstance(instanceAsJson);
        } catch (JsonParseException e) {
            logger.warn("Unexpected response format: {}", e.getMessage());
            mCaller.responseFormatError("Unexpected response format: " + e.getMessage(), false);
            return null;
        }
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * de.fh_zwickau.informatik.sensor.ZWayApiBase#postInstance(de.fh_zwickau.informatik.sensor.model.instances.
     * Instance)
     */
    @Override
    public synchronized Instance postInstance(Instance instance) {
        if (checkLogin()) {
            try {
                startHttpClient();

                Request request = mHttpClient.newRequest(getZAutomationTopLevelUrl() + "/" + PATH_INSTANCES)
                        .method(HttpMethod.POST).header(HttpHeader.ACCEPT, "application/json")
                        .header(HttpHeader.CONTENT_TYPE, "application/json")
                        .cookie(new HttpCookie("ZWAYSession", mZWaySessionId))
                        .content(new StringContentProvider(new Gson().toJson(instance)), "application/json");

                if (mUseRemoteService) {
                    request.cookie(new HttpCookie("ZBW_SESSID", mZWayRemoteSessionId));
                }

                ContentResponse response = request.send();

                // Check HTTP status code
                int statusCode = response.getStatus();
                if (statusCode != HttpStatus.CREATED_201) {
                    // Authentication error - retry login and operation
                    if (statusCode == HttpStatus.UNAUTHORIZED_401) {
                        if (getLogin() == null) {
                            mCaller.authenticationError();
                        } else {
                            return putInstance(instance);
                        }
                    } else {
                        processResponseStatus(statusCode);
                    }
                } else {
                    return parsePostInstance(response.getContentAsString());
                }
            } catch (Exception e) {
                if (e.getCause() instanceof HttpResponseException) {
                    int statusCode = ((HttpResponseException) e.getCause()).getResponse().getStatus();
                    // Authentication error - retry login and operation
                    if (statusCode == HttpStatus.UNAUTHORIZED_401) {
                        if (getLogin() == null) {
                            mCaller.authenticationError();
                        } else {
                            return postInstance(instance);
                        }
                    }
                } else {
                    handleException(e, "post instance");
                }
            } finally {
                stopHttpClient();
            }
        } // no else ... checkLogin() method will invoke the appropriate callback method

        return null;
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * de.fh_zwickau.informatik.sensor.ZWayApiBase#postInstance(de.fh_zwickau.informatik.sensor.model.instances.
     * Instance,
     * de.fh_zwickau.informatik.sensor.IZWayCallback)
     */
    @Override
    public void postInstance(final Instance instance, final IZWayCallback<Instance> callback) {
        if (checkLogin()) {
            try {
                startHttpClient();

                Request request = mHttpClient.newRequest(getZAutomationTopLevelUrl() + "/" + PATH_INSTANCES)
                        .method(HttpMethod.POST).header(HttpHeader.ACCEPT, "application/json")
                        .header(HttpHeader.CONTENT_TYPE, "application/json")
                        .cookie(new HttpCookie("ZWAYSession", mZWaySessionId))
                        .content(new StringContentProvider(new Gson().toJson(instance)), "application/json")
                        .onRequestFailure(new ZWayFailureListener());

                if (mUseRemoteService) {
                    request.cookie(new HttpCookie("ZBW_SESSID", mZWayRemoteSessionId));
                }

                request.send(new BufferingResponseListener() {
                    @Override
                    public void onComplete(Result result) {
                        int statusCode = result.getResponse().getStatus();
                        if (statusCode != HttpStatus.CREATED_201) {
                            if (statusCode == HttpStatus.UNAUTHORIZED_401) {
                                if (getLogin() == null) {
                                    mCaller.authenticationError();
                                } else {
                                    postInstance(instance, callback);
                                }
                            } else {
                                processResponseStatus(statusCode);
                            }
                        } else {
                            callback.onSuccess(parsePostInstance(getContentAsString()));
                        }
                    }
                });
            } catch (Exception e) {
                if (e.getCause() instanceof HttpResponseException) {
                    int statusCode = ((HttpResponseException) e.getCause()).getResponse().getStatus();
                    // Authentication error - retry login and operation
                    if (statusCode == HttpStatus.UNAUTHORIZED_401) {
                        if (getLogin() == null) {
                            mCaller.authenticationError();
                        } else {
                            postInstance(instance, callback);
                        }
                    }
                } else {
                    handleException(e, "post instance");
                }
            } finally {
                // do not stop http client for asynchronous call
            }
        } // no else ... checkLogin() method will invoke the appropriate callback method
    }

    private synchronized Instance parsePostInstance(String data) {
        // Request performed successfully: load response body
        // Expected response format: { "data": { ... }, "code": 201, "message":
        // "201 - Created", ... }
        try {
            Gson gson = new Gson();
            // Response -> String -> Json -> extract data field -> id field
            Integer id = gson.fromJson(data, JsonObject.class).get("data").getAsJsonObject().get("id").getAsInt();

            return getInstance(id);
        } catch (JsonParseException e) {
            logger.warn("Unexpected response format: {}", e.getMessage());
            mCaller.responseFormatError("Unexpected response format: " + e.getMessage(), false);
            return null;
        } catch (Exception e) {
            handleException(e, "post instance");
            return null;
        }
    }

    /*
     * (non-Javadoc)
     *
     * @see de.fh_zwickau.informatik.sensor.ZWayApiBase#deleteInstance(java.lang.Integer)
     */
    @Override
    public synchronized Boolean deleteInstance(Integer id) {
        if (checkLogin()) {
            try {
                startHttpClient();

                Request request = mHttpClient.newRequest(getZAutomationTopLevelUrl() + "/" + PATH_INSTANCES + "/" + id)
                        .method(HttpMethod.DELETE).header(HttpHeader.ACCEPT, "application/json")
                        .header(HttpHeader.CONTENT_TYPE, "application/json")
                        .cookie(new HttpCookie("ZWAYSession", mZWaySessionId));

                if (mUseRemoteService) {
                    request.cookie(new HttpCookie("ZBW_SESSID", mZWayRemoteSessionId));
                }

                ContentResponse response = request.send();

                // Check HTTP status code
                int statusCode = response.getStatus();
                if (statusCode != HttpStatus.NO_CONTENT_204) {
                    // Authentication error - retry login and operation
                    if (statusCode == HttpStatus.UNAUTHORIZED_401) {
                        if (getLogin() == null) {
                            mCaller.authenticationError();
                        } else {
                            return deleteInstance(id);
                        }
                    } else {
                        processResponseStatus(statusCode);
                    }
                } else {
                    return true;
                }
            } catch (Exception e) {
                if (e.getCause() instanceof HttpResponseException) {
                    int statusCode = ((HttpResponseException) e.getCause()).getResponse().getStatus();
                    // Authentication error - retry login and operation
                    if (statusCode == HttpStatus.UNAUTHORIZED_401) {
                        if (getLogin() == null) {
                            mCaller.authenticationError();
                        } else {
                            return deleteInstance(id);
                        }
                    }
                } else {
                    handleException(e, "delete instance");
                }
            } finally {
                stopHttpClient();
            }
        } // no else ... checkLogin() method will invoke the appropriate callback method

        return null;
    }

    /*
     * (non-Javadoc)
     *
     * @see de.fh_zwickau.informatik.sensor.ZWayApiBase#deleteInstance(java.lang.Integer,
     * de.fh_zwickau.informatik.sensor.IZWayCallback)
     */
    @Override
    public void deleteInstance(final Integer id, final IZWayCallback<Boolean> callback) {
        if (checkLogin()) {
            try {
                startHttpClient();

                Request request = mHttpClient.newRequest(getZAutomationTopLevelUrl() + "/" + PATH_INSTANCES + "/" + id)
                        .method(HttpMethod.DELETE).header(HttpHeader.ACCEPT, "application/json")
                        .header(HttpHeader.CONTENT_TYPE, "application/json")
                        .cookie(new HttpCookie("ZWAYSession", mZWaySessionId))
                        .onRequestFailure(new ZWayFailureListener());

                if (mUseRemoteService) {
                    request.cookie(new HttpCookie("ZBW_SESSID", mZWayRemoteSessionId));
                }

                request.send(new BufferingResponseListener() {
                    @Override
                    public void onComplete(Result result) {
                        int statusCode = result.getResponse().getStatus();
                        if (statusCode != HttpStatus.NO_CONTENT_204) {
                            if (statusCode == HttpStatus.UNAUTHORIZED_401) {
                                if (getLogin() == null) {
                                    mCaller.authenticationError();
                                } else {
                                    deleteInstance(id, callback);
                                }
                            } else {
                                processResponseStatus(statusCode);
                            }
                        } else {
                            callback.onSuccess(true);
                        }
                    }
                });
            } catch (Exception e) {
                if (e.getCause() instanceof HttpResponseException) {
                    int statusCode = ((HttpResponseException) e.getCause()).getResponse().getStatus();
                    // Authentication error - retry login and operation
                    if (statusCode == HttpStatus.UNAUTHORIZED_401) {
                        if (getLogin() == null) {
                            mCaller.authenticationError();
                        } else {
                            deleteInstance(id, callback);
                        }
                    }
                } else {
                    handleException(e, "delete instance");
                }
            } finally {
                // do not stop http client for asynchronous call
            }
        } // no else ... checkLogin() method will invoke the appropriate callback method
    }

    /*
     * (non-Javadoc)
     *
     * @see de.fh_zwickau.informatik.sensor.ZWayApiBase#getDevices()
     */
    @Override
    public synchronized DeviceList getDevices() {
        if (checkLogin()) {
            try {
                startHttpClient();

                Request request = mHttpClient.newRequest(getZAutomationTopLevelUrl() + "/" + PATH_DEVICES)
                        .method(HttpMethod.GET).header(HttpHeader.ACCEPT, "application/json")
                        .header(HttpHeader.CONTENT_TYPE, "application/json")
                        .cookie(new HttpCookie("ZWAYSession", mZWaySessionId));

                if (mUseRemoteService) {
                    request.cookie(new HttpCookie("ZBW_SESSID", mZWayRemoteSessionId));
                }

                ContentResponse response = request.send();

                // Check HTTP status code
                int statusCode = response.getStatus();
                if (statusCode != HttpStatus.OK_200) {
                    // Authentication error - retry login and operation
                    if (statusCode == HttpStatus.UNAUTHORIZED_401) {
                        if (getLogin() == null) {
                            mCaller.authenticationError();
                        } else {
                            return getDevices();
                        }
                    } else {
                        processResponseStatus(statusCode);
                    }
                } else {
                    return parseGetDevices(response.getContentAsString());
                }
            } catch (Exception e) {
                if (e.getCause() instanceof HttpResponseException) {
                    int statusCode = ((HttpResponseException) e.getCause()).getResponse().getStatus();
                    // Authentication error - retry login and operation
                    if (statusCode == HttpStatus.UNAUTHORIZED_401) {
                        if (getLogin() == null) {
                            mCaller.authenticationError();
                        } else {
                            return getDevices();
                        }
                    }
                } else {
                    handleException(e, "get devices");
                }
            } finally {
                stopHttpClient();
            }
        } // no else ... checkLogin() method will invoke the appropriate callback method

        return null;
    }

    /*
     * (non-Javadoc)
     *
     * @see de.fh_zwickau.informatik.sensor.ZWayApiBase#getDevices(de.fh_zwickau.informatik.sensor.IZWayCallback)
     */
    @Override
    public void getDevices(final IZWayCallback<DeviceList> callback) {
        if (checkLogin()) {
            try {
                startHttpClient();

                Request request = mHttpClient.newRequest(getZAutomationTopLevelUrl() + "/" + PATH_DEVICES)
                        .method(HttpMethod.GET).header(HttpHeader.ACCEPT, "application/json")
                        .header(HttpHeader.CONTENT_TYPE, "application/json")
                        .cookie(new HttpCookie("ZWAYSession", mZWaySessionId))
                        .onRequestFailure(new ZWayFailureListener());

                if (mUseRemoteService) {
                    request.cookie(new HttpCookie("ZBW_SESSID", mZWayRemoteSessionId));
                }

                request.send(new BufferingResponseListener() {
                    @Override
                    public void onComplete(Result result) {
                        int statusCode = result.getResponse().getStatus();
                        if (statusCode != HttpStatus.OK_200) {
                            if (statusCode == HttpStatus.UNAUTHORIZED_401) {
                                if (getLogin() == null) {
                                    mCaller.authenticationError();
                                } else {
                                    getDevices(callback);
                                }
                            } else {
                                processResponseStatus(statusCode);
                            }
                        } else {
                            callback.onSuccess(parseGetDevices(getContentAsString()));
                        }
                    }
                });
            } catch (Exception e) {
                if (e.getCause() instanceof HttpResponseException) {
                    int statusCode = ((HttpResponseException) e.getCause()).getResponse().getStatus();
                    // Authentication error - retry login and operation
                    if (statusCode == HttpStatus.UNAUTHORIZED_401) {
                        if (getLogin() == null) {
                            mCaller.authenticationError();
                        } else {
                            getDevices(callback);
                        }
                    }
                } else {
                    handleException(e, "get devices");
                }
            } finally {
                // do not stop http client for asynchronous call
            }
        } // no else ... checkLogin() method will invoke the appropriate callback method
    }

    private synchronized DeviceList parseGetDevices(String data) {
        // Request performed successfully: load response body
        // Expected response format: { "data": { "devices": [...], ... }, ... }
        try {
            Gson gson = new Gson();
            // Response -> String -> Json -> extract data field
            JsonObject responseDataAsJson = gson.fromJson(data, JsonObject.class).get("data").getAsJsonObject();
            // extract device array field
            JsonArray devicesAsJson = responseDataAsJson.get("devices").getAsJsonArray();

            return new DeviceListDeserializer().deserializeDeviceList(devicesAsJson, this);
        } catch (JsonParseException e) {
            logger.warn("Unexpected response format: {}", e.getMessage());
            mCaller.responseFormatError("Unexpected response format: " + e.getMessage(), false);
            return null;
        }
    }

    /*
     * (non-Javadoc)
     *
     * @see de.fh_zwickau.informatik.sensor.ZWayApiBase#getDevice(java.lang.String)
     */
    @Override
    public synchronized Device getDevice(String deviceId) {
        if (checkLogin()) {
            try {
                startHttpClient();

                Request request = mHttpClient
                        .newRequest(getZAutomationTopLevelUrl() + "/" + PATH_DEVICES + "/" + deviceId)
                        .method(HttpMethod.GET).header(HttpHeader.ACCEPT, "application/json")
                        .header(HttpHeader.CONTENT_TYPE, "application/json")
                        .cookie(new HttpCookie("ZWAYSession", mZWaySessionId));

                if (mUseRemoteService) {
                    request.cookie(new HttpCookie("ZBW_SESSID", mZWayRemoteSessionId));
                }

                ContentResponse response = request.send();

                // Check HTTP status code
                int statusCode = response.getStatus();
                if (statusCode != HttpStatus.OK_200) {
                    // Authentication error - retry login and operation
                    if (statusCode == HttpStatus.UNAUTHORIZED_401) {
                        if (getLogin() == null) {
                            mCaller.authenticationError();
                        } else {
                            return getDevice(deviceId);
                        }
                    } else {
                        processResponseStatus(statusCode);
                    }
                } else {
                    return parseGetDevice(response.getContentAsString());
                }
            } catch (Exception e) {
                if (e.getCause() instanceof HttpResponseException) {
                    int statusCode = ((HttpResponseException) e.getCause()).getResponse().getStatus();
                    // Authentication error - retry login and operation
                    if (statusCode == HttpStatus.UNAUTHORIZED_401) {
                        if (getLogin() == null) {
                            mCaller.authenticationError();
                        } else {
                            return getDevice(deviceId);
                        }
                    }
                } else {
                    handleException(e, "get device");
                }
            } finally {
                stopHttpClient();
            }
        } // no else ... checkLogin() method will invoke the appropriate callback method

        return null;
    }

    /*
     * (non-Javadoc)
     *
     * @see de.fh_zwickau.informatik.sensor.ZWayApiBase#getDevice(java.lang.String,
     * de.fh_zwickau.informatik.sensor.IZWayCallback)
     */
    @Override
    public void getDevice(final String deviceId, final IZWayCallback<Device> callback) {
        if (checkLogin()) {
            try {
                startHttpClient();

                Request request = mHttpClient
                        .newRequest(getZAutomationTopLevelUrl() + "/" + PATH_DEVICES + "/" + deviceId)
                        .method(HttpMethod.GET).header(HttpHeader.ACCEPT, "application/json")
                        .header(HttpHeader.CONTENT_TYPE, "application/json")
                        .cookie(new HttpCookie("ZWAYSession", mZWaySessionId))
                        .onRequestFailure(new ZWayFailureListener());

                if (mUseRemoteService) {
                    request.cookie(new HttpCookie("ZBW_SESSID", mZWayRemoteSessionId));
                }

                request.send(new BufferingResponseListener() {
                    @Override
                    public void onComplete(Result result) {
                        int statusCode = result.getResponse().getStatus();
                        if (statusCode != HttpStatus.OK_200) {
                            if (statusCode == HttpStatus.UNAUTHORIZED_401) {
                                if (getLogin() == null) {
                                    mCaller.authenticationError();
                                } else {
                                    getDevice(deviceId, callback);
                                }
                            } else {
                                processResponseStatus(statusCode);
                            }
                        } else {
                            callback.onSuccess(parseGetDevice(getContentAsString()));
                        }
                    }
                });
            } catch (Exception e) {
                if (e.getCause() instanceof HttpResponseException) {
                    int statusCode = ((HttpResponseException) e.getCause()).getResponse().getStatus();
                    // Authentication error - retry login and operation
                    if (statusCode == HttpStatus.UNAUTHORIZED_401) {
                        if (getLogin() == null) {
                            mCaller.authenticationError();
                        } else {
                            getDevice(deviceId, callback);
                        }
                    }
                } else {
                    handleException(e, "get device");
                }
            } finally {
                // do not stop http client for asynchronous call
            }
        } // no else ... checkLogin() method will invoke the appropriate callback method
    }

    private synchronized Device parseGetDevice(String data) {
        // Request performed successfully: load response body
        // Expected response format: { "data": { ... }, ... }
        try {
            Gson gson = new Gson();
            // Response -> String -> Json -> extract data field
            JsonObject deviceAsJson = gson.fromJson(data, JsonObject.class).get("data").getAsJsonObject();

            return new DeviceListDeserializer().deserializeDevice(deviceAsJson, this);
        } catch (JsonParseException e) {
            logger.warn("Unexpected response format: {}", e.getMessage());
            mCaller.responseFormatError("Unexpected response format: " + e.getMessage(), false);
            return null;
        }
    }

    /*
     * (non-Javadoc)
     *
     * @see de.fh_zwickau.informatik.sensor.ZWayApiBase#getDeviceAsJson(java.lang.String)
     */
    @Override
    public String getDeviceAsJson(String deviceId) {
        if (checkLogin()) {
            try {
                startHttpClient();

                Request request = mHttpClient
                        .newRequest(getZAutomationTopLevelUrl() + "/" + PATH_DEVICES + "/" + deviceId)
                        .method(HttpMethod.GET).header(HttpHeader.ACCEPT, "application/json")
                        .header(HttpHeader.CONTENT_TYPE, "application/json")
                        .cookie(new HttpCookie("ZWAYSession", mZWaySessionId));

                if (mUseRemoteService) {
                    request.cookie(new HttpCookie("ZBW_SESSID", mZWayRemoteSessionId));
                }

                ContentResponse response = request.send();

                // Check HTTP status code
                int statusCode = response.getStatus();
                if (statusCode != HttpStatus.OK_200) {
                    // Authentication error - retry login and operation
                    if (statusCode == HttpStatus.UNAUTHORIZED_401) {
                        if (getLogin() == null) {
                            mCaller.authenticationError();
                        } else {
                            return getDeviceAsJson(deviceId);
                        }
                    } else {
                        processResponseStatus(statusCode);
                    }
                } else {
                    return response.getContentAsString();
                }
            } catch (Exception e) {
                if (e.getCause() instanceof HttpResponseException) {
                    int statusCode = ((HttpResponseException) e.getCause()).getResponse().getStatus();
                    // Authentication error - retry login and operation
                    if (statusCode == HttpStatus.UNAUTHORIZED_401) {
                        if (getLogin() == null) {
                            mCaller.authenticationError();
                        } else {
                            return getDeviceAsJson(deviceId);
                        }
                    }
                } else {
                    handleException(e, "get device (json)");
                }
            } finally {
                stopHttpClient();
            }
        } // no else ... checkLogin() method will invoke the appropriate callback method

        return null;
    }

    /*
     * (non-Javadoc)
     *
     * @see de.fh_zwickau.informatik.sensor.ZWayApiBase#getDeviceAsJson(java.lang.String,
     * de.fh_zwickau.informatik.sensor.IZWayCallback)
     */
    @Override
    public void getDeviceAsJson(final String deviceId, final IZWayCallback<String> callback) {
        if (checkLogin()) {
            try {
                startHttpClient();

                Request request = mHttpClient
                        .newRequest(getZAutomationTopLevelUrl() + "/" + PATH_DEVICES + "/" + deviceId)
                        .method(HttpMethod.GET).header(HttpHeader.ACCEPT, "application/json")
                        .header(HttpHeader.CONTENT_TYPE, "application/json")
                        .cookie(new HttpCookie("ZWAYSession", mZWaySessionId))
                        .onRequestFailure(new ZWayFailureListener());

                if (mUseRemoteService) {
                    request.cookie(new HttpCookie("ZBW_SESSID", mZWayRemoteSessionId));
                }

                request.send(new BufferingResponseListener() {
                    @Override
                    public void onComplete(Result result) {
                        int statusCode = result.getResponse().getStatus();
                        if (statusCode != HttpStatus.OK_200) {
                            if (statusCode == HttpStatus.UNAUTHORIZED_401) {
                                if (getLogin() == null) {
                                    mCaller.authenticationError();
                                } else {
                                    getDeviceAsJson(deviceId, callback);
                                }
                            } else {
                                processResponseStatus(statusCode);
                            }
                        } else {
                            callback.onSuccess(getContentAsString());
                        }
                    }
                });
            } catch (Exception e) {
                if (e.getCause() instanceof HttpResponseException) {
                    int statusCode = ((HttpResponseException) e.getCause()).getResponse().getStatus();
                    // Authentication error - retry login and operation
                    if (statusCode == HttpStatus.UNAUTHORIZED_401) {
                        if (getLogin() == null) {
                            mCaller.authenticationError();
                        } else {
                            getDeviceAsJson(deviceId, callback);
                        }
                    }
                } else {
                    handleException(e, "get device");
                }
            } finally {
                // do not stop http client for asynchronous call
            }
        } // no else ... checkLogin() method will invoke the appropriate callback method
    }

    /*
     * (non-Javadoc)
     *
     * @see de.fh_zwickau.informatik.sensor.ZWayApiBase#putDevice(de.fh_zwickau.informatik.sensor.model.devices.Device)
     */
    @Override
    public synchronized Device putDevice(Device device) {
        if (checkLogin()) {
            try {
                startHttpClient();

                Request request = mHttpClient
                        .newRequest(getZAutomationTopLevelUrl() + "/" + PATH_DEVICES + "/" + device.getDeviceId())
                        .method(HttpMethod.PUT).header(HttpHeader.ACCEPT, "application/json")
                        .header(HttpHeader.CONTENT_TYPE, "application/json")
                        .cookie(new HttpCookie("ZWAYSession", mZWaySessionId))
                        .content(new StringContentProvider(new Gson().toJson(device)), "application/json");

                if (mUseRemoteService) {
                    request.cookie(new HttpCookie("ZBW_SESSID", mZWayRemoteSessionId));
                }

                ContentResponse response = request.send();

                // Check HTTP status code
                int statusCode = response.getStatus();
                if (statusCode != HttpStatus.OK_200) {
                    // Authentication error - retry login and operation
                    if (statusCode == HttpStatus.UNAUTHORIZED_401) {
                        if (getLogin() == null) {
                            mCaller.authenticationError();
                        } else {
                            return putDevice(device);
                        }
                    } else {
                        processResponseStatus(statusCode);
                    }
                } else {
                    return parsePutDevice(response.getContentAsString());
                }
            } catch (Exception e) {
                if (e.getCause() instanceof HttpResponseException) {
                    int statusCode = ((HttpResponseException) e.getCause()).getResponse().getStatus();
                    // Authentication error - retry login and operation
                    if (statusCode == HttpStatus.UNAUTHORIZED_401) {
                        if (getLogin() == null) {
                            mCaller.authenticationError();
                        } else {
                            return putDevice(device);
                        }
                    }
                } else {
                    handleException(e, "put device");
                }
            } finally {
                stopHttpClient();
            }
        } // no else ... checkLogin() method will invoke the appropriate callback method

        return null;
    }

    /*
     * (non-Javadoc)
     *
     * @see de.fh_zwickau.informatik.sensor.ZWayApiBase#putDevice(de.fh_zwickau.informatik.sensor.model.devices.Device,
     * de.fh_zwickau.informatik.sensor.IZWayCallback)
     */
    @Override
    public void putDevice(final Device device, final IZWayCallback<Device> callback) {
        if (checkLogin()) {
            try {
                startHttpClient();

                Request request = mHttpClient
                        .newRequest(getZAutomationTopLevelUrl() + "/" + PATH_INSTANCES + "/" + device.getDeviceId())
                        .method(HttpMethod.PUT).header(HttpHeader.ACCEPT, "application/json")
                        .header(HttpHeader.CONTENT_TYPE, "application/json")
                        .cookie(new HttpCookie("ZWAYSession", mZWaySessionId))
                        .content(new StringContentProvider(new Gson().toJson(device)), "application/json")
                        .onRequestFailure(new ZWayFailureListener());

                if (mUseRemoteService) {
                    request.cookie(new HttpCookie("ZBW_SESSID", mZWayRemoteSessionId));
                }

                request.send(new BufferingResponseListener() {
                    @Override
                    public void onComplete(Result result) {
                        int statusCode = result.getResponse().getStatus();
                        if (statusCode != HttpStatus.OK_200) {
                            if (statusCode == HttpStatus.UNAUTHORIZED_401) {
                                if (getLogin() == null) {
                                    mCaller.authenticationError();
                                } else {
                                    putDevice(device, callback);
                                }
                            } else {
                                processResponseStatus(statusCode);
                            }
                        } else {
                            callback.onSuccess(parsePutDevice(getContentAsString()));
                        }
                    }
                });
            } catch (Exception e) {
                if (e.getCause() instanceof HttpResponseException) {
                    int statusCode = ((HttpResponseException) e.getCause()).getResponse().getStatus();
                    // Authentication error - retry login and operation
                    if (statusCode == HttpStatus.UNAUTHORIZED_401) {
                        if (getLogin() == null) {
                            mCaller.authenticationError();
                        } else {
                            putDevice(device, callback);
                        }
                    }
                } else {
                    handleException(e, "put device");
                }
            } finally {
                // do not stop http client for asynchronous call
            }
        } // no else ... checkLogin() method will invoke the appropriate callback method
    }

    private synchronized Device parsePutDevice(String data) {
        // Request performed successfully: load response body
        // Expected response format: { "data": { ... }, "code": 200, "message":
        // "200 - OK", ... }
        try {
            Gson gson = new Gson();
            // Response -> String -> Json -> extract data field
            JsonObject deviceAsJson = gson.fromJson(data, JsonObject.class).get("data").getAsJsonObject();

            return new DeviceListDeserializer().deserializeDevice(deviceAsJson, this);
        } catch (JsonParseException e) {
            logger.warn("Unexpected response format: {}", e.getMessage());
            mCaller.responseFormatError("Unexpected response format: " + e.getMessage(), false);
            return null;
        }
    }

    /*
     * (non-Javadoc)
     *
     * @see de.fh_zwickau.informatik.sensor.ZWayApiBase#getDeviceCommand(de.fh_zwickau.informatik.sensor.model.devices.
     * DeviceCommand)
     */
    @Override
    public synchronized String getDeviceCommand(DeviceCommand command) {
        if (checkLogin()) {
            try {
                startHttpClient();

                String path = buildGetDeviceCommandPath(command);
                if (path == null) {
                    return "Device command parameter invalid";
                }

                Request request = mHttpClient.newRequest(getZAutomationTopLevelUrl() + "/" + path)
                        .method(HttpMethod.GET).header(HttpHeader.ACCEPT, "application/json")
                        .header(HttpHeader.CONTENT_TYPE, "application/json")
                        .cookie(new HttpCookie("ZWAYSession", mZWaySessionId));

                if (mUseRemoteService) {
                    request.cookie(new HttpCookie("ZBW_SESSID", mZWayRemoteSessionId));
                }

                ContentResponse response = request.send();

                // Check HTTP status code
                int statusCode = response.getStatus();
                if (statusCode != HttpStatus.OK_200) {
                    // Authentication error - retry login and operation
                    if (statusCode == HttpStatus.UNAUTHORIZED_401) {
                        if (getLogin() == null) {
                            mCaller.authenticationError();
                        } else {
                            return getDeviceCommand(command);
                        }
                    } else {
                        processResponseStatus(statusCode);
                    }
                } else {
                    return "Command: " + command.getCommand() + " successfully performed on device: "
                            + command.getDeviceId() + " (" + parseGetDeviceCommand(response.getContentAsString()) + ")";
                }
            } catch (Exception e) {
                if (e.getCause() instanceof HttpResponseException) {
                    int statusCode = ((HttpResponseException) e.getCause()).getResponse().getStatus();
                    // Authentication error - retry login and operation
                    if (statusCode == HttpStatus.UNAUTHORIZED_401) {
                        if (getLogin() == null) {
                            mCaller.authenticationError();
                        } else {
                            return getDeviceCommand(command);
                        }
                    }
                } else {
                    handleException(e, "get device command");
                }
            } finally {
                stopHttpClient();
            }
        } // no else ... checkLogin() method will invoke the appropriate callback method

        return null;
    }

    /*
     * (non-Javadoc)
     *
     * @see de.fh_zwickau.informatik.sensor.ZWayApiBase#getDeviceCommand(de.fh_zwickau.informatik.sensor.model.devices.
     * DeviceCommand, de.fh_zwickau.informatik.sensor.IZWayCallback)
     */
    @Override
    public void getDeviceCommand(final DeviceCommand command, final IZWayCallback<String> callback) {
        if (checkLogin()) {
            try {
                startHttpClient();

                String path = buildGetDeviceCommandPath(command);
                if (path == null) {
                    mCaller.apiError("Device command parameter invalid", false);
                    return;
                }

                Request request = mHttpClient.newRequest(getZAutomationTopLevelUrl() + "/" + path)
                        .method(HttpMethod.GET).header(HttpHeader.ACCEPT, "application/json")
                        .header(HttpHeader.CONTENT_TYPE, "application/json")
                        .cookie(new HttpCookie("ZWAYSession", mZWaySessionId))
                        .onRequestFailure(new ZWayFailureListener());

                if (mUseRemoteService) {
                    request.cookie(new HttpCookie("ZBW_SESSID", mZWayRemoteSessionId));
                }

                request.send(new BufferingResponseListener() {
                    @Override
                    public void onComplete(Result result) {
                        int statusCode = result.getResponse().getStatus();
                        if (statusCode != HttpStatus.OK_200) {
                            if (statusCode == HttpStatus.UNAUTHORIZED_401) {
                                if (getLogin() == null) {
                                    mCaller.authenticationError();
                                } else {
                                    getDeviceCommand(command, callback);
                                }
                            } else {
                                processResponseStatus(statusCode);
                            }
                        } else {
                            callback.onSuccess("Command: " + command.getCommand()
                                    + " successfully performed on device: " + command.getDeviceId() + " ("
                                    + parseGetDeviceCommand(getContentAsString()) + ")");
                        }
                    }
                });
            } catch (Exception e) {
                if (e.getCause() instanceof HttpResponseException) {
                    int statusCode = ((HttpResponseException) e.getCause()).getResponse().getStatus();
                    // Authentication error - retry login and operation
                    if (statusCode == HttpStatus.UNAUTHORIZED_401) {
                        if (getLogin() == null) {
                            mCaller.authenticationError();
                        } else {
                            getDeviceCommand(command, callback);
                        }
                    }
                } else {
                    handleException(e, "get device command");
                }
            } finally {
                // do not stop http client for asynchronous call
            }
        } // no else ... checkLogin() method will invoke the appropriate callback method
    }

    private synchronized String buildGetDeviceCommandPath(DeviceCommand command) {
        String path = StringUtils.replace(PATH_DEVICES_COMMAND, "{deviceId}", command.getDeviceId());
        path = StringUtils.replace(path, "{command}", command.getCommand());

        if (command.getParams() != null) {
            path += "?";

            Integer index = 0;
            for (Entry<String, String> entry : command.getParams().entrySet()) {
                if (index > 0) {
                    path += "&";
                }
                try {
                    path += URLEncoder.encode(entry.getKey(), "UTF-8") + "="
                            + URLEncoder.encode(entry.getValue(), "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    logger.warn("Device command parameter invalid: {}", e.getMessage());
                    mCaller.apiError("Device command parameter invalid: " + e.getMessage(), false);
                    return null;
                }
                index++;
            }
        }

        return path;
    }

    private synchronized String parseGetDeviceCommand(String data) {
        // Request performed successfully: load response body
        // Expected response format: { "data": { "code": ..., "message": "..." }, "code": 200, "message":
        // "200 - OK", ... }
        try {
            Gson gson = new Gson();
            // Response -> String -> Json -> extract data field
            JsonObject responseAsJson = gson.fromJson(data, JsonObject.class);
            String dataMessage = responseAsJson.get("message").getAsString();

            return dataMessage;
        } catch (JsonParseException e) {
            logger.warn("Unexpected response format: {}", e.getMessage());
            mCaller.responseFormatError("Unexpected response format: " + e.getMessage(), false);
            return null;
        }
    }

    /*
     * (non-Javadoc)
     *
     * @see de.fh_zwickau.informatik.sensor.ZWayApiBase#getIcons()
     */
    @Override
    public synchronized IconList getIcons() {
        if (checkLogin()) {
            try {
                startHttpClient();

                Request request = mHttpClient.newRequest(getZAutomationTopLevelUrl() + "/" + PATH_ICONS)
                        .method(HttpMethod.GET).header(HttpHeader.ACCEPT, "application/json")
                        .header(HttpHeader.CONTENT_TYPE, "application/json")
                        .cookie(new HttpCookie("ZWAYSession", mZWaySessionId));

                if (mUseRemoteService) {
                    request.cookie(new HttpCookie("ZBW_SESSID", mZWayRemoteSessionId));
                }

                ContentResponse response = request.send();

                // Check HTTP status code
                int statusCode = response.getStatus();
                if (statusCode != HttpStatus.OK_200) {
                    // Authentication error - retry login and operation
                    if (statusCode == HttpStatus.UNAUTHORIZED_401) {
                        if (getLogin() == null) {
                            mCaller.authenticationError();
                        } else {
                            return getIcons();
                        }
                    } else {
                        processResponseStatus(statusCode);
                    }
                } else {
                    return parseGetIcons(response.getContentAsString());
                }
            } catch (Exception e) {
                if (e.getCause() instanceof HttpResponseException) {
                    int statusCode = ((HttpResponseException) e.getCause()).getResponse().getStatus();
                    // Authentication error - retry login and operation
                    if (statusCode == HttpStatus.UNAUTHORIZED_401) {
                        if (getLogin() == null) {
                            mCaller.authenticationError();
                        } else {
                            return getIcons();
                        }
                    }
                } else {
                    handleException(e, "get icons");
                }
            } finally {
                stopHttpClient();
            }
        } // no else ... checkLogin() method will invoke the appropriate callback method

        return null;
    }

    /*
     * (non-Javadoc)
     *
     * @see de.fh_zwickau.informatik.sensor.ZWayApiBase#getIcons(de.fh_zwickau.informatik.sensor.IZWayCallback)
     */
    @Override
    public void getIcons(final IZWayCallback<IconList> callback) {
        if (checkLogin()) {
            try {
                startHttpClient();

                Request request = mHttpClient.newRequest(getZAutomationTopLevelUrl() + "/" + PATH_ICONS)
                        .method(HttpMethod.GET).header(HttpHeader.ACCEPT, "application/json")
                        .header(HttpHeader.CONTENT_TYPE, "application/json")
                        .cookie(new HttpCookie("ZWAYSession", mZWaySessionId))
                        .onRequestFailure(new ZWayFailureListener());

                if (mUseRemoteService) {
                    request.cookie(new HttpCookie("ZBW_SESSID", mZWayRemoteSessionId));
                }

                request.send(new BufferingResponseListener() {
                    @Override
                    public void onComplete(Result result) {
                        int statusCode = result.getResponse().getStatus();
                        if (statusCode != HttpStatus.OK_200) {
                            if (statusCode == HttpStatus.UNAUTHORIZED_401) {
                                if (getLogin() == null) {
                                    mCaller.authenticationError();
                                } else {
                                    getIcons(callback);
                                }
                            } else {
                                processResponseStatus(statusCode);
                            }
                        } else {
                            callback.onSuccess(parseGetIcons(getContentAsString()));
                        }
                    }
                });
            } catch (Exception e) {
                if (e.getCause() instanceof HttpResponseException) {
                    int statusCode = ((HttpResponseException) e.getCause()).getResponse().getStatus();
                    // Authentication error - retry login and operation
                    if (statusCode == HttpStatus.UNAUTHORIZED_401) {
                        if (getLogin() == null) {
                            mCaller.authenticationError();
                        } else {
                            getIcons(callback);
                        }
                    }
                } else {
                    handleException(e, "get icons");
                }
            } finally {
                // do not stop http client for asynchronous call
            }
        } // no else ... checkLogin() method will invoke the appropriate callback method
    }

    private synchronized IconList parseGetIcons(String data) {
        // Request performed successfully: load response body
        // Expected response format: { "data": [...] }, ... }
        try {
            Gson gson = new Gson();
            // Response -> String -> Json -> extract data field
            JsonArray iconsAsJson = gson.fromJson(data, JsonObject.class).get("data").getAsJsonArray();

            return new IconListDeserializer().deserializeIconList(iconsAsJson);
        } catch (JsonParseException e) {
            logger.warn("Unexpected response format: {}", e.getMessage());
            mCaller.responseFormatError("Unexpected response format: " + e.getMessage(), false);
            return null;
        }
    }

    @Override
    public String postIcon(File image) {
        if (checkLogin()) {
            try {
                startHttpClient();

                MultiPartContentProvider multiPart = new MultiPartContentProvider();
                multiPart.addFilePart("file", image.getName(), new PathContentProvider(Paths.get(image.getPath())),
                        null);
                multiPart.close();

                Request request = mHttpClient.newRequest(getZAutomationTopLevelUrl() + "/" + PATH_ICONS + "/upload")
                        .method(HttpMethod.POST).header(HttpHeader.ACCEPT, "application/json").content(multiPart)
                        .cookie(new HttpCookie("ZWAYSession", mZWaySessionId));

                if (mUseRemoteService) {
                    request.cookie(new HttpCookie("ZBW_SESSID", mZWayRemoteSessionId));
                }

                ContentResponse response = request.send();

                // Check HTTP status code
                int statusCode = response.getStatus();
                if (statusCode != HttpStatus.OK_200) {
                    // Authentication error - retry login and operation
                    if (statusCode == HttpStatus.UNAUTHORIZED_401) {
                        if (getLogin() == null) {
                            mCaller.authenticationError();
                        } else {
                            return postIcon(image);
                        }
                    } else {
                        processResponseStatus(statusCode);
                    }
                } else {
                    return "Icon upload successfully performed";
                }
            } catch (Exception e) {
                if (e.getCause() instanceof HttpResponseException) {
                    int statusCode = ((HttpResponseException) e.getCause()).getResponse().getStatus();
                    // Authentication error - retry login and operation
                    if (statusCode == HttpStatus.UNAUTHORIZED_401) {
                        if (getLogin() == null) {
                            mCaller.authenticationError();
                        } else {
                            return postIcon(image);
                        }
                    }
                } else {
                    handleException(e, "get post icon");
                }
            } finally {
                stopHttpClient();
            }
        } // no else ... checkLogin() method will invoke the appropriate callback method

        return null;
    }

    /**********************
     ****** ZWaveAPI ******
     **********************/

    /*
     * (non-Javadoc)
     *
     * @see de.fh_zwickau.informatik.sensor.ZWayApiBase#getZWaveDevice(int)
     */
    @Override
    public synchronized ZWaveDevice getZWaveDevice(int nodeId) {
        if (checkLogin()) {
            try {
                startHttpClient();

                String path = URLEncoder
                        .encode(StringUtils.replace(ZWAVE_PATH_DEVICES, "{nodeId}", String.valueOf(nodeId)), "UTF-8");

                Request request = mHttpClient.newRequest(getZWaveTopLevelUrl() + "/" + path).method(HttpMethod.GET)
                        .header(HttpHeader.ACCEPT, "application/json")
                        .header(HttpHeader.CONTENT_TYPE, "application/json")
                        .cookie(new HttpCookie("ZWAYSession", mZWaySessionId));

                if (mUseRemoteService) {
                    request.cookie(new HttpCookie("ZBW_SESSID", mZWayRemoteSessionId));
                }

                ContentResponse response = request.send();

                // Check HTTP status code
                int statusCode = response.getStatus();
                if (statusCode != HttpStatus.OK_200) {
                    // Authentication error - retry login and operation
                    if (statusCode == HttpStatus.UNAUTHORIZED_401) {
                        if (getLogin() == null) {
                            mCaller.authenticationError();
                        } else {
                            return getZWaveDevice(nodeId);
                        }
                    } else {
                        processResponseStatus(statusCode);
                    }
                } else {
                    return parseGetZWaveDevice(response.getContentAsString());
                }
            } catch (Exception e) {
                if (e.getCause() instanceof HttpResponseException) {
                    int statusCode = ((HttpResponseException) e.getCause()).getResponse().getStatus();
                    // Authentication error - retry login and operation
                    if (statusCode == HttpStatus.UNAUTHORIZED_401) {
                        if (getLogin() == null) {
                            mCaller.authenticationError();
                        } else {
                            return getZWaveDevice(nodeId);
                        }
                    }
                } else {
                    handleException(e, "get zwave device");
                }
            } finally {
                stopHttpClient();
            }
        } // no else ... checkLogin() method will invoke the appropriate callback method

        return null;
    }

    /*
     * (non-Javadoc)
     *
     * @see de.fh_zwickau.informatik.sensor.ZWayApiBase#getZWaveDevice(int,
     * de.fh_zwickau.informatik.sensor.IZWayCallback)
     */
    @Override
    public void getZWaveDevice(final int nodeId, final IZWayCallback<ZWaveDevice> callback) {
        if (checkLogin()) {
            try {
                startHttpClient();

                String path = URLEncoder
                        .encode(StringUtils.replace(ZWAVE_PATH_DEVICES, "{nodeId}", String.valueOf(nodeId)), "UTF-8");

                Request request = mHttpClient.newRequest(getZWaveTopLevelUrl() + "/" + path).method(HttpMethod.GET)
                        .header(HttpHeader.ACCEPT, "application/json")
                        .header(HttpHeader.CONTENT_TYPE, "application/json")
                        .cookie(new HttpCookie("ZWAYSession", mZWaySessionId))
                        .onRequestFailure(new ZWayFailureListener());

                if (mUseRemoteService) {
                    request.cookie(new HttpCookie("ZBW_SESSID", mZWayRemoteSessionId));
                }

                request.send(new BufferingResponseListener() {
                    @Override
                    public void onComplete(Result result) {
                        int statusCode = result.getResponse().getStatus();
                        if (statusCode != HttpStatus.OK_200) {
                            if (statusCode == HttpStatus.UNAUTHORIZED_401) {
                                if (getLogin() == null) {
                                    mCaller.authenticationError();
                                } else {
                                    getZWaveDevice(nodeId, callback);
                                }
                            } else {
                                processResponseStatus(statusCode);
                            }
                        } else {
                            callback.onSuccess(parseGetZWaveDevice(getContentAsString()));
                        }
                    }
                });
            } catch (Exception e) {
                if (e.getCause() instanceof HttpResponseException) {
                    int statusCode = ((HttpResponseException) e.getCause()).getResponse().getStatus();
                    // Authentication error - retry login and operation
                    if (statusCode == HttpStatus.UNAUTHORIZED_401) {
                        if (getLogin() == null) {
                            mCaller.authenticationError();
                        } else {
                            getZWaveDevice(nodeId, callback);
                        }
                    }
                } else {
                    handleException(e, "get zwave device");
                }
            } finally {
                // do not stop http client for asynchronous call
            }
        } // no else ... checkLogin() method will invoke the appropriate callback method
    }

    private synchronized ZWaveDevice parseGetZWaveDevice(String data) {
        // Request performed successfully: load response body
        // Expected response format: { "data": { "givenName": { "value": *** }, ... }, ... }
        try {
            Gson gson = new Gson();

            return gson.fromJson(data, ZWaveDevice.class);
        } catch (JsonParseException e) {
            logger.warn("Unexpected response format: {}", e.getMessage());
            mCaller.responseFormatError("Unexpected response format: " + e.getMessage(), false);
            return null;
        }
    }

    /*
     * (non-Javadoc)
     *
     * @see de.fh_zwickau.informatik.sensor.ZWayApiBase#getZWaveController()
     */
    @Override
    public synchronized ZWaveController getZWaveController() {
        if (checkLogin()) {
            try {
                startHttpClient();

                Request request = mHttpClient.newRequest(getZWaveTopLevelUrl() + "/" + ZWAVE_PATH_CONTROLLER)
                        .method(HttpMethod.GET).header(HttpHeader.ACCEPT, "application/json")
                        .header(HttpHeader.CONTENT_TYPE, "application/json")
                        .cookie(new HttpCookie("ZWAYSession", mZWaySessionId));

                if (mUseRemoteService) {
                    request.cookie(new HttpCookie("ZBW_SESSID", mZWayRemoteSessionId));
                }

                ContentResponse response = request.send();

                // Check HTTP status code
                int statusCode = response.getStatus();
                if (statusCode != HttpStatus.OK_200) {
                    // Authentication error - retry login and operation
                    if (statusCode == HttpStatus.UNAUTHORIZED_401) {
                        if (getLogin() == null) {
                            mCaller.authenticationError();
                        } else {
                            return getZWaveController();
                        }
                    } else {
                        processResponseStatus(statusCode);
                    }
                } else {
                    return parseGetZWaveController(response.getContentAsString());
                }
            } catch (Exception e) {
                if (e.getCause() instanceof HttpResponseException) {
                    int statusCode = ((HttpResponseException) e.getCause()).getResponse().getStatus();
                    // Authentication error - retry login and operation
                    if (statusCode == HttpStatus.UNAUTHORIZED_401) {
                        if (getLogin() == null) {
                            mCaller.authenticationError();
                        } else {
                            return getZWaveController();
                        }
                    }
                } else {
                    handleException(e, "get zwave controller");
                }
            } finally {
                stopHttpClient();
            }
        } // no else ... checkLogin() method will invoke the appropriate callback method

        return null;
    }

    /*
     * (non-Javadoc)
     *
     * @see de.fh_zwickau.informatik.sensor.ZWayApiBase#getZWaveController(
     * de.fh_zwickau.informatik.sensor.IZWayCallback)
     */
    @Override
    public void getZWaveController(final IZWayCallback<ZWaveController> callback) {
        if (checkLogin()) {
            try {
                startHttpClient();

                Request request = mHttpClient.newRequest(getZWaveTopLevelUrl() + "/" + ZWAVE_PATH_CONTROLLER)
                        .method(HttpMethod.GET).header(HttpHeader.ACCEPT, "application/json")
                        .header(HttpHeader.CONTENT_TYPE, "application/json")
                        .cookie(new HttpCookie("ZWAYSession", mZWaySessionId))
                        .onRequestFailure(new ZWayFailureListener());

                if (mUseRemoteService) {
                    request.cookie(new HttpCookie("ZBW_SESSID", mZWayRemoteSessionId));
                }

                request.send(new BufferingResponseListener() {
                    @Override
                    public void onComplete(Result result) {
                        int statusCode = result.getResponse().getStatus();
                        if (statusCode != HttpStatus.OK_200) {
                            if (statusCode == HttpStatus.UNAUTHORIZED_401) {
                                if (getLogin() == null) {
                                    mCaller.authenticationError();
                                } else {
                                    getZWaveController(callback);
                                }
                            } else {
                                processResponseStatus(statusCode);
                            }
                        } else {
                            callback.onSuccess(parseGetZWaveController(getContentAsString()));
                        }
                    }
                });
            } catch (Exception e) {
                if (e.getCause() instanceof HttpResponseException) {
                    int statusCode = ((HttpResponseException) e.getCause()).getResponse().getStatus();
                    // Authentication error - retry login and operation
                    if (statusCode == HttpStatus.UNAUTHORIZED_401) {
                        if (getLogin() == null) {
                            mCaller.authenticationError();
                        } else {
                            getZWaveController(callback);
                        }
                    }
                } else {
                    handleException(e, "get zwave controller");
                }
            } finally {
                // do not stop http client for asynchronous call
            }
        } // no else ... checkLogin() method will invoke the appropriate callback method
    }

    private synchronized ZWaveController parseGetZWaveController(String data) {
        // Request performed successfully: load response body
        // Expected response format: { "data": { "nodeId": { "value": *** }, ... }, ... }
        try {
            Gson gson = new Gson();

            return gson.fromJson(data, ZWaveController.class);
        } catch (JsonParseException e) {
            logger.warn("Unexpected response format: {}", e.getMessage());
            mCaller.responseFormatError("Unexpected response format: " + e.getMessage(), false);
            return null;
        }
    }

    /*
     * (non-Javadoc)
     *
     * @see de.fh_zwickau.informatik.sensor.ZWayApiBase#getZWaveInclusion()
     */
    @Override
    public synchronized void getZWaveInclusion(int flag) {
        if (checkLogin()) {
            try {
                startHttpClient();

                String path = URLEncoder
                        .encode(StringUtils.replace(ZWAVE_PATH_INCLUSION, "{flag}", String.valueOf(flag)), "UTF-8");

                Request request = mHttpClient.newRequest(getZWaveTopLevelUrl() + "/" + path).method(HttpMethod.GET)
                        .header(HttpHeader.ACCEPT, "application/json")
                        .header(HttpHeader.CONTENT_TYPE, "application/json")
                        .cookie(new HttpCookie("ZWAYSession", mZWaySessionId));

                if (mUseRemoteService) {
                    request.cookie(new HttpCookie("ZBW_SESSID", mZWayRemoteSessionId));
                }

                ContentResponse response = request.send();

                // Check HTTP status code
                int statusCode = response.getStatus();
                if (statusCode != HttpStatus.OK_200) {
                    // Authentication error - retry login and operation
                    if (statusCode == HttpStatus.UNAUTHORIZED_401) {
                        if (getLogin() == null) {
                            mCaller.authenticationError();
                        } else {
                            getZWaveInclusion(flag);
                        }
                    } else {
                        processResponseStatus(statusCode);
                    }
                } else {
                    return;
                }
            } catch (Exception e) {
                if (e.getCause() instanceof HttpResponseException) {
                    int statusCode = ((HttpResponseException) e.getCause()).getResponse().getStatus();
                    // Authentication error - retry login and operation
                    if (statusCode == HttpStatus.UNAUTHORIZED_401) {
                        if (getLogin() == null) {
                            mCaller.authenticationError();
                        } else {
                            getZWaveInclusion(flag);
                        }
                    }
                } else {
                    handleException(e, "get zwave inclusion");
                }
            } finally {
                stopHttpClient();
            }
        } // no else ... checkLogin() method will invoke the appropriate callback method
    }

    /*
     * (non-Javadoc)
     *
     * @see de.fh_zwickau.informatik.sensor.ZWayApiBase#getZWaveExclusion()
     */
    @Override
    public synchronized void getZWaveExclusion(int flag) {
        if (checkLogin()) {
            try {
                startHttpClient();

                String path = URLEncoder
                        .encode(StringUtils.replace(ZWAVE_PATH_EXCLUSION, "{flag}", String.valueOf(flag)), "UTF-8");

                Request request = mHttpClient.newRequest(getZWaveTopLevelUrl() + "/" + path).method(HttpMethod.GET)
                        .header(HttpHeader.ACCEPT, "application/json")
                        .header(HttpHeader.CONTENT_TYPE, "application/json")
                        .cookie(new HttpCookie("ZWAYSession", mZWaySessionId));

                if (mUseRemoteService) {
                    request.cookie(new HttpCookie("ZBW_SESSID", mZWayRemoteSessionId));
                }

                ContentResponse response = request.send();

                // Check HTTP status code
                int statusCode = response.getStatus();
                if (statusCode != HttpStatus.OK_200) {
                    // Authentication error - retry login and operation
                    if (statusCode == HttpStatus.UNAUTHORIZED_401) {
                        if (getLogin() == null) {
                            mCaller.authenticationError();
                        } else {
                            getZWaveExclusion(flag);
                        }
                    } else {
                        processResponseStatus(statusCode);
                    }
                } else {
                    return;
                }
            } catch (Exception e) {
                if (e.getCause() instanceof HttpResponseException) {
                    int statusCode = ((HttpResponseException) e.getCause()).getResponse().getStatus();
                    // Authentication error - retry login and operation
                    if (statusCode == HttpStatus.UNAUTHORIZED_401) {
                        if (getLogin() == null) {
                            mCaller.authenticationError();
                        } else {
                            getZWaveExclusion(flag);
                        }
                    }
                } else {
                    handleException(e, "get zwave exclusion");
                }
            } finally {
                stopHttpClient();
            }
        } // no else ... checkLogin() method will invoke the appropriate callback method
    }

    @Override
    public void updateControllerData(String field, String value) {
        if (checkLogin()) {
            try {
                startHttpClient();

                String path = URLEncoder.encode(ZWAVE_PATH_CONTROLLER_DATA + "." + field + "=" + value, "UTF-8");

                Request request = mHttpClient.newRequest(getZWaveTopLevelUrl() + "/" + path).method(HttpMethod.GET)
                        .header(HttpHeader.ACCEPT, "application/json")
                        .header(HttpHeader.CONTENT_TYPE, "application/json")
                        .cookie(new HttpCookie("ZWAYSession", mZWaySessionId));

                if (mUseRemoteService) {
                    request.cookie(new HttpCookie("ZBW_SESSID", mZWayRemoteSessionId));
                }

                ContentResponse response = request.send();

                // Check HTTP status code
                int statusCode = response.getStatus();
                if (statusCode != HttpStatus.OK_200) {
                    // Authentication error - retry login and operation
                    if (statusCode == HttpStatus.UNAUTHORIZED_401) {
                        if (getLogin() == null) {
                            mCaller.authenticationError();
                        } else {
                            updateControllerData(field, value);
                        }
                    } else {
                        processResponseStatus(statusCode);
                    }
                } else {
                    return;
                }
            } catch (Exception e) {
                if (e.getCause() instanceof HttpResponseException) {
                    int statusCode = ((HttpResponseException) e.getCause()).getResponse().getStatus();
                    // Authentication error - retry login and operation
                    if (statusCode == HttpStatus.UNAUTHORIZED_401) {
                        if (getLogin() == null) {
                            mCaller.authenticationError();
                        } else {
                            updateControllerData(field, value);
                        }
                    }
                } else {
                    logger.warn("Request updateControllerData(field, value) failed: {}", e.getMessage());
                    mCaller.apiError(e.getMessage(), false);
                }
            } finally {
                stopHttpClient();
            }
        } // no else ... checkLogin() method will invoke the appropriate callback method
    }

    @Override
    public void getZWaveDeviceThermostatModeSet(int nodeId, int mode) {
        if (checkLogin()) {
            try {
                startHttpClient();

                String path = URLEncoder.encode(
                        StringUtils.replace(ZWAVE_PATH_DEVICES_CC_THERMOSTAT_SET, "{nodeId}", String.valueOf(nodeId))
                                .replace("{mode}", String.valueOf(mode)),
                        "UTF-8");

                Request request = mHttpClient.newRequest(getZWaveTopLevelUrl() + "/" + path).method(HttpMethod.GET)
                        .header(HttpHeader.ACCEPT, "application/json")
                        .header(HttpHeader.CONTENT_TYPE, "application/json")
                        .cookie(new HttpCookie("ZWAYSession", mZWaySessionId));

                if (mUseRemoteService) {
                    request.cookie(new HttpCookie("ZBW_SESSID", mZWayRemoteSessionId));
                }

                ContentResponse response = request.send();

                // Check HTTP status code
                int statusCode = response.getStatus();
                if (statusCode != HttpStatus.OK_200) {
                    // Authentication error - retry login and operation
                    if (statusCode == HttpStatus.UNAUTHORIZED_401) {
                        if (getLogin() == null) {
                            mCaller.authenticationError();
                        } else {
                            getZWaveDeviceThermostatModeSet(nodeId, mode);
                        }
                    } else {
                        processResponseStatus(statusCode);
                    }
                } else {
                    return;
                }
            } catch (Exception e) {
                if (e.getCause() instanceof HttpResponseException) {
                    int statusCode = ((HttpResponseException) e.getCause()).getResponse().getStatus();
                    // Authentication error - retry login and operation
                    if (statusCode == HttpStatus.UNAUTHORIZED_401) {
                        if (getLogin() == null) {
                            mCaller.authenticationError();
                        } else {
                            getZWaveDeviceThermostatModeSet(nodeId, mode);
                        }
                    }
                } else {
                    handleException(e, "get zwave device thermostat mode set");
                }
            } finally {
                stopHttpClient();
            }
        } // no else ... checkLogin() method will invoke the appropriate callback method
    }

    /*********************
     ****** Utility ******
     ********************/

    private synchronized void initHttpClient() {
        if (mProtocol.equals("http")) {
            mHttpClient = new HttpClient();
        } else if (mProtocol.equals("https")) {
            mHttpClient = new HttpClient(new SslContextFactory());
        }
        mHttpClient.setConnectTimeout(HTTP_CLIENT_TIMEOUT);
    }

    private synchronized void startHttpClient() {
        initHttpClient();
        if (!mHttpClient.isStarted()) {
            try {
                mHttpClient.start();
            } catch (Exception e) {
                logger.warn("Can not start HttpClient !", e);
            }
        }
    }

    private synchronized void stopHttpClient() {
        if (mHttpClient.isStarted()) {
            try {
                mHttpClient.stop();
            } catch (Exception e) {
                logger.error("Unable to stop HttpClient !", e);
            }
        }
    }

    private class ZWayFailureListener implements FailureListener {
        @Override
        public void onFailure(Request request, Throwable failure) {
            mCaller.apiError("Request " + request.toString() + " failed: " + failure.toString(), false);
        }
    }
}
