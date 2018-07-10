package com.eshokin.socs.application.di.modules.context;

import android.content.Context;
import android.support.annotation.NonNull;

import com.eshokin.socs.application.di.AppScope;

import dagger.Module;
import dagger.Provides;

@Module
public class ContextModule {

    private Context mContext;

    public ContextModule(@NonNull Context context) {
        mContext = context;
    }

    @AppScope
    @Provides
    public Context provideContext() {
        return mContext.getApplicationContext();
    }
}
