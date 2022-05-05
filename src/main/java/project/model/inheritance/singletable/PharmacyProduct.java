package project.model.inheritance.singletable;


import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity(name = "SingleTablePharnacyProduct")
@Data
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorValue("PharmacyProduct")
public class PharmacyProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  long id;

    protected String nameOfPharmacyProduct;

    protected double price;

    protected String barcode;

    protected String description;
}
