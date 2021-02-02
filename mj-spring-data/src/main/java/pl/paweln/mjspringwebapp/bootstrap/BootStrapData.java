package pl.paweln.mjspringwebapp.bootstrap;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import pl.paweln.mjspringwebapp.domain.Author;
import pl.paweln.mjspringwebapp.domain.Book;
import pl.paweln.mjspringwebapp.domain.Publisher;
import pl.paweln.mjspringwebapp.repositories.AuthorRepository;
import pl.paweln.mjspringwebapp.repositories.BookRepository;
import pl.paweln.mjspringwebapp.repositories.PublisherRepository;

import java.util.LinkedList;
import java.util.List;

/*
 Better place in web-project
 */
@Component
@Slf4j
public class BootStrapData implements CommandLineRunner {
    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;
    private final PublisherRepository publisherRepository;


    public BootStrapData(AuthorRepository authorRepository, BookRepository bookRepository, PublisherRepository publisherRepository) {
        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
        this.publisherRepository = publisherRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        loadLibraryInitData();
    }

    private void loadLibraryInitData() {
        List<Author> authors = new LinkedList<>();

        authors.add(Author.builder().firstName("Martin").lastName("Wallace").build());
        authors.add(Author.builder().firstName("Jon").lastName("Smith").build());

        Book book = new Book("Mastering Spring Boot", "12345667");
        authors.get(1).getBooks().add(book);
        book.getAuthors().add(authors.get(1));

        Publisher publisher = new Publisher("Helion", "London Street 1", "00-000", "London", "London Area" );
        book.setPublisher(publisher);

        authorRepository.saveAll(authors);
    }
}
