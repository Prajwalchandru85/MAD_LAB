package com.example.tablaytpracc;

import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;

public class VPadapter extends FragmentPagerAdapter
{

    private final ArrayList<Fragment> fragmentArrayList= new ArrayList<>();
    private  final ArrayList<String> fragmenttitle=new ArrayList<>();
    public VPadapter(@NonNull FragmentManager fm, int behavior) {
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
    public void addfragment(Fragment fragment,String title)
    {
        fragmentArrayList.add(fragment);
        fragmenttitle.add(title);

    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return fragmentArrayList.get(position).toString();
    }
}

