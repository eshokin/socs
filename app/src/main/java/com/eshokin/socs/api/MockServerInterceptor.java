package com.eshokin.socs.api;

import com.eshokin.socs.jobs.DataGenerationJob;
import com.path.android.jobqueue.JobManager;

import java.io.IOException;
import java.util.Date;

import javax.inject.Inject;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Protocol;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class MockServerInterceptor implements Interceptor {

    @Inject
    JobManager mJobManager;

    @Override
    public Response intercept(Chain chain) throws IOException {
        Response response = null;
        if (chain.request() != null && chain.request().body() != null) {


            mJobManager.addJobInBackground(new DataGenerationJob(new Date(), new Date()));

            String responseString="Re re";
            response = new Response.Builder()
                    .code(200)
                    .message(responseString)
                    .request(chain.request())
                    .protocol(Protocol.HTTP_1_0)
                    .body(ResponseBody.create(MediaType.parse("application/json"), responseString.getBytes()))
                    .addHeader("content-type", "application/json")
                    .build();
        } else {
            // error
            response = chain.proceed(chain.request());
        }
        return response;

        /*
        if(BuildConfig.DEBUG) {
            Log.d(TAG, "---- DEBUG --- DEBUG -- DEBUG - DEBUG -- DEBUG --- DEBUG ----");
            Log.d(TAG, "----                FAKE SERVER RESPONSES                ----");
            String responseString;
            // Get Request URI.
            final URI uri = chain.request().uri();
            Log.d(TAG, "---- Request URL: " + uri.toString());
            // Get Query String.
            final String query = uri.getQuery();
            // Parse the Query String.
            final String[] parsedQuery = query.split("=");
            if(parsedQuery[0].equalsIgnoreCase("id") && parsedQuery[1].equalsIgnoreCase("1")) {
                responseString = TEACHER_ID_1;
            }
            else if(parsedQuery[0].equalsIgnoreCase("id") && parsedQuery[1].equalsIgnoreCase("2")){
                responseString = TEACHER_ID_2;
            }
            else {
                responseString = "";
            }

            response = new Response.Builder()
                    .code(200)
                    .message(responseString)
                    .request(chain.request())
                    .protocol(Protocol.HTTP_1_0)
                    .body(ResponseBody.create(MediaType.parse("application/json"), responseString.getBytes()))
                    .addHeader("content-type", "application/json")
                    .build();

            //Log.d(TAG, "---- DEBUG --- DEBUG -- DEBUG - DEBUG -- DEBUG --- DEBUG ----");
        }
        else {
            response = chain.proceed(chain.request());
        } */


    }
}
