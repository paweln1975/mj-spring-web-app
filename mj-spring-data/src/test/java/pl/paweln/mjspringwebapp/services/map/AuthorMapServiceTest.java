package pl.paweln.mjspringwebapp.services.map;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.paweln.mjspringwebapp.domain.Author;

import java.util.Set;

public class AuthorMapServiceTest {

    AuthorMapService authorMapService;
    String lastName = "Cocker";

    @BeforeEach
    public void setUp() {
        this.authorMapService = new AuthorMapService();
        this.authorMapService.save(Author.builder()
                .lastName(this.lastName).firstName("Joe").build());

    }

    @Test
    public void testFindAll() {
        Set<Author> authorSet = this.authorMapService.findAll();
        Assertions.assertEquals(1, authorSet.size());
    }

    @Test
    public void testFindById() {
        Author author = this.authorMapService.findById(1L);
        Assertions.assertEquals("Joe", author.getFirstName());
    }

    @Test
    public void testSave() {
        Author author = this.authorMapService.save(
                Author.builder().firstName("Ali").lastName("Baba").build()
        );

        Assertions.assertEquals(2L, author.getId());
    }

    @Test
    public void testDeleteById() {
        this.authorMapService.deleteById(1L);
        Set<Author> authorSet = this.authorMapService.findAll();
        Assertions.assertEquals(0, authorSet.size());
    }

    @Test
    public void testDelete() {
        this.authorMapService.delete(this.authorMapService.findById(1L));
        Assertions.assertEquals(0, this.authorMapService.findAll().size());
    }

    @Test
    public void testFindByLastName() {
        Author author = this.authorMapService.findByLastName(this.lastName);
        Assertions.assertNotNull(author);
        Assertions.assertEquals(this.lastName, author.getLastName());
    }

    @Test
    public void testFindByLastNameNotFound() {
        Author author = this.authorMapService.findByLastName("Foo");
        Assertions.assertNull(author);
    }

}
