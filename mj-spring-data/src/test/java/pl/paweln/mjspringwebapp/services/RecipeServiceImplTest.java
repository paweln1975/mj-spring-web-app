package pl.paweln.mjspringwebapp.services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import pl.paweln.mjspringwebapp.domain.Recipe;
import pl.paweln.mjspringwebapp.repositories.RecipeRepository;

import java.util.HashSet;
import java.util.Set;

public class RecipeServiceImplTest {
    RecipeServiceImpl recipeService;

    @Mock
    RecipeRepository recipeRepository;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        this.recipeService = new RecipeServiceImpl(this.recipeRepository);
    }

    @Test
    public void testGetRecipes() {
        Recipe recipe = new Recipe();
        Set<Recipe> recipesData = new HashSet<>();
        recipesData.add(recipe);

        Mockito.when(this.recipeRepository.findAll()).thenReturn(recipesData);
        Set<Recipe> recipes = this.recipeService.getRecipes();

        Assertions.assertEquals(1, recipes.size());
        Mockito.verify(this.recipeRepository, Mockito.times(1)).findAll();

    }
}
