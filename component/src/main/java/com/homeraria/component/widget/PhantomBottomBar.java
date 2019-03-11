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

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Property;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.OvershootInterpolator;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.homeraria.component.R;
import com.homeraria.component.utils.Utils;

import androidx.interpolator.view.animation.LinearOutSlowInInterpolator;

public class PhantomBottomBar extends RelativeLayout {
    private static final float BAR_HEIGHT = Utils.dpToPixel(60);
    private static final float NORMAL_LENGTH = Utils.dpToPixel(30);
    private static final float SMALL_LENGTH = Utils.dpToPixel(10);

    private ArcSideRectView mBaseButton;    //永远处于屏幕左侧不懂的按钮
    private ArcSideRectView mFunctionButtonA, mFunctionButtonB, mFunctionButtonC;     //三个功能按钮
    private View mLine;
    private boolean isInit = true;
    private int mCurrentSelected = 0, mLastSelected = 0;
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
//            if(isInit){
//                isInit = false;
//                requestLayout();
//            }

//            float scale = (mScreenWidth - 2 * mMargin - mFunctionButtonA.getWidth()) / mScreenWidth;
//            TranslateAnimation animation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_PARENT, -scale,
//                    Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0);
//            animation.setDuration(500);
//            animation.setInterpolator(new LinearOutSlowInInterpolator());
//            animation.setFillAfter(true);
//            mFunctionButtonA.startAnimation(animation);
//
//            float scale1 = (mScreenWidth - 2 * mMargin - mFunctionButtonA.getWidth() - 200) / mScreenWidth;
//            TranslateAnimation animation1 = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_PARENT, -scale1,
//                    Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0);
//            animation1.setDuration(500);
//            animation1.setInterpolator(new LinearOutSlowInInterpolator());
//            animation1.setFillAfter(true);
//            mFunctionButtonB.startAnimation(animation1);
//
//            float scale2 = (mScreenWidth - 2 * mMargin - mFunctionButtonA.getWidth() - 400) / mScreenWidth;
//            TranslateAnimation animation2 = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_PARENT, -scale2,
//                    Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0);
//            animation2.setDuration(500);
//            animation2.setInterpolator(new LinearOutSlowInInterpolator());
//            animation2.setFillAfter(true);
//            mFunctionButtonC.startAnimation(animation2);
//
//            ScaleAnimation animation3 = new ScaleAnimation(0,-30,1,1,1f,0.5f);
//            animation3.setDuration(500);
//            animation3.setInterpolator(new LinearOutSlowInInterpolator());
//            animation3.setFillAfter(true);
//            mLine.startAnimation(animation3);

            if (!isExpand) {
                ObjectAnimator animator = ObjectAnimator.ofFloat(PhantomBottomBar.this, PhantomBottomBar.PROCESS, 0, 1f);
                animator.setDuration(600);
                animator.setStartDelay(0);
                animator.setInterpolator(new LinearOutSlowInInterpolator());
                animator.start();

                mFunctionButtonA.setScaleX(1F);
                mFunctionButtonA.setScaleY(1F);

                mFunctionButtonB.setScaleX(0.5F);
                mFunctionButtonB.setScaleY(0.5F);

                mFunctionButtonC.setScaleX(0.5F);
                mFunctionButtonC.setScaleY(0.5F);

                mCurrentSelected = 0;
                isExpand = true;
            }else{
                ObjectAnimator animator = ObjectAnimator.ofFloat(PhantomBottomBar.this, PhantomBottomBar.PROCESS, 1, 0f);
                animator.setDuration(600);
                animator.setStartDelay(0);
                animator.setInterpolator(new LinearOutSlowInInterpolator());
                animator.start();

                isExpand = false;
            }
        });

        mFunctionButtonA.setOnClickListener(v -> {
            if (mCurrentSelected == 1) {
                b2a();

                mCurrentSelected = 0;
            } else if (mCurrentSelected == 2) {
                c2a();

                mCurrentSelected = 0;
            }
        });

        mFunctionButtonB.setOnClickListener(v -> {
            if (mCurrentSelected == 0) {
                a2b();

                mCurrentSelected = 1;
            } else if (mCurrentSelected == 2) {
                c2b();

                mCurrentSelected = 1;
            }
        });

        mFunctionButtonC.setOnClickListener(v -> {
            if (mCurrentSelected == 1) {
                b2c();

                mCurrentSelected = 2;
            } else if (mCurrentSelected == 0) {
                a2c();


                mCurrentSelected = 2;
            }
        });
    }

    private void a2c() {
    /*
    需要额外移动中间的按钮
     */
        ObjectAnimator trans = ObjectAnimator.ofFloat(mFunctionButtonB, "translationX", 0, -mFunctionButtonA.getWidth() * 0.5f);
        trans.setDuration(200);
        trans.setInterpolator(new OvershootInterpolator());
        trans.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mFunctionButtonB.setPivotX(mFunctionButtonB.getWidth() / 2);
                mFunctionButtonB.setPivotY(mFunctionButtonB.getHeight() / 2);

                int top = mFunctionButtonA.getTop();
                int left1 = (int) (mBaseButton.getLeft() - (mScreenWidth - 2 * mMargin - mFunctionButtonA.getWidth() / 2 - BAR_HEIGHT) * mProcess);
                mFunctionButtonB.layout(left1, top, left1 + mFunctionButtonA.getWidth(), top + mFunctionButtonA.getHeight());
                mFunctionButtonB.setTranslationX(0);
            }
        });
        trans.start();

        ObjectAnimator scaleX = ObjectAnimator.ofFloat(mFunctionButtonC, "scaleX", 0.5f, 1f);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(mFunctionButtonC, "scaleY", 0.5f, 1f);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setInterpolator(new OvershootInterpolator());
        animatorSet.setDuration(200);
        animatorSet.playTogether(scaleX, scaleY);
        animatorSet.start();

        ObjectAnimator scaleX1 = ObjectAnimator.ofFloat(mFunctionButtonA, "scaleX", 1f, 0.5f);
        ObjectAnimator scaleY1 = ObjectAnimator.ofFloat(mFunctionButtonA, "scaleY", 1f, 0.5f);
        AnimatorSet animatorSet1 = new AnimatorSet();
        animatorSet1.setInterpolator(new OvershootInterpolator());
        animatorSet1.setDuration(200);
        animatorSet1.playTogether(scaleX1, scaleY1);
        animatorSet1.start();
    }

    private void b2c() {
        int top = mFunctionButtonA.getTop();
        int right = (int) ((mFunctionButtonB.getRight() + mFunctionButtonB.getLeft()) / 2 + BAR_HEIGHT * 0.5);
        mFunctionButtonB.layout(right - mFunctionButtonA.getWidth(), top, right, top + mFunctionButtonA.getHeight());

        mFunctionButtonB.setPivotX(0);
        mFunctionButtonB.setPivotY(mFunctionButtonB.getHeight() / 2);

        ObjectAnimator scaleX = ObjectAnimator.ofFloat(mFunctionButtonC, "scaleX", 0.5f, 1f);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(mFunctionButtonC, "scaleY", 0.5f, 1f);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setInterpolator(new OvershootInterpolator());
        animatorSet.setDuration(200);
        animatorSet.playTogether(scaleX, scaleY);
        animatorSet.start();

        ObjectAnimator scaleX1 = ObjectAnimator.ofFloat(mFunctionButtonB, "scaleX", 1f, 0.5f);
        ObjectAnimator scaleY1 = ObjectAnimator.ofFloat(mFunctionButtonB, "scaleY", 1f, 0.5f);
        AnimatorSet animatorSet1 = new AnimatorSet();
        animatorSet1.setInterpolator(new OvershootInterpolator());
        animatorSet1.setDuration(200);
        animatorSet1.playTogether(scaleX1, scaleY1);
        animatorSet1.start();
    }

    private void c2b() {
        int top = mFunctionButtonA.getTop();
        int left1 = (int) (mFunctionButtonA.getLeft() + mFunctionButtonA.getWidth() / 2 + BAR_HEIGHT * 0.25);
        mFunctionButtonB.layout(left1, top, left1 + mFunctionButtonA.getWidth(), top + mFunctionButtonA.getHeight());

        mFunctionButtonB.setPivotX(0);
        mFunctionButtonB.setPivotY(mFunctionButtonB.getHeight() / 2);

        ObjectAnimator scaleX = ObjectAnimator.ofFloat(mFunctionButtonB, "scaleX", 0.5f, 1f);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(mFunctionButtonB, "scaleY", 0.5f, 1f);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setInterpolator(new OvershootInterpolator());
        animatorSet.setDuration(200);
        animatorSet.playTogether(scaleX, scaleY);
        animatorSet.start();

        ObjectAnimator scaleX1 = ObjectAnimator.ofFloat(mFunctionButtonC, "scaleX", 1f, 0.5f);
        ObjectAnimator scaleY1 = ObjectAnimator.ofFloat(mFunctionButtonC, "scaleY", 1f, 0.5f);
        AnimatorSet animatorSet1 = new AnimatorSet();
        animatorSet1.setInterpolator(new OvershootInterpolator());
        animatorSet1.setDuration(200);
        animatorSet1.playTogether(scaleX1, scaleY1);
        animatorSet1.start();
    }

    private void a2b() {
        int top = mFunctionButtonA.getTop();
        int left1 = (int) (mFunctionButtonA.getLeft() + mFunctionButtonA.getWidth() / 2 + BAR_HEIGHT * 0.25);
//                    int right = (int) ((mFunctionButtonB.getRight() + mFunctionButtonB.getLeft())/2 + BAR_HEIGHT * 0.25);
        mFunctionButtonB.layout(left1, top, left1 + mFunctionButtonA.getWidth(), top + mFunctionButtonA.getHeight());

        mFunctionButtonB.setPivotX(mFunctionButtonB.getWidth());
        mFunctionButtonB.setPivotY(mFunctionButtonB.getHeight() / 2);

        ObjectAnimator scaleX = ObjectAnimator.ofFloat(mFunctionButtonB, "scaleX", 0.5f, 1f);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(mFunctionButtonB, "scaleY", 0.5f, 1f);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setInterpolator(new OvershootInterpolator());
        animatorSet.setDuration(200);
        animatorSet.playTogether(scaleX, scaleY);
        animatorSet.start();

        ObjectAnimator scaleX1 = ObjectAnimator.ofFloat(mFunctionButtonA, "scaleX", 1f, 0.5f);
        ObjectAnimator scaleY1 = ObjectAnimator.ofFloat(mFunctionButtonA, "scaleY", 1f, 0.5f);
        AnimatorSet animatorSet1 = new AnimatorSet();
        animatorSet1.setInterpolator(new OvershootInterpolator());
        animatorSet1.setDuration(200);
        animatorSet1.playTogether(scaleX1, scaleY1);
        animatorSet1.start();
    }

    private void c2a() {
    /*
    需要额外移动中间的按钮
     */
        ObjectAnimator trans = ObjectAnimator.ofFloat(mFunctionButtonB, "translationX", 0, mFunctionButtonA.getWidth() * 0.5f);
        trans.setDuration(200);
        trans.setInterpolator(new OvershootInterpolator());
        trans.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mFunctionButtonB.setPivotX(mFunctionButtonB.getWidth() / 2);
                mFunctionButtonB.setPivotY(mFunctionButtonB.getHeight() / 2);

                int top = mFunctionButtonA.getTop();
                int left1 = (int) (mBaseButton.getLeft() - (mScreenWidth - 2 * mMargin - mFunctionButtonA.getWidth() - BAR_HEIGHT) * mProcess);
                mFunctionButtonB.layout(left1, top, left1 + mFunctionButtonA.getWidth(), top + mFunctionButtonA.getHeight());
                mFunctionButtonB.setTranslationX(0);
            }
        });
        trans.start();

        ObjectAnimator scaleX = ObjectAnimator.ofFloat(mFunctionButtonA, "scaleX", 0.5f, 1f);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(mFunctionButtonA, "scaleY", 0.5f, 1f);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setInterpolator(new OvershootInterpolator());
        animatorSet.setDuration(200);
        animatorSet.playTogether(scaleX, scaleY);
        animatorSet.start();

        ObjectAnimator scaleX1 = ObjectAnimator.ofFloat(mFunctionButtonC, "scaleX", 1f, 0.5f);
        ObjectAnimator scaleY1 = ObjectAnimator.ofFloat(mFunctionButtonC, "scaleY", 1f, 0.5f);
        AnimatorSet animatorSet1 = new AnimatorSet();
        animatorSet1.setInterpolator(new OvershootInterpolator());
        animatorSet1.setDuration(200);
        animatorSet1.playTogether(scaleX1, scaleY1);
        animatorSet1.start();
    }

    private void b2a() {
        int top = mFunctionButtonA.getTop();
//                    int left1 = (int) (mFunctionButtonA.getLeft() + mFunctionButtonA.getWidth()/2 + BAR_HEIGHT * 0.5);
        int right = (int) ((mFunctionButtonB.getRight() + mFunctionButtonB.getLeft()) / 2 + BAR_HEIGHT * 0.5);
        mFunctionButtonB.layout(right - mFunctionButtonA.getWidth(), top, right, top + mFunctionButtonA.getHeight());

        mFunctionButtonB.setPivotX(mFunctionButtonB.getWidth());
        mFunctionButtonB.setPivotY(mFunctionButtonB.getHeight() / 2);

        ObjectAnimator scaleX = ObjectAnimator.ofFloat(mFunctionButtonA, "scaleX", 0.5f, 1f);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(mFunctionButtonA, "scaleY", 0.5f, 1f);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setInterpolator(new OvershootInterpolator());
        animatorSet.setDuration(200);
        animatorSet.playTogether(scaleX, scaleY);
        animatorSet.start();

        ObjectAnimator scaleX1 = ObjectAnimator.ofFloat(mFunctionButtonB, "scaleX", 1f, 0.5f);
        ObjectAnimator scaleY1 = ObjectAnimator.ofFloat(mFunctionButtonB, "scaleY", 1f, 0.5f);
        AnimatorSet animatorSet1 = new AnimatorSet();
        animatorSet1.setInterpolator(new OvershootInterpolator());
        animatorSet1.setDuration(200);
        animatorSet1.playTogether(scaleX1, scaleY1);
        animatorSet1.start();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        mScreenWidth = w;
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);

//        if (isInit) {
//            return;
//        }

        mMargin = (int) (mScreenWidth - mBaseButton.getRight());

        mFunctionButtonA.setPivotX(0);
        mFunctionButtonA.setPivotY(mFunctionButtonB.getHeight() / 2);

        int top = mFunctionButtonA.getTop();
        int left = (int) (mBaseButton.getLeft() - (mScreenWidth - 2 * mMargin - mFunctionButtonA.getWidth()) * mProcess);
        mFunctionButtonA.layout(left, top, left + mFunctionButtonA.getWidth(), top + mFunctionButtonA.getHeight());

        mFunctionButtonB.setPivotX(mFunctionButtonB.getWidth() / 2);
        mFunctionButtonB.setPivotY(mFunctionButtonB.getHeight() / 2);

        int left1 = (int) (mBaseButton.getLeft() - (mScreenWidth - 2 * mMargin - mFunctionButtonA.getWidth() - BAR_HEIGHT) * mProcess);
        mFunctionButtonB.layout(left1, top, left1 + mFunctionButtonA.getWidth(), top + mFunctionButtonA.getHeight());

        mFunctionButtonC.setPivotX(mFunctionButtonB.getWidth());
        mFunctionButtonC.setPivotY(mFunctionButtonB.getHeight() / 2);

        int left2 = (int) (mBaseButton.getLeft() - (mScreenWidth - 2 * mMargin - mFunctionButtonA.getWidth() - BAR_HEIGHT * 1.5) * mProcess);
        mFunctionButtonC.layout(left2, top, left2 + mFunctionButtonA.getWidth(), top + mFunctionButtonA.getHeight());

        /*
        动态配置连线的长度
         */
        mLine.layout(mFunctionButtonA.getLeft() + 10, mLine.getTop(), mBaseButton.getRight() - 10, mLine.getBottom());

//        mFunctionButtonA.setAlpha(mProcess);
//        mFunctionButtonB.setAlpha(mProcess);
//        mFunctionButtonC.setAlpha(mProcess);
    }

    private void popCurrent(int currentSelected) {
        switch (currentSelected) {
            case 0:
                break;
            case 1:
                break;
            case 2:
                break;
        }
    }


    public static final Property<PhantomBottomBar, Float> PROCESS = new Property<PhantomBottomBar, Float>(Float.class, "process") {
        @Override
        public Float get(PhantomBottomBar object) {
            return object.getProcess();
        }

        @Override
        public void set(PhantomBottomBar object, Float value) {
            object.setProcess(value);
        }
    };
}
