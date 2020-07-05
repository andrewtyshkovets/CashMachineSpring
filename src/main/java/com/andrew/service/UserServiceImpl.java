package com.andrew.service;


import com.andrew.forms.UserForm;
import com.andrew.models.Role;
import com.andrew.models.State;
import com.andrew.models.User;
import com.andrew.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class UserServiceImpl implements UserService{
    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

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

}
