package com.voting.votingsystem.Controllers;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.voting.votingsystem.Entities.Body;
import org.springframework.web.bind.annotation.*;

@RestController
public class VotingController {

    @PostMapping("/mpesa")
    public String createCallback(@RequestBody String jsonString) {
        try{
            ObjectMapper mapper = new ObjectMapper();
            Body paymentDetails = mapper.readValue(jsonString, Body.class);
            System.out.println(paymentDetails.getStkCallback().getCallback().getCallbackMetadata().getItem().get(0).getValue());
        }catch(Exception ref){
            ref.printStackTrace();

        }


        return "Success message here ";

    }



}

