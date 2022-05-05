package project.model.inheritance.singletable;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity(name = "SingleTableMedicine")
@Data
@NoArgsConstructor
@DiscriminatorValue("Medicine")
public class Medicine extends PharmacyProduct {
    private String activeSubstanceOfTheMeddicine;

    private String categoryOfTheMeddicine;
}
