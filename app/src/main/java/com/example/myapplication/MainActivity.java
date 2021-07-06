package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import org.jetbrains.annotations.NotNull;

public class MainActivity extends AppCompatActivity {
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
        TabLayoutMediator.TabConfigurationStrategy strategy = new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull @NotNull TabLayout.Tab tab, int position) {
                if (position == 0) {
                    tab.setText("Top artists");
                } else if (position == 1) {
                    tab.setText("Top tracks");
                } else {
                    tab.setText("Search artists");
                }
            }
        };
        new TabLayoutMediator(tab, pager, strategy).attach();
    }
}