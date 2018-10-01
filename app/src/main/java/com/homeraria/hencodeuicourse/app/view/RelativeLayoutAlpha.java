package com.homeraria.hencodeuicourse.app.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import com.homeraria.hencodeuicourse.app.R;

/**
 * 自定义Relation，在点击其中button子View之后，执行一个子View的平移动画
 */
public class RelativeLayoutAlpha extends RelativeLayout
{
    int TRANSLATION_STATE = 4;

    Button mButton;
    ImageView mImageView;
    int mTranslationState = 0;

    public RelativeLayoutAlpha(Context context)
    {
        super(context);
    }

    public RelativeLayoutAlpha(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    public RelativeLayoutAlpha(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
    }

    public RelativeLayoutAlpha(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes)
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
        mImageView = findViewById(R.id.icon_view);

        //在button点击时，发生动画
        mButton.setOnClickListener(v -> {
            switch (mTranslationState)
            {
                case 0:
                    mImageView.animate().alpha(0);
                    break;
                case 1:
                    mImageView.animate().alpha(1);
                    break;
                case 2:
                    mImageView.animate().alphaBy(-1f);    //负值表示变浅，正值变深
                    break;
                case 3:
                    mImageView.animate().alphaBy(0.5f);
                    break;
            }

            //6个状态分别是X/Y/Z轴的平移和恢复，然后有重归于0
            mTranslationState++;
            if(mTranslationState == TRANSLATION_STATE)
            {
                mTranslationState = 0;
            }
        });
    }

}
