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
 * File: TargetSimpleLayout.java
 * Description:
 *
 * ---------------------------- Revision History: ------------------------
 * <author>             <date>          <version>           <desc>
 * sean.zhou@oppo.com   2019/2/18         1.0                 create this module
 * -----------------------------------------------------------------------
 */
package com.homeraria.hencodeuicourse.app.farmland;

import android.content.Context;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.homeraria.component.widget.ProgressBarCircularIndeterminate;
import com.homeraria.hencodeuicourse.app.R;
import com.homeraria.hencodeuicourse.app.widget.LineLayoutApp;
import com.homeraria.hencodeuicourse.app.widget.LinePopViewApp;

import java.util.ArrayList;
import java.util.List;

public class TargetSimpleLayout extends RelativeLayout {
    private Button mButton;
    private ProgressBarCircularIndeterminate mTarget;

    private List<RectF> mData = new ArrayList<>();
    private int centerX1, centerY1;

    public TargetSimpleLayout(Context context) {
        this(context, null);
    }

    public TargetSimpleLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TargetSimpleLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();

        mButton = findViewById(R.id.button);
        mTarget = findViewById(R.id.target);

        mButton.setOnClickListener(v -> {
            mTarget.doClick();
            if (TargetSimpleLayout.this.getChildCount() > 2) {
                TargetSimpleLayout.this.removeViewAt(2);
            }
        });

        mTarget.setEndListener((centerX, centerY) -> {
            /*
            模拟rough分类结束，收到精确分类的回调
             */
            RectF rectF = new RectF(centerX1, centerY1 - 250, centerX1 + 500, centerY1);
            mData.clear();
            mData.add(rectF);

            LineLayoutApp.addToParent(getContext(), TargetSimpleLayout.this, rectF);
            requestLayout();

        });
    }


    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
//        if (!changed) return;
        centerX1 = mTarget.getLeft() + mTarget.getWidth() / 2;
        centerY1 = mTarget.getTop() + mTarget.getHeight() / 2;

        Log.v("sean2", "TypeTextLayout.onLayout()");

        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            Log.v("sean1", "onLayout, i=" + i + ", class=" + child.getId() + ", change=" + changed);

            if (child instanceof LineLayoutApp) {
                RectF rect = mData.get(0);

                float width = rect.right - rect.left;
                float height = rect.bottom - rect.top;
                float scale = LinePopViewApp.UNDERLINE_LENGTH / width;

                int right = (int) (rect.left + (int) (1.5f * LinePopViewApp.UNDERLINE_LENGTH));
                int bottom = (int) (rect.top + (int) (scale * height / 0.5f));

                float scale1 = (rect.right - rect.left) / child.getMeasuredWidth();
                child.setScaleX(scale1);
                child.setScaleY(scale1);
                child.setPivotX(0f);
                child.setPivotY(0f);

                Log.v("sean2", "TypeTextLayout.onLayout(), " + "left:" + rect.left);

                child.layout((int) rect.left, (int) rect.top, right, bottom);
            }
        }
    }
}
