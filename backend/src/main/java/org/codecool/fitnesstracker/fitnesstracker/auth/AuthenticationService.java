package org.codecool.fitnesstracker.fitnesstracker.auth;

import org.codecool.fitnesstracker.fitnesstracker.config.JwtService;
import org.codecool.fitnesstracker.fitnesstracker.user.Role;
import org.codecool.fitnesstracker.fitnesstracker.user.User;
import org.codecool.fitnesstracker.fitnesstracker.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterRequest request) {
        var user = User.builder()

                .username(request.getUsername())
                .email(request.getEmail())
                .password((passwordEncoder.encode(request.getPassword()))).role(Role.USER).build();
        repository.save(user);
        org.springframework.security.core.userdetails.UserDetails newUserDetail = org.springframework.security.core.userdetails.User
                .withUsername(user.getUsername())
                .password(user.getPassword())
                .authorities(Role.USER.name())
                .build();
        //new org.springframework.security.core.userdetails.User(user.getUsername(),user.getPassword(),);
        var jwtToken = jwtService.generateToken(newUserDetail);
        return AuthenticationResponse.builder().token(jwtToken).build();
    }


    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );
        var user = repository.findByEmail(request.getEmail()).orElseThrow(); // catch and handle the exception
        var jwtToken = jwtService.generateToken(user);

        return AuthenticationResponse.builder().token(jwtToken).build();
    }

}
