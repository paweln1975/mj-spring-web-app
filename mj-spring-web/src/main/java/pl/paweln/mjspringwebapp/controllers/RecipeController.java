package pl.paweln.mjspringwebapp.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.paweln.mjspringwebapp.commands.RecipeCommand;
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

    @GetMapping("/recipes")
    public String getRecipes(Model model) {
        Set<Recipe> recipes = this.recipeService.getRecipes();
        model.addAttribute("recipes", recipes);

        if (log.isInfoEnabled()) {
            log.info("Returning recipes: " + recipes.size());
        }

        return "recipes/list";
    }

    @GetMapping("/recipe/{recipeId}/show")
    public String showById(@PathVariable String recipeId, Model model) {
        Recipe recipe = recipeService.findById(Long.valueOf(recipeId));
        model.addAttribute("recipe", recipe);

        Set<Ingredient> ingredients = recipe.getIngredientSet();
        model.addAttribute("ingredients", ingredients);

        Set<Category> categorySet = recipe.getCategorySet();
        model.addAttribute("categories", categorySet);

        if (log.isInfoEnabled()) {
            log.info("Returning recipe: " + recipe);
        }
        return "recipes/show";
    }

    @GetMapping("/recipe/{recipeId}/update")
    public String updateRecipe(@PathVariable String recipeId, Model model) {
        RecipeCommand recipeCommand = recipeService.findRecipeCommandById(Long.valueOf(recipeId));
        model.addAttribute("recipe", recipeCommand);

        if (log.isInfoEnabled()) {
            log.info("Returning recipe: " + recipeId);
        }
        return "recipes/form";
    }

    @GetMapping("/recipe/{recipeId}/delete")
    public String deleteRecipe(@PathVariable String recipeId, Model model) {
        recipeService.deleteById(Long.valueOf(recipeId));

        if (log.isInfoEnabled()) {
            log.info("Deleted recipe: " + recipeId);
        }

        return "redirect:/recipes";
    }

    @GetMapping("/recipes/find")
    public String findRecipes() {
        return "notImplemented";
    }

    @GetMapping("recipe/new")
    public String newRecipe(Model model) {
        model.addAttribute("recipe", new RecipeCommand());

        if (log.isInfoEnabled()) {
            log.info("Returning empty RecipeCommand.");
        }

        return "recipes/form";
    }

    @PostMapping
    @RequestMapping("/recipe")
    public String saveOrUpdateRecipe(@ModelAttribute RecipeCommand command) {
        RecipeCommand savedCommand = this.recipeService.saveRecipeCommand(command);

        if (log.isInfoEnabled()) {
            log.info("Saved recipe: " + savedCommand.getId());
        }
        return "redirect:/recipe/" + savedCommand.getId() + "/show";
    }
}
