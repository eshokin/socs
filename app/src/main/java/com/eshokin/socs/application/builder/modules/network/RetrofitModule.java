package com.eshokin.socs.application.builder.modules.network;

import com.eshokin.socs.BuildConfig;
import com.eshokin.socs.application.builder.AppScope;

import dagger.Module;
import dagger.Provides;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module(includes = {OkHttpClientModule.class})
public class RetrofitModule {

    @AppScope
    @Provides
    public Retrofit provideRetrofit(Retrofit.Builder builder) {
        return builder.baseUrl(BuildConfig.HOST).build();
    }

    @AppScope
    @Provides
    public Retrofit.Builder provideRetrofitBuilder(OkHttpClient okHttpClient, Converter.Factory converterFactory, RxJava2CallAdapterFactory rxJava2CallAdapterFactoryAdapter) {
        return new Retrofit.Builder()
                .client(okHttpClient)
                .addCallAdapterFactory(rxJava2CallAdapterFactoryAdapter)
                .addConverterFactory(converterFactory);
    }

    @AppScope
    @Provides
    public Converter.Factory provideConverterFactory() {
        return GsonConverterFactory.create();
    }

    @AppScope
    @Provides
    RxJava2CallAdapterFactory provideRxAdapter() {
        return RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io());
    }

}
