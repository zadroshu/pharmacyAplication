package project.model.inheritance.singletable;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity(name = "SingleTableMedicalDevice")
@Data
@NoArgsConstructor
@DiscriminatorValue("MedicalDevice")
public class MedicalDevice extends PharmacyProduct {

    private String categoryOfPharmacyDevices;
}
