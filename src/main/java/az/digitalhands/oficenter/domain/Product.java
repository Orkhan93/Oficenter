package az.digitalhands.oficenter.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@NamedQuery(name = "Product.getAllProducts",
query = "select new az.digitalhands.oficenter.wrapper.ProductWrapper" +
        "(p.id,p.name,p.description,p.quantity,p.price,p.status,p.category.id) from Product p")

@Entity
@Setter
@Getter
@Table(name = "product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "status")
    private String status;

    @Column(name = "price")
    private Double price;

    @Column(name = "quantity")
    private Integer quantity;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @Override
    public String toString() {
        return "Product{id=%d, name='%s', description='%s', status='%s', price=%s, quantity=%d}"
                .formatted(id, name, description, status, price, quantity);
    }

}