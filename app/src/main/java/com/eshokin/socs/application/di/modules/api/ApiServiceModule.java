package com.eshokin.socs.application.di.modules.api;

import com.eshokin.socs.application.di.AppScope;
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
