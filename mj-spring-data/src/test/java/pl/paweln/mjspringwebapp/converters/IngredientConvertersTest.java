package pl.paweln.mjspringwebapp.converters;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.paweln.mjspringwebapp.commands.IngredientCommand;
import pl.paweln.mjspringwebapp.commands.UnitOfMeasureCommand;
import pl.paweln.mjspringwebapp.domain.Ingredient;
import pl.paweln.mjspringwebapp.domain.Recipe;
import pl.paweln.mjspringwebapp.domain.UnitOfMeasure;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

public class IngredientConvertersTest {
    IngredientToIngredientCommand ingredientToIngredientCommand;
    IngredientCommandToIngredient ingredientCommandToIngredient;

    private Long lvalue = Long.valueOf(1L);
    private String description = "description";
    private BigDecimal amount = BigDecimal.valueOf(1L);

    @BeforeEach
    public void setUp() {
        this.ingredientToIngredientCommand =
                new IngredientToIngredientCommand(new UoMToUoMCommand());
        this.ingredientCommandToIngredient =
                new IngredientCommandToIngredient(new UoMCommandToUoM());
    }

    @Test
    public void testNullParameter() {
        assertNull(this.ingredientCommandToIngredient.convert(null));
        assertNull(this.ingredientToIngredientCommand.convert(null));
    }

    @Test
    public void testNotNullParameter() {
        IngredientCommand command = new IngredientCommand();
        command.setUnitOfMeasureCommand(new UnitOfMeasureCommand());

        assertNotNull(this.ingredientCommandToIngredient.convert(command));

        Ingredient ingredient = new Ingredient();
        assertNotNull(this.ingredientToIngredientCommand.convert(ingredient));
    }

    @Test
    public void testConvertCommandToIngredient() {
        IngredientCommand command = new IngredientCommand();
        command.setUnitOfMeasureCommand(new UnitOfMeasureCommand());
        command.setId(this.lvalue);
        command.setDescription(this.description);
        command.setAmount(this.amount);

        Ingredient ingredient = this.ingredientCommandToIngredient.convert(command);

        assertNotNull(ingredient);
        assertEquals(this.lvalue, ingredient.getId());
        assertEquals(this.description, ingredient.getDescription());
        assertEquals(this.amount, ingredient.getAmount());
        assertNotNull(ingredient.getUnitOfMeasure());
    }

    @Test
    public void testConvertIngredientToCommand() {
        Ingredient ingredient = new Ingredient();
        ingredient.setUnitOfMeasure(new UnitOfMeasure());
        ingredient.setId(this.lvalue);
        Recipe recipe = new Recipe();
        recipe.setId(this.lvalue);
        ingredient.setRecipe(recipe);
        ingredient.setDescription(this.description);
        ingredient.setAmount(this.amount);

        IngredientCommand command = this.ingredientToIngredientCommand.convert(ingredient);

        assertNotNull(command);
        assertEquals(this.lvalue, command.getId());
        assertEquals(this.lvalue, command.getRecipeId());
        assertEquals(this.description, command.getDescription());
        assertEquals(this.amount, command.getAmount());
        assertNotNull(command.getUnitOfMeasureCommand());



    }

}
