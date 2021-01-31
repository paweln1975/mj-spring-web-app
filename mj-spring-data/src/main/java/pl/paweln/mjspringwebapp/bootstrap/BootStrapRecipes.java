package pl.paweln.mjspringwebapp.bootstrap;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import pl.paweln.mjspringwebapp.domain.*;
import pl.paweln.mjspringwebapp.repositories.CategoryRepository;
import pl.paweln.mjspringwebapp.repositories.RecipeRepository;
import pl.paweln.mjspringwebapp.repositories.UnitOfMeasureRepository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class BootStrapRecipes implements ApplicationListener<ContextRefreshedEvent> {

    private final RecipeRepository recipeRepository;
    private final UnitOfMeasureRepository unitOfMeasureRepository;
    private final CategoryRepository categoryRepository;

    public BootStrapRecipes(RecipeRepository recipeRepository, UnitOfMeasureRepository unitOfMeasureRepository, CategoryRepository categoryRepository) {
        this.recipeRepository = recipeRepository;
        this.unitOfMeasureRepository = unitOfMeasureRepository;
        this.categoryRepository = categoryRepository;
    }

    List<Recipe> prepareData() {
        List<Recipe> list = new ArrayList<>();
        Optional<UnitOfMeasure> uomTeeSpoon = this.unitOfMeasureRepository.findByUnitName("Teaspoon");
        Optional<UnitOfMeasure> uomGram = this.unitOfMeasureRepository.findByUnitName("gram(s)");
        Optional<UnitOfMeasure> uomMl = this.unitOfMeasureRepository.findByUnitName("ml");
        Optional<UnitOfMeasure> uomNone = this.unitOfMeasureRepository.findByUnitName("none");

        Optional<Category> americanCategory = this.categoryRepository.findByCategoryName("American");
        Optional<Category> mexicanCategory = this.categoryRepository.findByCategoryName("Mexican");

        Recipe recipe = new Recipe();
        recipe.setDescription("Perfect Guacamole");
        recipe.setDifficulty(Difficulty.EASY);
        recipe.setPrepTime(10);
        recipe.setDirections("The best guacamole keeps it simple: just ripe avocados, salt, a squeeze of lime, onions, chiles, cilantro, and some chopped tomato. Serve it as a dip at your next party or spoon it on top of tacos for an easy dinner upgrade.");
        Notes notes = new Notes();
        notes.setNotes("The word “guacamole”, and the dip, are both originally from Mexico, where avocados have been cultivated for thousands of years. The name is derived from two Aztec Nahuatl words—ahuacatl (avocado) and molli (sauce).");

        recipe.setNotes(notes);

        recipe.addIngredient(new Ingredient(new BigDecimal(2), "Avacado", uomNone.get()))
                .addIngredient(new Ingredient(new BigDecimal(0.25), "salt, more to taste", uomTeeSpoon.get()));


        recipe.getCategorySet().add(americanCategory.get());
        recipe.getCategorySet().add(mexicanCategory.get());

        list.add(recipe);

        Recipe recipe1 = new Recipe();
        recipe1.setDescription("Spicy Grilled Chicken Tacos");
        recipe1.setDifficulty(Difficulty.HARD);
        recipe1.setPrepTime(100);
        recipe1.setDirections("Spicy grilled chicken tacos! Quick marinade, then grill. Ready in about 30 minutes. Great for a quick weeknight dinner, backyard cookouts, and tailgate parties.");

        list.add(recipe1);

        return list;}

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        this.recipeRepository.saveAll(this.prepareData());
    }
}
