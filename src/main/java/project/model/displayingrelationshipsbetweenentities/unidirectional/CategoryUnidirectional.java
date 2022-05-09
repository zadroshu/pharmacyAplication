package project.model.displayingrelationshipsbetweenentities.unidirectional;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "unidirectional_category")
@Data
@NoArgsConstructor
public class CategoryUnidirectional {

    @Id
    private Long id;

    public String nameOfCategory;


}
