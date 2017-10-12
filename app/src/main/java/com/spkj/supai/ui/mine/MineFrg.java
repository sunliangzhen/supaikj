package com.spkj.supai.ui.mine;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.spkj.supai.R;
import com.spkj.supai.ui.lar.LoginAty;
import com.spkj.supai.ui.lar.LoginWeiAty;
import com.spkj.supai.ui.mine.account.MineAccountAty;
import com.spkj.supai.ui.mine.address.MineAddressAty;
import com.spkj.supai.ui.mine.buy.MineBuyAty;
import com.spkj.supai.ui.mine.collection.MineCollectionAty;
import com.spkj.supai.ui.mine.company.MineCompanyAty;
import com.spkj.supai.ui.mine.cp.CpDetailsFrg;
import com.spkj.supai.ui.mine.cp.MineCpAty;
import com.spkj.supai.ui.mine.entrust.MineEntrustAty;
import com.spkj.supai.ui.mine.follow.MineFollowAty;
import com.spkj.supai.ui.mine.msg.MineMsgAty;
import com.spkj.supai.ui.mine.set.MineSetAty;
import com.spkj.supai.utils.ImagesUtils;
import com.toocms.dink5.mylibrary.app.Config;
import com.toocms.dink5.mylibrary.base.BaseFragment;
import com.toocms.dink5.mylibrary.commonutils.PreferencesUtils;
import com.toocms.dink5.mylibrary.view.MyRefreshAndLoadListen;

import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

/**
 * Created by aa on 2017/6/21.
 */

public class MineFrg extends BaseFragment implements MyRefreshAndLoadListen {

    @ViewInject(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeLayout;
    @ViewInject(R.id.tv_mine_login)
    TextView tv_mine_login;
    @ViewInject(R.id.mine_tv_name)
    TextView tv_name;
    @ViewInject(R.id.relay_head)
    RelativeLayout relay_head;
    @ViewInject(R.id.mine_imgv_head)
    ImageView imgv_head;

    @Override
    protected int getLayoutResource() {
        return R.layout.frg_mine;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    protected void initView() {

    }

    @Override
    public void onResume() {
        super.onResume();
        if (Config.isLogin()) {
            tv_name.setText(PreferencesUtils.getString(getActivity(), "nickName"));
            tv_mine_login.setVisibility(View.GONE);
            relay_head.setVisibility(View.VISIBLE);
            if (!TextUtils.isEmpty(PreferencesUtils.getString(getActivity(), "headImage")) && PreferencesUtils.getString(getActivity(), "headImage").contains("http")) {
                ImagesUtils.disImgCircle(getActivity(), PreferencesUtils.getString(getActivity(), "headImage"), imgv_head);
            }
        } else {
            tv_mine_login.setVisibility(View.VISIBLE);
            relay_head.setVisibility(View.GONE);
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @Override
    public void onPause() {
        super.onPause();
        swipeLayout.setRefreshing(false);
    }

    @Override
    public void refreshStart() {

    }

    @Override
    public void loadMoreStart() {

    }

    @Event(value = {R.id.relay_account, R.id.relay_canpai, R.id.relay_buy, R.id.relay_head, R.id.relay_set, R.id.relay_address,
            R.id.relay_company, R.id.relay_wt, R.id.relay_yz, R.id.relay_msg, R.id.tv_collection, R.id.tv_collection_num,
            R.id.tv_follow, R.id.tv_follow_num, R.id.tv_mine_login})
    private void onTestBaidulClick(View view) {
        switch (view.getId()) {
            case R.id.relay_account:
                startActivity(MineAccountAty.class);
                break;
            case R.id.relay_canpai:
                startActivity(MineCpAty.class);
                break;
            case R.id.relay_buy:
                startActivity(MineBuyAty.class);
                break;
            case R.id.relay_head:
                startActivity(MineSetAty.class);
                break;
            case R.id.relay_set:
                startActivity(MineSetAty.class);
                break;
            case R.id.relay_address:
                startActivity(MineAddressAty.class);
                break;
            case R.id.relay_company:
                startActivity(MineCompanyAty.class);
                break;
            case R.id.relay_msg:
                startActivity(MineMsgAty.class);
                break;
            case R.id.relay_wt:
                Bundle bundle = new Bundle();
                bundle.putString("type", "wt");
                startActivity(MineEntrustAty.class, bundle);
                break;
            case R.id.relay_yz:
                Bundle bundle2 = new Bundle();
                bundle2.putString("type", "yz");
                startActivity(MineEntrustAty.class, bundle2);
                break;
            case R.id.tv_collection:
                startActivity(MineCollectionAty.class);
                break;
            case R.id.tv_collection_num:
                startActivity(MineCollectionAty.class);
                break;
            case R.id.tv_follow:
                startActivity(MineFollowAty.class);
                break;
            case R.id.tv_follow_num:
                startActivity(MineFollowAty.class);
                break;
            case R.id.tv_mine_login:
                startActivity(LoginAty.class);
                break;
        }
    }

}
