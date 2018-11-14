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

/**
 * Phenas项目呼吸效果演示
 */
public class a12Activity extends FragmentActivity
{
    //用于作为pageFragment的指示tab栏
    TabLayout mTabLayout;
    ViewPager mPager;

    //保存每个page的信息
    List<a12Activity.PageModel> mPageModelList = new ArrayList<>();

    {
        mPageModelList.add(new a12Activity.PageModel("涟漪", R.layout.phenas_water_ripple));
        mPageModelList.add(new a12Activity.PageModel("呼吸", R.layout.phenas_breath));
        mPageModelList.add(new a12Activity.PageModel("粒子直线Surface", R.layout.phenas_particle_stright));
        mPageModelList.add(new a12Activity.PageModel("粒子直线View", R.layout.phenas_particle_stright2));
        mPageModelList.add(new a12Activity.PageModel("粒子反弹View", R.layout.phenas_particle_debounce));
        mPageModelList.add(new a12Activity.PageModel("粒子容器控制", R.layout.phenas_particle_stright3));
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
                a12Activity.PageModel pageModel = mPageModelList.get(position);
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
