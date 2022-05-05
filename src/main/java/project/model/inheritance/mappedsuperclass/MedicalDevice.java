package project.model.inheritance.mappedsuperclass;

import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.Entity;

@Entity(name = "MappedSuperClassMedicalDevice")
@Data
@NoArgsConstructor
public class MedicalDevice extends PharmacyProduct{

    private String categoryOfPharmacyDevices;
}
