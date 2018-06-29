package com.eshokin.socs.application.builder.modules.api;

import com.eshokin.socs.application.builder.AppScope;
import com.eshokin.socs.core.ServerApi;
import com.eshokin.socs.core.ClientApiService;

import dagger.Module;
import dagger.Provides;

@Module(includes = {ApiModule.class})
public class ClientApiModule {

    @AppScope
    @Provides
    public ClientApiService provideServerApiService(ServerApi api) {
        return new ClientApiService(api);
    }
}
