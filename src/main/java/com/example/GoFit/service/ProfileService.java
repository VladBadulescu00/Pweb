package com.example.GoFit.service;

import com.example.GoFit.DTO.ProfileDTO;
import com.example.GoFit.model.Profile;
import com.example.GoFit.model.User;
import com.example.GoFit.repository.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProfileService {

    @Autowired
    private ProfileRepository profileRepository;

    public void createUserProfile(User user, ProfileDTO profileDTO) {
        Profile profile = new Profile();
        profile.setUser(user);
        profile.setGender(profileDTO.getGender());
        profile.setBirthdate(profileDTO.getBirthdate());
        profileRepository.save(profile);
    }

    private Profile convertToEntity(ProfileDTO dto) {
        Profile profile = new Profile();
        profile.setBirthdate(dto.getBirthdate());
        profile.setGender(dto.getGender());
        return profile;
    }

    private ProfileDTO convertToDTO(Profile profile) {
        ProfileDTO dto = new ProfileDTO();
        dto.setBirthdate(profile.getBirthdate());
        dto.setGender(profile.getGender());
        return dto;
    }
}
