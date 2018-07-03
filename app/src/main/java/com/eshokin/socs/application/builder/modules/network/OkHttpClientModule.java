package com.eshokin.socs.application.builder.modules.network;

import android.util.Log;

import com.eshokin.socs.api.MockServerInterceptor;
import com.eshokin.socs.application.builder.AppScope;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

@Module
public class OkHttpClientModule {

    private static final String TAG = "OkHttpClient";

    @AppScope
    @Provides
    public OkHttpClient provideOkHttpClient(MockServerInterceptor mockServerInterceptor, HttpLoggingInterceptor httpLoggingInterceptor) {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.addInterceptor(mockServerInterceptor);
        builder.addInterceptor(httpLoggingInterceptor);
        return builder.build();
    }

    @AppScope
    @Provides
    public MockServerInterceptor provideMockServerInterceptor() {
        return new MockServerInterceptor();
    }

    @AppScope
    @Provides
    public HttpLoggingInterceptor provideHttpLoggingInterceptor() {
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor(message -> Log.d(TAG, message));
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return httpLoggingInterceptor;
    }
}
