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

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Property;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.OvershootInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.homeraria.component.R;
import com.homeraria.component.utils.Utils;

public class PhantomCircleSwitch extends RelativeLayout {
    private static final float BAR_HEIGHT = Utils.dpToPixel(60);
    private static final float NORMAL_LENGTH = Utils.dpToPixel(30);
    private static final float SMALL_LENGTH = Utils.dpToPixel(10);

    private ArcSideRectView mBorderButtonA, mBorderButtonB, mBorderButtonC, mCenterView;    //永远处于屏幕左侧不懂的按钮
    private ArcSideRectView mFunctionButtonA, mFunctionButtonB, mFunctionButtonC;     //三个功能按钮
    private View mLine;
    private LottieAnimationView mOuterCircle;
    private boolean isInit = true;
    private int mCurrentSelected = 0;
    private boolean isExpand = false;

    private float mScreenWidth;
    private int mMargin;
    private float mProcess = 0f;

    public float getProcess() {
        return mProcess;
    }

    public void setProcess(float mProcess) {
        this.mProcess = mProcess;
        requestLayout();
    }

    public PhantomCircleSwitch(Context context) {
        super(context);
        init();
    }

    public PhantomCircleSwitch(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public PhantomCircleSwitch(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.view_circle_switch, this, true);
        mCenterView = findViewById(R.id.center);
        mFunctionButtonA = findViewById(R.id.function_a);
        mFunctionButtonB = findViewById(R.id.function_b);
        mFunctionButtonC = findViewById(R.id.function_c);
        mOuterCircle = findViewById(R.id.outer_round);

        mBorderButtonA = findViewById(R.id.border_a);
        mBorderButtonA.setBorder(true);

        mBorderButtonB = findViewById(R.id.border_b);
        mBorderButtonB.setBorder(true);
        mBorderButtonB.setAlpha(0);

        mBorderButtonC = findViewById(R.id.border_c);
        mBorderButtonC.setBorder(true);
        mBorderButtonC.setAlpha(0);

        mFunctionButtonB.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "click", Toast.LENGTH_SHORT).show();

                if (mCurrentSelected == 0) {
                    ObjectAnimator alpha = ObjectAnimator.ofFloat(mBorderButtonA, "alpha", 1f, 0f);
                    ObjectAnimator alpha1 = ObjectAnimator.ofFloat(mBorderButtonB, "alpha", 0f, 1f);

                    AnimatorSet animatorSet = new AnimatorSet();
                    animatorSet.setInterpolator(new OvershootInterpolator());
                    animatorSet.setDuration(1000);
                    animatorSet.playTogether(alpha, alpha1);
                    animatorSet.start();
                }else if(mCurrentSelected == 2){
                    ObjectAnimator alpha = ObjectAnimator.ofFloat(mBorderButtonC, "alpha", 1f, 0f);
                    ObjectAnimator alpha1 = ObjectAnimator.ofFloat(mBorderButtonB, "alpha", 0f, 1f);

                    AnimatorSet animatorSet = new AnimatorSet();
                    animatorSet.setInterpolator(new OvershootInterpolator());
                    animatorSet.setDuration(1000);
                    animatorSet.playTogether(alpha, alpha1);
                    animatorSet.start();
                }

                mCurrentSelected = 1;
            }
        });

        mFunctionButtonA.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "click", Toast.LENGTH_SHORT).show();

                if (mCurrentSelected == 1) {
                    ObjectAnimator alpha = ObjectAnimator.ofFloat(mBorderButtonB, "alpha", 1f, 0f);
                    ObjectAnimator alpha1 = ObjectAnimator.ofFloat(mBorderButtonA, "alpha", 0f, 1f);

                    AnimatorSet animatorSet = new AnimatorSet();
                    animatorSet.setInterpolator(new OvershootInterpolator());
                    animatorSet.setDuration(1000);
                    animatorSet.playTogether(alpha, alpha1);
                    animatorSet.start();
                }else if(mCurrentSelected == 2){
                    ObjectAnimator alpha = ObjectAnimator.ofFloat(mBorderButtonC, "alpha", 1f, 0f);
                    ObjectAnimator alpha1 = ObjectAnimator.ofFloat(mBorderButtonA, "alpha", 0f, 1f);

                    AnimatorSet animatorSet = new AnimatorSet();
                    animatorSet.setInterpolator(new OvershootInterpolator());
                    animatorSet.setDuration(1000);
                    animatorSet.playTogether(alpha, alpha1);
                    animatorSet.start();
                }

                mCurrentSelected = 0;
            }
        });

        mFunctionButtonC.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "click", Toast.LENGTH_SHORT).show();

                if (mCurrentSelected == 0) {
                    ObjectAnimator alpha = ObjectAnimator.ofFloat(mBorderButtonA, "alpha", 1f, 0f);
                    ObjectAnimator alpha1 = ObjectAnimator.ofFloat(mBorderButtonC, "alpha", 0f, 1f);

                    AnimatorSet animatorSet = new AnimatorSet();
                    animatorSet.setInterpolator(new OvershootInterpolator());
                    animatorSet.setDuration(1000);
                    animatorSet.playTogether(alpha, alpha1);
                    animatorSet.start();
                }else if(mCurrentSelected == 1){
                    ObjectAnimator alpha = ObjectAnimator.ofFloat(mBorderButtonB, "alpha", 1f, 0f);
                    ObjectAnimator alpha1 = ObjectAnimator.ofFloat(mBorderButtonC, "alpha", 0f, 1f);

                    AnimatorSet animatorSet = new AnimatorSet();
                    animatorSet.setInterpolator(new OvershootInterpolator());
                    animatorSet.setDuration(1000);
                    animatorSet.playTogether(alpha, alpha1);
                    animatorSet.start();
                }

                mCurrentSelected = 2;
            }
        });

    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        mScreenWidth = w;
    }

    @SuppressLint("DrawAllocation")
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);

        if (changed) {
            RotateAnimation animation0 = new RotateAnimation(0, 360
                    , mOuterCircle.getWidth() / 2, mOuterCircle.getHeight() / 2);
            animation0.setDuration(50000);
            animation0.setRepeatCount(-1);
            animation0.setInterpolator(new LinearInterpolator());
            mOuterCircle.startAnimation(animation0);

            RotateAnimation animation = new RotateAnimation(0, 120
                    , mFunctionButtonB.getWidth() / 2, PhantomCircleSwitch.this.getHeight() / 2 - mFunctionButtonB.getTop());
            animation.setDuration(500);
            animation.setInterpolator(new OvershootInterpolator());
            animation.setFillAfter(true);
            animation.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    mFunctionButtonB.setPivotX(mFunctionButtonB.getWidth() / 2);
                    mFunctionButtonB.setPivotY(PhantomCircleSwitch.this.getHeight() / 2 - mFunctionButtonB.getTop());
                    mFunctionButtonB.setRotation(120);

                    mBorderButtonB.setPivotX(mBorderButtonB.getWidth() / 2);
                    mBorderButtonB.setPivotY(PhantomCircleSwitch.this.getHeight() / 2 - mBorderButtonB.getTop());
                    mBorderButtonB.setRotation(120);
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
            mFunctionButtonB.startAnimation(animation);


            RotateAnimation animation2 = new RotateAnimation(0, 240
                    , mFunctionButtonC.getWidth() / 2, PhantomCircleSwitch.this.getHeight() / 2 - mFunctionButtonC.getTop());
            animation2.setDuration(500);
            animation2.setInterpolator(new OvershootInterpolator());
            animation2.setFillAfter(true);
            animation2.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    mFunctionButtonC.setPivotX(mFunctionButtonC.getWidth() / 2);
                    mFunctionButtonC.setPivotY(PhantomCircleSwitch.this.getHeight() / 2 - mFunctionButtonC.getTop());
                    mFunctionButtonC.setRotation(-120);

                    mBorderButtonC.setPivotX(mBorderButtonC.getWidth() / 2);
                    mBorderButtonC.setPivotY(PhantomCircleSwitch.this.getHeight() / 2 - mBorderButtonC.getTop());
                    mBorderButtonC.setRotation(-120);
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
            mFunctionButtonC.startAnimation(animation2);

        }

    }


    public static final Property<PhantomCircleSwitch, Float> PROCESS = new Property<PhantomCircleSwitch, Float>(Float.class, "process") {
        @Override
        public Float get(PhantomCircleSwitch object) {
            return object.getProcess();
        }

        @Override
        public void set(PhantomCircleSwitch object, Float value) {
            object.setProcess(value);
        }
    };
}
