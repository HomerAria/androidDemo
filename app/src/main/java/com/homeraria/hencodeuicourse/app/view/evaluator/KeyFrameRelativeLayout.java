package com.homeraria.hencodeuicourse.app.view.evaluator;

import android.animation.Keyframe;
import android.animation.ObjectAnimator;
import android.animation.PointFEvaluator;
import android.animation.PropertyValuesHolder;
import android.animation.TypeEvaluator;
import android.content.Context;
import android.graphics.PointF;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.util.AttributeSet;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.RelativeLayout;
import com.homeraria.hencodeuicourse.app.R;

public class KeyFrameRelativeLayout extends RelativeLayout
{
    Button mButton;
    PositionCircleView mCircleView;

    int animationState = 0;

    public KeyFrameRelativeLayout(Context context)
    {
        super(context);
    }

    public KeyFrameRelativeLayout(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    public KeyFrameRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
    }

    public KeyFrameRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes)
    {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        mButton = findViewById(R.id.control_button);
        mCircleView = findViewById(R.id.evaluator_view);

        mButton.setOnClickListener(v -> {
            ObjectAnimator animator;

            switch (animationState) {
                case 0:
//                    animator = ObjectAnimator.ofObject(mCircleView, "point", new CustomPointFEvaluator(),
//                            new PointF(0, 0), new PointF(getWidth() / 2, getHeight() / 2));
                    //point是参数，我们需要一个回弹的效果
                    //这个效果通过Animator也是可以实现的
                    Keyframe frame1 = Keyframe.ofObject(0, new PointF(0, 0));
                    Keyframe frame2 = Keyframe.ofObject(0.8f, new PointF(getWidth() / 1.5f, getHeight() / 1.5f));
                    Keyframe frame3 = Keyframe.ofObject(1, new PointF(getWidth() / 2f, getHeight() / 2f));
//                    frame2.setInterpolator(new DecelerateInterpolator());
//                    frame3.setInterpolator(new AccelerateInterpolator());
                    PropertyValuesHolder holder = PropertyValuesHolder.ofKeyframe("point", frame1, frame2, frame3);
                    holder.setEvaluator(new PointFEvaluator());

                    animator = ObjectAnimator.ofPropertyValuesHolder(mCircleView, holder);
                    break;
                default:
                    //在api=26之后，系统继承了PointFEvaluator
                    animator = ObjectAnimator.ofObject(mCircleView, "point", new PointFEvaluator(),
                            new PointF(getWidth() / 2, getHeight() / 2), new PointF(0, 0));
                    break;
            }

            animator.setInterpolator(new FastOutSlowInInterpolator());
            animator.setDuration(1000)
                    .start();

            if (animationState == 0) {
                animationState++;
            } else {
                animationState = 0;
            }
        });
    }

    private class CustomPointFEvaluator implements TypeEvaluator<PointF>
    {
        PointF mPoint = new PointF();

        @Override
        public PointF evaluate(float fraction, PointF startValue, PointF endValue)
        {
            float x = startValue.x + fraction * (endValue.x - startValue.x);
            float y = startValue.y + fraction * (endValue.y - startValue.y);
            mPoint.set(x, y);

            return mPoint;
        }
    }
}
