package pl.paweln.mjspringwebapp.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.paweln.mjspringwebapp.services.RecipeService;

@Controller
public class RecipeController {

    private final RecipeService recipeService;

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @RequestMapping("/recipes")
    public String getRecipes(Model model) {
        model.addAttribute("recipes", this.recipeService.getRecipes());

        return "recipes/list";
    }
}
