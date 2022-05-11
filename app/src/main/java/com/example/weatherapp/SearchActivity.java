package com.example.weatherapp;

import static android.app.ProgressDialog.show;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Activity;
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
import android.os.StrictMode;
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

import com.example.weatherapp.city_list.CityWeatherModel;
import com.example.weatherapp.remote.apis.ApiParams;
import com.example.weatherapp.remote.model.GeoApiModel.GeoCodeFuzzy;
import com.example.weatherapp.remote.model.GeoApiModel.ResultsItem;
import com.example.weatherapp.remote.model.one_call_current_weather.OneCallWeatherResponse;
import com.example.weatherapp.remote.model.reverseGeoCode.ReverseGeoCodeResponseItem;
import com.example.weatherapp.search.CityModel;
import com.example.weatherapp.search.CitySearchAdapter;
import com.example.weatherapp.utils.viewmodels.CitySearchViewModel;
import com.example.weatherapp.utils.viewmodels.CityWeatherViewModel;
import com.example.weatherapp.utils.viewmodels.ReverseCitySearchViewModel;
import com.example.weatherapp.utils.view_services.ClearableEditText;
import com.example.weatherapp.utils.DialogOnClickListenerInterface;
import com.example.weatherapp.utils.location_services.LocationServices;
import com.example.weatherapp.utils.viewmodels.ViewModelFactory;
import com.example.weatherapp.utils.viewmodels.WeatherSearchViewModel;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity implements TextWatcher, LocationServices.LocationServicesListener, CitySearchAdapter.SearchInterface {

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

    //TODO update it later

    private OneCallWeatherResponse oneCall; //do not use anywhere other than in getWeather()
    private CityWeatherModel cityWeatherModel; //oneCall + cityModel got from get weather (either by clicking on the locate button, or item in RV)
    private CityModel cityModel; //got from locating the city using either gps or network
    private List<CityWeatherModel> finalModels = new ArrayList<>(); //retrieve and update if needed

    //ViewModels
    private CitySearchViewModel citySearchViewModel;
    private WeatherSearchViewModel weatherSearchViewModel;
    private ReverseCitySearchViewModel reverseCitySearchViewModel;
    private CityWeatherViewModel cityWeatherViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        if (android.os.Build.VERSION.SDK_INT > 9)
        {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        citySearchViewModel = new ViewModelProvider(this, new ViewModelFactory()).get(CitySearchViewModel.class);
        reverseCitySearchViewModel = new ViewModelProvider(this, new ViewModelFactory()).get(ReverseCitySearchViewModel.class);
        weatherSearchViewModel = new ViewModelProvider(this, new ViewModelFactory()).get(WeatherSearchViewModel.class);

        cityWeatherViewModel = new ViewModelProvider(this, new ViewModelFactory()).get(CityWeatherViewModel.class);

        cityWeatherViewModel.init(this);

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
                if (!hasFocus)
                    hideKeyboard(v);
            }
        });

        if (!isLocated) {
            locateTextUtil();
        } else {
            //do nothing
        }

        textButton.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (isLocated) {
                    locateTextUtil();
                } else {
                    //nothing
                }
                return true;
            }
        });
    }

    private void locateTextUtil() {
        text.setText("Locating...");
        Location location = locationServices.getLocation(this);
        if (location != null) {
            locate(location.getLatitude(), location.getLongitude());
            Log.d("LOC", location.toString());
        } else {
            text.setText(getResources().getString(R.string.locate_error));
        }
    }

    @Override
    public void onLocationChangeResult(Location location) {
        locate(location.getLatitude(), location.getLongitude());
    }

    @Override
    public void search(CityModel model) {
        getWeather(model);
        updateList(false);
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
                if (!isLocated)
                    locateTextUtil();
            }
            if ("android.location.PROVIDERS_CHANGED".equals(intent.getAction())) {
                if (!isLocated)
                    locateTextUtil();
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
                        CityModel tempCityModel = new CityModel();
                        tempCityModel.setFreeFormAddress(r.getAddress().getFreeformAddress());
                        tempCityModel.setCountry(r.getAddress().getCountry());
                        tempCityModel.setCountrySubdivision(r.getAddress().getCountrySubdivision());
                        tempCityModel.setLat(r.getPosition().getLat());
                        tempCityModel.setLon(r.getPosition().getLon());
                        tempCityModel.setAdded(false);

                        models.add(tempCityModel);
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
                if (reverseGeoCodeResponse != null && !reverseGeoCodeResponse.isEmpty()) {
                    ReverseGeoCodeResponseItem item = reverseGeoCodeResponse.get(0);
                    text.setText(item.getName());
                    isLocated = true;
                    cityModel = new CityModel();
                    cityModel.setAdded(true);
                    cityModel.setCountry(item.getCountry());
                    cityModel.setLon(item.getLon());
                    cityModel.setLat(item.getLat());
                    cityModel.setFreeFormAddress(item.getName());
                    cityModel.setCountrySubdivision(item.getState());
                    cityModel.setLocated(isLocated);
                }
            }
        });
    }

    private void getWeather(CityModel model) {
        OneCallWeatherResponse response = weatherSearchViewModel.getWeatherData(apiParams.getWeatherParams(model.getLat(), model.getLon()));
        if(response != null){
            this.oneCall = response;
            this.cityWeatherModel = new CityWeatherModel(model, this.oneCall);
        }

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
        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(activity);
        builder.setMessage(R.string.unable_to_locate_city).setCancelable(false)
                .setPositiveButton(R.string.dialog_go_to_settings, dListener)
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        AlertDialog dialog = builder.create();
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }

    //Button function
    public void myFun(View view) {
        if (isLocated) {
            getWeather(this.cityModel); //got cityModel from locateTextUtil and now cityWeatherModel
            updateList(true);
        } else
            locateTextUtil();
    }

    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }


    private void updateList(boolean updateLocatedModel) {
        LiveData<List<CityWeatherModel>> liveModels = cityWeatherViewModel.getCityWeatherModels();
        List<CityWeatherModel> tempLiveModels = liveModels.getValue();
        //null and empty check
        cityWeatherModel.setLastUpdate(System.currentTimeMillis()/1000);
        if (tempLiveModels != null && tempLiveModels.isEmpty())
            cityWeatherViewModel.addNewCity(this.cityWeatherModel);
        else {
            if (updateLocatedModel) {
                for (CityWeatherModel m : tempLiveModels) {
                    if (m.getCityModel().isLocated()) {
                        List<CityWeatherModel> tempList = liveModels.getValue();
                        CityWeatherModel tempModel = this.cityWeatherModel;
                        tempList.set(tempList.indexOf(m), tempModel);
                        finalModels = tempList;
                        break;
                    }
                }
                if (!finalModels.isEmpty())
                    cityWeatherViewModel.replaceCityData(this.finalModels);
                else {
                    cityWeatherViewModel.addNewCity(this.cityWeatherModel);
                }
            } else {
                cityWeatherViewModel.addNewCity(this.cityWeatherModel);
            }
        }
        cityWeatherViewModel.getCityWeatherModels().observe(this, new Observer<List<CityWeatherModel>>() {
            @Override
            public void onChanged(List<CityWeatherModel> modelList) {
                cityWeatherViewModel.saveCityData(getBaseContext(), modelList);
                cityWeatherViewModel.getIsUpdating().observe(SearchActivity.this, new Observer<Boolean>() {
                    @Override
                    public void onChanged(Boolean aBoolean) {
                        if(aBoolean){
                            //maybe add progressbar
                        }else{
                            goToActivity();
                        }
                    }
                });

            }
        });

    }

    private void goToActivity() {
        Intent intent = new Intent(SearchActivity.this, CityListActivity.class);
        startActivity(intent);
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