package com.eshokin.socs.application;

import android.support.multidex.MultiDexApplication;

import com.eshokin.socs.application.di.AppComponent;
import com.eshokin.socs.application.di.DaggerAppComponent;
import com.eshokin.socs.application.di.modules.context.ContextModule;

public class AppController extends MultiDexApplication {

    private static AppComponent component;

    @Override
    public void onCreate() {
        super.onCreate();

        initAppComponent();
    }

    public static AppComponent getComponent() {
        return component;
    }

    private void initAppComponent() {
        component = DaggerAppComponent.builder().contextModule(new ContextModule(this)).build();
    }
}
