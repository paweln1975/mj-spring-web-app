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
public class Category {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="category_name")
    private String categoryName;

    @ManyToMany (mappedBy = "categorySet")
    Set<Recipe> recipeSet = new HashSet<>();
}
