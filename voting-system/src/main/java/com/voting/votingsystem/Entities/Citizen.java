package com.voting.votingsystem.Entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Citizen {

    private String name;
    private int age;
    String regionOfScotland;
    private int membershipNo;

}
