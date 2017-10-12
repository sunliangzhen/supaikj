package com.spkj.supai.ui.mine.set;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.sdk.android.oss.ClientConfiguration;
import com.alibaba.sdk.android.oss.ClientException;
import com.alibaba.sdk.android.oss.OSS;
import com.alibaba.sdk.android.oss.OSSClient;
import com.alibaba.sdk.android.oss.ServiceException;
import com.alibaba.sdk.android.oss.callback.OSSCompletedCallback;
import com.alibaba.sdk.android.oss.callback.OSSProgressCallback;
import com.alibaba.sdk.android.oss.common.auth.OSSCredentialProvider;
import com.alibaba.sdk.android.oss.common.auth.OSSPlainTextAKSKCredentialProvider;
import com.alibaba.sdk.android.oss.internal.OSSAsyncTask;
import com.alibaba.sdk.android.oss.model.PutObjectRequest;
import com.alibaba.sdk.android.oss.model.PutObjectResult;
import com.spkj.supai.R;
import com.spkj.supai.app.BasAty;
import com.spkj.supai.interfaces.Lar;
import com.spkj.supai.utils.ImagesUtils;
import com.spkj.supai.utils.TimeUtil;
import com.toocms.dink5.mylibrary.app.Config;
import com.toocms.dink5.mylibrary.commonutils.JSONUtils;
import com.toocms.dink5.mylibrary.commonutils.PreferencesUtils;
import com.yalantis.ucrop.UCrop;

import org.xutils.http.RequestParams;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.io.File;
import java.util.ArrayList;
import java.util.Map;

import me.nereo.multi_image_selector.MultiImageSelector;

/**
 * Created by aa on 2017/7/3.
 */

public class MineSetAty extends BasAty {

    @ViewInject(R.id.tv_title)
    private TextView tv_title;
    @ViewInject(R.id.tv_name)
    private TextView tv_name;
    @ViewInject(R.id.tv_phone)
    private TextView tv_phone;
    @ViewInject(R.id.imgv_head)
    private ImageView imgv_head;

    private static final int REQUEST_IMAGE = 2;
    private ArrayList<String> mSelectPath;
    private static final String SAMPLE_CROPPED_IMAGE_NAME = "SampleCropImage";

    private Lar lar;
    private String url;

    @Override
    public int getLayoutId() {
        return R.layout.aty_mine_set;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView() {
        lar = new Lar();
    }

    @Override
    public void requestData() {

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!TextUtils.isEmpty(PreferencesUtils.getString(this, "headImage")) && PreferencesUtils.getString(this, "headImage").contains("http")) {
            ImagesUtils.disImgCircle(MineSetAty.this, PreferencesUtils.getString(this, "headImage"), imgv_head);
        }
        tv_name.setText(PreferencesUtils.getString(this, "nickName"));
        tv_phone.setText(PreferencesUtils.getString(this, "phone"));
    }

    @Event(value = {R.id.relay_back, R.id.relay_about, R.id.relay_resetphone, R.id.relay_repass, R.id.tv_outlogin,
            R.id.relay_nickname, R.id.relay_head})
    private void onTestBaidulClick(View view) {
        switch (view.getId()) {
            case R.id.relay_back:
                finish();
                break;
            case R.id.relay_about:
                startActivity(MineAboutAty.class);
                break;
            case R.id.relay_resetphone:
                Bundle bundle = new Bundle();
                bundle.putString("type", "rephone");
                startActivity(ResetPhoneAty.class, bundle);
                break;
            case R.id.relay_repass:
                Bundle bundle2 = new Bundle();
                bundle2.putString("type", "repass");
                startActivity(ResetPhoneAty.class, bundle2);
                break;
            case R.id.tv_outlogin:
                Config.setLoginState(false);
                finish();
                break;
            case R.id.relay_nickname:
                Bundle bundle1 = new Bundle();
                bundle1.putString("nickname", tv_name.getText().toString());
                startActivity(ResetNicknameAty.class, bundle1);
                break;
            case R.id.relay_head:
                pickImage();
                break;
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tv_title.setText("设置");
    }

    private void pickImage() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN // Permission was added in API Level 16
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            requestPermission(Manifest.permission.READ_EXTERNAL_STORAGE,
                    getString(R.string.mis_permission_rationale),
                    101);
        } else {
            int maxNum = 9;
            MultiImageSelector selector = MultiImageSelector.create(this);
            selector.showCamera(true);
            selector.count(maxNum);
            selector.single();
//            selector.multi();
            selector.origin(mSelectPath);
            selector.start(this, REQUEST_IMAGE);
        }
    }

    private void requestPermission(final String permission, String rationale, final int requestCode) {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, permission)) {
            new AlertDialog.Builder(this)
                    .setTitle(R.string.mis_permission_dialog_title)
                    .setMessage(rationale)
                    .setPositiveButton(R.string.mis_permission_dialog_ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions(MineSetAty.this, new String[]{permission}, requestCode);
                        }
                    })
                    .setNegativeButton(R.string.mis_permission_dialog_cancel, null)
                    .create().show();
        } else {
            ActivityCompat.requestPermissions(this, new String[]{permission}, requestCode);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 101) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                pickImage();
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if ((resultCode == RESULT_OK)) {
            if (requestCode == REQUEST_IMAGE) {
                mSelectPath = data.getStringArrayListExtra(MultiImageSelector.EXTRA_RESULT);
                Uri uri = Uri.fromFile(new File(mSelectPath.get(0)));
                startCropActivity(uri);
            } else if (requestCode == UCrop.REQUEST_CROP) {
                Uri resultUri = UCrop.getOutput(data);
                String path = new File(resultUri.getPath()).getAbsolutePath();
                startProgressDialog();
                a(path);
            }

        }
    }

    private void startCropActivity(@NonNull Uri uri) {
        String destinationFileName = SAMPLE_CROPPED_IMAGE_NAME + System.currentTimeMillis();
        destinationFileName += ".jpg";
        UCrop uCrop = UCrop.of(uri, Uri.fromFile(new File(getCacheDir(), destinationFileName)));
        uCrop = uCrop.withAspectRatio(1, 1);

        UCrop.Options options = new UCrop.Options();
        options.setCompressionFormat(Bitmap.CompressFormat.JPEG);
        options.setCompressionQuality(100);
        options.setHideBottomControls(true);
        options.setFreeStyleCropEnabled(true);
        uCrop.start(this);
    }

    private String objectname;

    public void a(String path) {
        final String endpoint = "http://oss-cn-beijing.aliyuncs.com";
        final String startpoint = "http://wanpai-image.oss-cn-beijing.aliyuncs.com/";
        final String buket = "wanpai-image";
        ClientConfiguration conf = new ClientConfiguration();
        conf.setConnectionTimeout(15 * 1000); // 连接超时，默认15秒
        conf.setSocketTimeout(15 * 1000); // socket超时，默认15秒
        conf.setMaxConcurrentRequest(5); // 最大并发请求书，默认5个
        conf.setMaxErrorRetry(2); // 失败后最大重试次数，默认2次
        OSSCredentialProvider credentialProvider = new OSSPlainTextAKSKCredentialProvider("LTAIdSc43VIQsgC6", "EiciYLcWV4AdE8V58FxmTedctHdh5B");
        OSS oss = new OSSClient(getApplicationContext(), endpoint, credentialProvider, conf);
        objectname = TimeUtil.getTimeMis() + "";
        url = startpoint + objectname;
        PutObjectRequest put = new PutObjectRequest(buket, objectname, path);
        put.setProgressCallback(new OSSProgressCallback<PutObjectRequest>() {
            @Override
            public void onProgress(PutObjectRequest request, long currentSize, long totalSize) {
            }
        });
        OSSAsyncTask task = oss.asyncPutObject(put, new OSSCompletedCallback<PutObjectRequest, PutObjectResult>() {
            @Override
            public void onSuccess(PutObjectRequest request, PutObjectResult result) {
                lar.updateUser(MineSetAty.this, "headImage", url, MineSetAty.this);
            }

            @Override
            public void onFailure(PutObjectRequest request, ClientException clientExcepion, ServiceException serviceException) {
                if (clientExcepion != null) {
                    clientExcepion.printStackTrace();
                }
                if (serviceException != null) {
                }
            }
        });
    }

    @Override
    public void onComplete(RequestParams var1, String var2, String type) {
        super.onComplete(var1, var2, type);
        stopProgressDialog();
        if (type.equals("user.info.update")) {
            Map<String, String> map = JSONUtils.parseKeyAndValueToMap(var2);
            if (map.get("responseCode").equals("0")) {
                PreferencesUtils.putString(this, "headImage", url);
                ImagesUtils.disImgCircle(MineSetAty.this, url, imgv_head);
            }

        }
    }
}

