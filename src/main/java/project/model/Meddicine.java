package project.model;

import com.opencsv.bean.CsvBindByName;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.util.Comparator;
import java.util.Objects;
@Entity
@Root(name = "Meddicine")
public class Meddicine extends PharmacyProduct {

    @Element(name = "activeSubstanceOfTheMeddicine")
    @CsvBindByName
    @Column
    private String activeSubstanceOfTheMeddicine;
    @Element(name = "categoryOfTheMeddicine")
    @CsvBindByName
    @Column
    private String categoryOfTheMeddicine;

    public Meddicine() {
    }

    public Meddicine(String nameOfPharmacyProduct, double price, String barcode, String description, String activeSubstanceOfTheMeddicine, String categoryOfTheMeddicine) {
        super(nameOfPharmacyProduct, price, barcode, description);
        this.activeSubstanceOfTheMeddicine = activeSubstanceOfTheMeddicine;
        this.categoryOfTheMeddicine = categoryOfTheMeddicine;
    }


    public String getActiveSubstanceOfTheMeddicine() {
        return activeSubstanceOfTheMeddicine;
    }

    public void setActiveSubstanceOfTheMeddicine(String activeSubstanceOfTheMeddicine) {
        this.activeSubstanceOfTheMeddicine = activeSubstanceOfTheMeddicine;
    }

    public String getCategoryOfTheMeddicine() {
        return categoryOfTheMeddicine;
    }

    public void setCategoryOfTheMeddicine(String categoryOfTheMeddicine) {
        this.categoryOfTheMeddicine = categoryOfTheMeddicine;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Meddicine)) return false;
        if (!super.equals(o)) return false;
        Meddicine meddicine = (Meddicine) o;
        return Objects.equals(activeSubstanceOfTheMeddicine, meddicine.activeSubstanceOfTheMeddicine) && Objects.equals(categoryOfTheMeddicine, meddicine.categoryOfTheMeddicine);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), activeSubstanceOfTheMeddicine, categoryOfTheMeddicine);
    }

    @Override
    public String toString() {
        return "Meddicine{" +
                "activeSubstanceOfTheMeddicine='" + activeSubstanceOfTheMeddicine + '\'' +
                ", categoryOfTheMeddicine='" + categoryOfTheMeddicine + '\'' +
                '}';
    }
}

class SortMeddicineMaxToMin implements Comparator<Meddicine> {
    @Override
    public int compare(Meddicine o1, Meddicine o2) {
        return (int) (o1.getPrice() - o2.getPrice());
    }
}

class SortMeddicineMinToMax implements Comparator<Meddicine> {
    @Override
    public int compare(Meddicine o1, Meddicine o2) {
        return (int) (o2.getPrice() - o1.getPrice());
    }
}
