package com.example.GoFit.repository;

import com.example.GoFit.model.Workout;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkoutRepository extends JpaRepository<Workout, Long> {
    // Custom query methods can be defined here
}
