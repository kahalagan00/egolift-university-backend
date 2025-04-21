package jmar.originaljava.egoliftuniversitybackend.service;

import jmar.originaljava.egoliftuniversitybackend.dto.ExerciseDTO;
import jmar.originaljava.egoliftuniversitybackend.mappers.ExerciseMapper;
import jmar.originaljava.egoliftuniversitybackend.model.Exercise;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;

@Slf4j
@Service
public class ExerciseServiceImpl implements ExerciseService {
    private Map<UUID, ExerciseDTO> exercisesMap;
    private final ExerciseMapper exerciseMapper;


    public ExerciseServiceImpl(ExerciseMapper exerciseMapper, Map<UUID, ExerciseDTO> exercises) {
        this.exerciseMapper = exerciseMapper;
        this.exercisesMap = new HashMap<>();

        System.out.println("Initialized ExerciseServiceImpl");

        ExerciseDTO exercise1 = ExerciseDTO.builder()
                .exerciseName("Pull Ups")
                .category("Pull")
                .difficulty(5)
                .likability(.7f)
                .popularity(.3f)
                .build();

        ExerciseDTO exercise2 = ExerciseDTO.builder()
                .exerciseName("Bench Press")
                .category("Push")
                .difficulty(4)
                .likability(.99f)
                .popularity(.85f)
                .build();

        ExerciseDTO exercise3 = ExerciseDTO.builder()
                .exerciseName("Squats")
                .category("Legs")
                .difficulty(5)
                .likability(.2f)
                .popularity(.9f)
                .build();

        exercisesMap.put(exercise1.getId(), exercise1);
        exercisesMap.put(exercise2.getId(), exercise2);
        exercisesMap.put(exercise3.getId(), exercise3);
    }

    @Override
    public List<ExerciseDTO> listExercises() {
        System.out.println("Get Exercises -> in ExerciseServiceImpl was called!");
        return new ArrayList<>(this.exercisesMap.values());
    }

    @Override
    public Optional<ExerciseDTO> getExerciseById(UUID exerciseId) {
        System.out.println("Get Exercise by Id -> in ExerciseServiceImpl was called!");
        ExerciseDTO exerciseDTO = exercisesMap.get(exerciseId);
        if (exerciseDTO == null) return Optional.empty();
        return Optional.of(exerciseDTO);
    }

    @Override
    public ExerciseDTO saveNewExercise(ExerciseDTO exercise) {
        System.out.println("Save New Exercise -> in ExerciseServiceImpl was called!");
        ExerciseDTO savedExercise = ExerciseDTO.builder()
                .id(UUID.randomUUID())
                .exerciseName(exercise.getExerciseName())
                .category(exercise.getCategory())
                .difficulty(exercise.getDifficulty())
                .likability(exercise.getLikability())
                .popularity(exercise.getPopularity())
                .build();

        exercisesMap.put(savedExercise.getId(), savedExercise);

        return savedExercise;
    }

    @Override
    public Optional<ExerciseDTO> updateExerciseById(UUID exerciseId, ExerciseDTO exercise) {
        ExerciseDTO existingExercise = exercisesMap.get(exerciseId);
        if (existingExercise == null) return Optional.empty();

        existingExercise.setExerciseName(exercise.getExerciseName());
        existingExercise.setCategory(exercise.getCategory());
        existingExercise.setDifficulty(exercise.getDifficulty());
        existingExercise.setLikability(exercise.getLikability());
        existingExercise.setPopularity(exercise.getPopularity());

        return Optional.of(existingExercise);
    }

    @Override
    public Boolean deleteExerciseById(UUID exerciseId) {
        exercisesMap.remove(exerciseId);
        return true;
    }

    @Override
    public Optional<ExerciseDTO> updateExercisePatchById(UUID exerciseId, ExerciseDTO exercise) {
        ExerciseDTO existingExercise = exercisesMap.get(exerciseId);
        if (existingExercise == null) return Optional.empty();

        if (StringUtils.hasText(exercise.getExerciseName())) {
            existingExercise.setExerciseName(exercise.getExerciseName());
        }

        if (StringUtils.hasText(exercise.getCategory())) {
            existingExercise.setCategory(exercise.getCategory());
        }

        if (exercise.getDifficulty() != 0.0f) {
            existingExercise.setDifficulty(exercise.getDifficulty());
        }

        if (exercise.getLikability() != 0.0f) {
            existingExercise.setLikability(exercise.getLikability());
        }

        if (exercise.getPopularity() != 0.0f) {
            existingExercise.setPopularity(exercise.getPopularity());
        }

        return Optional.of(existingExercise);

    }
}
