package com.example.GoFit.controller;

import com.example.GoFit.DTO.UserRegisterDTO;
import com.example.GoFit.DTO.UserLoginDTO;
import com.example.GoFit.config.JwtTokenResolver;
import com.example.GoFit.model.User;
import com.example.GoFit.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtTokenResolver jwtTokenResolver;

    @PostMapping("/register")
    public ResponseEntity<UserRegisterDTO> registerUser(@Validated @RequestBody UserRegisterDTO userRegisterDTO) {
        try {
            UserRegisterDTO createdUser = userService.createUser(userRegisterDTO);
            return ResponseEntity.ok(createdUser);
        } catch (RuntimeException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@Validated @RequestBody UserLoginDTO userLoginDTO) {
        ResponseEntity.ok("Login successful");

        User user = userService.getUserByEmail(userLoginDTO.getUsername());


        if (user == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid credentials");
        }

        String token = jwtTokenResolver.generateJwtToken(user.getUsername());

        return ResponseEntity.ok(token);
    }

//    @GetMapping
//    public ResponseEntity<List<UserRegisterDTO>> getAllUsers() {
//        return ResponseEntity.ok(userService.getAllUsers());
//    }

    @GetMapping
    public ResponseEntity<List<UserRegisterDTO>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        List<UserRegisterDTO> userDtos = users.stream()
                .map(user -> new UserRegisterDTO(user.getId(), user.getUsername(), user.getEmail(), user.getPassword()))
                .collect(Collectors.toList());
        return ResponseEntity.ok(userDtos);
    }


    @GetMapping("/username/{username}")
    public ResponseEntity<UserRegisterDTO> getUserByUsername(@PathVariable String username) {
        return ResponseEntity.ok(userService.getUserByUsername(username));
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<User> getUserByEmail(@PathVariable String email) {
        return ResponseEntity.ok(userService.getUserByEmail(email));
    }

    @PutMapping("/username/{username}")
    public ResponseEntity<UserRegisterDTO> updateUserByUsername(@PathVariable String username, @Validated @RequestBody UserRegisterDTO userDetailsDTO) {
        try {
            UserRegisterDTO updatedUser = userService.updateUserByUsername(username, userDetailsDTO);
            return ResponseEntity.ok(updatedUser);
        } catch (RuntimeException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
    }

//    @PutMapping("/email/{email}")
//    public ResponseEntity<UserRegisterDTO> updateUserByEmail(@PathVariable String email, @Validated @RequestBody UserRegisterDTO userDetailsDTO) {
//        try {
//            UserRegisterDTO updatedUser = userService.updateUserByEmail(email, userDetailsDTO);
//            return ResponseEntity.ok(updatedUser);
//        } catch (RuntimeException e) {
//            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
//        }
//    }

    @DeleteMapping("/username/{username}")
    public ResponseEntity<Void> deleteUserByUsername(@PathVariable String username) {
        userService.deleteUserByUsername(username);
        return ResponseEntity.ok().build();
    }

//    @DeleteMapping("/email/{email}")
//    public ResponseEntity<Void> deleteUserByEmail(@PathVariable String email) {
//        userService.deleteUserByEmail(email);
//        return ResponseEntity.ok().build();
//    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public String handleValidationExceptions(MethodArgumentNotValidException ex) {
        BindingResult result = ex.getBindingResult();
        List<String> errorMessages = result.getAllErrors().stream()
                .map(error -> {
                    String fieldName = ((FieldError) error).getField();
                    String errorMessage = error.getDefaultMessage();
                    return fieldName + ": " + errorMessage;
                })
                .collect(Collectors.toList());
        return errorMessages.toString();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(RuntimeException.class)
    public String handleRuntimeExceptions(RuntimeException ex) {
        return ex.getMessage();
    }
}
