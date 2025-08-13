package jmar.originaljava.egoliftuniversitybackend.repository;

import jmar.originaljava.egoliftuniversitybackend.model.Workout;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface WorkoutRepository extends JpaRepository<Workout, UUID>  {
}
