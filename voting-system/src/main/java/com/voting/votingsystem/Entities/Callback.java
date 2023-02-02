package com.voting.votingsystem.Entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data // to automatically generate the getters and setters for  this class,plus all the constructors .
public class Callback {
    @JsonProperty("MerchantRequestID")
    private String MerchantRequestID;

    @JsonProperty("CheckoutRequestID")
    private String CheckoutRequestID;

    @JsonProperty("ResultCode")
    private int ResultCode;

    @JsonProperty("ResultDesc")
    private String ResultDesc;

    @JsonProperty("CallbackMetadata")
    private CallbackMetadata CallbackMetadata;
    @Override
    public String toString() {
        return "Callback{" +
                "MerchantRequestID='" + CheckoutRequestID + '\'' +
                ", CheckoutRequestID='" + CheckoutRequestID + '\'' +
                ", ResultCode=" + ResultCode +
                ", ResultDesc='" + ResultDesc + '\'' +
                ", CallbackMetadata=" + CallbackMetadata +
                '}';
    }
}

