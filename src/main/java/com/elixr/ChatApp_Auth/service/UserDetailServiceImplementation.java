package com.elixr.ChatApp_Auth.service;

import com.elixr.ChatApp_Auth.contants.LoggerInfoConstants;
import com.elixr.ChatApp_Auth.contants.MessagesConstants;
import com.elixr.ChatApp_Auth.model.AuthUser;
import com.elixr.ChatApp_Auth.repository.AuthUserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class UserDetailServiceImplementation implements UserDetailsService {


    private final AuthUserRepository authUserRepository;

    public UserDetailServiceImplementation(AuthUserRepository authUserRepository) {
        this.authUserRepository = authUserRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<AuthUser> authUser = authUserRepository.findByUserName(username);
        if (authUser.isEmpty()){
            throw new UsernameNotFoundException(MessagesConstants.USER_NOT_FOUND);
        }
        return User.builder()
                .username(authUser.get().getUserName())
                .password(authUser.get().getPassword())
                .build();
    }
}
