package pl.paweln.mjspringwebapp.services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;
import pl.paweln.mjspringwebapp.commands.RecipeCommand;
import pl.paweln.mjspringwebapp.converters.RecipeCommandToRecipe;
import pl.paweln.mjspringwebapp.converters.RecipeToRecipeCommand;
import pl.paweln.mjspringwebapp.domain.Recipe;
import pl.paweln.mjspringwebapp.repositories.RecipeRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class RecipeServiceIT {
    @Autowired
    RecipeService recipeService;

    @Autowired
    RecipeRepository recipeRepository;

    @Autowired
    RecipeCommandToRecipe recipeCommandToRecipe;

    @Autowired
    RecipeToRecipeCommand recipeToRecipeCommand;

    @Transactional
    @Test
    public void saveDescriptionTest() {
        String newDescription = "New description";
        Iterable<Recipe> recipes = this.recipeRepository.findAll();
        Recipe testRecipe = recipes.iterator().next();
        RecipeCommand command = this.recipeToRecipeCommand.convert(testRecipe);

        command.setDescription(newDescription);
        RecipeCommand savedCommand = this.recipeService.saveRecipeCommand(command);

        assertEquals(newDescription, savedCommand.getDescription());
        assertEquals(testRecipe.getId(), savedCommand.getId());
        assertEquals(testRecipe.getCategorySet().size(), savedCommand.getCategorySet().size());
        assertEquals(testRecipe.getIngredientSet().size(), savedCommand.getIngredientSet().size());


    }
}
