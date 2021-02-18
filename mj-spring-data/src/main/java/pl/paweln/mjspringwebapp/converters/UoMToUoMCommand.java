package pl.paweln.mjspringwebapp.converters;

import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import pl.paweln.mjspringwebapp.commands.UnitOfMeasureCommand;
import pl.paweln.mjspringwebapp.domain.UnitOfMeasure;

@Component
public class UoMToUoMCommand implements Converter<UnitOfMeasure, UnitOfMeasureCommand> {
    @Override
    @Synchronized
    @Nullable
    public UnitOfMeasureCommand convert(UnitOfMeasure source) {
        if (source != null) {
            final UnitOfMeasureCommand uomc = new UnitOfMeasureCommand();
            uomc.setId(source.getId());
            uomc.setUnitName(source.getUnitName());
            return uomc;
        }
        return null;
    }
}
