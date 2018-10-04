package com.homeraria.hencodeuicourse.app.view.besier;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class Besier extends View {
    private Paint mPaint;

    //控件的中心点坐标，其他点坐标都基于此值
    private int mCenterX, mCenterY;

    //二阶贝塞尔曲线的始终点与控制点
    private PointF mStartPoint, mEndPoint, mControlPoint;

    public Besier(Context context) {
        this(context, null);
    }

    /**
     * 直接通过xml创建，是本例实际的调用入口
     */
    public Besier(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    /**
     * 最终调用的build function，执行成员变量的初始化
     */
    public Besier(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mPaint = new Paint();
        mPaint.setColor(Color.WHITE);
        mPaint.setStrokeWidth(8);
        mPaint.setStyle(Paint.Style.STROKE);

        mStartPoint = new PointF(0, 0);
        mEndPoint = new PointF(0, 0);
        mControlPoint = new PointF(0, 0);
    }

    /**
     * 最终确定view尺寸时的回调
     */
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        mCenterX = w / 2;
        mCenterY = h / 2;

        //确定三个决定点的初始位置
        mStartPoint.set(mCenterX - 200, mCenterY);
        mEndPoint.set(mCenterX + 200, mCenterY);
        mControlPoint.set(mCenterX, mCenterY - 100);
    }

    /**
     * mControlPoint会根据触摸位置变化
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mControlPoint.x = event.getX();
        mControlPoint.y = event.getY();

        //使画面无效，从而系统才会重新调用view的onDraw()
        invalidate();
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //绘制三点
        mPaint.setColor(Color.YELLOW);
        mPaint.setStrokeWidth(20);
        canvas.drawPoint(mStartPoint.x, mStartPoint.y, mPaint);
        canvas.drawPoint(mEndPoint.x, mEndPoint.y, mPaint);
        canvas.drawPoint(mControlPoint.x, mControlPoint.y, mPaint);

        //辅助线
        mPaint.setStrokeWidth(4);
        canvas.drawLine(mStartPoint.x, mStartPoint.y, mControlPoint.x, mControlPoint.y, mPaint);
        canvas.drawLine(mControlPoint.x, mControlPoint.y, mEndPoint.x, mEndPoint.y, mPaint);

        //Besier曲线
        mPaint.setColor(Color.WHITE);
        mPaint.setStrokeWidth(8);

        Path path = new Path();
        path.moveTo(mStartPoint.x, mStartPoint.y);
        path.quadTo(mControlPoint.x, mControlPoint.y, mEndPoint.x, mEndPoint.y);

        canvas.drawPath(path, mPaint);
    }
}
