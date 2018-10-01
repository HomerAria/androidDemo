package com.homeraria.hencodeuicourse.app.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.EmbossMaskFilter;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RadialGradient;
import android.graphics.Shader;
import android.graphics.SweepGradient;
import android.graphics.drawable.Drawable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;
import com.homeraria.hencodeuicourse.app.R;

/**
 * 练习自定义view的paint用法
 */
public class CustomPaintView extends View
{
    private String mExampleString; // TODO: use a default from R.string...
    private int mExampleColor = Color.RED; // TODO: use a default from R.color...
    private float mExampleDimension = 0; // TODO: use a default from R.dimen...
    private Drawable mExampleDrawable;

    private TextPaint mTextPaint;
    private float mTextWidth;
    private float mTextHeight;

    public CustomPaintView(Context context)
    {
        super(context);
        init(null, 0);
    }

    public CustomPaintView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        init(attrs, 0);
    }

    public CustomPaintView(Context context, AttributeSet attrs, int defStyle)
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

        Paint paint = new Paint();

        //线性渐变
        Shader shader = new LinearGradient(100, 100, 1000, 1000,
                Color.parseColor("#E91E63"),
                Color.parseColor("#2196F3"),
                Shader.TileMode.CLAMP);
        paint.setShader(shader);
        paint.setColor(Color.RED);   //被shader效果覆盖
        canvas.drawCircle(500, 500, 400, paint);

        paint.setTextSize(100);
        canvas.drawText("SEAN", 300, 1000, paint);

        //辐射渐变
        Shader shader1 = new RadialGradient(500, 1400, 400,
                Color.parseColor("#E91E63"),
                Color.parseColor("#2196F3"),
                Shader.TileMode.CLAMP);
        paint.setShader(shader1);
        canvas.drawCircle(500, 1400, 300, paint);

        //扫描
        Shader shader2 = new SweepGradient(500, 2000,
                Color.parseColor("#E91E63"),
                Color.parseColor("#2196F3"));
        paint.setShader(shader2);
        canvas.drawRect(50, 1700, 950, 2400, paint);

        //bitmap作为图形背景-->无法控制bitmap的位置
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.test_bg);
        Shader shader3 = new BitmapShader(bitmap, Shader.TileMode.MIRROR, Shader.TileMode.MIRROR);
        paint.setShader(shader3);
        canvas.drawCircle(500, 2700, 400, paint);

        //设置MaskFilter
        paint.setMaskFilter(new EmbossMaskFilter(new float[]{0, 1, 1}, 0.2f, 8, 10));
        canvas.drawBitmap(bitmap, 100, 3500, paint);

        //true path
        Path path = new Path();
        path.moveTo(150, 5000);    //设置起点
        path.rLineTo(50, -200);
        path.rLineTo(100, 100);

        Paint pathPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        pathPaint.setColor(Color.WHITE);
        pathPaint.setStyle(Paint.Style.STROKE);
        Path path1 = new Path();
        Path path2 = new Path();
        Path path3 = new Path();

        paint.setShader(null);

        // 使用 Paint.getFillPath() 获取实际绘制的 Path
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        paint.setStrokeWidth(0);
        // 第一处：获取 Path
        canvas.drawPath(path, paint);

        paint.getFillPath(path, path1);
        canvas.save();
        canvas.translate(0, 300);
        canvas.drawPath(path1, pathPaint);
        canvas.restore();

        canvas.save();
        canvas.translate(200, 0);
        paint.setStyle(Paint.Style.STROKE);
        // 第二处：设置 Style 为 STROKE 后再获取 Path
        canvas.drawPath(path, paint);
        canvas.restore();

        paint.getFillPath(path, path2);
        canvas.save();
        canvas.translate(200, 300);
        canvas.drawPath(path2, pathPaint);
        canvas.restore();

        canvas.save();
        canvas.translate(400, 0);
        paint.setStrokeWidth(40);
        // 第三处：Style 为 STROKE 并且线条宽度为 40 时的 Path
        canvas.drawPath(path, paint);
        canvas.restore();

        paint.getFillPath(path, path3);     //通过这里得到path3在paint条件下绘制的path的truePath
        canvas.save();
        canvas.translate(400, 300);
        canvas.drawPath(path3, pathPaint);
        canvas.restore();

        paint.setStyle(Paint.Style.FILL_AND_STROKE);   //getFillPath之后再变更paint属性是无效的
        paint.getFillPath(path, path3);
        canvas.save();
        canvas.translate(600, 300);
        canvas.drawPath(path3, pathPaint);
        canvas.restore();

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
