package com.eshokin.socs.core;

import com.eshokin.socs.core.schemas.requests.GetStatisticsMethodRequest;
import com.eshokin.socs.core.schemas.responses.GetStatisticsMethodResponse;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ServerApi {

    @POST("/getStatisticsMethod")
    Observable<GetStatisticsMethodResponse> getStatisticsMethod(@Body GetStatisticsMethodRequest request);
}
