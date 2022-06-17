package com.example.sen_touroperator.config.security;

import com.example.sen_touroperator.exception_handler.exceptions.UserException;
import com.example.sen_touroperator.repositroy.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MyUserDetailService implements UserDetailsService {
    private final UserRepository userRepository;


    public MyUserDetailService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        com.example.sen_touroperator.models.DAO.User user =
                userRepository.findUserByUsername(username).orElse(null);
        if(user ==null){
            throw new UserException("User doesnt exist");
        }
        return new MyUserSec(user.getUsername(),
                user.getPassword(),user.getId().toString(),user.getRole(),new ArrayList<>());
        //database User
    }
}
