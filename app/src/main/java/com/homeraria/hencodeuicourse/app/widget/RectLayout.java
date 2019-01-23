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

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;

import com.homeraria.hencodeuicourse.app.R;

/**
 * 识别结果出框动画
 */
public class RectLayout extends RelativeLayout {
    private static String TYPE_CONTENT = "回到正题，这次带来的效果，是一个Android的3D立体旋转的效果。 当然灵感的来源，来自早些时间微博上看到的效果图。 非常酷有木有！";

    private RectPopView mRect;
    private TypeTextView mTitleView, mContentView;
    private int left, right, top, bottom;
    private View parent;

    public RectLayout(Context context) {
        this(context, null);
    }

    public RectLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RectLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

//        this.setClipChildren(false);
//        this.setClipToPadding(false);
        parent = LayoutInflater.from(context).inflate(R.layout.rect_layout, this);

        mRect = parent.findViewById(R.id.rect);
        mTitleView = parent.findViewById(R.id.title);
        mContentView = parent.findViewById(R.id.content);

        mTitleView.setOnTypeViewListener(new TypeTextView.OnTypeViewListener() {
            @Override
            public void onTypeStart() {
            }

            @Override
            public void onTypeOver() {
                mContentView.start(TYPE_CONTENT);

            }
        });
    }

    public void show() {
        mTitleView.start("显示器");
        mRect.startAnim();
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        int top = mRect.getTop();
        this.setTranslationY(-top);
        this.show();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

        super.onLayout(changed, l, t, r, b);



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
