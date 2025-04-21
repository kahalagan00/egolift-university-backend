package jmar.originaljava.egoliftuniversitybackend.repository;

import jmar.originaljava.egoliftuniversitybackend.model.Exercise;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ExerciseRepository extends JpaRepository<Exercise, UUID> {
}
