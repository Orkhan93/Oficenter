package az.digitalhands.oficenter.repository;

import az.digitalhands.oficenter.domain.Product;
import az.digitalhands.oficenter.wrapper.ProductWrapper;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    List<ProductWrapper> getAllProducts();

    List<ProductWrapper> getAllProductsStatusTrue();

}