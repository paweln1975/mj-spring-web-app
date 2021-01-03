package pl.paweln.mjspringwebapp.bootstrap;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import pl.paweln.mjspringwebapp.domain.Author;
import pl.paweln.mjspringwebapp.domain.Book;
import pl.paweln.mjspringwebapp.domain.Publisher;
import pl.paweln.mjspringwebapp.repositories.AuthorRepository;
import pl.paweln.mjspringwebapp.repositories.BookRepository;
import pl.paweln.mjspringwebapp.repositories.PublisherRepository;

@Component
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

        Publisher publisher = new Publisher("Helion", "London Street 1", "00-000", "London", "London Area" );

        Book book = new Book("Mastering Spring Boot", "12345667");
        book.setPublisher(publisher);

        publisher.getBooks().add(book);

        Author author = new Author("Martin", "Wallace");

        book.getAuthors().add(author);
        author.getBooks().add(book);

        publisherRepository.save(publisher);
        authorRepository.save(author);
        bookRepository.save(book);



    }
}
