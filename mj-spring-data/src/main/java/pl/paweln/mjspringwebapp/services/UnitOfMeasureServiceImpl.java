package pl.paweln.mjspringwebapp.services;

import org.springframework.stereotype.Service;
import pl.paweln.mjspringwebapp.commands.UnitOfMeasureCommand;
import pl.paweln.mjspringwebapp.converters.UoMToUoMCommand;
import pl.paweln.mjspringwebapp.repositories.UnitOfMeasureRepository;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class UnitOfMeasureServiceImpl implements UnitOfMeasureService {

    UnitOfMeasureRepository unitOfMeasureRepository;
    UoMToUoMCommand uoMToUoMCommand;

    public UnitOfMeasureServiceImpl(UnitOfMeasureRepository unitOfMeasureRepository, UoMToUoMCommand uoMToUoMCommand) {
        this.unitOfMeasureRepository = unitOfMeasureRepository;
        this.uoMToUoMCommand = uoMToUoMCommand;
    }

    @Override
    public Set<UnitOfMeasureCommand> getUnitsOfMeasure() {
        return StreamSupport.stream(
                this.unitOfMeasureRepository.findAll().spliterator(), false)
                .map(this.uoMToUoMCommand::convert)
                .collect(Collectors.toSet());
    }
}
