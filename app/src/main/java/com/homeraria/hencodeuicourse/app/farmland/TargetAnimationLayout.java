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
 * File: TypeTextLayout.java
 * Description:
 *
 * ---------------------------- Revision History: ------------------------
 * <author>             <date>          <version>           <desc>
 * sean.zhou@oppo.com   2019/1/14      1.0                 create this module
 * -----------------------------------------------------------------------
 */
package com.homeraria.hencodeuicourse.app.farmland;

import android.content.Context;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.homeraria.component.widget.ComprehensiveLineView;
import com.homeraria.hencodeuicourse.app.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 用于完整测试新的识别结果文字展示动画
 */
public class TargetAnimationLayout extends RelativeLayout {
    private List<RectF> mData = new ArrayList<>();
    private Button mButton;
    private ComprehensiveLineView mTarget;

    public TargetAnimationLayout(Context context) {
        this(context, null);
    }

    public TargetAnimationLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TargetAnimationLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

//        mData.add(new RectF(100, 100, 300, 300));
        mData.add(new RectF(0, 300, 300, 900));
        initViews();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

    }

    private void initViews() {
//        this.post(() -> {
        for (int i = 0; i < mData.size(); i++) {
            ComprehensiveLineView view = new ComprehensiveLineView(getContext());

//                addView(view);


        }
//        });
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();

        mTarget = findViewById(R.id.target);
        mButton = findViewById(R.id.button);
        mButton.setOnClickListener(v-> mTarget.go2ndLevel());
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);

        for (int i = 0; i < this.getChildCount(); i++) {
            View currentView = this.getChildAt(i);
            if (currentView instanceof ComprehensiveLineView) {
                int width = currentView.getMeasuredWidth();
                int height = currentView.getMeasuredHeight();

                int top = - height / 2 + 200;
                currentView.layout(0, 0, width, height + 0);

            }
        }
    }
}
