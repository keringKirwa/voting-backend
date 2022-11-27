package com.voting.votingsystem.Models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CitizenModel {

    private String name;
    private String age;
    private String region;
    private String membershipId;
}
