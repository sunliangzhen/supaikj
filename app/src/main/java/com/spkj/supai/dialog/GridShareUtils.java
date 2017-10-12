package com.spkj.supai.dialog;

import android.app.ActionBar.LayoutParams;
import android.app.ProgressDialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.TextView;
import android.widget.Toast;

import com.spkj.supai.R;
import com.spkj.supai.view.MyGridView;
import com.toocms.dink5.mylibrary.base.BaseActivity;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;
import com.umeng.socialize.utils.SocializeUtils;

import java.util.ArrayList;

/**
 * 分享工具
 *
 * @author liaiguo
 */
public class GridShareUtils implements OnItemClickListener {

    private static final int THUMB_SIZE = 150;
    private Context mContext;
    // 弹窗view
    private View mShareView;
    // 弹窗
    private PopupWindow mShareWindow;
    private LayoutInflater mInflater;
    private float mAlpha;
    // 分享的标题
    private String mTitle;
    // 分享内容
    private String mContent;
    // 分享图片
    private String mImageUrl;
    // 分享url
    private String mShareUrl;
    private SHARE_MEDIA mShareMedia;

    private MyGridView mGridView;
    private ProgressDialog dialog;
    private ArrayList<String> list;
    private ArrayList<Integer> list_img;
    private TextView tv_cancel;
    private shareListen shareLisen;

    public GridShareUtils(Context context) {
        super();
        this.mContext = context;
        mInflater = LayoutInflater.from(mContext);
        dialog = new ProgressDialog(mContext);
        dialog.setMessage("加载中..");
        initShareWindow();
    }


    /**
     * 初始化弹窗
     */
    private void initShareWindow() {
        list = new ArrayList<>();
        list_img = new ArrayList<>();
        list.add("微信");
        list.add("微信朋友圈");
        list_img.add(R.drawable.weixin2);
        list_img.add(R.drawable.weixin_p);
        mShareView = mInflater.inflate(R.layout.grid_share_layout, null);

        mGridView = (MyGridView) mShareView.findViewById(R.id.share_grid);
        tv_cancel = (TextView) mShareView.findViewById(R.id.tv_cancel);

        mGridView.setNumColumns(2);
        ShareMethodAdapter adapter = new ShareMethodAdapter(mContext, list);
        mGridView.setAdapter(adapter);
        mGridView.setOnItemClickListener(this);


        mShareWindow = new PopupWindow(mShareView, LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);

        mShareWindow.setAnimationStyle(R.style.bottom_up_down_style);
//        ColorDrawable cd = new ColorDrawable(0000);
//        mShareWindow.setBackgroundDrawable(cd);
        mShareWindow.setFocusable(true);
        mShareWindow.setOutsideTouchable(true);
        mShareWindow.setOnDismissListener(new OnDismissListener() {

            @Override
            public void onDismiss() {
                setAlpha(false, 0);
            }
        });
        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mShareWindow != null && mShareWindow.isShowing()) {
                    mShareWindow.dismiss();
                }
            }
        });

    }

    /**
     * 设置要分享的参数
     */
    public void setParams(String title, String content, String imageUrl, String shareUrl) {
        this.mTitle = title;
        this.mContent = content;
        this.mImageUrl = imageUrl;
        this.mShareUrl = shareUrl;
    }

    /**
     * 显示分享弹窗
     */
    public void shareWindow() {
        setAlpha(true, 0.5f);
        mShareWindow.showAtLocation(((BaseActivity) mContext).getWindow().getDecorView(), Gravity.BOTTOM, 0, 0);
    }

    private void setAlpha(boolean before, float alpha) {
        Window window = ((BaseActivity) mContext).getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        if (before) {
            mAlpha = lp.alpha;
            lp.alpha = alpha;
        } else {
            lp.alpha = mAlpha;
        }
        window.setAttributes(lp);
    }

    private void shareToQQ(SHARE_MEDIA type) {


        UMWeb web = new UMWeb(mShareUrl);
        if (!TextUtils.isEmpty(mTitle)) {
            web.setTitle(mTitle);// 标题
        }
        if (!TextUtils.isEmpty(mContent)) {
            web.setDescription(mContent);// 描述
        }
        if (!TextUtils.isEmpty(mImageUrl)) {
            UMImage image = new UMImage(mContext, mImageUrl);// 网络图片
            web.setThumb(image); // 缩略图
        }
        new ShareAction((BaseActivity) mContext)
                .setPlatform(type).withMedia(web).setCallback(shareListener).share();

//        new ShareAction(RequestActivity.this).withText("hello")
////                        .setDisplayList(SHARE_MEDIA.WEIXIN_CIRCLE,SHARE_MEDIA.WEIXIN)
////                        .setCallback(new UMShareListener() {
////                        }).open();

    }

    private UMShareListener shareListener = new UMShareListener() {
        @Override
        public void onStart(SHARE_MEDIA platform) {
            SocializeUtils.safeShowDialog(dialog);
        }

        @Override
        public void onResult(SHARE_MEDIA platform) {
            SocializeUtils.safeCloseDialog(dialog);
            if(shareLisen!=null){
                shareLisen.shareScuess();
            }else{
                Toast.makeText(mContext, "分享成功", Toast.LENGTH_LONG).show();
            }
        }

        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            SocializeUtils.safeCloseDialog(dialog);
//            Toast.makeText(mContext, "分享失败" + t.getMessage(), Toast.LENGTH_LONG).show();
        }

        @Override
        public void onCancel(SHARE_MEDIA platform) {
            SocializeUtils.safeCloseDialog(dialog);
            Toast.makeText(mContext, "分享取消", Toast.LENGTH_LONG).show();
        }
    };

    public void cancel() {
        SocializeUtils.safeCloseDialog(dialog);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long arg3) {
        mShareWindow.dismiss();
        shareByMethod((String) parent.getAdapter().getItem(position));
    }

    private void shareByMethod(String method) {
        if ("微信".equals(method)) {
            mShareMedia = SHARE_MEDIA.WEIXIN;
            shareToQQ(SHARE_MEDIA.WEIXIN);
        } else if ("微信朋友圈".equals(method)) {

            mShareMedia = SHARE_MEDIA.WEIXIN_CIRCLE;
            shareToQQ(SHARE_MEDIA.WEIXIN_CIRCLE);
        } else if ("手机QQ".equals(method)) {

            mShareMedia = SHARE_MEDIA.QQ;
            shareToQQ(SHARE_MEDIA.QQ);
        } else if ("QQ空间".equals(method)) {

            mShareMedia = SHARE_MEDIA.QZONE;
            shareToQQ(SHARE_MEDIA.QZONE);
        } else if ("新浪微博".equals(method)) {

            mShareMedia = SHARE_MEDIA.SINA;
            shareToQQ(SHARE_MEDIA.SINA);
        }
    }

    public class ShareMethodAdapter extends BaseAdapter {

        private Context mContext;
        private ArrayList<String> mShareMethods;
        private LayoutInflater mInflater;

        public ShareMethodAdapter(Context mContext,
                                  ArrayList<String> mShareMethods) {
            super();
            this.mContext = mContext;
            this.mShareMethods = mShareMethods;
            mInflater = LayoutInflater.from(mContext);
        }

        @Override
        public int getCount() {
            return (null == mShareMethods) ? 0 : mShareMethods.size();
        }

        @Override
        public String getItem(int position) {
            return list.get(position);
        }

//		@Override
//		public Object getItem(int position) {
//			return (null == mShareMethods) ? null : mShareMethods.get(position);
//		}

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (null == mShareMethods || mShareMethods.isEmpty()
                    || mShareMethods.size() <= position) {
                return null;
            }

            ViewHolder holder;

            if (null == convertView) {
                holder = new ViewHolder();
                convertView = mInflater.inflate(R.layout.share_method_grid_item, null);

                holder.shareImg = (ImageView) convertView.findViewById(R.id.share_img);
                holder.shareTv = (TextView) convertView.findViewById(R.id.share_tv);

                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            holder.shareImg.setImageResource(list_img.get(position));
            holder.shareTv.setText(list.get(position));
            return convertView;
        }

        private class ViewHolder {
            ImageView shareImg;
            TextView shareTv;
        }
    }

    public void setShareLisen(shareListen shareLisen) {
        this.shareLisen = shareLisen;
    }

    public interface shareListen {
        void shareScuess();
    }
}
