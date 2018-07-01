package com.eshokin.socs.application.builder.modules.api;

import com.eshokin.socs.application.builder.AppScope;
import com.eshokin.socs.application.builder.modules.network.RetrofitModule;
import com.eshokin.socs.api.Api;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

@Module(includes = {RetrofitModule.class})
public class ApiModule {

    @AppScope
    @Provides
    public Api provideApi(Retrofit retrofit) {
        return retrofit.create(Api.class);
    }
}
