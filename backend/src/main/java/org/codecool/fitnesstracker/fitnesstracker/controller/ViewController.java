package org.codecool.fitnesstracker.fitnesstracker.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ViewController {
    @GetMapping({"/profile", "/calorie", "/activity", "/yourDailyCalorie", "/yourDailyActivity", "/analyzeUser", "/about"})
    public String forward() {
        return "forward:/";
    }
}
