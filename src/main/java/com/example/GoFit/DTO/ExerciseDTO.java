package com.example.GoFit.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class ExerciseDTO {

    private Long id;

    @NotBlank(message = "Exercise name cannot be blank")
    @Size(min = 3, max = 50, message = "Exercise name must be between 3 and 50 characters long")
    private String name;

    public ExerciseDTO() {
    }

    public ExerciseDTO(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
