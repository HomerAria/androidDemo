package com.homeraria.hencodeuicourse.app.view.three_d;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.homeraria.hencodeuicourse.app.R;

public class XRotateView extends RelativeLayout {
    private Paint paint;
    private Bitmap bitmap;
    private Camera camera;
    private int mCenterX, mCenterY, mBitmapH, mBitmapW;

    public XRotateView(Context context) {
        super(context);
    }

    public XRotateView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public XRotateView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();

        bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.maps);
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        camera = new Camera();


        mBitmapW = bitmap.getWidth();
        mBitmapH = bitmap.getHeight();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //绘制默认原图
        super.onDraw(canvas);

        //不能在onAttachedToWindow中调用，得到的仍然是0
        mCenterX = getWidth() / 2;
        mCenterY = getHeight() / 2;

        //1.绘制在中央-->不移动canvas, rotateX符合左手系
//        canvas.save();
//        camera.save();
//        camera.rotateX(10);
//        camera.applyToCanvas(canvas);
//        canvas.drawBitmap(bitmap, mCenterX - mBitmapW/2, mCenterY - mBitmapH/2, paint);
//        camera.restore();
//        canvas.restore();

        //2.绘制在中央-->不移动canvas, rotateY不符合左手系
//        canvas.save();
//        camera.save();
//        camera.rotateY(10);
//        camera.applyToCanvas(canvas);
//        canvas.drawBitmap(bitmap, mCenterX - mBitmapW/2, mCenterY - mBitmapH/2, paint);
//        camera.restore();
//        canvas.restore();

        //3.绘制在中央-->不移动canvas, rotateZ不符合左手系
//        canvas.save();
//        camera.save();
//        camera.rotateZ(10);
//        camera.applyToCanvas(canvas);
//        canvas.drawBitmap(bitmap, mCenterX - mBitmapW/2, mCenterY - mBitmapH/2, paint);
//        camera.restore();
//        canvas.restore();

        //4.绘制在中央-->移动canvas，使原点处于中央
//        canvas.save();
//        camera.save();
//        camera.rotateX(10);
//        camera.applyToCanvas(canvas);
//        canvas.translate(mCenterX, mCenterY );
//        canvas.drawBitmap(bitmap,  - mBitmapW/2, - mBitmapH/2, paint);
//        canvas.restore();
//        camera.restore();

        //5.绘制在中央-->移动canvas，注意与4的顺序区别
        //5与4比较可以知道：canvas的变化是带动camera一起变化的，两者xy原点默认重合
//        canvas.save();
//        camera.save();
//        canvas.translate(mCenterX , mCenterY );
//        camera.rotateX(10);
//        camera.applyToCanvas(canvas);
//        canvas.drawBitmap(bitmap, - mBitmapW / 2, - mBitmapH / 2, paint);
//        canvas.restore();
//        camera.restore();

        //6.绘制在中央-->移动canvas,得出结论与2一致
//        canvas.save();
//        camera.save();
//        canvas.translate(mCenterX , mCenterY );
//        camera.rotateY(10);
//        camera.applyToCanvas(canvas);
//        canvas.drawBitmap(bitmap, - mBitmapW / 2, - mBitmapH / 2, paint);
//        canvas.restore();
//        camera.restore();

        //7.绘制在中央-->移动canvas;在apply之前再次移动canvas-->camera同样处于图片中心正上方
//        canvas.save();
//        camera.save();
//        canvas.translate(mCenterX , mCenterY );
//        camera.rotateX(10);
//        canvas.translate(-200, 0);
//        camera.applyToCanvas(canvas);
//        canvas.drawBitmap(bitmap, - mBitmapW / 2, - mBitmapH / 2, paint);
//        canvas.restore();
//        camera.restore();

        //8.绘制在中央-->移动canvas;变化顺序,在apply之后再次移动canvas-->camera位置没有随之变化
        //可以得到结论：apply后camera位置固定下来
//        canvas.save();
//        camera.save();
//        canvas.translate(mCenterX , mCenterY );
//        camera.rotateX(10);
//        camera.applyToCanvas(canvas);
//        canvas.translate(-200, 0);
//        canvas.drawBitmap(bitmap, - mBitmapW / 2, - mBitmapH / 2, paint);
//        canvas.restore();
//        camera.restore();

        //9.绘制类似flipboard图片
        //A.绘制右半边
//        canvas.save();
//        camera.save();
//        canvas.translate(mCenterX, mCenterY);
//        camera.rotateY(-45);
//        camera.applyToCanvas(canvas);
//        canvas.clipRect(0, -mBitmapH/2, mBitmapW/2, mBitmapH/2);
//        canvas.drawBitmap(bitmap, -mBitmapW / 2, -mBitmapH / 2, paint);
//        canvas.restore();
//        camera.restore();

        //B.绘制左半边-->平面部分没有3d效果，不需要camera参与
//        canvas.save();
//        canvas.translate(mCenterX, mCenterY);
//        camera.applyToCanvas(canvas);
//        canvas.clipRect(-mBitmapW/2, -mBitmapH/2, 0, mBitmapH/2);
//        canvas.drawBitmap(bitmap, -mBitmapW / 2, -mBitmapH / 2, paint);
//        canvas.restore();

        //10.FlipBoard,两侧分割线为斜向45度==》“\”
        //A.绘制3d侧
        canvas.save();
        camera.save();
        canvas.translate(mCenterX, mCenterY);
        camera.rotateZ(25);   //等同于canvas.rotate(-25),因为不论转动camera还是canvas，都是两者的坐标系同步转动
        camera.rotateY(-45);
        camera.applyToCanvas(canvas);
        canvas.clipRect(0, -mCenterY, mCenterX, mCenterY);
        canvas.rotate(25);
        canvas.drawBitmap(bitmap, -mBitmapW / 2, -mBitmapH / 2, paint);
        canvas.restore();
        camera.restore();

        //B.绘制平面
        canvas.save();
        canvas.translate(mCenterX, mCenterY);
        canvas.rotate(-25);
//        canvas.clipRect(-mCenterX, -mCenterY, 0, mCenterY);    //注意clip截取可视面的顺序
        canvas.clipRect(-mCenterX, -mCenterY, -5, mCenterY);  //-->向下平移5像素显示分界线
        canvas.rotate(25);
        canvas.drawBitmap(bitmap, -mBitmapW / 2, -mBitmapH / 2, paint);
        canvas.restore();
    }
}
