package az.digitalhands.oficenter.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@NamedQuery(name = "Collection.getAllCollection",
        query = "select new az.digitalhands.oficenter.wrapper.CollectionWrapper(c.name) from Collection c")
@Entity
@Setter
@Getter
@Table(name = "collection")
public class Collection {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "collection"
            , cascade = CascadeType.ALL
            ,fetch = FetchType.LAZY)
    private List<Category> categories;

    @Override
    public String toString() {
        return "Collection{id=%d, name='%s'}".formatted(id, name);
    }

}