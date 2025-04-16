package jmar.originaljava.egoliftuniversitybackend.mappers;

import jmar.originaljava.egoliftuniversitybackend.dto.UserDTO;
import jmar.originaljava.egoliftuniversitybackend.model.User;
import org.mapstruct.Mapper;

@Mapper
public interface UserMapper {
    User userDTOToUser(UserDTO userDTO);
    UserDTO userToUserDTO(User user);
}
