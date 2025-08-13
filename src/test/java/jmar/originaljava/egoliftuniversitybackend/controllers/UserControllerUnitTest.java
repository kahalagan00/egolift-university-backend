package jmar.originaljava.egoliftuniversitybackend.controllers;

import jakarta.transaction.Transactional;
import jmar.originaljava.egoliftuniversitybackend.controller.NotFoundException;
import jmar.originaljava.egoliftuniversitybackend.controller.UserController;
import jmar.originaljava.egoliftuniversitybackend.dto.UserCreateDTO;
import jmar.originaljava.egoliftuniversitybackend.dto.UserDTO;
import jmar.originaljava.egoliftuniversitybackend.mappers.UserMapper;
import jmar.originaljava.egoliftuniversitybackend.model.User;
import jmar.originaljava.egoliftuniversitybackend.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;


@SpringBootTest
class UserControllerUnitTest {

    @Autowired
    UserController userController;

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserMapper userMapper;

    @Autowired
    WebApplicationContext wac;

    MockMvc mockMvc;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @Test
    void testDeleteUserByIdNotFound() {
        assertThrows(NotFoundException.class, () -> userController.deleteById(UUID.randomUUID()));
    }

    @Rollback
    @Transactional
    @Test
    void testDeleteUserById() {
        User user = userRepository.findAll().getFirst();
        ResponseEntity responseEntity = userController.deleteById(user.getId());
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        assertThat(userRepository.findById(user.getId())).isEmpty();
    }

    @Test
    void testUpdateUserPatchByIdNotFound() {
        assertThrows(NotFoundException.class, () -> {
            userController.updatePatchById(UUID.randomUUID(), UserCreateDTO.builder().build());
        });
    }

    @Rollback
    @Transactional
    @Test
    void testUpdateUserPatchById() {
        // Implement code here. PATCH mapping
        User user = userRepository.findAll().getFirst();
        UserCreateDTO userCreateDTO = userMapper.userToUserCreateDTO(user);
        userCreateDTO.setFirstName("Steph");
        userCreateDTO.setLastName("Curry");

        ResponseEntity responseEntity = userController.updatePatchById(userCreateDTO.getId(), userCreateDTO);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);

        User updatedUser = userRepository.findById(user.getId()).get();
        assertThat(updatedUser.getFirstName()).isEqualTo(userCreateDTO.getFirstName());
    }

    @Test
    void testUpdateUserByIdNotFound() {
        assertThrows(NotFoundException.class, () -> {
            userController.updateById(UUID.randomUUID(), UserCreateDTO.builder().build());
        });
    }

    @Rollback
    @Transactional
    @Test
    void testUpdateUserById() {
        // Implement code here. PUT mapping
        User user = userRepository.findAll().getFirst();
        UserCreateDTO userCreateDTO = userMapper.userToUserCreateDTO(user);
        userCreateDTO.setFirstName("Lebron");
        userCreateDTO.setLastName("James");

        ResponseEntity responseEntity = userController.updateById(userCreateDTO.getId(), userCreateDTO);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);

        User updatedUser = userRepository.findById(user.getId()).get();
        assertThat(updatedUser.getFirstName()).isEqualTo(userCreateDTO.getFirstName());

    }

    @Rollback
    @Transactional
    @Test
    void testSaveNewUser() {
        UserCreateDTO userCreateDTO = UserCreateDTO.builder()
                .firstName("Islam")
                .lastName("Makhachev")
                .email("islamMakhachev@email.com")
                .password(passwordEncoder.encode("allhamdulilah"))
                .height(1.78f)
                .weight(70f)
                .build();

        ResponseEntity responseEntity = userController.handlePost(userCreateDTO);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(responseEntity.getHeaders().getLocation()).isNotNull();

        String[] locationUUID = responseEntity.getHeaders().getLocation().toString().split("/");

        UUID savedUUId = UUID.fromString(locationUUID[4]);

        User user = userRepository.findById(savedUUId).get();
        assertThat(user).isNotNull();

    }

    @Test
    void testUserIdNotFound() {
        assertThrows(NotFoundException.class, () -> userController
                .getUserById(UUID.randomUUID()));
    }

    @Test
    void testGetById() {
        User user = userRepository.findAll().getFirst();
        UserDTO userDTO = userController.getUserById(user.getId());
        assertThat(userDTO).isNotNull();
    }

    @Test
    void testListUsers() {
        List<UserDTO> dtos = userController.listUsers();
        assertThat(dtos.size()).isEqualTo(3);
    }

    @Rollback
    @Transactional
    @Test
    void testEmptyList() {
        userRepository.deleteAll();
        List<UserDTO> dtos = userController.listUsers();
        assertThat(dtos.size()).isEqualTo(0);
    }

}