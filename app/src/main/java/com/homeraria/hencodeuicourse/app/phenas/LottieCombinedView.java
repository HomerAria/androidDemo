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
 * File: LottieCombinedView.java
 * Description:
 *
 * ---------------------------- Revision History: ------------------------
 * <author>             <date>          <version>           <desc>
 * sean.zhou@oppo.com   2019/3/9         1.0                 create this module
 * -----------------------------------------------------------------------
 */
package com.homeraria.hencodeuicourse.app.phenas;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.airbnb.lottie.LottieAnimationView;
import com.homeraria.component.widget.ArcSideRectView;

public class LottieCombinedView extends RelativeLayout {
    private static final int GENTLE_DURATION = 500;    //淡入淡出动画持续时间

    private LottieAnimationView mBorderViewA, mBorderViewB, mBorderViewC, mBorderViewD;
    private ArcSideRectView mAvatar;
    private Button mButton;
    private boolean hasFurther = false;

    public LottieCombinedView(Context context) {
        super(context);
        init();
    }

    public LottieCombinedView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public LottieCombinedView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        LayoutInflater.from(getContext()).inflate(com.homeraria.component.R.layout.view_lottie_combined, this, true);
        mBorderViewA = findViewById(com.homeraria.component.R.id.border_a);
        mBorderViewB = findViewById(com.homeraria.component.R.id.border_b);
        mBorderViewC = findViewById(com.homeraria.component.R.id.border_c);
        mBorderViewD = findViewById(com.homeraria.component.R.id.border_d);
        mAvatar = findViewById(com.homeraria.component.R.id.img);
        mButton = findViewById(com.homeraria.component.R.id.button);

        mButton.setOnClickListener(v -> {
            if(!hasFurther) {
                hasFurther = true;
            }else{
                hasFurther = false;
                mAvatar.setVisibility(GONE);

                mBorderViewD.addAnimatorListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        mBorderViewD.setVisibility(INVISIBLE);
                        mBorderViewC.setVisibility(VISIBLE);
                    }
                });
                mBorderViewD.reverseAnimationSpeed();
                mBorderViewD.playAnimation();
            }
        });
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();

        mAvatar.setVisibility(INVISIBLE);
        mBorderViewB.setVisibility(INVISIBLE);
        mBorderViewC.setVisibility(INVISIBLE);
        mBorderViewD.setVisibility(INVISIBLE);

        mBorderViewA.addAnimatorListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mBorderViewA.setVisibility(INVISIBLE);
                mBorderViewB.setVisibility(VISIBLE);

                mBorderViewB.playAnimation();
            }
        });

//        mBorderViewB.reverseAnimationSpeed();
        mBorderViewB.addAnimatorListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mBorderViewB.setVisibility(INVISIBLE);
                mBorderViewC.setVisibility(VISIBLE);

                mBorderViewC.playAnimation();
            }
        });

        mBorderViewC.addAnimatorListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationRepeat(Animator animation) {
                if (mBorderViewC.getVisibility() == INVISIBLE){
                    return;
                }

                if(!hasFurther){
                    return;
                }

                mBorderViewC.setVisibility(INVISIBLE);

                mBorderViewD.setVisibility(VISIBLE);

                mBorderViewD.playAnimation();
            }
        });

        mBorderViewD.addAnimatorListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                AlphaAnimation animation1 = new AlphaAnimation(0f, 1f);
                animation1.setDuration(GENTLE_DURATION);
                animation1.setInterpolator(new DecelerateInterpolator());
                animation1.setFillAfter(true);
                animation1.setAnimationListener(new Animation.AnimationListener() {
                    @SuppressLint("ClickableViewAccessibility")
                    @Override
                    public void onAnimationStart(Animation animation) {
                        mAvatar.setVisibility(VISIBLE);
                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
                mAvatar.startAnimation(animation1);
            }
        });

        mBorderViewA.playAnimation();
    }
}
