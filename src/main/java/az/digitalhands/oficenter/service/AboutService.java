package az.digitalhands.oficenter.service;

import az.digitalhands.oficenter.domain.About;
import az.digitalhands.oficenter.domain.User;
import az.digitalhands.oficenter.enums.UserRole;
import az.digitalhands.oficenter.exception.AboutNotFoundException;
import az.digitalhands.oficenter.exception.UserNotFoundException;
import az.digitalhands.oficenter.exception.error.ErrorMessage;
import az.digitalhands.oficenter.mappers.AboutMapper;
import az.digitalhands.oficenter.repository.AboutRepository;
import az.digitalhands.oficenter.repository.UserRepository;
import az.digitalhands.oficenter.request.AboutRequest;
import az.digitalhands.oficenter.response.AboutResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class AboutService {
    private final AboutRepository aboutRepository;
    private final AboutMapper aboutMapper;
    private final UserRepository userRepository;

    public ResponseEntity<AboutResponse> add(AboutRequest aboutRequest, Long userId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new UserNotFoundException(HttpStatus.NOT_FOUND.name(), ErrorMessage.USER_NOT_FOUND));
        if (Objects.nonNull(user) && user.getUserRole().equals(UserRole.ADMIN)) {
            About about = aboutMapper.fromRequestToModel(aboutRequest);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(aboutMapper.fromModelToResponse(aboutRepository.save(about)));
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    public ResponseEntity<AboutResponse> update(AboutRequest aboutRequest, Long userId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new UserNotFoundException(HttpStatus.NOT_FOUND.name(), ErrorMessage.USER_NOT_FOUND));
        if (Objects.nonNull(user) && user.getUserRole().equals(UserRole.ADMIN)) {
            aboutRepository.findById(aboutRequest.getId()).orElseThrow(
                    () -> new AboutNotFoundException(HttpStatus.NOT_FOUND.name(), ErrorMessage.ABOUT_NOT_FOUND));
            About updatedAbout = aboutMapper.fromRequestToModel(aboutRequest);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(aboutMapper.fromModelToResponse(aboutRepository.save(updatedAbout)));

        } else return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

    }

    public void deleteById(Long userId, Long aboutId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new UserNotFoundException(HttpStatus.NOT_FOUND.name(), ErrorMessage.USER_NOT_FOUND));
        if (Objects.nonNull(user) && user.getUserRole().equals(UserRole.ADMIN)) {
            About about = aboutRepository.findById(aboutId).orElseThrow(
                    () -> new AboutNotFoundException(HttpStatus.NOT_FOUND.name(), ErrorMessage.ABOUT_NOT_FOUND));
            aboutRepository.deleteById(aboutId);
            log.info("deleteById {}", about);

        } else ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
}
