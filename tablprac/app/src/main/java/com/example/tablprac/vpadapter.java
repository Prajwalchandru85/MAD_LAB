package com.example.tablprac;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;

public class vpadapter extends FragmentPagerAdapter
{
    private  final ArrayList<Fragment> fragmentArrayList=new ArrayList<>();
    private final ArrayList<String> fragmentTitle=new ArrayList<>();
    public vpadapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return fragmentArrayList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentArrayList.size();
    }
    public void addfrag(Fragment fragment,String title)
    {
    fragmentArrayList.add(fragment);
    fragmentTitle.add(title);

    }

    @Override
    public float getPageWidth(int position) {
        return fragmentTitle.get(position).length();
    }
}
