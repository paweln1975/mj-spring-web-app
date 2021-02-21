package pl.paweln.mjspringwebapp.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pl.paweln.mjspringwebapp.domain.Recipe;
import pl.paweln.mjspringwebapp.repositories.RecipeRepository;

import java.io.IOException;
import java.util.Optional;

@Service
@Slf4j
public class ImageServiceImpl implements ImageService {
    RecipeRepository recipeRepository;

    public ImageServiceImpl(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    @Override
    public void saveImageFile(Long recipeId, MultipartFile file) {
        Optional<Recipe> recipeOptional = this.recipeRepository.findById(Long.valueOf(recipeId));

        if (!recipeOptional.isPresent()) {
            if (log.isErrorEnabled()) {
                log.error("Recipe not found: " + recipeId);
                throw new RuntimeException("Recipe not found");
            }
        }

        Recipe recipe = recipeOptional.get();

        try {
            Byte[] bytes = new Byte[file.getBytes().length];

            int i = 0;

            for (byte b : file.getBytes()) {
                bytes[i++] = b;
            }
            recipe.setImage(bytes);
            this.recipeRepository.save(recipe);
        }
        catch (IOException ex) {
            if (log.isErrorEnabled()) {
                log.error("Error while saving image for recipe: " + recipeId);
            }
        }
    }
}
