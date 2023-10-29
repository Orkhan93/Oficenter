package az.digitalhands.oficenter.repository;

import az.digitalhands.oficenter.domain.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {

}