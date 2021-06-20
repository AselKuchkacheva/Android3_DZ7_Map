package com.example.android3_dz7_map;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class Prefs {

    private static final String MAPS_KEY = "latlng";
    public static final String PREFERENCES = "prefs";
    private SharedPreferences prefs;


    public Prefs(Context context) {
        prefs = context.getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE);
    }

    public void saveLocation(List<LatLng> savedList) {
        Gson gson = new Gson();
        String list = gson.toJson(savedList);
        prefs.edit().putString(MAPS_KEY, list).apply();
    }

    public List<LatLng> getLocation() {
        List<LatLng> list;

        String gsonStr = prefs.getString(MAPS_KEY, null);
        Type type = new TypeToken<List<LatLng>>() {
        }.getType();
        Gson gson = new Gson();
        list = gson.fromJson(gsonStr, type);
        if (list == null)
            list = new ArrayList<>();

        return list;
    }

    public void clearPrefs() {
        prefs.edit().clear().apply();
    }
}
