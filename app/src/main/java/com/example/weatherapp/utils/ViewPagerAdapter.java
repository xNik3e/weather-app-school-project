package com.example.weatherapp.utils;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.weatherapp.fragments.WeatherInfo;

public class ViewPagerAdapter extends FragmentStateAdapter {
    private int numPages;

    public ViewPagerAdapter(@NonNull FragmentActivity fragmentActivity, int numPages) {
        super(fragmentActivity);
        this.numPages = numPages;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        WeatherInfo fragment = new WeatherInfo(position);
        return fragment;
    }
    @Override
    public int getItemCount() {
        return numPages;
    }
}
