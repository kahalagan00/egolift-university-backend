package jmar.originaljava.egoliftuniversitybackend.service;

import jmar.originaljava.egoliftuniversitybackend.dto.UserCreateDTO;
import jmar.originaljava.egoliftuniversitybackend.dto.UserDTO;
import jmar.originaljava.egoliftuniversitybackend.mappers.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.util.*;

@Slf4j
@Service
public class UserServiceImpl implements UserService {
    private final PasswordEncoder passwordEncoder;
    private Map<UUID, UserCreateDTO> userMap;
    private final UserMapper userMapper;

    public UserServiceImpl(UserMapper userMapper, PasswordEncoder passwordEncoder) {
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
        this.userMap = new HashMap<>();

        System.out.println("Initialized UserServiceImpl");

        UserCreateDTO user1 = UserCreateDTO.builder()
                .id(UUID.randomUUID())
                .email("email@email.com")
                .password(UUID.randomUUID().toString())
                .firstName("Jmar")
                .lastName("Fats")
                .birthDate(LocalDate.parse("2000-11-08"))
                .weight(77.1f)
                .height(1.75f)
                .build();

        UserCreateDTO user2 = UserCreateDTO.builder()
                .id(UUID.randomUUID())
                .email("email@email.com")
                .password(UUID.randomUUID().toString())
                .firstName("Larry")
                .lastName("Wheels")
                .birthDate(LocalDate.parse("1994-12-03"))
                .weight(117.9f)
                .height(1.854f)
                .build();

        UserCreateDTO user3 = UserCreateDTO.builder()
                .id(UUID.randomUUID())
                .email("email@email.com")
                .password(UUID.randomUUID().toString())
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
        System.out.println("Get Users -> in UserServiceImpl was called!");

        List<UserDTO> userDTOList = new ArrayList<>();
        for (UserCreateDTO userCreateDTO : this.userMap.values()) {
            UserDTO userDTO = this.userMapper.userCreateDTOToUserDTO(userCreateDTO);
            userDTOList.add(userDTO);
        }

        return userDTOList;
    }

    @Override
    public Optional<UserDTO> getUserById(UUID userId) {
        log.debug("Get User by id: {} in UserServiceImpl", userId);
        UserCreateDTO userCreateDTO = userMap.get(userId);

        if (userCreateDTO == null) return Optional.empty();

        UserDTO userDTO = userMapper.userCreateDTOToUserDTO(userCreateDTO);
        return Optional.of(userDTO);
    }

    @Override
    public UserDTO saveNewUser(UserCreateDTO user) {
        log.debug("Save New User: {} in UserServiceImpl", user.toString());
        UserCreateDTO savedUser = UserCreateDTO.builder()
                .id(UUID.randomUUID())
                .email(user.getEmail())
                .password(user.getPassword())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .birthDate(user.getBirthDate())
                .height(user.getHeight())
                .weight(user.getWeight())
                .build();

        userMap.put(savedUser.getId(), savedUser);

        return userMapper.userCreateDTOToUserDTO(savedUser);
    }

    @Override
    public Optional<UserDTO> updateUserById(UUID userId, UserCreateDTO user) {
        UserCreateDTO existingUser = userMap.get(userId);

        if (existingUser == null) return Optional.empty();

        existingUser.setEmail(user.getEmail());
        existingUser.setPassword(passwordEncoder.encode(user.getPassword()));
        existingUser.setFirstName(user.getFirstName());
        existingUser.setLastName(user.getLastName());
        existingUser.setBirthDate(user.getBirthDate());
        existingUser.setPhone(user.getPhone());
        existingUser.setAddress(user.getAddress());
        existingUser.setCity(user.getCity());
        existingUser.setCountry(user.getCountry());
        existingUser.setHeight(user.getHeight());
        existingUser.setWeight(user.getWeight());

        return Optional.of(userMapper.userCreateDTOToUserDTO(existingUser));
    }

    @Override
    public Boolean deleteUserById(UUID userId) {
        userMap.remove(userId);
        return true;
    }

    @Override
    public Optional<UserDTO> updateUserPatchById(UUID userId, UserCreateDTO user) {
        UserCreateDTO existingUser = userMap.get(userId);

        if (existingUser == null) return Optional.empty();


        if (StringUtils.hasText(user.getEmail())) {
            existingUser.setEmail(user.getEmail());
        }

        if (StringUtils.hasText(user.getPassword())) {
            existingUser.setPassword(passwordEncoder.encode(user.getPassword()));
        }


        if (StringUtils.hasText(user.getFirstName())) {

            existingUser.setFirstName(user.getFirstName());
        }

        if (StringUtils.hasText(user.getLastName())) {

            existingUser.setLastName(user.getLastName());
        }

        if (StringUtils.hasText(user.getBirthDate().toString())) {

            existingUser.setBirthDate(user.getBirthDate());
        }


        if (StringUtils.hasText(user.getPhone())) {

            existingUser.setPhone(user.getPhone());
        }

        if (StringUtils.hasText(user.getAddress())) {

            existingUser.setAddress(user.getAddress());
        }


        if (StringUtils.hasText(user.getCity())) {

            existingUser.setCity(user.getCity());
        }

        if (StringUtils.hasText(user.getCountry())) {

            existingUser.setCountry(user.getCountry());
        }

        if (user.getHeight() != 0.0f) {

            existingUser.setHeight(user.getHeight());
        }

        if (user.getWeight() != 0.0f) {

            existingUser.setWeight(user.getWeight());
        }

        return Optional.of(userMapper.userCreateDTOToUserDTO(existingUser));
    }
}
