package pl.paweln.mjspringwebapp.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.paweln.mjspringwebapp.commands.RecipeCommand;
import pl.paweln.mjspringwebapp.services.RecipeService;

@Controller
@Slf4j
public class IngredientController {
    private final RecipeService recipeService;

    public IngredientController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @GetMapping
    @RequestMapping("recipe/{recipeId}/ingredients")
    public String getListIngredients(@PathVariable String recipeId, Model model) {
        RecipeCommand recipeCommand = this.recipeService.findCommandById(Long.valueOf(recipeId));

        model.addAttribute("recipe", recipeCommand);
        if (log.isInfoEnabled()) {
            log.info("Returning ingredient list: " + recipeCommand.getIngredientSet().size());
        }
        return "recipes/ingredients/list";
    }
}
