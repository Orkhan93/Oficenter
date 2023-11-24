package az.digitalhands.oficenter.service;

import az.digitalhands.oficenter.domain.BlogPost;
import az.digitalhands.oficenter.domain.User;
import az.digitalhands.oficenter.enums.UserRole;
import az.digitalhands.oficenter.exception.BlogPostNotFoundException;
import az.digitalhands.oficenter.exception.UnauthorizedException;
import az.digitalhands.oficenter.exception.UserNotFoundException;
import az.digitalhands.oficenter.exception.error.ErrorMessage;
import az.digitalhands.oficenter.mappers.BlogPostMapper;
import az.digitalhands.oficenter.repository.BlogPostRepository;
import az.digitalhands.oficenter.repository.UserRepository;
import az.digitalhands.oficenter.request.BlogPostRequest;
import az.digitalhands.oficenter.response.BlogPostResponse;
import az.digitalhands.oficenter.response.BlogPostResponseList;
import az.digitalhands.oficenter.wrapper.BlogPostWrapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class BlogPostService {

    private final BlogPostRepository blogPostRepository;
    private final UserRepository userRepository;
    private final BlogPostMapper blogPostMapper;

    public BlogPostResponse createBlog(BlogPostRequest blogPostRequest, Long userId) {
        log.info("Inside blogPostRequest {}", blogPostRequest);
        User user = userRepository.findById(userId).orElseThrow(
                () -> new UserNotFoundException(HttpStatus.NOT_FOUND.name(), ErrorMessage.USER_NOT_FOUND));
        if (Objects.nonNull(user) && user.getUserRole().equals(UserRole.ADMIN)) {
            BlogPost blogPost = blogPostMapper.fromRequestToModel(blogPostRequest);
            blogPost.setCreationDate(LocalDateTime.now());
            log.info("Inside createBlog {}", blogPost);
            return blogPostMapper.fromModelToResponse(blogPostRepository.save(blogPost));
        } else
            throw new UnauthorizedException(HttpStatus.UNAUTHORIZED.name(), ErrorMessage.UNAUTHORIZED);
    }

    public BlogPostResponse updateBlog(BlogPostRequest blogPostRequest, Long userId) {
        log.info("Inside blogPostRequest {}", blogPostRequest);
        User user = userRepository.findById(userId).orElseThrow(
                () -> new UserNotFoundException(HttpStatus.NOT_FOUND.name(), ErrorMessage.USER_NOT_FOUND));
        if (Objects.nonNull(user) && user.getUserRole().equals(UserRole.ADMIN)) {
            BlogPost blogPost = blogPostRepository.findById(blogPostRequest.getId()).orElseThrow(
                    () -> new BlogPostNotFoundException(HttpStatus.NOT_FOUND.name(), ErrorMessage.BLOG_POST_NOT_FOUND));
            if (Objects.nonNull(blogPost)) {
                BlogPost updatedBlog = blogPostMapper.fromRequestToModel(blogPostRequest);
                updatedBlog.setCreationDate(LocalDateTime.now());
                log.info("Inside updateBlog {}", updatedBlog);
                return blogPostMapper.fromModelToResponse(blogPostRepository.save(updatedBlog));
            } else
                throw new BlogPostNotFoundException(HttpStatus.NOT_FOUND.name(), ErrorMessage.BLOG_POST_NOT_FOUND);
        } else
            throw new UnauthorizedException(HttpStatus.UNAUTHORIZED.name(), ErrorMessage.UNAUTHORIZED);
    }

    public BlogPostResponseList getAllBlogs() {
        List<BlogPost> blogPosts = blogPostRepository.findAll();
        BlogPostResponseList list = new BlogPostResponseList();
        List<BlogPostResponse> blogPostResponses = blogPostMapper.fromModelListToResponseList(blogPosts);
        list.setBlogPostResponses(blogPostResponses);
        return list;
    }

    public BlogPostResponse getBlogById(Long blogId) {
        Optional<BlogPost> blogPost = blogPostRepository.findById(blogId);
        if (blogPost.isEmpty()) {
            throw new BlogPostNotFoundException(HttpStatus.NOT_FOUND.name(), ErrorMessage.BLOG_POST_NOT_FOUND);
        }
        log.info("Inside getBlogById {}", blogPost);
        return blogPostMapper.fromModelToResponse(blogPost.get());
    }

    public void deleteBlogPost(Long userId, Long blogPostId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new UserNotFoundException(HttpStatus.NOT_FOUND.name(), ErrorMessage.USER_NOT_FOUND));
        if (Objects.nonNull(user) && user.getUserRole().equals(UserRole.ADMIN)) {
            BlogPost blogPost = blogPostRepository.findById(blogPostId).orElseThrow(
                    () -> new BlogPostNotFoundException(HttpStatus.NOT_FOUND.name(), ErrorMessage.BLOG_POST_NOT_FOUND));
            blogPostRepository.deleteById(blogPostId);
            log.info("deleteBlogPost {}", blogPost);
        } else ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

}