package com.eshokin.socs.application.di;

import com.eshokin.socs.api.ApiService;
import com.eshokin.socs.api.MockServerInterceptor;
import com.eshokin.socs.application.di.modules.api.ApiServiceModule;
import com.eshokin.socs.application.di.modules.calculating.CalculatingModel;
import com.eshokin.socs.application.di.modules.context.ContextModule;
import com.eshokin.socs.application.di.modules.job.JobManagerModel;
import com.eshokin.socs.application.di.modules.rx.RxModule;
import com.eshokin.socs.calculating.Calculating;
import com.eshokin.socs.jobs.CalculateAverageJob;
import com.eshokin.socs.jobs.CalculateInterquartileRangeJob;
import com.eshokin.socs.jobs.CalculateMedianJob;
import com.eshokin.socs.jobs.CalculateMinMaxJob;
import com.eshokin.socs.screens.main.MainActivity;
import com.eshokin.socs.screens.main.MainPresenter;
import com.eshokin.socs.utils.Rx;
import com.path.android.jobqueue.JobManager;

import dagger.Component;

@AppScope
@Component(modules = {ContextModule.class, ApiServiceModule.class, JobManagerModel.class, CalculatingModel.class, RxModule.class})
public interface AppComponent {

    ApiService provideApiService();

    JobManager provideJobManager();

    Rx provideRx();

    void inject(MockServerInterceptor interceptor);

    void inject(CalculateMinMaxJob job);

    void inject(CalculateAverageJob job);

    void inject(CalculateMedianJob job);

    void inject(CalculateInterquartileRangeJob job);
}
