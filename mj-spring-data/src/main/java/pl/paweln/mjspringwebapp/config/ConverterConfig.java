package pl.paweln.mjspringwebapp.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import pl.paweln.mjspringwebapp.converters.*;

@Configuration
public class ConverterConfig implements WebMvcConfigurer {

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new RecipeToRecipeCommand(new CategoryToCategoryCommand(),new IngredientToIngredientCommand(new UoMToUoMCommand()),new NotesToNotesCommand()));
    }
}
