package com.example.ubuntu.list_todo.controllers;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.ubuntu.list_todo.Done;
import com.example.ubuntu.list_todo.Todo;
public class SectionsPagerAdapter extends FragmentPagerAdapter {

    public SectionsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new Todo();

            case 1:
                return new Done();

            default:
                return  null;
        }
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "To Do";
            case 1:
                return "Done";
        }
        return null;
    }
}