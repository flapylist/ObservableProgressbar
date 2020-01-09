package com.example.observableprogressbar;

import android.app.Application;

import io.reactivex.plugins.RxJavaPlugins;

public class App extends Application {

    @Override
    public void onCreate(){
        super.onCreate();
        RxJavaPlugins.setErrorHandler(throwable -> {});
    }
}
