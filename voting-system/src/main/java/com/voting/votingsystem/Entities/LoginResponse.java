package com.voting.votingsystem.Entities;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Getter
@Builder
public class LoginResponse{
    private String username;
    private String  jwtToken;

}