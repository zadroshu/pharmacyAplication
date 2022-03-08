package project.model;

import com.opencsv.bean.CsvBindByName;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Comparator;
import java.util.Objects;

@Entity
@Root(name = "PharmacyProduct")
public class PharmacyProduct implements Serializable {

    @Element(name = "nameOfTheMeddicine")
    @CsvBindByName
    @Column
    protected String nameOfPharmacyProduct;
    @Element(name = "price")
    @CsvBindByName
    @Column
    protected double price;
    @Element(name = "barcode")
    @CsvBindByName
    @Column
    @Id
    protected String barcode;
    @Element(name = "description")
    @CsvBindByName
    @Column
    protected String description;

    public PharmacyProduct() {
    }

    public PharmacyProduct(String nameOfPharmacyProduct, double price, String barcode, String description) {
        this.nameOfPharmacyProduct = nameOfPharmacyProduct;
        this.price = price;
        this.barcode = barcode;
        this.description = description;
    }


    public static class PharmacyProductComparatorMintoMax implements Comparator<PharmacyProduct> {
        @Override
        public int compare(PharmacyProduct o1, PharmacyProduct o2) {
            if (o1.getPrice() == o2.getPrice())
                return 0;
            else if(o1.getPrice() > o2.getPrice())
                return 1;
            else
                return -1;
        }
    }

    public static class PharmacyProductComparatorMaxToMin implements Comparator<PharmacyProduct> {
        @Override
        public int compare(PharmacyProduct o1, PharmacyProduct o2) {
            if (o1.getPrice() == o2.getPrice())
                return 0;
            else if(o1.getPrice() > o2.getPrice())
                return -1;
            else
                return 1;
        }
    }

    public String getNameOfPharmacyProduct() {
        return nameOfPharmacyProduct;
    }

    public void setNameOfPharmacyProduct(String nameOfPharmacyProduct) {
        this.nameOfPharmacyProduct = nameOfPharmacyProduct;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PharmacyProduct)) return false;
        PharmacyProduct that = (PharmacyProduct) o;
        return Double.compare(that.price, price) == 0 && Objects.equals(nameOfPharmacyProduct, that.nameOfPharmacyProduct) && Objects.equals(barcode, that.barcode) && Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nameOfPharmacyProduct, price, barcode, description);
    }

    @Override
    public String toString() {
        return "PharmacyProduct{" +
                "nameOfPharmacyProduct='" + nameOfPharmacyProduct + '\'' +
                ", price=" + price +
                ", barcode=" + barcode +
                ", description='" + description + '\'' +
                '}';
    }
}
