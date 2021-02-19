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

    @GetMapping
    @RequestMapping("/recipes")
    public String getRecipes(Model model) {
        Set<Recipe> recipes = this.recipeService.getRecipes();
        if (log.isInfoEnabled()) {
            log.info("Returning recipes: " + recipes.size());
        }

        model.addAttribute("recipes", recipes);

        return "recipes/list";
    }

    @GetMapping
    @RequestMapping("recipe/{id}/show")
    public String showById(@PathVariable String id, Model model) {
        Recipe recipe = recipeService.findById(Long.valueOf(id));
        model.addAttribute("recipe", recipe);

        Set<Ingredient> ingredients = recipe.getIngredientSet();
        model.addAttribute("ingredients", ingredients);

        Set<Category> categorySet = recipe.getCategorySet();
        model.addAttribute("categories", categorySet);


        return "recipes/show";
    }

    @GetMapping
    @RequestMapping("recipe/{id}/update")
    public String updateRecipe(@PathVariable String id, Model model) {
        RecipeCommand recipeCommand = recipeService.findCommandById(Long.valueOf(id));
        model.addAttribute("recipe", recipeCommand);

        return "recipes/form";
    }

    @GetMapping
    @RequestMapping("recipe/{id}/delete")
    public String deleteRecipe(@PathVariable String id, Model model) {
        recipeService.deleteById(Long.valueOf(id));

        return "redirect:/recipes";
    }

    @RequestMapping("recipes/find")
    public String findRecipes() {
        return "notImplemented";
    }

    @GetMapping
    @RequestMapping("recipe/new")
    public String newRecipe(Model model) {
        model.addAttribute("recipe", new RecipeCommand());
        return "recipes/form";
    }

    @PostMapping
    @RequestMapping("recipe")
    public String saveOrUpdateRecipe(@ModelAttribute RecipeCommand command) {
        RecipeCommand savedCommand = this.recipeService.saveRecipeCommand(command);
        return "redirect:/recipe/" + savedCommand.getId() + "/show";
    }
}
