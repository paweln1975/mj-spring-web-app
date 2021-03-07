package pl.paweln.mjspringwebapp.controllers;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;
import pl.paweln.mjspringwebapp.commands.IngredientCommand;
import pl.paweln.mjspringwebapp.commands.RecipeCommand;
import pl.paweln.mjspringwebapp.services.IngredientService;
import pl.paweln.mjspringwebapp.services.RecipeService;
import pl.paweln.mjspringwebapp.services.UnitOfMeasureService;

import java.util.HashSet;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class IngredientControllerTest {
    @InjectMocks
    IngredientController ingredientController;

    @Mock
    RecipeService recipeService;

    @Mock
    IngredientService ingredientService;

    @Mock
    UnitOfMeasureService uomService;

    @Mock
    Model model;

    MockMvc mock;

    @BeforeEach
    public void setUp() {
        mock = MockMvcBuilders.standaloneSetup(this.ingredientController).build();
    }

    @Test
    public void testGetListIngredients() throws Exception {
        RecipeCommand command = new RecipeCommand();

        when(recipeService.findRecipeCommandById(anyLong()))
                .thenReturn(command);

        mock.perform(MockMvcRequestBuilders.get("/recipe/1/ingredients"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipes/ingredients/list"))
                .andExpect(model().attributeExists("recipe"));
    }

    @Test
    public void testShowIngredient() throws Exception {
        IngredientCommand command = new IngredientCommand();

        when(this.ingredientService.findByRecipeIdAndIngredientId(anyLong(), anyLong())).thenReturn(command);

        mock.perform(MockMvcRequestBuilders.get("/recipe/1/ingredient/1/show"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipes/ingredients/show"))
                .andExpect(model().attributeExists("ingredient"));

    }

    @Test
    public void testShowIngredientModelAndView() throws Exception {
        IngredientCommand command = new IngredientCommand();
        command.setId(1L);

        when(this.ingredientService.findByRecipeIdAndIngredientId(anyLong(), anyLong())).thenReturn(command);

        mock.perform(MockMvcRequestBuilders.get("/recipe/1/ingredient/1/showmav"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipes/ingredients/show"))
                .andExpect(model().attributeExists("ingredient"))
                .andExpect(model().attribute("ingredient", Matchers.hasProperty("id", Matchers.is(1L))));

    }

    @Test
    public void testUpdateIngredient() throws Exception {
        IngredientCommand command = new IngredientCommand();
        when(this.ingredientService.findByRecipeIdAndIngredientId(anyLong(), anyLong()))
                .thenReturn(command);
        when(this.uomService.getUnitsOfMeasure()).thenReturn(new HashSet<>());

        mock.perform(MockMvcRequestBuilders.get("/recipe/1/ingredient/5/update"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipes/ingredients/form"))
                .andExpect(model().attributeExists("ingredient"))
                .andExpect(model().attributeExists("uomList"));
    }

    @Test
    public void testSaveOrUpdateIngredient() throws Exception {
        IngredientCommand command = new IngredientCommand();
        command.setId(3L);
        command.setRecipeId(2L);
        when(this.ingredientService.saveIngredientCommand(any()))
                .thenReturn(command);

        mock.perform(MockMvcRequestBuilders.post("/recipe/2/ingredient")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("id", "")
                .param("description", "some description")
                .param("amount", "5")
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/recipe/2/ingredient/3/show"));
    }

    @Test
    public void testNewIngredientForm() throws Exception {
        RecipeCommand command = new RecipeCommand();
        command.setId(1L);

        when(this.recipeService.findRecipeCommandById(anyLong())).thenReturn(command);
        when(this.uomService.getUnitsOfMeasure()).thenReturn(new HashSet<>());

        mock.perform(MockMvcRequestBuilders.get("/recipe/1/ingredient/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipes/ingredients/form"))
                .andExpect(model().attributeExists("ingredient"))
                .andExpect(model().attributeExists("uomList"));

        verify(this.recipeService, times(1)).findRecipeCommandById(anyLong());

    }

    @Test
    public void testDeleteIngredient() throws Exception {
        mock.perform(MockMvcRequestBuilders.get("/recipe/1/ingredient/2/delete"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/recipe/1/ingredients"));

        verify(this.ingredientService, times(1))
                .deleteByRecipeIdAndIngredientId(anyLong(), anyLong());
    }
}
