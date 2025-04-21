package jmar.originaljava.egoliftuniversitybackend.controller;


import jmar.originaljava.egoliftuniversitybackend.dto.UserCreateDTO;
import jmar.originaljava.egoliftuniversitybackend.dto.UserDTO;
import jmar.originaljava.egoliftuniversitybackend.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@RestController
public class UserController {
    public static final String USER_PATH = "/api/v1/user";
    public static final String USER_PATH_ID = USER_PATH + "/{userId}";
    private final UserService userService;

    // Retrieves a list of all users in the system.
    @GetMapping(value = USER_PATH)
    public List<UserDTO> listUsers() {
        System.out.println("Get Users -> in Controller was called!");
        return userService.listUsers();
    }

    // Retrieves a specific user by their unique identifier.
    @GetMapping(value = USER_PATH_ID)
    public UserDTO getUserById(@PathVariable("userId") UUID userId) {
        log.debug("Get User by Id -> in Controller was called!");
        return userService.getUserById(userId).orElseThrow(NotFoundException::new);
    }

    // Creates a new user in the system.
    @PostMapping(USER_PATH)
    public ResponseEntity handlePost(@RequestBody UserCreateDTO userCreateDTO) {
        log.debug("Post Create User -> in Controller was called!");
        UserDTO savedUser = userService.saveNewUser(userCreateDTO);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "/api/v1/user/" + savedUser.getId().toString());
        return new ResponseEntity(headers, HttpStatus.CREATED);
    }

    // Updates all fields of an existing user.
    @PutMapping(value = USER_PATH_ID)
    public ResponseEntity updateById(@PathVariable("userId") UUID userId,
                                     @RequestBody UserCreateDTO userCreateDTO) {
        log.debug("Update User by Id -> in Controller was called!");
        if (userService.updateUserById(userId, userCreateDTO).isEmpty()) {
            throw new NotFoundException();
        }

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    // Partially updates an existing user with only the provided fields.
    @PatchMapping(value = USER_PATH_ID)
    public ResponseEntity updatePatchById(@PathVariable("userId") UUID userId,
                                          @RequestBody UserCreateDTO userCreateDTO) {
        log.debug("Update Patch User by Id -> in Controller was called!");

        if (userService.updateUserPatchById(userId, userCreateDTO).isEmpty()) {
            throw new NotFoundException();
        }

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    // Removes a user from the system by their unique identifier.
    @DeleteMapping(USER_PATH_ID)
    public ResponseEntity deleteById(@PathVariable("userId") UUID userId) {
        log.debug("Delete User by Id -> in Controller was called!");

        if (!userService.deleteUserById(userId)) {
            throw new NotFoundException();
        }

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }


}
