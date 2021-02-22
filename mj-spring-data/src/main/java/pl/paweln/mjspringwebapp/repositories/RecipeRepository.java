package pl.paweln.mjspringwebapp.repositories;

import org.springframework.data.repository.CrudRepository;
import pl.paweln.mjspringwebapp.domain.Recipe;

import java.util.List;

public interface RecipeRepository extends CrudRepository<Recipe, Long> {
    List<Recipe> findAllByDescriptionLike(String description);
}
