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
public class RelativeLayoutScale extends RelativeLayout
{
    int TRANSLATION_STATE = 6;

    Button mButton;
    ImageView mImageView;
    int mTranslationState = 0;

    public RelativeLayoutScale(Context context)
    {
        super(context);
    }

    public RelativeLayoutScale(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    public RelativeLayoutScale(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
    }

    public RelativeLayoutScale(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes)
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
                    mImageView.animate().scaleX(-0.8f);   //-会发生镜像旋转
                    break;
                case 1:
                    mImageView.animate().scaleX(1);
                    break;
                case 2:
                    mImageView.animate().scaleYBy(1);    //scaleBy是基于原坐标增加的比例，-则代表减少
                    break;
                case 3:
                    mImageView.animate().scaleYBy(-1);
                    break;
                case 4:
                    mImageView.animate().scaleX(0.5f).scaleY(0.8f);
                    break;
                case 5:
                    mImageView.animate().scaleX(1).scaleY(1);
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
