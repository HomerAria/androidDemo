package com.homeraria.hencodeuicourse.app;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class a7Activity extends FragmentActivity
{
    //用于作为pageFragment的指示tab栏
    TabLayout mTabLayout;
    ViewPager mPager;

    //保存每个page的信息
    List<a7Activity.PageModel> mPageModelList = new ArrayList<>();

    {
        mPageModelList.add(new a7Activity.PageModel("ArgbEvaluator", R.layout.argb_evaluator));
        mPageModelList.add(new a7Activity.PageModel("HsvEvaluator", R.layout.hsv_evaluator));
        mPageModelList.add(new a7Activity.PageModel("PointEvaluator", R.layout.point_evaluator));
        mPageModelList.add(new a7Activity.PageModel("Multi", R.layout.multi));
        mPageModelList.add(new a7Activity.PageModel("KeyFrame", R.layout.key_frame));

    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_a7);

        mTabLayout = findViewById(R.id.tabLayout);
        mPager = findViewById(R.id.pager);

        mPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager())
        {
            @Override
            public Fragment getItem(int position)
            {
                a7Activity.PageModel pageModel = mPageModelList.get(position);
                return PageFragment.newInstance(pageModel.practiceLayoutRes);
            }

            @Override
            public int getCount()
            {
                return mPageModelList.size();
            }

            @Override
            public CharSequence getPageTitle(int position)
            {
                return mPageModelList.get(position).titleRes;
            }
        });

        mTabLayout.setupWithViewPager(mPager);
    }

    private class PageModel
    {
        String titleRes;
        @LayoutRes
        int practiceLayoutRes;

        PageModel(String titleRes, @LayoutRes int practiceLayoutRes)
        {
            this.titleRes = titleRes;
            this.practiceLayoutRes = practiceLayoutRes;
        }
    }
}
