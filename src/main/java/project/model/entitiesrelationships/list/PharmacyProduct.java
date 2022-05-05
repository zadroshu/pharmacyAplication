package project.model.entitiesrelationships.list;


import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity(name = "ListPharmacyProduct")
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
