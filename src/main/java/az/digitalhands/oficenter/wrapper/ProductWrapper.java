package az.digitalhands.oficenter.wrapper;

import az.digitalhands.oficenter.enums.StatusRole;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ProductWrapper {

    private Long id;
    private String name;
    private String description;
    private Integer quantity;
    private Double price;
    private StatusRole status;
    private String imageOfProduct;
    private Long categoryId;

}