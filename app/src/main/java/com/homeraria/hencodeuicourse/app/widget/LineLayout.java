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
package com.homeraria.hencodeuicourse.app.widget;

import android.animation.Animator;
import android.content.Context;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.homeraria.hencodeuicourse.app.R;

/**
 * 识别结果出框动画
 */
public class LineLayout extends RelativeLayout {
    private static String TYPE_CONTENT = "回到正题，这次带来的效果，是一个Android的3D立体旋转的效果。 当然灵感的来源，来自早些时间微博上看到的效果图。 非常酷有木有！";

    private LinePopView mRect;
    private TypeTextView mTitleView, mContentView;
    private int left, right, top, bottom;
    private View parent;

    public static void addToParent(Context context, ViewGroup parent, RectF rectF){
        LineLayout view = new LineLayout(context);
        float width = rectF.right - rectF.left;
        float height = rectF.bottom - rectF.top;
        float scale = LinePopView.UNDERLINE_LENGTH / width;

        RelativeLayout.LayoutParams params = new LayoutParams((int) (1.5f * LinePopView.UNDERLINE_LENGTH), (int) (scale * height / 2f));

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

//        this.setClipChildren(false);
//        this.setClipToPadding(false);
        parent = LayoutInflater.from(context).inflate(R.layout.line_layout, this, true);

        mRect = parent.findViewById(R.id.rect);
        mTitleView = parent.findViewById(R.id.title);
        mContentView = parent.findViewById(R.id.content);

//        mTitleView.setOnTypeViewListener(new TypeTextView.OnTypeViewListener() {
////            @Override
////            public void onTypeStart() {
////            }
////
////            @Override
////            public void onTypeOver() {
////                mContentView.start(TYPE_CONTENT);
////
////            }
////        });
        mRect.setAnimationListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                mTitleView.start("显示器");
                mContentView.start(TYPE_CONTENT);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }

    public void show() {
        mRect.startAnim();
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();

        this.show();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

        super.onLayout(changed, l, t, r, b);

//        ViewGroup child = (ViewGroup) getChildAt(0);
//
//        for (int i = 0; i < child.getChildCount(); i++) {
//            View grandChild = child.getChildAt(i);
//
//            if (grandChild instanceof TypeTextView) {
//                float width = 0;
////                grandChild.layout((int) (l + width / 3f), t, (int) (r + width / 3f), b);
//            }
//        }

        RelativeLayout.LayoutParams params = (LayoutParams) mTitleView.getLayoutParams();
        RelativeLayout.LayoutParams params1 = (LayoutParams) mContentView.getLayoutParams();
        params.leftMargin = (int) ((r - l) / 3f);
        params1.leftMargin = (int) ((r - l) / 3f);
        mTitleView.setLayoutParams(params);
        mContentView.setLayoutParams(params1);


//        View recChild = getChildAt(0);
//        mRect.layout(l, t, r, b);
//
//        float x = (float)(r - l) / (float)this.getMeasuredWidth();
//        float y = (float)(b - t) / (float)this.getMeasuredHeight();
//        float scale = Math.max(x, y);
//        this.setScaleX(scale);
//        this.setScaleY(scale);
//        this.setPivotX(0f);
//        this.setPivotY(0f);
    }
}
