package com.example.GoFit.service;

import com.example.GoFit.DTO.DietPlanDTO;
import com.example.GoFit.model.DietPlan;
import com.example.GoFit.repository.DietPlanRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DietPlanService {

    @Autowired
    private DietPlanRepository dietPlanRepository;

    @Transactional
    public DietPlanDTO createDietPlan(DietPlanDTO dietPlanDTO) {
        DietPlan dietPlan = new DietPlan();
        dietPlan.setDescription(dietPlanDTO.getDescription());
        DietPlan savedDietPlan = dietPlanRepository.save(dietPlan);
        return convertToDTO(savedDietPlan);
    }

    private DietPlanDTO convertToDTO(DietPlan dietPlan) {
        DietPlanDTO dto = new DietPlanDTO();
        dto.setId(dietPlan.getId());
        dto.setDescription(dietPlan.getDescription());
        return dto;
    }

    private DietPlan convertToEntity(DietPlanDTO dietPlanDTO) {
        DietPlan dietPlan = new DietPlan();
        dietPlan.setDescription(dietPlanDTO.getDescription());
        return dietPlan;
    }

    public List<DietPlanDTO> getAllDietPlans() {
        List<DietPlan> dietPlans = dietPlanRepository.findAll();
        return dietPlans.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public DietPlanDTO getDietPlanById(Long id) {
        DietPlan dietPlan = dietPlanRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("DietPlan not found"));
        return convertToDTO(dietPlan);
    }

    public DietPlan updateDietPlan(Long id, DietPlan dietPlanDetails) {
        DietPlan dietPlan = dietPlanRepository.findById(id).orElseThrow(() -> new RuntimeException("DietPlan not found"));
        dietPlan.setDescription(dietPlanDetails.getDescription());
        return dietPlanRepository.save(dietPlan);
    }

    public void deleteDietPlan(Long id) {
        DietPlan dietPlan = dietPlanRepository.findById(id).orElseThrow(() -> new RuntimeException("DietPlan not found"));
        dietPlanRepository.delete(dietPlan);
    }
}
