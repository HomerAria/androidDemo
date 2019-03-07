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
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;

import com.homeraria.component.R;

import androidx.annotation.Nullable;

/**
 * 绘制弧边矩形，且只有内部可以显示图片
 */
public class ArcSideRectView extends View {
    private Rect ICON_RECT = new Rect(0, 0, 500, 500);
    private float mArcRatio = 0.25f;    //[0f, 1f] 表征

    private Paint mPaint, mBorderPaint;
    private Shader mShader;

    //控件的中心点坐标，其他点坐标都基于此值
    private int mCenterX = 0, mCenterY = 0;

    /*
    二阶贝塞尔曲线的始终点与控制点
    每行分别代表一条边
     */
    private PointF mStartPointT, mEndPointT, mControlPointAT, mControlPointBT;

    private PointF mEndPointL, mControlPointAL, mControlPointBL;

    private PointF mEndPointR, mControlPointAR, mControlPointBR;

    private PointF mEndPointB, mControlPointAB, mControlPointBB;


    public ArcSideRectView(Context context) {
        this(context, null);
    }

    /**
     * 直接通过xml创建，是本例实际的调用入口
     */
    public ArcSideRectView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    /**
     * 最终调用的build function，执行成员变量的初始化
     */
    public ArcSideRectView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mPaint = new Paint();
        mPaint.setColor(Color.WHITE);
        mPaint.setStrokeWidth(8);
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
//        mPaint.setStrokeCap(Paint.Cap.ROUND);

        mBorderPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mBorderPaint.setDither(true);
        mBorderPaint.setARGB(255, 255, 215, 0);
        mBorderPaint.setStyle(Paint.Style.STROKE);
        mBorderPaint.setStrokeWidth(8);

//        calBezier();
    }

    private void calBezier() {
        float ratioLength = ICON_RECT.bottom * mArcRatio;
        float length = ICON_RECT.bottom;

        mEndPointL = new PointF(ratioLength / 2 + mCenterX, ratioLength / 2 + mCenterY);
        mStartPointT = new PointF(ratioLength / 2 + mCenterX, ratioLength / 2 + mCenterY);

        mEndPointT = new PointF(length - ratioLength / 2 + mCenterX, ratioLength / 2 + mCenterY);

        mEndPointR = new PointF(length - ratioLength / 2 + mCenterX, length - ratioLength / 2 + mCenterY);

        mEndPointB = new PointF(ratioLength / 2 + mCenterX, length - ratioLength / 2 + mCenterY);

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

//        mCenterX = (w - 500) / 2;
//        mCenterY = (h - 500) / 2;

        /*
        根据xml的设置动态分配尺寸
         */
        int length = Math.min(w, h);
        ICON_RECT.set(0, 0, length, length);
        calBezier();

        /*
        shader中的额bitmap需要根据view的尺寸做缩放,同时图片的中心与View的中心位置重合
         */
        Bitmap fieldBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.bg1);
        int bitmapWidth = fieldBitmap.getWidth();
        int bitmapHeight = fieldBitmap.getHeight();
        int bitmapLength = Math.min(bitmapHeight, bitmapWidth);
        float scale = (float) length / (float) bitmapLength;

        Matrix matrix = new Matrix();
        //缩放，图片的短边长等于icon的边长
        matrix.postScale(scale, scale);
        //位移，两者中心重合-->scale之后的尺寸
        matrix.postTranslate(-(bitmapWidth * scale - length) / 2,
                -(bitmapHeight * scale - length) / 2);

        mShader = new BitmapShader(fieldBitmap, Shader.TileMode.REPEAT, Shader.TileMode.MIRROR);
        mShader.setLocalMatrix(matrix);

        /*
        根据尺寸动态配置边框粗细
         */
        int width = length / 100 > 2 ? length / 100 : 2;
        mBorderPaint.setStrokeWidth(width);
        mPaint.setStrokeWidth(width);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        /*
        绘制其中的图片资源
         */
        mPaint.setShader(mShader);

        Path pathT = new Path();
        pathT.moveTo(mStartPointT.x, mStartPointT.y);
        pathT.cubicTo(mControlPointAT.x, mControlPointAT.y, mControlPointBT.x, mControlPointBT.y, mEndPointT.x, mEndPointT.y);

        pathT.cubicTo(mControlPointAR.x, mControlPointAR.y, mControlPointBR.x, mControlPointBR.y, mEndPointR.x, mEndPointR.y);

        pathT.cubicTo(mControlPointAB.x, mControlPointAB.y, mControlPointBB.x, mControlPointBB.y, mEndPointB.x, mEndPointB.y);

        pathT.cubicTo(mControlPointAL.x, mControlPointAL.y, mControlPointBL.x, mControlPointBL.y, mEndPointL.x, mEndPointL.y);

        canvas.drawPath(pathT, mPaint);

        /*
        绘制border
         */
        canvas.drawPath(pathT, mBorderPaint);
    }

}
