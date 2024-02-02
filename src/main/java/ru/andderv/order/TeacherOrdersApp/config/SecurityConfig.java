package ru.andderv.order.TeacherOrdersApp.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import ru.andderv.order.TeacherOrdersApp.services.SecurityPersonService;

/**
 * @author andderV
 * @date 11.11.2023 16:12
 * TeacherOrdersApp
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private final SecurityPersonService personService;

    @Autowired
    public SecurityConfig(SecurityPersonService personService) {
        this.personService = personService;
    }

    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((auth) ->
                        auth.requestMatchers("/adminPage").hasRole("ADMIN")
                                .requestMatchers("/auth/login", "/auth/registration", "/error")
                                .permitAll()
                                .anyRequest().hasAnyRole("ADMIN", "USER")
                                )
                .formLogin(formLogin -> formLogin.loginPage("/auth/login")
                        .loginProcessingUrl("/process-login")
                        .defaultSuccessUrl("/index", true)
                        .failureUrl("/auth/login?error=true"))
                .logout(logout -> logout.logoutUrl("/logout").logoutSuccessUrl("/auth/login"));
        return http.build();
    }

    @Autowired
    void configure(AuthenticationManagerBuilder builder) throws Exception {
        builder.userDetailsService(personService).passwordEncoder(passwordEncoder());
    }


}
