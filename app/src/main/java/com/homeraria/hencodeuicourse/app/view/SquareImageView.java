package com.homeraria.hencodeuicourse.app.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * 显示图片，但是在测量自身尺寸时只给出正方形结果(即长宽一致)
 */
public class SquareImageView extends ImageView
{
    public SquareImageView(Context context)
    {
        super(context);
    }

    public SquareImageView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    public SquareImageView(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
    }

    public SquareImageView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes)
    {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int measuredHeight = getMeasuredHeight();
        int measuredWidth = getMeasuredWidth();

        //长宽总等于其中较小的一个值
        if (measuredHeight > measuredWidth)
        {
            measuredHeight = measuredWidth;
        } else
        {
            measuredWidth = measuredHeight;
        }

        setMeasuredDimension(measuredWidth, measuredHeight);
    }
}
