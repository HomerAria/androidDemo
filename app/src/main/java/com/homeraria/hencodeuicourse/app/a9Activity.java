package com.homeraria.hencodeuicourse.app;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;
import com.homeraria.hencodeuicourse.app.fragment.PageFragment;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.LayoutRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

public class a9Activity extends FragmentActivity
{
    //用于作为pageFragment的指示tab栏
    TabLayout mTabLayout;
    ViewPager mPager;

    //保存每个page的信息
    List<a9Activity.PageModel> mPageModelList = new ArrayList<>();

    {
        mPageModelList.add(new a9Activity.PageModel("饼图", R.layout.echars_pai));
        mPageModelList.add(new a9Activity.PageModel("柱状图", R.layout.echars_bar));
        mPageModelList.add(new a9Activity.PageModel("蜡烛图", R.layout.echars_candle));

    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_a9);

        mTabLayout = findViewById(R.id.tabLayout);
        mPager = findViewById(R.id.pager);

        mPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager())
        {
            @Override
            public Fragment getItem(int position)
            {
                a9Activity.PageModel pageModel = mPageModelList.get(position);
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
