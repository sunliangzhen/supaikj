package com.spkj.supai.view;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.spkj.supai.R;
import com.spkj.supai.view.b.ViewPath;
import com.spkj.supai.view.b.ViewPathEvaluator;
import com.spkj.supai.view.b.ViewPoint;
import com.zhy.autolayout.config.AutoLayoutConifg;
import com.zhy.autolayout.utils.AutoUtils;

import org.xutils.common.util.LogUtil;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static android.R.attr.mode;

/**
 * Created by Administrator on 2016/11/3.
 */

public class LinerView extends View {
    public LinerView(Context context) {
        super(context);
        setWillNotDraw(false);
        init();
    }

    public LinerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setWillNotDraw(false);
        init();
    }

    public LinerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setWillNotDraw(false);
        init();

    }

    public LinerView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        setWillNotDraw(false);
        init();
    }

    //-------------View相关-------------
//View自身的宽和高
    private int mHeight;
    private int mWidth;

    //-------------统计图相关-------------
//x轴的条目
    private int xNum = 8;
    //y轴的条目
    private int yNum = 4;
    private int newYnum;
    //y轴条目之间的距离
    private int ySize = AutoUtils.getPercentHeightSize(150);
    //x轴条目之间的距离
    private int xSize;
    //y轴的长度,11个条目只有10段距离
    private int yLastSize;

//    //-------------必须给的资源相关-------------
//    private String[] xData = new String[]{"星期一", "星期二", "星期三", "星期四", "星期五", "星期六", "星期天"};
//    private String[] yData = new String[]{"0", "10", "20", "30", "40", "50"};
//    private String str = "项目完成的进度（单位%）";
//    //折线表示的最大值,取yData的最大值
//    private int maxData = Integer.parseInt(yData[yData.length - 1]);
//    //折线真实值
//    private int[] yValue = new int[]{8, 20, 42, 12, 30, 2, 50};


    private int maxData = 0;
    private ArrayList<Float> dataList = new ArrayList<>();
    private ArrayList<Integer> listY = new ArrayList<>();
    private ArrayList<String> yData = new ArrayList<>();
    private ArrayList<String> xData = new ArrayList<>();
    //-------------画笔相关-------------
//边框的画笔
    private Paint borderPaint;
    private Paint borderPaint2;
    private Paint mY;
    //文字的画笔
    private Paint textPaint;
    //折线的画笔
    private Paint linePaint;
    //黑点的画笔
    private Paint pointPaint;
    //黑点的画笔
    private Paint mPointY;

    private int screenWidth;
    private int bottomPadding = AutoUtils.getPercentHeightSize(100);
    private int topPadding = AutoUtils.getPercentHeightSize(100);
    private int leftPadding = AutoUtils.getPercentHeightSize(100);
    private int rightPadding = AutoUtils.getPercentHeightSize(100);
    private int firstX = xSize * 2;
    private int state = 0;
    private int indexY;
    private List<Float> point = new ArrayList<>();
    private Point[] mPoints;

    private LinermYmovelisten linermYmovelisten;

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;
    }

    private void init() {
        screenWidth = AutoLayoutConifg.getInstance().getScreenWidth();
        dataList.clear();
        yData.clear();
        xData.clear();
        maxData = 0;
        dataList.add(0.0f);
        dataList.add(0.0f);
        dataList.add(0.0f);
        dataList.add(0.0f);
        dataList.add(0.0f);
        dataList.add(0.0f);
        dataList.add(1.0f);
        dataList.add(0.0f);

        yData.add("0");
        yData.add("1");
        yData.add("2");
        yData.add("3");
        yData.add("4");
        yData.add("5");

        xData.add("1");
        xData.add("4");
        xData.add("8");
        xData.add("12");
        xData.add("16");
        xData.add("20");
        xData.add("24");
        xData.add("28");
        linePaint = new Paint();
        linePaint.setColor(0xffffffff);
        linePaint.setAntiAlias(true);
        linePaint.setStyle(Paint.Style.STROKE);
        linePaint.setStrokeWidth((float) AutoUtils.getPercentWidthSize(6));

    }

    public void setYnum(int yNum) {
        this.yNum = yNum;
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        LogUtil.e("执行了onDraw（）方法");
//        this.setLayerType(View.LAYER_TYPE_HARDWARE, null);
//        this.setLayerType(View.LAYER_TYPE_NONE, null);

        initPaint();
        canvas.translate(leftPadding, mHeight - bottomPadding);
        drawBorder(canvas);
//        drawmY(canvas);
        drawPoint(canvas);
        drawText(canvas);
//        drawPointText(canvas);
//        drawmYpoint(canvas);
//        drawLine(null);
        if (path4 != null) {
            LogUtil.e("aaaaaaaaaaaaaaaaaaaa绘制曲线");
            canvas.drawPath(path4, linePaint);
        }
    }

    @Override
    public void draw(final Canvas canvas) {
        super.draw(canvas);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(widthMeasureSpec, ySize * (yData.size() - 1) + topPadding + bottomPadding);
    }


    int startY = 0;
    int startx = 0;
    int endY = 0;
    int endX = 0;


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                state = 1;
                startY = (int) event.getRawY();
                startx = (int) event.getRawX();
                LogUtil.e("ssssssssssssssssssss" + startx);
                break;
            case MotionEvent.ACTION_MOVE:
                state = 1;
                endY = (int) event.getRawY();
                endX = (int) event.getRawX();
                if (Math.abs(endX - startx) >= Math.abs(endY - startY) && Math.abs(endX - startx) > 30) {
//                    int rawX = (int) event.getRawX();
//                    if (rawX > leftPadding) {
//                        state = 1;
//                        firstX = (int) event.getRawX() - leftPadding;
//                    } else if (rawX < leftPadding) {
//                        firstX = 0;
//                    }
                    if (linermYmovelisten != null) {
                        linermYmovelisten.move(MotionEvent.ACTION_MOVE, (int) event.getRawX(), leftPadding, rightPadding);
                    }
                }
                break;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                int rawX2 = (int) event.getRawX();
                if (rawX2 + relay_myWith / 2 < ySize) {
                    firstX = 0;
                } else {
                    firstX = (int) event.getRawX() - ySize;
                }

                for (int i = 0; i < listY.size(); i++) {
                    if (firstX + relay_myWith / 2 == listY.get(i) || Math.abs(firstX + relay_myWith / 2 - listY.get(i)) <= xSize / 2) {
                        firstX = listY.get(i);
                        indexY = i;
                        break;
                    }
                }

                for (int i = 0; i < dataList.size(); i++) {
                    float position = (dataList.get(i) * yLastSize / maxData);
                    if (i == indexY) {
//                        canvas.save();
//                        canvas.translate(i * xSize - percentHeightSize / 2, position - percentHeightSize / 2);
//                        Drawable thumb_double = getResources().getDrawable(R.drawable.shape_line);
//                        if (thumb_double != null) {
//                            thumb_double.setBounds(0, 0, percentHeightSize, percentHeightSize);
//                            thumb_double.draw(canvas);
//                        }
                        if (linermYmovelisten != null) {
                            linermYmovelisten.pointMove((int) (position), i * xSize + rightPadding, bottomPadding);
                        }
                    }
                }
//        }
                state = 0;
                if (linermYmovelisten != null) {
                    linermYmovelisten.move(MotionEvent.ACTION_MOVE, firstX + leftPadding, leftPadding, rightPadding);
                }
//                invalidate();
                break;
            default:
                break;

        }
        return true;
    }

    public interface LinermYmovelisten {
        void move(int type, int distanceX, int leftPadding, int rightPadding);

        void pointMove(int distanceY, int distanceX, int bottomPadding);
    }

    public void setLinermYmovelisten(LinermYmovelisten linermYmovelisten) {
        this.linermYmovelisten = linermYmovelisten;
    }

    public void setmYdefaultPosition(int index, int point_width) {

        RelativeLayout.LayoutParams linearParams = (RelativeLayout.LayoutParams) relay_my.getLayoutParams();
        linearParams.leftMargin = index * xSize + leftPadding - point_width / 2;
        relay_my.setLayoutParams(linearParams);


        for (int i = 0; i < dataList.size(); i++) {
            float position = (dataList.get(i) * yLastSize / maxData);
            if (i == index) {
                linermYmovelisten.pointMove((int) (position), i * xSize + rightPadding, bottomPadding);
            }
        }

    }

    /**
     * 初始化画笔
     */
    private void initPaint() {
        //边框画笔
        borderPaint = new Paint();
        borderPaint.setAntiAlias(true);
        borderPaint.setStyle(Paint.Style.STROKE);
        borderPaint.setColor(0x99ffffff);
        borderPaint.setStrokeWidth((float) AutoUtils.getPercentWidthSize(2));
        //边框画笔
        borderPaint2 = new Paint();
        borderPaint2.setAntiAlias(true);
        borderPaint2.setStyle(Paint.Style.STROKE);
        borderPaint2.setColor(0x99ffffff);
        borderPaint2.setStrokeWidth((float) AutoUtils.getPercentWidthSize(5));
        //文字画笔
        textPaint = new Paint();
        textPaint.setTextSize(AutoUtils.getPercentWidthSizeBigger(25));
        textPaint.setColor(0x99ffffff);
        textPaint.setAntiAlias(true);
        //区域画笔
        linePaint = new Paint();
        linePaint.setColor(0xffffffff);
        linePaint.setAntiAlias(true);
        linePaint.setStyle(Paint.Style.STROKE);
        linePaint.setStrokeWidth((float) AutoUtils.getPercentWidthSize(6));
        //黑点画笔
        pointPaint = new Paint();
        pointPaint.setAntiAlias(true);
        pointPaint.setStyle(Paint.Style.FILL);
        pointPaint.setColor(0xffffffff);

        mPointY = new Paint();
        mPointY.setAntiAlias(true);
        mPointY.setStyle(Paint.Style.FILL);
        mPointY.setColor(0xffffffff);

        mY = new Paint();
        mY.setAntiAlias(true);
        mY.setStyle(Paint.Style.STROKE);
        mY.setColor(0xffffffff);
        mY.setStrokeWidth((float) AutoUtils.getPercentWidthSize(5));
    }

    /**
     * 画边框
     *
     * @param canvas
     */

    private void drawBorder(Canvas canvas) {
        Path path = new Path();

        for (int i = 0; i < yNum; i++) {
            path.reset();
            path.moveTo(0, -i * ySize);
            path.lineTo((xNum - 1) * xSize, -i * ySize);
            canvas.drawPath(path, borderPaint);
        }
//        for (int i = 0; i < xNum; i++) {
//            path.reset();
//            path.moveTo(i * xSize, 0);
//            path.lineTo(i * xSize, -(yNum - 1) * ySize);
//            canvas.drawPath(path, borderPaint);
//        }
        if (state == 0) {
            for (int i = 0; i < listY.size(); i++) {
                if (firstX == listY.get(i) || Math.abs(firstX - listY.get(i)) <= xSize / 2) {
                    firstX = listY.get(i);
                    indexY = i;
                    break;
                }
            }
        }
    }

    /**
     * 画移动的Y轴
     *
     * @param canvas
     */
    private void drawmY(Canvas canvas) {
        Path pathY = new Path();
        pathY.reset();
        pathY.moveTo(firstX, 0);
        pathY.lineTo(firstX, -AutoUtils.getPercentHeightSize(520));
        canvas.drawPath(pathY, mY);
    }

    /**
     * 画x轴坐标
     *
     * @param canvas
     */

    private void drawPoint(Canvas canvas) {
        for (int i = 0; i < xNum; i++) {
            listY.add(i * xSize);
//            canvas.drawCircle(i * xSize, 0, 5, pointPaint);
        }
    }

    /**
     * 画文字
     *
     * @param canvas
     */
    private void drawText(Canvas canvas) {
        //x轴的文字
        for (int i = 0; i < xData.size(); i++) {
            //测量文字的宽高
            Rect rect = new Rect();
            textPaint.getTextBounds(xData.get(i), 0, xData.get(i).length(), rect);
            float textWidth = rect.width();
            float textHeight = rect.height();
            canvas.drawText(xData.get(i), i * xSize - textWidth / 2, textHeight + 20, textPaint);
        }
        //y轴的文字
        for (int i = 0; i < yData.size(); i++) {
            //测量文字的宽高
            Rect rect = new Rect();
            textPaint.getTextBounds(yData.get(i), 0, yData.get(i).length(), rect);
            float textWidth = rect.width();
            float textHeight = rect.height();
            canvas.drawText(yData.get(i), -textWidth - 20, i * (-ySize) + (textHeight / 2), textPaint);
        }
        //顶部文字
//        canvas.drawText(str, 0, (-ySize) * (yData.length - 1) - 20, textPaint);
    }

    /**
     * 画折线
     *
     * @param canvas
     */

    private void drawLine(Canvas canvas) {
        point.clear();
        mPoints = new Point[dataList.size()];
        for (int i = 0; i < dataList.size(); i++) {
            //计算折线的位置：（当前点的值/最大值）拿到百分比percent
            //用百分比percent乘与y轴总长，就获得了折线的位置
            //这里拿到的百分比一直为0，所以换一种方法，先乘与总长再除与最大值，而且记得加上负号
            float position = -(dataList.get(i) * yLastSize / maxData);
            point.add(position);
            mPoints[i] = new Point(i * xSize, (int) position);
        }
        drawScrollLine(canvas);
    }

    /**
     * 画移动的点
     *
     * @param canvas
     */
    private void drawmYpoint(Canvas canvas) {
        int percentHeightSize = AutoUtils.getPercentHeightSize(40);
        if (state == 0) {
            LogUtil.e("执行了drawmYpoint()indexY=" + indexY);
            canvas.drawCircle(indexY * xSize, 0, AutoUtils.getPercentHeightSize(15), pointPaint);
        }
        for (int i = 0; i < dataList.size(); i++) {
            float position = -(dataList.get(i) * yLastSize / maxData);
            if (i == indexY) {
                canvas.save();
                canvas.translate(i * xSize - percentHeightSize / 2, position - percentHeightSize / 2);
                Drawable thumb_double = getResources().getDrawable(R.drawable.shape_line);
                if (thumb_double != null) {
                    thumb_double.setBounds(0, 0, percentHeightSize, percentHeightSize);
                    thumb_double.draw(canvas);
                }
                canvas.restore();
            }
        }
    }


    private Point[] getPoints() {
        Point[] points = new Point[dataList.size()];
        for (int i = 0; i < dataList.size(); i++) {
//            int ph = bheight - (int) (bheight * (yRawData.get(i) / maxValue));
            points[i] = new Point(i * xSize, (int) i);
        }
        return points;
    }

    private Path path4 = new Path();
    private ValueAnimator redAnim4;
    private AnimatorSet animatorSet2;


    private void drawScrollLine(Canvas canvas) {
        Point startp = new Point();
        Point endp = new Point();
//        for (int i = 0; i < mPoints.length - 1; i++) {
//            startp = mPoints[i];
//            endp = mPoints[i + 1];
//            int wt = (startp.x + endp.x) / 2;
//            Point p3 = new Point();
//            Point p4 = new Point();
//            p3.y = startp.y;
//            p3.x = wt;
//            p4.y = endp.y;
//            p4.x = wt;
//
//            Path path = new Path();
//            path.moveTo(startp.x, startp.y);
//            path.cubicTo(p3.x, p3.y, p4.x, p4.y, endp.x, endp.y);
//            canvas.drawPath(path, linePaint);
//        }
        startp = mPoints[0];
        endp = mPoints[mPoints.length - 1];

        ViewPath viewPath4 = new ViewPath();
        viewPath4.moveTo(startp.x, startp.y);

        for (int i = 0; i < mPoints.length - 1; i++) {
            startp = mPoints[i];
            endp = mPoints[i + 1];
            int wt = (startp.x + endp.x) / 2;
            Point p3 = new Point();
            Point p4 = new Point();
            p3.y = startp.y;
            p3.x = wt;
            p4.y = endp.y;
            p4.x = wt;
            viewPath4.curveTo(p3.x, p3.y, p4.x, p4.y, endp.x, endp.y);
        }

        redAnim4 = ValueAnimator.ofObject(new ViewPathEvaluator(), viewPath4.getPoints().toArray());
        redAnim4.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                ViewPoint cpoint4 = (ViewPoint) valueAnimator.getAnimatedValue();
                Log.e("ssssssssssssssssssss", "cpoint4.x=" + cpoint4.x + ",cpoint4.y=" + cpoint4.y);
                if (path4 == null) {
                    LogUtil.e("aaaaaaaaaaaaaaaaaaaa创建里曲线");
                    path4 = new Path();
                }
                path4.lineTo(cpoint4.x, cpoint4.y);
                ViewCompat.postInvalidateOnAnimation(LinerView.this);
            }
        });
        redAnim4.setDuration(5000);
        animatorSet2 = new AnimatorSet();
        animatorSet2.playTogether(redAnim4);
        animatorSet2.setDuration(5000);
        animatorSet2.start();

    }

    private void drawPointText(Canvas canvas) {
        for (int i = 0; i < dataList.size(); i++) {
            //测量文字的宽高
            String i1 = dataList.get(i) + "";
            Rect rect = new Rect();
            textPaint.getTextBounds(i1, 0, i1.length(), rect);
            float textWidth = rect.width();
            canvas.drawText(dataList.get(i) + "", i * xSize - textWidth / 2, point.get(i) - 10, textPaint);
        }
    }

    private View view_lineY;
    private View view_Point;
    private View view_myWrite;
    private RelativeLayout relay_my;
    private int relay_myWith;

    public void setData(ArrayList<Float> list, ArrayList<String> list2,
                        RelativeLayout relay, View view_myWrite,
                        int pointWith, View view_lineY, View view_Point,
                        RelativeLayout relay_my, int view_myWriteWidth, int relay_myWith) {
        this.view_Point = view_Point;
        this.view_lineY = view_lineY;
        this.relay_my = relay_my;
        this.view_myWrite = view_myWrite;
        this.relay_myWith = relay_myWith;
        path4 = null;
        if (animatorSet2 != null) {
            animatorSet2.cancel();
        }
        if (redAnim4 != null) {
            redAnim4.cancel();
        }
        xData.clear();
        dataList.clear();
        yData.clear();
        maxData = 0;
        xData.addAll(list2);
        dataList.addAll(list);
        for (int i = 0; i < dataList.size(); i++) {
            if (dataList.get(i) > maxData) {
                maxData = (int) Math.ceil(dataList.get(i));
            }
        }
        if (maxData > 0 && maxData > (yNum - 1)) {
            int v1 = maxData % (yNum - 1);
            int v2 = maxData / (yNum - 1);
            if (v1 != 0) {
                maxData = ++v2 * (yNum - 1);
            }
        }
        if (maxData <= (yNum - 1)) {
            maxData = (yNum - 1);
            for (int i = 0; i < yNum; i++) {
                yData.add(i == 0 ? i + "" : i + "k");
            }
        } else {
            for (int i = 0; i < yNum; i++) {
                int i1 = (i * (maxData / (yNum - 1)));
                yData.add(i1 + "k");
            }
        }
        if (maxData == 0) {
            init();
        }
        yNum = newYnum != 0 ? newYnum : yData.size();
        xNum = xData.size();
        xSize = (screenWidth - leftPadding - rightPadding) / (xNum - 1);
        yLastSize = (yNum - 1) * ySize;

        RelativeLayout.LayoutParams linearParams = (RelativeLayout.LayoutParams) relay.getLayoutParams();
        linearParams.height = ySize * (yData.size() - 1) + topPadding + pointWith;
        relay.setLayoutParams(linearParams);


        RelativeLayout.LayoutParams linearParams2 = (RelativeLayout.LayoutParams) view_lineY.getLayoutParams();
        linearParams2.height = ySize * (yData.size() - 1) + topPadding;
        view_lineY.setLayoutParams(linearParams2);

        RelativeLayout.LayoutParams linearParams3 = (RelativeLayout.LayoutParams) view_myWrite.getLayoutParams();
        linearParams3.topMargin = ySize * (yData.size() - 1) + topPadding - view_myWriteWidth / 2;
        view_myWrite.setLayoutParams(linearParams3);
        ViewCompat.postInvalidateOnAnimation(this);
        drawLine(null);
    }
}
