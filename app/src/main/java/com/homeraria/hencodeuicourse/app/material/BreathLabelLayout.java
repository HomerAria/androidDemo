package com.homeraria.hencodeuicourse.app.material;

import android.animation.Animator;
import android.content.Context;
import android.graphics.Point;
import android.os.Handler;
import android.support.v4.view.animation.FastOutLinearInInterpolator;
import android.util.AttributeSet;
import android.util.Log;
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

    private RevealFrameLayout mRevealLayout, mRevealLayout1;
    private RelativeLayout mFreeLand;

    public BreathLabelLayout(Context context) {
        super(context);
    }

    public BreathLabelLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BreathLabelLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

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

        BreathLabel label = new BreathLabel(getContext());
        String labelText = label.add2Parent(mFreeLand, 100, 200).setLabel("动态LABEL").getLabel();

        Log.v(BreathLabelLayout.class.getSimpleName(), "width:" + (mFreeLand.getRight()));
//        mFreeLand.addView(label, label.getPositionParam(100, 100));
    }

    protected float hypo(View view, int x, int y) {
        Point p1 = new Point(x, y);
        Point p2 = new Point(view.getWidth() / 2, view.getHeight() / 2);

        return (float) Math.sqrt(Math.pow(p1.y - p2.y, 2) + Math.pow(p1.x - p2.x, 2));
    }
}
