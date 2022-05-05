package project.model.inheritance.tableperclass;


import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity(name = "TablePerClassPharmacyProduct")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Data
@NoArgsConstructor
public class PharmacyProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private  long id;

    protected String nameOfPharmacyProduct;

    protected double price;

    protected String barcode;

    protected String description;
}
