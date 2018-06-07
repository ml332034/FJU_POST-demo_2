package com.example.lawrence.fju_post;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;


public class PagerAdapter extends FragmentStatePagerAdapter {

    int mNoOfTabs;


    private final List<Fragment> fragmentList = new ArrayList<>();
    private final List<String> FragmentListTitles = new ArrayList<>();

    public PagerAdapter(FragmentManager fm) {
        super(fm);
    }
    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }



    @Override
    public int getCount() {
        return FragmentListTitles.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return FragmentListTitles.get(position);
    }
    public void AddFragment(Fragment fragment, String Titles){
        fragmentList.add(fragment);
        FragmentListTitles.add(Titles);
    }
}
