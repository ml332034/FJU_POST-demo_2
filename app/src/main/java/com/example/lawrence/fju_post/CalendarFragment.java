package com.example.lawrence.fju_post;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class CalendarFragment extends Fragment {

    private TabLayout tabLayout;
    private AppBarLayout appBarLayout;
    private  ViewPager viewPager;

    public CalendarFragment() {
        // Required empty public constructor
    }


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View rootView = inflater.inflate(R.layout.fragment_calendar, container, false);
        viewPager = (ViewPager) rootView.findViewById(R.id.viewpager);
        appBarLayout = (AppBarLayout) rootView.findViewById(R.id.appbarid);
        tabLayout = (TabLayout) rootView.findViewById(R.id.tablayout_id);
        setupViewPager(viewPager);
        return rootView;

    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());
        adapter.AddFragment(new FragmentCalendar(), "行事曆");
        adapter.AddFragment(new FragmentToDolist(), "待辦事項");

        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

    }


}

