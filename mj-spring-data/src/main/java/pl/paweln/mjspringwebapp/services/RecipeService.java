package pl.paweln.mjspringwebapp.services;

import pl.paweln.mjspringwebapp.commands.RecipeCommand;
import pl.paweln.mjspringwebapp.domain.Recipe;

import java.util.Set;

public interface RecipeService  {
    Set<Recipe> getRecipes();

    Recipe findById(Long id);

    RecipeCommand saveRecipeCommand(RecipeCommand command);

    RecipeCommand findCommandById(Long id);

    void deleteById(Long id);
}
