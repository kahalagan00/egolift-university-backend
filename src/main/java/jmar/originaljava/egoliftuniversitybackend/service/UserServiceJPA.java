package jmar.originaljava.egoliftuniversitybackend.service;

import jmar.originaljava.egoliftuniversitybackend.dto.UserCreateDTO;
import jmar.originaljava.egoliftuniversitybackend.dto.UserDTO;
import jmar.originaljava.egoliftuniversitybackend.mappers.UserMapper;
import jmar.originaljava.egoliftuniversitybackend.model.Exercise;
import jmar.originaljava.egoliftuniversitybackend.model.User;
import jmar.originaljava.egoliftuniversitybackend.repository.ExerciseRepository;
import jmar.originaljava.egoliftuniversitybackend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
@Primary
@RequiredArgsConstructor
public class UserServiceJPA implements UserService {
    private final UserRepository userRepository;
    private final ExerciseRepository exerciseRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;


    @Override
    public List<UserDTO> listUsers() {
        System.out.println("Get Users -> in ServiceJPA was called!");
        return userRepository.findAll()
                .stream()
                .map(userMapper::userToUserDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<UserDTO> getUserById(UUID userId) {
        return Optional.ofNullable(userMapper.userToUserDTO
                (userRepository.findById(userId)
                        .orElse(null)));
    }

    @Override
    public UserDTO saveNewUser(UserCreateDTO userCreateDTO) {
        // Hash the password before saving to DB
        User rawUser = userMapper.userCreateDTOToUser(userCreateDTO);

        if (userCreateDTO.getExerciseId() != null && !userCreateDTO.getExerciseId().isEmpty()) {
            Exercise favoriteExercise = exerciseRepository.findById(UUID.fromString(userCreateDTO.getExerciseId()))
                    .orElse(null);

            rawUser.setFavoriteExercise(favoriteExercise);
        }

        rawUser.setPassword(passwordEncoder.encode(rawUser.getPassword()));
        User savedUser = userRepository.save(rawUser);
        return userMapper.userToUserDTO(savedUser);
    }

    @Override
    public Optional<UserDTO> updateUserById(UUID userId, UserCreateDTO user) {
        AtomicReference<Optional<UserDTO>> atomicReference = new AtomicReference<>();

        userRepository.findById(userId)
                .ifPresentOrElse(foundUser -> {
                    foundUser.setPassword(passwordEncoder.encode(user.getPassword()));
                    foundUser.setFirstName(user.getFirstName());
                    foundUser.setLastName(user.getLastName());
                    foundUser.setEmail(user.getEmail());
                    foundUser.setPhone(user.getPhone());
                    foundUser.setAddress(user.getAddress());
                    foundUser.setCity(user.getCity());
                    foundUser.setCountry(user.getCountry());
                    foundUser.setWeight(user.getWeight());
                    foundUser.setHeight(user.getHeight());

                    if (user.getExerciseId() != null && !user.getExerciseId().isEmpty()) {
                        Exercise favoriteExercise = exerciseRepository.findById(UUID.fromString(user.getExerciseId()))
                                .orElse(null);

                        foundUser.setFavoriteExercise(favoriteExercise);
                    }

                    atomicReference.set(Optional.of(userMapper
                            .userToUserDTO(userRepository.save(foundUser))));
                }, () -> atomicReference.set(Optional.empty()));

        return atomicReference.get();
    }

    @Override
    public Boolean deleteUserById(UUID userId) {
        if (userRepository.existsById(userId)) {
            userRepository.deleteById(userId);
            return true;
        }
        return false;
    }

    @Override
    public Optional<UserDTO> updateUserPatchById(UUID userId, UserCreateDTO user) {
        AtomicReference<Optional<UserDTO>> atomicReference = new AtomicReference<>();

        userRepository.findById(userId)
                .ifPresentOrElse(foundUser -> {
                    if (StringUtils.hasText(user.getEmail())) {
                        foundUser.setEmail(user.getEmail());
                    }

                    if (StringUtils.hasText(user.getPassword())) {
                        foundUser.setPassword(passwordEncoder.encode(user.getPassword()));
                    }

                    if (StringUtils.hasText(user.getFirstName())) {
                        foundUser.setFirstName(user.getFirstName());
                    }

                    if (StringUtils.hasText(user.getLastName())) {
                        foundUser.setLastName(user.getLastName());
                    }

                    if (user.getBirthDate() != null) {
                        foundUser.setBirthDate(user.getBirthDate());
                    }

                    if (StringUtils.hasText(user.getPhone())) {
                        foundUser.setPhone(user.getPhone());
                    }

                    if (StringUtils.hasText(user.getAddress())) {
                        foundUser.setAddress(user.getAddress());
                    }

                    if (StringUtils.hasText(user.getCity())) {
                        foundUser.setCity(user.getCity());
                    }

                    if (StringUtils.hasText(user.getCountry())) {
                        foundUser.setCountry(user.getCountry());
                    }

                    if (user.getHeight() != 0.0f) {
                        foundUser.setHeight(user.getHeight());
                    }

                    if (user.getWeight() != 0.0f) {
                        foundUser.setWeight(user.getWeight());
                    }

                    if (user.getExerciseId() != null && !user.getExerciseId().isEmpty()) {
                        Exercise favoriteExercise = exerciseRepository.findById(UUID.fromString(user.getExerciseId()))
                                .orElse(null);

                        foundUser.setFavoriteExercise(favoriteExercise);
                    }

                    atomicReference.set(Optional.of(userMapper
                            .userToUserDTO(userRepository.save(foundUser))));
                }, () -> atomicReference.set(Optional.empty()));

        return atomicReference.get();
    }
}
