package project.model;

import com.opencsv.bean.CsvBindByName;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Root(name = "Order")
public class Order implements Serializable {
    @Element(name = "orderId")
    @CsvBindByName
    @Column
    long orderId;
    @Element(name = "pharmacyProductBarcode")
    @CsvBindByName
    @Column
    String pharmacyProductBarcode;

    public Order(){}

    public Order(long orderId, String pharmacyProductBarcode) {
        this.orderId = orderId;
        this.pharmacyProductBarcode = pharmacyProductBarcode;
    }

    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    public String getPharmacyProductBarcode() {
        return pharmacyProductBarcode;
    }

    public void setPharmacyProductBarcode(String pharmacyProductBarcode) {
        this.pharmacyProductBarcode = pharmacyProductBarcode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return orderId == order.orderId && Objects.equals(pharmacyProductBarcode, order.pharmacyProductBarcode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderId, pharmacyProductBarcode);
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderId=" + orderId +
                ", pharmacyProductBarcode='" + pharmacyProductBarcode + '\'' +
                '}';
    }
}
