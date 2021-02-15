package pl.paweln.mjspringwebapp.services.springdata;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import pl.paweln.mjspringwebapp.domain.Author;
import pl.paweln.mjspringwebapp.repositories.AuthorRepository;
import pl.paweln.mjspringwebapp.services.AuthorService;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@Profile("jpa")
public class AuthorJPAService implements AuthorService {
    private final AuthorRepository authorRepository;

    public AuthorJPAService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Override
    public Author findByLastName(String lastName) {
        return this.authorRepository.findByLastName(lastName).get();
    }

    @Override
    public Author findById(Long id) {
        Optional<Author> optionalAuthor = this.authorRepository.findById(id);
        if (optionalAuthor.isPresent()) {
            return optionalAuthor.get();
        } else {
            return optionalAuthor.orElse(null);
        }
    }

    @Override
    public Author save(Author object) {
        return this.authorRepository.save(object);
    }

    @Override
    public Set<Author> findAll() {
        Set<Author> output = new HashSet<>();
        this.authorRepository.findAll().iterator().forEachRemaining(output::add);
        return output;
    }

    @Override
    public void delete(Author object) {
        this.authorRepository.delete(object);
    }

    @Override
    public void deleteById(Long id) {
        this.authorRepository.deleteById(id);
    }
}
