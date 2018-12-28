package com.homeraria.hencodeuicourse.app.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;

import com.homeraria.hencodeuicourse.app.R;
import com.homeraria.hencodeuicourse.app.Utils;

import androidx.appcompat.widget.AppCompatSeekBar;

/**
 * 用作SquareImageView的外框，用于尺寸修改
 */
public class AdjustPanel extends RelativeLayout
{
    FrameLayout parentLayout;
    AppCompatSeekBar heightBar;
    AppCompatSeekBar widthBar;

    float bottomMargin = Utils.dpToPixel(48);
    float minWidth = Utils.dpToPixel(80);
    float minHeight = Utils.dpToPixel(100);

    public AdjustPanel(Context context)
    {
        super(context);
    }

    public AdjustPanel(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    public AdjustPanel(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
    }

    public AdjustPanel(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes)
    {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onAttachedToWindow()
    {
        super.onAttachedToWindow();

        parentLayout = findViewById(R.id.parentLayout);
        widthBar = findViewById(R.id.widthBar);
        heightBar = findViewById(R.id.heightBar);

        SeekBar.OnSeekBarChangeListener listener = new SeekBar.OnSeekBarChangeListener()
        {
            @Override
            public void onProgressChanged(SeekBar seekBar, int percent, boolean b)
            {
                LayoutParams layoutParams = (LayoutParams) parentLayout.getLayoutParams();
                layoutParams.width = (int) (minWidth + (AdjustPanel.this.getWidth()
                        - minWidth) * widthBar.getProgress() / 100);
                layoutParams.height = (int) (minHeight + (AdjustPanel.this.getHeight()
                        - bottomMargin - minHeight) * heightBar.getProgress() / 100);
                parentLayout.setLayoutParams(layoutParams);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar)
            {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar)
            {

            }
        };
        widthBar.setOnSeekBarChangeListener(listener);
        heightBar.setOnSeekBarChangeListener(listener);
    }
}
