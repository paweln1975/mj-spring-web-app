package pl.paweln.mjspringwebapp.repositories;

import org.springframework.data.repository.CrudRepository;
import pl.paweln.mjspringwebapp.domain.Author;

import java.util.Optional;

public interface AuthorRepository extends CrudRepository<Author, Long> {
    Optional<Author> findByLastName(String lastName);
}
