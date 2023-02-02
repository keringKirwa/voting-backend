package com.voting.votingsystem.Controllers;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.voting.votingsystem.Entities.Body;
import com.voting.votingsystem.Entities.Item;
import com.voting.votingsystem.lib.DateFormatter;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
public class VotingController {

    @PostMapping("/mpesa")
    public String createCallback(@RequestBody String jsonString) {
        DateFormatter dateFormatter=new DateFormatter();
        try{
            ObjectMapper mapper = new ObjectMapper();
            Body paymentDetails = mapper.readValue(jsonString, Body.class);

            if(paymentDetails.getStkCallback().getCallback().getCallbackMetadata() != null || paymentDetails.getStkCallback().getCallback().getResultCode()==0){

                System.out.println("JSON REP for a successful payment ::: "+jsonString);

                ArrayList<Item> paymentDetailsArray = (ArrayList<Item>) paymentDetails.getStkCallback().getCallback().getCallbackMetadata().getItem();


                int amountPaid= Integer.parseInt(paymentDetailsArray.get(0).getValue());
                String receiptNo=paymentDetailsArray.get(1).getValue();
                String unformattedDate= paymentDetailsArray.get(3).getValue();
                String phoneNumber=paymentDetailsArray.get(4).getValue();



                String formattedDate= dateFormatter.dateFormatter(unformattedDate);


            }else {
                System.out.println("UNSUCCESSFUL TRANSACTION :: The user cancelled the transaction OR  there is insufficient funds in his or her account !!! ");
                System.out.println("JSON DATA*(UNSUCCESSFUL) :: "+jsonString);
            }

        }catch(Exception ref){
            ref.printStackTrace();

        }


        return "Success message here ";

    }



}

