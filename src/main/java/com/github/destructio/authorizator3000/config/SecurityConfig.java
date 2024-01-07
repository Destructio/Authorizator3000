package com.github.destructio.authorizator3000.config;

import com.github.destructio.authorizator3000.service.JpaOAuth2UserService;
import com.github.destructio.authorizator3000.service.JpaOidcUserService;
import com.github.destructio.authorizator3000.service.JpaUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {
    private final JpaOAuth2UserService jpaOAuth2UserService;
    private final JpaOidcUserService jpaOidcUserService;
    private final JpaUserDetailsService jpaUserDetailsService;

    @Autowired
    public SecurityConfig(JpaOAuth2UserService jpaOAuth2UserService, JpaOidcUserService jpaOidcUserService, JpaUserDetailsService jpaUserDetailsService) {
        this.jpaOAuth2UserService = jpaOAuth2UserService;
        this.jpaOidcUserService = jpaOidcUserService;
        this.jpaUserDetailsService = jpaUserDetailsService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests((authorize) -> authorize
                        .requestMatchers("/login").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/users").permitAll()
                        .anyRequest().authenticated()
                )
                .oauth2Login(oauth2 -> oauth2
                        .userInfoEndpoint(user -> user
                                .userService(jpaOAuth2UserService)
                                .oidcUserService(jpaOidcUserService)
                        )
                )
                .userDetailsService(jpaUserDetailsService)
                .httpBasic(withDefaults())
                .formLogin(withDefaults())
        ;

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}