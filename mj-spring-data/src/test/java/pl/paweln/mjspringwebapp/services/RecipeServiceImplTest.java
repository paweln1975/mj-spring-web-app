package pl.paweln.mjspringwebapp.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import pl.paweln.mjspringwebapp.converters.RecipeCommandToRecipe;
import pl.paweln.mjspringwebapp.converters.RecipeToRecipeCommand;
import pl.paweln.mjspringwebapp.domain.Recipe;
import pl.paweln.mjspringwebapp.repositories.RecipeRepository;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

public class RecipeServiceImplTest {
    RecipeServiceImpl recipeService;

    @Mock
    RecipeRepository recipeRepository;

    @Mock
    RecipeToRecipeCommand recipeToRecipeCommand;

    @Mock
    RecipeCommandToRecipe recipeCommandToRecipe;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        this.recipeService = new RecipeServiceImpl(
                this.recipeRepository,
                recipeCommandToRecipe,
                recipeToRecipeCommand);
    }

    @Test
    public void getRecipeByIdTest() {
        Recipe recipe = new Recipe();
        recipe.setId(1L);

        when(this.recipeRepository.findById(anyLong()))
                .thenReturn(Optional.of(recipe));

        Recipe recipeReturned = this.recipeService.findById(1L);

        assertNotNull(recipeReturned);
        verify(this.recipeRepository, times(1)).findById(anyLong());
        verify(this.recipeRepository, never()).findAll();
    }

    @Test
    public void getRecipesTest() {
        Recipe recipe = new Recipe();
        Set<Recipe> recipesData = new HashSet<>();
        recipesData.add(recipe);

        when(this.recipeRepository.findAll()).thenReturn(recipesData);
        Set<Recipe> recipes = this.recipeService.getRecipes();

        assertEquals(1, recipes.size());
        verify(this.recipeRepository, times(1)).findAll();
        verify(this.recipeRepository, never()).findById(anyLong());

    }

    @Test
    public void deleteByIdTest() {
        Long id = Long.valueOf(2L);
        this.recipeService.deleteById(id);

        verify(this.recipeRepository, times(1)).deleteById(anyLong());
    }


}
