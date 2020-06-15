package com.andrew.service;

import com.andrew.forms.UserForm;
import com.andrew.models.User;
import com.andrew.transfer.TokenDto;

import java.util.Optional;

public interface UserService {

    void signUp(UserForm userForm);

    User signIn(UserForm userForm);

    TokenDto login(UserForm loginForm);
}
