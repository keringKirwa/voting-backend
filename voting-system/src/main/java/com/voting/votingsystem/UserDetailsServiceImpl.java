package com.voting.votingsystem;

import com.voting.votingsystem.Entities.MyAuthority;
import com.voting.votingsystem.Entities.MyUserDetails;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.ArrayList;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
   /**
    *  @Autowired

    private UserRepository to be implemented here.
    note that the UserDetails is an interface and hence can be used as a type.
    */

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("the login function was called ....");
        ArrayList<MyAuthority> myAuthorities=new ArrayList<>();

        myAuthorities.add(new MyAuthority("user"));
        myAuthorities.add(new MyAuthority("Admin"));

        MyUserDetails myUserDetails=new MyUserDetails("kelvinkerin@gmail.com","$2a$10$Js9Q.tP8KBT4s.S2W/w24.Os8PIAHtgIJnO6eZXvnqzEXFGMJ3qHu",myAuthorities);

        return myUserDetails;
    }
}
