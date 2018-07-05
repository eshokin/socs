package com.eshokin.socs.application.builder.modules.rx;

import com.eshokin.socs.application.builder.AppScope;
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
