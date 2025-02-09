package com.example.challengefragmentrecycleview;

import android.app.Application;

import java.util.ArrayList;

public class ApplicationClass extends Application {
    public static ArrayList<Car> cars;

    @Override
    public void onCreate() {
        super.onCreate();
        cars = new ArrayList<Car>();
        cars.add(new Car("Volkswagen","Polo", "Chuck Norris", "1234567890"));
        cars.add(new Car("Mercedes","E200", "Bill Gates", "23873873733"));
        cars.add(new Car("Nissan","Micra", "Lando Norris", "2738172637"));
    }
}
