package com.example.weatherapp.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.example.weatherapp.R;

public class WeatherSingleInfoView extends LinearLayout {

    public static final int[] WeatherSingleInfoViewAttr = {R.attr.weatherIcon, R.attr.weatherLevel, R.attr.weatherType};
    private View view;
    private TextView infoType;
    private TextView infoLevel;
    private TextView infoUnit;


    public WeatherSingleInfoView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WeatherSingleInfoView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        inflateView(context, attrs);
    }

    public WeatherSingleInfoView setInfoLevel(String str){
        this.infoLevel.setText(str);
        return this;
    }
    public WeatherSingleInfoView setInfoType(String str){
        this.infoType.setText(str);
        return this;
    }
    public WeatherSingleInfoView setInfoUnit(String str){
        this.infoUnit.setText(str);
        return this;
    }
    public void inflateView(Context context, AttributeSet attributeSet){
        TypedArray obtainStyledAttributes = getContext().obtainStyledAttributes(attributeSet, WeatherSingleInfoViewAttr);
        @SuppressLint("ResourceType") String string = obtainStyledAttributes.getString(1);
        @SuppressLint("ResourceType") String string1 = obtainStyledAttributes.getString(2);
        this.view = LayoutInflater.from(context).inflate(R.layout.weather_single_info_element, this);
        this.infoType = (TextView) this.view.findViewById(R.id.single_info_type);
        this.infoLevel = (TextView) this.view.findViewById(R.id.single_info_level);
        this.infoUnit = (TextView) this.view.findViewById(R.id.single_info_unit);
        this.infoType.setText(string1);
        this.infoType.setTextColor(getContext().getColor(R.color.text_secondary_dark));
        this.infoType.setTextAppearance(R.style.control_text_body1);
        this.infoLevel.setText(string);
        this.infoLevel.setTextColor(getContext().getColor(R.color.text_primary_dark));
        this.infoLevel.setTextAppearance(R.style.control_text_under_display);
        obtainStyledAttributes.recycle();


    }

}
