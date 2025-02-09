package com.example.mapapp;

import android.app.Application;

import com.backendless.Backendless;
import com.backendless.BackendlessUser;

import java.util.List;

public class ApplicationClass extends Application {
    public static final String APPLICATION_ID = "86C5A1E7-3217-472A-B793-22C6A0DB8FF4";
    public static final String API_KEY = "FA003CFF-F7AD-4F7C-974F-336669D92B9B"; // ANDROID API Key
    public static final String SERVER_URL = "https://api.backendless.com"; // Use region-specific URL



    @Override
    public void onCreate() {
        super.onCreate();

        Backendless.setUrl( SERVER_URL );
        Backendless.initApp( getApplicationContext(),
                APPLICATION_ID,
                API_KEY );
    }
}
