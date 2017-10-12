package com.spkj.supai.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;

import com.zhy.autolayout.utils.AutoUtils;


/**
 * Created by Administrator on 2016/8/25.
 */
public class CycleView extends View {

    Paint mPaint;
    int progress = 100;
    int start_degree = 90;

    public CycleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        InitResources(context, attrs);

    }

    int background_int;
    int progress_int;
    float layout_width_float;
    int textColor_int;
    float textSize_float;
    int max_int;
    Drawable thumb_double;
    int thumbSize_int;

    private int w;
    private int h;

    private int h_w;
    private int h_ww;

    private void InitResources(Context context, AttributeSet attrs) {
        TypedArray mTypedArray = context.obtainStyledAttributes(attrs, com.toocms.dink5.mylibrary.R.styleable.windows2);

        background_int = mTypedArray.getColor(com.toocms.dink5.mylibrary.R.styleable.windows2_background2, 0xFF87cfe8);
        progress_int = mTypedArray.getColor(com.toocms.dink5.mylibrary.R.styleable.windows2_progressDrawable2, 0xFFabd800);
        layout_width_float = mTypedArray.getDimension(com.toocms.dink5.mylibrary.R.styleable.windows2_layout_width2, 7);
        textColor_int = mTypedArray.getColor(com.toocms.dink5.mylibrary.R.styleable.windows2_textColor2, 0x00ada1de);
        textSize_float = mTypedArray.getDimension(com.toocms.dink5.mylibrary.R.styleable.windows2_textSize2, 50);
        max_int = mTypedArray.getInt(com.toocms.dink5.mylibrary.R.styleable.windows2_max2, 100);
        progress = mTypedArray.getInt(com.toocms.dink5.mylibrary.R.styleable.windows2_progress2, 100);
        thumb_double = mTypedArray.getDrawable(com.toocms.dink5.mylibrary.R.styleable.windows2_thumb2);
        thumbSize_int = mTypedArray.getInt(com.toocms.dink5.mylibrary.R.styleable.windows2_thumbSize2, 30);
        mTypedArray.recycle();

        if (thumb_double == null) {
            Bitmap bitmap = Bitmap.createBitmap(thumbSize_int, thumbSize_int, Bitmap.Config.ARGB_8888);
            // 图片画片
            Canvas cas = new Canvas(bitmap);
            Paint paint = new Paint();
            paint.setStyle(Paint.Style.FILL);
            paint.setColor(0xFF68ba32);
            int center = thumbSize_int / 2;
            int radius = center - 4;
            cas.drawCircle(center, center, radius, paint);
//            thumb_double = new BitmapDrawable(getResources(), bitmap);
        }
        w = AutoUtils.getPercentHeightSize(220);
        h_w = AutoUtils.getPercentHeightSize(10);
        h_ww = AutoUtils.getPercentHeightSize(10);
        mPaint = new Paint();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawProgressView(canvas);
    }

    private void drawProgressView(Canvas canvas) {
        InitOval(canvas);
        drawBackground(canvas);
        drawProgress(canvas);
        drawProgressText(canvas);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //设置宽高
        setMeasuredDimension(w, w);
    }

    RectF oval;

    private void InitOval(Canvas canvas) {
//        w = getWidth();
        int center = getWidth() / 2;
        int radius = (int) (center - thumbSize_int / 2);
        // 画布中央减去半径就是左上角
        int left_top = center - radius;
        // 画布中央加上半径就是右下角
        int right_bottom = center + radius;

        if (oval == null) {
//            oval = new RectF(left_top + layout_width_float / 2, left_top + layout_width_float / 2, right_bottom - layout_width_float / 2, right_bottom - layout_width_float / 2);

            oval = new RectF(h_w / 2 + h_ww, h_w / 2 + h_ww,
                    w - h_w - h_ww, w - h_w - h_ww);
//            oval = new RectF(thumbSize_int / 2 + layout_width_float / 2, thumbSize_int / 2 + layout_width_float / 2,
//                    getWidth() - thumbSize_int / 2 - layout_width_float / 2, getHeight() - thumbSize_int / 2 - layout_width_float / 2);
        }
    }

    /**
     * 绘制进度文字
     *
     * @param canvas
     */
    private void drawProgressText(Canvas canvas) {

        mPaint.setTextSize(textSize_float);
        mPaint.setColor(textColor_int);
        mPaint.setStrokeWidth(2);
        mPaint.setStyle(Paint.Style.FILL);
        String progresstext = progress + "%";
        float text_left = (getWidth() - mPaint.measureText(progresstext)) / 2;
        Paint.FontMetrics fontMetrics = mPaint.getFontMetrics();
        // 绘制文字是底部对齐
        float text_top = (float) ((getHeight() / 2 + Math.ceil(fontMetrics.descent - fontMetrics.ascent) / 2));
        canvas.drawText(progresstext, text_left, text_top, mPaint);

    }

    private void drawProgress(Canvas canvas) {
        // 设置进度
        mPaint.setColor(progress_int);
        mPaint.setStrokeWidth(layout_width_float);
        float seek = 0;
        if (max_int > 0) {
            seek = (float) progress / max_int * 360;
        }
        canvas.drawArc(oval, start_degree, seek, false, mPaint);

        canvas.save();
        int center = w / 2 - h_w / 2 - h_ww;
        int radius = (int) (center - layout_width_float / 2 - thumbSize_int / 2);

        double cycle_round = (seek + start_degree) * Math.PI / 180;
        float x = (float) (Math.cos(cycle_round) * (center) + center - thumbSize_int / 2 + h_ww);
        float y = (float) (Math.sin(cycle_round) * (center) + center - thumbSize_int / 2 + h_ww);
        canvas.translate(x, y);
        if(thumb_double!=null){
            thumb_double.setBounds(0, 0, thumbSize_int, thumbSize_int);
            thumb_double.draw(canvas);
        }
        canvas.restore();
    }

    private void drawBackground(Canvas canvas) {

        mPaint.setStrokeWidth(layout_width_float);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setAntiAlias(true);
        // 设置背景
        mPaint.setColor(background_int);
        canvas.drawArc(oval, start_degree, 360, false, mPaint);

    }

    /**
     * 查看Seekbar源码
     *
     * @param progress
     */
    public synchronized void setProgress(int progress) {
        if (progress > max_int) {
            progress = max_int;
        }
        this.progress = progress;
        postInvalidate();
    }

    public int getProgress() {
        return this.progress;
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (thumb_double != null) {
            thumb_double.setCallback(null);
            thumb_double = null;
        }
    }

}
