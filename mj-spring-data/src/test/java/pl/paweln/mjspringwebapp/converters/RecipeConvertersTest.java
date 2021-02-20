package pl.paweln.mjspringwebapp.converters;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.paweln.mjspringwebapp.commands.NotesCommand;
import pl.paweln.mjspringwebapp.commands.RecipeCommand;
import pl.paweln.mjspringwebapp.domain.Difficulty;
import pl.paweln.mjspringwebapp.domain.Notes;
import pl.paweln.mjspringwebapp.domain.Recipe;

import static org.junit.jupiter.api.Assertions.*;

public class RecipeConvertersTest {
    RecipeCommandToRecipe recipeCommandToRecipeConverter;
    RecipeToRecipeCommand recipeToRecipeCommandConverter;

    private Long lvalue = Long.valueOf(1L);
    private String description = "description";
    private Integer prepTime = Integer.valueOf(30);
    private String directions = "directions";
    private Difficulty difficulty = Difficulty.EASY;

    @BeforeEach
    public void setUp() {
        this.recipeCommandToRecipeConverter = new RecipeCommandToRecipe(
                new CategoryCommandToCategory(),
                new IngredientCommandToIngredient(new UoMCommandToUoM()),
                new NotesCommandToNotes()
        );

        this.recipeToRecipeCommandConverter = new RecipeToRecipeCommand(
            new CategoryToCategoryCommand(),
            new IngredientToIngredientCommand(new UoMToUoMCommand()),
            new NotesToNotesCommand()
        );
    }

    @Test
    public void testNullParameter() {
        assertNull(this.recipeCommandToRecipeConverter.convert(null));
        assertNull(this.recipeToRecipeCommandConverter.convert(null));
    }

    @Test
    public void testNotNullParameter() {
        RecipeCommand command = new RecipeCommand();
        command.setNotesCommand(new NotesCommand());
        assertNotNull(this.recipeCommandToRecipeConverter.convert(command));

        Recipe recipe = new Recipe();
        assertNotNull(this.recipeToRecipeCommandConverter.convert(recipe));
    }

    @Test
    public void testConvertToRecipe() {
        RecipeCommand command = new RecipeCommand();
        command.setId(this.lvalue);
        command.setDescription(this.description);
        command.setNotesCommand(new NotesCommand());
        command.setPrepTime(this.prepTime);
        command.setDirections(this.directions);

        Recipe recipe = this.recipeCommandToRecipeConverter.convert(command);

        assertNotNull(recipe);
        assertEquals(lvalue, recipe.getId());
        assertEquals(description, recipe.getDescription());
        assertEquals(directions, recipe.getDirections());
        assertEquals(prepTime, recipe.getPrepTime());
        assertNotNull(recipe.getNotes());
    }

    @Test
    public void testConvertToRecipeCommand() {
        Recipe recipe = new Recipe();
        recipe.setId(lvalue);
        recipe.setDescription(this.description);
        recipe.setDifficulty(this.difficulty);
        recipe.setPrepTime(this.prepTime);
        recipe.setDirections(this.directions);

        Notes notes = new Notes();
        notes.setId(lvalue);
        recipe.setNotes(notes);

        RecipeCommand command = this.recipeToRecipeCommandConverter.convert(recipe);

        assertEquals(lvalue, command.getId());
        assertEquals(this.description, command.getDescription());
        assertEquals(this.prepTime, command.getPrepTime());
        assertEquals(this.difficulty, command.getDifficulty());
        assertEquals(this.directions, command.getDirections());
    }
}
