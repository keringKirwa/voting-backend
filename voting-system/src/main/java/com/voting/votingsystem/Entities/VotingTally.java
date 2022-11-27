package com.voting.votingsystem.Entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class VotingTally {
    private Candidate candidate;
    private int totalVotes;

}
