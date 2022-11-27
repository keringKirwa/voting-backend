package com.voting.votingsystem.Entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Candidate {
     private String name;
     private String party;
     private int candidateId;

}
