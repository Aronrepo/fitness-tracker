package org.codecool.fitnesstracker.fitnesstracker.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.codecool.fitnesstracker.fitnesstracker.auth.AuthenticationService;
import org.codecool.fitnesstracker.fitnesstracker.controller.dto.NewUserDTO;
import org.codecool.fitnesstracker.fitnesstracker.controller.dto.UserDTO;
import org.codecool.fitnesstracker.fitnesstracker.controller.dto.UserInfoDTO;
import org.codecool.fitnesstracker.fitnesstracker.user.User;
import org.codecool.fitnesstracker.fitnesstracker.exceptions.InvalidCredentialsException;
import org.codecool.fitnesstracker.fitnesstracker.user.UserRepository;
import org.codecool.fitnesstracker.fitnesstracker.exceptions.UserNotFoundException;
import org.codecool.fitnesstracker.fitnesstracker.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    List<UserDTO> users = new ArrayList<>();
//    @Value("${jwt.secret}")
//    private String jwtSecret;
//    @Value("${jwt.expiration}")
//    private int jwtExpiration;

    public List<UserDTO> getAllUsers() {
        return users;
    }

    public User findUserByEmail(String userEmail) {
        Optional<User> optionalUser = userRepository.findByEmail(userEmail);
        if(optionalUser.isEmpty()) {
            throw new UserNotFoundException("User with this email does not exist" + userEmail);
        }
        return optionalUser.get();
    }


    @Transactional
    public void addUserInfo(UserInfoDTO userInfo, String userEmail) {
        User user = findUserByEmail(userEmail);
        user.setGender(userInfo.gender());
        user.setHeight(userInfo.height());
        user.setWeight(userInfo.weight());
        user.setBirthDate(userInfo.birthDate());
    }
}
