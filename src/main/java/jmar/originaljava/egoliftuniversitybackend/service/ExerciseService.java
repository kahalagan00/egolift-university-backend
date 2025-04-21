package jmar.originaljava.egoliftuniversitybackend.service;

import jmar.originaljava.egoliftuniversitybackend.dto.ExerciseDTO;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ExerciseService {
    List<ExerciseDTO> listExercises();
    Optional<ExerciseDTO> getExerciseById(UUID exerciseId);
    ExerciseDTO saveNewExercise(ExerciseDTO exercise);
    Optional<ExerciseDTO> updateExerciseById(UUID exerciseId, ExerciseDTO exercise);
    Boolean deleteExerciseById(UUID exerciseId);
    Optional<ExerciseDTO> updateExercisePatchById(UUID exerciseId, ExerciseDTO exercise);
}
