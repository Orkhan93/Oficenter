package az.digitalhands.oficenter.repository;

import az.digitalhands.oficenter.domain.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartRepository extends JpaRepository<Cart, Long> {

    List<Cart> findByCustomer_Id(Long customerId);

}