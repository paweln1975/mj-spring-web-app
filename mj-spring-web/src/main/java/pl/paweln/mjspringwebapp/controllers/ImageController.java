package pl.paweln.mjspringwebapp.controllers;

import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import pl.paweln.mjspringwebapp.commands.RecipeCommand;
import pl.paweln.mjspringwebapp.services.ImageService;
import pl.paweln.mjspringwebapp.services.RecipeService;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;


@Controller
@Slf4j
public class ImageController {
    private final ImageService imageService;
    private final RecipeService recipeService;

    public ImageController(ImageService imageService, RecipeService recipeService) {
        this.imageService = imageService;
        this.recipeService = recipeService;
    }

    @GetMapping("/recipe/{recipeId}/newimage")
    public String showUploadForm(@PathVariable String recipeId, Model model) {
        RecipeCommand recipeCommand = recipeService.findRecipeCommandById(Long.valueOf(recipeId));
        if (log.isInfoEnabled()) {
            log.info("Recipe found: " + recipeCommand);
        }
        model.addAttribute("recipe", recipeCommand);
        return "/recipes/imageform";
    }

    @PostMapping("/recipe/{recipeId}/uploadimage")
    public String handleImagePost (@PathVariable String recipeId, @RequestParam("imagefile") MultipartFile file) {
        this.imageService.saveImageFile(Long.valueOf(recipeId), file);
        return "redirect:/recipe/" + recipeId + "/show";
    }

    @GetMapping("/recipe/{recipeId}/image")
    public void renderImage (@PathVariable String recipeId, HttpServletResponse response) throws IOException {
        RecipeCommand recipeCommand = this.recipeService.findRecipeCommandById(Long.valueOf(recipeId));

        byte[] byteArr = new byte[recipeCommand.getImage().length];
        int i = 0;

        for (Byte b : recipeCommand.getImage()) {
            byteArr[i++] = b;
        }

        response.setContentType("image/jpeg");
        InputStream is = new ByteArrayInputStream(byteArr);
        IOUtils.copy(is, response.getOutputStream());

    }
    
}
