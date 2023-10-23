package az.digitalhands.oficenter.service;

import az.digitalhands.oficenter.domain.User;
import az.digitalhands.oficenter.enums.UserRole;
import az.digitalhands.oficenter.exception.IncorrectPasswordException;
import az.digitalhands.oficenter.exception.UserNotFoundException;
import az.digitalhands.oficenter.exception.error.ErrorMessage;
import az.digitalhands.oficenter.mappers.UserMapper;
import az.digitalhands.oficenter.repository.UserRepository;
import az.digitalhands.oficenter.request.ChangePasswordRequest;
import az.digitalhands.oficenter.request.UserRequest;

import az.digitalhands.oficenter.response.UserResponse;
import az.digitalhands.oficenter.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static az.digitalhands.oficenter.constant.Oficenter.USER_ALREADY_EXISTS;
import static az.digitalhands.oficenter.exception.error.ErrorMessage.BAD_CREDENTIALS;
import static az.digitalhands.oficenter.exception.error.ErrorMessage.INVALID_DATA;
import static org.springframework.http.HttpStatus.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final JwtUtil jwtUtil;
    private final EncryptionService encryptionService;
    private final PasswordEncoder passwordEncoder;

    public ResponseEntity<?> signUp(UserRequest userSignUpRequest) {
        if (validationSignUp(userSignUpRequest)) {
            Optional<User> user = userRepository.findByEmailEqualsIgnoreCase(userSignUpRequest.getEmail());
            if (user.isEmpty()) {
                User saved = userMapper.fromUserSignUpRequestToModel(userSignUpRequest);
                saved.setPassword(encryptionService.encryptPassword(userSignUpRequest.getPassword()));
                saved.setUserRole(UserRole.ADMIN);
                return ResponseEntity.status(CREATED)
                        .body(userRepository.save(saved));
            } else {
                log.error("userSignUpRequest {}", userSignUpRequest);
                return ResponseEntity.status(BAD_REQUEST).body(USER_ALREADY_EXISTS);
            }
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    public String login(UserRequest userLoginRequest) {
        Optional<User> optionalUser = userRepository.findByEmailEqualsIgnoreCase(userLoginRequest.getEmail());
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            if (encryptionService.verifyPassword(userLoginRequest.getPassword(), user.getPassword())) {
                return jwtUtil.generateTokenTest(userLoginRequest.getEmail());
            } else
                return INVALID_DATA;
        }
        log.error("login {}", optionalUser);
        return BAD_CREDENTIALS;
    }

    public ResponseEntity<UserResponse> getUserById(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new UserNotFoundException(HttpStatus.NOT_FOUND.name(), ErrorMessage.USER_NOT_FOUND + userId));
        return ResponseEntity.ok(userMapper.fromModelToResponse(user));
    }

    public void changePassword(ChangePasswordRequest changePasswordRequest, Long userId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new UserNotFoundException(HttpStatus.NOT_FOUND.name(), ErrorMessage.USER_NOT_FOUND + userId));
        if (!encryptionService.verifyPassword(changePasswordRequest.getOldPassword(), user.getPassword())) {
            throw new IncorrectPasswordException(BAD_REQUEST.name(), ErrorMessage.INCORRECT_PASSWORD);
        }
        if (!changePasswordRequest.getNewPassword().equals(changePasswordRequest.getConfirmPassword())) {
            throw new IncorrectPasswordException(BAD_REQUEST.name(), ErrorMessage.NOT_MATCHES);
        }
        user.setPassword(passwordEncoder.encode(changePasswordRequest.getNewPassword()));
        userRepository.save(user);
        log.info("changePassword {}", user);
    }

    private boolean validationSignUp(UserRequest userRequest) {
        return userRequest.getName() != null && userRequest.getEmail() != null
                && userRequest.getUsername() != null && userRequest.getPassword() != null;
    }

}