package com.homeraria.hencodeuicourse.app.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.homeraria.hencodeuicourse.app.R;
import com.homeraria.hencodeuicourse.app.widget.ActivityRevealListener;
import com.homeraria.hencodeuicourse.app.widget.ScreenShotInterface;

/**
 * Created by Konstantin on 22.12.2014.
 */
public class ContentFragment extends Fragment implements ScreenShotInterface {
    private View containerView;
    private ImageView mImageView;
    protected int res;
    private Bitmap bitmap;
    private ActivityRevealListener mActivityListener;

    public static ContentFragment newInstance(int resId) {
        ContentFragment contentFragment = new ContentFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(Integer.class.getName(), resId);
        contentFragment.setArguments(bundle);
        return contentFragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context instanceof ActivityRevealListener){
            mActivityListener = (ActivityRevealListener) context;
        }else{
            throw new RuntimeException(context.toString() + "must implement ActivityRevealListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mActivityListener = null;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        res = getArguments().getInt(Integer.class.getName());
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_content, container, false);
        this.containerView = rootView.findViewById(R.id.container);
        mImageView = rootView.findViewById(R.id.image_content);
//        mImageView.setClickable(true);
//        mImageView.setFocusable(true);
        mImageView.setImageResource(res);
        Log.v("ContentFragment", "res="+ res);
//        mImageView.setOnTouchListener((v, event) -> {
//            bitmap = takeScreenShotSync();
//
//            return true;
//        });
        rootView.findViewById(R.id.change).setOnClickListener(v->{
            bitmap = takeScreenShotSync();
            mActivityListener.onFragmentSwitch(v, ContentFragment.this);
        });


        return rootView;
    }

    @Override
    public void takeScreenShot() {
        Thread thread = new Thread() {
            @Override
            public void run() {
                Bitmap bitmap = Bitmap.createBitmap(containerView.getWidth(),
                        containerView.getHeight(), Bitmap.Config.ARGB_8888);
                Canvas canvas = new Canvas(bitmap);
                containerView.draw(canvas);
                ContentFragment.this.bitmap = bitmap;
            }
        };

        thread.start();

    }

    private Bitmap takeScreenShotSync(){
        Bitmap bitmap = Bitmap.createBitmap(containerView.getWidth(),
                containerView.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        containerView.draw(canvas);
        return bitmap;
    }

    @Override
    public Bitmap getBitmap() {
        return bitmap;
    }

    public interface OnSwitchListener {
        void onSwitch(MotionEvent event, Bitmap bitmap);
    }
}

