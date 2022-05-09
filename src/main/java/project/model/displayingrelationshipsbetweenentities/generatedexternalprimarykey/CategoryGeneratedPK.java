package project.model.displayingrelationshipsbetweenentities.generatedexternalprimarykey;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "generated_pk_category")
@Data
@NoArgsConstructor
public class CategoryGeneratedPK {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @org.hibernate.annotations.GenericGenerator(
            name = "categoryKeyGenerator",
            strategy = "foreign",
            parameters =
                    @org.hibernate.annotations.Parameter(
                            name = "property", value = "medicine"
                    )
    )
    protected Long id;

    public String nameOfCategory;

    @OneToOne(optional = false)
    @PrimaryKeyJoinColumn
    protected MedicineGeneratedPK medicine;

    public CategoryGeneratedPK(MedicineGeneratedPK medicine){
        this.medicine = medicine;
    }


}
