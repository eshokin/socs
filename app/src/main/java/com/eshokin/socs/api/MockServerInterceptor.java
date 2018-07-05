package com.eshokin.socs.api;

import android.support.annotation.NonNull;

import com.eshokin.socs.api.schemas.Point;
import com.eshokin.socs.api.schemas.requests.GetStatisticsMethodRequest;
import com.eshokin.socs.api.schemas.responses.GetStatisticsMethodResponse;
import com.eshokin.socs.application.AppController;
import com.eshokin.socs.calculating.Calculating;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.List;

import javax.inject.Inject;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Protocol;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import okio.Buffer;

public class MockServerInterceptor implements Interceptor {

    private Gson mGson;

    @Inject
    Calculating mCalculating;

    public MockServerInterceptor() {
        AppController.getComponent().inject(this);
        mGson = new Gson();
    }

    @Override
    public okhttp3.Response intercept(@NonNull Chain chain) throws IOException {
        okhttp3.Response.Builder response = new okhttp3.Response.Builder();
        response.request(chain.request()).code(200).protocol(Protocol.HTTP_1_0).addHeader("content-type", "application/json");
        GetStatisticsMethodResponse statisticsMethodResponse = new GetStatisticsMethodResponse();
        statisticsMethodResponse.setResult(new GetStatisticsMethodResponse.Result());
        statisticsMethodResponse.setStatus(new Response.Status());
        statisticsMethodResponse.getStatus().setError("internal_error");
        statisticsMethodResponse.getStatus().setMessage("Internal error");

        if (chain.request() != null && chain.request().body() != null) {
            String body = getRequestBody(chain.request().body());
            GetStatisticsMethodRequest request = mGson.fromJson(body, GetStatisticsMethodRequest.class);
            if (request != null) {
                List<Point> points = mCalculating.generateStatistics(request.getStartInterval(), request.getEndInterval());
                statisticsMethodResponse.getResult().setPoints(points);
                statisticsMethodResponse.getStatus().setError("ok");
                statisticsMethodResponse.getStatus().setMessage("ok");
            }
        }
        String responseString = mGson.toJson(statisticsMethodResponse);
        return response.message(responseString).body(ResponseBody.create(MediaType.parse("application/json"), responseString.getBytes())).build();
    }

    private String getRequestBody(RequestBody requestBody) throws IOException {
        Buffer buffer = new Buffer();
        requestBody.writeTo(buffer);
        return buffer.readUtf8();
    }
}
