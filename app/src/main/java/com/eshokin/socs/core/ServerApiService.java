package com.eshokin.socs.core;

import com.eshokin.socs.core.schemas.requests.GetStatisticsMethodRequest;
import com.eshokin.socs.core.schemas.responses.GetStatisticsMethodResponse;

import io.reactivex.Observable;

public class ServerApiService {

    private ServerApi mServerApi;

    public ServerApiService(ServerApi serverApi) {
        mServerApi = serverApi;
    }

    public Observable<GetStatisticsMethodResponse> getStatisticsMethod(GetStatisticsMethodRequest request) {
        return mServerApi.getStatisticsMethod(request);
    }
}
