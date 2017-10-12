package com.toocms.dink5.mylibrary.base;


import android.annotation.TargetApi;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.LayoutInflaterCompat;
import android.support.v4.view.ViewCompat;
import android.transition.Transition;
import android.view.View;
import android.view.Window;

import com.toocms.dink5.mylibrary.R;
import com.toocms.dink5.mylibrary.app.AppManager;
import com.toocms.dink5.mylibrary.app.WeApplication;
import com.toocms.dink5.mylibrary.baserx.RxManager;
import com.toocms.dink5.mylibrary.commonutils.TUtil;
import com.toocms.dink5.mylibrary.commonutils.ToastUitl;
import com.toocms.dink5.mylibrary.commonwidget.LoadingDialog;
import com.toocms.dink5.mylibrary.commonwidget.StatusBarCompat;
import com.toocms.dink5.mylibrary.font.CustomFontCompatDelegate;
import com.toocms.dink5.mylibrary.net.ApiListener;
import com.zhy.autolayout.AutoLayoutActivity;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


public abstract class BaseActivity<T extends BasePresenter, E extends BaseModel> extends AutoLayoutActivity implements ApiListener {
    public T mPresenter;
    public E mModel;
    public Context mContext;
    public RxManager mRxManager;
    protected WeApplication application;
    private boolean isTwoBack;
    protected boolean hasAnimiation = true;

    private CustomFontCompatDelegate compatDelegate;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        LayoutInflaterCompat.setFactory(getLayoutInflater(), getLayoutFactory());
        super.onCreate(savedInstanceState);
        mRxManager = new RxManager();
        doBeforeSetcontentView();
        setContentView(getLayoutId());
        x.view().inject(this);
        mContext = this;
        mPresenter = TUtil.getT(this, 0);
        mModel = TUtil.getT(this, 1);
        if (mPresenter != null) {
            mPresenter.mContext = this;
        }
        this.application = (WeApplication) this.getApplication();
        this.initPresenter();
        this.initView();
        requestData();
    }

    public CustomFontCompatDelegate getLayoutFactory() {
        if (compatDelegate == null) {
            compatDelegate = new CustomFontCompatDelegate();
        }
        return compatDelegate;
    }

    private void doBeforeSetcontentView() {
        initTheme();
        AppManager.getInstance().addActivity(this);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
//        mSetStatusBarColor();
        SetStatusBarColor();
    }

    public abstract int getLayoutId();

    public abstract void initPresenter();

    public abstract void initView();

    public abstract void requestData();

    public void setBackTwo(boolean isTwoBack) {
        this.isTwoBack = isTwoBack;
    }

    private void initTheme() {
//        ChangeModeController.setTheme(this, R.style.DayTheme, R.style.NightTheme);
    }

    /**
     * 开启浮动加载进度条
     */
    public void startProgressDialog() {
        LoadingDialog.showDialogForLoading(this);
    }

    /**
     * 停止浮动加载进度条
     */
    public void stopProgressDialog() {
        LoadingDialog.cancelDialogForLoading();
    }

    /**
     * 着色状态栏（4.4以上系统有效）
     */
    public void SetStatusBarColor() {
        String system = ObtainMobile.getSystem();
        if (system.equals(ObtainMobile.SYS_FLYME)) {
            StatusBarCompat.setStatusBarColor(this, ContextCompat.getColor(this, R.color.white));
        } else if (system.equals(ObtainMobile.SYS_MIUI)) {
            StatusBarCompat.setStatusBarColor(this, ContextCompat.getColor(this, R.color.white));
        } else if (system.equals(ObtainMobile.SYS_EMUI)) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                StatusBarCompat.setStatusBarColor(this, ContextCompat.getColor(this, R.color.white));
            } else {
                StatusBarCompat.setStatusBarColor(this, ContextCompat.getColor(this, R.color.gray));
            }
        } else {
            StatusBarCompat.setStatusBarColor(this, ContextCompat.getColor(this, R.color.gray));
        }
    }

    public void mSetStatusBarColor() {
        StatusBarCompat.setStatusBarColor(this, ContextCompat.getColor(this, R.color.status_main));
    }

    /**
     * 着色状态栏（4.4以上系统有效）
     */
    protected void SetStatusBarColor(int color) {
        StatusBarCompat.setStatusBarColor(this, color);
    }

    /**
     * 沉浸状态栏（4.4以上系统有效）
     */
    public void SetTranslanteBar() {
        StatusBarCompat.translucentStatusBar(this);
    }


    public void startActivity(Class<?> cls) {
        startActivity(cls, null);
    }

    public void startActivityForResult(Class<?> cls, int requestCode) {
        startActivityForResult(cls, null, requestCode);
    }

    public void startActivityForResult(Class<?> cls, Bundle bundle,
                                       int requestCode) {
        Intent intent = new Intent();
        intent.setClass(this, cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivityForResult(intent, requestCode);
    }

    public void startActivity(Class<?> cls, Bundle bundle) {
        Intent intent = new Intent();
        intent.setClass(this, cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    public void startActivityUserActivityOptions(Class<?> cls, Bundle bundle, View view, String name) {
        Intent intent = new Intent();
        intent.setClass(this, cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ActivityOptions options = ActivityOptions
                    .makeSceneTransitionAnimation(this, view, name);
            startActivity(intent, options.toBundle());
        } else {
            //让新的Activity从一个小的范围扩大到全屏
            ActivityOptionsCompat options = ActivityOptionsCompat
                    .makeScaleUpAnimation(view, view.getWidth() / 2, view.getHeight() / 2, 0, 0);
            ActivityCompat.startActivity(this, intent, options.toBundle());
        }
    }


    public void initTransition(View view, String name) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            postponeEnterTransition();
            ViewCompat.setTransitionName(view, name);
            addTransitionListener();
            startPostponedEnterTransition();
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private boolean addTransitionListener() {
        final Transition transition = getWindow().getSharedElementEnterTransition();
        if (transition != null) {
            transition.addListener(new OnTransitionListener() {
                @Override
                public void onTransitionEnd(Transition transition) {
                    transition.removeListener(this);
                }
            });
            return true;
        }
        return false;
    }

    public void showShortToast(String text) {
        ToastUitl.showShort(text);
    }

    public void showShortToast(int resId) {
        ToastUitl.showShort(resId);
    }

    public void showLongToast(int resId) {
        ToastUitl.showLong(resId);
    }

    public void showLongToast(String text) {
        ToastUitl.showLong(text);
    }

    public void showNetError() {
        ToastUitl.showShort(R.string.net_error);
    }

    /**
     * 带图片的toast
     *
     * @param text
     * @param res
     */
    public void showToastWithImg(String text, int res) {
        ToastUitl.showToastWithImg(text, res);
    }

    /**
     * 网络访问错误提醒
     */
    public void showNetErrorTip() {
        ToastUitl.showToastWithImg(getText(R.string.net_error).toString(), R.drawable.ic_wifi_off);
    }

    public void showNetErrorTip(String error) {
        ToastUitl.showToastWithImg(error, R.drawable.ic_wifi_off);
    }

    private long firstTime;

    @Override
    public void onBackPressed() {
        if (isTwoBack) {
            if (System.currentTimeMillis() - firstTime < 3000) {
                hasAnimiation = false;
                AppManager.getInstance().AppExit(this);
            } else {
                firstTime = System.currentTimeMillis();
                showShortToast("再按一次返回桌面");
            }
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public void finish() {
        super.finish();
        if (hasAnimiation) {
            overridePendingTransition(R.anim.slide_left_in, R.anim.slide_right_out);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null)
            mPresenter.onDestroy();
        mRxManager.clear();
    }

    public void onCancelled(Callback.CancelledException var1) {
    }

    @Override
    public void onComplete(RequestParams var1, String var2, String type) {

    }

    @Override
    public void onError(Map<String, String> error, RequestParams params) {
        this.showShortToast(error.get("message"));
    }

    @Override
    public void onExceptionType(Throwable var1, RequestParams params, String type) {

    }

    protected BaseFragment currentFragment;
    private List<BaseFragment> fragments;


    public void addFragment(Class<?> cls, Object data) {
        FragmentParam param = new FragmentParam();
        param.cls = cls;
        param.data = data;
        param.addToBackStack = false;
        this.processFragement(param);
    }

    private void processFragement(FragmentParam param) {
        int containerId = this.getFragmentContainerId();
        Class cls = param.cls;
        if (cls != null) {
            try {
                String e = this.getFragmentTag(param);
                BaseFragment fragment = (BaseFragment) this.getSupportFragmentManager().findFragmentByTag(e);
                if (fragment == null) {
                    fragment = (BaseFragment) cls.newInstance();
                }
//                fragment.onComeIn(param.data);
                if (this.currentFragment != null) {
//                    this.currentFragment.onLeave();
                }

                if (this.fragments == null) {
                    this.fragments = new ArrayList();
                }
                addDistinctEntry(this.fragments, fragment);
                FragmentTransaction ft = this.getSupportFragmentManager().beginTransaction();
                if (param.type != FragmentParam.TYPE.ADD) {
                    ft.replace(containerId, fragment, e);
                } else if (!fragment.isAdded()) {
                    ft.add(containerId, fragment, e);
                } else {
                    Iterator var7 = this.fragments.iterator();
                    while (var7.hasNext()) {
                        BaseFragment lastFragment = (BaseFragment) var7.next();
                        ft.hide(lastFragment);
                    }
                    if (this.currentFragment != null) {
                        this.currentFragment.onPause();
                    }
                    ft.show(fragment);

                    fragment.onResume();
                }
                this.currentFragment = fragment;
                if (param.addToBackStack) {
                    ft.addToBackStack(e);
                }

                ft.commitAllowingStateLoss();
            } catch (InstantiationException var9) {
                var9.printStackTrace();
            } catch (IllegalAccessException var10) {
                var10.printStackTrace();
            }

        }
    }

    public static <V> boolean addDistinctEntry(List<V> sourceList, V entry) {
        return (sourceList != null && !sourceList.contains(entry)) && sourceList.add(entry);
    }

    protected int getFragmentContainerId() {
        return 0;
    }

    protected String getFragmentTag(FragmentParam param) {
        StringBuilder sb = new StringBuilder(param.cls.toString());
        return sb.toString();
    }

}
