package com.example.weatherapp;

import static android.app.ProgressDialog.show;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.weatherapp.remote.model.ApiParams;
import com.example.weatherapp.remote.model.GeoApiModel.GeoCodeFuzzy;
import com.example.weatherapp.remote.model.GeoApiModel.ResultsItem;
import com.example.weatherapp.remote.model.reverseGeoCode.ReverseGeoCodeResponse;
import com.example.weatherapp.remote.model.reverseGeoCode.ReverseGeoCodeResponseItem;
import com.example.weatherapp.search.CityModel;
import com.example.weatherapp.search.CitySearchAdapter;
import com.example.weatherapp.search.CitySearchViewModel;
import com.example.weatherapp.search.ReverseCitySearchViewModel;
import com.example.weatherapp.utils.ClearableEditText;
import com.example.weatherapp.utils.DialogOnClickListenerInterface;
import com.example.weatherapp.utils.LocationServices;
import com.example.weatherapp.utils.ViewModelFactory;
import com.google.android.material.appbar.MaterialToolbar;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity implements TextWatcher, LocationServices.LocationServicesListener {

    private MaterialToolbar toolbar;
    private FrameLayout iconContainer;
    private ImageView icBack;
    private ClearableEditText cEditText;
    private LinearLayout locatedCityContent;
    private TextView locatedCityTitle;
    private RelativeLayout relativeLayout;
    private RecyclerView searchedCities;
    private TextView hint;
    private View locatedCityLayout;
    private List<CityModel> models;
    private CitySearchAdapter adapter;
    private CitySearchViewModel citySearchViewModel;
    private ReverseCitySearchViewModel reverseCitySearchViewModel;
    private boolean isLocated = false;
    private LocationServices locationServices;
    private FrameLayout textButton;


    private View locatedDisplayLayout;
    private TextView text;

    private final ApiParams apiParams = new ApiParams();

    private boolean isConnected = true;

    private InputMethodManager inputMethodManager;
    private ConnectivityManager connectivityManager;
    private BroadcastReceiver broadcastReceiver = new MyConnectivityReceiver();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        citySearchViewModel = new ViewModelProvider(this, new ViewModelFactory()).get(CitySearchViewModel.class);
        reverseCitySearchViewModel = new ViewModelProvider(this, new ViewModelFactory()).get(ReverseCitySearchViewModel.class);

        toolbar = findViewById(R.id.toolbar);
        iconContainer = findViewById(R.id.toolbar_icon_container);
        icBack = findViewById(R.id.ic_back);
        cEditText = findViewById(R.id.search_bar_input_field);
        locatedCityContent = findViewById(R.id.located_city_content);
        locatedCityTitle = findViewById(R.id.located_city_title);
        relativeLayout = findViewById(R.id.relative_layout);
        searchedCities = findViewById(R.id.searched_cities);
        hint = findViewById(R.id.hint_text_view);
        locationServices = new LocationServices(this);

        locatedDisplayLayout = findViewById(R.id.located_city_layout);
        text = (TextView) locatedDisplayLayout.findViewById(R.id.text);
        textButton = (FrameLayout) locatedDisplayLayout.findViewById(R.id.text_button);

        inputMethodManager = getSystemService(InputMethodManager.class);
        connectivityManager = getSystemService(ConnectivityManager.class);
        registerReceiver();

        models = new ArrayList<>();
        adapter = new CitySearchAdapter(this, models);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        searchedCities.setLayoutManager(linearLayoutManager);
        searchedCities.setAdapter(adapter);
        iconContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchActivity.super.onBackPressed();
            }
        });

        cEditText.addTextChangedListener(this);
        if (cEditText.requestFocus()) {
            this.inputMethodManager.showSoftInput(this.cEditText, 1);
        }

        cEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus)
                    hideKeyboard(v);
            }
        });

        if (!isLocated) {
            text.setText("Locating...");
            Location location = locationServices.getLocation(this);
            if (location != null) {
                locate(location.getLatitude(), location.getLongitude());
                Log.d("LOC", location.toString());
            } else {
                text.setText("error");
            }

        } else {
            //Show recent location
        }


    }

    @Override
    public void onLocationChangeResult(Location location) {
        locate(location.getLatitude(), location.getLongitude());
    }



    class MyConnectivityReceiver extends BroadcastReceiver {
        MyConnectivityReceiver() {
        }

        @Override
        public void onReceive(Context context, Intent intent) {
            TextView hint;
            int i;
            if ("android.net.conn.CONNECTIVITY_CHANGE".equals(intent.getAction())) {
                NetworkInfo activeNetworkInfo = SearchActivity.this.connectivityManager.getActiveNetworkInfo();
                if (activeNetworkInfo == null || !activeNetworkInfo.isAvailable()) {
                    SearchActivity.this.hint.setText("No network connection");
                    hint = SearchActivity.this.hint;
                    i = R.drawable.no_network;
                    isConnected = false;
                } else {
                    SearchActivity.this.hint.setText("No matches");
                    hint = SearchActivity.this.hint;
                    i = R.drawable.ic_search;
                    isConnected = true;
                }
                hint.setCompoundDrawablesWithIntrinsicBounds(null, context.getDrawable(i), null, null);
            }
            if ("android.location.PROVIDERS_CHANGED".equals(intent.getAction())) {
                if (!isLocated) {
                    text.setText("Locating...");
                    Location location = locationServices.getLocation(context);
                    if (location != null) {
                        locate(location.getLatitude(), location.getLongitude());
                    } else {
                        text.setText("Error");
                    }
                }
            }

        }
    }


    private void finishActivity() {
        finish();
        locationServices.stopUsingGPS();
        overridePendingTransition(R.anim.alpha_in, R.anim.translate_down);
        this.inputMethodManager.hideSoftInputFromWindow(this.cEditText.getWindowToken(), 0);
    }

    private void registerReceiver() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        intentFilter.addAction("android.location.PROVIDERS_CHANGED");
        registerReceiver(this.broadcastReceiver, intentFilter);
    }

    private void unregisterReceiver() {
        BroadcastReceiver broadcastReceiver = this.broadcastReceiver;
        if (broadcastReceiver != null) {
            unregisterReceiver(broadcastReceiver);
            this.broadcastReceiver = null;
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (isConnected) {
            if (s.length() >= 2) {
                setEverythingHidden(true);
                search(s.toString());
            } else {
                if (s.length() == 1) {
                    locatedCityTitle.setVisibility(View.GONE);
                    locatedCityContent.setVisibility(View.GONE);
                } else
                    setEverythingVisible(true);

                hint.setText(getResources().getString(R.string.search_something));
                hint.setCompoundDrawablesWithIntrinsicBounds(null, getResources().getDrawable(R.drawable.ic_search), null, null);
                models.clear();
                adapter.notifyDataSetChanged();
            }
        } else {
            if (s.length() >= 2) {
                setEverythingHidden(true);
            } else {
                if (s.length() == 1) {
                    locatedCityTitle.setVisibility(View.GONE);
                    locatedCityContent.setVisibility(View.GONE);
                } else
                    setEverythingVisible(true);

            }
            hint.setText("No network connection");
            hint.setCompoundDrawablesWithIntrinsicBounds(null, getResources().getDrawable(R.drawable.no_network), null, null);
            Toast.makeText(SearchActivity.this, "Please connect to the Network", Toast.LENGTH_SHORT).show();
        }

    }

    private void search(String query) {
        citySearchViewModel.getGeoLocation(query, apiParams.getGeoParams()).observe(SearchActivity.this, new Observer<GeoCodeFuzzy>() {
            @SuppressLint("UseCompatLoadingForDrawables")
            @Override
            public void onChanged(GeoCodeFuzzy geoCodeFuzzy) {
                if (geoCodeFuzzy.getResults() != null && !geoCodeFuzzy.getResults().isEmpty()) {
                    setEverythingHidden(true);
                    models.clear();
                    for (ResultsItem r : geoCodeFuzzy.getResults()) {
                        CityModel cityModel = new CityModel();
                        cityModel.setAdded(false);
                        cityModel.setFreeFormAddress(r.getAddress().getFreeformAddress());
                        cityModel.setCountry(r.getAddress().getCountry());
                        cityModel.setCountrySubdivision(r.getAddress().getCountrySubdivision());
                        cityModel.setLat(r.getPosition().getLat());
                        cityModel.setLon(r.getPosition().getLon());
                        models.add(cityModel);
                    }
                    adapter.notifyDataSetChanged();
                } else {
                    setEverythingVisible(false);
                    hint.setText(getResources().getString(R.string.no_matches));
                    hint.setCompoundDrawablesWithIntrinsicBounds(null, SearchActivity.this.getResources().getDrawable(R.drawable.no_search_icon), null, null);
                }
            }
        });
    }
    private void locate(double latitude, double longitude) {
        reverseCitySearchViewModel.getReverseGeoCode(apiParams.getLocParams(latitude, longitude)).observe(SearchActivity.this, new Observer<List<ReverseGeoCodeResponseItem>>() {
            @Override
            public void onChanged(List<ReverseGeoCodeResponseItem> reverseGeoCodeResponse) {
                if(reverseGeoCodeResponse != null && !reverseGeoCodeResponse.isEmpty()){
                    ReverseGeoCodeResponseItem item = reverseGeoCodeResponse.get(0);
                    text.setText(item.getName());
                }
            }
        });
    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    private void setEverythingHidden(boolean isLocated) {
        hint.setVisibility(View.GONE);
        if (isLocated) {
            locatedCityTitle.setVisibility(View.GONE);
            locatedCityContent.setVisibility(View.GONE);
        }
        relativeLayout.setVisibility(View.VISIBLE);
    }

    private void setEverythingVisible(boolean isLocated) {
        hint.setVisibility(View.VISIBLE);
        if (isLocated) {
            locatedCityTitle.setVisibility(View.VISIBLE);
            locatedCityContent.setVisibility(View.VISIBLE);
        }
        relativeLayout.setVisibility(View.GONE);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishActivity();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        locationServices.stopUsingGPS();
        unregisterReceiver();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 123) {
            for (int i = 0; i < permissions.length; i++) {
                if (permissions[i].equals("android.permission.ACCESS_FINE_LOCATION")) {
                    if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                        //
                    } else {
                        if (!shouldRequestPermission(this, "android.permission.ACCESS_FINE_LOCATION")) {
                            createDialog(this);
                        }
                    }
                }
            }
        }
    }

    public static boolean shouldRequestPermission(Activity activity, String... strArr) {
        boolean z = false;
        for (String shouldShowRequestPermissionRationale : strArr) {
            z |= activity.shouldShowRequestPermissionRationale(shouldShowRequestPermissionRationale);
        }
        return z;
    }

    public static void createDialog(Activity activity) {
        DialogOnClickListenerInterface dListener = new DialogOnClickListenerInterface(activity);
        AlertDialog create = new AlertDialog.Builder(activity).setMessage(R.string.unable_to_locate_city).
                setPositiveButton(R.string.dialog_go_to_settings, dListener).
                setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).create();
        create.setCanceledOnTouchOutside(false);
        create.show();
    }

    public void myFun(View view) {
        Toast.makeText(SearchActivity.this, "Button clicked", Toast.LENGTH_SHORT).show();
        text.setText("Locating...");
        Location location = locationServices.getLocation(this);
        if (location != null) {
            locate(location.getLatitude(), location.getLongitude());
            Log.d("LOC", location.toString());
        } else {
            text.setText("error");
        }
    }

    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager =(InputMethodManager)getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (getCurrentFocus() != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
        return super.dispatchTouchEvent(ev);
    }
}