package com.homeraria.hencodeuicourse.app.view.evaluator;

import android.animation.ObjectAnimator;
import android.animation.PointFEvaluator;
import android.animation.TypeEvaluator;
import android.content.Context;
import android.graphics.PointF;
import androidx.interpolator.view.animation.FastOutSlowInInterpolator;
import android.util.AttributeSet;
import android.widget.Button;
import android.widget.RelativeLayout;
import com.homeraria.hencodeuicourse.app.R;

public class PointEvaluatorRelativeLayout extends RelativeLayout
{
    Button mButton;
    PositionCircleView mCircleView;

    int animationState = 0;

    public PointEvaluatorRelativeLayout(Context context)
    {
        super(context);
    }

    public PointEvaluatorRelativeLayout(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    public PointEvaluatorRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
    }

    public PointEvaluatorRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes)
    {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onAttachedToWindow()
    {
        mButton = findViewById(R.id.control_button);
        mCircleView = findViewById(R.id.evaluator_view);

        mButton.setOnClickListener(v -> {
            ObjectAnimator animator;

            switch (animationState)
            {
                case 0:
                    animator = ObjectAnimator.ofObject(mCircleView, "point", new CustomPointFEvaluator(),
                            new PointF(0, 0), new PointF(getWidth() / 2, getHeight() / 2));
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

            if (animationState == 0)
            {
                animationState++;
            } else
            {
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
