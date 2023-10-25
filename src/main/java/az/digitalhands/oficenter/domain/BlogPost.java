    package az.digitalhands.oficenter.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@NamedQuery(name = "BlogPost.getAllBlogPosts", query = "select new az.digitalhands.oficenter.wrapper.BlogPostWrapper" +
        "(b.id,b.title,b.content,b.creationDate,b.imageOfBlogPost) from BlogPost b")

@Entity
@Setter
@Getter
@Table(name = "blog_post")
public class BlogPost {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "content")
    private String content;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "creationDate")
    @JsonFormat(pattern = "dd-mm-yyyy'T'HH:mm")
    private LocalDateTime creationDate;

    @Column(name = "image")
    private String imageOfBlogPost;


    @Override
    public String toString() {
        return "BlogPost{id=%d, title='%s', content='%s', creationDate=%s}"
                .formatted(id, title, content, creationDate);
    }

}