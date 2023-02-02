package com.voting.votingsystem.Entities;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Body {
    @JsonProperty("Body")
    private StkCallBack stkCallback;
}
