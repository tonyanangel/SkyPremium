package com.skypremiuminternational.app.domain.models.creditCard;

public class SetDefaultCreditCardrequest {
    String buildId;
    String chanel;

    public SetDefaultCreditCardrequest(String buildId, String chanel) {
        this.buildId = buildId;
        this.chanel = chanel;
    }

    public String getBuildId() {
        return buildId;
    }

    public void setBuildId(String buildId) {
        this.buildId = buildId;
    }

    public String getChanel() {
        return chanel;
    }

    public void setChanel(String chanel) {
        this.chanel = chanel;
    }
}
