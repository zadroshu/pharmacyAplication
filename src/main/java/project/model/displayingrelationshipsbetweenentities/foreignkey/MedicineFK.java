package project.model.displayingrelationshipsbetweenentities.foreignkey;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "foreign_medicine")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MedicineFK {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    public String name;

    public double coast;

    @OneToOne(fetch = FetchType.LAZY,
            optional = false,
            cascade = CascadeType.PERSIST)
    @JoinColumn(unique = true)
    protected CategoryFK categoryFK;

}
