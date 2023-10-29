package az.digitalhands.oficenter.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@NamedQuery(name = "About.getAllAbout",query = "select new az.digitalhands.oficenter.wrapper.AboutWrapper" +
        "(a.id,a.title,a.content,a.imageOfData) from About a")

@Entity
@Getter
@Setter
@Table(name = "about")
public class About {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "content")
    private String content;

    @Column(name = "image")
    private String imageOfData;

    @Override
    public String toString() {
        return "About{id=%d, title='%s', content='%s', imageOfData='%s'}"
                .formatted(id, title, content, imageOfData);
    }

}