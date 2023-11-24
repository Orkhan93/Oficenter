package az.digitalhands.oficenter.service;

import az.digitalhands.oficenter.domain.Collection;
import az.digitalhands.oficenter.domain.User;
import az.digitalhands.oficenter.enums.UserRole;
import az.digitalhands.oficenter.exception.CollectionNotFoundException;
import az.digitalhands.oficenter.exception.UnauthorizedException;
import az.digitalhands.oficenter.exception.UserNotFoundException;
import az.digitalhands.oficenter.exception.error.ErrorMessage;
import az.digitalhands.oficenter.mappers.CollectionMapper;
import az.digitalhands.oficenter.repository.CollectionRepository;
import az.digitalhands.oficenter.repository.UserRepository;
import az.digitalhands.oficenter.request.CollectionRequest;
import az.digitalhands.oficenter.response.CollectionResponse;
import az.digitalhands.oficenter.response.CollectionResponseList;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class CollectionService {

    private final CollectionRepository collectionRepository;
    private final UserRepository userRepository;
    private final CollectionMapper collectionMapper;

    public CollectionResponse createCollection(CollectionRequest collectionRequest, Long userId) {
        log.info("Inside collectionRequest {}", collectionRequest);
        User user = userRepository.findById(userId).orElseThrow(
                () -> new UserNotFoundException(HttpStatus.NOT_FOUND.name(), ErrorMessage.USER_NOT_FOUND));
        if (Objects.nonNull(user) && user.getUserRole().equals(UserRole.ADMIN)) {
            Collection collection = collectionMapper.fromRequestToModel(collectionRequest);
            log.info("Inside createCollection {}", collection);
            return collectionMapper.fromModelToResponse(collectionRepository.save(collection));
        }
        throw new UnauthorizedException(HttpStatus.UNAUTHORIZED.name(), ErrorMessage.UNAUTHORIZED);
    }

    public CollectionResponse updateCollection(CollectionRequest collectionRequest, Long userId) {
        log.info("Inside collectionRequest {}", collectionRequest);
        User user = userRepository.findById(userId).orElseThrow(
                () -> new UserNotFoundException(HttpStatus.NOT_FOUND.name(), ErrorMessage.USER_NOT_FOUND));
        if (Objects.nonNull(user) && user.getUserRole().equals(UserRole.ADMIN)) {
            Collection findCollection = collectionRepository.findById(collectionRequest.getId()).orElseThrow(
                    () -> new CollectionNotFoundException(HttpStatus.NOT_FOUND.name(), ErrorMessage.COLLECTION_NOT_FOUND));
            if (Objects.nonNull(findCollection)) {
                log.info("Inside updateCollection {}", collectionRequest);
                return collectionMapper.fromModelToResponse
                        (collectionRepository.save(collectionMapper.fromRequestToModel(collectionRequest)));
            } else
                throw new CollectionNotFoundException(HttpStatus.NOT_FOUND.name(), ErrorMessage.COLLECTION_NOT_FOUND);
        }
        throw new UnauthorizedException(HttpStatus.UNAUTHORIZED.name(), ErrorMessage.UNAUTHORIZED);
    }

    public CollectionResponseList getAllCollection() {
        List<Collection> collections = collectionRepository.findAll();
        CollectionResponseList list = new CollectionResponseList();
        List<CollectionResponse> collectionResponses = collectionMapper.fromModelListToResponseList(collections);
        list.setCollectionResponses(collectionResponses);
        return list;
    }

    public CollectionResponse getCollectionById(Long collectionId) {
        Collection collection = collectionRepository.findById(collectionId).orElseThrow(
                () -> new CollectionNotFoundException(HttpStatus.NOT_FOUND.name(), ErrorMessage.COLLECTION_NOT_FOUND));
        log.info("Inside getCollectionById {}", collection);
        return collectionMapper.fromModelToResponse(collection);
    }

    public void deleteById(Long userId, Long collectionId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new UserNotFoundException(HttpStatus.NOT_FOUND.name(), ErrorMessage.USER_NOT_FOUND));
        if (Objects.nonNull(user) && user.getUserRole().equals(UserRole.ADMIN)) {
            Collection collection = collectionRepository.findById(collectionId).orElseThrow(
                    () -> new CollectionNotFoundException(HttpStatus.NOT_FOUND.name(), ErrorMessage.COLLECTION_NOT_FOUND));
            collectionRepository.deleteById(collectionId);
            log.info("deleteCollection {}", collection);
        } else ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    public void deleteAllCollections(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new UserNotFoundException(HttpStatus.NOT_FOUND.name(), ErrorMessage.USER_NOT_FOUND));
        if (Objects.nonNull(user) && user.getUserRole().equals(UserRole.ADMIN)) {
            collectionRepository.deleteAll();
            log.info("deleteAllCollections successfully");
        } else ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

}