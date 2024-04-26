package com.example.GoFit.service;

import com.example.GoFit.DTO.ExerciseDTO;
import com.example.GoFit.DTO.WorkoutDTO;
import com.example.GoFit.model.Exercise;
import com.example.GoFit.model.User;
import com.example.GoFit.model.Workout;
import com.example.GoFit.repository.UserRepository;
import com.example.GoFit.repository.WorkoutRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class WorkoutService {

    @Autowired
    private WorkoutRepository workoutRepository;

    @Autowired
    private UserRepository userRepository;



    @Transactional
    public WorkoutDTO createWorkout(WorkoutDTO workoutDTO) {

        Workout workout = new Workout();
        workout.setDate(workoutDTO.getDate());
        workout.setTitle(workoutDTO.getTitle());

        Workout savedWorkout = workoutRepository.save(workout);
        return convertToDTO(savedWorkout);
    }


    public List<WorkoutDTO> getAllWorkouts() {
        return workoutRepository.findAll().stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public WorkoutDTO getWorkoutById(Long id) {
        Workout workout = workoutRepository.findById(id).orElseThrow(() -> new RuntimeException("Workout not found"));
        WorkoutDTO workoutDTO = new WorkoutDTO();
        workoutDTO.setId(workout.getId());
        workoutDTO.setDate(workout.getDate());
        workoutDTO.setTitle(workout.getTitle());
        return workoutDTO;
    }

    public Workout updateWorkout(Long id, Workout workoutDetails) {
        Workout workout = workoutRepository.findById(id).orElseThrow(() -> new RuntimeException("Workout not found"));
        workout.setDate(workoutDetails.getDate());
        workout.setTitle(workoutDetails.getTitle());
        workout.setExercises(new HashSet<>(workoutDetails.getExercises()));
        return workoutRepository.save(workout);
    }


    public void deleteWorkout(Long id) {
        Workout workout = workoutRepository.findById(id).orElseThrow(() -> new RuntimeException("Workout not found"));
        workoutRepository.delete(workout);
    }

    private WorkoutDTO convertToDTO(Workout workout) {
        WorkoutDTO workoutDTO = new WorkoutDTO();
        workoutDTO.setId(workout.getId());
        workoutDTO.setDate(workout.getDate());
        workoutDTO.setTitle(workout.getTitle());
        return workoutDTO;
    }


}


