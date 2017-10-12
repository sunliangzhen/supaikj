package com.toocms.dink5.mylibrary.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import com.lcodecore.tkrefreshlayout.Footer.LoadingView;
import com.lcodecore.tkrefreshlayout.IBottomView;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.lcodecore.tkrefreshlayout.header.SinaRefreshView;
import com.toocms.dink5.mylibrary.R;

/**
 * Created by pc on 2017/3/3.
 */

public class MyTwinklingRefreshLayout extends TwinklingRefreshLayout {


    private MyRefreshAndLoadListen refreshAndLoadListen;

    public MyTwinklingRefreshLayout(Context context) {
        super(context);
        init(context);
    }

    public MyTwinklingRefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public MyTwinklingRefreshLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public void setMyRefreshAndLoadListen(MyRefreshAndLoadListen refreshAndLoadListen) {
        this.refreshAndLoadListen = refreshAndLoadListen;
    }

    public void init(Context context) {
        setWaveHeight(100);
        setHeaderHeight(70);
        setBottomView(new IBottomView() {
            @Override
            public View getView() {
                View rootView = View.inflate(getContext(), R.layout.load_foot, null);
                return rootView;
            }

            @Override
            public void onPullingUp(float fraction, float maxHeadHeight, float headHeight) {

            }

            @Override
            public void startAnim(float maxHeadHeight, float headHeight) {

            }

            @Override
            public void onPullReleasing(float fraction, float maxHeadHeight, float headHeight) {

            }

            @Override
            public void onFinish() {

            }

            @Override
            public void reset() {

            }
        });
        setBottomView(new LoadingView(context));
        setHeaderView(new SinaRefreshView(context));
        setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(final TwinklingRefreshLayout refreshLayout) {
                if (refreshAndLoadListen != null) {
                    refreshAndLoadListen.refreshStart();
                }
            }

            @Override
            public void onLoadMore(final TwinklingRefreshLayout refreshLayout) {
                if (refreshAndLoadListen != null) {
                    refreshAndLoadListen.loadMoreStart();
                }
            }
        });
    }
}
