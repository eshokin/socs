package com.eshokin.socs.screens.main;

import com.arellomobile.mvp.InjectViewState;
import com.eshokin.socs.api.ApiService;
import com.eshokin.socs.api.schemas.requests.GetStatisticsMethodRequest;
import com.eshokin.socs.api.schemas.responses.GetStatisticsMethodResponse;
import com.eshokin.socs.application.AppController;
import com.eshokin.socs.screens.base.BasePresenter;
import com.eshokin.socs.utils.RxUtils;

import java.util.Date;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;

@InjectViewState
public class MainPresenter extends BasePresenter<MainView> {

    @Inject
    ApiService mApiService;

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

                    /*
                    if (response != null && response.getStatus() != null && response.getStatus().isOk() && response.getResult().getValues() != null) {
                        ActionOutput[] actionOutputs = response.getResult().getValues();
                        if (actionOutputs.length > 0) {
                            getViewState().setOffers(Arrays.asList(response.getResult().getValues()));
                            if (!TextUtils.isEmpty(mSelectedOfferId)) {
                                for (int i = 0; i < response.getResult().getValues().length; i++) {
                                    if (response.getResult().getValues()[i].get_id().equals(mSelectedOfferId)) {
                                        getViewState().selectOffer(i);
                                        break;
                                    }
                                }

                            }
                        } else {

                        }
                    } else {

                    } */
                }, error -> {
                    getViewState().showLoading(false);
                });
        unSubscribeOnDestroy(disposable);
    }
}
