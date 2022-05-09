package project.model.displayingrelationshipsbetweenentities.sharedprimarykey;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "shared_pk_category")
@Data
@NoArgsConstructor
public class CategorySharedPK {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    public String nameOfCategory;


}
