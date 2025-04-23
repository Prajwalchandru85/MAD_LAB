package com.example.tablprac;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {
    TabLayout tabLayout;
    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        tabLayout = findViewById(R.id.tabLayout2);
        viewPager = findViewById(R.id.vp);
        tabLayout.setupWithViewPager(viewPager);
        vpadapter vpadapter = new vpadapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        vpadapter.addfrag(new fragment1(), "Monday");
        vpadapter.addfrag(new fragment2(), "Tuesday");
      //  vpadapter.addfrag(new fragment3(), "Wednesday");
        viewPager.setAdapter(vpadapter);



    }
}