package org.codecool.fitnesstracker.fitnesstracker.controller;

import org.codecool.fitnesstracker.fitnesstracker.controller.dto.AnalyticDailyDTO;
import org.codecool.fitnesstracker.fitnesstracker.service.AnalyzeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("analyze")
public class AnalyzeController {

    public final AnalyzeService analyzeService;

    @Autowired
    public AnalyzeController(AnalyzeService analyzeService) {
        this.analyzeService = analyzeService;
    }

    @GetMapping("/")
    public ResponseEntity<List<AnalyticDailyDTO>> getAnalytics(@RequestParam String duration,
                                                               @CurrentSecurityContext(expression = "authentication") Authentication authentication){
        return new ResponseEntity<>(analyzeService.listAnalyticForPeriod(duration, authentication.getName()), HttpStatus.OK);
    }
}
