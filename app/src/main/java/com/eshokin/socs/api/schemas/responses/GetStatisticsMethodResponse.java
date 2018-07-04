package com.eshokin.socs.api.schemas.responses;

import com.eshokin.socs.api.schemas.Point;
import com.eshokin.socs.api.schemas.ResponseSchema;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetStatisticsMethodResponse extends ResponseSchema<GetStatisticsMethodResponse.Result> {

    public static class Result {

        @SerializedName("points")
        private List<Point> points;

        public List<Point> getPoints() {
            return points;
        }

        public void setPoints(List<Point> points) {
            this.points = points;
        }
    }
}
