package com.homeraria.hencodeuicourse.app;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.homeraria.hencodeuicourse.app.fragment.PageFragment;

import java.util.ArrayList;
import java.util.List;

public class a10Activity extends FragmentActivity
{
    //用于作为pageFragment的指示tab栏
    TabLayout mTabLayout;
    ViewPager mPager;

    //保存每个page的信息
    List<a10Activity.PageModel> mPageModelList = new ArrayList<>();

    {
        mPageModelList.add(new a10Activity.PageModel("FB基础", R.layout.flip_board_base));
//        mPageModelList.add(new a10Activity.PageModel("FB完整", R.layout.flip_board));

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
                a10Activity.PageModel pageModel = mPageModelList.get(position);
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
