package project.model.displayingrelationshipsbetweenentities.many_to_many_association;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "many_to_many_medicine")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MedicineMTM {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    public String name;

    public double coast;

    @ManyToMany(mappedBy = "medicines")
    protected Set<CategoryMTM> categorys = new HashSet<>();

}
