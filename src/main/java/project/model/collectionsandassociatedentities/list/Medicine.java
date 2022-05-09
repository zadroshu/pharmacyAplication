package project.model.collectionsandassociatedentities.list;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "list_medicine")
@Data
@NoArgsConstructor
public class Medicine extends PharmacyProduct {
    private String activeSubstanceOfTheMeddicine;

    @ElementCollection
    @CollectionTable(name = "list_category_of_the_meddicine")
    @OrderColumn
    @Column(name = "CategoryUnidirectional")
    protected List<String> listCategoryOfTheMeddicine = new ArrayList<>();

}
