package pl.paweln.mjspringwebapp.services;

import pl.paweln.mjspringwebapp.commands.UnitOfMeasureCommand;

import java.util.Set;

public interface UnitOfMeasureService {
    Set<UnitOfMeasureCommand> getUnitsOfMeasure();
}
