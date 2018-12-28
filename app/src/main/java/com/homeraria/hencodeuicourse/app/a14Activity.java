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

/**
 * Phenas项目呼吸效果演示
 */
public class a14Activity extends FragmentActivity
{
    //用于作为pageFragment的指示tab栏
    TabLayout mTabLayout;
    ViewPager mPager;

    //保存每个page的信息
    List<a14Activity.PageModel> mPageModelList = new ArrayList<>();

    {
        mPageModelList.add(new a14Activity.PageModel("涟漪", R.layout.phenas_water_ripple));
        mPageModelList.add(new a14Activity.PageModel("呼吸", R.layout.phenas_breath));
        mPageModelList.add(new a14Activity.PageModel("粒子直线Surface", R.layout.phenas_particle_stright));
        mPageModelList.add(new a14Activity.PageModel("粒子直线View", R.layout.phenas_particle_stright2));
        mPageModelList.add(new a14Activity.PageModel("粒子反弹View", R.layout.phenas_particle_debounce));
        mPageModelList.add(new a14Activity.PageModel("粒子容器控制", R.layout.phenas_particle_stright3));
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
                a14Activity.PageModel pageModel = mPageModelList.get(position);
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
