package pl.paweln.mjspringwebapp.converters;

import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import pl.paweln.mjspringwebapp.commands.RecipeCommand;
import pl.paweln.mjspringwebapp.domain.Recipe;

@Component
public class RecipeCommandToRecipe implements Converter<RecipeCommand, Recipe> {
    private final CategoryCommandToCategory categoryConverter;
    private final IngredientCommandToIngredient ingredientConverter;
    private final NotesCommandToNotes notesConverter;

    public RecipeCommandToRecipe(CategoryCommandToCategory categoryConverter, IngredientCommandToIngredient ingredientConverter, NotesCommandToNotes notesConverter) {
        this.categoryConverter = categoryConverter;
        this.ingredientConverter = ingredientConverter;
        this.notesConverter = notesConverter;
    }

    @Override
    @Synchronized
    @Nullable
    public Recipe convert(RecipeCommand source) {
        if (source == null)
            return null;

        final Recipe recipe = new Recipe();
        recipe.setId(source.getId());
        recipe.setDescription(source.getDescription());
        recipe.setDifficulty(source.getDifficulty());
        recipe.setDirections(source.getDirections());
        recipe.setPrepTime(source.getPrepTime());
        recipe.setNotes(notesConverter.convert(source.getNotesCommand()));

        if (source.getCategorySet() != null && source.getCategorySet().size() > 0){
            source.getCategorySet()
                    .forEach( category -> recipe.getCategorySet().add(categoryConverter.convert(category)));
        }

        if (source.getIngredientSet() != null && source.getIngredientSet().size() > 0){
            source.getIngredientSet()
                    .forEach(ingredient -> recipe.getIngredientSet().add(ingredientConverter.convert(ingredient)));
        }

        return recipe;
    }

}
