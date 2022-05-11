package com.example.weatherapp.utils;

import com.example.weatherapp.weather.WeatherEnum;

public class EnumHelper {
    public EnumHelper() {
    }
    public static WeatherEnum getResourcesByWeatherId(int weatherId) {
        WeatherEnum temp;
        switch (weatherId) {
            case 200:
                temp = WeatherEnum.THUNDERSTORM200;
                break;
            case 201:
                temp = WeatherEnum.THUNDERSTORM201;
                break;
            case 202:
                temp = WeatherEnum.THUNDERSTORM202;
                break;
            case 210:
                temp = WeatherEnum.THUNDERSTORM210;
                break;
            case 211:
                temp = WeatherEnum.THUNDERSTORM211;
                break;
            case 212:
                temp = WeatherEnum.THUNDERSTORM212;
                break;
            case 221:
                temp = WeatherEnum.THUNDERSTORM221;
                break;
            case 230:
                temp = WeatherEnum.THUNDERSTORM230;
                break;
            case 231:
                temp = WeatherEnum.THUNDERSTORM231;
                break;
            case 232:
                temp = WeatherEnum.THUNDERSTORM232;
                break;
            case 300:
                temp = WeatherEnum.DRIZZLE300;
                break;
            case 301:
                temp = WeatherEnum.DRIZZLE301;
                break;
            case 302:
                temp = WeatherEnum.DRIZZLE302;
                break;
            case 310:
                temp = WeatherEnum.DRIZZLE310;
                break;
            case 311:
                temp = WeatherEnum.DRIZZLE311;
                break;
            case 312:
                temp = WeatherEnum.DRIZZLE312;
                break;
            case 313:
                temp = WeatherEnum.DRIZZLE313;
                break;
            case 314:
                temp = WeatherEnum.DRIZZLE314;
                break;
            case 321:
                temp = WeatherEnum.DRIZZLE321;
                break;
            case 500:
                temp = WeatherEnum.RAIN500;
                break;
            case 501:
                temp = WeatherEnum.RAIN501;
                break;
            case 502:
                temp = WeatherEnum.RAIN502;
                break;
            case 503:
                temp = WeatherEnum.RAIN503;
                break;
            case 504:
                temp = WeatherEnum.RAIN504;
                break;
            case 511:
                temp = WeatherEnum.RAIN511;
                break;
            case 520:
                temp = WeatherEnum.RAIN520;
                break;
            case 521:
                temp = WeatherEnum.RAIN521;
                break;
            case 522:
                temp = WeatherEnum.RAIN522;
                break;
            case 531:
                temp = WeatherEnum.RAIN531;
                break;
            case 600:
                temp = WeatherEnum.SNOW600;
                break;
            case 601:
                temp = WeatherEnum.SNOW601;
                break;
            case 602:
                temp = WeatherEnum.SNOW602;
                break;
            case 611:
                temp = WeatherEnum.SNOW611;
                break;
            case 612:
                temp = WeatherEnum.SNOW612;
                break;
            case 613:
                temp = WeatherEnum.SNOW613;
                break;
            case 615:
                temp = WeatherEnum.SNOW615;
                break;
            case 616:
                temp = WeatherEnum.SNOW616;
                break;
            case 620:
                temp = WeatherEnum.SNOW620;
                break;
            case 621:
                temp = WeatherEnum.SNOW621;
                break;
            case 622:
                temp = WeatherEnum.SNOW622;
                break;
            case 701:
                temp = WeatherEnum.MIST701;
                break;
            case 711:
                temp = WeatherEnum.SMOKE711;
                break;
            case 721:
                temp = WeatherEnum.HAZE721;
                break;
            case 731:
                temp = WeatherEnum.DUST731;
                break;
            case 741:
                temp = WeatherEnum.FOG741;
                break;
            case 751:
                temp = WeatherEnum.SAND751;
                break;
            case 761:
                temp = WeatherEnum.DUST761;
                break;
            case 762:
                temp = WeatherEnum.ASH762;
                break;
            case 771:
                temp = WeatherEnum.SQUALL771;
                break;
            case 781:
                temp = WeatherEnum.TORNADO781;
                break;
            case 800:
                temp = WeatherEnum.CLEAR800;
                break;
            case 801:
                temp = WeatherEnum.CLOUDS801;
                break;
            case 802:
                temp = WeatherEnum.CLOUDS802;
                break;
            case 803:
                temp = WeatherEnum.CLOUDS803;
                break;
            case 804:
                temp = WeatherEnum.CLOUDS804;
                break;
            default:
                temp = WeatherEnum.UNKNOWN;
                break;
        }
        return temp;
    }
}
