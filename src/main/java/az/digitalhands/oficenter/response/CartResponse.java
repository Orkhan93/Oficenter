package az.digitalhands.oficenter.response;


import az.digitalhands.oficenter.domain.CartItem;
import az.digitalhands.oficenter.domain.Customer;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Setter
@Getter
public class CartResponse {

    private Long id;
    private Double totalPrice;
    private Integer totalItems;

    @JsonIgnore
    private Customer customer;

    @JsonIgnore
    private Long productId;

    @JsonIgnore
    private Set<CartItem> cartItems;

}