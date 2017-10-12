package com.spkj.supai.view;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.RelativeLayout;

import com.spkj.supai.R;
import com.zhy.autolayout.config.AutoLayoutConifg;
import com.zhy.autolayout.utils.AutoUtils;

import org.xutils.common.util.LogUtil;

import java.util.ArrayList;

import static android.R.attr.mode;


/**
 * Created by ROYGEM-0830-1 on 2017/9/28.
 */

public class LineRelayout extends RelativeLayout implements LinerView.LinermYmovelisten {

    private LinerView linerview_my;
    private RelativeLayout relay_my;
    private RelativeLayout relay_linerout;
    private View view_my;
    private View view_myWrite;
    private View view_lineY;

    public LineRelayout(Context context) {
        super(context);
        initView(context);
    }

    public LineRelayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public LineRelayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    public LineRelayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView(context);
    }

    private int mMeasuredWidth;
    private int view_myWidth;
    private int view_myWriteWidth;
    private int mMeasuredHeight;

    private void initView(Context context) {
        View.inflate(context, R.layout.linerlayout, this);
        linerview_my = (LinerView) findViewById(R.id.linerview_my);
        relay_my = (RelativeLayout) findViewById(R.id.relay_my);
        relay_linerout = (RelativeLayout) findViewById(R.id.relay_linerout);
        view_my = findViewById(R.id.view_my);
        view_myWrite = findViewById(R.id.view_myWrite);
        view_lineY = findViewById(R.id.view_lineY);

        int w = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        int h = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        relay_my.measure(w, h);//测量控件的宽高
        mMeasuredWidth = relay_my.getMeasuredWidth();
        mMeasuredHeight = relay_my.getMeasuredHeight();


        RelativeLayout.LayoutParams linearParams = (RelativeLayout.LayoutParams) view_my.getLayoutParams();
        linearParams.height = AutoUtils.getPercentHeightSize(50);
        linearParams.width = AutoUtils.getPercentHeightSize(50);
        view_my.setLayoutParams(linearParams);
        view_myWidth = AutoUtils.getPercentHeightSize(50);

        RelativeLayout.LayoutParams linearParams2 = (RelativeLayout.LayoutParams) view_myWrite.getLayoutParams();
        linearParams2.height = AutoUtils.getPercentHeightSize(30);
        linearParams2.width = AutoUtils.getPercentHeightSize(30);
        view_myWrite.setLayoutParams(linearParams2);
        view_myWriteWidth = AutoUtils.getPercentHeightSize(30);


        matrix = new Matrix();
        savedMatrix = new Matrix();

//        matrix.setTranslate(0f, 0f);
//        setScaleType(ImageView.ScaleType.MATRIX);
//        setImageMatrix(matrix);

        startPoint = new PointF();
        middlePoint = new PointF();

        oldDistance = 1f;

    }

    public void setData(ArrayList<Float> list, ArrayList<String> list2) {
        linerview_my.setData(list, list2, relay_my, view_myWrite, view_myWidth / 2, view_lineY, view_my, relay_my, view_myWriteWidth, mMeasuredWidth);
        linerview_my.setLinermYmovelisten(this);
    }

    public void setmYdefaultPosition(int index) {
        linerview_my.setmYdefaultPosition(index, view_myWidth);
    }


    private void setImageViewMargin(int dX) {
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) relay_my.getLayoutParams();
        int left = dX - mMeasuredWidth / 2;
        layoutParams.leftMargin = left;
        relay_my.setLayoutParams(layoutParams);
    }

//    @Override
//    public boolean dispatchTouchEvent(MotionEvent ev) {
//        //请求所有父控件及祖宗控件不要拦截事件
//        getParent().requestDisallowInterceptTouchEvent(true);
//        return super.dispatchTouchEvent(ev);
//    }

    private int lastdY;
    private int lastdX;

    private void setPointMargin(final int dY, int dX, int bottomPadding) {
        ObjectAnimator animator;
        animator = ObjectAnimator.ofFloat(view_my, "translationY", -lastdY, -dY);
        animator.setDuration(1000);
        animator.setInterpolator(new LinearInterpolator());
        animator.start();
        lastdY = dY;

        ObjectAnimator animator2;
        animator2 = ObjectAnimator.ofFloat(view_myWrite, "translationX", lastdX, dX - view_myWriteWidth / 2);
        animator2.setDuration(1000);
        animator2.setInterpolator(new LinearInterpolator());
        animator2.start();
        lastdX = dX;
    }

    /**
     * 获取屏幕的宽度
     *
     * @return
     */
    private int getScreenWidth() {
        int screenWidth = AutoLayoutConifg.getInstance().getScreenWidth();
        return screenWidth;
    }


    @Override
    public void move(int type, int distanceX, int leftPadding, int rightPadding) {
        int moveDistance = distanceX;
        if (distanceX < leftPadding) {
            moveDistance = leftPadding;
        } else if ((getScreenWidth() - leftPadding) < distanceX) {
            moveDistance = getScreenWidth() - leftPadding;
        }
        setImageViewMargin(moveDistance);

    }

    @Override
    public void pointMove(int distanceY, int distanceX, int bottomPadding) {
        LogUtil.e("pointMove =" + distanceY);
        setPointMargin(distanceY, distanceX, bottomPadding);
    }

    private static int NONE = 0;
    private static int DRAG = 1;    // 拖动
    private static int ZOOM = 2;    // 缩放
    private static int ROTA = 3;    // 旋转
    private int mode = NONE;
    private float oldDistance;
    private float oldAngle;
    private boolean long_touch = false;


    private Matrix matrix;
    private Matrix savedMatrix;
    private PointF startPoint;
    private PointF middlePoint;

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (matrix != null && mode == ZOOM) {
            this.setLayerType(View.LAYER_TYPE_HARDWARE, null);
            canvas.setMatrix(matrix);
            this.setLayerType(View.LAYER_TYPE_NONE, null);
        }
    }

//    @Override
//    public boolean onTouchEvent(MotionEvent event) {
//        switch (event.getAction() & MotionEvent.ACTION_MASK) {
//            case MotionEvent.ACTION_DOWN:           // 第一个手指touch
//                savedMatrix.set(matrix);
//                startPoint.set(event.getX(), event.getY());
//                mode = DRAG;
//                long_touch = false;
//                break;
//            case MotionEvent.ACTION_POINTER_DOWN:   // 第二个手指touch
//                oldDistance = getDistance(event);   // 计算第二个手指touch时，两指之间的距离
//                oldAngle = getDegree(event);        // 计算第二个手指touch时，两指所形成的直线和x轴的角度
//                if (oldDistance > 10f) {
//                    savedMatrix.set(matrix);
//                    middlePoint = midPoint(event);
//                    if (!long_touch) {
//                        mode = ZOOM;
//                    } else {
//                        mode = ROTA;
//                    }
//                }
//                break;
//            case MotionEvent.ACTION_UP:
//                mode = NONE;
//                break;
//            case MotionEvent.ACTION_POINTER_UP:
//                mode = NONE;
//                break;
//            case MotionEvent.ACTION_MOVE:
////                if (vibrator != null) vibrator.cancel();
//                if (mode == DRAG) {
//                    matrix.set(savedMatrix);
//                    matrix.postTranslate(event.getX() - startPoint.x, event.getY() - startPoint.y);
//                }
//
//                if (mode == ZOOM) {
//                    float newDistance = getDistance(event);
//
//                    if (newDistance > 10f) {
//                        matrix.set(savedMatrix);
//                        float scale = newDistance / oldDistance;
//                        matrix.postScale(scale, scale, 0, 0);
////                        invalidate();
//                        ViewCompat.postInvalidateOnAnimation(this);
//                    }
//                }
//
//                if (mode == ROTA) {
//                    float newAngle = getDegree(event);
//                    matrix.set(savedMatrix);
//                    float degrees = newAngle - oldAngle;
//                    matrix.postRotate(degrees, middlePoint.x, middlePoint.y);
//                }
//                break;
//        }
////        setImageMatrix(matrix);
////        gdetector.onTouchEvent(event);
//        return true;
//    }

    // 计算两个手指之间的距离
    private float getDistance(MotionEvent event) {
        float x = event.getX(0) - event.getX(1);
        float y = event.getY(0) - event.getY(1);
        return (float) Math.sqrt(x * x + y * y);
    }

    // 计算两个手指所形成的直线和x轴的角度
    private float getDegree(MotionEvent event) {
        return (float) (Math.atan((event.getY(1) - event.getY(0)) / (event.getX(1) - event.getX(0))) * 180f);
    }

    // 计算两个手指之间，中间点的坐标
    private PointF midPoint(MotionEvent event) {
        PointF point = new PointF();
        float x = event.getX(0) + event.getX(1);
        float y = event.getY(0) + event.getY(1);
        point.set(x / 2, y / 2);

        return point;
    }

}
