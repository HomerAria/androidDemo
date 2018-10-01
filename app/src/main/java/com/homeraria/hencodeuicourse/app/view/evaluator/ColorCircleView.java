package com.homeraria.hencodeuicourse.app.view.evaluator;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * 生成一个圆形，其中将颜色暴露出来给ObjectAnimator控制
 */
public class ColorCircleView extends View
{
    Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    int mColor = 0xffff0000;

    public ColorCircleView(Context context)
    {
        super(context);
    }

    public ColorCircleView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    public ColorCircleView(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
    }

    public ColorCircleView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes)
    {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public int getColor()
    {
        return mColor;
    }

    public void setColor(int mColor)
    {
        this.mColor = mColor;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);

        mPaint.setColor(mColor);
        canvas.drawCircle(getWidth() / 2, getHeight() / 2, getHeight() / 2, mPaint);
    }
}
