package com.example.fengdeyu.xuanyue_reader.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.fengdeyu.xuanyue_reader.fragment.BookcaseFragment;
import com.example.fengdeyu.xuanyue_reader.fragment.HomeFindFragment;

import java.util.List;

/**
 * Created by fengdeyu on 2016/11/23.
 */

public class FragmentAdapter extends FragmentStatePagerAdapter {
    private List<Fragment> mFragments;
    private List<String> mTitles;

    public FragmentAdapter(FragmentManager fm,List<Fragment> mFragments, List<String> mTitles) {
        super(fm);
        this.mFragments = mFragments;
        this.mTitles = mTitles;
    }

    @Override
    public int getItemPosition(Object object) {
        if (object.getClass().getName().equals(BookcaseFragment.class.getName())
                || object.getClass().getName().equals(HomeFindFragment.class.getName())) {
            return POSITION_NONE;
        }
        return super.getItemPosition(object);
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles.get(position);
    }
}
