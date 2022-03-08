package project.model;

import com.opencsv.bean.CsvBindByName;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.util.Objects;
@Entity
@Root(name="MedicalDevices")
public class MedicalDevice extends PharmacyProduct{

    @Element(name = "categoryOfPharmacyDevices")
    @CsvBindByName
    @Column
    private String categoryOfPharmacyDevices;

    public MedicalDevice(){}

    public MedicalDevice(String nameOfPharmacyProduct, double price, String barcode, String description, String categoryOfPharmacyDevices) {
        super(nameOfPharmacyProduct, price, barcode, description);
        this.categoryOfPharmacyDevices = categoryOfPharmacyDevices;

    }

    public String getCategoryOfPharmacyDevices() {
        return categoryOfPharmacyDevices;
    }

    public void setCategoryOfPharmacyDevices(String categoryOfPharmacyDevices) {
        this.categoryOfPharmacyDevices = categoryOfPharmacyDevices;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MedicalDevice)) return false;
        if (!super.equals(o)) return false;
        MedicalDevice that = (MedicalDevice) o;
        return Objects.equals(categoryOfPharmacyDevices, that.categoryOfPharmacyDevices);
    }

    @Override
    public String toString() {
        return "MedicalDevices{" +
                "categoryOfPharmacyDevices='" + categoryOfPharmacyDevices + '\'' +
                '}';
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), categoryOfPharmacyDevices);
    }
}
