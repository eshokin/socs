package com.eshokin.socs.screens.main;

import com.arellomobile.mvp.InjectViewState;
import com.eshokin.socs.api.ApiService;
import com.eshokin.socs.api.schemas.Point;
import com.eshokin.socs.api.schemas.requests.GetStatisticsMethodRequest;
import com.eshokin.socs.api.schemas.responses.GetStatisticsMethodResponse;
import com.eshokin.socs.application.AppController;
import com.eshokin.socs.calculating.Calculating;
import com.eshokin.socs.screens.base.BasePresenter;
import com.eshokin.socs.utils.RxUtils;
import com.path.android.jobqueue.JobManager;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;

@InjectViewState
public class MainPresenter extends BasePresenter<MainView> {

    @Inject
    ApiService mApiService;

    @Inject
    JobManager mJobManager;

    public MainPresenter() {
        AppController.getComponent().inject(this);
    }

    public void getStatistics(Date startInterval, Date endInterval) {
        getViewState().showLoading(true);
        GetStatisticsMethodRequest request = new GetStatisticsMethodRequest();
        request.setStartInterval(startInterval);
        request.setEndInterval(endInterval);

        final Observable<GetStatisticsMethodResponse> observable = mApiService.getStatisticsMethod(request);
        Disposable disposable = observable
                .compose(RxUtils.applySchedulers())
                .subscribe(response -> {
                    getViewState().showLoading(false);
                    if (response != null && response.getStatus() != null && response.getStatus().isOk() && response.getResult().getPoints() != null) {
                        List<Point> points = response.getResult().getPoints();
                        if (points.size() > 0) {

                        } else {
                            // empty list
                        }
                    } else {
                        // server internal error
                    }
                }, error -> {
                    getViewState().showLoading(false);
                    // server not responding
                });
        unSubscribeOnDestroy(disposable);
    }
}
