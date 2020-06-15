package com.andrew.security.provider;

import com.andrew.models.Token;
import com.andrew.repositories.TokensRepository;
import com.andrew.security.token.TokenAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import java.util.Optional;


@Component
public class TokenAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private TokensRepository tokensRepository;

    @Qualifier("userDetailsServiceImpl")
    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        TokenAuthentication tokenAuthentication = (TokenAuthentication)authentication;

        Optional<Token> tokenCandidate = tokensRepository.findOneByValue(tokenAuthentication.getName());

        if (tokenCandidate.isPresent()) {
            UserDetails userDetails = userDetailsService.loadUserByUsername(tokenCandidate.get().getUser().getUsername());
            tokenAuthentication.setUserDetails(userDetails);
            tokenAuthentication.setAuthenticated(true);
            return tokenAuthentication;
        } else throw new IllegalArgumentException("Bad token");
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return TokenAuthentication.class.equals(authentication);
    }
}
