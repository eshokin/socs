package com.eshokin.socs.application.builder.modules.job;

import android.content.Context;
import android.util.Log;

import com.eshokin.socs.application.builder.AppScope;
import com.path.android.jobqueue.JobManager;
import com.path.android.jobqueue.config.Configuration;
import com.path.android.jobqueue.log.CustomLogger;

import dagger.Module;
import dagger.Provides;

@Module
public class JobManagerModel {

    @AppScope
    @Provides
    public JobManager provideJobManager(Context context, Configuration configuration) {
        return new JobManager(context, configuration);
    }

    @AppScope
    @Provides
    public Configuration provideConfiguration(Context context) {
        return new Configuration.Builder(context)
                .customLogger(new CustomLogger() {
                    private static final String TAG = "JOBS";

                    @Override
                    public boolean isDebugEnabled() {
                        return true;
                    }

                    @Override
                    public void d(String text, Object... args) {
                        Log.d(TAG, String.format(text, args));
                    }

                    @Override
                    public void e(Throwable t, String text, Object... args) {
                        Log.e(TAG, String.format(text, args), t);
                    }

                    @Override
                    public void e(String text, Object... args) {
                        Log.e(TAG, String.format(text, args));
                    }
                })
                .minConsumerCount(1)
                .maxConsumerCount(4)
                .loadFactor(4)
                .consumerKeepAlive(120)
                .build();
    }
}
