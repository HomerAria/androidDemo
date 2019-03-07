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
 * File: ArcSideRectViewBase.java
 * Description: 初步弧边矩形
 *
 * ---------------------------- Revision History: ------------------------
 * <author>             <date>          <version>           <desc>
 * sean.zhou@oppo.com   2019/3/5         1.0                 create this module
 * -----------------------------------------------------------------------
 */
package com.homeraria.component.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

/**
 * 使用三阶贝赛尔曲线实现弧边矩形的实验类
 */
public class ArcSideRectViewBase extends View {
    private static final Rect ICON_RECT = new Rect(0, 0, 500, 500);
    private float mArcRatio = 0.2f;    //[0f, 1f] 表征

    private Paint mPaint;

    //控件的中心点坐标，其他点坐标都基于此值
    private int mCenterX, mCenterY;

    /*
    二阶贝塞尔曲线的始终点与控制点
    每行分别代表一条边
     */
    private PointF mStartPointT, mEndPointT, mControlPointAT, mControlPointBT;

    private PointF mStartPointL, mEndPointL, mControlPointAL, mControlPointBL;

    private PointF mStartPointR, mEndPointR, mControlPointAR, mControlPointBR;

    private PointF mStartPointB, mEndPointB, mControlPointAB, mControlPointBB;


    public ArcSideRectViewBase(Context context) {
        this(context, null);
    }

    /**
     * 直接通过xml创建，是本例实际的调用入口
     */
    public ArcSideRectViewBase(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    /**
     * 最终调用的build function，执行成员变量的初始化
     */
    public ArcSideRectViewBase(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mPaint = new Paint();
        mPaint.setColor(Color.WHITE);
        mPaint.setStrokeWidth(8);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        /*
        端头为圆
         */
//        mPaint.setStrokeCap(Paint.Cap.ROUND);

        calBezier();
    }

    private void calBezier() {
        float ratioLength = ICON_RECT.bottom * mArcRatio;
        float length = ICON_RECT.bottom;

        mEndPointL = new PointF(ratioLength / 2 + mCenterX, ratioLength / 2 + mCenterY);
        mStartPointT = new PointF(ratioLength / 2 + mCenterX, ratioLength / 2 + mCenterY);

        mEndPointT = new PointF(length - ratioLength / 2 + mCenterX, ratioLength / 2 + mCenterY);
        mStartPointR = new PointF(length - ratioLength / 2 + mCenterX, ratioLength / 2 + mCenterY);

        mEndPointR = new PointF(length - ratioLength / 2 + mCenterX, length - ratioLength / 2 + mCenterY);
        mStartPointB = new PointF(length - ratioLength / 2 + mCenterX, length - ratioLength / 2 + mCenterY);

        mEndPointB = new PointF(ratioLength / 2 + mCenterX, length - ratioLength / 2 + mCenterY);
        mStartPointL = new PointF(ratioLength / 2 + mCenterX, length - ratioLength / 2 + mCenterY);

        mControlPointAT = new PointF(ratioLength + mCenterX, mCenterY);
        mControlPointBT = new PointF(length - ratioLength + mCenterX, mCenterY);

        mControlPointAR = new PointF(length + mCenterX, ratioLength + mCenterY);
        mControlPointBR = new PointF(length + mCenterX, length - ratioLength + mCenterY);

        mControlPointAB = new PointF(length - ratioLength + mCenterX, length + mCenterY);
        mControlPointBB = new PointF(ratioLength + mCenterX, length + mCenterY);

        mControlPointAL = new PointF(mCenterX, length - ratioLength + mCenterY);
        mControlPointBL = new PointF(mCenterX, ratioLength + mCenterY);
    }

    /**
     * 最终确定view尺寸时的回调
     */
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        mCenterX = (w - 500) / 2;
        mCenterY = (h - 500) / 2;
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //Besier曲线
        mPaint.setColor(Color.WHITE);
        mPaint.setStrokeWidth(8);

        //path收尾相连，可以仅仅绘制一条path联通，而不用分别绘制四条paths
        Path pathT = new Path();
        pathT.moveTo(mStartPointT.x, mStartPointT.y);
        pathT.cubicTo(mControlPointAT.x, mControlPointAT.y, mControlPointBT.x, mControlPointBT.y, mEndPointT.x, mEndPointT.y);

//        canvas.drawPath(pathT, mPaint);


//        Path pathR = new Path();
//        pathR.moveTo(mStartPointR.x, mStartPointR.y);
        pathT.cubicTo(mControlPointAR.x, mControlPointAR.y, mControlPointBR.x, mControlPointBR.y, mEndPointR.x, mEndPointR.y);

//        canvas.drawPath(pathR, mPaint);
//
//
//        Path pathB = new Path();
//        pathB.moveTo(mStartPointB.x, mStartPointB.y);
        pathT.cubicTo(mControlPointAB.x, mControlPointAB.y, mControlPointBB.x, mControlPointBB.y, mEndPointB.x, mEndPointB.y);

//        canvas.drawPath(pathB, mPaint);
//
//
//        Path pathL = new Path();
//        pathL.moveTo(mStartPointL.x, mStartPointL.y);
        pathT.cubicTo(mControlPointAL.x, mControlPointAL.y, mControlPointBL.x, mControlPointBL.y, mEndPointL.x, mEndPointL.y);

        canvas.drawPath(pathT, mPaint);
    }

}
