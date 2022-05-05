package project.model.inheritance.joined;


import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity(name = "JoinedPharmacyProduct")
@Data
@NoArgsConstructor
public class PharmacyProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  long id;

    protected String nameOfPharmacyProduct;

    protected double price;

    protected String barcode;

    protected String description;
}
