package project.model.displayingrelationshipsbetweenentities.many_to_many_association;

import lombok.Data;
import lombok.NoArgsConstructor;
import project.model.displayingrelationshipsbetweenentities.many_to_many_association.MedicineMTM;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "many_to_many_category")
@Data
@NoArgsConstructor
public class CategoryMTM {

    @Id
    protected Long id;

    public String nameOfCategory;

    @ManyToMany(cascade = CascadeType.PERSIST)
    @JoinTable(
            name = "category_medicine",
            joinColumns = @JoinColumn(name = "category_id"),
            inverseJoinColumns = @JoinColumn(name = "medicine_id")
    )
    protected Set<MedicineMTM> medicines = new HashSet<>();



}
