package az.digitalhands.oficenter.controller;

import az.digitalhands.oficenter.request.ProductRequest;
import az.digitalhands.oficenter.request.UpdateStatusRequest;
import az.digitalhands.oficenter.response.ProductResponse;
import az.digitalhands.oficenter.response.ProductResponseList;
import az.digitalhands.oficenter.service.ProductService;
import az.digitalhands.oficenter.wrapper.ProductWrapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping("/add/{userId}")
    public ResponseEntity<ProductResponse> createProduct(@RequestBody ProductRequest productRequest,
                                                         @PathVariable(name = "userId") Long userId) {
        return ResponseEntity.status(HttpStatus.CREATED).body(productService.createProduct(productRequest, userId));
    }

    @PutMapping("/update/{userId}")
    public ResponseEntity<ProductResponse> updateProduct(@RequestBody ProductRequest productRequest,
                                                         @PathVariable(name = "userId") Long userId) {
        return ResponseEntity.status(HttpStatus.OK).body(productService.updateProduct(productRequest, userId));
    }

    @GetMapping("/get/{productId}")
    public ResponseEntity<ProductResponse> getProductById(@PathVariable(name = "productId") Long productId) {
        return ResponseEntity.status(HttpStatus.OK).body(productService.getProductById(productId));
    }

    @GetMapping("/getAll")
    public ResponseEntity<ProductResponseList> getAllProducts() {
        return ResponseEntity.status(HttpStatus.OK).body(productService.getAllProducts());
    }

    @GetMapping("/getAllStatus")
    public ResponseEntity<List<ProductWrapper>> getAllProductsStatusTrue() {
        return productService.getAllProductsStatusTrue();
    }


    @DeleteMapping("/{userId}/delete/{productId}")
    public void deleteProductById(@PathVariable(name = "userId") Long userId, @PathVariable(name = "productId") Long productId) {
        productService.deleteProductById(userId, productId);
    }

    @PutMapping("updateStatus/{userId}")
    public ResponseEntity<ProductResponse> updateStatus(@PathVariable(name = "userId") Long userId,
                                                        @RequestBody UpdateStatusRequest statusRequest) {
        return ResponseEntity.status(HttpStatus.OK).body(productService.updateStatus(userId, statusRequest));
    }

}