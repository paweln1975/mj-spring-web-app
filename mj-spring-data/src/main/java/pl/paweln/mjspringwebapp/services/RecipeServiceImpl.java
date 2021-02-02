package pl.paweln.mjspringwebapp.services;

import org.springframework.stereotype.Service;
import pl.paweln.mjspringwebapp.domain.Recipe;
import pl.paweln.mjspringwebapp.repositories.RecipeRepository;

import java.util.HashSet;
import java.util.Set;

@Service

public class RecipeServiceImpl implements RecipeService {
    private final RecipeRepository recipeRepository;

    public RecipeServiceImpl(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    @Override
    public Set<Recipe> getRecipes() {

        Set<Recipe> recipeSet = new HashSet<>();
        this.recipeRepository.findAll().iterator().forEachRemaining(recipeSet::add);


        return recipeSet;
    }
}
