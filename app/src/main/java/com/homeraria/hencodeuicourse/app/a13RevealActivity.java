package com.homeraria.hencodeuicourse.app;

import android.graphics.Point;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.MotionEvent;
import android.view.View;

import com.homeraria.hencodeuicourse.app.fragment.ContentFragment;

/**
 * Phenas项目呼吸效果演示
 */
public class a13RevealActivity extends FragmentActivity {
    ContentFragment mContentFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.base);

        mContentFragment = ContentFragment.newInstance(R.mipmap.facebook_icon);
        getSupportFragmentManager().beginTransaction().add(R.id.container, mContentFragment).commit();
    }



    protected float hypo(View view, MotionEvent event) {
        Point p1 = new Point((int) event.getX(), (int) event.getY());
        Point p2 = new Point(view.getWidth() / 2, view.getHeight() / 2);

        return (float) Math.sqrt(Math.pow(p1.y - p2.y, 2) + Math.pow(p1.x - p2.x, 2));
    }
}
