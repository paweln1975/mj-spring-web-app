package pl.paweln.mjspringwebapp.controllers;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;
import pl.paweln.mjspringwebapp.commands.RecipeCommand;
import pl.paweln.mjspringwebapp.controllers.exceptions.ControllerExceptionHandler;
import pl.paweln.mjspringwebapp.domain.Category;
import pl.paweln.mjspringwebapp.domain.Ingredient;
import pl.paweln.mjspringwebapp.domain.Recipe;
import pl.paweln.mjspringwebapp.exceptions.NotFoundException;
import pl.paweln.mjspringwebapp.services.RecipeService;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class RecipeControllerTest {
    @InjectMocks
    RecipeController recipeController;

    @Mock
    RecipeService recipeService;

    @Mock
    Model model;

    Set<Recipe> recipeSet;

    MockMvc mock;

    @BeforeEach
    public void setup() {
        Recipe recipe1 = new Recipe();
        recipe1.setId(1L);
        recipe1.addIngredient(new Ingredient());
        recipe1.addIngredient(new Ingredient());
        recipe1.getCategorySet().add(new Category());
        recipe1.getCategorySet().add(new Category());


        Recipe recipe2 = new Recipe();
        recipe2.setId(2L);
        recipe2.addIngredient(new Ingredient());
        recipe2.addIngredient(new Ingredient());
        recipe2.getCategorySet().add(new Category());
        recipe2.getCategorySet().add(new Category());


        recipeSet = new HashSet<>();
        recipeSet.add(recipe1);
        recipeSet.add(recipe2);

        mock = MockMvcBuilders.standaloneSetup(this.recipeController)
                .setControllerAdvice(new ControllerExceptionHandler())
                .build();

    }

    @Test
    public void testMVC() throws Exception {

        when(this.recipeService.getRecipes()).thenReturn(recipeSet);

        mock.perform(MockMvcRequestBuilders.get("/recipes"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipes/list"))
                .andExpect(model().attribute("recipes", Matchers.hasSize(2)));
    }

    @Test
    public void testGetRecipes() {

        when(this.recipeService.getRecipes()).thenReturn(recipeSet);

        ArgumentCaptor<Set<Recipe>> captor = ArgumentCaptor.forClass(Set.class);


        String ret = this.recipeController.getRecipes(this.model);
        assertEquals("recipes/list", ret);

        verify(this.recipeService, times(1)).getRecipes();
        verify(this.model, times(1))
                .addAttribute(eq("recipes"), captor.capture());

        Set<Recipe> setInController = captor.getValue();
        assertEquals(2, setInController.size());
    }

    @Test
    public void testShowRecipe() throws Exception {
        when(this.recipeService.findById(anyLong()))
                .thenReturn(this.recipeSet.iterator().next());

        mock.perform(MockMvcRequestBuilders.get("/recipe/1/show"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipes/show"))
                .andExpect(model().attribute("recipe", Matchers.any(Recipe.class)))
                .andExpect(model().attribute("ingredients", Matchers.hasSize(2)))
                .andExpect(model().attribute("categories", Matchers.hasSize(2)));

    }

    @Test
    public void testGetNewRecipeForm() throws Exception {
        RecipeCommand command = new RecipeCommand();

        mock.perform(MockMvcRequestBuilders.get("/recipe/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipes/form"))
                .andExpect(model().attributeExists("recipe"));
    }

    @Test
    public void testPostNewRecipeForm() throws Exception {
        RecipeCommand recipeCommand = new RecipeCommand();
        recipeCommand.setId(2L);

        when(this.recipeService.saveRecipeCommand(any())).thenReturn(recipeCommand);

        mock.perform(MockMvcRequestBuilders.post("/recipe")
                    .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                    .param("id", "")
                    .param("description", "some string")
                    .param("directions", "test directions")
                    .param("prepTime", "5")
                 )
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/recipe/2/show"));
    }

    @Test
    public void testPostNewRecipeFormValidationFail() throws Exception {
        RecipeCommand recipeCommand = new RecipeCommand();
        recipeCommand.setId(2L);

//        when(recipeService.saveRecipeCommand(any())).thenReturn(recipeCommand);

        mock.perform(MockMvcRequestBuilders.post("/recipe")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("id", "")

        )
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("recipe"))
                .andExpect(view().name("recipes/form"));
    }

    @Test
    public void testGetUpdateView() throws Exception {
        RecipeCommand recipeCommand = new RecipeCommand();
        recipeCommand.setId(2L);

        when(this.recipeService.findRecipeCommandById(anyLong())).thenReturn(recipeCommand);

        mock.perform(MockMvcRequestBuilders.get("/recipe/2/update"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipes/form"))
                .andExpect(model().attributeExists("recipe"));

    }

    @Test
    public void testDeleteRecipe() throws Exception {
        mock.perform(MockMvcRequestBuilders.get("/recipe/1/delete"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/recipes"));

        verify(this.recipeService, times(1)).deleteById(anyLong());
    }

    @Test
    public void testFindRecipeReturnOne() throws Exception {
        Recipe recipe = new Recipe();
        recipe.setId(1L);

        when(this.recipeService.findAllByDescriptionLike(anyString())).thenReturn(List.of(recipe));

        mock.perform(MockMvcRequestBuilders.get("/recipes/find"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/recipe/1/show"));

        verify(this.recipeService, times(1)).findAllByDescriptionLike(anyString());
    }

    @Test
    public void testFindRecipeReturnMany() throws Exception {
        Recipe recipe1 = new Recipe();
        recipe1.setId(1L);

        Recipe recipe2 = new Recipe();
        recipe2.setId(2L);



        when(this.recipeService.findAllByDescriptionLike(anyString())).thenReturn(List.of(recipe1, recipe2));

        mock.perform(MockMvcRequestBuilders.get("/recipes/find"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipes/list"));

        verify(this.recipeService, times(1)).findAllByDescriptionLike(anyString());
    }

    @Test
    public void testGetRecipeNotFound() throws Exception {
        Recipe recipe = new Recipe();
        recipe.setId(1L);

        when (this.recipeService.findById(anyLong())).thenThrow(NotFoundException.class);

        mock.perform(MockMvcRequestBuilders.get("/recipe/1/show"))
                .andExpect(status().isNotFound())
                .andExpect(view().name(ControllerExceptionHandler.DEFAULT_ERROR_VIEW));
    }

    @Test
    public void testGetRecipeNumberFormat() throws Exception {

        mock.perform(MockMvcRequestBuilders.get("/recipe/a/show"))
                .andExpect(status().isBadRequest())
                .andExpect(view().name(ControllerExceptionHandler.DEFAULT_ERROR_VIEW));
    }
}
