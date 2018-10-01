package com.homeraria.hencodeuicourse.app.view.evaluator;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.View;
import com.homeraria.hencodeuicourse.app.Utils;

/**
 * 这次暴露给ObjectAnimator的参数是position
 */
public class PositionCircleView extends View
{
    float mRadious = Utils.dpToPixel(20);

    //Point是int， Point是float
    PointF mPoint = new PointF();
    Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    public PositionCircleView(Context context)
    {
        super(context);
    }

    public PositionCircleView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    public PositionCircleView(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
    }

    public PositionCircleView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes)
    {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public PointF getPoint()
    {
        return mPoint;
    }

    public void setPoint(PointF mPoint)
    {
        if(mPoint != null)
        {
            this.mPoint = mPoint;
            invalidate();
        }
    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);

        mPaint.setColor(Color.GREEN);
        canvas.drawCircle(mPoint.x + mRadious, mPoint.y + mRadious, mRadious, mPaint);
    }
}
