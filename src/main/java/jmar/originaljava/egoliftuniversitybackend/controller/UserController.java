package jmar.originaljava.egoliftuniversitybackend.controller;


import jmar.originaljava.egoliftuniversitybackend.dto.UserDTO;
import jmar.originaljava.egoliftuniversitybackend.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
public class UserController {
    public static final String USER_PATH = "/api/v1/user";
    public static final String USER_PATH_ID = USER_PATH + "/{userId}";
    private final UserService userService;

    @GetMapping(value = USER_PATH)
    public List<UserDTO> listUsers() {
        return userService.listUsers();
    }
}
