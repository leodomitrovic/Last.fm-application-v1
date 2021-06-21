package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelStoreOwner;

import android.app.Fragment;

import android.app.FragmentManager;
import android.os.Bundle;
import android.widget.ImageView;

import com.google.android.material.tabs.TabLayout;

import org.jetbrains.annotations.NotNull;

public class MainActivity extends AppCompatActivity {
    TabLayout tab;
    FragmentManager fManager;
    Fragment f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tab = findViewById(R.id.tab_view);
        fManager = getFragmentManager();
        f = new TopArtistsFragment1(MainActivity.this);
        ImageView i;
        fManager.beginTransaction().replace(R.id.container, f).commit();
        tab.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0:
                        f = new TopArtistsFragment1(MainActivity.this);
                        fManager.beginTransaction().replace(R.id.container, f).commit();
                        return;
                    case 1:
                        f = new TopTracksFragment1(MainActivity.this);
                        fManager.beginTransaction().replace(R.id.container, f).commit();
                        return;
                    case 2:
                        f = new SearchArtistsFragment1(MainActivity.this);
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