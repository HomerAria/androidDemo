package com.homeraria.hencodeuicourse.app.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import com.homeraria.hencodeuicourse.app.R;
import com.homeraria.hencodeuicourse.app.Utils;

/**
 * 控制view平移速度
 */
public class LinearLayoutDuration extends LinearLayout
{
    SeekBar mDurationSb;
    TextView mDurationValueTv;
    Button mAnimateBt;
    ImageView mImageView;

    int duration = 300;
    int translationState = 0;

    public LinearLayoutDuration(Context context)
    {
        super(context);
    }

    public LinearLayoutDuration(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    public LinearLayoutDuration(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
    }

    public LinearLayoutDuration(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes)
    {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onAttachedToWindow()
    {
        super.onAttachedToWindow();

        //初始化seekBar-->控制duration的值
        mDurationSb = findViewById(R.id.durationSb);
        mDurationValueTv = findViewById(R.id.durationValueTv);

        mDurationValueTv.setText("" + duration);
        mDurationSb.setMax(10);
        mDurationSb.setProgress(1);
        mDurationSb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener()
        {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser)
            {
                duration = progress * 300;
                mDurationValueTv.setText("" + duration);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar)
            {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar)
            {

            }
        });

        //初始化运动图标
        mAnimateBt = findViewById(R.id.control_button);
        mImageView = findViewById(R.id.image_icon);

        mAnimateBt.setOnClickListener(v -> {
            switch (translationState)
            {
                case 0:
                    mImageView.animate().translationX(Utils.dpToPixel(200)).setDuration(duration);
                    break;
                case 1:
                    mImageView.animate().translationX(0).setDuration(duration);
                    break;
            }
            if (translationState == 0)
            {
                translationState++;
            } else
            {
                translationState = 0;
            }
        });
    }
}
