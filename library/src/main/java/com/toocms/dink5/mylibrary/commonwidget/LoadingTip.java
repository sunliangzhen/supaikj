package com.toocms.dink5.mylibrary.commonwidget;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.toocms.dink5.mylibrary.R;


/**
 * des:加载页面内嵌提示
 * Created by xsf
 * on 2016.07.17:22
 */
public class LoadingTip extends LinearLayout {

    private ImageView img_tip_logo;
    private ProgressBar progress;
    private TextView tv_tips;
    private TextView bt_operate;
    private TextView tv_lar;
    private String errorMsg;
    private onReloadListener onReloadListener;
    private onLarListener onLarListener;


    public LoadingTip(Context context) {
        super(context);
        initView(context);
    }

    public LoadingTip(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public LoadingTip(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public LoadingTip(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView(context);
    }

    //分为服务器失败，网络加载失败、数据为空、加载中、完成四种状态
    public static enum LoadStatus {
        sereverError, error, empty, loading, finish, lar
    }

    private void initView(Context context) {
        View.inflate(context, R.layout.dialog_loading_tip, this);
        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        img_tip_logo = (ImageView) findViewById(R.id.img_tip_logo);
        progress = (ProgressBar) findViewById(R.id.progress);
        tv_tips = (TextView) findViewById(R.id.tv_tips);
        tv_lar = (TextView) findViewById(R.id.tv_lar);
        bt_operate = (TextView) findViewById(R.id.bt_operate);
        //重新尝试
        bt_operate.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onReloadListener != null) {
                    onReloadListener.reload();
                }
            }
        });
        //登陆
        tv_lar.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onLarListener != null) {
                    onLarListener.lar();
                }
            }
        });
        setVisibility(View.GONE);
    }

    public void setTips(String tips) {
        if (tv_tips != null) {
            tv_tips.setText(tips);
        }
    }

    /**
     * 根据状态显示不同的提示
     *
     * @param loadStatus
     */
    public void setLoadingTip(LoadStatus loadStatus) {
        switch (loadStatus) {
            case empty:
                setVisibility(View.VISIBLE);
                img_tip_logo.setVisibility(View.VISIBLE);
                progress.setVisibility(View.GONE);
                tv_tips.setText(getContext().getText(R.string.empty).toString());
                img_tip_logo.setImageResource(R.drawable.ic_empty);
                bt_operate.setVisibility(View.VISIBLE);
                tv_tips.setVisibility(View.GONE);
                tv_lar.setVisibility(View.GONE);
                break;
            case sereverError:
                setVisibility(View.VISIBLE);
                img_tip_logo.setVisibility(View.VISIBLE);
                progress.setVisibility(View.GONE);
                if (TextUtils.isEmpty(errorMsg)) {
                    tv_tips.setText(getContext().getText(R.string.net_error).toString());
                } else {
                    tv_tips.setText(errorMsg);
                }
                img_tip_logo.setImageResource(R.drawable.ic_neterror);
                bt_operate.setVisibility(View.GONE);
                tv_tips.setVisibility(View.GONE);
                tv_lar.setVisibility(View.GONE);
                break;
            case error:
                setVisibility(View.VISIBLE);
                img_tip_logo.setVisibility(View.VISIBLE);
                progress.setVisibility(View.GONE);
                if (TextUtils.isEmpty(errorMsg)) {
                    tv_tips.setText(getContext().getText(R.string.net_error).toString());
                } else {
                    tv_tips.setText(errorMsg);
                }
                img_tip_logo.setImageResource(R.drawable.ic_neterror);
                bt_operate.setVisibility(View.VISIBLE);
                tv_tips.setVisibility(View.GONE);
                tv_lar.setVisibility(View.GONE);
                break;
            case loading:
                setVisibility(View.VISIBLE);
                img_tip_logo.setVisibility(View.GONE);
                progress.setVisibility(View.VISIBLE);
                tv_tips.setText("加载中...");
                bt_operate.setVisibility(View.GONE);
                tv_tips.setVisibility(View.VISIBLE);
                tv_lar.setVisibility(View.GONE);
                break;
            case lar:
                setVisibility(View.VISIBLE);
                img_tip_logo.setVisibility(View.GONE);
                progress.setVisibility(View.GONE);
                bt_operate.setVisibility(View.GONE);
                tv_tips.setVisibility(View.GONE);
                tv_lar.setVisibility(View.VISIBLE);
                break;
            case finish:
                setVisibility(View.GONE);
                break;
        }
    }


    public void setOnReloadListener(onReloadListener onReloadListener) {
        this.onReloadListener = onReloadListener;

    }

    public void setLarListener(onLarListener onLarListener) {
        this.onLarListener = onLarListener;

    }

    /**
     * 重新尝试接口
     */
    public interface onReloadListener {
        void reload();
    }

    public interface onLarListener {
        void lar();
    }
}

