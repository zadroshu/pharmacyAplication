package project.model.inheritance.mappedsuperclass;


import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@MappedSuperclass
public class PharmacyProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  long id;

    protected String nameOfPharmacyProduct;

    protected double price;

    protected String barcode;

    protected String description;
}
