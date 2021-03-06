package com.homeraria.hencodeuicourse.app.fragment;

import android.os.Bundle;
import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;

import com.homeraria.hencodeuicourse.app.R;


public class PageFragment extends Fragment
{
    @LayoutRes
    int practiceLayoutRes;

    public static PageFragment newInstance(@LayoutRes int practiceLayoutRes)
    {
        PageFragment fragment = new PageFragment();
        Bundle args = new Bundle();
        args.putInt("practiceLayoutRes", practiceLayoutRes);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_page, container, false);

        ViewStub practiceStub = view.findViewById(R.id.practiceStub);
        practiceStub.setLayoutResource(practiceLayoutRes);
        practiceStub.inflate();

        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (args != null)
        {
            practiceLayoutRes = args.getInt("practiceLayoutRes");
        }
    }

}