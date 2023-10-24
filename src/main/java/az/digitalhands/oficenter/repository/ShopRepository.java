package az.digitalhands.oficenter.repository;

import az.digitalhands.oficenter.domain.Shop;
import az.digitalhands.oficenter.wrapper.ShopWrapper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShopRepository extends JpaRepository<Shop,Long> {
    List<ShopWrapper> getAllShops();


}
