package ru.andderv.order.TeacherOrdersApp.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import ru.andderv.order.TeacherOrdersApp.services.SecurityPersonService;

import java.util.Collections;

/**
 * @author andderV
 * @date 11.11.2023 16:07
 * TeacherOrdersApp
 */
@Component
public class AuthProviderImpl implements AuthenticationProvider {
    private final SecurityPersonService securityPersonService;

    @Autowired
    public AuthProviderImpl(SecurityPersonService securityPersonService) {
        this.securityPersonService = securityPersonService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String name = authentication.getName();
        UserDetails personDetails = securityPersonService.loadUserByUsername(name);
        String password = authentication.getCredentials().toString();

        if (!password.equals(personDetails.getPassword())) {
            throw new BadCredentialsException("Неверный пароль");
        }
        return new UsernamePasswordAuthenticationToken(personDetails, password, Collections.emptyList());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return true;
    }
}
