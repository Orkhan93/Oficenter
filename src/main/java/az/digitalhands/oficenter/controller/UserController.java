package az.digitalhands.oficenter.controller;


import az.digitalhands.oficenter.domain.User;
import az.digitalhands.oficenter.exception.UserNotFoundException;
import az.digitalhands.oficenter.exception.error.ErrorMessage;
import az.digitalhands.oficenter.repository.UserRepository;
import az.digitalhands.oficenter.request.ChangePasswordRequest;
import az.digitalhands.oficenter.request.UserRequest;
import az.digitalhands.oficenter.response.AuthenticationResponse;
import az.digitalhands.oficenter.response.UserResponse;
import az.digitalhands.oficenter.service.UserService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserRepository userRepository;

    @PostMapping("/signup")
    public ResponseEntity<?> signUp(@RequestBody UserRequest userRequest) {
        return userService.signUp(userRequest);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserRequest userRequest) {
        String jwt = userService.login(userRequest);
        if (jwt == null) {
            return ResponseEntity.status(BAD_REQUEST).build();
        } else {
            User user = userRepository.findByEmailEqualsIgnoreCase(userRequest.getEmail()).orElseThrow(
                    () -> new UserNotFoundException(HttpStatus.NOT_FOUND.name(), ErrorMessage.USER_NOT_FOUND));
            if (Objects.nonNull(user)) {
                AuthenticationResponse response = new AuthenticationResponse();
                response.setJwtToken(jwt);
                response.setId(user.getId());
                return ResponseEntity.ok(response);
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ErrorMessage.USER_NOT_FOUND);
        }
    }

    @GetMapping("/get/{userId}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable(name = "userId") Long userId) {
        return userService.getUserById(userId);
    }

    @PutMapping("/changePassword/{userId}")
    public void changePassword(@RequestBody ChangePasswordRequest changePasswordRequest,
                               @PathVariable(name = "userId") Long userId) {
        userService.changePassword(changePasswordRequest, userId);
    }

    @PostMapping("/forgotPassword")
    public ResponseEntity<String> forgotPassword(@RequestBody UserRequest userRequest) throws MessagingException {
        return userService.forgotPassword(userRequest);
    }

}