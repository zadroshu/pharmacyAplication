package project.model.entitiesrelationships.list;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;

@Entity(name = "ListMedicine")
@Data
@NoArgsConstructor
public class Medicine extends PharmacyProduct {
    private String activeSubstanceOfTheMeddicine;

    private String categoryOfTheMeddicine;
}
