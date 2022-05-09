package project.model.displayingrelationshipsbetweenentities.unidirectional;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "unidirectional_medicine")
@Data
@NoArgsConstructor
public class MedicineUnidirectional {
    @Id
    protected Long id;

    public String name;

    public double coast;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "categoryId", nullable = false)
    protected CategoryUnidirectional categoryUnidirectional;

}
