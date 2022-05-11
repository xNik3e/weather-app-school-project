package com.example.weatherapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.w3c.dom.Text;

public class CityListActivity extends AppCompatActivity {

    private MaterialToolbar toolbar;
    private FrameLayout iconContainer;
    private ImageView icBack;
    private RelativeLayout citiesLayout;
    private RecyclerView cities;
    private FloatingActionButton fab;
    private TextView hint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_list);

        toolbar = findViewById(R.id.toolbar);
        iconContainer = findViewById(R.id.toolbar_icon_container);
        icBack = findViewById(R.id.ic_back);
        citiesLayout = findViewById(R.id.cities_layout);
        cities = findViewById(R.id.cities);
        fab = findViewById(R.id.fab);
        hint = findViewById(R.id.no_city_view);

        iconContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CityListActivity.this, SearchActivity.class));
            }
        });
    }
}