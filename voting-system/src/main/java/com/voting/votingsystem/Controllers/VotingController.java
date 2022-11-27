package com.voting.votingsystem.Controllers;

import com.voting.votingsystem.Entities.Candidate;
import com.voting.votingsystem.Entities.VotingTally;
import com.voting.votingsystem.Exceptions;
import com.voting.votingsystem.Models.CitizenModel;
import com.voting.votingsystem.Models.UpdateVotingStatus;
import com.voting.votingsystem.Models.VotingDetails;
import com.voting.votingsystem.Services.VotingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
public class VotingController {

    @Autowired
    private VotingService votingService;

    @PostMapping("/registerCitizen")
    public String  registerCitizen(@RequestBody CitizenModel registrationDetails) throws Exceptions {
        return  votingService.registerCitizen(registrationDetails);
    }

    @PostMapping("/vote")
    public String vote(@RequestBody VotingDetails votingDetails) throws Exceptions {
        return votingService.vote(votingDetails);
    }

    @PutMapping("vote/retractVote/{citizenId}")
    public String retractVote(@PathVariable String citizenId) throws Exceptions {
        int intVoterId =Integer.parseInt(citizenId);
        return votingService.retractVote(intVoterId);
    }


    @GetMapping ("/candidates/getAllCandidates")
    public ArrayList<Candidate> getAllCandidates() {
        return votingService.getCandidates();
    }

    @GetMapping ("/tallies")
    public ArrayList<VotingTally> getTallies() {
        return votingService.getTallies();
    }

    @GetMapping ("/get-my-vote/{cId}")
    public Candidate  getMyVote(@PathVariable String cId) throws Exceptions {
        return votingService.getMyVote(cId);
    }

    @GetMapping ("/getVotingStatus/{p}")
    public String getVotingStatus(@PathVariable String p) throws Exceptions {
        return votingService.getVotingStatus(p);
    }
    @PostMapping("/update-voting-status")
    public String updateVotingStatus(@RequestBody UpdateVotingStatus updateVotingStatus) {
        return votingService.updateVotingStopped(updateVotingStatus);
    }
}
