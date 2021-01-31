package pl.paweln.mjspringwebapp.domain;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Category {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="category_name")
    private String categoryName;

    @ManyToMany (mappedBy = "categorySet")
    Set<Recipe> recipeSet = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public Set<Recipe> getRecipeSet() {
        return recipeSet;
    }

    public void setRecipeSet(Set<Recipe> recipeSet) {
        this.recipeSet = recipeSet;
    }
}
