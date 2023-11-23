package az.digitalhands.oficenter.service;

import az.digitalhands.oficenter.domain.Category;
import az.digitalhands.oficenter.domain.Collection;
import az.digitalhands.oficenter.domain.User;
import az.digitalhands.oficenter.enums.UserRole;
import az.digitalhands.oficenter.exception.CategoryNotFoundException;
import az.digitalhands.oficenter.exception.CollectionNotFoundException;
import az.digitalhands.oficenter.exception.UnauthorizedException;
import az.digitalhands.oficenter.exception.UserNotFoundException;
import az.digitalhands.oficenter.exception.error.ErrorMessage;
import az.digitalhands.oficenter.mappers.CategoryMapper;
import az.digitalhands.oficenter.repository.CategoryRepository;
import az.digitalhands.oficenter.repository.CollectionRepository;
import az.digitalhands.oficenter.repository.UserRepository;
import az.digitalhands.oficenter.request.CategoryRequest;
import az.digitalhands.oficenter.response.CategoryResponse;
import az.digitalhands.oficenter.response.CategoryResponseList;
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

    public CategoryResponse createCategory(CategoryRequest categoryRequest, Long userId) {
        log.info("Inside categoryRequest {}", categoryRequest);
        User user = userRepository.findById(userId).orElseThrow(
                () -> new UserNotFoundException(HttpStatus.NOT_FOUND.name(), ErrorMessage.USER_NOT_FOUND));
        if (Objects.nonNull(user) && user.getUserRole().equals(UserRole.ADMIN)) {
            Collection collection = collectionRepository.findById(categoryRequest.getCollectionId()).orElseThrow(
                    () -> new CollectionNotFoundException(HttpStatus.NOT_FOUND.name(), ErrorMessage.COLLECTION_NOT_FOUND));
            Category category = categoryMapper.fromRequestToModel(categoryRequest);
            category.setCollection(collection);
            log.info("Inside createCategory {}", category);
            return categoryMapper.fromModelToResponse(categoryRepository.save(category));
        } else
            throw new UnauthorizedException(HttpStatus.UNAUTHORIZED.name(), ErrorMessage.UNAUTHORIZED);
    }

    public CategoryResponse updateCategory(CategoryRequest categoryRequest, Long userId) {
        log.info("Inside categoryRequest {}", categoryRequest);
        User user = userRepository.findById(userId).orElseThrow(
                () -> new UserNotFoundException(HttpStatus.NOT_FOUND.name(), ErrorMessage.USER_NOT_FOUND));
        if (Objects.nonNull(user) && user.getUserRole().equals(UserRole.ADMIN)) {
            Category findCategory = categoryRepository
                    .findById(categoryRequest.getId()).orElseThrow(
                            () -> new CategoryNotFoundException(HttpStatus.NOT_FOUND.name(), ErrorMessage.CATEGORY_NOT_FOUND));
            if (Objects.nonNull(findCategory)) {
                Collection collection = collectionRepository.findById(categoryRequest.getCollectionId()).orElseThrow(
                        () -> new CollectionNotFoundException(HttpStatus.NOT_FOUND.name(), ErrorMessage.COLLECTION_NOT_FOUND));
                Category category = categoryMapper.fromRequestToModel(categoryRequest);
                category.setCollection(collection);
                log.info("Inside updateCategory {}", category);
                return categoryMapper.fromModelToResponse(categoryRepository.save(category));
            }
            throw new CategoryNotFoundException(HttpStatus.NOT_FOUND.name(), ErrorMessage.CATEGORY_NOT_FOUND);
        }
        throw new UnauthorizedException(HttpStatus.UNAUTHORIZED.name(), ErrorMessage.UNAUTHORIZED);
    }

    public CategoryResponseList getAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        CategoryResponseList list = new CategoryResponseList();
        List<CategoryResponse> categoryResponses = categoryMapper.fromModelListToResponseList(categories);
        list.setCategoryResponseList(categoryResponses);
        return list;
    }

    public CategoryResponse getCategoryById(Long categoryId) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(
                () -> new CategoryNotFoundException(HttpStatus.NOT_FOUND.name(), ErrorMessage.CATEGORY_NOT_FOUND));
        if (Objects.isNull(category)) {
            throw new CategoryNotFoundException(HttpStatus.NOT_FOUND.name(), ErrorMessage.CATEGORY_NOT_FOUND);
        } else {
            log.info("Inside getCategoryById {}", category);
            return categoryMapper.fromModelToResponse(category);
        }
    }

    public void deleteById(Long userId, Long categoryId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new UserNotFoundException(HttpStatus.NOT_FOUND.name(), ErrorMessage.USER_NOT_FOUND));
        if (Objects.nonNull(user) && user.getUserRole().equals(UserRole.ADMIN)) {
            Category category = categoryRepository
                    .findById(categoryId).orElseThrow(
                            () -> new CategoryNotFoundException(HttpStatus.NOT_FOUND.name(), ErrorMessage.CATEGORY_NOT_FOUND));
            categoryRepository.deleteById(categoryId);
            log.info("deleteById {}", category);
        } else
            throw new UnauthorizedException(HttpStatus.UNAUTHORIZED.name(), ErrorMessage.UNAUTHORIZED);
    }

    public void deleteAllCategories(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new UserNotFoundException(HttpStatus.NOT_FOUND.name(), ErrorMessage.USER_NOT_FOUND));
        if (Objects.nonNull(user) && user.getUserRole().equals(UserRole.ADMIN)) {
            categoryRepository.deleteAll();
            log.info("deleteAllCategories successfully");
        } else
            throw new UnauthorizedException(HttpStatus.UNAUTHORIZED.name(), ErrorMessage.UNAUTHORIZED);
    }

}