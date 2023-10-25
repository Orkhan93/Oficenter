package az.digitalhands.oficenter.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@NamedQuery(name = "Category.getAllCategories",
        query = "select new az.digitalhands.oficenter.wrapper.CategoryWrapper(c.id,c.name) from Category  c")
@Entity
@Setter
@Getter
@Table(name = "category")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @ManyToOne
    @JoinColumn(name = "collection_id", nullable = false)
    private Collection collection;

    @OneToMany(mappedBy = "category"
            ,cascade = CascadeType.ALL
            ,fetch = FetchType.LAZY)
    private List<Product> products;

    @Override
    public String toString() {
        return "Category{id=%d, name='%s'}".formatted(id, name);
    }

}