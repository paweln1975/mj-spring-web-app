package pl.paweln.mjspringwebapp.repositories;

import org.springframework.data.repository.CrudRepository;
import pl.paweln.mjspringwebapp.domain.Book;

public interface BookRepository extends CrudRepository<Book, Long> {
}
