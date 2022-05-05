package project.model.entitiesrelationships.list;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.PrimaryKeyJoinColumn;

@Entity(name = "ListMedicalDevice")
@Data
@NoArgsConstructor
public class MedicalDevice extends PharmacyProduct {

    private String categoryOfPharmacyDevices;
}
