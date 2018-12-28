package com.homeraria.hencodeuicourse.app.phenas.particle;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.media.ThumbnailUtils;
import androidx.annotation.DrawableRes;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;

import com.homeraria.hencodeuicourse.app.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author sean
 * @describe 通过View实现粒子组的管理-->占用系统内存&CPU明显低于使用SurfaceView实现方式
 * @email sean.zhou@oppo.com
 * @date on 2018/11/12 11:33
 */
public class BitmapParticleView extends View {
    private static final String TAG = "ParticleView";
    private static final int FRAME_RATE = 16;   //[ms]
    private static final int MAX_NUM = 20;     //随机粒子数量

    private ValueAnimator mParticleAnim;
    private int mMeasuredWidth, mMeasuredHeight;
    private List<BaseParticle> mCircles = new ArrayList<>();
    private Random mRandom = new Random();
    private Matrix mMatrix = new Matrix();
    private Paint mPaint = new Paint();
    private Bitmap mBitmap;

    public BitmapParticleView(Context context) {
        super(context);
        init();
    }

    public BitmapParticleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public BitmapParticleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setLayerType(View.LAYER_TYPE_HARDWARE, null);
        mBitmap = getParticleBitmap(R.mipmap.pill);
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.WHITE);
        mPaint.setDither(true);
        mPaint.setStyle(Paint.Style.FILL);

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
                mCircles.add(new BitmapParticle(mBitmap, mMatrix, mPaint, mRandom, mMeasuredWidth, mMeasuredHeight));
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
            mCircles.get(i).drawItemRandomly(canvas);
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

    private Bitmap getParticleBitmap(@DrawableRes int resId) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.RGB_565;
        return ThumbnailUtils.extractThumbnail(BitmapFactory.decodeResource(getContext().getResources(), resId, options), dip2px(30), dip2px(30), ThumbnailUtils.OPTIONS_RECYCLE_INPUT);
//        return Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getContext().getResources(), resId, options),
//                dip2px(30), dip2px(30), true);
    }

    private int dip2px(float value) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, value, getContext().getResources().getDisplayMetrics());
    }
}
