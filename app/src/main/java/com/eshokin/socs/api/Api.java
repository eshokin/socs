package com.eshokin.socs.api;

import com.eshokin.socs.api.schemas.requests.GetStatisticsMethodRequest;
import com.eshokin.socs.api.schemas.responses.GetStatisticsMethodResponse;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface Api {

    @POST("/getStatisticsMethod")
    Observable<GetStatisticsMethodResponse> getStatisticsMethod(@Body GetStatisticsMethodRequest request);
}
