package com.homeraria.hencodeuicourse.app.view.progress;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import com.homeraria.hencodeuicourse.app.Utils;

/**
 * 用于自定义objectAnimator
 */
public class ObjectAnimatorView extends View
{
    final float radius = Utils.dpToPixel(80);

    float progress = 0;
    RectF arcRectF = new RectF();
    Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    {
        mPaint.setTextSize(Utils.dpToPixel(40));
        mPaint.setTextAlign(Paint.Align.CENTER);     //置于中心
    }

    public ObjectAnimatorView(Context context)
    {
        super(context);
    }

    public ObjectAnimatorView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    public ObjectAnimatorView(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
    }

    public ObjectAnimatorView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes)
    {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    /**
     * 这一组getter/setter函数是ObjectAnimator用来控制View的
     */
    public float getProgress()
    {
        return progress;
    }

    public void setProgress(float mProgress)
    {
        this.progress = mProgress;
        invalidate();    //必须加入，告知系统当前界面失效，才会在开始动画时发生界面更新
    }


    @Override
    protected void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);

        float centerX = getWidth() / 2;
        float centerY = getHeight() / 2;

        //绘制一个现实百分比的数字，周围是环绕的弧形，其弧形绕过的角度是数字显示的完成度
        mPaint.setColor(Color.parseColor("#E91E63"));
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStrokeWidth(Utils.dpToPixel(15));
        arcRectF.set(centerX - radius, centerY - radius, centerX + radius, centerY + radius);
        canvas.drawArc(arcRectF, 135, progress * 2.7f, false, mPaint);

        mPaint.setColor(Color.WHITE);
        mPaint.setStyle(Paint.Style.FILL);
        canvas.drawText((int) progress + "%", centerX, centerY - (mPaint.ascent() + mPaint.descent()) / 2, mPaint);
    }
}
