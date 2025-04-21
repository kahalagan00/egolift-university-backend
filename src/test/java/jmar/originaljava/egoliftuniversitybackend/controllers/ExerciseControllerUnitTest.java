package jmar.originaljava.egoliftuniversitybackend.controllers;

import jakarta.transaction.Transactional;
import jmar.originaljava.egoliftuniversitybackend.controller.ExerciseController;
import jmar.originaljava.egoliftuniversitybackend.controller.NotFoundException;
import jmar.originaljava.egoliftuniversitybackend.dto.ExerciseDTO;
import jmar.originaljava.egoliftuniversitybackend.mappers.ExerciseMapper;
import jmar.originaljava.egoliftuniversitybackend.model.Exercise;
import jmar.originaljava.egoliftuniversitybackend.repository.ExerciseRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;


@SpringBootTest
class ExerciseControllerUnitTest {

    @Autowired
    ExerciseController exerciseController;

    @Autowired
    ExerciseRepository exerciseRepository;

    @Autowired
    ExerciseMapper exerciseMapper;

    @Autowired
    WebApplicationContext wac;

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @Test
    void testDeleteExerciseByIdNotFound() {
        assertThrows(NotFoundException.class, () -> exerciseController.deleteById(UUID.randomUUID()));
    }

    @Rollback
    @Transactional
    @Test
    void testDeleteExerciseById() {
        Exercise exercise = exerciseRepository.findAll().getFirst();
        ResponseEntity responseEntity = exerciseController.deleteById(exercise.getId());
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        assertThat(exerciseRepository.findById(exercise.getId())).isEmpty();
    }

    @Test
    void testUpdateExercisePatchByIdNotFound() {
        assertThrows(NotFoundException.class, () -> {
            exerciseController.updatePatchById(UUID.randomUUID(), ExerciseDTO.builder().build());
        });
    }

    @Rollback
    @Transactional
    @Test
    void testUpdateExercisePatchById() {
        Exercise exercise = exerciseRepository.findAll().getFirst();
        ExerciseDTO exerciseDTO = exerciseMapper.exerciseToExerciseDTO(exercise);
        exerciseDTO.setExerciseName("Weighted Dips");
        exerciseDTO.setCategory("Push");
        exerciseDTO.setDifficulty(5);
        exerciseDTO.setLikability(.33f);
        exerciseDTO.setPopularity(.88f);

        ResponseEntity responseEntity = exerciseController.updatePatchById(exerciseDTO.getId(), exerciseDTO);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);

        Exercise updatedExercise = exerciseRepository.findById(exercise.getId()).get();
        assertThat(updatedExercise.getExerciseName()).isEqualTo(exerciseDTO.getExerciseName());
    }

    @Test
    void testUpdateExerciseByIdNotFound() {
        assertThrows(NotFoundException.class, () -> {
            exerciseController.updateById(UUID.randomUUID(), ExerciseDTO.builder().build());
        });
    }

    @Rollback
    @Transactional
    @Test
    void testUpdateExerciseById() {
        Exercise exercise = exerciseRepository.findAll().getFirst();
        ExerciseDTO exerciseDTO = exerciseMapper.exerciseToExerciseDTO(exercise);
        exerciseDTO.setExerciseName("Calf Raises");
        exerciseDTO.setCategory("Legs");
        exerciseDTO.setDifficulty(4);
        exerciseDTO.setLikability(.55f);
        exerciseDTO.setPopularity(.77f);

        ResponseEntity responseEntity = exerciseController.updateById(exerciseDTO.getId(), exerciseDTO);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);

        Exercise updatedExercise = exerciseRepository.findById(exercise.getId()).get();
        assertThat(updatedExercise.getExerciseName()).isEqualTo(exerciseDTO.getExerciseName());
    }

    @Rollback
    @Transactional
    @Test
    void testSaveNewExercise() {
        ExerciseDTO exerciseDTO = ExerciseDTO.builder()
                .exerciseName("Lateral Raises")
                .category("Pull")
                .difficulty(4)
                .likability(.32f)
                .popularity(.5f)
                .build();

        ResponseEntity responseEntity = exerciseController.handlePost(exerciseDTO);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(responseEntity.getHeaders().getLocation()).isNotNull();

        String[] locationUUID = responseEntity.getHeaders().getLocation().toString().split("/");

        UUID savedUUId = UUID.fromString(locationUUID[4]);

        Exercise exercise = exerciseRepository.findById(savedUUId).get();
        assertThat(exercise).isNotNull();
    }

    @Test
    void testGetExerciseByIdNotFound() {
        assertThrows(NotFoundException.class, () -> exerciseController
                .getExerciseById(UUID.randomUUID()));
    }

    @Test
    void testGetExerciseById() {
        Exercise exercise = exerciseRepository.findAll().getFirst();
        ExerciseDTO exerciseDTO = exerciseController.getExerciseById(exercise.getId());
        assertThat(exerciseDTO).isNotNull();
    }

    @Test
    void testListExercises() {
        List<ExerciseDTO> dtos = exerciseController.listExercises();
        assertThat(dtos.size()).isEqualTo(3);
    }

    @Rollback
    @Transactional
    @Test
    void testEmptyList() {
        exerciseRepository.deleteAll();
        List<ExerciseDTO> dtos = exerciseController.listExercises();
        assertThat(dtos.size()).isEqualTo(0);
    }
}
