package project.model.collectionsandassociatedentities.map;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashMap;
import java.util.Map;

@Entity(name = "map_medicine")
@Data
@NoArgsConstructor
public class Medicine extends PharmacyProduct {
    private String activeSubstanceOfTheMeddicine;

    @ElementCollection
    @CollectionTable(name = "map_category_of_the_meddicine")
    @MapKeyColumn(name = "Caegory")
    @Column(name = "MedicineUnidirectional")
    protected Map<String, String> MapCategoryOfTheMeddicine = new HashMap<>();

}
