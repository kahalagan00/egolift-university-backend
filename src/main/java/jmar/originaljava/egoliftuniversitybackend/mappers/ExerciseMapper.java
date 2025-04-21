package jmar.originaljava.egoliftuniversitybackend.mappers;

import jmar.originaljava.egoliftuniversitybackend.dto.ExerciseDTO;
import jmar.originaljava.egoliftuniversitybackend.model.Exercise;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ExerciseMapper {
    ExerciseDTO exerciseToExerciseDTO(Exercise exercise);
    Exercise exerciseDTOToExercise(ExerciseDTO exerciseDTO);
}
