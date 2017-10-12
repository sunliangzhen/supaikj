package com.spkj.supai.ui.pay;

import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;

import com.alipay.sdk.app.PayTask;
import com.google.gson.Gson;
import com.spkj.supai.R;
import com.spkj.supai.bean.PayResult;
import com.spkj.supai.bean.WeiXinPayBean;
import com.spkj.supai.interfaces.Cash;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.toocms.dink5.mylibrary.base.BaseActivity;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.Event;

import java.util.List;
import java.util.Map;

/**
 * Created by aa on 2017/6/29.
 */

public class PayAty extends BaseActivity {

    private Cash cash;
    private String mOrderinfo;
    private static final int SDK_PAY_FLAG = 1;

    @Override
    public int getLayoutId() {
        return R.layout.aty_pay;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView() {
        cash = new Cash();
    }

    @Override
    public void requestData() {

    }

    @Event(value = {R.id.btn_wei, R.id.btn_zhi})
    private void onTestBaidulClick(View view) {

        switch (view.getId()) {
            case R.id.btn_wei:
                cash.pay(this, this);
                break;
            case R.id.btn_zhi:
                cash.pay(this, this);
                break;
        }
    }

    private Handler mHandler = new Handler() {
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG:
                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                    String resultStatus = payResult.getResultStatus();
                    String memo = payResult.getMemo();
                    cash.paySuccess(PayAty.this, PayAty.this);
                    // 判断resultStatus 为9000则代表支付成功
                    if (TextUtils.equals(resultStatus, "9000")) {
                    }
                    break;
            }
        }
    };


    @Override
    public void onComplete(RequestParams var1, String t, String type) {
        super.onComplete(var1, t, type);
        if (type.equals("order.pay.alisynchronized")) {

        }

        if (type.equals("order.auction.pay")) {
            try {
                JSONObject jsonObject = new JSONObject(t);
                if (TextUtils.equals(jsonObject.getString("responseCode"), "0")) {
                    if (TextUtils.equals(jsonObject.getString("payWay"), "0")) {
                        Gson g = new Gson();
                        orderinfo orderinfo = g.fromJson(t, orderinfo.class);
                        mOrderinfo = orderinfo.orderInfoJson;
                        final String orderInfo = mOrderinfo;
                        Runnable payRunnable = new Runnable() {
                            @Override
                            public void run() {
                                PayTask alipay = new PayTask(PayAty.this);
                                Map<String, String> result = alipay.payV2(orderInfo, true);
                                Message msg = new Message();
                                msg.what = SDK_PAY_FLAG;
                                msg.obj = result;
                                mHandler.sendMessage(msg);
                            }
                        };
                        Thread payThread = new Thread(payRunnable);
                        payThread.start();
                    } else {
                        Gson g = new Gson();
                        WeiXinPayBean weiXinPayBean = g.fromJson(t, WeiXinPayBean.class);
                        List<WeiXinPayBean.OrderInfoJsonBean> orderInfoJson = weiXinPayBean.getOrderInfoJson();
                        IWXAPI mWxApi = WXAPIFactory.createWXAPI(PayAty.this, "wxe045af8087c9fefc");
                        mWxApi.registerApp("wxe045af8087c9fefc");

                        PayReq req = new PayReq();
                        req.appId = "wxe045af8087c9fefc";
                        req.partnerId = orderInfoJson.get(0).getPartnerid();
                        req.prepayId = orderInfoJson.get(0).getPrepayid();
                        req.nonceStr = orderInfoJson.get(0).getNoncestr();
                        req.packageValue = "Sign=WXPay";
                        req.sign = orderInfoJson.get(0).getSign();
                        req.timeStamp = orderInfoJson.get(0).getTimestamp();
                        mWxApi.sendReq(req);
                    }
                } else {
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }


    class orderinfo {
        public String command;
        public String responseCode;
        public String errorMsg;
        public String orderInfoJson;
    }
}
