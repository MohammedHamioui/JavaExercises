package com.example.contacts;

import android.app.Application;

import com.backendless.Backendless;
import com.backendless.BackendlessUser;

import java.util.List;

public class ApplicationClass extends Application {
    public static final String APPLICATION_ID = "418667D7-997F-4912-BC00-7CCFFC43B8B6";
    public static final String API_KEY = "2F76BB6B-EC1F-464D-A2D5-C4A40AFF630D"; // ANDROID API Key
    public static final String SERVER_URL = "https://api.backendless.com"; // Use region-specific URL

    public static BackendlessUser user;
    public static List<Contact> contacts;



    @Override
    public void onCreate() {
        super.onCreate();

        Backendless.setUrl( SERVER_URL );
        Backendless.initApp( getApplicationContext(),
                APPLICATION_ID,
                API_KEY );
    }
}
