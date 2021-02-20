package pl.paweln.mjspringwebapp.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.paweln.mjspringwebapp.commands.IngredientCommand;
import pl.paweln.mjspringwebapp.converters.IngredientCommandToIngredient;
import pl.paweln.mjspringwebapp.converters.IngredientToIngredientCommand;
import pl.paweln.mjspringwebapp.domain.Ingredient;
import pl.paweln.mjspringwebapp.domain.Recipe;
import pl.paweln.mjspringwebapp.domain.UnitOfMeasure;
import pl.paweln.mjspringwebapp.repositories.RecipeRepository;
import pl.paweln.mjspringwebapp.repositories.UnitOfMeasureRepository;

import java.util.Optional;

@Service
@Slf4j
public class IngredientServiceImpl implements IngredientService {

    private final IngredientCommandToIngredient ingredientCommandToIngredient;
    private final IngredientToIngredientCommand ingredientToIngredientCommand;
    private final RecipeRepository recipeRepository;
    private final UnitOfMeasureRepository unitOfMeasureRepository;

    public IngredientServiceImpl(IngredientCommandToIngredient ingredientCommandToIngredient, IngredientToIngredientCommand ingredientToIngredientCommand, RecipeRepository recipeRepository, UnitOfMeasureRepository unitOfMeasureRepository) {
        this.ingredientCommandToIngredient = ingredientCommandToIngredient;
        this.ingredientToIngredientCommand = ingredientToIngredientCommand;
        this.recipeRepository = recipeRepository;
        this.unitOfMeasureRepository = unitOfMeasureRepository;
    }

    @Override
    @Transactional
    public IngredientCommand findByRecipeIdAndIngredientId(Long recipeId, Long ingredientId) {
        Optional<Recipe> recipe = this.recipeRepository.findById(recipeId);
        if (!recipe.isPresent()) {
            if (log.isErrorEnabled()) {
                log.error("Recipe not found: " + recipeId);
            }
        }

        Optional<IngredientCommand> ingredientCommand = recipe.get().getIngredientSet().stream()
                .filter(ingredient -> ingredient.getId().equals(ingredientId))
                .map(ingredient -> this.ingredientToIngredientCommand.convert(ingredient))
                .findFirst();

        if (!ingredientCommand.isPresent()) {
            if (log.isErrorEnabled()) {
                log.error("Ingredient not found: " + ingredientId);
            }
        }
        return ingredientCommand.get();
    }

    @Override
    @Transactional
    public IngredientCommand saveIngredientCommand(IngredientCommand command) {
        Optional<Recipe> recipeOptional
                = this.recipeRepository.findById(command.getRecipeId());

        if (!recipeOptional.isPresent()) {
            if (log.isErrorEnabled()) {
                log.error("Recipe not found: " + command.getRecipeId());
            }
            return new IngredientCommand();
        } else {
            Recipe recipe = recipeOptional.get();

            Optional<Ingredient> ingredientOptional = recipe.getIngredientSet().stream()
                    .filter(ingredient -> ingredient.getId().equals(command.getId()))
                    .findFirst();

            if (ingredientOptional.isPresent()) {
                Ingredient ingredient = ingredientOptional.get();
                ingredient.setAmount(command.getAmount());
                ingredient.setDescription(command.getDescription());

                UnitOfMeasure uom = this.unitOfMeasureRepository.findById(command.getUnitOfMeasureCommand().getId())
                        .orElseThrow(() -> new RuntimeException("Unit of Measure not found"));
                ingredient.setUnitOfMeasure(uom);
            } else {
                recipe.addIngredient(ingredientCommandToIngredient.convert(command));
            }

            Recipe savedRecipe = this.recipeRepository.save(recipe);

            return savedRecipe.getIngredientSet().stream()
                            .filter(ingredient -> ingredient.getId().equals(command.getId()))
                            .map(ingredient -> this.ingredientToIngredientCommand.convert(ingredient))
                            .findFirst().get();
        }
    }

}
