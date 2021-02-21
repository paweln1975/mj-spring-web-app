package pl.paweln.mjspringwebapp.converters;

import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import pl.paweln.mjspringwebapp.commands.RecipeCommand;
import pl.paweln.mjspringwebapp.domain.Category;
import pl.paweln.mjspringwebapp.domain.Recipe;

@Component
public class RecipeToRecipeCommand implements Converter<Recipe, RecipeCommand> {

    private final CategoryToCategoryCommand categoryConverter;
    private final IngredientToIngredientCommand ingredientConverter;
    private final NotesToNotesCommand notesConverter;

    public RecipeToRecipeCommand(CategoryToCategoryCommand categoryConverter, IngredientToIngredientCommand ingredientConverter, NotesToNotesCommand notesConverter) {
        this.categoryConverter = categoryConverter;
        this.ingredientConverter = ingredientConverter;
        this.notesConverter = notesConverter;
    }

    @Override
    @Synchronized
    @Nullable
    public RecipeCommand convert(Recipe source) {
        if (source == null)
            return null;
        RecipeCommand command = new RecipeCommand();
        command.setId(source.getId());
        command.setDescription(source.getDescription());
        command.setPrepTime(source.getPrepTime());
        command.setDifficulty(source.getDifficulty());
        command.setDirections(source.getDirections());
        command.setNotesCommand(notesConverter.convert(source.getNotes()));
        command.setImage(source.getImage());

        if (source.getCategorySet() != null && source.getCategorySet().size() > 0){
            source.getCategorySet()
                    .forEach((Category category) -> command.getCategorySet().add(categoryConverter.convert(category)));
        }

        if (source.getIngredientSet()!= null && source.getIngredientSet().size() > 0){
            source.getIngredientSet()
                    .forEach(ingredient -> command.getIngredientSet().add(ingredientConverter.convert(ingredient)));
        }

        return command;
    }
}
