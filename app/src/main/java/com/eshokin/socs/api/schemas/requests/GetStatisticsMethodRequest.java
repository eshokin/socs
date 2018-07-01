package com.eshokin.socs.api.schemas.requests;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class GetStatisticsMethodRequest {

    @SerializedName("startInterval")
    private Date startInterval;

    @SerializedName("endInterval")
    private Date endInterval;

    public void setStartInterval(Date startInterval) {
        this.startInterval = startInterval;
    }

    public void setEndInterval(Date endInterval) {
        this.endInterval = endInterval;
    }
}

