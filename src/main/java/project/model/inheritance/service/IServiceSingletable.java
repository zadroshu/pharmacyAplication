package project.model.inheritance.service;

import project.model.inheritance.singletable.MedicalDevice;
import project.model.inheritance.singletable.Medicine;
import project.model.inheritance.singletable.PharmacyProduct;

import java.util.List;
import java.util.Optional;

public interface IServiceSingletable {
    public void addPharmacyProduct(PharmacyProduct pharmacyProduct);
    public void updatePharmacyProduct(PharmacyProduct pharmacyProduct);
    public Optional<List<PharmacyProduct>> getAllPharmacyProduct();
    public Optional<PharmacyProduct> getPharmacyProduct(long id);
    public void deletePharmacyProduct(long id);

    public void addMedicalDevice(MedicalDevice medicalDevice);
    public void updateMedicalDevice(MedicalDevice pharmacyProduct);
    public Optional<List<MedicalDevice>> getAllMedicalDevice();
    public Optional<MedicalDevice> getMedicalDevice(long id);
    public void deleteMedicalDevice(long id);

    public void addMedicine(Medicine medicine);
    public void updateMedicine(Medicine medicine);
    public Optional<List<Medicine>> getAllMedicine();
    public Optional<Medicine> getMedicine(long id);
    public void deleteMedicine(long id);
}
