package com.homeraria.hencodeuicourse.app.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;
import com.homeraria.hencodeuicourse.app.R;

/**
 * 练习自定义view的canvas.clip***()&几何变换用法
 */
public class CustomCameraView extends View
{
    private String mExampleString; // TODO: use a default from R.string...
    private int mExampleColor = Color.RED; // TODO: use a default from R.color...
    private float mExampleDimension = 0; // TODO: use a default from R.dimen...
    private Drawable mExampleDrawable;

    private TextPaint mTextPaint;
    private float mTextWidth;
    private float mTextHeight;

    public CustomCameraView(Context context)
    {
        super(context);
        init(null, 0);
    }

    public CustomCameraView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        init(attrs, 0);
    }

    public CustomCameraView(Context context, AttributeSet attrs, int defStyle)
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
        Bitmap icon = BitmapFactory.decodeResource(getResources(), R.mipmap.test_icon);

        canvas.drawBitmap(icon, 100, 100, paint);

        canvas.save();
        canvas.rotate(45, 0, 0);
        canvas.translate(200, 0);
        canvas.drawBitmap(icon, 100, 100, paint);
        canvas.restore();

        canvas.save();
        canvas.translate(200, 0);
        canvas.rotate(45, 0, 0);

        canvas.drawBitmap(icon, 100, 100, paint);
        canvas.restore();

        //矩形剪裁
        canvas.save();
        canvas.translate(0, 1000);
        canvas.clipRect(300, 100, 700, 500);
        canvas.drawBitmap(icon, 100, 100, paint);
        canvas.restore();

        //自定义剪裁
        Path path = new Path();
        path.addCircle(300, 500, 150, Path.Direction.CW);
        canvas.save();
        canvas.translate(0, 1500);
        canvas.clipPath(path);
        canvas.drawBitmap(icon, 100, 100, paint);
        canvas.restore();

        path.setFillType(Path.FillType.INVERSE_WINDING);    //path填充的是外部区域，因此剪裁的圆形内部
        canvas.save();
        canvas.translate(200, 1500);
        canvas.clipPath(path);
        canvas.drawBitmap(icon, 100, 100, paint);
        canvas.restore();

        //canvas几何变换-->在几何变换之后，坐标也是随之一同变换的，比如旋转画布，坐标轴也随之旋转，平移就是按照新的坐标轴；
        //如果先平移在旋转，则平移就是按照原先的坐标轴；
        //缩放也一样，scale之后，坐标轴也随之缩放，平移数值相同，但会发现实际平移量不同了；
        Bitmap map = BitmapFactory.decodeResource(getResources(), R.mipmap.maps);
        canvas.drawBitmap(map, 100, 2500, paint);

        canvas.save();
        canvas.rotate(-45, 100 + map.getWidth() / 2, 2500 + map.getHeight() / 2);
        canvas.translate(500, 0);
        canvas.drawBitmap(map, 100, 2500, paint);
        canvas.restore();

        canvas.save();
        canvas.translate(0, 500);
        canvas.drawBitmap(map, 100, 2500, paint);
        canvas.restore();

        canvas.save();
        canvas.translate(500, 500);
        canvas.scale(0.5f, 0.5f, 100 + map.getWidth() / 2, 2500 + map.getHeight() / 2);
        canvas.drawBitmap(map, 100, 2500, paint);
        canvas.restore();

        canvas.save();
        canvas.translate(0, 1000);
        canvas.drawBitmap(map, 100, 2500, paint);
        canvas.restore();

        canvas.save();
        canvas.translate(500, 1000);
        canvas.skew(0, -0.1f);   //无法自定义错切的原点，总是canvas的原点
        canvas.drawBitmap(map, 100, 2500, paint);
        canvas.restore();

        //camera三维变换--->和canvas一样想象为一个实体，类似观察者之眼，需要save和restore
        Camera camera = new Camera();

        canvas.save();
        canvas.translate(100, 4000);
        canvas.drawBitmap(map, 0, 0, paint);
        canvas.restore();

        canvas.save();
        canvas.translate(800, 4000);
        camera.save();
        camera.rotateX(-30);      //默认原点就是canvas的原点
        camera.applyToCanvas(canvas);
        canvas.drawBitmap(map, 0, 0, paint);
        canvas.restore();
        camera.restore();

        canvas.save();
        canvas.translate(100, 4500);
        camera.save();
        camera.rotateY(30);
        camera.applyToCanvas(canvas);
        canvas.drawBitmap(map, 0, 0, paint);
        canvas.restore();
        camera.restore();

        canvas.save();
        canvas.translate(800, 4500);
        camera.save();
        camera.rotateZ(-30);
        camera.applyToCanvas(canvas);
        canvas.drawBitmap(map, 0, 0, paint);
        canvas.restore();
        camera.restore();

        canvas.save();
        canvas.translate(100, 5000);
        camera.save();
        camera.rotateX(-30);      //默认原点就是canvas的原点
        canvas.translate(map.getWidth() / 2, map.getHeight() / 2);
        camera.applyToCanvas(canvas);
        canvas.translate(-map.getWidth() / 2, -map.getHeight() / 2);
        canvas.drawBitmap(map, 0, 0, paint);
        canvas.restore();
        camera.restore();

        canvas.save();
        canvas.translate(800, 5000);
        camera.save();
        canvas.translate(map.getWidth() / 2, map.getHeight() / 2);   //和camera.rotate的顺序先后对效果无影响
        camera.rotateX(-30);      //默认原点就是canvas的原点
        camera.applyToCanvas(canvas);
        //这两个平移的意义在于将camera移动到画面正上方，而不是原点正上方(apply已经执行，这次平移camera固定)
        canvas.translate(-map.getWidth() / 2, -map.getHeight() / 2);
        canvas.drawBitmap(map, 0, 0, paint);
        canvas.restore();
        camera.restore();

        //Note：
        //在camera.applyToCanvas(canvas)发生之前，canvas和camera两者的坐标原点一致，跟着canvas的运动；
        //在apply之后，camera的坐标固定下来，canvas再平移就不会对camera有影响；
        //在上一个例子中：
        //在canvas.draw**()之前图都不存在，平移的仅仅是camera和canvas坐标原点和，当然camera会随着坐标移动；
        //当然我们已经知道需要绘制的图案尺寸，所以通过canvas坐标的平移，把camera移动到“潜在”图片的正上方位置；
        //为何说潜在图片呢，因为这是图片还未绘制在canvas上，且drawBitmap中的left和top我们已经确定是0-->也就是说图片左上角一定是贴着canvas的原点；

        //为了论证上述猜测，可以在注释后一个canvas位移函数，这样camera应该仍然在图片左上角的正上方，而不再图片中心的正上方
        canvas.save();
        canvas.translate(100, 5500);
        camera.save();
        canvas.translate(map.getWidth() / 2, map.getHeight() / 2);   //和camera.rotate的顺序先后对效果无影响
        camera.rotateX(-30);      //默认原点就是canvas的原点
        camera.applyToCanvas(canvas);
        canvas.drawBitmap(map, 0, 0, paint);
        canvas.restore();
        camera.restore();

        //然后，如果在drawBitmap时将图片平移，使得camera位于图片的正上方，应该也可以得到前前一例中的图片
        //不同的是，前前一例中最后又移动的canvas坐标系，而这里移动的是图片的绘制位置
        canvas.save();
        canvas.translate(800, 5500);
        camera.save();
        canvas.translate(map.getWidth() / 2, map.getHeight() / 2);   //和camera.rotate的顺序先后对效果无影响
        camera.rotateX(-30);      //默认原点就是canvas的原点
        camera.applyToCanvas(canvas);
        canvas.drawBitmap(map, -map.getWidth() / 2, -map.getHeight() / 2, paint);
        canvas.restore();
        camera.restore();

        //再论证
        //不移动camera的位置，仅仅将图片移动到camera正下方，应该也可以获得上例的图片
        //需要注意的是因为图片绘制在canvas的其他位置，需要提前移动canvas至合适位置
        canvas.save();
        canvas.translate(100 + map.getWidth() / 2, 6000 + map.getHeight() / 2);
        camera.save();
        camera.rotateX(-30);      //默认原点就是canvas的原点
        camera.applyToCanvas(canvas);
        canvas.drawBitmap(map, -map.getWidth() / 2, -map.getHeight() / 2, paint);
        canvas.restore();
        camera.restore();

        //HOMEWORK
        Shader shader = new LinearGradient(100, 100, 1000, 1000,
                Color.parseColor("#E91E63"),
                Color.parseColor("#2196F3"),
                Shader.TileMode.CLAMP);
        Paint paint1 = new Paint();
        Paint paint2 = new Paint();
        paint1.setShader(shader);
        paint1.setTextSize(200);
        paint2.setColor(Color.BLACK);
        paint2.setTextSize(200);

        canvas.save();
        canvas.translate(800, 0);
        do3DRotate(camera, canvas);
        canvas.drawCircle(300, 400, 260, paint1);
        canvas.drawText("LOVE", 0, 700, paint2);
        canvas.restore();

        canvas.save();
        canvas.translate(800, 0);
        do3DRotate(camera, canvas);
        Path path1 = new Path();
        path1.addCircle(300, 400, 260, Path.Direction.CW);
        path1.setFillType(Path.FillType.INVERSE_WINDING);
        //注意裁切和绘制的顺序不同，效果不同
        //draw在前的话，之后再clip就不会对绘制的结果产生任何影响
        canvas.clipPath(path1);
        canvas.drawText("LOVE", 0, 700, paint1);
    }

    private void do3DRotate(Camera camera, Canvas canvas)
    {
        camera.save();
        canvas.translate(300, 400);
        camera.rotateX(30);
        camera.applyToCanvas(canvas);
        canvas.translate(-300, -400);
        camera.restore();
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
