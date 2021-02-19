package pl.paweln.mjspringwebapp.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;
import pl.paweln.mjspringwebapp.commands.RecipeCommand;
import pl.paweln.mjspringwebapp.services.RecipeService;

import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class IngredientControllerTest {
    @InjectMocks
    IngredientController ingredientController;

    @Mock
    RecipeService recipeService;

    @Mock
    Model model;

    MockMvc mock;

    @BeforeEach
    public void setUp() {
        mock = MockMvcBuilders.standaloneSetup(this.ingredientController).build();
    }

    @Test
    public void getListIngredients() throws Exception {
        RecipeCommand command = new RecipeCommand();

        when(recipeService.findCommandById(anyLong()))
                .thenReturn(command);

        mock.perform(MockMvcRequestBuilders.get("/recipe/1/ingredients"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipes/ingredients/list"))
                .andExpect(model().attributeExists("recipe"));
    }
}
