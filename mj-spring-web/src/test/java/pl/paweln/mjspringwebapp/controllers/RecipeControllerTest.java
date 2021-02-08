package pl.paweln.mjspringwebapp.controllers;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;
import pl.paweln.mjspringwebapp.services.RecipeService;

public class RecipeControllerTest {
    RecipeController recipeController;

    @Mock
    RecipeService recipeService;

    @Mock
    Model model;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        this.recipeController = new RecipeController(this.recipeService);
    }

    @Test
    public void testGetRecipes() {
        String ret = this.recipeController.getRecipes(this.model);
        Assertions.assertEquals("recipes/list", ret);

        Mockito.verify(this.recipeService, Mockito.times(1)).getRecipes();
        Mockito.verify(this.model, Mockito.times(1))
                .addAttribute(Mockito.eq("recipes"), Mockito.anySet());
    }
}
