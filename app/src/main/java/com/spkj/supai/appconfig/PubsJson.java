package com.spkj.supai.appconfig;

/**
 * Created by aa on 2017/4/27.
 */

public class PubsJson {
    private String imei;//手机imei编码
    private String os;  //手机操作系统(android、ios)
    private String osVersion;//操作系统版本，例如ios 10.1.2
    private String appVersion;//应用版本号
    private String timestamp;//时间戳

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public String getOs() {
        return os;
    }

    public void setOs(String os) {
        this.os = os;
    }

    public String getOsVersion() {
        return osVersion;
    }

    public void setOsVersion(String osVersion) {
        this.osVersion = osVersion;
    }

    public String getAppVersion() {
        return appVersion;
    }

    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
