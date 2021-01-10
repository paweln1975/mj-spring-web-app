package pl.paweln.mjspringwebapp.services;

import pl.paweln.mjspringwebapp.domain.Author;

public interface AuthorService extends CrudService<Author, Long> {
    Author findByLastName(String lastName);

}
