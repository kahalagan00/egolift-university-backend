package jmar.originaljava.egoliftuniversitybackend.service;

import jmar.originaljava.egoliftuniversitybackend.dto.UserDTO;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserService {
    List<UserDTO> listUsers();
    Optional<UserDTO> getUserById(UUID userId);
    UserDTO saveNewUser(UserDTO user);
    Optional<UserDTO> updateUserById(UUID userId, UserDTO user);
    Boolean deleteUserById(UUID userId);
    Optional<UserDTO> updateUserPatchById(UUID userId, UserDTO user);
}
