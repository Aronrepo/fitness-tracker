package org.codecool.fitnesstracker.fitnesstracker.config;

import jakarta.servlet.Filter;
import lombok.RequiredArgsConstructor;
import org.codecool.fitnesstracker.fitnesstracker.user.Authorities;
import org.codecool.fitnesstracker.fitnesstracker.user.Role;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.http.HttpMethod.*;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

    private final JwtAuthenticationFilter jwtAuthFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(CsrfConfigurer::disable)
                .authorizeHttpRequests(auth ->
                        auth
                                .requestMatchers("/api/v1/auth/**",
                                        "/",
                                        "/calorie",
                                        "/about",
                                        "/manifest.json",
                                        "/favicon.ico",
                                        "/logo192.png",
                                        "/logo512.png",
                                        "http://localhost:3000/static/media/aboutVideo.b4d390a94f17f55db39b.mp4",
                                        "/static/**",
                                        "/templates/**",
                                        "/profile",
                                        "/yourDailyCalorie",
                                        "/yourDailyActivity",
                                        "/analyzeUser",
                                        "/activity",
                                        "/index.html"
                                )
                                .permitAll()
                                .requestMatchers(POST,"/activities/").hasAuthority(Authorities.SET_ACTIVITIES.name())
                                .requestMatchers(GET,"/activities/").hasAuthority(Authorities.GET_ACTIVITIES.name())
                                .requestMatchers(POST,"/calories/").hasAuthority(Authorities.SET_CALORIES.name())
                                .requestMatchers(GET,"/calories/").hasAuthority(Authorities.GET_CALORIES.name())
                                .requestMatchers(GET,"/analyze/").hasAuthority(Authorities.GET_ANALYZE.name())
                                .requestMatchers(PUT,"/user/").hasAuthority(Authorities.CHANGE_USER.name())
                                .requestMatchers(PUT,"/foodtype/search").hasAuthority(Authorities.CHANGE_USER.name())
                                .anyRequest()
                                .authenticated()

                )
                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
