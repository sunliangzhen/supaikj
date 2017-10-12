package com.spkj.supai;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ImageView;

import com.spkj.supai.app.BasAty;
import com.spkj.supai.dialog.GridShareUtils;
import com.spkj.supai.ui.MainAty;
import com.spkj.supai.ui.lar.LoginAty;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.utils.SocializeUtils;

import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MainActivity extends BasAty implements GridShareUtils.shareListen {


    @ViewInject(R.id.imgv)
    public ImageView imgv;

    private ProgressDialog dialog;

    private static final int REQUEST_IMAGE = 2;
    private ArrayList<String> mSelectPath;

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView() {
        dialog = new ProgressDialog(this);
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
    }

    @Override
    public void requestData() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Event(value = {R.id.btn_share, R.id.btn_login, R.id.btn_photo, R.id.btn_home, R.id.btn_logins})
    private void onTestBaidulClick(View view) {

        switch (view.getId()) {
            case R.id.btn_logins:
                startActivity(LoginAty.class);
                break;
            case R.id.btn_home:
                startActivity(MainAty.class);
                break;
            case R.id.btn_photo:
//                pickImage();
                break;
            case R.id.btn_share:
                GridShareUtils gridShareUtils = new GridShareUtils(this);
                gridShareUtils.setShareLisen(this);
                String url_share = "http://blog.csdn.net/mabeijianxi/article/details/63335722";
                gridShareUtils.setParams("111", "“玩好”是种生活艺术，“玩拍”是种生活态度", "", url_share);
                gridShareUtils.shareWindow();
                break;
            case R.id.btn_login:
                if (isAvilible("com.tencent.mm")) {
//                    dialog.setMessage("正在进行微信登录");
//                    SocializeUtils.safeShowDialog(dialog);
                    UMShareAPI.get(this).getPlatformInfo(this, SHARE_MEDIA.WEIXIN, authListener);
                } else {
                    showShortToast("未安装微信");
                }
                break;
        }
    }

//    private void pickImage() {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN // Permission was added in API Level 16
//                && ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
//                != PackageManager.PERMISSION_GRANTED) {
//            requestPermission(Manifest.permission.READ_EXTERNAL_STORAGE,
//                    getString(R.string.mis_permission_rationale),
//                    101);
//        } else {
//            int maxNum = 9;
//            MultiImageSelector selector = MultiImageSelector.create(MainActivity.this);
//            selector.showCamera(true);
//            selector.count(maxNum);
//            selector.single();
////            selector.multi();
//            selector.origin(mSelectPath);
//            selector.start(MainActivity.this, REQUEST_IMAGE);
//        }
//    }

//    private void requestPermission(final String permission, String rationale, final int requestCode) {
//        if (ActivityCompat.shouldShowRequestPermissionRationale(this, permission)) {
//            new AlertDialog.Builder(this)
//                    .setTitle(R.string.mis_permission_dialog_title)
//                    .setMessage(rationale)
//                    .setPositiveButton(R.string.mis_permission_dialog_ok, new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            ActivityCompat.requestPermissions(MainActivity.this, new String[]{permission}, requestCode);
//                        }
//                    })
//                    .setNegativeButton(R.string.mis_permission_dialog_cancel, null)
//                    .create().show();
//        } else {
//            ActivityCompat.requestPermissions(this, new String[]{permission}, requestCode);
//        }
//    }

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
    public void shareScuess() {
    }


    public boolean isAvilible(String packageName) {
        //获取packagemanager
        PackageManager packageManager = getPackageManager();
        //获取所有已安装程序的包信息
        List<PackageInfo> packageInfos = packageManager.getInstalledPackages(0);
        //用于存储所有已安装程序的包名
        List<String> packageNames = new ArrayList<String>();
        //从pinfo中将包名字逐一取出，压入pName list中
        if (packageInfos != null) {
            for (int i = 0; i < packageInfos.size(); i++) {
                String packName = packageInfos.get(i).packageName;
                packageNames.add(packName);
            }
        }
        //判断packageNames中是否有目标程序的包名，有TRUE，没有FALSE
        return packageNames.contains(packageName);
    }

    UMAuthListener authListener = new UMAuthListener() {
        @Override
        public void onStart(SHARE_MEDIA platform) {
            dialog.setMessage("正在进行微信登录");
            SocializeUtils.safeShowDialog(dialog);
        }

        @Override
        public void onComplete(SHARE_MEDIA platform, int action, Map<String, String> data) {
            dialog.setMessage("验证手机号");
            UMShareAPI.get(getApplicationContext()).deleteOauth(MainActivity.this, SHARE_MEDIA.WEIXIN, null);
            String temp = "";
            for (String key : data.keySet()) {
                temp = temp + key + " : " + data.get(key) + "\n";
            }
//            WeiXinLogin(data);
        }

        @Override
        public void onError(SHARE_MEDIA platform, int action, Throwable t) {
            SocializeUtils.safeCloseDialog(dialog);
        }

        @Override
        public void onCancel(SHARE_MEDIA platform, int action) {
            SocializeUtils.safeCloseDialog(dialog);
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
//        if ((resultCode == RESULT_OK)) {
//            if (requestCode == REQUEST_IMAGE) {
//                mSelectPath = data.getStringArrayListExtra(MultiImageSelector.EXTRA_RESULT);
//                Uri uri = Uri.fromFile(new File(mSelectPath.get(0)));
//                startCropActivity(uri);
//            } else if (requestCode == UCrop.REQUEST_CROP) {
//                Uri resultUri = UCrop.getOutput(data);
//                String path = new File(resultUri.getPath()).getAbsolutePath();
//                ImagesUtils.disImg(this, path, imgv);
//            }
//
//        }
    }

    private static final String SAMPLE_CROPPED_IMAGE_NAME = "SampleCropImage";

//    private void startCropActivity(@NonNull Uri uri) {
//        String destinationFileName = SAMPLE_CROPPED_IMAGE_NAME + System.currentTimeMillis();
//        destinationFileName += ".jpg";
//        UCrop uCrop = UCrop.of(uri, Uri.fromFile(new File(getCacheDir(), destinationFileName)));
//        uCrop = uCrop.withAspectRatio(1, 1);
//
//        UCrop.Options options = new UCrop.Options();
//        options.setCompressionFormat(Bitmap.CompressFormat.JPEG);
//        options.setCompressionQuality(100);
//        options.setHideBottomControls(true);
//        options.setFreeStyleCropEnabled(true);
//        uCrop.start(this);
//    }
}
