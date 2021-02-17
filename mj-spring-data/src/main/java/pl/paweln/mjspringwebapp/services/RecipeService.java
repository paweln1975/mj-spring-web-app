package pl.paweln.mjspringwebapp.services;

import pl.paweln.mjspringwebapp.domain.Recipe;

import java.util.Set;

public interface RecipeService  {
    Set<Recipe> getRecipes();

    Recipe findById(Long id);
}
