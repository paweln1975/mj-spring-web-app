package pl.paweln.mjspringwebapp.repositories;

import org.springframework.data.repository.CrudRepository;
import pl.paweln.mjspringwebapp.domain.Category;

import java.util.Optional;

public interface CategoryRepository extends CrudRepository<Category, Long> {
    Optional<Category> findByCategoryName(String categoryName);
}
