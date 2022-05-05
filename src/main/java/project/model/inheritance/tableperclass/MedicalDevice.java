package project.model.inheritance.tableperclass;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;

@Entity(name = "TablePerClassMedicalDevice")
@Data
@NoArgsConstructor
public class MedicalDevice extends PharmacyProduct {

    private String categoryOfPharmacyDevices;
}
