package com.homeraria.hencodeuicourse.app.phenas.particle;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.TypedValue;

import java.util.Random;

/**
 * @author sean
 * @describe 单个圆形基础粒子：除了起始位置，每个粒子速度不同，最终透明度不同，尺寸不同
 * @email sean.zhou@oppo.com
 * @date on 2018/11/10 16:24
 */
public class CircleParticle implements BaseParticle {
    private static final int ALPHA_MIN = 10;
    private static final float DEFAULT_RADIUS = 2f;
    private static final float DEFAULT_SPEED = 5f;
    private static final float INNER_RATIO = 0.2f;

    private int mAlpha = 200;
    private float mSpeed = DEFAULT_SPEED;
    private float mRadius = DEFAULT_RADIUS, mStartRadius = DEFAULT_RADIUS;

    private int mWidth, mHeight;     //moving area
    private float mX, mY, mStartX, mStartY, mDisX, mDisY;     //position
    private float mDistance;
    private boolean mIsAddX, mIsAddY;    //x,y轴方向
    private boolean mIsNeedChange = true;

    private Random mRandomGenerator;
    private Paint mPaint = new Paint();    //每个粒子需要自己独立的paint，不能相互干涉
    private boolean isNeedGather = false;  //默认不需要聚拢
    private float mGatherCountDown = 50;   //初始值定义汇聚速度，同时计数何时汇聚结束

    public CircleParticle(Random random, int width, int height) {
        this.mWidth = width;
        this.mHeight = height;
        this.mRandomGenerator = random;

        setRandomParam();
        setPaint();
    }

    private void setRandomParam() {
        mStartX = mRandomGenerator.nextFloat() * mWidth;
        mStartY = mRandomGenerator.nextFloat() * mHeight;
        mX = mStartX;
        mY = mStartY;

        mStartRadius = dip2Px(mRandomGenerator.nextInt(10) + 3);
        mRadius = mStartRadius;
        mAlpha = mRandomGenerator.nextInt(50) + 200;
        mSpeed = (float) mRandomGenerator.nextInt(5) + 3f;

        mIsAddX = mRandomGenerator.nextBoolean();
        mIsAddY = mRandomGenerator.nextBoolean();

        //nextFloat是[0,1]的均匀分布，而nextGaussian为正态分布
//        mDisX = mSpeed * mRandomGenerator.nextFloat();
//        mDisY = mSpeed * mRandomGenerator.nextFloat();
        mDisX = (float) (mSpeed * mRandomGenerator.nextGaussian());
        mDisY = mSpeed - mDisX;    //x,y方向运动速度需要相互关联，防止出现两者都是0而使粒子静止的情况

        //判断移动的最大距离
        if (judgeInner()) {
            mDistance = mRandomGenerator.nextInt((int) (0.5f * mWidth)) + (0.25f * mWidth);
        } else {
            if (mIsAddX && mIsAddY) {
                // 右下
                mDistance = getHypotenuse(mWidth - mStartX, mHeight - mStartY);
            } else if (!mIsAddX && mIsAddY) {
                // 左下
                mDistance = getHypotenuse(mStartX, mHeight - mStartY);
            } else if (mIsAddX) {
                // 右上
                mDistance = getHypotenuse(mWidth - mStartX, mStartY);
            } else {
                mDistance = getHypotenuse(mStartX, mStartY);
            }
            mDistance = mDistance - 10f;
        }
        mIsNeedChange = mDistance >= (0.4f * getHypotenuse(0.5 * mWidth, 0.5 * mHeight));
    }

    private void setPaint() {
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.parseColor("#00935f"));
        mPaint.setDither(true);
        mPaint.setStyle(Paint.Style.FILL);
    }

    @Override
    public void drawItemRandomly(Canvas canvas) {
        if (mX == mStartX) {
            mPaint.setAlpha(ALPHA_MIN);
        }
        //绘制
        canvas.drawCircle(mX += getPNValue(mIsAddX, mDisX), mY += getPNValue(mIsAddY, mDisY), mRadius, mPaint);
        double moveDis = Math.sqrt(Math.pow(mX - mStartX, 2) + Math.pow(mY - mStartY, 2));
        if (mIsNeedChange) {
            //alpha&ratio都会随着运动过程做变化，需要从小到大再变小：因为匀速运动，moveDis是时间线性的，所以可以将moveDis当成时间轴
            float ratio = (float) ((moveDis / mDistance)) * 5;
            mPaint.setAlpha((int) ((mAlpha - ALPHA_MIN) * ratio + ALPHA_MIN));
            mRadius = mStartRadius * (1 - ratio);
//            Log.v("sean", "mDistance:" + mDistance + ", ratio:" + ratio);
        }
        if (moveDis >= mDistance || mPaint.getAlpha() >= mAlpha || mRadius <= mStartRadius * 0.1) {
            resetDisXY();
        }
    }

    @Override
    public void drawItemGathering(Canvas canvas, float gatheringX, float gatheringY) {
        //绘制
        if (!isNeedGather) {
            isNeedGather = true;
            setGatherParam(gatheringX, gatheringY);
        }
        //直线运动
        canvas.drawCircle(mX += mDisX, mY += mDisY, mRadius, mPaint);

        
        mGatherCountDown--;
        if(mGatherCountDown == 0f){
            mDisX = 0;
            mDisY = 0;
        }

//        double moveDis = Math.sqrt(Math.pow(mX - mStartX, 2) + Math.pow(mY - mStartY, 2));
        if (mIsNeedChange) {
            //alpha&ratio都会随着运动过程做变化，需要从小到大再变小：因为匀速运动，moveDis是时间线性的，所以可以将moveDis当成时间轴
//            float ratio = (float) ((moveDis / mDistance)) * 5;
//            mPaint.setAlpha((int) ((mAlpha - ALPHA_MIN) * ratio + ALPHA_MIN));
//            mRadius = mStartRadius * (1 - ratio);
        }
    }

    private void setGatherParam(float gatherX, float gatherY) {
        mDisX = (gatherX - mX) / mGatherCountDown;
        mDisY = (gatherY - mY) / mGatherCountDown;
    }

    private int dip2Px(float pxValue) {
        int dp = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, pxValue, Resources.getSystem().getDisplayMetrics());
        return dp;
    }

    private float getPNValue(boolean isAdd, float value) {
        return isAdd ? value : (0 - value);
    }

    private void resetDisXY() {
        setRandomParam();

        mX = mStartX;
        mY = mStartY;
        mRadius = mStartRadius;
    }

    private float getHypotenuse(double x, double y) {
        return (float) Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
    }

    /**
     * 判断粒子当前位置是否在运动区域内
     *
     * @return true在区域内，false不在区域内
     */
    private boolean judgeInner() {
        float judgeWL = INNER_RATIO * mWidth;
        float judgeWR = (1 - INNER_RATIO) * mWidth;

        float judgeHT = INNER_RATIO * mHeight;
        float judgeHB = (1 - INNER_RATIO) * mHeight;

        boolean judgeX = mX >= judgeWL && mX <= judgeWR;
        boolean judgeY = mY >= judgeHT && mY <= judgeHB;
        return judgeX && judgeY;
    }
}
