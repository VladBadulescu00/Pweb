package com.example.GoFit.repository;

import  com.example.GoFit.model.DietPlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DietPlanRepository extends JpaRepository<DietPlan, Long> {
    // Custom query methods can be defined here
}
