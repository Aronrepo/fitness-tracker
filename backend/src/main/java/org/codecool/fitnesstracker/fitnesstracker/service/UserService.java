package org.codecool.fitnesstracker.fitnesstracker.service;

import org.codecool.fitnesstracker.fitnesstracker.controller.dto.UserDTO;
import org.codecool.fitnesstracker.fitnesstracker.controller.dto.UserInfoDTO;
import org.codecool.fitnesstracker.fitnesstracker.user.User;
import org.codecool.fitnesstracker.fitnesstracker.user.UserRepository;
import org.codecool.fitnesstracker.fitnesstracker.exceptions.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    List<UserDTO> users = new ArrayList<>();


    public UserInfoDTO getUserInfo(String userEmail) {
        Optional<User> optionalUser = userRepository.findByEmail(userEmail);
        if (optionalUser.isEmpty()) {
            throw new UserNotFoundException("User with this email does not exist" + userEmail);
        }
        UserInfoDTO userInfoDTO = new UserInfoDTO(optionalUser.get().getGender(), optionalUser.get().getWeight(), optionalUser.get().getHeight(), optionalUser.get().getBirthDate());
        return userInfoDTO;
    }

    public User findUserByEmail(String userEmail) {
        Optional<User> optionalUser = userRepository.findByEmail(userEmail);
        if (optionalUser.isEmpty()) {
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
