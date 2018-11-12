package com.homeraria.hencodeuicourse.app.phenas.particle;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;

import java.util.Random;

/**
 * @author sean
 * @describe 单个旋转Bitmap粒子：每个粒子运动速度相同，旋转速度相同，只是开始位置和开始旋转角度不同，且遇到边框会反弹
 * @email sean.zhou@oppo.com
 * @date on 2018/11/10 16:27
 */
public class BitmapParticle implements BaseParticle {

    private int mWidth, mHeight;     //moving area
    private float mX, mY, mStartX, mStartY, mDisX, mDisY;     //position
    private boolean mIsAddX, mIsAddY;    //x,y轴方向

    private Bitmap mDrawBitmap;
    private float mBitmapCenterX, mBitmapCenterY;
    private int mDrawBitmapWidth, mDrawBitmapHeight;
    private float mDegrees, mDisDegree;

    private Random mRandomGenerator;
    private Paint mPaint;
    private Matrix mBitmapMatrix;

    public BitmapParticle(Bitmap drawBitmap, Matrix matrix, Paint paint, Random random, int width, int height) {
        this.mWidth = width;
        this.mHeight = height;
        this.mRandomGenerator = random;
        this.mPaint = paint;
        this.mDrawBitmap = drawBitmap;
        this.mBitmapMatrix = matrix;

        mDrawBitmapWidth = drawBitmap.getWidth();
        mDrawBitmapHeight = drawBitmap.getHeight();

        mBitmapCenterX = mDrawBitmapWidth / 2;
        mBitmapCenterY = mDrawBitmapHeight / 2;

        setRandomGenerator();
    }

    private void setRandomGenerator() {
        mStartX = mRandomGenerator.nextFloat() * mWidth;
        mStartY = mRandomGenerator.nextFloat() * mHeight;
        mX = mStartX;
        mY = mStartY;

        mIsAddX = mRandomGenerator.nextBoolean();
        mIsAddY = mRandomGenerator.nextBoolean();

        mDisX = (float) mRandomGenerator.nextGaussian();
        mDisY = 1 - mDisX;    //x,y方向运动速度需要相互关联，防止出现两者都是0而使粒子静止的情况

        mDegrees =mRandomGenerator.nextInt(360);
        mDisDegree = mRandomGenerator.nextInt(5) + 3f;
    }


    @Override
    public void drawItem(Canvas canvas) {
        //绘制
        mBitmapMatrix.reset();
        mBitmapMatrix.preTranslate(mX += getPNValue(mIsAddX, mDisX), mY += getPNValue(mIsAddY, mDisY));
        mBitmapMatrix.preRotate(mDegrees += mDisDegree, mBitmapCenterX, mBitmapCenterY);
        canvas.drawBitmap(mDrawBitmap, mBitmapMatrix, mPaint);
        judgeOutline();
    }

    private void judgeOutline() {
        boolean judgeX = mX <= 0 || mX >= (mWidth - mDrawBitmapWidth);
        boolean judgeY = mY <= 0 || mY >= (mHeight - mDrawBitmapHeight);
        if (judgeX) {
            mIsAddX = !mIsAddX;
            mIsAddY = mRandomGenerator.nextBoolean();
//            setRandomParm();
            if (mX <= 0) {
                mX = 0;
            } else {
                mX = mWidth - mDrawBitmapWidth;
            }
            return;
        }
        if (judgeY) {
            mIsAddY = !mIsAddY;
            mIsAddX = mRandomGenerator.nextBoolean();
//            setRandomParm();
            if (mY <= 0) {
                mY = 0;
            } else {
                mY = mHeight - mDrawBitmapHeight;
            }
        }
    }

    private float getPNValue(boolean isAdd, float value) {
        return isAdd ? value : (0 - value);
    }
}
