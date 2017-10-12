package com.spkj.supai.ui.order;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.spkj.supai.R;
import com.spkj.supai.app.Constant;
import com.zhy.autolayout.utils.AutoUtils;

import org.xutils.x;


/**
 * 首页RecyclerView的Adapter
 */
public class TotalRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private LayoutInflater inflater;

    public TotalRecyclerAdapter(Context context) {
        this.mContext = context;
        inflater = LayoutInflater.from(mContext);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == Constant.daizhifu) {   //待支付
            View view = inflater.inflate(R.layout.item_daifukuan, parent, false);
            return new DaiFuKuanViewHolder(view, mItemClickListener);
        }
        return null;
    }

    /**
     * 设置Item点击监听
     *
     * @param listener
     */
    public void setOnItemClickListener(MyItemClickListener listener) {
        this.mItemClickListener = listener;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        switch (getItemViewType(position)) {
            case Constant.daizhifu:
                break;
            default:
                break;
        }

    }


    @Override
    public int getItemViewType(int position) {
//        if (TextUtils.equals(mDatas.get(position).getStatus(), "0")) {
//            return Constant.daizhifu;
//        } else if (TextUtils.equals(mDatas.get(position).getStatus(), "3")) {
//            return Constant.daishiyong;
//        } else if (TextUtils.equals(mDatas.get(position).getStatus(), "1")) {
//            return Constant.daifahuo;
//        } else if (TextUtils.equals(mDatas.get(position).getStatus(), "2")) {
//            return Constant.daishouhuo;
//        } else if (TextUtils.equals(mDatas.get(position).getStatus(), "4")) {
//            return Constant.yiwancheng;
//        } else if (TextUtils.equals(mDatas.get(position).getStatus(), "5")) {
//            return Constant.yishixiao;
//        } else if (TextUtils.equals(mDatas.get(position).getStatus(), "7")) {
//            return Constant.jingpaizhong;
//        }else if (TextUtils.equals(mDatas.get(position).getStatus(), "8")) {
//            return Constant.zhifuzhong;
//        }
        return Constant.daizhifu;
    }

    @Override
    public int getItemCount() {
//        return mDatas.size();
        return 10;
    }


    public interface MyItemClickListener {
        void onItemClick(View view, int postion);
    }

    private MyItemClickListener mItemClickListener;

    class DaiFuKuanViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public MyItemClickListener mListener;

//        @ViewInject(R.id.tv_pay)
//        public TextView tv_pay;
//        @ViewInject(R.id.daifukuan_iv_1)
//        public ImageView daifukuan_iv_1;
//        @ViewInject(R.id.daifukaun_tv_3)
//        public TextView daifukaun_tv_3;
//        @ViewInject(R.id.daifukuan_tv_1)
//        public TextView daifukuan_tv_1;
//        @ViewInject(R.id.cv_countdownView)
//        public CountdownView cv_countdownView;

        //
        public DaiFuKuanViewHolder(View view, MyItemClickListener listener) {
            super(view);
            x.view().inject(this, itemView);
            AutoUtils.autoSize(itemView);
            this.mListener = listener;
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (mListener != null) {
                mListener.onItemClick(v, getPosition());
            }
        }
    }

}
