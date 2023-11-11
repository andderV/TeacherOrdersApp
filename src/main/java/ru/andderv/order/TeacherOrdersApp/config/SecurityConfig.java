package ru.andderv.order.TeacherOrdersApp.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import ru.andderv.order.TeacherOrdersApp.security.AuthProviderImpl;

/**
 * @author andderV
 * @date 11.11.2023 16:12
 * TeacherOrdersApp
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private final AuthProviderImpl authProvider;

    @Autowired
    public SecurityConfig(AuthProviderImpl authProvider) {
        this.authProvider = authProvider;
    }
}
