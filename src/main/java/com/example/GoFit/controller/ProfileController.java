package com.example.GoFit.controller;

import com.example.GoFit.DTO.ProfileDTO;
import com.example.GoFit.model.Profile;
import com.example.GoFit.model.User;
import com.example.GoFit.service.ProfileService;
import com.example.GoFit.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/profile")
public class ProfileController {

    @Autowired
    private ProfileService profileService;

    @Autowired
    private UserService userService;

    @PostMapping("/{userId}")
    public ResponseEntity<?> createUserProfile(@PathVariable Long userId, @RequestBody ProfileDTO profileDTO) {
        User user = userService.getUserById(userId);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }

        profileService.createUserProfile(user, profileDTO);
        return ResponseEntity.ok("Profile created successfully for user with id: " + userId);
    }


}
