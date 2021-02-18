package pl.paweln.mjspringwebapp.commands;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.paweln.mjspringwebapp.domain.Difficulty;

import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
public class RecipeCommand {
    private Long id;
    private String description;
    private Integer prepTime;
    private String directions;
    private Set<CategoryCommand> categorySet = new HashSet<>();
    private NotesCommand notesCommand;
    private Difficulty difficulty;
    private Set<IngredientCommand> ingredientSet = new HashSet();

}
