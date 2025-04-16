package jmar.originaljava.egoliftuniversitybackend.service;

import jmar.originaljava.egoliftuniversitybackend.dto.UserDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Slf4j
@Service
public class UserServiceImpl implements UserService {
    private Map<UUID, UserDTO> userMap;

    public UserServiceImpl() {
        this.userMap = new HashMap<>();

        UserDTO user1 = UserDTO.builder()
                .id(UUID.randomUUID())
                .firstName("Jmar")
                .lastName("Fats")
                .birthDate(LocalDate.parse("2000-11-08"))
                .weight(77.1f)
                .height(1.75f)
                .build();

        UserDTO user2 = UserDTO.builder()
                .id(UUID.randomUUID())
                .firstName("Larry")
                .lastName("Wheels")
                .birthDate(LocalDate.parse("1994-12-03"))
                .weight(117.9f)
                .height(1.854f)
                .build();

        UserDTO user3 = UserDTO.builder()
                .id(UUID.randomUUID())
                .firstName("Khamzat")
                .lastName("Chimaev")
                .birthDate(LocalDate.parse("1994-05-01"))
                .weight(84f)
                .height(1.88f)
                .build();

        userMap.put(user1.getId(), user1);
        userMap.put(user2.getId(), user2);
        userMap.put(user3.getId(), user3);
    }

    @Override
    public List<UserDTO> listUsers() {
        log.debug("Get Users in UserServiceImpl");
        return new ArrayList<>(userMap.values());
    }

    @Override
    public Optional<UserDTO> getUserById(UUID userId) {
        log.debug("Get User by id: {} in UserServiceImpl", userId);
        return Optional.of(this.userMap.get(userId));
    }

    @Override
    public UserDTO saveNewUser(UserDTO user) {
        log.debug("Save New User: {} in UserServiceImpl", user.toString());
        UserDTO savedUser = UserDTO.builder()
                .id(UUID.randomUUID())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .birthDate(user.getBirthDate())
                .height(user.getHeight())
                .weight(user.getWeight())
                .build();

        this.userMap.put(savedUser.getId(), savedUser);
        return savedUser;
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
