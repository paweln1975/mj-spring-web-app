package pl.paweln.mjspringwebapp.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import pl.paweln.mjspringwebapp.commands.RecipeCommand;
import pl.paweln.mjspringwebapp.services.ImageService;
import pl.paweln.mjspringwebapp.services.RecipeService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class ImageControllerTest {
    @InjectMocks
    ImageController imageController;

    @Mock
    RecipeService recipeService;

    @Mock
    ImageService imageService;

    MockMvc mock;

    @BeforeEach
    public void setup() {
        mock = MockMvcBuilders.standaloneSetup(this.imageController).build();
    }

    @Test
    public void testGetImageForm() throws Exception {
        RecipeCommand recipeCommand = new RecipeCommand();
        recipeCommand.setId(1L);

        when(this.recipeService.findRecipeCommandById(anyLong())).thenReturn(recipeCommand);

        mock.perform(MockMvcRequestBuilders.get("/recipe/1/newimage"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("recipe"))
                .andExpect(view().name("/recipes/imageform"));

        verify(this.recipeService, times(1)).findRecipeCommandById(anyLong());

    }

    @Test
    public void testHandleImageUpload() throws Exception {
        MockMultipartFile file = new MockMultipartFile("imagefile", "test.txt", "text/plain", "Mastering Spring Boot".getBytes());

        mock.perform(MockMvcRequestBuilders.multipart("/recipe/1/uploadimage").file(file))
                .andExpect(status().is3xxRedirection())
                .andExpect(header().string("Location", "/recipe/1/show"));

        verify(this.imageService, times(1)).saveImageFile(anyLong(), any());
    }

    @Test
    public void testRenderImage() throws Exception {
        RecipeCommand command = new RecipeCommand();
        command.setId(1L);

        String file = "fake image string";
        Byte[] bytes = new Byte[file.getBytes().length];

        int i = 0;
        for (byte b : file.getBytes()) {
            bytes[i++] = b;
        }
        command.setImage(bytes);

        when(this.recipeService.findRecipeCommandById(anyLong())).thenReturn(command);

        MockHttpServletResponse response = mock.perform(MockMvcRequestBuilders.get("/recipe/1/image"))
                .andExpect(status().isOk())
                .andReturn().getResponse();

        byte[] responseBytes = response.getContentAsByteArray();
        assertEquals(bytes.length, responseBytes.length);
    }
}
