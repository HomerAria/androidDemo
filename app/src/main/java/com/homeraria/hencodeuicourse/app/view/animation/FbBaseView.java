package com.homeraria.hencodeuicourse.app.view.animation;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Keep;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;

import com.homeraria.hencodeuicourse.app.R;

public class FbBaseView extends View {
    //Y轴方向旋转角度
    private float degreeY;
    //不变的那一半，Y轴方向旋转角度
    private float fixDegreeY;
    //Z轴方向（平面内）旋转的角度
    private float degreeZ;

    private Paint paint;
    private Bitmap bitmap;
    private Camera camera;

    public FbBaseView(Context context) {
        this(context, null);
    }

    public FbBaseView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FbBaseView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.maps);
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        camera = new Camera();

        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        float newZ = -displayMetrics.density * 6;
        camera.setLocation(0, 0, newZ);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //bitmap的尺寸
        int bitmapWidth = bitmap.getWidth();
        int bitmapHeight = bitmap.getHeight();
        //本view的尺寸
        int centerX = getWidth() / 2;
        int centerY = getHeight() / 2;
        //bitmap的左上角在本view坐标中的位置
        int x = centerX - bitmapWidth / 2;
        int y = centerY - bitmapHeight / 2;

        //画变换的一半
        //先旋转，再裁切，再使用camera执行3D动效,**然后保存camera效果**,最后再旋转回来
        canvas.save();
        camera.save();
        canvas.translate(centerX, centerY);
        canvas.rotate(-degreeZ);
        camera.rotateY(degreeY);
        camera.applyToCanvas(canvas);
        //计算裁切参数时清注意，此时的canvas的坐标系已经移动
        canvas.clipRect(0, -centerY, centerX, centerY);
        canvas.rotate(degreeZ);
        canvas.translate(-centerX, -centerY);
        camera.restore();
        canvas.drawBitmap(bitmap, x, y, paint);
        canvas.restore();

        //画不变换的另一半
        canvas.save();
        camera.save();
        canvas.translate(centerX, centerY);
        canvas.rotate(-degreeZ);
        //计算裁切参数时清注意，此时的canvas的坐标系已经移动
        canvas.clipRect(-centerX, -centerY, 0, centerY);
        //此时的canvas的坐标系已经旋转，所以这里是rotateY
        camera.rotateY(fixDegreeY);
        camera.applyToCanvas(canvas);
        canvas.rotate(degreeZ);
        canvas.translate(-centerX, -centerY);
        camera.restore();
        canvas.drawBitmap(bitmap, x, y, paint);
        canvas.restore();
    }

    /**
     * 启动动画之前调用，把参数reset到初始状态
     */
    public void reset() {
        degreeY = 0;
        fixDegreeY = 0;
        degreeZ = 0;
    }

    /**
     * 以下三个属性用于暴露给ObjectAnimator执行动画
     * @param fixDegreeY
     */
    @Keep
    public void setFixDegreeY(float fixDegreeY) {
        this.fixDegreeY = fixDegreeY;
        invalidate();
    }

    @Keep
    public void setDegreeY(float degreeY) {
        this.degreeY = degreeY;
        invalidate();
    }

    @Keep
    public void setDegreeZ(float degreeZ) {
        this.degreeZ = degreeZ;
        invalidate();
    }

}
