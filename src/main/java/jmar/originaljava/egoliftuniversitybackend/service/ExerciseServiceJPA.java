package jmar.originaljava.egoliftuniversitybackend.service;

import jmar.originaljava.egoliftuniversitybackend.dto.ExerciseDTO;
import jmar.originaljava.egoliftuniversitybackend.mappers.ExerciseMapper;
import jmar.originaljava.egoliftuniversitybackend.model.Exercise;
import jmar.originaljava.egoliftuniversitybackend.repository.ExerciseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;


@Service
@Primary
@RequiredArgsConstructor
public class ExerciseServiceJPA implements ExerciseService {
    private final ExerciseRepository exerciseRepository;
    private final ExerciseMapper exerciseMapper;

    @Override
    public List<ExerciseDTO> listExercises() {
        System.out.println("Get Exercises -> in ExerciseServiceJPA was called!");
        return exerciseRepository.findAll()
                .stream()
                .map(exerciseMapper::exerciseToExerciseDTO)
                .toList();
    }

    @Override
    public Optional<ExerciseDTO> getExerciseById(UUID exerciseId) {
        System.out.println("Get Exercise by Id -> in ExerciseServiceJPA was called!");
        return Optional.ofNullable(exerciseMapper.exerciseToExerciseDTO
                (exerciseRepository.findById(exerciseId)
                .orElse(null)));
    }

    @Override
    public ExerciseDTO saveNewExercise(ExerciseDTO exercise) {
        System.out.println("Save New Exercise -> in ExerciseServiceJPA was called!");
        Exercise rawExercise = exerciseMapper.exerciseDTOToExercise(exercise);
        Exercise savedExercise = exerciseRepository.save(rawExercise);
        return exerciseMapper.exerciseToExerciseDTO(savedExercise);
    }

    @Override
    public Optional<ExerciseDTO> updateExerciseById(UUID exerciseId, ExerciseDTO exercise) {
        AtomicReference<Optional<ExerciseDTO>> atomicReference = new AtomicReference<>();

        exerciseRepository.findById(exerciseId)
                .ifPresentOrElse(foundExercise -> {
                    foundExercise.setExerciseName(exercise.getExerciseName());
                    foundExercise.setCategory(exercise.getCategory());
                    foundExercise.setDifficulty(exercise.getDifficulty());
                    foundExercise.setLikability(exercise.getLikability());
                    foundExercise.setPopularity(exercise.getPopularity());
                    atomicReference.set(Optional.of(exerciseMapper
                            .exerciseToExerciseDTO(exerciseRepository.save(foundExercise))));
                }, () -> atomicReference.set(Optional.empty()));

        return atomicReference.get();
    }

    @Override
    public Boolean deleteExerciseById(UUID exerciseId) {
        if (exerciseRepository.existsById(exerciseId)) {
            exerciseRepository.deleteById(exerciseId);
            return true;
        }
        return false;
    }

    @Override
    public Optional<ExerciseDTO> updateExercisePatchById(UUID exerciseId, ExerciseDTO exercise) {
        System.out.println("Update Exercise Patch by Id -> in ExerciseServiceJPA was called!");
        AtomicReference<Optional<ExerciseDTO>> atomicReference = new AtomicReference<>();

        exerciseRepository.findById(exerciseId)
                .ifPresentOrElse(foundExercise -> {

                    if (StringUtils.hasText(exercise.getExerciseName())) {
                        foundExercise.setExerciseName(exercise.getExerciseName());
                    }

                    if (StringUtils.hasText(exercise.getCategory())) {
                        foundExercise.setCategory(exercise.getCategory());
                    }

                    if (exercise.getDifficulty() != 0.0f) {
                        foundExercise.setDifficulty(exercise.getDifficulty());
                    }

                    if (exercise.getLikability() != 0.0f) {
                        foundExercise.setLikability(exercise.getLikability());
                    }

                    if (exercise.getPopularity() != 0.0f) {
                        foundExercise.setPopularity(exercise.getPopularity());
                    }

                    atomicReference.set(Optional.of(exerciseMapper
                            .exerciseToExerciseDTO(exerciseRepository.save(foundExercise))));

                }, () -> atomicReference.set(Optional.empty()));

        return atomicReference.get();
    }
}
