package az.digitalhands.oficenter.controller;

import az.digitalhands.oficenter.request.BlogPostRequest;
import az.digitalhands.oficenter.response.BlogPostResponse;
import az.digitalhands.oficenter.response.BlogPostResponseList;
import az.digitalhands.oficenter.service.BlogPostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/blog-post")
@RequiredArgsConstructor
public class BlogPostController {

    private final BlogPostService blogPostService;

    @PostMapping("/add/{userId}")
    public ResponseEntity<BlogPostResponse> createBlog(@RequestBody BlogPostRequest blogPostRequest,
                                                       @PathVariable Long userId) {
        return ResponseEntity.status(HttpStatus.CREATED).body(blogPostService.createBlog(blogPostRequest, userId));
    }

    @PutMapping("/update/{userId}")
    public ResponseEntity<BlogPostResponse> updateBlog(@RequestBody BlogPostRequest blogPostRequest,
                                                       @PathVariable Long userId) {
        return ResponseEntity.status(HttpStatus.OK).body(blogPostService.updateBlog(blogPostRequest, userId));
    }

    @GetMapping("/getAll")
    public ResponseEntity<BlogPostResponseList> getAllBlogs() {
        return ResponseEntity.status(HttpStatus.OK).body(blogPostService.getAllBlogs());
    }

    @GetMapping("/get/{blogId}")
    public ResponseEntity<BlogPostResponse> getBlogById(@PathVariable Long blogId) {
        return ResponseEntity.status(HttpStatus.OK).body(blogPostService.getBlogById(blogId));
    }

    @DeleteMapping("/{userId}/delete/{blogPostId}")
    public void deleteBlogPost(@PathVariable(name = "userId") Long userId,
                               @PathVariable(name = "blogPostId") Long blogPostId) {
        blogPostService.deleteBlogPost(userId, blogPostId);
    }

}