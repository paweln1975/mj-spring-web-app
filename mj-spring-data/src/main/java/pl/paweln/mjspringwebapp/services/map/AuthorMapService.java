package pl.paweln.mjspringwebapp.services.map;

import pl.paweln.mjspringwebapp.domain.Author;
import pl.paweln.mjspringwebapp.services.CrudService;

import java.util.Set;

public class AuthorMapService extends AbstractMapService<Author, Long> implements CrudService<Author, Long> {
    @Override
    public Author findById(Long id) {
        return super.findById(id);
    }

    @Override
    public Author save(Author object) {
        return super.save(object.getId(), object);
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
}
