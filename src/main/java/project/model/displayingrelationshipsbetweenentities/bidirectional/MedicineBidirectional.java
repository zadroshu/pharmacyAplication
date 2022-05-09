package project.model.displayingrelationshipsbetweenentities.bidirectional;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "bidirectional_medicine")
@Data
@NoArgsConstructor
public class MedicineBidirectional {
    @Id
    protected Long id;

    public String name;

    public double coast;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "categoryId", nullable = false)
    protected CategoryBidirectional categoryBidirectional;


}
