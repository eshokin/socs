package com.eshokin.socs.application.di.modules.calculating;

import com.eshokin.socs.application.di.AppScope;
import com.eshokin.socs.calculating.Calculating;

import dagger.Module;
import dagger.Provides;

@Module
public class CalculatingModel {

    @AppScope
    @Provides
    public Calculating provideCalculating() {
        return new Calculating();
    }
}
