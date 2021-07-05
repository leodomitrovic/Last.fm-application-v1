package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class MainActivity extends FragmentActivity {
    TabLayout tab;
    ViewPager2 pager;
    FragmentStateAdapter vpa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        vpa = new ViewPagerAdapter(this);
        tab = findViewById(R.id.tab_view);
        pager = findViewById(R.id.pager);
        pager.setAdapter(vpa);

        /*new TabLayoutMediator(tab, pager,
                (tab, position) -> tab.setText("OBJECT " + (position + 1))
        ).attach();*/
    }
}