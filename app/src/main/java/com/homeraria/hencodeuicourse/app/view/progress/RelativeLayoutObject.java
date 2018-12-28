package com.homeraria.hencodeuicourse.app.view.progress;

import android.animation.ObjectAnimator;
import android.content.Context;
import androidx.interpolator.view.animation.FastOutSlowInInterpolator;
import android.util.AttributeSet;
import android.widget.Button;
import android.widget.RelativeLayout;
import com.homeraria.hencodeuicourse.app.R;

/**
 * 自定义Relation，在点击其中button子View之后，执行一个子View的平移动画
 */
public class RelativeLayoutObject extends RelativeLayout
{
    Button mButton;
    ObjectAnimatorView mAnimatorView;

    public RelativeLayoutObject(Context context)
    {
        super(context);
    }

    public RelativeLayoutObject(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    public RelativeLayoutObject(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
    }

    public RelativeLayoutObject(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes)
    {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    /**
     * ALT+insert可以自动添加需要override的方法
     * 此方法在界面可见<--->不可见之间转化时调用，用于pager左右切换时更新view
     */
    @Override
    protected void onAttachedToWindow()
    {
        mButton = findViewById(R.id.control_button);
        mAnimatorView = findViewById(R.id.object_view);

        //在button点击时，发生动画
        mButton.setOnClickListener(v -> {
            ObjectAnimator animator = ObjectAnimator.ofFloat(mAnimatorView, "progress", 0, 80);
            animator.setDuration(1000)
                    .setInterpolator(new FastOutSlowInInterpolator());
            animator.start();
        });
    }

}
