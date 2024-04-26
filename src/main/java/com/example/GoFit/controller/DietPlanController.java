package com.example.GoFit.controller;

import com.example.GoFit.DTO.DietPlanDTO;
import com.example.GoFit.DTO.WorkoutDTO;
import com.example.GoFit.model.DietPlan;
import com.example.GoFit.service.DietPlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/dietplan")
public class DietPlanController {

    @Autowired
    private DietPlanService dietPlanService;

    @PostMapping
    public ResponseEntity<DietPlanDTO> createDietPlan(@RequestBody DietPlanDTO dietPlanDTO) {
        DietPlanDTO createdDietPlan = dietPlanService.createDietPlan(dietPlanDTO);
        return ResponseEntity.ok(createdDietPlan);
    }

    @GetMapping
    public ResponseEntity<List<DietPlanDTO>> getAllDietPlans() {
        return ResponseEntity.ok(dietPlanService.getAllDietPlans());
    }


    @GetMapping("{id}")
    public ResponseEntity<DietPlanDTO> getDietPlanById(@PathVariable Long id) {
        DietPlanDTO dietPlanDTO = dietPlanService.getDietPlanById(id);
        return ResponseEntity.ok(dietPlanDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DietPlan> updateDietPlan(@PathVariable Long id, @RequestBody DietPlan dietPlanDetails) {
        return ResponseEntity.ok(dietPlanService.updateDietPlan(id, dietPlanDetails));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDietPlan(@PathVariable Long id) {
        dietPlanService.deleteDietPlan(id);
        return ResponseEntity.ok().build();
    }
}




