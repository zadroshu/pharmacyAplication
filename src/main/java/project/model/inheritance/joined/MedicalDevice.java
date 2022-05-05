package project.model.inheritance.joined;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.PrimaryKeyJoinColumn;

@Entity(name = "JoinedMedicalDevice")
@Inheritance(strategy = InheritanceType.JOINED)
@PrimaryKeyJoinColumn(name = "medical_device_id")
@Data
@NoArgsConstructor
public class MedicalDevice extends PharmacyProduct {

    private String categoryOfPharmacyDevices;
}
