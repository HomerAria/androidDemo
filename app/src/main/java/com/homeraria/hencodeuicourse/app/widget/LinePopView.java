/*
 * Copyright (c) 2018 Guangdong oppo Mobile Communication(Shanghai)
 * Corp.,Ltd. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are
 * met:
 *     * Redistributions of source code must retain the above copyright
 *       notice, this list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above
 *       copyright notice, this list of conditions and the following
 *       disclaimer in the documentation and/or other materials provided
 *       with the distribution.
 *     * Neither the name of The Linux Foundation nor the names of its
 *       contributors may be used to endorse or promote products derived
 *       from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED "AS IS" AND ANY EXPRESS OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NON-INFRINGEMENT
 * ARE DISCLAIMED.  IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS
 * BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR
 * BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY,
 * WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE
 * OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN
 * IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 * File: RectPopView.java
 * Description:
 *
 * ---------------------------- Revision History: ------------------------
 * <author>             <date>          <version>           <desc>
 * sean.zhou@oppo.com   2019/1/14      1.0                 create this module
 * -----------------------------------------------------------------------
 */
package com.homeraria.hencodeuicourse.app.widget;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.graphics.PathMeasure;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;

import com.homeraria.hencodeuicourse.app.R;
import com.homeraria.hencodeuicourse.app.Utils;

import androidx.annotation.Keep;
import androidx.annotation.Nullable;

/**
 * 折线引出文字，且带有出现动画
 * 两部分：斜向引线+下划线
 */
public class LinePopView extends View {
    public static final float UNDERLINE_LENGTH = Utils.dpToPixel(200);
    private static final float LINE_WIDTH = 6f;
    private static final float SHADOW_RADIUS = 2f;
    private static final float PADDING = LINE_WIDTH + SHADOW_RADIUS;

    private float mWidth;
    private float mHeight;
    private Paint mBorderPaint;
    private float mProcess;
    private boolean isDoAnimation = false;
    private Animator.AnimatorListener mAnimatorListener;

    public LinePopView(Context context) {
        super(context);
    }

    public LinePopView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public LinePopView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Keep
    public void setProcess(float process) {
        mProcess = process;

        invalidate();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        mProcess = 0f;
        mWidth = w;
        mHeight = h;

        mBorderPaint = new Paint();
        mBorderPaint.setStrokeWidth(LINE_WIDTH);
        mBorderPaint.setColor(getResources().getColor(R.color.md_grey_500, null));
        mBorderPaint.setAntiAlias(true);
        mBorderPaint.setDither(true);
        mBorderPaint.setStyle(Paint.Style.STROKE);
        mBorderPaint.setShadowLayer(SHADOW_RADIUS, 0, 0, getResources().getColor(R.color.md_black_1000, null));
        mBorderPaint.setStrokeCap(Paint.Cap.ROUND);
    }

    public void setAnimationListener(Animator.AnimatorListener listener) {
        this.mAnimatorListener = listener;
    }

    public void startAnim() {
        ObjectAnimator animator = ObjectAnimator.ofFloat(this, "process", 0f, 1f);
        animator.setInterpolator(new AccelerateDecelerateInterpolator());
        animator.setDuration(1000);
        animator.start();

        if (mAnimatorListener != null) {
            animator.addListener(mAnimatorListener);
        }

        isDoAnimation = true;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

//        Log.v("sean2", "LinePopView.omMeasure()");
    }

    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas canvas) {
        if (!isDoAnimation) {
            canvas.save();

            Path path = new Path();
            path.moveTo(0, mHeight);
            path.lineTo(mWidth / 3, 0);
            path.lineTo(mWidth, 0);

            canvas.drawPath(path, mBorderPaint);

            canvas.restore();
        } else {
            canvas.save();

            Path path = new Path();
            path.moveTo(PADDING, mHeight - PADDING);
            path.lineTo(mWidth / 3, PADDING);
            path.lineTo(mWidth - PADDING, PADDING);

            /*
            计算path长度
             */
            PathMeasure measure = new PathMeasure(path, false);
            float pathLength = measure.getLength();
            PathEffect effect = new DashPathEffect(new float[]{pathLength, pathLength}, pathLength - pathLength * mProcess);
            mBorderPaint.setPathEffect(effect);

            canvas.drawPath(path, mBorderPaint);

            canvas.restore();
        }
    }
}
