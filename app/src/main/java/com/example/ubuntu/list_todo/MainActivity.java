package com.example.ubuntu.list_todo;


import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import com.example.ubuntu.list_todo.controllers.SectionsPagerAdapter;
import com.example.ubuntu.list_todo.controllers.Task;

public class MainActivity extends AppCompatActivity implements Todo.DataMover {
    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;
    private Done done;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

    }

    @Override
    public void onAttachFragment(Fragment fragment) {
        if(fragment instanceof Todo) {
            ((Todo) fragment).setCommunicator(this);
        }else{
            done = (Done) fragment;
        }
    }

    @Override
    public void moveThisToFragment(LinearLayout task) {
        done.addDone(task);
    }
}
