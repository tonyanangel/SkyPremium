package com.skypremiuminternational.app.domain.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ERespond{
    @SerializedName("status")
    @Expose
    Boolean status;

    @SerializedName("message")
    @Expose
    String message;

    public void setMessage(String message) {
        this.message = message;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Boolean getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}
