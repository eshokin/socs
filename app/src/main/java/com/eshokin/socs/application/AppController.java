package com.eshokin.socs.application;

import android.app.Application;

import com.eshokin.socs.application.builder.AppComponent;
import com.eshokin.socs.application.builder.DaggerAppComponent;
import com.eshokin.socs.application.builder.modules.context.ContextModule;

public class AppController extends Application {

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
