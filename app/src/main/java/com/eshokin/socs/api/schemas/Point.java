package com.eshokin.socs.api.schemas;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class Point {

    @SerializedName("date")

    private Date date;

    @SerializedName("rate")
    private Double rate;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Double getRate() {
        return rate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }
}
