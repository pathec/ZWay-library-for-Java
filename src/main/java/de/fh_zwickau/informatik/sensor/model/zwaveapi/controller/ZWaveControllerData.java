/**
 * Copyright (C) 2016 by Software-Systementwicklung Zwickau Research Group
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package de.fh_zwickau.informatik.sensor.model.zwaveapi.controller;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.google.gson.annotations.SerializedName;

import de.fh_zwickau.informatik.sensor.model.zwaveapi.ZWaveDataElement;

/**
 * The {@link ZWaveControllerData} represents the data field of Z-Wave device. See also:
 * http://docs.zwayhomeautomation.apiary.io/#
 *
 * @author Patrick Hecker - Initial contribution
 */
public class ZWaveControllerData {
    @SerializedName("nodeId")
    private ZWaveDataElement mNodeId;
    @SerializedName("homeId")
    private ZWaveDataElement mHomeId;
    @SerializedName("SUCNodeId")
    private ZWaveDataElement mSUCNodeId;
    @SerializedName("isPrimary")
    private ZWaveDataElement mIsPrimary;
    @SerializedName("isInOthersNetwork")
    private ZWaveDataElement mIsInOthersNetwork;
    @SerializedName("isRealPrimary")
    private ZWaveDataElement mIsRealPrimary;
    @SerializedName("isSUC")
    private ZWaveDataElement mIsSUC;
    @SerializedName("SISPresent")
    private ZWaveDataElement mSISPresent;
    @SerializedName("libType")
    private ZWaveDataElement mLibType;
    @SerializedName("SDK")
    private ZWaveDataElement mSDK;
    @SerializedName("ZWlibMajor")
    private ZWaveDataElement mZWlibMajor;
    @SerializedName("ZWlibMinor")
    private ZWaveDataElement mZWlibMinor;
    @SerializedName("ZWLib")
    private ZWaveDataElement mZWLib;
    @SerializedName("ZWVersion")
    private ZWaveDataElement mZWVersion;
    @SerializedName("ZWaveChip")
    private ZWaveDataElement mZWaveChip;
    @SerializedName("APIVersion")
    private ZWaveDataElement mAPIVersion;
    @SerializedName("manufacturerId")
    private ZWaveDataElement mManufacturerId;
    @SerializedName("vendor")
    private ZWaveDataElement mVendor;
    @SerializedName("manufacturerProductType")
    private ZWaveDataElement mManufacturerProductType;
    @SerializedName("manufacturerProductId")
    private ZWaveDataElement mManufacturerProductId;
    // TODO capabilities is an array
    @SerializedName("controllerState")
    private ZWaveDataElement mControllerState;
    @SerializedName("nonManagmentJobs")
    private ZWaveDataElement mNonManagmentJobs;
    @SerializedName("lastIncludedDevice")
    private ZWaveDataElement mLastIncludedDevice;
    @SerializedName("lastExcludedDevice")
    private ZWaveDataElement mLastExcludedDevice;
    @SerializedName("secureInclusion")
    private ZWaveDataElement mSecureInclusion;
    @SerializedName("oldSerialAPIAckTimeout10ms")
    private ZWaveDataElement mOldSerialAPIAckTimeout10ms;
    @SerializedName("oldSerialAPIByteTimeout10ms")
    private ZWaveDataElement mOldSerialAPIByteTimeout10ms;
    @SerializedName("curSerialAPIAckTimeout10ms")
    private ZWaveDataElement mCurSerialAPIAckTimeout10ms;
    @SerializedName("curSerialAPIByteTimeout10ms")
    private ZWaveDataElement mCurSerialAPIByteTimeout10ms;
    @SerializedName("countJobs")
    private ZWaveDataElement mCountJobs;
    @SerializedName("memoryGetAddress")
    private ZWaveDataElement mMemoryGetAddress;
    @SerializedName("memoryGetData")
    private ZWaveDataElement mMemoryGetData;
    // TODO functionClasses is an array
    // TODO functionClassesNames is an array
    @SerializedName("softwareRevisionVersion")
    private ZWaveDataElement mSoftwareRevisionVersion;
    @SerializedName("softwareRevisionId")
    private ZWaveDataElement mSoftwareRevisionId;
    @SerializedName("softwareRevisionDate")
    private ZWaveDataElement mSoftwareRevisionDate;
    @SerializedName("uuid")
    private ZWaveDataElement mUuid;
    // TODO caps is an array
    @SerializedName("frequency")
    private ZWaveDataElement mFrequency;
    @SerializedName("deviceRelaxDelay")
    private ZWaveDataElement mDeviceRelaxDelay;

    /**
     * Instantiate a data field of Z-Wave controller with default values.
     */
    public ZWaveControllerData() {
        this.mNodeId = new ZWaveDataElement();
        this.mHomeId = new ZWaveDataElement();
        this.mSUCNodeId = new ZWaveDataElement();
        this.mIsPrimary = new ZWaveDataElement();
        this.mIsInOthersNetwork = new ZWaveDataElement();
        this.mIsRealPrimary = new ZWaveDataElement();
        this.mIsSUC = new ZWaveDataElement();
        this.mSISPresent = new ZWaveDataElement();
        this.mLibType = new ZWaveDataElement();
        this.mSDK = new ZWaveDataElement();
        this.mZWlibMajor = new ZWaveDataElement();
        this.mZWlibMinor = new ZWaveDataElement();
        this.mZWLib = new ZWaveDataElement();
        this.mZWVersion = new ZWaveDataElement();
        this.mZWaveChip = new ZWaveDataElement();
        this.mAPIVersion = new ZWaveDataElement();
        this.mManufacturerId = new ZWaveDataElement();
        this.mVendor = new ZWaveDataElement();
        this.mManufacturerProductType = new ZWaveDataElement();
        this.mManufacturerProductId = new ZWaveDataElement();
        this.mControllerState = new ZWaveDataElement();
        this.mNonManagmentJobs = new ZWaveDataElement();
        this.mLastIncludedDevice = new ZWaveDataElement();
        this.mLastExcludedDevice = new ZWaveDataElement();
        this.mSecureInclusion = new ZWaveDataElement();
        this.mOldSerialAPIAckTimeout10ms = new ZWaveDataElement();
        this.mOldSerialAPIByteTimeout10ms = new ZWaveDataElement();
        this.mCurSerialAPIAckTimeout10ms = new ZWaveDataElement();
        this.mCurSerialAPIByteTimeout10ms = new ZWaveDataElement();
        this.mCountJobs = new ZWaveDataElement();
        this.mMemoryGetAddress = new ZWaveDataElement();
        this.mMemoryGetData = new ZWaveDataElement();
        this.mSoftwareRevisionVersion = new ZWaveDataElement();
        this.mSoftwareRevisionId = new ZWaveDataElement();
        this.mSoftwareRevisionDate = new ZWaveDataElement();
        this.mUuid = new ZWaveDataElement();
        this.mFrequency = new ZWaveDataElement();
        this.mDeviceRelaxDelay = new ZWaveDataElement();
    }

    public ZWaveDataElement getNodeId() {
        if (mNodeId == null) {
            mNodeId = new ZWaveDataElement();
        }
        return mNodeId;
    }

    public void setNodeId(ZWaveDataElement nodeId) {
        this.mNodeId = nodeId;
    }

    public ZWaveDataElement getHomeId() {
        if (mHomeId == null) {
            mHomeId = new ZWaveDataElement();
        }
        return mHomeId;
    }

    public void setHomeId(ZWaveDataElement homeId) {
        this.mHomeId = homeId;
    }

    public ZWaveDataElement getSUCNodeId() {
        if (mSUCNodeId == null) {
            mSUCNodeId = new ZWaveDataElement();
        }
        return mSUCNodeId;
    }

    public void setSUCNodeId(ZWaveDataElement SUCNodeId) {
        this.mSUCNodeId = SUCNodeId;
    }

    public ZWaveDataElement getIsPrimary() {
        if (mIsPrimary == null) {
            mIsPrimary = new ZWaveDataElement();
        }
        return mIsPrimary;
    }

    public void setIsPrimary(ZWaveDataElement isPrimary) {
        this.mIsPrimary = isPrimary;
    }

    public ZWaveDataElement getIsInOthersNetwork() {
        if (mIsInOthersNetwork == null) {
            mIsInOthersNetwork = new ZWaveDataElement();
        }
        return mIsInOthersNetwork;
    }

    public void setIsInOthersNetwork(ZWaveDataElement isInOthersNetwork) {
        this.mIsInOthersNetwork = isInOthersNetwork;
    }

    public ZWaveDataElement getIsRealPrimary() {
        if (mIsRealPrimary == null) {
            mIsRealPrimary = new ZWaveDataElement();
        }
        return mIsRealPrimary;
    }

    public void setIsRealPrimary(ZWaveDataElement isRealPrimary) {
        this.mIsRealPrimary = isRealPrimary;
    }

    public ZWaveDataElement getIsSUC() {
        if (mIsSUC == null) {
            mIsSUC = new ZWaveDataElement();
        }
        return mIsSUC;
    }

    public void setIsSUC(ZWaveDataElement isSUC) {
        this.mIsSUC = isSUC;
    }

    public ZWaveDataElement getSISPresent() {
        if (mSISPresent == null) {
            mSISPresent = new ZWaveDataElement();
        }
        return mSISPresent;
    }

    public void setSISPresent(ZWaveDataElement SISPresent) {
        this.mSISPresent = SISPresent;
    }

    public ZWaveDataElement getLibType() {
        if (mLibType == null) {
            mLibType = new ZWaveDataElement();
        }
        return mLibType;
    }

    public void setLibType(ZWaveDataElement libType) {
        this.mLibType = libType;
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

    public ZWaveDataElement getZWlibMajor() {
        if (mZWlibMajor == null) {
            mZWlibMajor = new ZWaveDataElement();
        }
        return mZWlibMajor;
    }

    public void setZWlibMajor(ZWaveDataElement ZWlibMajor) {
        this.mZWlibMajor = ZWlibMajor;
    }

    public ZWaveDataElement getZWlibMinor() {
        if (mZWlibMinor == null) {
            mZWlibMinor = new ZWaveDataElement();
        }
        return mZWlibMinor;
    }

    public void setZWlibMinor(ZWaveDataElement ZWlibMinor) {
        this.mZWlibMinor = ZWlibMinor;
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

    public ZWaveDataElement getZWVersion() {
        if (mZWVersion == null) {
            mZWVersion = new ZWaveDataElement();
        }
        return mZWVersion;
    }

    public void setZWVersion(ZWaveDataElement ZWVersion) {
        this.mZWVersion = ZWVersion;
    }

    public ZWaveDataElement getZWaveChip() {
        if (mZWaveChip == null) {
            mZWaveChip = new ZWaveDataElement();
        }
        return mZWaveChip;
    }

    public void setZWaveChip(ZWaveDataElement ZWaveChip) {
        this.mZWaveChip = ZWaveChip;
    }

    public ZWaveDataElement getAPIVersion() {
        if (mAPIVersion == null) {
            mAPIVersion = new ZWaveDataElement();
        }
        return mAPIVersion;
    }

    public void setAPIVersion(ZWaveDataElement APIVersion) {
        this.mAPIVersion = APIVersion;
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

    public ZWaveDataElement getVendor() {
        if (mVendor == null) {
            mVendor = new ZWaveDataElement();
        }
        return mVendor;
    }

    public void setVendor(ZWaveDataElement vendor) {
        this.mVendor = vendor;
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

    public ZWaveDataElement getManufacturerProductId() {
        if (mManufacturerProductId == null) {
            mManufacturerProductId = new ZWaveDataElement();
        }
        return mManufacturerProductId;
    }

    public void setManufacturerProductId(ZWaveDataElement manufacturerProductId) {
        this.mManufacturerProductId = manufacturerProductId;
    }

    public ZWaveDataElement getControllerState() {
        if (mControllerState == null) {
            mControllerState = new ZWaveDataElement();
        }
        return mControllerState;
    }

    public void setControllerState(ZWaveDataElement controllerState) {
        this.mControllerState = controllerState;
    }

    public ZWaveDataElement getNonManagmentJobs() {
        if (mNonManagmentJobs == null) {
            mNonManagmentJobs = new ZWaveDataElement();
        }
        return mNonManagmentJobs;
    }

    public void setNonManagmentJobs(ZWaveDataElement nonManagmentJobs) {
        this.mNonManagmentJobs = nonManagmentJobs;
    }

    public ZWaveDataElement getLastIncludedDevice() {
        if (mLastIncludedDevice == null) {
            mLastIncludedDevice = new ZWaveDataElement();
        }
        return mLastIncludedDevice;
    }

    public void setLastIncludedDevice(ZWaveDataElement lastIncludedDevice) {
        this.mLastIncludedDevice = lastIncludedDevice;
    }

    public ZWaveDataElement getLastExcludedDevice() {
        if (mLastExcludedDevice == null) {
            mLastExcludedDevice = new ZWaveDataElement();
        }
        return mLastExcludedDevice;
    }

    public void setLastExcludedDevice(ZWaveDataElement lastExcludedDevice) {
        this.mLastExcludedDevice = lastExcludedDevice;
    }

    public ZWaveDataElement getSecureInclusion() {
        if (mSecureInclusion == null) {
            mSecureInclusion = new ZWaveDataElement();
        }
        return mSecureInclusion;
    }

    public void setSecureInclusion(ZWaveDataElement secureInclusion) {
        this.mSecureInclusion = secureInclusion;
    }

    public ZWaveDataElement getOldSerialAPIAckTimeout10ms() {
        if (mOldSerialAPIAckTimeout10ms == null) {
            mOldSerialAPIAckTimeout10ms = new ZWaveDataElement();
        }
        return mOldSerialAPIAckTimeout10ms;
    }

    public void setOldSerialAPIAckTimeout10ms(ZWaveDataElement oldSerialAPIAckTimeout10ms) {
        this.mOldSerialAPIAckTimeout10ms = oldSerialAPIAckTimeout10ms;
    }

    public ZWaveDataElement getOldSerialAPIByteTimeout10ms() {
        if (mOldSerialAPIByteTimeout10ms == null) {
            mOldSerialAPIByteTimeout10ms = new ZWaveDataElement();
        }
        return mOldSerialAPIByteTimeout10ms;
    }

    public void setOldSerialAPIByteTimeout10ms(ZWaveDataElement oldSerialAPIByteTimeout10ms) {
        this.mOldSerialAPIByteTimeout10ms = oldSerialAPIByteTimeout10ms;
    }

    public ZWaveDataElement getCurSerialAPIAckTimeout10ms() {
        if (mCurSerialAPIAckTimeout10ms == null) {
            mCurSerialAPIAckTimeout10ms = new ZWaveDataElement();
        }
        return mCurSerialAPIAckTimeout10ms;
    }

    public void setCurSerialAPIAckTimeout10ms(ZWaveDataElement curSerialAPIAckTimeout10ms) {
        this.mCurSerialAPIAckTimeout10ms = curSerialAPIAckTimeout10ms;
    }

    public ZWaveDataElement getCurSerialAPIByteTimeout10ms() {
        if (mCurSerialAPIByteTimeout10ms == null) {
            mCurSerialAPIByteTimeout10ms = new ZWaveDataElement();
        }
        return mCurSerialAPIByteTimeout10ms;
    }

    public void setCurSerialAPIByteTimeout10ms(ZWaveDataElement curSerialAPIByteTimeout10ms) {
        this.mCurSerialAPIByteTimeout10ms = curSerialAPIByteTimeout10ms;
    }

    public ZWaveDataElement getCountJobs() {
        if (mCountJobs == null) {
            mCountJobs = new ZWaveDataElement();
        }
        return mCountJobs;
    }

    public void setCountJobs(ZWaveDataElement countJobs) {
        this.mCountJobs = countJobs;
    }

    public ZWaveDataElement getMemoryGetAddress() {
        if (mMemoryGetAddress == null) {
            mMemoryGetAddress = new ZWaveDataElement();
        }
        return mMemoryGetAddress;
    }

    public void setMemoryGetAddress(ZWaveDataElement memoryGetAddress) {
        this.mMemoryGetAddress = memoryGetAddress;
    }

    public ZWaveDataElement getMemoryGetData() {
        if (mMemoryGetData == null) {
            mMemoryGetData = new ZWaveDataElement();
        }
        return mMemoryGetData;
    }

    public void setMemoryGetData(ZWaveDataElement memoryGetData) {
        this.mMemoryGetData = memoryGetData;
    }

    public ZWaveDataElement getSoftwareRevisionVersion() {
        if (mSoftwareRevisionVersion == null) {
            mSoftwareRevisionVersion = new ZWaveDataElement();
        }
        return mSoftwareRevisionVersion;
    }

    public void setSoftwareRevisionVersion(ZWaveDataElement softwareRevisionVersion) {
        this.mSoftwareRevisionVersion = softwareRevisionVersion;
    }

    public ZWaveDataElement getSoftwareRevisionId() {
        if (mSoftwareRevisionId == null) {
            mSoftwareRevisionId = new ZWaveDataElement();
        }
        return mSoftwareRevisionId;
    }

    public void setSoftwareRevisionId(ZWaveDataElement softwareRevisionId) {
        this.mSoftwareRevisionId = softwareRevisionId;
    }

    public ZWaveDataElement getSoftwareRevisionDate() {
        if (mSoftwareRevisionDate == null) {
            mSoftwareRevisionDate = new ZWaveDataElement();
        }
        return mSoftwareRevisionDate;
    }

    public void setSoftwareRevisionDate(ZWaveDataElement softwareRevisionDate) {
        this.mSoftwareRevisionDate = softwareRevisionDate;
    }

    public ZWaveDataElement getUuid() {
        if (mUuid == null) {
            mUuid = new ZWaveDataElement();
        }
        return mUuid;
    }

    public void setUuid(ZWaveDataElement uuid) {
        this.mUuid = uuid;
    }

    public ZWaveDataElement getFrequency() {
        if (mFrequency == null) {
            mFrequency = new ZWaveDataElement();
        }
        return mFrequency;
    }

    public void setFrequency(ZWaveDataElement frequency) {
        this.mFrequency = frequency;
    }

    public ZWaveDataElement getDeviceRelaxDelay() {
        if (mDeviceRelaxDelay == null) {
            mDeviceRelaxDelay = new ZWaveDataElement();
        }
        return mDeviceRelaxDelay;
    }

    public void setDeviceRelaxDelay(ZWaveDataElement deviceRelaxDelay) {
        this.mDeviceRelaxDelay = deviceRelaxDelay;
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return new ToStringBuilder(this).append("basicType", mNodeId).append("homeId", mHomeId)
                .append("SUCNodeId", mSUCNodeId).append("isPrimary", mIsPrimary)
                .append("iIsInOthersNetwork", mIsInOthersNetwork).append("isRealPrimary", mIsRealPrimary)
                .append("isSUC", mIsSUC).append("SISPresent", mSISPresent).append("libType", mLibType)
                .append("SDK", mSDK).append("ZWlibMajor", mZWlibMajor).append("ZWlibMinor", mZWlibMinor)
                .append("ZWLib", mZWLib).append("ZWVersion", mZWVersion).append("ZWaveChip", mZWaveChip)
                .append("APIVersion", mAPIVersion).append("manufacturerId", mManufacturerId).append("vendor", mVendor)
                .append("manufacturerProductType", mManufacturerProductType)
                .append("manufacturerProductId", mManufacturerProductId).append("controllerState", mControllerState)
                .append("nonManagmentJobs", mNonManagmentJobs).append("lastIncludedDevice", mLastIncludedDevice)
                .append("lastExcludedDevice", mLastExcludedDevice).append("secureInclusion", mSecureInclusion)
                .append("oldSerialAPIAckTimeout10ms", mOldSerialAPIAckTimeout10ms)
                .append("oldSerialAPIByteTimeout10ms", mOldSerialAPIByteTimeout10ms)
                .append("curSerialAPIAckTimeout10ms", mCurSerialAPIAckTimeout10ms)
                .append("curSerialAPIByteTimeout10ms", mCurSerialAPIByteTimeout10ms).append("countJobs", mCountJobs)
                .append("memoryGetAddress", mMemoryGetAddress).append("memoryGetData", mMemoryGetData)
                .append("softwareRevisionVersion", mSoftwareRevisionVersion)
                .append("softwareRevisionId", mSoftwareRevisionId).append("softwareRevisionDate", mSoftwareRevisionDate)
                .append("uuid", mUuid).append("frequency", mFrequency).append("deviceRelaxDela", mDeviceRelaxDelay)
                .toString();
    }
}
