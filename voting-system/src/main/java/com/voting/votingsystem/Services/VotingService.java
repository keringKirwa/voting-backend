package com.voting.votingsystem.Services;

import com.voting.votingsystem.Entities.Candidate;
import com.voting.votingsystem.Entities.Citizen;
import com.voting.votingsystem.Entities.VotingTally;
import com.voting.votingsystem.Exceptions;
import com.voting.votingsystem.Models.CitizenModel;
import com.voting.votingsystem.Models.VotingDetails;
import com.voting.votingsystem.Models.UpdateVotingStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;

@Service
public class VotingService {
    private static final Logger logger = LoggerFactory.getLogger(VotingService.class);
    public Candidate c1,c2,c3;
    public final String PASSWORD= "pass";
    public boolean votingStopped=false;
    private final int idAllowedToVote []={6289,1010, 909393, 500,575757};
    public ArrayList<Citizen> citizensList=new ArrayList<>();
    public HashMap<Integer, Integer> hasVoted=new HashMap<>();
    public HashMap<Integer, Integer> registeredForVoting=new HashMap<Integer, Integer>();
    public HashMap<Integer, Integer> votedFor=new HashMap<>();
    public HashMap<Integer,  ArrayList<Integer>> candidateVotes=new HashMap<>();

    public String  registerCitizen(CitizenModel citizenModel) throws Exceptions {

        String name = citizenModel.getName();
        int age=Integer.parseInt(citizenModel.getAge());
        int citizenId=Integer.parseInt(citizenModel.getMembershipId());
        String region =citizenModel.getRegion();
        if(region==null || name==null|| age <18 || allowedToVote(citizenId)==false){
            throw  new Exceptions("User not allowed to vote ");

        }
        if(citizensList.contains(new Citizen(name, age,region,citizenId))){
            logger.error("Voter with Id ("+citizenId+") Had Already Registered");
            return "Voter  Had Already Registered";
        }
        citizensList.add(new Citizen(name, age,region,citizenId));
        registeredForVoting.put(Integer.parseInt(citizenModel.getMembershipId()),1);
        logger.info("Voter With MemberShipId (" +citizenId+" ) Qualifies  for Voting, Hence Successfully Registered");
        return "Successfully Registered";
}

    public String  vote(VotingDetails votingDetails) throws Exceptions {
        if(votingStopped){
            return "Sorry , voting Already Stopped";
        }
        Integer cId=Integer.parseInt(votingDetails.getCitizenId());

        if(registeredForVoting.get(cId)== null || registeredForVoting.get(cId)!=1){
            logger.error("Voter Did not register for Voting");
            throw new Exceptions("Voter Did not register for Voting");
        }

        if( hasVoted.get(cId)!=null && hasVoted.get(cId)==1 ){
            logger.error("Voter Cannot Cast A vote twice");
            throw new Exceptions("Voter Cannot Cast A vote twice");
        }

        if(candidateVotes.size()==0){
            candidateVotes.put(Integer.parseInt(votingDetails.getCandidateId()),new ArrayList<Integer>());
            candidateVotes.get(Integer.parseInt(votingDetails.getCandidateId())).add(cId);
            votedFor.put(cId,Integer.parseInt(votingDetails.getCandidateId()));
            hasVoted.put(cId,1);
            logger.info("Voter with Id ( "+cId+" ) Voted Successfully" );
            return "voted Successfully ";
        }else {
            candidateVotes.get(Integer.parseInt(votingDetails.getCandidateId())).add(cId);

        }
        votedFor.put(cId,Integer.parseInt(votingDetails.getCandidateId()));
        hasVoted.put(cId,1);
        logger.info("Voter with Id ( "+cId+" ) Voted Successfully" );

        return "voted Successfully ";
    }
    public ArrayList<VotingTally>  getTallies(){
        ArrayList<VotingTally> votingTallies=new ArrayList<>();
        votingTallies.add(new VotingTally(c1,candidateVotes.get(c1.getCandidateId()).size()));
        votingTallies.add(new VotingTally(c2,candidateVotes.get(c2.getCandidateId()).size()));
        votingTallies.add(new VotingTally(c3,candidateVotes.get(c3.getCandidateId()).size()));
        return votingTallies;
    }
    public String  retractVote(Integer cId) throws Exceptions {
        if(hasVoted.get(cId)!=null && hasVoted.get(cId)!=1){
            logger.error("Voter with Id ( " +cId+ " )Had Not yet voted");
            throw new Exceptions("Had Not yet voted ");
        }
        if(registeredForVoting.get(cId)!=null && registeredForVoting.get(cId) !=1){
            logger.error("Voter with Id ( " +cId+ " ) Did not register for Voting");
            throw new Exceptions("Voter Did not register for Voting");
        }
        if(hasVoted.get(cId)==null){
            throw new Exceptions("Had not voted Yet");
        }

        hasVoted.remove(cId);
        if(votedFor.get(cId)!=null ){
            Integer votedForId=votedFor.get(cId);
            boolean removed =candidateVotes.get(votedForId).remove(cId);
        }

        logger.info("Voter with Id ( "+cId+" ) Retracted His/Her Vote Successfully" );

        return "Successfully Retracted";
    }
    private  boolean allowedToVote(int id){
        boolean  validCitizen =false;
        for (int citizenIdentifier :idAllowedToVote) {
            if(id==citizenIdentifier){
                validCitizen=true;
                System.out.println("Valid citizen ::++++++++++++++++++++++>>>> "+id);
                break;
            }
        }
        return validCitizen;
    }

    private  boolean registeredForVoting(int id){
        return registeredForVoting.get(id)==1;

    }
    public   ArrayList<Candidate> getCandidates(){
        ArrayList<Candidate> cand=new ArrayList<>();
        cand.add(c1);
        cand.add(c2);
        cand.add(c3);
        return  cand;
    }
public String getVotingStatus(String ps) throws Exceptions {
        if(!ps.equals(PASSWORD)){
            throw new Exceptions("Only admin can update voting status");
        }
        if(votingStopped==false){
            return "Open";
        }
        else {
            return "Closed";
        }

}

     public Candidate getMyVote (String cId) throws Exceptions {
        Integer newCID=Integer.parseInt(cId);
        if(hasVoted.get(newCID)==null || hasVoted.get(newCID)!=null && hasVoted.get(newCID)!=1){
            throw new Exceptions("Had Not yet Voted");

        }else {
            Integer iVotedFor=votedFor.get(newCID);
            if(c1.getCandidateId()==iVotedFor){
                return c1;
            }
            else if(c2.getCandidateId()==iVotedFor) return c2;
            else return c3;
        }
    }

    public String updateVotingStopped(UpdateVotingStatus updateVotingStatus){
        if(!PASSWORD.equals(updateVotingStatus.getPassword())){
            return ("password not correct");
        }
        if((updateVotingStatus.getVotingStatus()).equals("stop")){
            votingStopped=true;
            return "closed";
        }if(updateVotingStatus.getVotingStatus().equals("continue")){
            votingStopped=false;
            return "open";
        }
        logger.info("Voting Status Successfully Updated By the Admin.  :: Voting Stopped Is:( "+votingStopped+" )" );
        return "voting updated successfully, Now Opened";
    }

    @Bean
    public void registerCandidates(){
        c1=new Candidate("Kumar","Republican Party", 500);
        c2=new Candidate("Peter","Rebellion Party", 65432);
        c3=new Candidate("Samson","Democratic Party", 6789);

        ArrayList<Integer> arr1=new ArrayList<>();
        ArrayList<Integer> arr2=new ArrayList<>();
        ArrayList<Integer> arr3=new ArrayList<>();

        candidateVotes.put(c1.getCandidateId(),arr1);
        candidateVotes.put(c2.getCandidateId(),arr2);
        candidateVotes.put(c3.getCandidateId(),arr3);

    }

}
