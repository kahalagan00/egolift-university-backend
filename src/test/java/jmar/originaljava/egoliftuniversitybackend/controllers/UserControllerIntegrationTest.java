package jmar.originaljava.egoliftuniversitybackend.controllers;


import com.fasterxml.jackson.databind.ObjectMapper;
import jmar.originaljava.egoliftuniversitybackend.config.SecurityConfig;
import jmar.originaljava.egoliftuniversitybackend.controller.UserController;
import jmar.originaljava.egoliftuniversitybackend.dto.UserDTO;
import jmar.originaljava.egoliftuniversitybackend.mappers.UserMapper;
import jmar.originaljava.egoliftuniversitybackend.mappers.UserMapperImpl;
import jmar.originaljava.egoliftuniversitybackend.service.UserService;
import jmar.originaljava.egoliftuniversitybackend.service.UserServiceImpl;
import jmar.originaljava.egoliftuniversitybackend.service.UserServiceJPA;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.http.MediaType;

import java.util.Optional;

import static org.hamcrest.core.Is.is;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
@Import({UserMapperImpl.class, SecurityConfig.class})
class UserControllerIntegrationTest {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockitoBean
    UserService userService;

    UserServiceImpl userServiceImpl;

    @Autowired
    UserMapper userMapper;

    @MockitoBean
    PasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        userServiceImpl = new UserServiceImpl(userMapper, passwordEncoder);
    }

    // Why is this not working?
    @Test
    void testGetUserById() throws Exception {
        UserDTO testUser = userServiceImpl.listUsers().getFirst();

        System.out.println(testUser.toString());

        given(userService.getUserById(testUser.getId())).willReturn(Optional.of(testUser));

        mockMvc.perform(get(UserController.USER_PATH_ID, testUser.getId())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(testUser.getId().toString())))
                .andExpect(jsonPath("$.firstName", is(testUser.getFirstName())));

    }

}