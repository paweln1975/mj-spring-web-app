package pl.paweln.mjspringwebapp.controllers;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;
import pl.paweln.mjspringwebapp.domain.Recipe;
import pl.paweln.mjspringwebapp.services.RecipeService;

import java.util.HashSet;
import java.util.Set;

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
    public void testMVC() throws Exception {
        MockMvc mock = MockMvcBuilders.standaloneSetup(this.recipeController).build();

        mock.perform(MockMvcRequestBuilders.get("/recipes"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("recipes/list"));
    }

    @Test
    public void testGetRecipes() {
        Recipe recipe1 = new Recipe();
        recipe1.setId(1L);

        Recipe recipe2 = new Recipe();
        recipe2.setId(2L);

        Set<Recipe> recipeSet = new HashSet<>();
        recipeSet.add(recipe1);
        recipeSet.add(recipe2);

        Mockito.when(this.recipeService.getRecipes()).thenReturn(recipeSet);

        ArgumentCaptor<Set<Recipe>> captor = ArgumentCaptor.forClass(Set.class);


        String ret = this.recipeController.getRecipes(this.model);
        Assertions.assertEquals("recipes/list", ret);

        Mockito.verify(this.recipeService, Mockito.times(1)).getRecipes();
        Mockito.verify(this.model, Mockito.times(1))
                .addAttribute(Mockito.eq("recipes"), captor.capture());

        Set<Recipe> setInController = captor.getValue();
        Assertions.assertEquals(2, setInController.size());
    }
}
