package com.voting.votingsystem.Entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;

@Data
@Getter
public class Item {
    @JsonProperty("Name")
    private String Name;
    @JsonProperty("Value")
    private String Value;


    @Override
    public String toString() {
        return "Item{" +
                "Name='" + Name + '\'' +
                ", Value='" + Value + '\'' +
                '}';
    }
}
