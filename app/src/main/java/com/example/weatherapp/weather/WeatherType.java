package com.example.weatherapp.weather;

import com.example.weatherapp.R;

public enum WeatherType {
    CLEAR,
    CLOUDY,
    OVERCAST,
    SHOWERS,
    FOG,
    SANDSTORM,
    HAZE,
    SLEET_RAIN_WITH_SNOW,
    LIGHT_RAIN,
    RAIN,
    HEAVY_RAIN,
    RAINSTORM,
    SNOW;

    public int getCityBackgroundWeatherResId(boolean isDay) {
        switch (this) {
            case CLOUDY:
                return isDay ? R.drawable.bkg_cloudy : R.drawable.bkg_cloudy_night;
            case OVERCAST:
                return isDay ? R.drawable.bkg_overcast : R.drawable.bkg_overcast_night;
            case SHOWERS:
                return isDay ? R.drawable.bkg_shower : R.drawable.bkg_shower_night;
            case FOG:
                return isDay ? R.drawable.bkg_fog : R.drawable.bkg_fog_night;
            case SANDSTORM:
                return isDay ? R.drawable.bkg_sandstorm : R.drawable.bkg_sandstorm_night;
            case HAZE:
                return isDay ? R.drawable.bkg_haze : R.drawable.bkg_haze_night;
            case SLEET_RAIN_WITH_SNOW:
                return isDay ? R.drawable.bkg_sleet : R.drawable.bkg_cloudy_night;
            case LIGHT_RAIN:
                return isDay ? R.drawable.bkg_drizzle : R.drawable.bkg_drizzle_night;
            case RAIN:
                return isDay ? R.drawable.bkg_rain : R.drawable.bkg_rain_night;
            case HEAVY_RAIN:
                return isDay ? R.drawable.bkg_downpour : R.drawable.bkg_downpour_night;
            case RAINSTORM:
                return isDay ? R.drawable.bkg_rainstorm : R.drawable.bkg_rainstorm_night;
            case SNOW:
                return isDay ? R.drawable.bkg_snow : R.drawable.bkg_snow_night;
            case CLEAR:
            default:
                return isDay ? R.drawable.bkg_sunny : R.drawable.bkg_sunny_night;
        }
    }

    public int getWeatherColorResId(boolean isDay) {
        switch (this) {
            case CLOUDY:
            case CLEAR:
                return isDay ? R.color.weather_sun : R.color.weather_sun_night;
            case OVERCAST:
                return isDay ? R.color.weather_overcast : R.color.weather_overcast_night;
            case SHOWERS:
                return isDay ? R.color.weather_shower_rain : R.color.weather_shower_rain_night;
            case FOG:
                return isDay ? R.color.weather_fog : R.color.weather_fog_night;
            case SANDSTORM:
                return isDay ? R.color.weather_dust : R.color.weather_dust_night;
            case HAZE:
                return isDay ? R.color.weather_haze : R.color.weather_haze_night;
            case SLEET_RAIN_WITH_SNOW:
            case SNOW:
                return isDay ? R.color.weather_snow : R.color.weather_snow_night;
            case LIGHT_RAIN:
                return isDay ? R.color.weather_drizzle_rain : R.color.weather_drizzle_rain_night;
            case RAIN:
                return isDay ? R.color.weather_rain : R.color.weather_rain_night;
            case HEAVY_RAIN:
                return isDay ? R.color.weather_downpour_rain : R.color.weather_downpour_rain_night;
            case RAINSTORM:
                return isDay ? R.color.weather_storm_rain : R.color.weather_shower_rain;
            default:
                throw new IllegalStateException("Unexpected value: " + this);
        }
    }




}
