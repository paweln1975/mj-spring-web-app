package pl.paweln.mjspringwebapp.commands;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@NoArgsConstructor
@ToString
public class UnitOfMeasureCommand {
    private Long id;
    private String unitName;
}
