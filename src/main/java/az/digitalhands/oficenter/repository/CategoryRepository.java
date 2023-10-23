package az.digitalhands.oficenter.repository;

import az.digitalhands.oficenter.domain.Category;
import az.digitalhands.oficenter.wrapper.CategoryWrapper;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    List<CategoryWrapper> getAllCategories();

}