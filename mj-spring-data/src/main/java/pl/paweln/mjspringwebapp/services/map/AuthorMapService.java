package pl.paweln.mjspringwebapp.services.map;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import pl.paweln.mjspringwebapp.domain.Author;
import pl.paweln.mjspringwebapp.services.AuthorService;

import java.util.Set;

@Service
@Profile({"default", "dev"})
public class AuthorMapService extends AbstractMapService<Author, Long> implements AuthorService {
    @Override
    public Author findById(Long id) {
        return super.findById(id);
    }

    // better place to implement this is AbstractMapService but I dont not have BaseEntity class
    @Override
    public Author save(Author author) {
        if (author != null) {
            if (author.getId() == null) {
                author.setId(super.getNextId());
            }
            return super.save(author.getId(), author);
        } else {
            throw new RuntimeException("Author is null");
        }
    }

    @Override
    public Set<Author> findAll() {
        return super.findAll();
    }

    @Override
    public void deleteById(Long id) {
        super.deleteById(id);
    }

    @Override
    public void delete(Author object) {
        super.delete(object);
    }

    @Override
    public Author findByLastName(String lastName) {
        return this.findAll()
                .stream()
                .filter(author -> author.getLastName().equalsIgnoreCase(lastName))
                .findFirst()
                .orElse(null);
    }
}
