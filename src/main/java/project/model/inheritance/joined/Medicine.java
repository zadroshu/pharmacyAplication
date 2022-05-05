package project.model.inheritance.joined;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;

@Entity(name = "JoinedMedicine")
@PrimaryKeyJoinColumn(name = "medicine_id")
@Data
@NoArgsConstructor
public class Medicine extends PharmacyProduct {
    private String activeSubstanceOfTheMeddicine;

    private String categoryOfTheMeddicine;
}
