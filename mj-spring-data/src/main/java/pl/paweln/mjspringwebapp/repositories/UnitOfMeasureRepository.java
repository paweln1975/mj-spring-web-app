package pl.paweln.mjspringwebapp.repositories;

import org.springframework.data.repository.CrudRepository;
import pl.paweln.mjspringwebapp.domain.UnitOfMeasure;

import java.util.Optional;

public interface UnitOfMeasureRepository extends CrudRepository<UnitOfMeasure, Long> {
    Optional<UnitOfMeasure> findByUnitName (String unitName);
}
