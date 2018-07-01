package com.eshokin.socs.application.builder.modules.api;

import com.eshokin.socs.application.builder.AppScope;
import com.eshokin.socs.api.Api;
import com.eshokin.socs.api.ApiService;

import dagger.Module;
import dagger.Provides;

@Module(includes = {ApiModule.class})
public class ApiServiceModule {

    @AppScope
    @Provides
    public ApiService provideApiService(Api api) {
        return new ApiService(api);
    }
}
