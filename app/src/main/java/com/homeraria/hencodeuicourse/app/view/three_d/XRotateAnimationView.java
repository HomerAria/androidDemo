package com.homeraria.hencodeuicourse.app.view.three_d;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.homeraria.hencodeuicourse.app.R;

/**
 * 基于XRotateView，但暴露出一个动画属性，可以使map的2d侧和3d侧中分线绕Z轴转动
 */
public class XRotateAnimationView extends RelativeLayout implements View.OnClickListener {
    private Paint paint;
    private Bitmap bitmap;
    private Camera camera;
    private int mCenterX, mCenterY, mBitmapH, mBitmapW;

    private Button mButton;
    private float rotateZ;
    private float rotateStart;

    public void setRotateStart(float rotateStart) {
        this.rotateStart = rotateStart;
        invalidate();
    }

    public void setRotateZ(float rotateZ) {
        this.rotateZ = rotateZ;
        invalidate();
    }

    public XRotateAnimationView(Context context) {
        super(context);
    }

    public XRotateAnimationView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public XRotateAnimationView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();

        bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.maps);
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        camera = new Camera();

        mButton = findViewById(R.id.control_button);
        mButton.setOnClickListener(this);
        mBitmapW = bitmap.getWidth();
        mBitmapH = bitmap.getHeight();

        rotateZ = 0;
        rotateStart = 0;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //绘制默认原图
        super.onDraw(canvas);

        //不能在onAttachedToWindow中调用，得到的仍然是0
        mCenterX = getWidth() / 2;
        mCenterY = getHeight() / 2;

        //A.绘制3d侧
        canvas.save();
        camera.save();
        canvas.translate(mCenterX, mCenterY);
        camera.rotateZ(rotateZ);   //等同于canvas.rotate(-25),因为不论转动camera还是canvas，都是两者的坐标系同步转动
        camera.rotateY(-rotateStart);
        camera.applyToCanvas(canvas);
        canvas.clipRect(0, -mCenterY, mCenterX, mCenterY);
        canvas.rotate(rotateZ);
        canvas.drawBitmap(bitmap, -mBitmapW / 2, -mBitmapH / 2, paint);
        canvas.restore();
        camera.restore();

        //B.绘制平面
        canvas.save();
        canvas.translate(mCenterX, mCenterY);
        canvas.rotate(-rotateZ);
//        canvas.clipRect(-mCenterX, -mCenterY, 0, mCenterY);    //注意clip截取可视面的顺序
        canvas.clipRect(-mCenterX, -mCenterY, 0, mCenterY);  //-->向下平移5像素显示分界线
        canvas.rotate(rotateZ);
        canvas.drawBitmap(bitmap, -mBitmapW / 2, -mBitmapH / 2, paint);
        canvas.restore();
    }

    /**
     * 开始执行动画
     */
    @Override
    public void onClick(View v) {
        ObjectAnimator animator2 = ObjectAnimator.ofFloat(XRotateAnimationView.this, "rotateStart", 0, 45);
        animator2.setDuration(1000);
        animator2.setStartDelay(0);
        animator2.start();

        ObjectAnimator animator1 = ObjectAnimator.ofFloat(XRotateAnimationView.this, "rotateZ", 0, 360);
        animator1.setDuration(1000);
        animator1.setStartDelay(1500);
        animator1.start();

        ObjectAnimator animator3 = ObjectAnimator.ofFloat(XRotateAnimationView.this, "rotateStart", 45, 0);
        animator3.setDuration(1000);
        animator3.setStartDelay(3000);
        animator3.start();

    }
}
