package com.homeraria.component.widget;


import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Property;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageView;

import com.homeraria.component.R;
import com.homeraria.component.enums.TargetAnimProcess;
import com.homeraria.component.utils.Utils;


public class ProgressBarCircularIndeterminate extends CustomBaseView {
    final static String ANDROIDXML = "http://schemas.android.com/apk/res/android";
    final static OvershootInterpolator OVERSHOOT_INTERPOLATOR = new OvershootInterpolator(4);
    final static int ANIMATION_GAP_FRAME_NUMBER = 10;

    int backgroundColor = Color.parseColor("#1E88E5");
    Bitmap bitmap1;
    Paint transPaint;
    private TargetAnimProcess mProcessStatus;
    private AnimationEndListener mEndListener;

    public void setEndListener(AnimationEndListener endListener) {
        this.mEndListener = endListener;
    }

    public ProgressBarCircularIndeterminate(Context context, AttributeSet attrs) {
        super(context, attrs);
        setAttributes(attrs);

        // 第一次解析将inJustDecodeBounds设置为true，来获取图片大小
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(getResources(), R.mipmap.company, options);
        // 调用上面定义的方法计算inSampleSize值
//            options.inSampleSize = calculateInSampleSize(options, canvas.getWidth(), canvas.getHeight());
        // 使用获取到的inSampleSize值再次解析图片
        options.inJustDecodeBounds = false;
        options.inMutable = true;

        bitmap1 = BitmapFactory.decodeResource(getResources(), R.mipmap.company, options);

        transPaint = new Paint();
//        transPaint.setAlpha(200);

        mProcessStatus = TargetAnimProcess.Idle;
    }

    // Set atributtes of XML to View
    protected void setAttributes(AttributeSet attrs) {

        setMinimumHeight(Utils.dpToPx(32, getResources()));
        setMinimumWidth(Utils.dpToPx(32, getResources()));

        //Set background Color
        // Color by resource
        int backgroundColor = attrs.getAttributeResourceValue(ANDROIDXML, "background", -1);
        if (backgroundColor != -1) {
            setBackgroundColor(getResources().getColor(backgroundColor));
        } else {
            // Color by hexadecimal
            int background = attrs.getAttributeIntValue(ANDROIDXML, "background", -1);
            if (background != -1)
                setBackgroundColor(background);
            else
                setBackgroundColor(Color.parseColor("#1E88E5"));
        }

        setMinimumHeight(Utils.dpToPx(3, getResources()));


    }

    /**
     * Make a dark color to ripple effect
     *
     * @return
     */
    protected int makePressColor() {
        int r = (this.backgroundColor >> 16) & 0xFF;
        int g = (this.backgroundColor >> 8) & 0xFF;
        int b = (this.backgroundColor >> 0) & 0xFF;
//		r = (r+90 > 245) ? 245 : r+90;
//		g = (g+90 > 245) ? 245 : g+90;
//		b = (b+90 > 245) ? 245 : b+90;
        return Color.argb(255, r, g, b);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        bitmap1 = Bitmap.createScaledBitmap(bitmap1, w, h, true);
        bitmap1 = createCircleBitmap(bitmap1);

//        start1stAnimation();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (cont > 0) {

            canvas.drawBitmap(bitmap1, 0, 0, transPaint);
        }

        if (firstAnimationOver == false) {
            drawFirstAnimation(canvas);
        }

        if (cont > 0) {
            drawSecondAnimation(canvas);
            invalidate();
        }
    }

    private Bitmap createCircleBitmap(Bitmap resource) {
        //获取图片的宽度
        int width = resource.getWidth();
        Paint paint = new Paint();
        //设置抗锯齿
        paint.setAntiAlias(true);

        //创建一个与原bitmap一样宽度的正方形bitmap
        Bitmap circleBitmap = Bitmap.createBitmap(width, width, Bitmap.Config.ARGB_8888);
        //以该bitmap为低创建一块画布
        Canvas canvas = new Canvas(circleBitmap);
        //以（width/2, width/2）为圆心，width/2为半径画一个圆
        canvas.drawCircle(width / 2, width / 2, width / 2, paint);

        //设置画笔为取交集模式
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        //裁剪图片
        canvas.drawBitmap(resource, 0, 0, paint);

        return circleBitmap;
    }

    float radius1 = 0;
    float radius2 = 0;
    int cont = 0;
    boolean firstAnimationOver = false;

    public float getRadius1() {
        return radius1;
    }

    public float getRadius2() {
        return radius2;
    }

    public void setRadius1(float radius1) {
        this.radius1 = (radius1 >= 1) ?
                (float) getWidth() / 2 : radius1 * (float) getWidth() / 2;
        postInvalidate();
    }

    public void setRadius2(float radius2) {
        this.radius2 = radius2 >= 1 ?
                getWidth() / 2 - Utils.dpToPx(4, getResources()) : radius2 * (getWidth() / 2 - Utils.dpToPx(4, getResources()));
        postInvalidate();
    }

    public void doClick() {
        if (mProcessStatus == TargetAnimProcess.Idle) {
            radius1 = 0;
            radius2 = 0;
            cont = 0;
            firstAnimationOver = false;

            /*
            在执行缩小动画后，scale都变成了0；
            如果此时不重置，重新执行动画后就看不见了
             */
            this.setScaleX(1);
            this.setScaleY(1);

            start1stAnimation();
            mProcessStatus = TargetAnimProcess.InRough;    //这里暂时把inRouch表示为正在动画中的状态，临时用一下
        } else if (mProcessStatus == TargetAnimProcess.InExact) {
            mProcessStatus = TargetAnimProcess.InRough;

            AnimatorSet animatorSet = new AnimatorSet();

            ObjectAnimator starScaleYAnimator = ObjectAnimator.ofFloat(this, ImageView.SCALE_Y, 1f, 1.2f, 1.3f, 0f);
            starScaleYAnimator.setDuration(500);
            starScaleYAnimator.setStartDelay(0);
            starScaleYAnimator.setInterpolator(new DecelerateInterpolator());

            ObjectAnimator starScaleXAnimator = ObjectAnimator.ofFloat(this, ImageView.SCALE_X, 1f, 1.2f, 1.3f, 0f);
            starScaleXAnimator.setDuration(500);
            starScaleXAnimator.setStartDelay(0);
            starScaleXAnimator.setInterpolator(new DecelerateInterpolator());

            animatorSet.playTogether(starScaleXAnimator, starScaleYAnimator);
            animatorSet.start();
            animatorSet.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationCancel(Animator animation) {
                    mProcessStatus = TargetAnimProcess.Idle;
                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    mProcessStatus = TargetAnimProcess.Idle;

                    if(mEndListener != null) {
                        mEndListener.onEnd(ProgressBarCircularIndeterminate.this.getLeft() + ProgressBarCircularIndeterminate.this.getWidth() / 2,
                                ProgressBarCircularIndeterminate.this.getTop() + ProgressBarCircularIndeterminate.this.getHeight() / 2);
                    }
                }
            });
        }
    }

    public void start1stAnimation() {
        AnimatorSet animatorSet = new AnimatorSet();

        ObjectAnimator starScaleXAnimator = ObjectAnimator.ofFloat(this, RADIUS_1, 0f, 1f);
        starScaleXAnimator.setDuration(350);
        starScaleXAnimator.setInterpolator(new AccelerateDecelerateInterpolator());

        ObjectAnimator starScaleXAnimator1 = ObjectAnimator.ofFloat(this, RADIUS_2, 0f, 1f);
        starScaleXAnimator1.setDuration(250);
        starScaleXAnimator1.setStartDelay(350);
        starScaleXAnimator.setInterpolator(new DecelerateInterpolator());

        animatorSet.playTogether(starScaleXAnimator, starScaleXAnimator1);
        animatorSet.start();
        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationCancel(Animator animation) {
                mProcessStatus = TargetAnimProcess.InExact;
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                mProcessStatus = TargetAnimProcess.InExact;
            }
        });
    }

    /**
     * Draw first animation of view
     *
     * @param canvas
     */
    private void drawFirstAnimation(Canvas canvas) {
        if (radius1 < getWidth() / 2) {
            Paint paint = new Paint();
            paint.setAntiAlias(true);
            paint.setColor(makePressColor());
//            radius1 = (radius1 >= getWidth() / 2) ? (float) getWidth() / 2 : radius1 + 1;
            canvas.drawCircle(getWidth() / 2, getHeight() / 2, radius1, paint);
        } else {
            Bitmap bitmap = Bitmap.createBitmap(canvas.getWidth(), canvas.getHeight(), Bitmap.Config.ARGB_8888);

            Canvas temp = new Canvas(bitmap);
            Paint paint = new Paint();
            paint.setAntiAlias(true);
            paint.setColor(makePressColor());
            temp.drawCircle(getWidth() / 2, getHeight() / 2, getHeight() / 2, paint);

            //镂空部分
            Paint transparentPaint = new Paint();
            transparentPaint.setAntiAlias(true);
            transparentPaint.setColor(getResources().getColor(android.R.color.transparent));
            transparentPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
            if (cont >= ANIMATION_GAP_FRAME_NUMBER) {
                /*
                圆形扩展到最后4dp的边框后需要等待cont++，直到30帧后，进一步收缩边框直到消失
                 */
                radius2 = (radius2 >= getWidth() / 2) ? (float) getWidth() / 2 : radius2 + 1;
//                radius2 = getWidth() / 2;
            } else {
//                radius2 = (radius2 >= getWidth() / 2 - Utils.dpToPx(4, getResources())) ? (float) getWidth() / 2 - Utils.dpToPx(4, getResources()) : radius2 + 1;
            }
            temp.drawCircle(getWidth() / 2, getHeight() / 2, radius2, transparentPaint);


            if (radius2 >= getWidth() / 2 - Utils.dpToPx(4, getResources())) {
                cont++;
//                firstAnimationOver = true;
            }
            if (radius2 >= getWidth() / 2)
                firstAnimationOver = true;


            canvas.drawBitmap(bitmap1, 0, 0, transPaint);
            canvas.drawBitmap(bitmap, 0, 0, new Paint());
        }
    }

    public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // 源图片的高度和宽度
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;
        if (height > reqHeight || width > reqWidth) {
            // 计算出实际宽高和目标宽高的比率
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            // 选择宽和高中最小的比率作为inSampleSize的值，这样可以保证最终图片的宽和高
            // 一定都会大于等于目标的宽和高。
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        return inSampleSize;
    }

    int arcD = 1;
    int arcO = 0;
    float rotateAngle = 0;
    int limite = 0;

    /**
     * Draw second animation of view
     *
     * @param canvas
     */
    private void drawSecondAnimation(Canvas canvas) {
        if (arcO == limite)
            arcD += 6;
        if (arcD >= 290 || arcO > limite) {
            arcO += 6;
            arcD -= 6;
        }
        if (arcO > limite + 290) {
            limite = arcO;
            arcO = limite;
            arcD = 1;
        }
        rotateAngle += 4;
        canvas.save();
        canvas.rotate(rotateAngle, getWidth() / 2, getHeight() / 2);

        Bitmap bitmap = Bitmap.createBitmap(canvas.getWidth(), canvas.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas temp = new Canvas(bitmap);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(backgroundColor);
//		temp.drawARGB(0, 0, 0, 255);
        temp.drawArc(new RectF(0, 0, getWidth(), getHeight()), arcO, arcD, true, paint);     //绘制扇形

        /*
        将扇形中间切掉
         */
        Paint transparentPaint = new Paint();
        transparentPaint.setAntiAlias(true);
        transparentPaint.setColor(getResources().getColor(android.R.color.transparent));
        transparentPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
        temp.drawCircle(getWidth() / 2, getHeight() / 2, (getWidth() / 2) - Utils.dpToPx(4, getResources()), transparentPaint);

        canvas.drawBitmap(bitmap, 0, 0, new Paint());
        canvas.restore();
    }


    // Set color of background
    public void setBackgroundColor(int color) {
        super.setBackgroundColor(getResources().getColor(android.R.color.transparent));
        if (isEnabled())
            beforeBackground = backgroundColor;
        this.backgroundColor = color;
    }

    public static final Property<ProgressBarCircularIndeterminate, Float> RADIUS_1 =
            new Property<ProgressBarCircularIndeterminate, Float>(Float.class, "radius1") {
                @Override
                public Float get(ProgressBarCircularIndeterminate object) {
                    return object.getRadius1();
                }

                @Override
                public void set(ProgressBarCircularIndeterminate object, Float value) {
                    object.setRadius1(value);
                }
            };

    public static final Property<ProgressBarCircularIndeterminate, Float> RADIUS_2 =
            new Property<ProgressBarCircularIndeterminate, Float>(Float.class, "radius2") {
                @Override
                public Float get(ProgressBarCircularIndeterminate object) {
                    return object.getRadius2();
                }

                @Override
                public void set(ProgressBarCircularIndeterminate object, Float value) {
                    object.setRadius2(value);
                }
            };

    //==================================================================================================
    public interface AnimationEndListener {
        void onEnd(int centerX, int centerY);
    }
}
