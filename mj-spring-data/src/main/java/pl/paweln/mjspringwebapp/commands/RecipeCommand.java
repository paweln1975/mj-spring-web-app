package pl.paweln.mjspringwebapp.commands;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import pl.paweln.mjspringwebapp.domain.Difficulty;

import javax.validation.constraints.*;
import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
@ToString
public class RecipeCommand {
    private Long id;

    @NotBlank
    @Size(min=1, max=255)
    private String description;

    @Min(1)
    @Max(999)
    @NotNull
    private Integer prepTime;

    @NotBlank
    @Size(min=10, max=4000)
    private String directions;

    private Set<CategoryCommand> categorySet = new HashSet<>();
    private NotesCommand notesCommand;
    private Difficulty difficulty;
    private Set<IngredientCommand> ingredientSet = new HashSet();
    private Byte[] image = new Byte[0];

}
