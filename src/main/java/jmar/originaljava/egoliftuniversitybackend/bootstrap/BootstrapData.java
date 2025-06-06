package jmar.originaljava.egoliftuniversitybackend.bootstrap;

import jakarta.transaction.Transactional;
import jmar.originaljava.egoliftuniversitybackend.dto.UserDTO;
import jmar.originaljava.egoliftuniversitybackend.model.Exercise;
import jmar.originaljava.egoliftuniversitybackend.model.User;
import jmar.originaljava.egoliftuniversitybackend.repository.ExerciseRepository;
import jmar.originaljava.egoliftuniversitybackend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class BootstrapData implements CommandLineRunner {
    private final UserRepository userRepository;
    private final ExerciseRepository exerciseRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    @Override
    public void run(String... args) throws Exception {
        // Executes on application startup to initialize sample data
        loadUserData();
        loadExerciseData();
    }

    private void loadExerciseData() {
        if (exerciseRepository.count() > 0) {
            return;
        }

        Exercise exercise1 = Exercise.builder()
                .exerciseName("Pull Ups")
                .category("Pull")
                .difficulty(5)
                .likability(.7f)
                .popularity(.3f)
                .build();

        Exercise exercise2 = Exercise.builder()
                        .exerciseName("Bench Press")
                        .category("Push")
                        .difficulty(4)
                        .likability(.99f)
                        .popularity(.85f)
                        .build();

        Exercise exercise3 = Exercise.builder()
                        .exerciseName("Squats")
                        .category("Legs")
                        .difficulty(5)
                        .likability(.2f)
                        .popularity(.9f)
                        .build();

        exerciseRepository.saveAll(Arrays.asList(exercise1, exercise2, exercise3));

    }

    private void loadUserData() {
        // Creates and saves sample user data to the database if no users exist
        if (userRepository.count() > 0) {
            return;
        }

        User user1 = User.builder()
                .password(passwordEncoder.encode("jmarFattboipapassword"))
                .email("jmar@email.com")
                .firstName("Jmar")
                .lastName("Fattyboi")
                .birthDate(LocalDate.parse("2000-11-08"))
                .weight(77.1f)
                .height(1.75f)
                .build();

        User user2 = User.builder()
                .password(passwordEncoder.encode("larryTrenwheelsapdsah1lpassword"))
                .email("larry@email.com")
                .firstName("Larry")
                .lastName("Trenwheels")
                .birthDate(LocalDate.parse("1994-12-03"))
                .weight(117.9f)
                .height(1.854f)
                .build();

        User user3 = User.builder()
                .password(passwordEncoder.encode("chechenwarriorpassword123"))
                .email("khamzat@email.com")
                .firstName("Khamzat (Borz)")
                .lastName("Chimaev")
                .birthDate(LocalDate.parse("1994-05-01"))
                .weight(84f)
                .height(1.88f)
                .build();

        userRepository.saveAll(Arrays.asList(user1, user2, user3));
    }
}
