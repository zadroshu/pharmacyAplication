package project.model.collectionsandassociatedentities.set;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;

@Entity(name = "set_medical_device")
@Data
@NoArgsConstructor
public class MedicalDevice extends PharmacyProduct {

    private String categoryOfPharmacyDevices;
}
