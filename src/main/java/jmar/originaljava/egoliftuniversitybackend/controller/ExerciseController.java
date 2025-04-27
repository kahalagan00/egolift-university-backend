package jmar.originaljava.egoliftuniversitybackend.controller;

import jmar.originaljava.egoliftuniversitybackend.dto.ExerciseDTO;
import jmar.originaljava.egoliftuniversitybackend.service.ExerciseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@RestController
@CrossOrigin(origins = "http://localhost:5173") // For development only
public class ExerciseController {
    public static final String EXERCISE_PATH = "/api/v1/exercise";
    public static final String EXERCISE_PATH_ID = EXERCISE_PATH + "/{exerciseId}";
    private final ExerciseService exerciseService;

    @GetMapping(value = EXERCISE_PATH)
    public List<ExerciseDTO> listExercises() {
        System.out.println("Get Exercises -> in Controller was called!");
        return exerciseService.listExercises();
    }

    @GetMapping(value = EXERCISE_PATH_ID)
    public ExerciseDTO getExerciseById(@PathVariable("exerciseId") UUID exerciseId) {
        System.out.println("Get Exercise by Id -> in Controller was called!");
        return exerciseService.getExerciseById(exerciseId).orElseThrow(NotFoundException::new);
    }


    @PostMapping(value = EXERCISE_PATH)
    public ResponseEntity handlePost(@RequestBody ExerciseDTO exerciseDTO) {
        System.out.println("Post Create Exercise -> in Controller was called!");
        ExerciseDTO savedExercise = exerciseService.saveNewExercise(exerciseDTO);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "/api/v1/exercise/" + savedExercise.getId().toString());
        return new ResponseEntity(headers, HttpStatus.CREATED);
    }

    @PutMapping(value = EXERCISE_PATH_ID)
    public ResponseEntity updateById(@PathVariable("exerciseId") UUID exerciseId,
                                     @RequestBody ExerciseDTO exerciseDTO) {
        System.out.println("Update Exercise by Id -> in Controller was called!");
        if (exerciseService.updateExerciseById(exerciseId, exerciseDTO).isEmpty()) {
            throw new NotFoundException();
        }

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @PatchMapping(value = EXERCISE_PATH_ID)
    public ResponseEntity updatePatchById(@PathVariable("exerciseId") UUID exerciseId,
                                          @RequestBody ExerciseDTO exerciseDTO) {
        System.out.println("Update Patch Exercise by Id -> in Controller was called!");
        if (exerciseService.updateExercisePatchById(exerciseId, exerciseDTO).isEmpty()) {
            throw new NotFoundException();
        }

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping(value = EXERCISE_PATH_ID)
    public ResponseEntity deleteById(@PathVariable("exerciseId") UUID exerciseId) {
        System.out.println("Delete Exercise by Id -> in Controller was called!");
        if (!exerciseService.deleteExerciseById(exerciseId)) {
            throw new NotFoundException();
        }

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

}
