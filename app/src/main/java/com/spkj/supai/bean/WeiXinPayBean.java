package com.spkj.supai.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by wanghao on 2017/4/12.
 */
public class WeiXinPayBean {


    /**
     * command : order.pay
     * responseCode : 0
     * errorMsg : 成功
     * orderInfoJson : [{"timestamp":"1492005695","sign":"51AC9825C82648063C1E309B0D590DAA","partnerid":"1454986802","noncestr":"ofz1zt8we22epvl26ui6","prepayid":"wx2017041222013282689aae170500176180","package":"Sign=WXPay","appid":"wxe045af8087c9fefc"}]
     * payWay : null
     * orderType : 1
     */

    private String command;
    private String responseCode;
    private String errorMsg;
    private Object payWay;
    private String orderType;
    private List<OrderInfoJsonBean> orderInfoJson;

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public String getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public Object getPayWay() {
        return payWay;
    }

    public void setPayWay(Object payWay) {
        this.payWay = payWay;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public List<OrderInfoJsonBean> getOrderInfoJson() {
        return orderInfoJson;
    }

    public void setOrderInfoJson(List<OrderInfoJsonBean> orderInfoJson) {
        this.orderInfoJson = orderInfoJson;
    }

    public static class OrderInfoJsonBean {
        /**
         * timestamp : 1492005695
         * sign : 51AC9825C82648063C1E309B0D590DAA
         * partnerid : 1454986802
         * noncestr : ofz1zt8we22epvl26ui6
         * prepayid : wx2017041222013282689aae170500176180
         * package : Sign=WXPay
         * appid : wxe045af8087c9fefc
         */

        private String timestamp;
        private String sign;
        private String partnerid;
        private String noncestr;
        private String prepayid;
        @SerializedName("package")
        private String packageX;
        private String appid;

        public String getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(String timestamp) {
            this.timestamp = timestamp;
        }

        public String getSign() {
            return sign;
        }

        public void setSign(String sign) {
            this.sign = sign;
        }

        public String getPartnerid() {
            return partnerid;
        }

        public void setPartnerid(String partnerid) {
            this.partnerid = partnerid;
        }

        public String getNoncestr() {
            return noncestr;
        }

        public void setNoncestr(String noncestr) {
            this.noncestr = noncestr;
        }

        public String getPrepayid() {
            return prepayid;
        }

        public void setPrepayid(String prepayid) {
            this.prepayid = prepayid;
        }

        public String getPackageX() {
            return packageX;
        }

        public void setPackageX(String packageX) {
            this.packageX = packageX;
        }

        public String getAppid() {
            return appid;
        }

        public void setAppid(String appid) {
            this.appid = appid;
        }
    }
}
