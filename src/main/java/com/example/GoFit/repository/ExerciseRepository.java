package com.example.GoFit.repository;

import com.example.GoFit.model.Exercise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExerciseRepository extends JpaRepository<Exercise, Long> {
    // Custom query methods can be defined here
}
