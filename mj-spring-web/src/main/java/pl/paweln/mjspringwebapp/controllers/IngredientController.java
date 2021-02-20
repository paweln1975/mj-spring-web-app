package pl.paweln.mjspringwebapp.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.paweln.mjspringwebapp.commands.IngredientCommand;
import pl.paweln.mjspringwebapp.commands.RecipeCommand;
import pl.paweln.mjspringwebapp.services.IngredientService;
import pl.paweln.mjspringwebapp.services.RecipeService;
import pl.paweln.mjspringwebapp.services.UnitOfMeasureService;


@Controller
@Slf4j
public class IngredientController {
    private final RecipeService recipeService;

    private final IngredientService ingredientService;

    private final UnitOfMeasureService uomService;

    public IngredientController(RecipeService recipeService, IngredientService ingredientService, UnitOfMeasureService uomService) {
        this.recipeService = recipeService;
        this.ingredientService = ingredientService;
        this.uomService = uomService;
    }

    @GetMapping
    @RequestMapping("recipe/{recipeId}/ingredients")
    public String getListIngredients(@PathVariable String recipeId, Model model) {
        RecipeCommand recipeCommand = this.recipeService.findRecipeCommandById(Long.valueOf(recipeId));

        model.addAttribute("recipe", recipeCommand);
        if (log.isInfoEnabled()) {
            log.info("Returning ingredient list: " + recipeCommand.getIngredientSet().size());
        }
        return "recipes/ingredients/list";
    }

    @GetMapping
    @RequestMapping("/recipe/{recipeId}/ingredient/{ingredientId}/show")
    public String showIngredient(@PathVariable String recipeId, @PathVariable String ingredientId, Model model) {
        IngredientCommand ingredientCommand = this.ingredientService.findByRecipeIdAndIngredientId(
                Long.valueOf(recipeId), Long.valueOf(ingredientId));

        model.addAttribute("ingredient", ingredientCommand);
        if (log.isInfoEnabled()) {
            log.info("Returning ingredient: " + ingredientCommand);
        }

        return "recipes/ingredients/show";
    }

    @GetMapping
    @RequestMapping("recipe/{recipeId}/ingredient/{ingredientId}/update")
    public String updateIngredient(@PathVariable String recipeId,
                                   @PathVariable String ingredientId, Model model) {
        IngredientCommand command = this.ingredientService
                .findByRecipeIdAndIngredientId(Long.valueOf(recipeId), Long.valueOf(ingredientId));

        model.addAttribute("ingredient", command);
        model.addAttribute("uomList", this.uomService.getUnitsOfMeasure());

        return "recipes/ingredients/form";
    }


    @PostMapping
    @RequestMapping ("/recipe/{recipeId}/ingredient")
    public String saveOrUpdate(@ModelAttribute IngredientCommand command) {
        IngredientCommand savedCommand = this.ingredientService.saveIngredientCommand(command);
        if (log.isDebugEnabled()) {
            log.debug("Save ingredient for recipe: " + savedCommand.getRecipeId());
            log.debug("Saved ingredient: " + savedCommand);
        }
        return "redirect:/recipe/" + savedCommand.getRecipeId() + "/ingredient/" + savedCommand.getId() + "/show";
    }
    

}
