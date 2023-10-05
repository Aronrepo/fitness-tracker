package org.codecool.fitnesstracker.fitnesstracker.config;

import jakarta.servlet.Filter;
import lombok.RequiredArgsConstructor;
import org.codecool.fitnesstracker.fitnesstracker.user.Role;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.http.HttpMethod.*;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth ->
                        auth
                                .requestMatchers(
                                        "/",
                                        "/calorie",
                                        "/about",
                                        "/manifest.json",
                                        "/favicon.ico",
                                        "/logo192.png",
                                        "/logo512.png",
                                        "http://localhost:3000/static/media/aboutVideo.b4d390a94f17f55db39b.mp4",
                                        "/api/v1/auth/**",
                                        "/static/**",
                                        "/templates/**",
                                        "/profile",
                                        "/yourDailyCalorie",
                                        "/yourDailyActivity",
                                        "/analyzeUser",
                                        "/activity",
                                        "/index.html")
                                .permitAll()
                                .requestMatchers(POST,"/activities/").hasRole(Role.USER.name())
                                .requestMatchers(GET,"/activities/").hasRole(Role.USER.name())
                                .requestMatchers(POST,"/calories/").hasRole(Role.USER.name())
                                .requestMatchers(GET,"/calories/").hasRole(Role.USER.name())
                                .requestMatchers(GET,"/analyze/").hasRole(Role.USER.name())
                                .requestMatchers(PUT,"/user/").hasRole(Role.USER.name())
                                .anyRequest()
                                .authenticated()

                )
                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
