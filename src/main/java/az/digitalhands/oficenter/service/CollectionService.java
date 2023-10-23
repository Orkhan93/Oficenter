package az.digitalhands.oficenter.service;

import az.digitalhands.oficenter.domain.Collection;
import az.digitalhands.oficenter.domain.User;
import az.digitalhands.oficenter.enums.UserRole;
import az.digitalhands.oficenter.exception.CollectionNotFoundException;
import az.digitalhands.oficenter.exception.UserNotFoundException;
import az.digitalhands.oficenter.exception.error.ErrorMessage;
import az.digitalhands.oficenter.mappers.CollectionMapper;
import az.digitalhands.oficenter.repository.CollectionRepository;
import az.digitalhands.oficenter.repository.UserRepository;
import az.digitalhands.oficenter.request.CollectionRequest;
import az.digitalhands.oficenter.response.CollectionResponse;
import az.digitalhands.oficenter.wrapper.CollectionWrapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class CollectionService {

    private final CollectionRepository collectionRepository;
    private final UserRepository userRepository;
    private final CollectionMapper collectionMapper;

    public ResponseEntity<CollectionResponse> createCollection(CollectionRequest collectionRequest, Long userId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new UserNotFoundException(HttpStatus.NOT_FOUND.name(),ErrorMessage.USER_NOT_FOUND));
        if (Objects.nonNull(user) && user.getUserRole().equals(UserRole.ADMIN)) {
            Collection collection = collectionMapper.fromRequestToModel(collectionRequest);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(collectionMapper.fromModelToResponse(collectionRepository.save(collection)));
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    public ResponseEntity<CollectionResponse> updateCollection(CollectionRequest collectionRequest, Long userId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new UserNotFoundException(HttpStatus.NOT_FOUND.name(),ErrorMessage.USER_NOT_FOUND));
        if (Objects.nonNull(user) && user.getUserRole().equals(UserRole.ADMIN)) {
            Collection findCollection = collectionRepository.findById(collectionRequest.getId()).orElseThrow(
                    () -> new CollectionNotFoundException(HttpStatus.NOT_FOUND.name(),ErrorMessage.COLLECTION_NOT_FOUND));
            if (Objects.nonNull(findCollection)) {
                return ResponseEntity.status(HttpStatus.OK)
                        .body(collectionMapper.fromModelToResponse
                                (collectionRepository.save(collectionMapper.fromRequestToModel(collectionRequest))));
            } else
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    public ResponseEntity<List<CollectionWrapper>> getAllCollection() {
        return ResponseEntity.status(HttpStatus.OK).body(collectionRepository.getAllCollection());
    }

    public ResponseEntity<CollectionResponse> getCollectionById(Long collectionId) {
        Collection collection = collectionRepository.findById(collectionId).orElseThrow(
                () -> new CollectionNotFoundException(HttpStatus.NOT_FOUND.name(),ErrorMessage.COLLECTION_NOT_FOUND));
        return ResponseEntity.status(HttpStatus.OK).body(collectionMapper.fromModelToResponse(collection));
    }

    public void deleteById(Long userId, Long collectionId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new UserNotFoundException(HttpStatus.NOT_FOUND.name(),ErrorMessage.USER_NOT_FOUND));
        if (Objects.nonNull(user) && user.getUserRole().equals(UserRole.ADMIN)) {
            Collection collection = collectionRepository.findById(collectionId).orElseThrow(
                    () -> new CollectionNotFoundException(HttpStatus.NOT_FOUND.name(),ErrorMessage.COLLECTION_NOT_FOUND));
            collectionRepository.deleteById(collectionId);
            log.info("deleteCollection {}", collection);
        }
    }

    public void deleteAllCategories(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new UserNotFoundException(HttpStatus.NOT_FOUND.name(),ErrorMessage.USER_NOT_FOUND));
        if (Objects.nonNull(user) && user.getUserRole().equals(UserRole.ADMIN)) {
            collectionRepository.deleteAll();
            log.info("deleteAllCategories successfully");
        }
    }


}