package pl.paweln.mjspringwebapp.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import pl.paweln.mjspringwebapp.commands.RecipeCommand;
import pl.paweln.mjspringwebapp.converters.*;
import pl.paweln.mjspringwebapp.domain.Category;
import pl.paweln.mjspringwebapp.domain.Ingredient;
import pl.paweln.mjspringwebapp.domain.Recipe;
import pl.paweln.mjspringwebapp.exceptions.NotFoundException;
import pl.paweln.mjspringwebapp.services.RecipeService;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@Slf4j
public class RecipeController {
    public static final String DEFAULT_ERROR_VIEW = "error";

    private final RecipeService recipeService;

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @ModelAttribute
    public void populateEmptyRecipe(Model model) {
        model.addAttribute("recipe", new RecipeCommand());
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

    // it's better to create find form then have a search in the menu
    // it caused need to put everywhere the recipe bean to have it in the thymeleaf
    @GetMapping("/recipes/find")
    public String processFind(@ModelAttribute RecipeCommand command, BindingResult result, Model model) {
        if (command.getDescription() == null) {
            command.setDescription("");
        }

        RecipeToRecipeCommand recipeToRecipeCommand = new RecipeToRecipeCommand(
                new CategoryToCategoryCommand(), new IngredientToIngredientCommand(new UoMToUoMCommand()), new NotesToNotesCommand()
        );

        List<Recipe> allByDescriptionLike = this.recipeService.findAllByDescriptionLike("%" + command.getDescription() + "%");

        List<RecipeCommand> recipeCommands = allByDescriptionLike.stream().map(recipe -> recipeToRecipeCommand.convert(recipe)).collect(Collectors.toList());

        if (recipeCommands.isEmpty()) {
            result.rejectValue("description", "not found");
            return "index";
        } else if (recipeCommands.size() == 1) {
            return "redirect:/recipe/" + recipeCommands.get(0).getId() +"/show";
        } else {
            model.addAttribute("recipes", recipeCommands);
            return "recipes/list";
        }
    }

    @GetMapping("recipe/new")
    public String newRecipe(Model model) {

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

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public ModelAndView handleNotFoundException (HttpServletResponse response, Exception ex) {
        if (log.isErrorEnabled()) {
            log.error("Error occurred. Text message=" + ex.getMessage());
            log.error("Http response=" + response.getStatus());
        }

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName(DEFAULT_ERROR_VIEW);
        modelAndView.getModelMap().addAttribute("recipe", new RecipeCommand());
        modelAndView.getModelMap().addAttribute("exception", ex);
        return modelAndView;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(NumberFormatException.class)
    public ModelAndView handleNumberFormatException(HttpServletResponse response, Exception ex) {

        if (log.isErrorEnabled()) {
            log.error("Error occurred. Text message=" + ex.getMessage());
        }

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName(DEFAULT_ERROR_VIEW);
        modelAndView.getModelMap().addAttribute("recipe", new RecipeCommand());
        modelAndView.getModelMap().addAttribute("exception", ex);


        return modelAndView;
    }


}
