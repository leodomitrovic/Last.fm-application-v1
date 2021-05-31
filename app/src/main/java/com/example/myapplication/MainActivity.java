package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import android.app.Fragment;
import android.app.FragmentManager;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {
    TabLayout tab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tab = findViewById(R.id.tab_view);
        tab.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                FragmentManager fManager = getFragmentManager();
                Fragment f;
                switch (tab.getPosition()) {
                    case 0:
                        f = new TopArtistsFragment(MainActivity.this);
                        fManager.beginTransaction().replace(R.id.container, f).commit();
                        return;
                    case 1:
                        f = new TopTracksFragment(MainActivity.this);
                        fManager.beginTransaction().replace(R.id.container, f).commit();
                        return;
                    case 2:
                        f = new SearchArtistsFragment(MainActivity.this);
                        fManager.beginTransaction().replace(R.id.container, f).commit();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }
}