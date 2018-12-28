package com.homeraria.hencodeuicourse.app.material;

import android.animation.Animator;
import android.content.Context;
import android.graphics.Point;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import com.homeraria.hencodeuicourse.app.R;

import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.interpolator.view.animation.FastOutLinearInInterpolator;
import io.codetail.animation.ViewAnimationUtils;
import io.codetail.widget.RevealFrameLayout;

/**
 * @author sean
 * @describe 嵌入RevealFrameLayout，其中有多个View做水波展开切换
 * @email sean.zhou@oppo.com
 * @date on 2018/11/22 15:43
 */
public class CircleRevealLayout extends CoordinatorLayout {
    protected Context mContext;
    protected RevealFrameLayout mRevealLayout;
    protected GestureDetector mDetector;
    private GestureDetector.OnGestureListener mGestureListener;

    private int mCurrentViewIndex = 0;

    public CircleRevealLayout(Context context) {
        super(context);
        mContext = context;
    }

    public CircleRevealLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
    }

    public CircleRevealLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
    }

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();

        mRevealLayout = findViewById(R.id.reveal_layout);
        initGesture();

//        mCurrentViewIndex = mRevealLayout.getChildCount();
        for (int i = 0; i < mRevealLayout.getChildCount(); i++) {
            mRevealLayout.getChildAt(i).setOnTouchListener((view, event) -> mDetector.onTouchEvent(event));
        }

    }

    protected void initGesture() {
        mGestureListener = new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onDown(MotionEvent e) {
                return true;
            }

            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                /**
                 * ps:
                 * bringChildToFront()会使父控件内child排序发生实际变化，被带到front的永远处于最后一位，而其他的children随顺序前进；
                 * 所以这里mCurrentViewIndex永远保持为0不变的话，每次点击都会去除处于最前面一位的view，就相当于循环显示view了；
                 * 不需要对mCurrentViewIndex做递增！
                 */
                Log.v(CircleRevealLayout.this.getClass().getSimpleName(), "index:" + mCurrentViewIndex);

                View nextView = mRevealLayout.getChildAt(mCurrentViewIndex);
//                nextView.bringToFront();
                mRevealLayout.bringChildToFront(nextView);

//                new android.os.Handler().post(() -> {
//                    for (int i = 0; i < mRevealLayout.getChildCount(); i++) {
//                        Log.v(CircleRevealLayout.this.getClass().getSimpleName(), "steak(" + i + "):" + mRevealLayout.getChildAt(i).getClass().getSimpleName());
//                    }
//                });

                final float finalRadius = (float) Math.hypot(nextView.getWidth() / 2f, nextView.getHeight() / 2f) + hypo(nextView, e);
                Animator revealAnimator = ViewAnimationUtils.
                        createCircularReveal(nextView, (int) e.getX(), (int) e.getY(), 0, finalRadius, View.LAYER_TYPE_HARDWARE);
                revealAnimator.setDuration(1000);
                revealAnimator.setInterpolator(new FastOutLinearInInterpolator());
                revealAnimator.start();

                return true;
            }
        };

        mDetector = new GestureDetector(mContext, mGestureListener);
    }

    protected float hypo(View view, MotionEvent event) {
        Point p1 = new Point((int) event.getX(), (int) event.getY());
        Point p2 = new Point(view.getWidth() / 2, view.getHeight() / 2);

        return (float) Math.sqrt(Math.pow(p1.y - p2.y, 2) + Math.pow(p1.x - p2.x, 2));
    }

    protected float hypo(View view, int x, int y) {
        Point p1 = new Point(x, y);
        Point p2 = new Point(view.getWidth() / 2, view.getHeight() / 2);

        return (float) Math.sqrt(Math.pow(p1.y - p2.y, 2) + Math.pow(p1.x - p2.x, 2));
    }
}
