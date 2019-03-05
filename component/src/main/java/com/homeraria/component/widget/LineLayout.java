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
 * File: RectLayout.java
 * Description:
 *
 * ---------------------------- Revision History: ------------------------
 * <author>             <date>          <version>           <desc>
 * sean.zhou@oppo.com   2019/1/14      1.0                 create this module
 * -----------------------------------------------------------------------
 */
package com.homeraria.component.widget;

import android.animation.Animator;
import android.content.Context;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.homeraria.component.R;

/**
 * 识别结果出框动画
 */
public class LineLayout extends RelativeLayout {
    public static final float LINE_RATIO = 6f;

    private String mContent = "回到正题，这次带来的效果，是一个Android的3D立体旋转的效果。 当然灵感的来源，来自早些时间微博上看到的效果图。 非常酷有木有！";
    private String mTitle = "title";

    private LinePopView mRect;
    private TypeTextView mTitleView, mContentView;
    private int left, right, top, bottom;
    private View parent;

    public static void addToParent(Context context, ViewGroup parent, RectF rectF) {
        LineLayout view = new LineLayout(context);
        addToParent(view, parent, rectF);
    }

    public static void addToParent(LineLayout view, ViewGroup parent, RectF rectF) {
        float width = rectF.right - rectF.left;
        float height = rectF.bottom - rectF.top;
        float scale = LinePopView.UNDERLINE_LENGTH / width;

        LayoutParams params = new LayoutParams((int) (1.5f * LinePopView.UNDERLINE_LENGTH), (int) (scale * height / 2f));

        parent.addView(view, params);
    }

    public LineLayout(Context context) {
        this(context, null);
    }

    public LineLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LineLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        parent = LayoutInflater.from(context).inflate(R.layout.line_layout, this, true);

        mRect = parent.findViewById(R.id.rect);
        mTitleView = parent.findViewById(R.id.title);
        mContentView = parent.findViewById(R.id.content);

        mTitleView.setOnTypeViewListener(new TypeTextView.OnTypeViewListener() {
            @Override
            public void onTypeStart() {

            }

            @Override
            public void onTypeOver() {
            }
        });

        mContentView.setOnTypeViewListener(new TypeTextView.OnTypeViewListener() {
            @Override
            public void onTypeStart() {

            }

            @Override
            public void onTypeOver() {
            }
        });

        mRect.setAnimationListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                mTitleView.start(mTitle);
                mTitleView.setBackgroundResource(R.drawable.line_text_background);
                mContentView.start(mContent);
                mContentView.setBackgroundResource(R.drawable.line_text_background);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }

    public String getTitle() {
        return mTitle;
    }

    public void show() {
        Log.v("sean3", "LineLayout.show:" + this.getId());
        mRect.startAnim();
    }

    public void setContent(String title, String content) {
        this.mTitle = title;
        this.mContent = content;
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();

//        this.show();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int width = getMeasuredWidth();
        setMeasuredDimension(width, width);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);

        LayoutParams params2 = (LayoutParams) mRect.getLayoutParams();
//        mRect.layout(params2.);

        /*
        重新配置文字位置
         */
        LayoutParams params = (LayoutParams) mTitleView.getLayoutParams();
        LayoutParams params1 = (LayoutParams) mContentView.getLayoutParams();
        params.leftMargin = (int) ((r - l) / LINE_RATIO);
        params1.leftMargin = (int) ((r - l) / LINE_RATIO);
        mTitleView.setLayoutParams(params);
        mContentView.setLayoutParams(params1);
    }
}
