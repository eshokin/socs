package com.eshokin.socs.api;

import android.support.annotation.NonNull;

import com.eshokin.socs.api.schemas.requests.GetStatisticsMethodRequest;
import com.eshokin.socs.application.AppController;
import com.eshokin.socs.jobs.DataGenerationJob;
import com.google.gson.Gson;
import com.path.android.jobqueue.JobManager;

import java.io.IOException;

import javax.inject.Inject;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Protocol;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;

public class MockServerInterceptor implements Interceptor {

    private Gson mGson;

    @Inject
    JobManager mJobManager;

    public MockServerInterceptor() {
        AppController.getComponent().inject(this);
        mGson = new Gson();
    }

    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        String responseString = "Error";
        Response.Builder response = new Response.Builder();
        response.request(chain.request()).protocol(Protocol.HTTP_1_0).addHeader("content-type", "application/json");
        if (chain.request() != null && chain.request().body() != null) {
            String body = getRequestBody(chain.request().body());
            GetStatisticsMethodRequest request = new Gson().fromJson(body, GetStatisticsMethodRequest.class);
            if (request != null) {
                mJobManager.addJobInBackground(new DataGenerationJob(request.getStartInterval(), request.getEndInterval()));
            }
            responseString = body;
        }
        return response.code(200).message(responseString).body(ResponseBody.create(MediaType.parse("application/json"), responseString.getBytes())).build();
    }

    private String getRequestBody(RequestBody requestBody) throws IOException {
        Buffer buffer = new Buffer();
        requestBody.writeTo(buffer);
        return buffer.readUtf8();
    }
}
