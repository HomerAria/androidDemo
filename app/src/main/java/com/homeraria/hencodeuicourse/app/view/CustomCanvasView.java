package com.homeraria.hencodeuicourse.app.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.drawable.Drawable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;
import com.homeraria.hencodeuicourse.app.R;

/**
 * 练习自定义view的canvas.draw***()用法
 */
public class CustomCanvasView extends View
{
    private String mExampleString; // TODO: use a default from R.string...
    private int mExampleColor = Color.RED; // TODO: use a default from R.color...
    private float mExampleDimension = 0; // TODO: use a default from R.dimen...
    private Drawable mExampleDrawable;

    private TextPaint mTextPaint;
    private float mTextWidth;
    private float mTextHeight;

    public CustomCanvasView(Context context)
    {
        super(context);
        init(null, 0);
    }

    public CustomCanvasView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        init(attrs, 0);
    }

    public CustomCanvasView(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
        init(attrs, defStyle);
    }

    private void init(AttributeSet attrs, int defStyle)
    {
        // Load attributes
        final TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.CustomCanvasView, defStyle, 0);

        mExampleString = a.getString(R.styleable.CustomCanvasView_exampleString);
        mExampleColor = a.getColor(R.styleable.CustomCanvasView_exampleColor, mExampleColor);

        // Use getDimensionPixelSize or getDimensionPixelOffset when dealing with
        // values that should fall on pixel boundaries.
        mExampleDimension = a.getDimension(R.styleable.CustomCanvasView_exampleDimension, mExampleDimension);

        if (a.hasValue(R.styleable.CustomCanvasView_exampleDrawable))
        {
            mExampleDrawable = a.getDrawable(R.styleable.CustomCanvasView_exampleDrawable);
            mExampleDrawable.setCallback(this);
        }

        //将styleable资源释放
        a.recycle();

        // Set up a default TextPaint object
        mTextPaint = new TextPaint();
        mTextPaint.setFlags(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setTextAlign(Paint.Align.LEFT);

        // Update TextPaint and text measurements from attributes
        invalidateTextPaintAndMeasurements();
    }

    /**
     * 设置各项属性的参数
     */
    private void invalidateTextPaintAndMeasurements()
    {
        mTextPaint.setTextSize(mExampleDimension);
        mTextPaint.setColor(mExampleColor);
        mTextWidth = mTextPaint.measureText(mExampleString);

        Paint.FontMetrics fontMetrics = mTextPaint.getFontMetrics();
        mTextHeight = fontMetrics.bottom;
    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);

        // allocations per draw cycle. 定位
        int paddingLeft = getPaddingLeft();
        int paddingTop = getPaddingTop();
        int paddingRight = getPaddingRight();
        int paddingBottom = getPaddingBottom();

        int contentWidth = getWidth() - paddingLeft - paddingRight;
        int contentHeight = getHeight() - paddingTop - paddingBottom;

//        // Draw the text.
//        canvas.drawText(mExampleString,
//                paddingLeft + (contentWidth - mTextWidth) / 2,
//                paddingTop + (contentHeight + mTextHeight) / 2,
//                mTextPaint);
//
//        // Draw the example drawable on top of the text. 后绘制的覆盖先绘制
//        if (mExampleDrawable != null)
//        {
//            mExampleDrawable.setBounds(paddingLeft, paddingTop,
//                    paddingLeft + contentWidth, paddingTop + contentHeight);
//            mExampleDrawable.draw(canvas);
//        }

        //绘制背景色--->填充整个矩形占有区域
        canvas.drawColor(Color.BLACK);

        //绘制圆形，默认实心图案
        Paint paint = new Paint();
        paint.setColor(Color.RED);
        canvas.drawCircle(150, 150, 100, paint);

        //空心图案
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(5);
        canvas.drawCircle(450, 150, 100, paint);

        //抗锯齿
        paint.setAntiAlias(true);
        canvas.drawCircle(750, 150, 100, paint);

        //实心矩形
        paint.setStyle(Paint.Style.FILL);
        canvas.drawRect(150, 350, 250, 450, paint);

        //实心矩形+边框
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        canvas.drawRect(450, 350, 550, 450, paint);

        //空心矩形
        paint.setStyle(Paint.Style.STROKE);
        canvas.drawRect(750, 350, 850, 450, paint);

        //绘制点
        paint.setStrokeWidth(20);
        paint.setStrokeCap(Paint.Cap.ROUND);
        canvas.drawPoint(350, 300, paint);

        paint.setStrokeCap(Paint.Cap.SQUARE);
        canvas.drawPoint(650, 300, paint);

        //绘制椭圆
        paint.setStyle(Paint.Style.FILL);
        canvas.drawOval(100, 600, 500, 700, paint);

        //绘制直线
        paint.setStrokeWidth(10);
        canvas.drawLine(600, 600, 900, 700, paint);

        float[] points = {100, 800, 300, 800, 250, 850, 200, 1000, 100, 1000, 300, 1000};  //两个点互为一对，与其他无关
        canvas.drawLines(points, paint);

        //自定义图形
        paint.setStyle(Paint.Style.STROKE);
        Path path = new Path();
        path.addCircle(600, 900, 100, Path.Direction.CW);
        path.addCircle(700, 900, 100, Path.Direction.CW);
        path.lineTo(800, 1000);   //起点是上一次path的终点坐标
        path.rMoveTo(100, 0);   //将下一个图案的起点水平横移
        path.rLineTo(-100, 200);
        path.arcTo(500, 1100, 700, 1300, -90, -90, false);
//        path.close();    //封闭图形，要考虑moveTo()和foreMoveTo参数

        path.rLineTo(-200, 0);   //close之后，本次的起点就是封闭位置终点
        path.rLineTo(0, -100);
        path.close();

        canvas.drawPath(path, paint);

        //绘制Bitmap
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
        canvas.drawBitmap(bitmap, 200, 1200, paint);

        //绘制text
        
    }

    /**
     * Gets the example string attribute value.
     *
     * @return The example string attribute value.
     */
    public String getExampleString()
    {
        return mExampleString;
    }

    /**
     * Sets the view's example string attribute value. In the example view, this string
     * is the text to draw.
     *
     * @param exampleString The example string attribute value to use.
     */
    public void setExampleString(String exampleString)
    {
        mExampleString = exampleString;
        invalidateTextPaintAndMeasurements();
    }

    /**
     * Gets the example color attribute value.
     *
     * @return The example color attribute value.
     */
    public int getExampleColor()
    {
        return mExampleColor;
    }

    /**
     * Sets the view's example color attribute value. In the example view, this color
     * is the font color.
     *
     * @param exampleColor The example color attribute value to use.
     */
    public void setExampleColor(int exampleColor)
    {
        mExampleColor = exampleColor;
        invalidateTextPaintAndMeasurements();
    }

    /**
     * Gets the example dimension attribute value.
     *
     * @return The example dimension attribute value.
     */
    public float getExampleDimension()
    {
        return mExampleDimension;
    }

    /**
     * Sets the view's example dimension attribute value. In the example view, this dimension
     * is the font size.
     *
     * @param exampleDimension The example dimension attribute value to use.
     */
    public void setExampleDimension(float exampleDimension)
    {
        mExampleDimension = exampleDimension;
        invalidateTextPaintAndMeasurements();
    }

    /**
     * Gets the example drawable attribute value.
     *
     * @return The example drawable attribute value.
     */
    public Drawable getExampleDrawable()
    {
        return mExampleDrawable;
    }

    /**
     * Sets the view's example drawable attribute value. In the example view, this drawable is
     * drawn above the text.
     *
     * @param exampleDrawable The example drawable attribute value to use.
     */
    public void setExampleDrawable(Drawable exampleDrawable)
    {
        mExampleDrawable = exampleDrawable;
    }
}
