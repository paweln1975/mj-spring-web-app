package pl.paweln.mjspringwebapp.services.springdata;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.paweln.mjspringwebapp.domain.Author;
import pl.paweln.mjspringwebapp.repositories.AuthorRepository;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AuthorJPAServiceTest {

    @Mock
    private AuthorRepository authorRepository;

    @InjectMocks
    private AuthorJPAService authorJPAService;

    Author returnedAuthor;

    @BeforeEach
    public void setUp() {
        returnedAuthor = Author.builder()
                .lastName("Smith")
                .firstName("John")
                .build();
        returnedAuthor.setId(1L);
    }

    @Test
    public void testFindByLastName() {

        when(this.authorRepository.findByLastName("Smith"))
                .thenReturn(Optional.of(returnedAuthor));

        Author author = this.authorJPAService.findByLastName("Smith");

        assertEquals("John", author.getFirstName());
        verify(this.authorRepository).findByLastName("Smith");

    }

    @Test
    public void testFindAll() {
        Set<Author> authorSet = new HashSet<>();
        Author author1 = Author.builder().firstName("Smith").build();
        author1.setId(1L);
        authorSet.add(author1);

        Author author2 = Author.builder().firstName("Jordan").build();
        author2.setId(2L);
        authorSet.add(author2);

        when(this.authorRepository.findAll()).thenReturn(authorSet);

        Set<Author> authors = this.authorJPAService.findAll();

        assertNotNull(authors);
        assertEquals(2, authors.size());
    }

    @Test
    public void testFindById() {
        when(this.authorRepository.findById(anyLong()))
                .thenReturn(Optional.of(this.returnedAuthor));
        Author author = this.authorJPAService.findById(2L);
        assertNotNull(author);

    }

    @Test
    public void testFindByIdNotFound() {
        when(this.authorRepository.findById(anyLong()))
                .thenReturn(Optional.empty());
        Author author = this.authorJPAService.findById(1L);
        assertNull(author);

    }

    @Test
    public void testSave() {
        Author authorToSave = Author.builder().firstName("Michael").lastName("Jordan").build();
        when(this.authorRepository.save(any())).thenReturn(this.returnedAuthor);

        Author savedAuthor = this.authorJPAService.save(authorToSave);
        assertNotNull(savedAuthor);

        verify(this.authorRepository).save(any());
    }

    @Test
    public void testDelete() {
        this.authorJPAService.delete(this.returnedAuthor);
        verify(this.authorRepository, times(1)).delete(any());
    }

    @Test
    public void testDeleteById() {
        this.authorJPAService.deleteById(1L);
        verify(this.authorRepository, times(1)).deleteById(anyLong());
    }
}
