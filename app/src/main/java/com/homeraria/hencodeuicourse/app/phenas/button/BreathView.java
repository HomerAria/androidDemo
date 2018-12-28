package com.homeraria.hencodeuicourse.app.phenas.button;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Build;
import androidx.annotation.Keep;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;

import com.homeraria.hencodeuicourse.app.R;
import com.homeraria.hencodeuicourse.app.phenas.widget.BreathInterpolator;

/**
 * Phenas呼吸效果
 */
@RequiresApi(api = Build.VERSION_CODES.M)
public class BreathView extends View {
    public final float SOLID_SPREAD_RATIO = 2f;    //外部半透明弥散最大半径和中心实心圆半径之比
    public final float SMOOTH_SAMPLE_NUMBER = 20f;
    private final int SEMI_ALPHA = 200;
    private final int COLOR = getResources().getColor(R.color.colorPrimary, null);

    public float MAX_RADIUS = 200f, MIN_RADIUS = MAX_RADIUS / SOLID_SPREAD_RATIO;    //默认值

    private Paint mPaint = new Paint();
//    private ObjectAnimator animator, animator1;

    //控件的中心点坐标，其他点坐标都基于此值
    private int mCenterX, mCenterY;
    private int alpha;
    private float radius;
    private boolean isBreathing = true;
    private OnClickListener mOnClickListener;

    public void setmOnClickListener(OnClickListener mOnClickListener) {
        this.mOnClickListener = mOnClickListener;
    }

    /**
     * 会不断被ValueAnimator调用，需要放置mRadius被重复调用,new Paint()不能再这里执行
     *
     * @param degreeY
     */
    @Keep
    public void setRadius(float degreeY) {
        MAX_RADIUS = degreeY;
//        if(mPaint == null) {
//            mPaint = new Paint();
        mPaint.setColor(COLOR);
        mPaint.setStrokeWidth((MAX_RADIUS - MIN_RADIUS) / SMOOTH_SAMPLE_NUMBER);
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
//        }

        alpha = SEMI_ALPHA;
        radius = MIN_RADIUS;
        invalidate();
    }

    public BreathView(Context context) {
        this(context, null);
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
        mPaint.setColor(COLOR);
        mPaint.setStrokeWidth((MAX_RADIUS - MIN_RADIUS) / SMOOTH_SAMPLE_NUMBER);
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
    }

    public void startAnim() {
//        if (animator == null) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(this, "radius", MIN_RADIUS, MAX_RADIUS);
        animator.setInterpolator(new BreathInterpolator());
        animator.setDuration(1000);
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

        MAX_RADIUS = Math.min(h, w) / 2f;
        MIN_RADIUS = MAX_RADIUS / SOLID_SPREAD_RATIO;
        Log.v(BreathView.class.getSimpleName(), "h=" + h + ", w=" + w);

        //动态适应宽高，确定尺寸后开始执行动画
        radius = MIN_RADIUS;
        alpha = SEMI_ALPHA;
        startAnim();

    }

    /**
     * mControlPoint会根据触摸位置变化
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
//        startAnim();

//        mControlPoint.x = event.getX();
//        mControlPoint.y = event.getY();

        //使画面无效，从而系统才会重新调用view的onDraw()
//        invalidate();
        return false;
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
//            Log.v("sean", alpha + "");
            canvas.drawCircle(mCenterX, mCenterY, radius, mPaint);
        }
//        while (isBreathing) {
//
//        }
    }
}
