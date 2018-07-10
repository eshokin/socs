package com.eshokin.socs.application.di.modules.rx;

import com.eshokin.socs.application.di.AppScope;
import com.eshokin.socs.utils.Rx;

import dagger.Module;
import dagger.Provides;

@Module
public class RxModule {

    @AppScope
    @Provides
    public Rx provideRx() {
        return new Rx();
    }
}
