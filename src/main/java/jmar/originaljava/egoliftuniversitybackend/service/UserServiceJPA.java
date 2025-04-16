package jmar.originaljava.egoliftuniversitybackend.service;

import jmar.originaljava.egoliftuniversitybackend.dto.UserDTO;
import jmar.originaljava.egoliftuniversitybackend.mappers.UserMapper;
import jmar.originaljava.egoliftuniversitybackend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Primary
@RequiredArgsConstructor
public class UserServiceJPA implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;


    @Override
    public List<UserDTO> listUsers() {
        return userRepository.findAll()
                .stream()
                .map(userMapper::userToUserDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<UserDTO> getUserById(UUID userId) {
        return Optional.empty();
    }

    @Override
    public UserDTO saveNewUser(UserDTO user) {
        return null;
    }

    @Override
    public Optional<UserDTO> updateUserById(UUID userId, UserDTO user) {
        return Optional.empty();
    }

    @Override
    public Boolean deleteUserById(UUID userId) {
        return null;
    }

    @Override
    public Optional<UserDTO> updateUserPatchById(UUID userId, UserDTO user) {
        return Optional.empty();
    }
}
