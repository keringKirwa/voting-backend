package com.voting.votingsystem.Entities;

import org.springframework.security.core.GrantedAuthority;

public class MyAuthority implements GrantedAuthority {
    private final String authority;

    public MyAuthority(String authority) {
        this.authority = authority;
    }

    @Override
    public String getAuthority() {
        return authority;
    }
}