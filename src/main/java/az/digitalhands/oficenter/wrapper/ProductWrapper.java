package az.digitalhands.oficenter.wrapper;

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
    private String status;
    private Long categoryId;

}