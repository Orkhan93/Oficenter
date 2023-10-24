package az.digitalhands.oficenter.repository;

import az.digitalhands.oficenter.domain.BlogPost;
import az.digitalhands.oficenter.wrapper.BlogPostWrapper;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BlogPostRepository extends JpaRepository<BlogPost, Long> {

    List<BlogPostWrapper> getAllBlogPosts();

}