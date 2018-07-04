package com.eshokin.socs.application.builder.modules.calculating;

import com.eshokin.socs.application.builder.AppScope;
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
