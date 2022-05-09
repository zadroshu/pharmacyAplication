package project.model.displayingrelationshipsbetweenentities.bidirectional;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "bidirectional_category")
@Data
@NoArgsConstructor
public class CategoryBidirectional {

    @Id
    private Long id;

    public String nameOfCategory;

    @OneToMany(mappedBy = "categoryBidirectional",
            fetch =  FetchType.LAZY)
    protected Set<MedicineBidirectional> medicines = new HashSet<>();

}
