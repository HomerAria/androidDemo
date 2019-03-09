/*
 * Copyright (c) 2019 Guangdong oppo Mobile Communication(Shanghai)
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
 * File: PhantomBottomBar.java
 * Description:
 *
 * ---------------------------- Revision History: ------------------------
 * <author>             <date>          <version>           <desc>
 * sean.zhou@oppo.com   2019/3/8         1.0                 create this module
 * -----------------------------------------------------------------------
 */
package com.homeraria.component.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;

import com.homeraria.component.R;
import com.homeraria.component.utils.Utils;

public class PhantomBottomBar extends RelativeLayout {
    private static final float BAR_HEIGHT = Utils.dpToPixel(60);
    private static final float NORMAL_LENGTH = Utils.dpToPixel(30);
    private static final float SMALL_LENGTH = Utils.dpToPixel(10);

    private ArcSideRectView mBaseButton;    //永远处于屏幕左侧不懂的按钮
    private ArcSideRectView mFunctionButtonA, mFunctionButtonB, mFunctionButtonC;     //三个功能按钮
    private View mLine;
    private boolean isInit = true;

    private float mScreenWidth;
    private int mMargin;

    public PhantomBottomBar(Context context) {
        super(context);
        init();
    }

    public PhantomBottomBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public PhantomBottomBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.view_bottom_bar, this, true);
        mBaseButton = findViewById(R.id.base);
        mFunctionButtonA = findViewById(R.id.function_a);
        mFunctionButtonB = findViewById(R.id.function_b);
        mFunctionButtonC = findViewById(R.id.function_c);
        mLine = findViewById(R.id.line);

        mBaseButton.setOnClickListener(v -> {
            if(isInit){
                isInit = false;
                requestLayout();
            }
        });
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        mScreenWidth = w;
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);

        if(isInit){
            return;
        }

        mMargin = (int) (mScreenWidth - mBaseButton.getRight());

        int top = mFunctionButtonA.getTop();
        mFunctionButtonA.layout(mMargin, top, mMargin + mFunctionButtonA.getWidth(), top + mFunctionButtonA.getHeight());

        mFunctionButtonB.setPivotX(mFunctionButtonB.getWidth() / 2);
        mFunctionButtonB.setPivotY(mFunctionButtonB.getHeight() / 2);
        mFunctionButtonB.setScaleX(0.5F);
        mFunctionButtonB.setScaleY(0.5F);

        mFunctionButtonB.layout(mMargin + 200, top, mMargin + 200 + mFunctionButtonA.getWidth(), top + mFunctionButtonA.getHeight());

        mFunctionButtonC.setPivotX(0);
        mFunctionButtonC.setPivotY(mFunctionButtonB.getHeight() / 2);
        mFunctionButtonC.setScaleX(0.5F);
        mFunctionButtonC.setScaleY(0.5F);

        mFunctionButtonC.layout(mMargin + 400, top, mMargin + 400 + mFunctionButtonA.getWidth(), top + mFunctionButtonA.getHeight());

        /*
        动态配置连线的长度
         */
        mLine.layout(mFunctionButtonA.getRight() + 10, mLine.getTop(), mBaseButton.getLeft() - 10, mLine.getBottom());
    }
}
