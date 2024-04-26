package com.example.GoFit.service;

import com.example.GoFit.DTO.ExerciseDTO;
import com.example.GoFit.model.Exercise;
import com.example.GoFit.repository.ExerciseRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ExerciseService {

    @Autowired
    private ExerciseRepository exerciseRepository;

    @Transactional
    public ExerciseDTO createExercise(ExerciseDTO exerciseDTO) {
        Exercise exercise = new Exercise();
        exercise.setName(exerciseDTO.getName());

        Exercise savedExercise = exerciseRepository.save(exercise);
        return convertExerciseToDTO(savedExercise);
    }
    

    public List<ExerciseDTO> getAllExercises() {
        return exerciseRepository.findAll().stream().map(this::convertExerciseToDTO).collect(Collectors.toList());
    }

    public ExerciseDTO getExerciseById(Long id) {
        Exercise exercise = exerciseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Exercise not found"));
        return convertExerciseToDTO(exercise);
    }


    public Exercise updateExercise(Long id, Exercise exerciseDetails) {
        Exercise exercise = exerciseRepository.findById(id).orElseThrow(() -> new RuntimeException("Exercise not found"));
        exercise.setName(exerciseDetails.getName());
        return exerciseRepository.save(exercise);
    }

    public void deleteExercise(Long id) {
        Exercise exercise = exerciseRepository.findById(id).orElseThrow(() -> new RuntimeException("Exercise not found"));
        exerciseRepository.delete(exercise);
    }

    private ExerciseDTO convertExerciseToDTO(Exercise exercise) {
        if (exercise == null) {
            return null;
        }
        ExerciseDTO exerciseDTO = new ExerciseDTO();
        exerciseDTO.setId(exercise.getId());
        exerciseDTO.setName(exercise.getName());
        return exerciseDTO;
    }

}
