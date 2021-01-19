package pl.paweln.mjspringwebapp.bootstrap;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import pl.paweln.mjspringwebapp.domain.Author;
import pl.paweln.mjspringwebapp.services.AuthorService;

@Component
public class DataLoader implements CommandLineRunner {

    private final AuthorService authorService;


    public DataLoader(AuthorService authorService) {
        this.authorService = authorService;
    }

    @Override
    public void run(String... args) throws Exception {
        Author author1 = new Author("Peter", "Jackson");
        Author author2 = new Author("Scott", "Ridley");


        this.authorService.save(author1);
        this.authorService.save(author2);

        System.out.println("Authors loaded into maps service.");
    }
}
