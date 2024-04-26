package com.example.GoFit.DTO;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;

import java.time.LocalDate;

public class ProfileDTO {
    @NotNull(message = "Birthdate cannot be null")
    @Past(message = "Birthdate must be a date in the past")
    private LocalDate birthdate;

    @NotNull(message = "Gender cannot be null")
    @Pattern(regexp = "Male|Female|Other", message = "Gender must be 'Male', 'Female', or 'Other'")
    private String gender;

    public ProfileDTO() {
    }

    public ProfileDTO(LocalDate birthdate, String gender) {
        this.birthdate = birthdate;
        this.gender = gender;
    }

    public LocalDate getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(LocalDate birthdate) {
        this.birthdate = birthdate;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
