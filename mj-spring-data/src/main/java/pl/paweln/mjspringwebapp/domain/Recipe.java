package pl.paweln.mjspringwebapp.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Recipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;
    private Integer prepTime;
    private String directions;

    @Lob
    private Byte[] image;

    @Enumerated(value = EnumType.STRING)
    Difficulty difficulty;

    @OneToMany (cascade = CascadeType.ALL, mappedBy = "recipe")
    private Set<Ingredient> ingredientSet = new HashSet<>();

    @OneToOne (cascade = CascadeType.ALL)
    private Notes notes;

    @ManyToMany
    @JoinTable(name = "recipe_category",
            joinColumns = @JoinColumn(name = "recipe_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    private Set<Category> categorySet = new HashSet<>();

    public void setNotes(Notes notes) {
        this.notes = notes;
        notes.setRecipe(this);
    }

    public Recipe addIngredient(Ingredient ingredient) {
        ingredient.setRecipe(this);
        this.ingredientSet.add(ingredient);
        return this;
    }

}
