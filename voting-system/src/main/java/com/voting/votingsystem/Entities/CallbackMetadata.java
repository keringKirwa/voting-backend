package com.voting.votingsystem.Entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class CallbackMetadata {
    @JsonProperty("Item")
    private List<Item> Item;

}
