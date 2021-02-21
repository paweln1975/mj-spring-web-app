package pl.paweln.mjspringwebapp.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pl.paweln.mjspringwebapp.commands.RecipeCommand;
import pl.paweln.mjspringwebapp.services.ImageService;
import pl.paweln.mjspringwebapp.services.RecipeService;


@Controller
@Slf4j
public class ImageController {
    private final ImageService imageService;
    private final RecipeService recipeService;

    public ImageController(ImageService imageService, RecipeService recipeService) {
        this.imageService = imageService;
        this.recipeService = recipeService;
    }

    @GetMapping("/recipe/{recipeId}/image")
    public String showUploadForm(@PathVariable String recipeId, Model model) {
        RecipeCommand recipeCommand = recipeService.findRecipeCommandById(Long.valueOf(recipeId));
        if (log.isInfoEnabled()) {
            log.info("Recipe found: " + recipeCommand);
        }
        model.addAttribute("recipe", recipeCommand);
        return "/recipes/imageupload";
    }

    @PostMapping
    @RequestMapping("/recipe/{recipeId}/image")
    public String handleImagePost (@PathVariable String recipeId, @RequestParam("imagefile") MultipartFile file) {
        this.imageService.saveImageFile(Long.valueOf(recipeId), file);
        return "redirect:/recipe/" + recipeId + "/show";
    }
    
}
