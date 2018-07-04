package com.eshokin.socs.application.builder;

import com.eshokin.socs.api.MockServerInterceptor;
import com.eshokin.socs.application.builder.modules.api.ApiServiceModule;
import com.eshokin.socs.application.builder.modules.calculating.CalculatingModel;
import com.eshokin.socs.application.builder.modules.context.ContextModule;
import com.eshokin.socs.application.builder.modules.job.JobManagerModel;
import com.eshokin.socs.jobs.DataGenerationJob;
import com.eshokin.socs.screens.main.MainActivity;
import com.eshokin.socs.screens.main.MainPresenter;

import dagger.Component;

@AppScope
@Component(modules = {ContextModule.class, ApiServiceModule.class, JobManagerModel.class, CalculatingModel.class})
public interface AppComponent {

    void inject(MainActivity activity);

    void inject(MainPresenter presenter);

    void inject(MockServerInterceptor interceptor);

    void inject(DataGenerationJob job);
}
