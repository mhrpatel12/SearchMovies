package com.mihir.searchmovies;

import android.app.Application;
import android.content.Context;

import com.mihir.searchmovies.di.AppComponent;
import com.mihir.searchmovies.di.DaggerAppComponent;
import com.mihir.searchmovies.di.UtilsModule;


/**
 * Created by mihir on 26-06-2019.
 */

public class MyApplication extends Application {
    AppComponent appComponent;
    Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        appComponent = DaggerAppComponent.builder().utilsModule(new UtilsModule()).build();
    }

    public AppComponent getAppComponent() {
        return appComponent;
    }

    @Override
    protected void attachBaseContext(Context context) {
        super.attachBaseContext(context);
    }
}