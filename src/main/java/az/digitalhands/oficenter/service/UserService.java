package az.digitalhands.oficenter.service;

import az.digitalhands.oficenter.constant.OficenterConstant;
import az.digitalhands.oficenter.domain.User;
import az.digitalhands.oficenter.enums.UserRole;
import az.digitalhands.oficenter.exception.IncorrectPasswordException;
import az.digitalhands.oficenter.exception.UserNotFoundException;
import az.digitalhands.oficenter.exception.error.ErrorMessage;
import az.digitalhands.oficenter.mappers.UserMapper;
import az.digitalhands.oficenter.repository.UserRepository;
import az.digitalhands.oficenter.request.ChangePasswordRequest;
import az.digitalhands.oficenter.request.ForgotPasswordRequest;
import az.digitalhands.oficenter.request.LoginRequest;
import az.digitalhands.oficenter.request.UserRequest;

import az.digitalhands.oficenter.response.UserResponse;
import az.digitalhands.oficenter.security.JwtUtil;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static az.digitalhands.oficenter.constant.OficenterConstant.BY_OFICENTER;
import static az.digitalhands.oficenter.constant.OficenterConstant.USER_ALREADY_EXISTS;
import static az.digitalhands.oficenter.exception.error.ErrorMessage.BAD_CREDENTIALS;
import static org.springframework.http.HttpStatus.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final JwtUtil jwtUtil;
    private final EmailService emailService;

    public ResponseEntity<?> signUp(UserRequest userSignUpRequest) {
        log.info("Inside userSignUpRequest {}", userSignUpRequest);
        if (validationSignUp(userSignUpRequest)) {
            Optional<User> user = userRepository.findByEmailEqualsIgnoreCase(userSignUpRequest.getEmail());
            if (user.isEmpty()) {
                User saved = userMapper.fromUserSignUpRequestToModel(userSignUpRequest);
                saved.setUserRole(UserRole.ADMIN);
                log.info("Inside signUp {}", saved);
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

    public String login(LoginRequest loginRequest) {
        log.info("Inside loginRequest {}", loginRequest);
        Optional<User> optionalUser = userRepository.findByEmailEqualsIgnoreCase(loginRequest.getEmail());
        if (optionalUser.isPresent()) {
            return jwtUtil.generateTokenTest(loginRequest.getEmail());
        }
        log.error("login {}", optionalUser);
        return BAD_CREDENTIALS;
    }

    public ResponseEntity<UserResponse> getUserById(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new UserNotFoundException(HttpStatus.NOT_FOUND.name(), ErrorMessage.USER_NOT_FOUND + userId));
        log.info("Inside getUserById {}", user);
        return ResponseEntity.ok(userMapper.fromModelToResponse(user));
    }

    public void changePassword(ChangePasswordRequest changePasswordRequest, Long userId) {
        log.info("Inside changePasswordRequest {}", changePasswordRequest);
        User user = userRepository.findById(userId).orElseThrow(
                () -> new UserNotFoundException(HttpStatus.NOT_FOUND.name(), ErrorMessage.USER_NOT_FOUND + userId));
        if (!user.getPassword().equals(changePasswordRequest.getOldPassword())) {
            throw new IncorrectPasswordException(BAD_REQUEST.name(), ErrorMessage.INCORRECT_PASSWORD);
        }
        if (!changePasswordRequest.getNewPassword().equals(changePasswordRequest.getConfirmPassword())) {
            throw new IncorrectPasswordException(BAD_REQUEST.name(), ErrorMessage.NOT_MATCHES);
        }
        user.setPassword(changePasswordRequest.getNewPassword());
        userRepository.save(user);
        log.info("Inside changePassword {}", user);
    }

    public ResponseEntity<String> forgotPassword(ForgotPasswordRequest forgotPasswordRequest) throws MessagingException {
        log.info("Inside forgotPasswordRequest {}", forgotPasswordRequest);
        Optional<User> user = userRepository.findByEmailEqualsIgnoreCase(forgotPasswordRequest.getEmail());
        if (user.isPresent()) {
            emailService.forgetMail(user.get().getEmail(), BY_OFICENTER, user.get().getPassword());
            return ResponseEntity.status(OK).body(OficenterConstant.CHECK_EMAIL);
        } else
            return ResponseEntity.status(BAD_REQUEST).body(ErrorMessage.USER_NOT_FOUND);
    }

    private boolean validationSignUp(UserRequest userRequest) {
        return userRequest.getName() != null && userRequest.getEmail() != null
                && userRequest.getUsername() != null && userRequest.getPassword() != null;
    }

}