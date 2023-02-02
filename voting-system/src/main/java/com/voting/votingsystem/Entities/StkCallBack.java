package com.voting.votingsystem.Entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class StkCallBack {

    @JsonProperty("stkCallback")
    private Callback callback;

    // Getter Methods


}