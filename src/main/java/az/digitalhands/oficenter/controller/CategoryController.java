package az.digitalhands.oficenter.controller;

import az.digitalhands.oficenter.request.CategoryRequest;
import az.digitalhands.oficenter.response.CategoryResponse;
import az.digitalhands.oficenter.response.CategoryResponseList;
import az.digitalhands.oficenter.service.CategoryService;
import az.digitalhands.oficenter.wrapper.CategoryWrapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/categories")
public class CategoryController {
    private final CategoryService categoryService;


    @PostMapping("/add/{userId}")
    public ResponseEntity<CategoryResponse> createCategory(@RequestBody CategoryRequest categoryRequest,
                                                           @PathVariable Long userId) {
        return ResponseEntity.status(HttpStatus.OK).body(categoryService.createCategory(categoryRequest, userId));
    }

    @PutMapping("/update/{userId}")
    public ResponseEntity<CategoryResponse> updateCategory(@RequestBody CategoryRequest categoryRequest,
                                                           @PathVariable Long userId) {
        return ResponseEntity.status(HttpStatus.OK).body(categoryService.updateCategory(categoryRequest, userId));
    }

    @GetMapping("/get/{categoryId}")
    public ResponseEntity<CategoryResponse> getCategory(@PathVariable Long categoryId) {
        return ResponseEntity.status(HttpStatus.OK).body(categoryService.getCategoryById(categoryId));
    }

    @GetMapping("/getAll")
    public ResponseEntity<CategoryResponseList> getAllCategories() {
        return ResponseEntity.status(HttpStatus.OK).body(categoryService.getAllCategories());
    }

    @DeleteMapping("/{userId}/delete/{categoryId}")
    public void deleteCategory(@PathVariable Long userId,
                               @PathVariable Long categoryId) {
        categoryService.deleteById(userId, categoryId);
    }

    @DeleteMapping("/{userId}/deleteAll")
    public void deleteAllCategories(@PathVariable Long userId) {
        categoryService.deleteAllCategories(userId);
    }

}
