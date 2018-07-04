package com.eshokin.socs.api.schemas.responses;

import com.eshokin.socs.api.schemas.ResponseSchema;
import com.google.gson.annotations.SerializedName;

public class GetStatisticsMethodResponse extends ResponseSchema<GetStatisticsMethodResponse.Result> {

    public static class Result {

          @SerializedName("name")
          private Boolean deliveryAvailable;
    }

}
