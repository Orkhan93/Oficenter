package az.digitalhands.oficenter.service;

import az.digitalhands.oficenter.domain.Category;
import az.digitalhands.oficenter.domain.Product;
import az.digitalhands.oficenter.domain.User;
import az.digitalhands.oficenter.enums.StatusRole;
import az.digitalhands.oficenter.enums.UserRole;
import az.digitalhands.oficenter.exception.CategoryNotFoundException;
import az.digitalhands.oficenter.exception.ProductNotFoundException;
import az.digitalhands.oficenter.exception.UserNotFoundException;
import az.digitalhands.oficenter.exception.error.ErrorMessage;
import az.digitalhands.oficenter.mappers.ProductMapper;
import az.digitalhands.oficenter.repository.CategoryRepository;
import az.digitalhands.oficenter.repository.ProductRepository;
import az.digitalhands.oficenter.repository.UserRepository;
import az.digitalhands.oficenter.request.ProductRequest;
import az.digitalhands.oficenter.request.UpdateStatusRequest;
import az.digitalhands.oficenter.response.ProductResponse;
import az.digitalhands.oficenter.wrapper.ProductWrapper;
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
public class ProductService {

    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final ProductMapper productMapper;

    public ResponseEntity<ProductResponse> createProduct(ProductRequest productRequest, Long userId) {
        log.info("Inside productRequest {}", productRequest);
        User user = userRepository.findById(userId).orElseThrow(
                () -> new UserNotFoundException(HttpStatus.NOT_FOUND.name(), ErrorMessage.USER_NOT_FOUND));
        if (Objects.nonNull(user) && user.getUserRole().equals(UserRole.ADMIN)) {
            Category category = categoryRepository.findById(productRequest.getCategoryId()).orElseThrow(
                    () -> new CategoryNotFoundException(HttpStatus.NOT_FOUND.name(), ErrorMessage.CATEGORY_NOT_FOUND));
            Product product = productMapper.fromRequestToModel(productRequest);
            product.setCategory(category);
            product.setStatus(StatusRole.FALSE);
            log.info("Inside createProduct {}", product);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(productMapper.fromModelToResponse(productRepository.save(product)));
        } else
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

    }

    public ResponseEntity<ProductResponse> updateProduct(ProductRequest productRequest, Long userId) {
        log.info("Inside productRequest {}", productRequest);
        User user = userRepository.findById(userId).orElseThrow(
                () -> new UserNotFoundException(HttpStatus.NOT_FOUND.name(), ErrorMessage.USER_NOT_FOUND));
        if (Objects.nonNull(user) && user.getUserRole().equals(UserRole.ADMIN)) {
            Product product = productRepository.findById(productRequest.getId())
                    .orElseThrow(() -> new ProductNotFoundException(HttpStatus.NOT_FOUND.name(), ErrorMessage.PRODUCT_NOT_FOUND));
            if (Objects.nonNull(product)) {
                Category category = categoryRepository.findById(productRequest.getCategoryId())
                        .orElseThrow(() -> new CategoryNotFoundException(HttpStatus.NOT_FOUND.name(), ErrorMessage.CATEGORY_NOT_FOUND));
                Product updateProduct = productMapper.fromRequestToModel(productRequest);
                updateProduct.setCategory(category);
                updateProduct.setStatus(StatusRole.FALSE);
                log.info("Inside updateProduct {}", updateProduct);
                return ResponseEntity.status(HttpStatus.OK)
                        .body(productMapper.fromModelToResponse
                                (productRepository.save(updateProduct)));
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    public ResponseEntity<ProductResponse> getProductById(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException(HttpStatus.NOT_FOUND.name(), ErrorMessage.PRODUCT_NOT_FOUND));
        if (Objects.nonNull(product)) {
            log.info("Inside getProductById {}", product);
            return ResponseEntity.status(HttpStatus.OK).body(productMapper.fromModelToResponse(product));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    public ResponseEntity<List<ProductWrapper>> getAllProducts() {
        log.info("Inside getAllProducts {}", productRepository.getAllProducts());
        return ResponseEntity.status(HttpStatus.OK).body(productRepository.getAllProducts());
    }

    public ResponseEntity<List<ProductWrapper>> getAllProductsStatusTrue() {
        log.info("Inside getAllProductsStatusTrue {}", productRepository.getAllProductsStatusTrue());
        return ResponseEntity.status(HttpStatus.OK).body(productRepository.getAllProductsStatusTrue());
    }


    public void deleteProductById(Long userId, Long productId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(HttpStatus.NOT_FOUND.name(), ErrorMessage.USER_NOT_FOUND));
        if (Objects.nonNull(user) && user.getUserRole().equals(UserRole.ADMIN)) {
            Product product = productRepository.findById(productId)
                    .orElseThrow(() -> new ProductNotFoundException(HttpStatus.NOT_FOUND.name(), ErrorMessage.PRODUCT_NOT_FOUND));
            productRepository.deleteById(productId);
            log.info("deleteProductById {}", product);
        } else ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }


    public ResponseEntity<ProductResponse> updateStatus(Long userId, UpdateStatusRequest statusRequest) {
        log.info("Inside statusRequest {}", statusRequest);
        User user = userRepository.findById(userId).orElseThrow(
                () -> new UserNotFoundException(HttpStatus.NOT_FOUND.name(), ErrorMessage.USER_NOT_FOUND));
        if (Objects.nonNull(user) && user.getUserRole().equals(UserRole.ADMIN)) {
            Product product = productRepository.findById(statusRequest.getId())
                    .orElseThrow(() -> new ProductNotFoundException(HttpStatus.NOT_FOUND.name(), ErrorMessage.PRODUCT_NOT_FOUND));
            product.setStatus(statusRequest.getNewRole());
            log.info("Inside updateStatus {}", product);
            productRepository.save(product);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(productMapper.fromModelToResponse(productRepository.save(product)));

        } else
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

    }

}