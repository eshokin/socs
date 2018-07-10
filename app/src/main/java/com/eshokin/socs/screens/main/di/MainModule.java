package com.eshokin.socs.screens.main.di;

import android.content.Context;
import android.support.annotation.NonNull;

import com.eshokin.socs.application.di.AppScope;

import dagger.Module;
import dagger.Provides;

@Module
public class MainModule {

    private Context mContext;

    public MainModule(@NonNull Context context) {
        mContext = context;
    }

    @MainScope
    @Provides
    public Context provideContext() {
        return mContext;
    }

}
