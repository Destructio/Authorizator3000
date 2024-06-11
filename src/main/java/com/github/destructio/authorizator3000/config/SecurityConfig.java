package com.github.destructio.authorizator3000.config;

import com.github.destructio.authorizator3000.service.JpaOAuth2UserService;
import com.github.destructio.authorizator3000.service.JpaOidcUserService;
import com.github.destructio.authorizator3000.service.JpaUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final JpaOAuth2UserService jpaOAuth2UserService;
    private final JpaOidcUserService jpaOidcUserService;
    private final JpaUserDetailsService jpaUserDetailsService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests((authorize) -> authorize
                        .requestMatchers("/login").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/users").permitAll()
                        .requestMatchers("/swagger-ui/**", "/v3/api-docs/**", "/swagger-ui.html").permitAll()
                        .anyRequest().authenticated()
                )
                .oauth2Login(oauth2 -> oauth2
                        .userInfoEndpoint(user -> user
                                .userService(jpaOAuth2UserService)
                                .oidcUserService(jpaOidcUserService)
                        )
                )
                .userDetailsService(jpaUserDetailsService)
                .formLogin(withDefaults())
        ;

        return http.build();
    }

}