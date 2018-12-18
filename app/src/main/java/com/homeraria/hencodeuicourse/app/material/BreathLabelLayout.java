package com.homeraria.hencodeuicourse.app.material;

import android.animation.Animator;
import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ClipDescription;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Handler;
import android.os.SystemClock;
import android.support.annotation.RequiresApi;
import android.support.v4.view.animation.FastOutLinearInInterpolator;
import android.util.AttributeSet;
import android.util.Log;
import android.view.DragEvent;
import android.view.HapticFeedbackConstants;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.widget.RelativeLayout;

import com.homeraria.hencodeuicourse.app.R;

import io.codetail.widget.RevealFrameLayout;

/**
 * @author sean
 * @describe 实现呼吸按钮至标签的切换
 * @email sean.zhou@oppo.com
 * @date on 2018/12/4 14:55
 */
public class BreathLabelLayout extends RelativeLayout {
    private final static int BREATH_PERIOD = 2000;
    private final static String BREATH_LABEL_TAG = "BreathLabel";
    private final static String TAG = BreathLabelLayout.class.getSimpleName();

    private RevealFrameLayout mRevealLayout, mRevealLayout1;
    private RelativeLayout mFreeLand;
    private PointF mTouchOffset;

    public BreathLabelLayout(Context context) {
        super(context);
    }

    public BreathLabelLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BreathLabelLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();

        mRevealLayout = findViewById(R.id.reveal_layout);
        mRevealLayout1 = findViewById(R.id.reveal_layout1);
        mFreeLand = findViewById(R.id.free_layout);

        new Handler().postDelayed(() -> {
            View nextView = mRevealLayout.getChildAt(1);
            nextView.setVisibility(VISIBLE);
            mRevealLayout.bringChildToFront(nextView);
            final float finalRadius = (float) Math.hypot(nextView.getWidth() / 2f, nextView.getHeight() / 2f) +
                    hypo(nextView, (nextView.getRight() + nextView.getLeft()) / 2, (nextView.getTop() + nextView.getBottom()) / 2);

            try {
                Animator revealAnimator = ViewAnimationUtils.
                        createCircularReveal(nextView, (nextView.getRight() + nextView.getLeft()) / 2,
                                (nextView.getTop() + nextView.getBottom()) / 2, 0, finalRadius);
                revealAnimator.setDuration(1000);
                revealAnimator.setInterpolator(new FastOutLinearInInterpolator());
                revealAnimator.start();
            } catch (IllegalStateException e) {
                /*
                Cannot start this animator on a detached view!
                因为有延迟执行，可能已经返回上个界面，造成view已经detach
                 */
                Log.e(BreathLabelLayout.class.getSimpleName(), "Error: " + e.getMessage());
            }

        }, BREATH_PERIOD);

        new Handler().postDelayed(() -> {
            View currentView = findViewById(R.id.target);
            View nextView = findViewById(R.id.target_text);
            nextView.setVisibility(VISIBLE);
//            mRevealLayout.bringChildToFront(nextView);
            final float finalRadius = (float) Math.hypot(mRevealLayout1.getWidth() / 2f, mRevealLayout1.getHeight() / 2f) +
                    hypo(mRevealLayout1, (mRevealLayout1.getRight() + mRevealLayout1.getLeft()) / 2, (mRevealLayout1.getTop() + mRevealLayout1.getBottom()) / 2);

            Log.v(BreathLabelLayout.class.getSimpleName(), "text.x=" + nextView.getLeft() + ", current.x=" + currentView.getLeft());
            try {
                Animator revealAnimator = ViewAnimationUtils.
                        createCircularReveal(nextView, currentView.getLeft(),
                                (nextView.getTop() + nextView.getBottom()) / 2, 0, nextView.getWidth());
                revealAnimator.setDuration(1000);
                revealAnimator.setInterpolator(new FastOutLinearInInterpolator());
                revealAnimator.start();
            } catch (IllegalStateException e) {
                /*
                Cannot start this animator on a detached view!
                因为有延迟执行，可能已经返回上个界面，造成view已经detach
                 */
                Log.e(BreathLabelLayout.class.getSimpleName(), "Error: " + e.getMessage());
            }

        }, BREATH_PERIOD);

        AdaptiveBreathLabel label = new AdaptiveBreathLabel(getContext());
        label.setTag(BREATH_LABEL_TAG);
        String labelText = label.add2Parent(mFreeLand, 100, 200).setLabel("动态LABEL").getLabel();

        Log.v(BreathLabelLayout.class.getSimpleName(), "width:" + (mFreeLand.getRight()));
//        mFreeLand.addView(label, label.getPositionParam(100, 100));

        label.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                //这里的位置的参考坐标系是v，所以位置是相对v左上角的相对位置
                mTouchOffset = new PointF(event.getX(), event.getY());
            }
            return false;
        });

        label.setOnLongClickListener(new OnLongClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public boolean onLongClick(View v) {
                ClipData.Item item = new ClipData.Item(v.getTag() + "");
                ClipData dragData = new ClipData(v.getTag() + "", new String[]{ClipDescription.MIMETYPE_TEXT_PLAIN}, item);

//                DragShadowBuilder mShadow = new DragShadowBuilder(v);
                DragShadowBuilder mShadow = new MyDragShadowBuilder(v);
                v.startDragAndDrop(dragData,
                        mShadow,
                        null,
                        DRAG_FLAG_OPAQUE);       /*这个flag是针对View.DragShadowBuilder的，当调用startDragAndDrop方法中带有这个flag时，拖拽产生的投影的不透明的，否则是半透明的*/
                v.setVisibility(INVISIBLE);
//                mFreeLand.removeView(v);
                performHapticFeedback(HapticFeedbackConstants.LONG_PRESS);    //长按震动
                return true;
            }
        });

        mFreeLand.setOnDragListener(new OnDragListener() {
            private PointF lastPosition = null;
            private boolean isLeaving = false;

            @Override
            public boolean onDrag(View v, DragEvent event) {
                String simpleName = v.getClass().getSimpleName();
                Log.w(TAG, "view name:" + simpleName);

                float x = event.getX();
                float y = event.getY();
                //获取事件
                int action = event.getAction();
                switch (action) {
                    case DragEvent.ACTION_DRAG_STARTED:
                        Log.i(TAG, "开始拖拽");
                        break;
                    case DragEvent.ACTION_DRAG_ENDED:
                        /*
                        不能在这里处理拖拽结束显示View的操作，因为这是event传回的坐标都是原点坐标
                         */
                        if (isLeaving)
                            label.setVisibility(VISIBLE);
//                            label.moveTo((int) (lastPosition.x -mTouchOffset.x), (int)(lastPosition.y));
                        Log.i(TAG, "结束拖拽位置:x =" + x + ",y=" + y);
                        break;
                    case DragEvent.ACTION_DRAG_ENTERED:
                        Log.i(TAG, "拖拽的view进入监听的view时");
                        isLeaving = false;
                        break;
                    case DragEvent.ACTION_DRAG_EXITED:
                        Log.i(TAG, "拖拽的view离开监听的view时的位置:x =" + x + ",y=" + y);
                        isLeaving = true;
                        break;
                    case DragEvent.ACTION_DRAG_LOCATION:

                        long l = SystemClock.currentThreadTimeMillis();
                        Log.i(TAG, "拖拽的view在监听view中的位置:x =" + x + ",y=" + y);
                        lastPosition = new PointF(x, y);
                        break;
                    case DragEvent.ACTION_DROP:
                        Log.i(TAG, "释放拖拽的view位置:x =" + x + ",y=" + y);
                        label.moveTo((int) (event.getX() - mTouchOffset.x), (int) (event.getY() - mTouchOffset.y));
                        label.setVisibility(VISIBLE);
                        break;
                }
                //是否响应拖拽事件，true响应，返回false只能接受到ACTION_DRAG_STARTED事件，后续事件不会收到
                return true;
            }
        });

        //再添加一个BreathLabelAdaptive
//        new AdaptiveBreathLabel(getContext()).setLabel("自适应Label").add2Parent(mFreeLand, 900, 400);

    }

    protected float hypo(View view, int x, int y) {
        Point p1 = new Point(x, y);
        Point p2 = new Point(view.getWidth() / 2, view.getHeight() / 2);

        return (float) Math.sqrt(Math.pow(p1.y - p2.y, 2) + Math.pow(p1.x - p2.x, 2));
    }

    /**
     * 可以自定义被拖动时的View代替图层外观
     * 这里是为原图层view添加阴影效果
     */
    private class MyDragShadowBuilder extends View.DragShadowBuilder {

        // The drag shadow image, defined as a drawable thing
        private Drawable shadow;
        //存储新绘制的拖动阴影图像
        private Bitmap newBitmap;
        private Paint mPaint = new Paint();

        // Defines the constructor for myDragShadowBuilder
        public MyDragShadowBuilder(View v) {

            // Stores the View parameter passed to myDragShadowBuilder.
            super(v);

            // Creates a draggable image that will fill the Canvas provided by the system.
            shadow = new ColorDrawable(Color.LTGRAY);
        }

        // Defines a callback that sends the drag shadow dimensions and touch point back to the
        // system.
        @Override
        public void onProvideShadowMetrics(Point size, Point touch) {
            int width, height;

            //拖动阴影图像的宽度和高度，比原图大50%
            width = (int) (getView().getWidth());
            height = (int) (getView().getHeight());
            //设置拖动阴影图像绘制的区域
            shadow.setBounds(0, 0, width, height);
            //设置宽度和高度
            size.set(width, height);
            //设置手指在拖动阴影图案的位置
            touch.set((int) mTouchOffset.x, (int) mTouchOffset.y + 5);

            Log.v(TAG, "被拖动View位置：x=" + getView().getLeft() + ", y=" + getView().getTop() + ", 手指位置：x=" + touch.x + ", y=" + touch.y);

            //判断传人的View对象是否为ImageView对象，下面要获取ImageView控件中的图像资源
            if (getView() instanceof BreathLabel) {
                //getView方法返回的值就是构造方法传人的v参数值
                BreathLabel view = (BreathLabel) getView();
//                view.liftUp(10);
                //获取ImageView控件的Drawable对象
                Bitmap bitmap = loadBitmapFromViewBySystem(view);
//                if (view.getWidth() > 0 && view.getHeight() > 0) {
//                    bitmap = Bitmap.createBitmap(view.getWidth(),
//                            view.getHeight(), Bitmap.Config.ARGB_8888);
//                    Canvas canvas = new Canvas(bitmap);
//                    view.draw(canvas);
//                }

                //根据拖动阴影图像的尺寸创建一个新的可绘制的Bitmap图像
                newBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
                //Canvas与Bitmap关联
                Canvas canvas = new Canvas(newBitmap);
                //将原图绘制在画布上(图像尺寸放大50%)。这里的画布只是与newBitmap绑定的 完全独立的，现在还没有正式将图像绘制在拖动阴影图像上。实际上是将bitmap放大50%，然后绘制在newBitmap上
                canvas.drawBitmap(bitmap,
                        new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight()),
                        new Rect(0, 0, width, height), null);

            }else if(getView() instanceof AdaptiveBreathLabel){
                AdaptiveBreathLabel view = (AdaptiveBreathLabel) getView();
                Bitmap bitmap = loadBitmapFromViewBySystem(view);
                newBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
                //Canvas与Bitmap关联
                Canvas canvas = new Canvas(newBitmap);
                //将原图绘制在画布上(图像尺寸放大50%)。这里的画布只是与newBitmap绑定的 完全独立的，现在还没有正式将图像绘制在拖动阴影图像上。实际上是将bitmap放大50%，然后绘制在newBitmap上
                canvas.drawBitmap(bitmap,
                        new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight()),
                        new Rect(0, 0, width, height), null);
            }
        }

        // Defines a callback that draws the drag shadow in a Canvas that the system constructs
        // from the dimensions passed in onProvideShadowMetrics().
        @Override
        public void onDrawShadow(Canvas canvas) {

            // 讲图像正式的绘制在拖动阴影图像上。
//            shadow.draw(canvas);
//            mPaint.setShadowLayer(5, 5, 5, Color.BLACK);
            mPaint.setAntiAlias(true);
            canvas.drawBitmap(newBitmap, 0, 0, mPaint);
        }
    }

    public static Bitmap loadBitmapFromViewBySystem(View v) {
        if (v == null) {
            return null;
        }
        v.setDrawingCacheEnabled(true);
        v.buildDrawingCache();
        return v.getDrawingCache();
    }
}
