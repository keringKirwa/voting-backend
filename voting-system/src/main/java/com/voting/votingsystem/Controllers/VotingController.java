package com.voting.votingsystem.Controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.voting.votingsystem.Entities.*;
import com.voting.votingsystem.UserDetailsServiceImpl;
import com.voting.votingsystem.utils.DateFormatter;
import com.voting.votingsystem.utils.JWTInterpreter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class VotingController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JWTInterpreter jwtInterpreter;
    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    /**
     * the controller funtionns in this case can be used to return a ResponsrEntity instead of returning a specific object .
     * in this way , we will  we can return an error object  too.
     *
     * @param jsonString
     * @return
     */
    @PostMapping("/mpesa")
    public String createCallback(@RequestBody String jsonString) {
        DateFormatter dateFormatter = new DateFormatter();
        try {
            ObjectMapper mapper = new ObjectMapper();
            Body paymentDetails = mapper.readValue(jsonString, Body.class);

            if (paymentDetails.getStkCallback().getCallback().getCallbackMetadata() != null || paymentDetails.getStkCallback().getCallback().getResultCode() == 0) {

                System.out.println("JSON REP for a successful payment ::: " + jsonString);

                ArrayList<Item> paymentDetailsArray = (ArrayList<Item>) paymentDetails.getStkCallback().getCallback().getCallbackMetadata().getItem();


                String receiptNo = paymentDetailsArray.get(1).getValue();
                String unformattedDate = paymentDetailsArray.get(3).getValue();
                String phoneNumber = paymentDetailsArray.get(3).getValue();
                System.out.println(receiptNo);


                String formattedDate = dateFormatter.dateFormatter(unformattedDate);

            } else {
                System.out.println("UNSUCCESSFUL TRANSACTION :: The user cancelled the transaction OR  there is insufficient funds in his or her account !!! ");
                System.out.println("JSON DATA*(UNSUCCESSFUL) :: " + jsonString);
            }

        } catch (Exception ref) {
            ref.printStackTrace();
        }
        return "Success message here ";

    }

    @PostMapping("/auth/login")
    public ResponseEntity<Object> login(@RequestBody LoginRequest loginRequest) {

        try {

            /**
             * note that teh  authenticationManager below refers to DAO authentication  manager .
             * note  that for  login and the register , the users will not be required  to provide the jwt . but for all  the  other requests , then
             * the  jwt token that is not expired  must be provided . so for the register  , we save the user details , then for the login  , we authenticate the users .
             */

            HashMap<String, Object> hashMap=new HashMap<>();
            hashMap.put("kelvin", "kering");
            hashMap.put("school", "Lamaon");

            System.out.println(new PasswordEncode().passwordEncoder().encode(loginRequest.getPassword()));

            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            return ResponseEntity.ok(new LoginResponse(authentication.getName(),jwtInterpreter.generateJWT(hashMap,userDetailsService.loadUserByUsername("kelvin"))));

        } catch (BadCredentialsException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
        }

    }

    @PostMapping("/auth/register")
    public ResponseEntity<Object> register(@RequestBody LoginRequest loginRequest) {
        /**
         * no auth for the register  page .
         */

        try {
            System.out.println("register Controller called ");
            System.out.println(loginRequest.getPassword());



            return ResponseEntity.status(HttpStatus.ACCEPTED).body("hello login sucessful...");

        } catch (BadCredentialsException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
        }

    }


}

