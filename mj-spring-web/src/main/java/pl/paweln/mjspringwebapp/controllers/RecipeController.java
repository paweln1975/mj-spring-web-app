package pl.paweln.mjspringwebapp.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.paweln.mjspringwebapp.domain.Category;
import pl.paweln.mjspringwebapp.domain.Ingredient;
import pl.paweln.mjspringwebapp.domain.Recipe;
import pl.paweln.mjspringwebapp.services.RecipeService;

import java.util.Set;

@Controller
@Slf4j
public class RecipeController {

    private final RecipeService recipeService;

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @RequestMapping("/recipes")
    public String getRecipes(Model model) {
        Set<Recipe> recipes = this.recipeService.getRecipes();
        if (log.isInfoEnabled()) {
            log.info("Returning recipes: " + recipes.size());
        }

        model.addAttribute("recipes", recipes);

        return "recipes/list";
    }

    @RequestMapping("recipe/show/{id}")
    public String showById(@PathVariable String id, Model model) {
        Recipe recipe = recipeService.findById(Long.valueOf(id));
        model.addAttribute("recipe", recipe);

        Set<Ingredient> ingredients = recipe.getIngredientSet();
        model.addAttribute("ingredients", ingredients);

        Set<Category> categorySet = recipe.getCategorySet();
        model.addAttribute("categories", categorySet);


        return "recipes/show";
    }

    @RequestMapping("recipes/find")
    public String findRecipes() {
        return "notImplemented";
    }
}
