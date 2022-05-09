package project.model.collectionsandassociatedentities.map;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;

@Entity(name = "map_medical_device")
@Data
@NoArgsConstructor
public class MedicalDevice extends PharmacyProduct {

    private String categoryOfPharmacyDevices;
}
