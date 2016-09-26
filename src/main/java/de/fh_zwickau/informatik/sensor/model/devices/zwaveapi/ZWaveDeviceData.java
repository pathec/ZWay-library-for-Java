/**
 * Copyright (C) 2016 by Software-Systementwicklung Zwickau Research Group
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package de.fh_zwickau.informatik.sensor.model.devices.zwaveapi;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.google.gson.annotations.SerializedName;

/**
 * The {@link ZWaveDeviceData} represents the data field of Z-Wave device. See also:
 * http://docs.zwayhomeautomation.apiary.io/#
 *
 * @author Patrick Hecker - Initial contribution
 */
public class ZWaveDeviceData {
    @SerializedName("basicType")
    private ZWaveDataElement mBasicType;
    @SerializedName("genericType")
    private ZWaveDataElement mGenericType;
    @SerializedName("specificType")
    private ZWaveDataElement mSpecificType;
    @SerializedName("infoProtocolSpecific")
    private ZWaveDataElement mInfoProtocolSpecific;
    @SerializedName("deviceTypeString")
    private ZWaveDataElement mDeviceTypeString;
    @SerializedName("isVirtual")
    private ZWaveDataElement mIsVirtual;
    @SerializedName("isListening")
    private ZWaveDataElement mIsListening;
    @SerializedName("isRouting")
    private ZWaveDataElement mIsRouting;
    @SerializedName("isAwake")
    private ZWaveDataElement mIsAwake;
    @SerializedName("optional")
    private ZWaveDataElement mOptional;
    @SerializedName("isFailed")
    private ZWaveDataElement mIsFailed;
    @SerializedName("beam")
    private ZWaveDataElement mBeam;
    @SerializedName("sensor250")
    private ZWaveDataElement mSensor250;
    @SerializedName("sensor1000")
    private ZWaveDataElement mSensor1000;
    // TODO neighbours is an array
    @SerializedName("manufacturerId")
    private ZWaveDataElement mManufacturerId;
    @SerializedName("vendorString")
    private ZWaveDataElement mVendorString;
    @SerializedName("manufacturerProductType")
    private ZWaveDataElement mManufacturerProductType;
    @SerializedName("ZWLib")
    private ZWaveDataElement mZWLib;
    @SerializedName("ZWProtocolMajor")
    private ZWaveDataElement mZWProtocolMajor;
    @SerializedName("ZWProtocolMinor")
    private ZWaveDataElement mZWProtocolMinor;
    @SerializedName("SDK")
    private ZWaveDataElement mSDK;
    @SerializedName("applicationMajor")
    private ZWaveDataElement mApplicationMajor;
    @SerializedName("applicationMinor")
    private ZWaveDataElement mApplicationMinor;
    // TODO nodeInfoFrame is an array
    @SerializedName("ZDDXMLFile")
    private ZWaveDataElement mZDDXMLFile;
    @SerializedName("lastSend")
    private ZWaveDataElement mLastSend;
    @SerializedName("lastNonceGet")
    private ZWaveDataElement mLastNonceGet;
    @SerializedName("lastReceived")
    private ZWaveDataElement mLastReceived;
    @SerializedName("failureCount")
    private ZWaveDataElement mFailureCount;
    @SerializedName("keepAwake")
    private ZWaveDataElement mKeepAwake;
    @SerializedName("countSuccess")
    private ZWaveDataElement mCountSuccess;
    @SerializedName("countFailed")
    private ZWaveDataElement mCountFailed;
    @SerializedName("queueLength")
    private ZWaveDataElement mQueueLength;
    @SerializedName("givenName")
    private ZWaveDataElement mGivenName;

    /**
     * Instantiate a data field of Z-Wave device with default values.
     */
    public ZWaveDeviceData() {
        this.mBasicType = new ZWaveDataElement();
        this.mGenericType = new ZWaveDataElement();
        this.mSpecificType = new ZWaveDataElement();
        this.mInfoProtocolSpecific = new ZWaveDataElement();
        this.mDeviceTypeString = new ZWaveDataElement();
        this.mIsVirtual = new ZWaveDataElement();
        this.mIsListening = new ZWaveDataElement();
        this.mIsRouting = new ZWaveDataElement();
        this.mIsAwake = new ZWaveDataElement();
        this.mOptional = new ZWaveDataElement();
        this.mIsFailed = new ZWaveDataElement();
        this.mBeam = new ZWaveDataElement();
        this.mSensor250 = new ZWaveDataElement();
        this.mSensor1000 = new ZWaveDataElement();
        this.mManufacturerId = new ZWaveDataElement();
        this.mVendorString = new ZWaveDataElement();
        this.mManufacturerProductType = new ZWaveDataElement();
        this.mZWLib = new ZWaveDataElement();
        this.mZWProtocolMajor = new ZWaveDataElement();
        this.mZWProtocolMinor = new ZWaveDataElement();
        this.mSDK = new ZWaveDataElement();
        this.mApplicationMajor = new ZWaveDataElement();
        this.mApplicationMinor = new ZWaveDataElement();
        this.mZDDXMLFile = new ZWaveDataElement();
        this.mLastSend = new ZWaveDataElement();
        this.mLastNonceGet = new ZWaveDataElement();
        this.mLastReceived = new ZWaveDataElement();
        this.mFailureCount = new ZWaveDataElement();
        this.mKeepAwake = new ZWaveDataElement();
        this.mCountSuccess = new ZWaveDataElement();
        this.mCountFailed = new ZWaveDataElement();
        this.mQueueLength = new ZWaveDataElement();
        this.mGivenName = new ZWaveDataElement();
    }

    public ZWaveDataElement getBasicType() {
        if (mBasicType == null) {
            mBasicType = new ZWaveDataElement();
        }
        return mBasicType;
    }

    public void setBasicType(ZWaveDataElement basicType) {
        this.mBasicType = basicType;
    }

    public ZWaveDataElement getGenericType() {
        if (mGenericType == null) {
            mGenericType = new ZWaveDataElement();
        }
        return mGenericType;
    }

    public void setGenericType(ZWaveDataElement genericType) {
        this.mGenericType = genericType;
    }

    public ZWaveDataElement getSpecificType() {
        if (mSpecificType == null) {
            mSpecificType = new ZWaveDataElement();
        }
        return mSpecificType;
    }

    public void setSpecificType(ZWaveDataElement specificType) {
        this.mSpecificType = specificType;
    }

    public ZWaveDataElement getInfoProtocolSpecific() {
        if (mInfoProtocolSpecific == null) {
            mInfoProtocolSpecific = new ZWaveDataElement();
        }
        return mInfoProtocolSpecific;
    }

    public void setInfoProtocolSpecific(ZWaveDataElement infoProtocolSpecific) {
        this.mInfoProtocolSpecific = infoProtocolSpecific;
    }

    public ZWaveDataElement getDeviceTypeString() {
        if (mDeviceTypeString == null) {
            mDeviceTypeString = new ZWaveDataElement();
        }
        return mDeviceTypeString;
    }

    public void setDeviceTypeString(ZWaveDataElement deviceTypeString) {
        this.mDeviceTypeString = deviceTypeString;
    }

    public ZWaveDataElement getIsVirtual() {
        if (mIsVirtual == null) {
            mIsVirtual = new ZWaveDataElement();
        }
        return mIsVirtual;
    }

    public void setIsVirtual(ZWaveDataElement isVirtual) {
        this.mIsVirtual = isVirtual;
    }

    public ZWaveDataElement getIsListening() {
        if (mIsListening == null) {
            mIsListening = new ZWaveDataElement();
        }
        return mIsListening;
    }

    public void setIsListening(ZWaveDataElement isListening) {
        this.mIsListening = isListening;
    }

    public ZWaveDataElement getIsRouting() {
        if (mIsRouting == null) {
            mIsRouting = new ZWaveDataElement();
        }
        return mIsRouting;
    }

    public void setIsRouting(ZWaveDataElement isRouting) {
        this.mIsRouting = isRouting;
    }

    public ZWaveDataElement getIsAwake() {
        if (mIsAwake == null) {
            mIsAwake = new ZWaveDataElement();
        }
        return mIsAwake;
    }

    public void setIsAwake(ZWaveDataElement isAwake) {
        this.mIsAwake = isAwake;
    }

    public ZWaveDataElement getOptional() {
        if (mOptional == null) {
            mOptional = new ZWaveDataElement();
        }
        return mOptional;
    }

    public void setOptional(ZWaveDataElement optional) {
        this.mOptional = optional;
    }

    public ZWaveDataElement getIsFailed() {
        if (mIsFailed == null) {
            mIsFailed = new ZWaveDataElement();
        }
        return mIsFailed;
    }

    public void setIsFailed(ZWaveDataElement isFailed) {
        this.mIsFailed = isFailed;
    }

    public ZWaveDataElement getBeam() {
        if (mBeam == null) {
            mBeam = new ZWaveDataElement();
        }
        return mBeam;
    }

    public void setBeam(ZWaveDataElement beam) {
        this.mBeam = beam;
    }

    public ZWaveDataElement getSensor250() {
        if (mSensor250 == null) {
            mSensor250 = new ZWaveDataElement();
        }
        return mSensor250;
    }

    public void setSensor250(ZWaveDataElement sensor250) {
        this.mSensor250 = sensor250;
    }

    public ZWaveDataElement getSensor1000() {
        if (mSensor1000 == null) {
            mSensor1000 = new ZWaveDataElement();
        }
        return mSensor1000;
    }

    public void setSensor1000(ZWaveDataElement sensor1000) {
        this.mSensor1000 = sensor1000;
    }

    public ZWaveDataElement getManufacturerId() {
        if (mManufacturerId == null) {
            mManufacturerId = new ZWaveDataElement();
        }
        return mManufacturerId;
    }

    public void setManufacturerId(ZWaveDataElement manufacturerId) {
        this.mManufacturerId = manufacturerId;
    }

    public ZWaveDataElement getVendorString() {
        if (mVendorString == null) {
            mVendorString = new ZWaveDataElement();
        }
        return mVendorString;
    }

    public void setVendorString(ZWaveDataElement vendorString) {
        this.mVendorString = vendorString;
    }

    public ZWaveDataElement getManufacturerProductType() {
        if (mManufacturerProductType == null) {
            mManufacturerProductType = new ZWaveDataElement();
        }
        return mManufacturerProductType;
    }

    public void setManufacturerProductType(ZWaveDataElement manufacturerProductType) {
        this.mManufacturerProductType = manufacturerProductType;
    }

    public ZWaveDataElement getZWLib() {
        if (mZWLib == null) {
            mZWLib = new ZWaveDataElement();
        }
        return mZWLib;
    }

    public void setZWLib(ZWaveDataElement ZWLib) {
        this.mZWLib = ZWLib;
    }

    public ZWaveDataElement getZWProtocolMajor() {
        if (mZWProtocolMajor == null) {
            mZWProtocolMajor = new ZWaveDataElement();
        }
        return mZWProtocolMajor;
    }

    public void setZWProtocolMajor(ZWaveDataElement ZWProtocolMajor) {
        this.mZWProtocolMajor = ZWProtocolMajor;
    }

    public ZWaveDataElement getZWProtocolMinor() {
        if (mZWProtocolMinor == null) {
            mZWProtocolMinor = new ZWaveDataElement();
        }
        return mZWProtocolMinor;
    }

    public void setZWProtocolMinor(ZWaveDataElement ZWProtocolMinor) {
        this.mZWProtocolMinor = ZWProtocolMinor;
    }

    public ZWaveDataElement getSDK() {
        if (mSDK == null) {
            mSDK = new ZWaveDataElement();
        }
        return mSDK;
    }

    public void setSDK(ZWaveDataElement SDK) {
        this.mSDK = SDK;
    }

    public ZWaveDataElement getApplicationMajor() {
        if (mApplicationMajor == null) {
            mApplicationMajor = new ZWaveDataElement();
        }
        return mApplicationMajor;
    }

    public void setApplicationMajor(ZWaveDataElement applicationMajor) {
        this.mApplicationMajor = applicationMajor;
    }

    public ZWaveDataElement getApplicationMinor() {
        if (mApplicationMinor == null) {
            mApplicationMinor = new ZWaveDataElement();
        }
        return mApplicationMinor;
    }

    public void setApplicationMinor(ZWaveDataElement applicationMinor) {
        this.mApplicationMinor = applicationMinor;
    }

    public ZWaveDataElement getZDDXMLFile() {
        if (mZDDXMLFile == null) {
            mZDDXMLFile = new ZWaveDataElement();
        }
        return mZDDXMLFile;
    }

    public void setZDDXMLFile(ZWaveDataElement ZDDXMLFile) {
        this.mZDDXMLFile = ZDDXMLFile;
    }

    public ZWaveDataElement getLastSend() {
        if (mLastSend == null) {
            mLastSend = new ZWaveDataElement();
        }
        return mLastSend;
    }

    public void setLastSend(ZWaveDataElement lastSend) {
        this.mLastSend = lastSend;
    }

    public ZWaveDataElement getLastNonceGet() {
        if (mLastNonceGet == null) {
            mLastNonceGet = new ZWaveDataElement();
        }
        return mLastNonceGet;
    }

    public void setLastNonceGet(ZWaveDataElement lastNonceGet) {
        this.mLastNonceGet = lastNonceGet;
    }

    public ZWaveDataElement getLastReceived() {
        if (mLastReceived == null) {
            mLastReceived = new ZWaveDataElement();
        }
        return mLastReceived;
    }

    public void setLastReceived(ZWaveDataElement lastReceived) {
        this.mLastReceived = lastReceived;
    }

    public ZWaveDataElement getFailureCount() {
        if (mFailureCount == null) {
            mFailureCount = new ZWaveDataElement();
        }
        return mFailureCount;
    }

    public void setFailureCount(ZWaveDataElement failureCount) {
        this.mFailureCount = failureCount;
    }

    public ZWaveDataElement getKeepAwake() {
        if (mKeepAwake == null) {
            mKeepAwake = new ZWaveDataElement();
        }
        return mKeepAwake;
    }

    public void setKeepAwake(ZWaveDataElement keepAwake) {
        this.mKeepAwake = keepAwake;
    }

    public ZWaveDataElement getCountSuccess() {
        if (mCountSuccess == null) {
            mCountSuccess = new ZWaveDataElement();
        }
        return mCountSuccess;
    }

    public void setCountSuccess(ZWaveDataElement countSuccess) {
        this.mCountSuccess = countSuccess;
    }

    public ZWaveDataElement getCountFailed() {
        return mCountFailed;
    }

    public void setCountFailed(ZWaveDataElement countFailed) {
        this.mCountFailed = countFailed;
    }

    public ZWaveDataElement getQueueLength() {
        if (mQueueLength == null) {
            mQueueLength = new ZWaveDataElement();
        }
        return mQueueLength;
    }

    public void setQueueLength(ZWaveDataElement queueLength) {
        this.mQueueLength = queueLength;
    }

    public ZWaveDataElement getGivenName() {
        if (mGivenName == null) {
            mGivenName = new ZWaveDataElement();
        }
        return mGivenName;
    }

    public void setGivenName(ZWaveDataElement givenName) {
        this.mGivenName = givenName;
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return new ToStringBuilder(this).append("basicType", mBasicType).append("genericType", mGenericType)
                .append("specificType", mSpecificType).append("infoProtocolSpecific", mInfoProtocolSpecific)
                .append("deviceTypeString", mDeviceTypeString).append("isVirtual", mIsVirtual)
                .append("isListening", mIsListening).append("isRouting", mIsRouting).append("isAwake", mIsAwake)
                .append("optional", mOptional).append("isFailed", mIsFailed).append("beam", mBeam)
                .append("sensor250", mSensor250).append("sensor1000", mSensor1000)
                .append("manufacturerId", mManufacturerId).append("vendorString", mVendorString)
                .append("manufacturerProductType", mManufacturerProductType).append("ZWLib", mZWLib)
                .append("ZWProtocolMajor", mZWProtocolMajor).append("ZWProtocolMinor", mZWProtocolMinor)
                .append("SDK", mSDK).append("applicationMajor", mApplicationMajor)
                .append("applicationMinor", mApplicationMinor).append("ZDDXMLFile", mZDDXMLFile)
                .append("lastSend", mLastSend).append("lastNonceGet", mLastNonceGet)
                .append("lastReceived", mLastReceived).append("failureCount", mFailureCount)
                .append("keepAwake", mKeepAwake).append("countSuccess", mCountSuccess)
                .append("countFailed", mCountFailed).append("queueLength", mQueueLength).append("givenName", mGivenName)
                .toString();
    }
}
