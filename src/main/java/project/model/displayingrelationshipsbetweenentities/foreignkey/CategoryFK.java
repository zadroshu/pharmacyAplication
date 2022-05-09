package project.model.displayingrelationshipsbetweenentities.foreignkey;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "foreign_category")
@Data
@NoArgsConstructor
public class CategoryFK {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    public String nameOfCategory;


}
