/**
 * Copyright (C) 2016 by Software-Systementwicklung Zwickau Research Group
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package de.fh_zwickau.informatik.sensor.model.login;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.google.gson.annotations.SerializedName;

/**
 * The {@link LoginForm} represents the Z-Way login form. See also:
 * http://docs.zwayhomeautomation.apiary.io/#
 *
 * @author Patrick Hecker - Initial contribution
 */
public class LoginForm {
    @SerializedName("form")
    private Boolean mForm;
    @SerializedName("login")
    private String mLogin;
    @SerializedName("password")
    private String mPassword;
    @SerializedName("keepme")
    private Boolean mKeepme;
    @SerializedName("default_ui")
    private Integer mDefaultUI;

    /**
     * Instantiate a login form with default values.
     */
    public LoginForm() {
        mForm = true;
        mLogin = "";
        mPassword = "";
        mKeepme = false;
        mDefaultUI = 1;
    }

    /**
     * Instantiate a login form with passed values.
     *
     * @param form should be true
     * @param login username
     * @param password password
     * @param keepme should be false
     * @param defaultUI should be 1
     */
    public LoginForm(Boolean form, String login, String password, Boolean keepme, Integer defaultUI) {
        mForm = form;
        mLogin = login;
        mPassword = password;
        mKeepme = keepme;
        mDefaultUI = defaultUI;
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return new ToStringBuilder(this).append("form", mForm).append("login", mLogin).append("password", mPassword)
                .append("keepme", mKeepme).append("defaultui", mDefaultUI).toString();
    }
}
