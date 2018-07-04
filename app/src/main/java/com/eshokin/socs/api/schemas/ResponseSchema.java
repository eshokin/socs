package com.eshokin.socs.api.schemas;

import com.google.gson.annotations.SerializedName;

public class ResponseSchema<T> {

    public static class Status {

        @SerializedName("error")
        private String error;

        @SerializedName("errorMessage")
        private String message;

        public String getError() {
            return error;
        }

        public void setError(String error) {
            this.error = error;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public boolean isOk() {
            return "ok".equalsIgnoreCase(error);
        }
    }

    @SerializedName("result")
    private T result;

    @SerializedName("status")
    private Status status;

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
