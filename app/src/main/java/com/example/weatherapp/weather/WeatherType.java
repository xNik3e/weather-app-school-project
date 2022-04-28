package com.example.weatherapp.weather;

import com.example.weatherapp.R;

public enum WeatherType {
    SUNNY,
    CLEAR,
    CLOUDY,
    OVERCAST,
    SHOWERS,
    SNOW_SHOWERS,
    FOG,
    SANDSTORM,
    HAZE,
    THUNDERSTORMS,
    HAIL,
    SLEET_RAIN_WITH_SNOW,
    LIGHT_RAIN,
    RAIN,
    HEAVY_RAIN,
    RAINSTORM,
    FLURRIES,
    SNOW,
    TORNADO;

    public int getCityBackgroundWeatherResId(boolean isDay) {
        switch (this) {
            case CLOUDY:
                return isDay ? R.drawable.bkg_cloudy : R.drawable.bkg_cloudy_night;
            case OVERCAST:
                return isDay ? R.drawable.bkg_overcast : R.drawable.bkg_overcast_night;
            case SHOWERS:
                return isDay ? R.drawable.bkg_shower : R.drawable.bkg_shower_night;
            case SNOW_SHOWERS:
                return R.drawable.bkg_snowstorm;
            case FOG:
                return isDay ? R.drawable.bkg_fog : R.drawable.bkg_fog_night;
            case SANDSTORM:
                return isDay ? R.drawable.bkg_sandstorm : R.drawable.bkg_sandstorm_night;
            case HAZE:
                return isDay ? R.drawable.bkg_haze : R.drawable.bkg_haze_night;
            case THUNDERSTORMS:
                return isDay ? R.drawable.bkg_thundershower : R.drawable.bkg_thundershower_night;
            case HAIL:
                return R.drawable.bkg_hail;
            case SLEET_RAIN_WITH_SNOW:
                return isDay ? R.drawable.bkg_sleet : R.drawable.bkg_sleet_night;
            case LIGHT_RAIN:
                return isDay ? R.drawable.bkg_drizzle : R.drawable.bkg_drizzle_night;
            case RAIN:
                return isDay ? R.drawable.bkg_rain : R.drawable.bkg_rain_night;
            case HEAVY_RAIN:
                return isDay ? R.drawable.bkg_downpour : R.drawable.bkg_downpour_night;
            case RAINSTORM:
                return isDay ? R.drawable.bkg_rainstorm : R.drawable.bkg_rainstorm_night;
            case FLURRIES:
                return R.drawable.bkg_flurry;
            case SNOW:
                return isDay ? R.drawable.bkg_snow : R.drawable.bkg_snow_night;
            case TORNADO:
            case SUNNY:
            case CLEAR:
            default:
                return isDay ? R.drawable.bkg_sunny : R.drawable.bkg_sunny_night;
        }
    }
}
