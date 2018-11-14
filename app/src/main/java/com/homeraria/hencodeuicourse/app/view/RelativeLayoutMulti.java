package com.homeraria.hencodeuicourse.app.view;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.Context;
import android.util.AttributeSet;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AnimationSet;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import com.homeraria.hencodeuicourse.app.R;
import com.homeraria.hencodeuicourse.app.Utils;

/**
 * 一次执行多个动画属性-->PropertyValuesHolder
 * 分步依次执行一组动画，执行的顺序可以自由控制-->AnimatiorSet
 */
public class RelativeLayoutMulti extends RelativeLayout
{
    Button mButton;
    ImageView mImageView;
    int animationState = 0;
    boolean init = true;

    public RelativeLayoutMulti(Context context)
    {
        super(context);
    }

    public RelativeLayoutMulti(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    public RelativeLayoutMulti(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
    }

    /**
     * ALT+insert可以自动添加需要override的方法
     * 此方法在界面可见<--->不可见之间转化时调用，用于pager左右切换时更新view
     */
    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        mButton = findViewById(R.id.control_button);
        mImageView = findViewById(R.id.icon_view);

        //PropertyValuesHolder用于保存propertyAnimation,ObjectAnimator可以一次调用
        PropertyValuesHolder holderA1 = PropertyValuesHolder.ofFloat("scaleX", 0, 1);
        PropertyValuesHolder holderA2 = PropertyValuesHolder.ofFloat("scaleY", 0, 1);
        PropertyValuesHolder holderA3 = PropertyValuesHolder.ofFloat("alpha", 0, 1);
        PropertyValuesHolder holderB1 = PropertyValuesHolder.ofFloat("scaleX", 1, 0);
        PropertyValuesHolder holderB2 = PropertyValuesHolder.ofFloat("scaleY", 1, 0);
        PropertyValuesHolder holderB3 = PropertyValuesHolder.ofFloat("alpha", 1, 0);

        mButton.setOnClickListener(v -> {
            //直接使用propertyAnimation
            switch (animationState) {
                case 0:
//                    mImageView.animate()
//                            .scaleX(0)
//                            .scaleY(0)
//                            .alpha(0);
                    ObjectAnimator animator = ObjectAnimator.ofPropertyValuesHolder(mImageView, holderB1, holderB2, holderB3);
                    if (init) {
                        animator.start();
                    } else {
                        ObjectAnimator animator4 = ObjectAnimator.ofFloat(mImageView, "translationX", Utils.dpToPixel(0));
                        ObjectAnimator animator5 = ObjectAnimator.ofFloat(mImageView, "rotation", 0);

                        AnimatorSet set = new AnimatorSet();
                        set.playSequentially(animator5, animator4, animator);
                        set.start();
                    }
                    break;
                case 1:
//                    mImageView.animate()
//                            .scaleX(1)
//                            .scaleY(1)
//                            .alpha(1);
                    ObjectAnimator animator1 = ObjectAnimator.ofPropertyValuesHolder(mImageView, holderA1, holderA2, holderA3);
                    animator1.setInterpolator(new LinearInterpolator());

                    ObjectAnimator animator2 = ObjectAnimator.ofFloat(mImageView, "translationX", Utils.dpToPixel(200));
                    animator2.setInterpolator(new DecelerateInterpolator());

                    ObjectAnimator animator3 = ObjectAnimator.ofFloat(mImageView, "rotation", 90);

                    AnimatorSet set = new AnimatorSet();
//                    set.playSequentially(animator1, animator2, animator3);
                    set.play(animator1).before(animator2);
                    set.playTogether(animator2, animator3);
                    set.start();
                    break;
            }

            init = false;

            if (animationState == 0) {
                animationState++;
            } else {
                animationState = 0;
            }

        });
    }

}
