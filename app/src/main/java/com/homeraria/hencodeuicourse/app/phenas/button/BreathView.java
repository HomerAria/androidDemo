package com.homeraria.hencodeuicourse.app.phenas.button;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Keep;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;

import com.homeraria.hencodeuicourse.app.phenas.widget.BreathInterpolator;

/**
 * Phenas呼吸效果
 */
public class BreathView extends View {

    public final float SMOOTH_SAMPLE_NUMBER = 20f, MIN_RADIUS = 100f;
    private final int SEMI_ALPHA = 200;

    public float MAX_RADIUS = 200f;

    private Paint mPaint;
//    private ObjectAnimator animator, animator1;

    //控件的中心点坐标，其他点坐标都基于此值
    private int mCenterX, mCenterY;
    private int alpha;
    private float radius;
    private boolean isBreathing = true;

    public BreathView(Context context) {
        this(context, null);
    }

    @Keep
    public void setRadius(float degreeY) {
        MAX_RADIUS = degreeY;
        mPaint = new Paint();
        mPaint.setColor(Color.parseColor("#00ce9b"));
        mPaint.setStrokeWidth((MAX_RADIUS - MIN_RADIUS) / SMOOTH_SAMPLE_NUMBER);
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);

        alpha = SEMI_ALPHA;
        radius = MIN_RADIUS;
        invalidate();
    }

    /**
     * 直接通过xml创建，是本例实际的调用入口
     */
    public BreathView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    /**
     * 最终调用的build function，执行成员变量的初始化
     */
    public BreathView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mPaint = new Paint();
        mPaint.setColor(Color.parseColor("#00ce9b"));
        mPaint.setStrokeWidth((MAX_RADIUS - MIN_RADIUS) / SMOOTH_SAMPLE_NUMBER);
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);

        alpha = SEMI_ALPHA;
        radius = MIN_RADIUS;
//        startAnim();
    }

    public void startAnim() {
//        if (animator == null) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(this, "radius", 100f, 200f);
        animator.setInterpolator(new BreathInterpolator());
        animator.setDuration(3000);
            animator.setRepeatCount(Animation.INFINITE);
//            animator.setRepeatMode(ValueAnimator.REVERSE);
//        }
//        if (animator1 == null) {
//            animator1 = ObjectAnimator.ofFloat(this, "scaleY", 1f,  1.5f, 1f);
//            animator1.setInterpolator(new AccelerateDecelerateInterpolator());
//            animator1.setDuration(1000);
//            animator1.setRepeatCount(Animation.INFINITE);
//            animator1.setRepeatMode(ValueAnimator.REVERSE);
//        }
        animator.start();
//        animator1.start();
    }

    /**
     * 最终确定view尺寸时的回调
     */
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        mCenterX = w / 2;
        mCenterY = h / 2;
    }

    /**
     * mControlPoint会根据触摸位置变化
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        startAnim();

//        mControlPoint.x = event.getX();
//        mControlPoint.y = event.getY();

        //使画面无效，从而系统才会重新调用view的onDraw()
//        invalidate();
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawCircle(mCenterX, mCenterY, radius, mPaint);
        while ((alpha - SEMI_ALPHA / SMOOTH_SAMPLE_NUMBER) >= 0) {
            alpha = alpha - SEMI_ALPHA / (int) SMOOTH_SAMPLE_NUMBER;
            radius = radius + (MAX_RADIUS - MIN_RADIUS) / SMOOTH_SAMPLE_NUMBER;
            mPaint.setAlpha(alpha);
            mPaint.setStyle(Paint.Style.STROKE);
            mPaint.setStrokeWidth((MAX_RADIUS - MIN_RADIUS) / SMOOTH_SAMPLE_NUMBER);
            Log.v("sean", alpha + "");
            canvas.drawCircle(mCenterX, mCenterY, radius, mPaint);
        }
//        while (isBreathing) {
//
//        }
    }
}
