package project.model.inheritance.mappedsuperclass;

import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.Entity;

@Entity(name = "MappedSuperClassMedicine")
@Data
@NoArgsConstructor
public class Medicine extends PharmacyProduct{
    private String activeSubstanceOfTheMeddicine;

    private String categoryOfTheMeddicine;
}
