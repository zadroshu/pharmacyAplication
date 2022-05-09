package project.model.collectionsandassociatedentities.set;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity(name = "set_medicine")
@Data
@NoArgsConstructor
public class Medicine extends PharmacyProduct {
    private String activeSubstanceOfTheMeddicine;

    @ElementCollection
    @CollectionTable(
            name = "set_category_of_the_meddicine",
            joinColumns = @JoinColumn(name = "category_id")
    )
    @Column(name = "CategoryUnidirectional")
    protected Set<String> setCategoryOfTheMeddicine = new HashSet<>();

}
