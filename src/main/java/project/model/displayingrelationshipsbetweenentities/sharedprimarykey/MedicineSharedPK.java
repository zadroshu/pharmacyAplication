package project.model.displayingrelationshipsbetweenentities.sharedprimarykey;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import project.model.inheritance.mappedsuperclass.Medicine;

import javax.persistence.*;

@Entity
@Table(name = "shared_pk_medicine")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MedicineSharedPK {
    @Id
    protected Long id;

    public String name;

    public double coast;

    @OneToOne(fetch = FetchType.LAZY,
            optional = false)
    @PrimaryKeyJoinColumn
    protected CategorySharedPK categorySharedPK;

}
