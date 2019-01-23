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

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;

import com.homeraria.hencodeuicourse.app.R;
import com.homeraria.hencodeuicourse.app.phenas.widget.BreathInterpolator;

import androidx.annotation.Keep;
import androidx.annotation.Nullable;

/**
 * 类似对焦框，且带有出现动画
 */
public class RectPopView extends View {
    private float mWidth;
    private float mHeight;
    private Paint mBorderPaint;
    private float mProcess;
    private boolean isDoAnimation = true;

    public RectPopView(Context context) {
        super(context);
    }

    public RectPopView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public RectPopView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
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
        mBorderPaint.setStrokeWidth(6);
        mBorderPaint.setColor(getResources().getColor(R.color.md_grey_500, null));
        mBorderPaint.setAntiAlias(true);
        mBorderPaint.setDither(true);
    }

    public void startAnim() {
        ObjectAnimator animator = ObjectAnimator.ofFloat(this, "process", 0f, 1f);
        animator.setInterpolator(new AccelerateDecelerateInterpolator());
        animator.setDuration(1000);
        animator.start();

        isDoAnimation = true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (!isDoAnimation) {
            canvas.save();
            canvas.drawLine(0, 0, mWidth / 3, 0, mBorderPaint);
            canvas.drawLine(0, 0, 0, mHeight / 3, mBorderPaint);

            canvas.drawLine(mWidth, 0, 2 * mWidth / 3, 0, mBorderPaint);
            canvas.drawLine(mWidth, 0, mWidth, mHeight / 3, mBorderPaint);


            canvas.drawLine(0, mHeight, mWidth / 3, mHeight, mBorderPaint);
            canvas.drawLine(0, mHeight, 0, 2 * mHeight / 3, mBorderPaint);


            canvas.drawLine(mWidth, mHeight, 2 * mWidth / 3, mHeight, mBorderPaint);
            canvas.drawLine(mWidth, mHeight, mWidth, 2 * mHeight / 3, mBorderPaint);

            canvas.restore();
        } else {
            canvas.save();
            canvas.drawLine(0, 0, mProcess * mWidth / 3, 0, mBorderPaint);
            canvas.drawLine(0, 0, 0, mProcess * mHeight / 3, mBorderPaint);

            canvas.drawLine(mWidth, 0,  (mWidth - mProcess *mWidth / 3), 0, mBorderPaint);
            canvas.drawLine(mWidth, 0, mWidth, mProcess * mHeight / 3, mBorderPaint);


            canvas.drawLine(0, mHeight, mProcess * mWidth / 3, mHeight, mBorderPaint);
            canvas.drawLine(0, mHeight, 0,  (mHeight - mProcess *mHeight / 3), mBorderPaint);


            canvas.drawLine(mWidth, mHeight,  (mWidth - mProcess *mWidth / 3), mHeight, mBorderPaint);
            canvas.drawLine(mWidth, mHeight, mWidth,  (mHeight - mProcess *mHeight / 3), mBorderPaint);

            canvas.restore();
        }
    }
}
