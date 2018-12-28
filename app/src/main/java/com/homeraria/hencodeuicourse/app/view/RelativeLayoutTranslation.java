package com.homeraria.hencodeuicourse.app.view;

import android.content.Context;
import android.graphics.Outline;
import android.graphics.Path;
import android.os.Build;
import androidx.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewOutlineProvider;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import com.homeraria.hencodeuicourse.app.R;

import static com.homeraria.hencodeuicourse.app.Utils.dpToPixel;

/**
 * 自定义Relation，在点击其中button子View之后，执行一个子View的平移动画
 */
public class RelativeLayoutTranslation extends RelativeLayout
{
    int TRANSLATION_STATE = 6;

    Button mButton;
    ImageView mImageView;
    int mTranslationState = 0;

    public RelativeLayoutTranslation(Context context)
    {
        super(context);
    }

    public RelativeLayoutTranslation(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    public RelativeLayoutTranslation(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
    }

    public RelativeLayoutTranslation(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes)
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

        mImageView.setOutlineProvider(new MusicOutlineProvider());

        //在button点击时，发生动画
        mButton.setOnClickListener(v -> {
            switch (mTranslationState)
            {
                case 0:
                    mImageView.animate().translationX(dpToPixel(100));
                    break;
                case 1:
                    mImageView.animate().translationX(0);
                    break;
                case 2:
                    mImageView.animate().translationYBy(dpToPixel(50));
                    break;
                case 3:
                    mImageView.animate().translationYBy(dpToPixel(-50));
                    break;
                case 4:
                    mImageView.animate().translationZ(dpToPixel(50));
                    break;
                case 5:
                    mImageView.animate().translationZBy(dpToPixel(-50));
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

    /**
     * 为音乐图标设置三角形的 Outline。
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    class MusicOutlineProvider extends ViewOutlineProvider
    {
        Path path = new Path();

        {
//            path.moveTo(dpToPixel(20), dpToPixel(20));
            path.rLineTo(dpToPixel(200), dpToPixel(0));
            path.rLineTo(dpToPixel(0), dpToPixel(200));
            path.rLineTo(dpToPixel(-200), dpToPixel(0));
            path.close();
        }

        @Override
        public void getOutline(View view, Outline outline) {
            outline.setConvexPath(path);
        }
    }
}
