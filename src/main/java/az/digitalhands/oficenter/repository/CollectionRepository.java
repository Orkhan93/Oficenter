package az.digitalhands.oficenter.repository;

import az.digitalhands.oficenter.domain.Collection;
import az.digitalhands.oficenter.wrapper.CollectionWrapper;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CollectionRepository extends JpaRepository<Collection, Long> {

    List<CollectionWrapper> getAllCollection();

}