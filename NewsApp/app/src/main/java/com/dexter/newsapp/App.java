package com.dexter.newsapp;

import android.app.Application;
import android.content.Context;
import com.dexter.newsapp.di.component.AppComponent;
import com.dexter.newsapp.di.component.DaggerAppComponent;
import com.dexter.newsapp.di.module.AppModule;

public class App extends Application {
    AppComponent appComponent;
    Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        appComponent = DaggerAppComponent.builder().appModule(new AppModule(this)).build();
    }

    public AppComponent getAppComponent() {
        return appComponent;
    }

    @Override
    protected void attachBaseContext(Context context) {
        super.attachBaseContext(context);
    }
}
