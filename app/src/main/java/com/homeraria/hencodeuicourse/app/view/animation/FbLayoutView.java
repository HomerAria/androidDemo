package com.homeraria.hencodeuicourse.app.view.animation;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.homeraria.hencodeuicourse.app.R;

public class FbLayoutView extends RelativeLayout implements View.OnClickListener {
    private Context mContext;
    private FbBaseView mImageView;

    /**
     * 在java中new时调用
     */
    public FbLayoutView(Context context) {
        super(context, null);
        mContext = context;
    }

    /**
     * 在xml中创建时调用，attributeSet就是xml中的属性tag
     */
    public FbLayoutView(Context context, AttributeSet attrs) {
        super(context, attrs, 0);
        mContext = context;
    }

    public FbLayoutView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    /**
     * 控件初始化
     */
    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();

        Button mButton = findViewById(R.id.control_button);
        mImageView = findViewById(R.id.target);
        mButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Toast.makeText(mContext, "dianjile ", Toast.LENGTH_SHORT).show();

        //对imageview执行动画,整个动画分三步
        //step1:
        ObjectAnimator animator1 = ObjectAnimator.ofFloat(mImageView, "degreeY", 0, -45);
        animator1.setDuration(1000);
        animator1.setStartDelay(500);
        animator1.start();


    }
}
