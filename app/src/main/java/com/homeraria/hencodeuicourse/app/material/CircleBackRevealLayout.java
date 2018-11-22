package com.homeraria.hencodeuicourse.app.material;

import android.animation.Animator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.view.animation.FastOutLinearInInterpolator;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import com.homeraria.hencodeuicourse.app.R;

import io.codetail.animation.ViewAnimationUtils;

/**
 * @author sean
 * @describe 可以实现水波展开的进入和返回，且其他控件
 * @email sean.zhou@oppo.com
 * @date on 2018/11/22 20:02
 */
public class CircleBackRevealLayout extends CircleRevealLayout {
    public CircleBackRevealLayout(Context context) {
        super(context);
    }

    public CircleBackRevealLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CircleBackRevealLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();

        //重新定义点击事件
        for (int i = 0; i < mRevealLayout.getChildCount(); i++) {
            mRevealLayout.getChildAt(i).setOnTouchListener((view, event) -> {
                if(view.getId() != R.id.parent){
                    mDetector.onTouchEvent(event);
                }
                return true;
            });
        }
        findViewById(R.id.snack_button).setOnClickListener(v -> Snackbar.make(findViewById(R.id.parent), "Snackbar", Snackbar.LENGTH_SHORT).show());
        findViewById(R.id.exit).setOnClickListener(v -> {
            View nextView = mRevealLayout.getChildAt(0);
//                nextView.bringToFront();
            mRevealLayout.bringChildToFront(nextView);

            final float finalRadius = (float) Math.hypot(nextView.getWidth() / 2f, nextView.getHeight() / 2f) + hypo(nextView, (v.getRight() + v.getLeft())/2, (v.getTop()+v.getBottom())/2);
            Animator revealAnimator = ViewAnimationUtils.
                    createCircularReveal(nextView, (v.getRight() + v.getLeft())/2, (v.getTop()+v.getBottom())/2, 0, finalRadius, View.LAYER_TYPE_HARDWARE);
            revealAnimator.setDuration(1000);
            revealAnimator.setInterpolator(new FastOutLinearInInterpolator());
            revealAnimator.start();
        });
    }
}
