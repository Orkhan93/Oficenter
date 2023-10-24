package az.digitalhands.oficenter.response;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
public class BlogPostResponse {

    private Long id;
    private String title;
    private String content;
    private LocalDateTime creationDate;
    private String imageOfBlogPost;

}