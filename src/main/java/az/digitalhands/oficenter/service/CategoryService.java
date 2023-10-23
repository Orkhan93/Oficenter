package az.digitalhands.oficenter.service;

import az.digitalhands.oficenter.domain.Category;
import az.digitalhands.oficenter.domain.Collection;
import az.digitalhands.oficenter.domain.User;
import az.digitalhands.oficenter.enums.UserRole;
import az.digitalhands.oficenter.exception.CategoryNotFoundException;
import az.digitalhands.oficenter.exception.CollectionNotFoundException;
import az.digitalhands.oficenter.exception.UserNotFoundException;
import az.digitalhands.oficenter.exception.error.ErrorMessage;
import az.digitalhands.oficenter.mappers.CategoryMapper;
import az.digitalhands.oficenter.repository.CategoryRepository;
import az.digitalhands.oficenter.repository.CollectionRepository;
import az.digitalhands.oficenter.repository.UserRepository;
import az.digitalhands.oficenter.request.CategoryRequest;
import az.digitalhands.oficenter.response.CategoryResponse;
import az.digitalhands.oficenter.wrapper.CategoryWrapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final CollectionRepository collectionRepository;
    private final UserRepository userRepository;
    private final CategoryMapper categoryMapper;

    public ResponseEntity<CategoryResponse> createCategory(CategoryRequest categoryRequest, Long userId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new UserNotFoundException(HttpStatus.NOT_FOUND.name(),ErrorMessage.USER_NOT_FOUND));
        if (Objects.nonNull(user) && user.getUserRole().equals(UserRole.ADMIN)) {
            Collection collection = collectionRepository.findById(categoryRequest.getCollectionId()).orElseThrow(
                    () -> new CollectionNotFoundException(HttpStatus.NOT_FOUND.name(),ErrorMessage.COLLECTION_NOT_FOUND));
            Category category = categoryMapper.fromRequestToModel(categoryRequest);
            category.setCollection(collection);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(categoryMapper.fromModelToResponse(categoryRepository.save(category)));
        } else
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    public ResponseEntity<CategoryResponse> updateCategory(CategoryRequest categoryRequest, Long userId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new UserNotFoundException(HttpStatus.NOT_FOUND.name(),ErrorMessage.USER_NOT_FOUND));
        if (Objects.nonNull(user) && user.getUserRole().equals(UserRole.ADMIN)) {
            Category findCategory = categoryRepository
                    .findById(categoryRequest.getId()).orElseThrow(
                            () -> new CategoryNotFoundException(HttpStatus.NOT_FOUND.name(),ErrorMessage.CATEGORY_NOT_FOUND));
            if (Objects.nonNull(findCategory)) {
                Collection collection = collectionRepository.findById(categoryRequest.getCollectionId()).orElseThrow(
                        () -> new CollectionNotFoundException(HttpStatus.NOT_FOUND.name(),ErrorMessage.COLLECTION_NOT_FOUND));
                Category category = categoryMapper.fromRequestToModel(categoryRequest);
                category.setCollection(collection);

                return ResponseEntity.status(HttpStatus.OK)
                        .body(categoryMapper.fromModelToResponse(categoryRepository.save(category)));
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    public ResponseEntity<List<CategoryWrapper>> getAllCategories() {
        return ResponseEntity.status(HttpStatus.OK).body
                ((categoryRepository.getAllCategories()));
    }

    public ResponseEntity<CategoryResponse> getCategoryById(Long categoryId) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(
                () -> new CategoryNotFoundException(HttpStatus.NOT_FOUND.name(),ErrorMessage.CATEGORY_NOT_FOUND));

        if (Objects.nonNull(category)) {
            return ResponseEntity.status(HttpStatus.OK).body(categoryMapper.fromModelToResponse(category));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

    }

    public void deleteById(Long userId, Long categoryId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new UserNotFoundException(HttpStatus.NOT_FOUND.name(),ErrorMessage.USER_NOT_FOUND));
        if (Objects.nonNull(user) && user.getUserRole().equals(UserRole.ADMIN)) {
            Category category = categoryRepository
                    .findById(categoryId).orElseThrow(
                            () -> new CategoryNotFoundException(HttpStatus.NOT_FOUND.name(),ErrorMessage.CATEGORY_NOT_FOUND));
            categoryRepository.deleteById(categoryId);
            log.info("deleteById {}", category);
        }
    }

    public void deleteAllCategories(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new UserNotFoundException(HttpStatus.NOT_FOUND.name(),ErrorMessage.USER_NOT_FOUND));
        if (Objects.nonNull(user) && user.getUserRole().equals(UserRole.ADMIN)) {
            categoryRepository.deleteAll();
            log.info("deleteAllCategories successfully");
        }
    }

}
