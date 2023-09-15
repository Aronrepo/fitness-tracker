package org.codecool.fitnesstracker.fitnesstracker.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
@Controller
public class ViewController {

//        @GetMapping("/")
//        public String index() {
//            return "index"; // Return the name of the HTML file (without the extension)
//        }



    @GetMapping("/about")
    public String getAbout() {
        return "index";
    }
}
