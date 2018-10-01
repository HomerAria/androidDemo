package com.homeraria.hencodeuicourse.app.view.evaluator;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.Button;
import android.widget.RelativeLayout;
import com.homeraria.hencodeuicourse.app.R;

public class ArgbEvaluatorRelativeLayout extends RelativeLayout
{
    Button mButton;
    ColorCircleView mCircleView;

    public ArgbEvaluatorRelativeLayout(Context context)
    {
        super(context);
    }

    public ArgbEvaluatorRelativeLayout(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    public ArgbEvaluatorRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
    }

    public ArgbEvaluatorRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes)
    {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onAttachedToWindow()
    {
        mButton = findViewById(R.id.control_button);
        mCircleView = findViewById(R.id.evaluator_view);

        mButton.setOnClickListener(v -> {
            ObjectAnimator animator = ObjectAnimator.ofInt(mCircleView, "color", 0xffff0000, 0xff00ff00);
            animator.setEvaluator(new ArgbEvaluator());
            animator.setDuration(1000)
                    .start();
        });
    }
}
