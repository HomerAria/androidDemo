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

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.homeraria.component.R;
import com.homeraria.component.utils.Utils;

public class TopologyView extends SquareRelativeLayout {

    private final static float BASE_RADIUS = Utils.dpToPixel(65);
    private final static float ALPHA = 48;
    private final static float BETA = 80;

    private Context mContext;

    private ImageView mSelfView, mRelationViewA, mRelationViewB, mRelationViewC;

    private RelativeLayout mRootLayout;

    public TopologyView(Context context) {
        super(context);
        init(context);
    }

    public TopologyView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public TopologyView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        mContext = context;

        LayoutInflater.from(getContext()).inflate(R.layout.album_person_topology, this, true);

        mRootLayout = findViewById(R.id.root_layout);
        mSelfView = findViewById(R.id.self_avatar_view);


        mRelationViewA = new ImageView(mContext);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams((int) Utils.dpToPixel(40), (int) Utils.dpToPixel(40));
        params.addRule(CENTER_IN_PARENT);
        mRelationViewA.setBackgroundResource(R.drawable.background_circle);
        mRelationViewA.setLayoutParams(params);

        //计算位置
        double radius = Math.toRadians(ALPHA);
        double dx = BASE_RADIUS * Math.cos(radius);
        double dy = BASE_RADIUS * Math.sin(radius);

        mRelationViewA.setX((float) -dx);
        mRelationViewA.setY((float) -dy);
        mRootLayout.addView(mRelationViewA);
        Log.v("sean5", "self AY=" + (centerY + dy - mRelationViewA.getHeight() / 2) + ", AX=" + (centerX - dx - mRelationViewA.getWidth() / 2));

        mRelationViewB = new ImageView(mContext);
        RelativeLayout.LayoutParams params1 = new RelativeLayout.LayoutParams((int) Utils.dpToPixel(40), (int) Utils.dpToPixel(40));
        params1.addRule(CENTER_IN_PARENT);
        mRelationViewB.setBackgroundResource(R.drawable.background_circle);
        mRelationViewB.setLayoutParams(params1);

        //计算位置
        double radius1 = Math.toRadians(ALPHA);
        double dx1 = 2 * BASE_RADIUS * Math.cos(radius1);
        double dy1 = 2 * BASE_RADIUS * Math.sin(radius1);

        mRelationViewB.setX((float) (dx1));
        mRelationViewB.setY((float) (dy1));
        mRootLayout.addView(mRelationViewB);
        Log.v("sean5", "self BY=" + (centerY + dy1 - mRelationViewB.getHeight() / 2) + ", BX=" + (centerX - dx1 - mRelationViewB.getWidth() / 2));

        mRelationViewC = new ImageView(mContext);
        RelativeLayout.LayoutParams params2 = new RelativeLayout.LayoutParams((int) Utils.dpToPixel(40), (int) Utils.dpToPixel(40));
        params2.addRule(CENTER_IN_PARENT);
        mRelationViewC.setBackgroundResource(R.drawable.background_circle);
        mRelationViewC.setLayoutParams(params2);

        //计算位置
        double radius2 = Math.toRadians(BETA);
        double dx2 = 3 * BASE_RADIUS * Math.cos(radius2);
        double dy2 = 3 * BASE_RADIUS * Math.sin(radius2);

        mRelationViewC.setX((float) (dx2));
        mRelationViewC.setY((float) (-dy2));
        mRootLayout.addView(mRelationViewC);
        Log.v("sean5", "self BY=" + (centerY + dy1 - mRelationViewB.getHeight() / 2) + ", BX=" + (centerX - dx1 - mRelationViewB.getWidth() / 2));
    }


    int centerY;
    int centerX;

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
    }

    @SuppressLint("DrawAllocation")
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);

        Toast.makeText(mContext, "centerY=" + mSelfView.getLayoutParams().height
                + ", centerX=" + mSelfView.getLayoutParams().width, Toast.LENGTH_SHORT).show();

        int[] location = new int[2];
        mSelfView.getLocationInWindow(location);
        centerY = location[1] + mSelfView.getHeight() / 2;
        centerX = location[0] + mSelfView.getWidth() / 2;

        Log.v("sean5", "self centerY=" + centerY + ", centerX=" + centerX);


//        if (changed) {
//            RotateAnimation animation0 = new RotateAnimation(0, 360
//                    , mOuterCircle.getWidth() / 2, mOuterCircle.getHeight() / 2);
//            animation0.setDuration(50000);
//            animation0.setRepeatCount(-1);
//            animation0.setInterpolator(new LinearInterpolator());
//            mOuterCircle.startAnimation(animation0);
//
//            RotateAnimation animation = new RotateAnimation(0, 120
//                    , mFunctionButtonB.getWidth() / 2, TopologyView.this.getHeight() / 2 - mFunctionButtonB.getTop());
//            animation.setDuration(500);
//            animation.setInterpolator(new OvershootInterpolator());
//            animation.setFillAfter(true);
//            animation.setAnimationListener(new Animation.AnimationListener() {
//                @Override
//                public void onAnimationStart(Animation animation) {
//
//                }
//
//                @Override
//                public void onAnimationEnd(Animation animation) {
//                    mFunctionButtonB.setPivotX(mFunctionButtonB.getWidth() / 2);
//                    mFunctionButtonB.setPivotY(TopologyView.this.getHeight() / 2 - mFunctionButtonB.getTop());
//                    mFunctionButtonB.setRotation(120);
//
//                    mBorderButtonB.setPivotX(mBorderButtonB.getWidth() / 2);
//                    mBorderButtonB.setPivotY(TopologyView.this.getHeight() / 2 - mBorderButtonB.getTop());
//                    mBorderButtonB.setRotation(120);
//                }
//
//                @Override
//                public void onAnimationRepeat(Animation animation) {
//
//                }
//            });
//            mFunctionButtonB.startAnimation(animation);
//
//
//            RotateAnimation animation2 = new RotateAnimation(0, 240
//                    , mFunctionButtonC.getWidth() / 2, TopologyView.this.getHeight() / 2 - mFunctionButtonC.getTop());
//            animation2.setDuration(500);
//            animation2.setInterpolator(new OvershootInterpolator());
//            animation2.setFillAfter(true);
//            animation2.setAnimationListener(new Animation.AnimationListener() {
//                @Override
//                public void onAnimationStart(Animation animation) {
//
//                }
//
//                @Override
//                public void onAnimationEnd(Animation animation) {
//                    mFunctionButtonC.setPivotX(mFunctionButtonC.getWidth() / 2);
//                    mFunctionButtonC.setPivotY(TopologyView.this.getHeight() / 2 - mFunctionButtonC.getTop());
//                    mFunctionButtonC.setRotation(-120);
//
//                    mBorderButtonC.setPivotX(mBorderButtonC.getWidth() / 2);
//                    mBorderButtonC.setPivotY(TopologyView.this.getHeight() / 2 - mBorderButtonC.getTop());
//                    mBorderButtonC.setRotation(-120);
//                }
//
//                @Override
//                public void onAnimationRepeat(Animation animation) {
//
//                }
//            });
//            mFunctionButtonC.startAnimation(animation2);
//
//        }

    }

}
