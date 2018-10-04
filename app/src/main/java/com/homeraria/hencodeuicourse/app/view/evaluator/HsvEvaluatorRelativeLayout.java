package com.homeraria.hencodeuicourse.app.view.evaluator;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.TypeEvaluator;
import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.widget.Button;
import android.widget.RelativeLayout;
import com.homeraria.hencodeuicourse.app.R;

public class HsvEvaluatorRelativeLayout extends RelativeLayout
{
    Button mButton;
    ColorCircleView mCircleView;

    public HsvEvaluatorRelativeLayout(Context context)
    {
        super(context);
    }

    public HsvEvaluatorRelativeLayout(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    public HsvEvaluatorRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
    }

    public HsvEvaluatorRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes)
    {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        mButton = findViewById(R.id.control_button);
        mCircleView = findViewById(R.id.evaluator_view);

        mButton.setOnClickListener(v -> {
            ObjectAnimator animator = ObjectAnimator.ofInt(mCircleView, "color", 0xffff0000, 0xff00ff00);
//            animator.setEvaluator(new ArgbEvaluator());
            //使用自定义的evaluator
            animator.setEvaluator(new HsvEvaluator());

            animator.setDuration(1000)
                    .start();
        });

    }

    private class HsvEvaluator implements TypeEvaluator<Integer>
    {
        float[] startHsv = new float[3];
        float[] endHsv = new float[3];
        float[] outHsv = new float[3];

        /**
         * 根据某个动画完成度，得到我们需要的参数值（这里就是color的int值）
         * @param fraction 动画完成百分比
         * @param startValue 动画完成度0%时的值
         * @param endValue 动画完成度100%时的值
         * @return 需要的值
         */
        @Override
        public Integer evaluate(float fraction, Integer startValue, Integer endValue)
        {
            // 把 ARGB 转换成 HSV
            Color.colorToHSV(startValue, startHsv);
            Color.colorToHSV(endValue, endHsv);

            // 计算当前动画完成度（fraction）所对应的颜色值
            if (endHsv[0] - startHsv[0] > 180)
            {
                endHsv[0] -= 360;
            } else if (endHsv[0] - startHsv[0] < -180)
            {
                endHsv[0] += 360;
            }
            outHsv[0] = startHsv[0] + (endHsv[0] - startHsv[0]) * fraction;
            if (outHsv[0] > 360)
            {
                outHsv[0] -= 360;
            } else if (outHsv[0] < 0)
            {
                outHsv[0] += 360;
            }
            outHsv[1] = startHsv[1] + (endHsv[1] - startHsv[1]) * fraction;
            outHsv[2] = startHsv[2] + (endHsv[2] - startHsv[2]) * fraction;

            // 计算当前动画完成度（fraction）所对应的透明度
            int alpha = startValue >> 24 + (int) ((endValue >> 24 - startValue >> 24) * fraction);

            // 把 HSV 转换回 ARGB 返回
            return Color.HSVToColor(alpha, outHsv);
        }
    }
}
