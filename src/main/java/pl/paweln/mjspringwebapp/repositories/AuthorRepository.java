package pl.paweln.mjspringwebapp.repositories;

import org.springframework.data.repository.CrudRepository;
import pl.paweln.mjspringwebapp.domain.Author;

public interface AuthorRepository extends CrudRepository<Author, Long> {
}
