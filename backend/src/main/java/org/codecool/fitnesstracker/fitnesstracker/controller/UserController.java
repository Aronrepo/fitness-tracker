package org.codecool.fitnesstracker.fitnesstracker.controller;

import org.codecool.fitnesstracker.fitnesstracker.controller.dto.UserDTO;
import org.codecool.fitnesstracker.fitnesstracker.controller.dto.UserInfoDTO;
import org.codecool.fitnesstracker.fitnesstracker.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("user")
public class UserController {

    public final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/all")
    public List<UserDTO> getAllUsers() {
        return userService.getAllUsers();
    }

    @PutMapping()
    public void getUserInfo(@CurrentSecurityContext(expression = "authentication") Authentication authentication,
                            @RequestBody UserInfoDTO userInfo
    ) {
        userService.addUserInfo(userInfo, authentication.getName());
    }
}
