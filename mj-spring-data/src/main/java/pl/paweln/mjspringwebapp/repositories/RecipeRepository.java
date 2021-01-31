package pl.paweln.mjspringwebapp.repositories;

import org.springframework.data.repository.CrudRepository;
import pl.paweln.mjspringwebapp.domain.Recipe;

public interface RecipeRepository extends CrudRepository<Recipe, Long> {

}
