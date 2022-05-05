package project.model;

import javax.persistence.*;
import java.io.Serializable;
@Embeddable
public class Address implements Serializable {
    @Column(name = "ADDRESS_STREET", nullable = false)
    private String street;
    @Column(name = "ADDRESS_ZIPCODE", nullable = false)
    private String zipcode;
    @Column(name = "ADDRESS_CITY", nullable = false)
    private String city;

    public Address() {
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
