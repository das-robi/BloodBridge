package com.robindas.bloodbridge.Services;

import com.robindas.bloodbridge.Exceptions.BadRequestException;
import com.robindas.bloodbridge.Exceptions.ResourceNotFoundException;
import com.robindas.bloodbridge.Model.Users;
import com.robindas.bloodbridge.Repositories.UsersRepository;
import com.robindas.bloodbridge.Util.UserPrinciple;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailServices implements UserDetailsService {

    @Autowired
    private UsersRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        System.out.println("USER name " + username);

        Users users = repository.getUserByUserName(username);

        if (users == null){
            System.out.println("USER Not Found");

            throw new BadRequestException("Username & Password Invalid");
        }

        return new UserPrinciple(users);
    }
}
