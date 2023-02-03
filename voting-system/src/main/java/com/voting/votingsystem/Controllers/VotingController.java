package com.voting.votingsystem.Controllers;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.voting.votingsystem.Entities.*;
import com.voting.votingsystem.lib.DateFormatter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;

import java.util.ArrayList;

@RestController
@RequiredArgsConstructor
public class VotingController {
    @Autowired
    private AuthenticationManager authenticationManager;

    /**
     * the controller funtionns in this case can be used to return a ResponsrEntity instead of returning a specific object .
     * in this way , we will  we can return an error object  too.
     * @param jsonString
     * @return
     */
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

    @PostMapping("/auth/login")
    public ResponseEntity<Object> login(@RequestBody LoginRequest loginRequest){

        try {
            /**
             * note that teh  authenticationManager below refers to DAO authentication  manager .
             */
            System.out.println(new PasswordEncode().passwordEncoder().encode(loginRequest.getPassword()));

            Authentication authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            return ResponseEntity.ok(new LoginResponse(authentication.getName(),"this is the JWT token"));

        } catch (BadCredentialsException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
        }

    }




}

