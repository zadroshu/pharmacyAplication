package project.model.inheritance.tableperclass;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;

@Entity(name = "TablePerClassMedicine")
@Data
@NoArgsConstructor
public class Medicine extends PharmacyProduct {
    private String activeSubstanceOfTheMeddicine;

    private String categoryOfTheMeddicine;
}
