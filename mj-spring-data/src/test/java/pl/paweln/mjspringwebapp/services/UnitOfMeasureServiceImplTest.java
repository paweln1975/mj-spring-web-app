package pl.paweln.mjspringwebapp.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import pl.paweln.mjspringwebapp.commands.UnitOfMeasureCommand;
import pl.paweln.mjspringwebapp.converters.UoMToUoMCommand;
import pl.paweln.mjspringwebapp.domain.UnitOfMeasure;
import pl.paweln.mjspringwebapp.repositories.UnitOfMeasureRepository;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class UnitOfMeasureServiceImplTest {

    @Mock
    UnitOfMeasureRepository unitOfMeasureRepository;

    UnitOfMeasureService unitOfMeasureService;
    UoMToUoMCommand command;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        this.command = new UoMToUoMCommand();
        this.unitOfMeasureService = new UnitOfMeasureServiceImpl(
                this.unitOfMeasureRepository, this.command);
    }

    @Test
    public void testGetUnitsOfMeasure() {
        Set<UnitOfMeasure> unitOfMeasures = new HashSet<>();
        UnitOfMeasure unit1 = new UnitOfMeasure();
        unit1.setId(1L);
        unit1.setUnitName("Teaspoon");

        UnitOfMeasure unit2 = new UnitOfMeasure();
        unit2.setId(1L);
        unit2.setUnitName("g");

        unitOfMeasures.add(unit1);
        unitOfMeasures.add(unit2);

        when(this.unitOfMeasureRepository.findAll()).thenReturn(unitOfMeasures);

        Set<UnitOfMeasureCommand> unitOfMeasureCommands = this.unitOfMeasureService.getUnitsOfMeasure();

        assertEquals(2, unitOfMeasureCommands.size());
        verify(this.unitOfMeasureRepository, times(1)).findAll();

    }
}
