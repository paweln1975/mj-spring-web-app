package pl.paweln.mjspringwebapp.repositories;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import pl.paweln.mjspringwebapp.domain.UnitOfMeasure;

import java.util.Optional;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class UnitOfMeasureRepositoryTest {

    @Autowired
    UnitOfMeasureRepository repository;

    @Test
    public void testFindByDescriptionTeaTest() {
        String value = "Teaspoon";
        Optional<UnitOfMeasure> unitOfMeasure = this.repository.findByUnitName(value);
        Assertions.assertEquals(value, unitOfMeasure.get().getUnitName());
    }

    @Test
    public void testFindByDescriptionMlTest() {
        String value = "ml";
        Optional<UnitOfMeasure> unitOfMeasure = this.repository.findByUnitName(value);
        Assertions.assertEquals(value, unitOfMeasure.get().getUnitName());
    }
}
