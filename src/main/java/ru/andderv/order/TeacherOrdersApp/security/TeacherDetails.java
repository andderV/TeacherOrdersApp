package ru.andderv.order.TeacherOrdersApp.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ru.andderv.order.TeacherOrdersApp.models.Teacher;

import java.util.Collection;
import java.util.Collections;

/**
 * @author andderV
 * @date 11.11.2023 15:53
 * TeacherOrdersApp
 */
public class TeacherDetails implements UserDetails {
//    private final SecurityPerson person;
    private final Teacher teacher;


    public TeacherDetails(Teacher teacher) {
        this.teacher = teacher;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority(teacher.getRole()));
    }

    @Override
    public String getPassword() {
        return teacher.getUserPassword();
    }

    @Override
    public String getUsername() {
        return teacher.getUserName();
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

    public Teacher getTeacher(){
        return this.teacher;
    }
}
