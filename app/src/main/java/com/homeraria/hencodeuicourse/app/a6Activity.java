package com.homeraria.hencodeuicourse.app;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import java.util.ArrayList;
import java.util.List;

public class a6Activity extends FragmentActivity
{
    //用于作为pageFragment的指示tab栏
    TabLayout mTabLayout;
    ViewPager mPager;

    //保存每个page的信息
    List<PageModel> mPageModelList = new ArrayList<>();

    {
        mPageModelList.add(new PageModel("translation", R.layout.translation));
        mPageModelList.add(new PageModel("rotation", R.layout.rotation));
        mPageModelList.add(new PageModel("scale", R.layout.scale));
        mPageModelList.add(new PageModel("alpha", R.layout.alpha));
        mPageModelList.add(new PageModel("duration", R.layout.duration));
        mPageModelList.add(new PageModel("object", R.layout.object_animator));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_a6);

        mTabLayout = findViewById(R.id.tabLayout);
        mPager = findViewById(R.id.pager);

        mPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager())
        {
            @Override
            public Fragment getItem(int position)
            {
                PageModel pagemodel = mPageModelList.get(position);
                return PageFragment.newInstance(pagemodel.practiceLayoutRes);
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
