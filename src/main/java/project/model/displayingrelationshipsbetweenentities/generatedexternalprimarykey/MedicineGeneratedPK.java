package project.model.displayingrelationshipsbetweenentities.generatedexternalprimarykey;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "generated_pk_medicine")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MedicineGeneratedPK {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    public String name;

    public double coast;

    @OneToOne(mappedBy = "medicine",
            cascade = CascadeType.PERSIST)
    @PrimaryKeyJoinColumn
    protected CategoryGeneratedPK categorySharedPK;

}
