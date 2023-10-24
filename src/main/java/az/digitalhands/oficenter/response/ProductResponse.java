package az.digitalhands.oficenter.response;

import az.digitalhands.oficenter.enums.StatusRole;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ProductResponse {

    private Long id;
    private String name;
    private String description;
    private Integer quantity;
    private Double price;
    private StatusRole status;
    private String imageOfProduct;
    @JsonIgnore
    private Long categoryId;

}