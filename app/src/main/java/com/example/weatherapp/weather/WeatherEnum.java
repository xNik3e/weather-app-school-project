package com.example.weatherapp.weather;

import com.example.weatherapp.R;

public enum WeatherEnum {
    THUNDERSTORM200(R.string.enum_weather_type_thunderstorm_200, WeatherType.RAINSTORM),
    THUNDERSTORM201(R.string.enum_weather_type_thunderstorm_201, WeatherType.RAINSTORM),
    THUNDERSTORM202(R.string.enum_weather_type_thunderstorm_202, WeatherType.RAINSTORM),
    THUNDERSTORM210(R.string.enum_weather_type_thunderstorm_210, WeatherType.RAINSTORM),
    THUNDERSTORM211(R.string.enum_weather_type_thunderstorm_211, WeatherType.RAINSTORM),
    THUNDERSTORM212(R.string.enum_weather_type_thunderstorm_212, WeatherType.RAINSTORM),
    THUNDERSTORM221(R.string.enum_weather_type_thunderstorm_221, WeatherType.RAINSTORM),
    THUNDERSTORM230(R.string.enum_weather_type_thunderstorm_230, WeatherType.RAINSTORM),
    THUNDERSTORM231(R.string.enum_weather_type_thunderstorm_231, WeatherType.RAINSTORM),
    THUNDERSTORM232(R.string.enum_weather_type_thunderstorm_232, WeatherType.RAINSTORM),

    DRIZZLE300(R.string.enum_weather_type_drizzle_300, WeatherType.LIGHT_RAIN),
    DRIZZLE301(R.string.enum_weather_type_drizzle_301, WeatherType.LIGHT_RAIN),
    DRIZZLE302(R.string.enum_weather_type_drizzle_302, WeatherType.RAIN),
    DRIZZLE310(R.string.enum_weather_type_drizzle_310, WeatherType.RAIN),
    DRIZZLE311(R.string.enum_weather_type_drizzle_311, WeatherType.RAIN),
    DRIZZLE312(R.string.enum_weather_type_drizzle_312, WeatherType.RAIN),
    DRIZZLE313(R.string.enum_weather_type_drizzle_313, WeatherType.RAIN),
    DRIZZLE314(R.string.enum_weather_type_drizzle_314, WeatherType.HEAVY_RAIN),
    DRIZZLE321(R.string.enum_weather_type_drizzle_321, WeatherType.SHOWERS),

    RAIN500(R.string.enum_weather_type_rain_500, WeatherType.RAIN),
    RAIN501(R.string.enum_weather_type_rain_501, WeatherType.RAIN),
    RAIN502(R.string.enum_weather_type_rain_502, WeatherType.HEAVY_RAIN),
    RAIN503(R.string.enum_weather_type_rain_503, WeatherType.HEAVY_RAIN),
    RAIN504(R.string.enum_weather_type_rain_504, WeatherType.HEAVY_RAIN),
    RAIN511(R.string.enum_weather_type_rain_511, WeatherType.SLEET_RAIN_WITH_SNOW),
    RAIN520(R.string.enum_weather_type_rain_520, WeatherType.SHOWERS),
    RAIN521(R.string.enum_weather_type_rain_521, WeatherType.SHOWERS),
    RAIN522(R.string.enum_weather_type_rain_522, WeatherType.SHOWERS),
    RAIN531(R.string.enum_weather_type_rain_531, WeatherType.SHOWERS),

    SNOW600(R.string.enum_weather_type_snow_600, WeatherType.SNOW),
    SNOW601(R.string.enum_weather_type_snow_601, WeatherType.SNOW),
    SNOW602(R.string.enum_weather_type_snow_602, WeatherType.SNOW),
    SNOW611(R.string.enum_weather_type_snow_611, WeatherType.SLEET_RAIN_WITH_SNOW),
    SNOW612(R.string.enum_weather_type_snow_612, WeatherType.SLEET_RAIN_WITH_SNOW),
    SNOW613(R.string.enum_weather_type_snow_613, WeatherType.SLEET_RAIN_WITH_SNOW),
    SNOW615(R.string.enum_weather_type_snow_615, WeatherType.SLEET_RAIN_WITH_SNOW),
    SNOW616(R.string.enum_weather_type_snow_616, WeatherType.SLEET_RAIN_WITH_SNOW),
    SNOW620(R.string.enum_weather_type_snow_620, WeatherType.SNOW),
    SNOW621(R.string.enum_weather_type_snow_621, WeatherType.SNOW),
    SNOW622(R.string.enum_weather_type_snow_622, WeatherType.SNOW),

    MIST701(R.string.enum_weather_type_mist_701, WeatherType.HAZE),
    SMOKE711(R.string.enum_weather_type_smoke_711, WeatherType.CLEAR),
    HAZE721(R.string.enum_weather_type_haze_721, WeatherType.HAZE),
    DUST731(R.string.enum_weather_type_dust_731, WeatherType.SANDSTORM),
    FOG741(R.string.enum_weather_type_fog_741, WeatherType.FOG),
    SAND751(R.string.enum_weather_type_sand_751, WeatherType.SANDSTORM),
    DUST761(R.string.enum_weather_type_dust_761, WeatherType.SANDSTORM),
    ASH762(R.string.enum_weather_type_ash_762, WeatherType.SANDSTORM),
    SQUALL771(R.string.enum_weather_type_squall_771, WeatherType.CLEAR),
    TORNADO781(R.string.enum_weather_type_tornado_781, WeatherType.CLEAR),

    CLEAR800(R.string.enum_weather_type_clear_800, WeatherType.CLEAR),
    CLOUDS801(R.string.enum_weather_type_clouds_801, WeatherType.CLOUDY),
    CLOUDS802(R.string.enum_weather_type_clouds_802, WeatherType.CLOUDY),
    CLOUDS803(R.string.enum_weather_type_clouds_803, WeatherType.CLOUDY),
    CLOUDS804(R.string.enum_weather_type_clouds_804, WeatherType.OVERCAST),

    UNKNOWN(R.string.enum_weather_type_clear_800, WeatherType.CLEAR);

    private final int mDescriptionID;
    private final WeatherType mTypes;

    private WeatherEnum(int i, WeatherType type) {
        this.mDescriptionID = i;
        this.mTypes = type;
    }

    public int getCityBackgroundWeatherResId(boolean isDay) {
        return this.mTypes.getCityBackgroundWeatherResId(isDay);
    }

    public int getDescriptionID() {
        return this.mDescriptionID;
    }

    public WeatherType getTypes() {
        return this.mTypes;
    }
}
