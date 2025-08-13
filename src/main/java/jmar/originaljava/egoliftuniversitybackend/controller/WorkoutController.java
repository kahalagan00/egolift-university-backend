package jmar.originaljava.egoliftuniversitybackend.controller;


import jmar.originaljava.egoliftuniversitybackend.model.Workout;
import jmar.originaljava.egoliftuniversitybackend.service.WorkoutService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@CrossOrigin(origins = "http://localhost:5173") // For development only
public class WorkoutController {
    public static final String WORKOUT_PATH = "/api/v1/workout";
    public static final String WORKOUT_PATH_ID = WORKOUT_PATH + "/{workoutId}";
    private final WorkoutService workoutService;

    @GetMapping(value = WORKOUT_PATH)
    public List<Workout> listWorkouts() {
        System.out.println("Get Workouts -> in Controller was called");
        return workoutService.listWorkouts();
    }

}
