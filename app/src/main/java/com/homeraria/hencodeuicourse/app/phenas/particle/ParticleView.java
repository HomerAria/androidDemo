package com.homeraria.hencodeuicourse.app.phenas.particle;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
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
public class ParticleView extends View {
    private static final String TAG = "ParticleView";
    private static final int FRAME_RATE = 16;   //[ms]
    private static final int MAX_NUM = 50;     //随机粒子数量

    private ValueAnimator mParticleAnim;
    private int mMeasuredWidth, mMeasuredHeight;
    private List<BaseParticle> mCircles = new ArrayList<>();
    private Random mRandom = new Random();

    public ParticleView(Context context) {
        super(context);
        init();
    }

    public ParticleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ParticleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
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
        mMeasuredWidth = w;
        mMeasuredHeight = h;

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
        for (BaseParticle circle : mCircles) {
            circle.drawItem(canvas);
        }
        canvas.restore();
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
