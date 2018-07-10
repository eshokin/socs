package com.eshokin.socs.api.schemas.requests;

import com.eshokin.socs.api.enumerations.Interval;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class GetStatisticsMethodRequest {

    @SerializedName("startDate")
    private Date startDate;

    @SerializedName("endDate")
    private Date endDate;

    @SerializedName("interval")
    private Interval interval;

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public Interval getInterval() {
        return interval;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public void setInterval(Interval interval) {
        this.interval = interval;
    }
}

