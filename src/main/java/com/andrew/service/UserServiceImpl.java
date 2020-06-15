package com.andrew.service;


import com.andrew.forms.UserForm;
import com.andrew.models.Role;
import com.andrew.models.State;
import com.andrew.models.Token;
import com.andrew.models.User;
import com.andrew.repositories.TokensRepository;
import com.andrew.repositories.UsersRepository;
import com.andrew.transfer.TokenDto;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.apache.commons.lang.RandomStringUtils;

import java.util.Optional;

import static com.andrew.transfer.TokenDto.from;


@Service
public class UserServiceImpl implements UserService{
    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private TokensRepository tokensRepository;

    @Override
    public void signUp(UserForm userForm) {
        String hashPassword = passwordEncoder.encode(userForm.getPassword());
        System.err.println(hashPassword);
        User user = User.builder()
                .username(userForm.getUsername())
                .password(hashPassword)
                .fullName(userForm.getFullName())
                .role(Role.CASHIER)
                .state(State.ACTIVE)
                .build();
        usersRepository.save(user);

    }

    @Override
    public User signIn(UserForm userForm){
       Optional<User> user = usersRepository.findByUsername(userForm.getUsername());
       if(user.isPresent()){
           if(passwordEncoder.matches(userForm.getPassword(),user.get().getPassword())){
               System.err.println(user.get());
               if(user!=null){
                   return user.get();
               }
           }
       }
        return null;
    }

    @Override
    public TokenDto login(UserForm loginForm) {
        Optional<User> userCandidate = usersRepository.findByUsername(loginForm.getUsername());

        if (userCandidate.isPresent()) {
            User user = userCandidate.get();

            if (passwordEncoder.matches(loginForm.getPassword(), user.getPassword())) {
                Token token = Token.builder()
                        .user(user)
                        .value(RandomStringUtils.random(10, true, true))
                        .build();

                tokensRepository.save(token);
                return from(token);
            }
        } throw new IllegalArgumentException("User not found");
    }
}
