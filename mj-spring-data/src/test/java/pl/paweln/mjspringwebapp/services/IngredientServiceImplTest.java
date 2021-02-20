package pl.paweln.mjspringwebapp.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import pl.paweln.mjspringwebapp.commands.IngredientCommand;
import pl.paweln.mjspringwebapp.converters.IngredientCommandToIngredient;
import pl.paweln.mjspringwebapp.converters.IngredientToIngredientCommand;
import pl.paweln.mjspringwebapp.converters.UoMCommandToUoM;
import pl.paweln.mjspringwebapp.converters.UoMToUoMCommand;
import pl.paweln.mjspringwebapp.domain.Ingredient;
import pl.paweln.mjspringwebapp.domain.Recipe;
import pl.paweln.mjspringwebapp.repositories.RecipeRepository;
import pl.paweln.mjspringwebapp.repositories.UnitOfMeasureRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class IngredientServiceImplTest {

    IngredientService ingredientService;

    @Mock
    RecipeRepository recipeRepository;

    @Mock
    UnitOfMeasureRepository unitOfMeasureRepository;

    IngredientCommandToIngredient ingredientCommandToIngredient;
    IngredientToIngredientCommand ingredientToIngredientCommand;

    public IngredientServiceImplTest() {
        this.ingredientToIngredientCommand = new IngredientToIngredientCommand(new UoMToUoMCommand());
        this.ingredientCommandToIngredient = new IngredientCommandToIngredient(new UoMCommandToUoM());

    }

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        ingredientService = new IngredientServiceImpl(
                this.ingredientCommandToIngredient,
                this.ingredientToIngredientCommand,
                this.recipeRepository,
                this.unitOfMeasureRepository);
    }

    @Test
    public void testFindByRecipeIdAndIngredientId() {
        Recipe recipe = new Recipe();
        recipe.setId(1L);

        Ingredient ingredient1 = new Ingredient();
        ingredient1.setId(1L);

        Ingredient ingredient2 = new Ingredient();
        ingredient2.setId(1L);

        Ingredient ingredient3 = new Ingredient();
        ingredient3.setId(3L);

        recipe.addIngredient(ingredient1);
        recipe.addIngredient(ingredient2);
        recipe.addIngredient(ingredient3);
        Optional<Recipe> recipeOptional = Optional.of(recipe);

        when(recipeRepository.findById(anyLong())).thenReturn(recipeOptional);

        IngredientCommand command = this.ingredientService.findByRecipeIdAndIngredientId(1L, 3L);

        assertEquals(Long.valueOf(3L), command.getId());
        assertEquals(Long.valueOf(1L), command.getRecipeId());

        verify(this.recipeRepository, times(1)).findById(anyLong());
    }

    @Test
    public void testSaveOrUpdateIngredient() {

        Optional<Recipe> optionalRecipe = Optional.of(new Recipe());

        IngredientCommand ingredientCommand = new IngredientCommand();
        ingredientCommand.setId(1L);
        ingredientCommand.setRecipeId(2L);

        Recipe savedRecipe = new Recipe();
        savedRecipe.setId(2L);
        Ingredient ingredient = new Ingredient();
        ingredient.setId(1L);
        savedRecipe.addIngredient(ingredient);

        when(this.recipeRepository.findById(anyLong())).thenReturn(optionalRecipe);
        when(this.recipeRepository.save(any())).thenReturn(savedRecipe);

        IngredientCommand savedCommand = this.ingredientService.saveIngredientCommand(ingredientCommand);

        assertEquals(Long.valueOf(1L), savedCommand.getId());
        verify(this.recipeRepository, times(1)).findById(anyLong());
        verify(this.recipeRepository, times(1)).save(any(Recipe.class));


    }
}
