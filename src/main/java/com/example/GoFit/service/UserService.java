package com.example.GoFit.service;

import com.example.GoFit.DTO.UserRegisterDTO;
import com.example.GoFit.model.User;
import com.example.GoFit.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    public UserRegisterDTO createUser(UserRegisterDTO userRegisterDTO) {
        User user = convertToEntity(userRegisterDTO);
        User savedUser = userRepository.save(user);
        return convertToDTO(savedUser);
    }

    private User convertToEntity(UserRegisterDTO dto) {
        User user = new User();
        user.setUsername(dto.getUsername());
        user.setEmail(dto.getEmail());
        user.setPassword(dto.getPassword());
        return user;
    }

    private UserRegisterDTO convertToDTO(User user) {
        UserRegisterDTO dto = new UserRegisterDTO();
        dto.setUsername(user.getUsername());
        dto.setEmail(user.getEmail());
        return dto;
    }

//    public List<UserRegisterDTO> getAllUsers() {
//        return userRepository.findAll().stream()
//                .map(user -> modelMapper.map(user, UserRegisterDTO.class))
//                .collect(Collectors.toList());
//    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public UserRegisterDTO getUserByUsername(String username) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("User not found"));
        return modelMapper.map(user, UserRegisterDTO.class);
    }

//    public UserRegisterDTO getUserByEmail(String email) {
//        User user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));
//        return modelMapper.map(user, UserRegisterDTO.class);
//    }

    public UserRegisterDTO updateUserByUsername(String username, UserRegisterDTO userDetailsDTO) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("User not found"));
        modelMapper.map(userDetailsDTO, user);
        return modelMapper.map(userRepository.save(user), UserRegisterDTO.class);
    }

//    public UserRegisterDTO updateUserByEmail(String email, UserRegisterDTO userDetailsDTO) {
//        User user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));
//        modelMapper.map(userDetailsDTO, user);
//        return modelMapper.map(userRepository.save(user), UserRegisterDTO.class);
//    }

    public void deleteUserByUsername(String username) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("User not found"));
        userRepository.delete(user);
    }

//    public void deleteUserByEmail(String email) {
//        User user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));
//        userRepository.delete(user);
//    }


    public User getUserById(Long userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        return userOptional.orElseThrow(() -> new RuntimeException("User not found"));
    }

    public UserRegisterDTO updateUserById(Long userId, UserRegisterDTO userDetailsDTO) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        modelMapper.map(userDetailsDTO, user);
        return modelMapper.map(userRepository.save(user), UserRegisterDTO.class);
    }

    public User getUserByEmail(String username) {
        return userRepository.findByEmail(username);
    }



}
