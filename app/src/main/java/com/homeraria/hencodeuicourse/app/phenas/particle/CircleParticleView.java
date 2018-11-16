package com.homeraria.hencodeuicourse.app.phenas.particle;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author sean
 * @describe 通过View实现粒子组的管理-->占用系统内存&CPU明显低于使用SurfaceView实现方式
 * @email sean.zhou@oppo.com
 * @date on 2018/11/12 11:33
 */
public class CircleParticleView extends View {
    private static final String TAG = "ParticleView";
    private static final int FRAME_RATE = 16;   //[ms]
    private static final int MAX_NUM = 50;     //随机粒子数量

    private ValueAnimator mParticleAnim;
    private int mMeasuredWidth, mMeasuredHeight, mOriginalHeight = 0;
    private List<BaseParticle> mCircles = new ArrayList<>();
    private Random mRandom = new Random();

    private float mGatheringX, mGatheringY;     //汇聚中心位置
    private Paint mGatheringPaint = new Paint();
    private boolean isGathering = false;

    public CircleParticleView(Context context) {
        super(context);
        init();
    }

    public CircleParticleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CircleParticleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setLayerType(View.LAYER_TYPE_HARDWARE, null);

        //通过ValueAnimator使View运动起来
        mParticleAnim = ValueAnimator.ofInt(0).setDuration(FRAME_RATE);
        mParticleAnim.setRepeatCount(ValueAnimator.INFINITE);
        mParticleAnim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationRepeat(Animator animation) {
                super.onAnimationRepeat(animation);
                invalidate();
            }
        });

        mGatheringPaint.setColor(Color.YELLOW);
        mGatheringPaint.setStyle(Paint.Style.FILL);
    }

    /**
     * 清除所有粒子
     */
    public void clearParticles() {
        if (mCircles != null && mCircles.size() > 0) {
            mCircles.clear();
            invalidate();
        }
    }

    public void showParticles() {
        if (mCircles.size() == 0 && mMeasuredWidth != 0) {
            for (int i = 0; i < MAX_NUM; i++) {
                mCircles.add(new CircleParticle(mRandom, mMeasuredWidth, mOriginalHeight));
            }
            invalidate();
        }
    }

    public void startGather() {
        isGathering = true;
    }

    public void reset(){
        isGathering = false;
        getRandomGatheringPosition();
        if (mCircles != null && mCircles.size() > 0)  mCircles.clear();
        for (int i = 0; i < MAX_NUM; i++) {
            mCircles.add(new CircleParticle(mRandom, mMeasuredWidth, mOriginalHeight));
        }
        invalidate();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        Log.d(TAG, "onMeasure: " + getMeasuredWidth() + "+" + getMeasuredHeight());
//        if (mMeasuredWidth == 0) {
//            mMeasuredWidth = getMeasuredWidth();
//            mMeasuredHeight = getMeasuredHeight();
//        }


    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        Log.d(TAG, "onSizeChanged: " + w + "+" + h);
        //相比onMeasure(),不会发生多次回调，且w&h就是最终view的宽高
        mMeasuredWidth = w;     //不会发生改变
        mMeasuredHeight = h;
//        getDefaultGatheringPosition();
        getRandomGatheringPosition();

        if (mOriginalHeight == 0) {
            mOriginalHeight = h;
        }

        if (mCircles.size() == 0 && mMeasuredWidth != 0) {
            for (int i = 0; i < MAX_NUM; i++) {
                mCircles.add(new CircleParticle(mRandom, mMeasuredWidth, mMeasuredHeight));
            }
        }
        if (!mParticleAnim.isRunning()) {
            Log.d(TAG, "onMeasure  anim start  : ");
            mParticleAnim.start();
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.save();
        //会随着动画运行不断产生ArrayList对象
//        for (BaseParticle circle : mCircles) {
//            circle.drawItemRandomly(canvas);
//        }
        //不会产生新的ArrayList对象
        for (int i = 0; i < mCircles.size(); i++) {
            if (isGathering) {
                mCircles.get(i).drawItemGathering(canvas, mGatheringX, mGatheringY);
            } else {
                mCircles.get(i).drawItemRandomly(canvas);
            }
        }
        canvas.restore();

        canvas.save();
        canvas.drawCircle(mGatheringX, mGatheringY, 25, mGatheringPaint);
        canvas.restore();
    }

    /**
     * 默认汇聚点为View的中心点
     */
    private void getDefaultGatheringPosition() {
        mGatheringX = mMeasuredWidth / 2;   //不需要获取绝对坐标，是根据本View的坐标系相对绘制的
        mGatheringY = mMeasuredHeight / 2;
        Log.v("sean", "gatherX=" + mGatheringX + ", Y=" + mGatheringY + ", screenY");
    }

    /**
     * 在本View范围内随机设置汇聚点
     */
    private void getRandomGatheringPosition(){
        mGatheringX = (float) (mMeasuredWidth *(0.25+ mRandom.nextFloat()/2));
        mGatheringY = (float) (mMeasuredHeight *(0.25+ mRandom.nextFloat()/2));
    }

    public void setHeight(float heightRatio) {
        this.getLayoutParams().height = (int) (mOriginalHeight * heightRatio);
        this.requestLayout();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        Log.d(TAG, "onDetachedFromWindow: ");
        if (null != mParticleAnim) {
            mParticleAnim.end();
            mParticleAnim = null;
        }
    }
}
