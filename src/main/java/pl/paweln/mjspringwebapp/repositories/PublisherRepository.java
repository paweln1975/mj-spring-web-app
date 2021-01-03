package pl.paweln.mjspringwebapp.repositories;

import org.springframework.data.repository.CrudRepository;
import pl.paweln.mjspringwebapp.domain.Publisher;

public interface PublisherRepository extends CrudRepository<Publisher, Long> {

}