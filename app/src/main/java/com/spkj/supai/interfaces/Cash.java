package com.spkj.supai.interfaces;

import android.content.Context;

import com.toocms.dink5.mylibrary.app.AppConfig;
import com.toocms.dink5.mylibrary.commonutils.PreferencesUtils;
import com.toocms.dink5.mylibrary.net.ApiListener;
import com.toocms.dink5.mylibrary.net.ApiTool;

import org.xutils.http.RequestParams;


/**
 */
public class Cash {

    private String module;

    public Cash() {
        module = this.getClass().getSimpleName().toLowerCase(); // 模块名
    }


    /**
     * 支付
     *
     * @param apiListener
     */
    public void pay(Context context, ApiListener apiListener) {

        RequestParams params = new RequestParams(AppConfig.BASE_URL);
        params.addBodyParameter("command", "order.auction.pay");
        params.addBodyParameter("orderId", "orderId");
        params.addBodyParameter("payWay", "payWay");
        params.addBodyParameter("orderType", "orderType");
        params.addBodyParameter("receiveAddressId", "receiveAddressId");
        params.addBodyParameter("userId", PreferencesUtils.getString(context, "userId", ""));
        params.addBodyParameter("token", PreferencesUtils.getString(context, "token", ""));
        ApiTool apiTool = new ApiTool();
        apiTool.postApi(params, apiListener, "order.auction.pay");
    }

    /**
     * 支付宝回调
     *
     * @param apiListener
     */
    public void paySuccess(Context context, ApiListener apiListener) {

        RequestParams params = new RequestParams(AppConfig.BASE_URL);
        params.addBodyParameter("command", "order.pay.alisynchronized");
        params.addBodyParameter("memo", "memo");
        params.addBodyParameter("resultStatus", "resultStatus");
        params.addBodyParameter("result", "result");
        params.addBodyParameter("userId", PreferencesUtils.getString(context, "userId", ""));
        params.addBodyParameter("token", PreferencesUtils.getString(context, "token", ""));
        ApiTool apiTool = new ApiTool();
        apiTool.postApi(params, apiListener, "order.pay.alisynchronized");
    }

}
