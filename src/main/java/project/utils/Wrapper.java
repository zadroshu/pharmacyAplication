package project.utils;

import project.model.*;

import java.util.List;
import java.util.Objects;

public class Wrapper {

    public List<User> userList;

    public List<Meddicine> meddicineList;

    public List<MedicalDevice> medicalDeviceList;

    public List<Order> orderList;

    public Wrapper(){}

    public List<Order> getOrderList() {
        return orderList;
    }

    public void setOrderList(List<Order> orderList) {
        this.orderList = orderList;
    }

    public List<User> getUserList() {
        return userList;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }

    public List<Meddicine> getMeddicineList() {
        return meddicineList;
    }

    public void setMeddicineList(List<Meddicine> meddicineList) {
        this.meddicineList = meddicineList;
    }

    public List<MedicalDevice> getMedicalDeviceList() {
        return medicalDeviceList;
    }

    public void setMedicalDeviceList(List<MedicalDevice> medicalDeviceList) {
        this.medicalDeviceList = medicalDeviceList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Wrapper wrapper = (Wrapper) o;
        return Objects.equals(userList, wrapper.userList) && Objects.equals(meddicineList, wrapper.meddicineList) && Objects.equals(medicalDeviceList, wrapper.medicalDeviceList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userList, meddicineList, medicalDeviceList);
    }

    @Override
    public String toString() {
        return "Wrapper{" +
                "userList=" + userList +
                ", meddicineList=" + meddicineList +
                ", medicalDeviceList=" + medicalDeviceList +
                '}';
    }
}
