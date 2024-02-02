package ru.andderv.order.TeacherOrdersApp.security;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import ru.andderv.order.TeacherOrdersApp.models.SecurityPerson;

import java.util.Collection;
import java.util.Collections;

/**
 * @author andderV
 * @date 11.11.2023 15:53
 * TeacherOrdersApp
 */
public class SecurityPersonDetails implements UserDetails {
    private final SecurityPerson person;

    public SecurityPersonDetails(SecurityPerson person) {
        this.person = person;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority(person.getRole()));
    }

    @Override
    public String getPassword() {
        return person.getUserPassword();
    }

    @Override
    public String getUsername() {
        return person.getUserName();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public SecurityPerson getPerson(){
        return this.person;
    }
}
