package com.spkj.supai.ui.mine.set;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.spkj.supai.R;
import com.spkj.supai.app.BasAty;

import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

/**
 * Created by aa on 2017/7/5.
 */

public class ResetNicknameAty extends BasAty {


    @ViewInject(R.id.tv_title)
    private TextView tv_title;
    @ViewInject(R.id.tv_right)
    private TextView tv_right;
    @ViewInject(R.id.et_name)
    private EditText et_name;

    private String name;

    @Override
    public int getLayoutId() {
        return R.layout.aty_renickname;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView() {
        name = getIntent().getStringExtra("nickname");
    }

    @Override
    public void requestData() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tv_title.setText("用户名");
        tv_right.setText("保存");
        tv_right.setVisibility(View.VISIBLE);
        et_name.setText(name);
    }

    @Event(value = {R.id.relay_back, R.id.tv_right, R.id.iv_close})
    private void onTestBaidulClick(View view) {
        switch (view.getId()) {
            case R.id.relay_back:
                finish();
                break;
            case R.id.tv_right:
                if (TextUtils.isEmpty(et_name.getText().toString())) {
                    showLongToast("用户名不能为空");
                    return;
                }
                finish();
                break;
            case R.id.iv_close:
                et_name.setText("");
                break;
        }
    }

}
