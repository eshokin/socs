package com.eshokin.socs.api;

import com.eshokin.socs.api.schemas.requests.GetStatisticsMethodRequest;
import com.eshokin.socs.api.schemas.responses.GetStatisticsMethodResponse;

import io.reactivex.Observable;

public class ApiService {

    private Api mApi;

    public ApiService(Api api) {
        mApi = api;
    }

    public Observable<GetStatisticsMethodResponse> getStatisticsMethod(GetStatisticsMethodRequest request) {
        return mApi.getStatisticsMethod(request);
    }
}
