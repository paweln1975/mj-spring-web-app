package pl.paweln.mjspringwebapp.controllers;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;
import pl.paweln.mjspringwebapp.domain.Recipe;
import pl.paweln.mjspringwebapp.services.RecipeService;

import java.util.HashSet;
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

        Recipe recipe2 = new Recipe();
        recipe2.setId(2L);

        recipeSet = new HashSet<>();
        recipeSet.add(recipe1);
        recipeSet.add(recipe2);

        mock = MockMvcBuilders.standaloneSetup(this.recipeController).build();

    }

    @Test
    public void mvcTest() throws Exception {

        when(this.recipeService.getRecipes()).thenReturn(recipeSet);

        mock.perform(MockMvcRequestBuilders.get("/recipes"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipes/list"))
                .andExpect(model().attribute("recipes", Matchers.hasSize(2)));
    }

    @Test
    public void getRecipesTest() {

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
    public void findRecipesTest() throws Exception {
        mock.perform(MockMvcRequestBuilders.get("/recipes/find"))
                .andExpect(status().isOk())
                .andExpect(view().name("notImplemented"));

        verifyNoInteractions(this.recipeService);
    }

    @Test
    public void getRecipeTest() throws Exception {
        when(this.recipeService.findById(anyLong()))
                .thenReturn(this.recipeSet.iterator().next());

        mock.perform(MockMvcRequestBuilders.get("/recipe/show/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipes/show"));
    }
}
