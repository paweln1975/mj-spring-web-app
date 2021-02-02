package pl.paweln.mjspringwebapp.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Ingredient {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    private BigDecimal amount;
    private String description;

    @OneToOne (fetch = FetchType.EAGER)
    private UnitOfMeasure unitOfMeasure;

    @ManyToOne
    private Recipe recipe;

    public Ingredient(BigDecimal amount, String description, UnitOfMeasure unitOfMeasure) {
        this.amount = amount;
        this.description = description;
        this.unitOfMeasure = unitOfMeasure;
    }
}
