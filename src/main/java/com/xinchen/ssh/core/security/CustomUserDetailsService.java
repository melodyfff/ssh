package com.xinchen.ssh.core.security;

import com.xinchen.ssh.demo.entity.Role;
import com.xinchen.ssh.demo.entity.User;
import com.xinchen.ssh.demo.service.IUserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("customUserDetailsService")
public class CustomUserDetailsService implements UserDetailsService{

    private static final Logger LOGGER = Logger
            .getLogger(CustomUserDetailsService.class);

    @Autowired
    private IUserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        List<User> users = userService.load(username);

        LOGGER.info("User login:"+users);

        if (users == null || users.size()==0){
            LOGGER.info("USER NOT FOUND!");
            throw new UsernameNotFoundException("Username not found");
        }

        User user = users.get(0);

        return  new org.springframework.security.core.userdetails.User(user.getUserName(), user.getPassword(),
                true, true, true, true, getGrantedAuthorities(user));
    }

    private List<GrantedAuthority> getGrantedAuthorities(User user){

        List<GrantedAuthority> authorities = new ArrayList<>();

        for(Role role : user.getRoleList()){
            LOGGER.info("role : "+role);
            authorities.add(new SimpleGrantedAuthority("ROLE_"+role.getRoleName()));
        }
        LOGGER.info("authorities :"+authorities);
        return authorities;
    }

}
