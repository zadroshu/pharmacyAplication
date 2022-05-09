package project.model.inheritance.service;

import org.junit.jupiter.api.Test;
import project.model.inheritance.mappedsuperclass.MedicalDevice;
import project.model.inheritance.mappedsuperclass.Medicine;
import project.model.inheritance.mappedsuperclass.PharmacyProduct;

import static org.junit.jupiter.api.Assertions.*;

class MappedSuperClassServiceTest {

    @Test
    void addPharmacyProduct() {
        try {
            System.out.println("addPharmacyProduct");
            MappedSuperClassService mappedSuperClassService = new MappedSuperClassService();

            PharmacyProduct pharmacyProduct = new PharmacyProduct();

            pharmacyProduct.setNameOfPharmacyProduct("Pinicilin");
            pharmacyProduct.setDescription("kill all bacterias");
            pharmacyProduct.setBarcode("bar12387112");
            pharmacyProduct.setPrice(240);

            mappedSuperClassService.addPharmacyProduct(pharmacyProduct);

            assertTrue(true);

        }catch (Exception e){
            e.printStackTrace();
            fail();
        }

    }

    @Test
    void updatePharmacyProduct() {
    }

    @Test
    void getAllPharmacyProduct() {
        try {
            System.out.println("addPharmacyProduct");
            MappedSuperClassService mappedSuperClassService = new MappedSuperClassService();



            if (mappedSuperClassService.getAllPharmacyProduct().isPresent()){
                for (PharmacyProduct pharmacyProduct : mappedSuperClassService.getAllPharmacyProduct().get()) {
                    System.out.println("\nName : " + pharmacyProduct.getNameOfPharmacyProduct() + "\n"
                    + "Price" + pharmacyProduct.getPrice() + "\n"
                    + "Description " + pharmacyProduct.getDescription());
                }


            }else {
                System.out.println("No such element!");
            }

            assertTrue(true);

        }catch (Exception e){
            e.printStackTrace();
            fail();
        }
    }

    @Test
    void deletePharmacyProduct() {
    }


    @Test
    void testUpdatePharmacyProduct() {
    }


    @Test
    void addMedicalDevice() {
        try {
            System.out.println("addMedicalDevice");
            MappedSuperClassService mappedSuperClassService = new MappedSuperClassService();

            MedicalDevice medicalDevice = new MedicalDevice();

            medicalDevice.setNameOfPharmacyProduct("clisma ");
            medicalDevice.setDescription("clisma");
            medicalDevice.setBarcode("clis123ma123");
            medicalDevice.setPrice(250);
            medicalDevice.setCategoryOfPharmacyDevices("discription of clisma");

            mappedSuperClassService.addPharmacyProduct(medicalDevice);

            assertTrue(true);

        }catch (Exception e){
            e.printStackTrace();
            fail();
        }
    }

    @Test
    void updateMedicalDevice() {
        try {
            System.out.println("updateMedicalDevice");
            MappedSuperClassService mappedSuperClassService = new MappedSuperClassService();

            MedicalDevice medicalDevice = new MedicalDevice();

            medicalDevice.setId(2);
            medicalDevice.setNameOfPharmacyProduct("pressure meter ");
            medicalDevice.setDescription("pressure meter");
            medicalDevice.setBarcode("bar12pressure meterrw112");
            medicalDevice.setPrice(1800);
            medicalDevice.setCategoryOfPharmacyDevices("pressure meter");

            mappedSuperClassService.updateMedicalDevice(medicalDevice);


            assertTrue(true);

        }catch (Exception e){
            e.printStackTrace();
            fail();
        }
    }


    @Test
    void getAllMedicalDevice() {
        try {
            System.out.println("getAllMedicalDevice");
            MappedSuperClassService mappedSuperClassService = new MappedSuperClassService();



            if (mappedSuperClassService.getAllMedicalDevice().isPresent()){
                for (MedicalDevice medicalDevice : mappedSuperClassService.getAllMedicalDevice().get()) {
                    System.out.println("\nName : " + medicalDevice.getNameOfPharmacyProduct() + "\n"
                            + "Price : " + medicalDevice.getPrice() + "\n"
                            + "Description : "  + medicalDevice.getDescription() + "\n"
                            + "CategoryUnidirectional : " + medicalDevice.getCategoryOfPharmacyDevices());
                }


            }else {
                System.out.println("No such element!");
            }

            assertTrue(true);

        }catch (Exception e){
            e.printStackTrace();
            fail();
        }
    }

    @Test
    void getMedicalDevice() {
        try {
            System.out.println("updateMedicalDevice");
            MappedSuperClassService mappedSuperClassService = new MappedSuperClassService();

            MedicalDevice medicalDevice =mappedSuperClassService.getMedicalDevice(2).get();

            System.out.println("\nName : " + medicalDevice.getNameOfPharmacyProduct() + "\n"
                    + "Price : " + medicalDevice.getPrice() + "\n"
                    + "Description : "  + medicalDevice.getDescription() + "\n"
                    + "CategoryUnidirectional : " + medicalDevice.getCategoryOfPharmacyDevices());

            assertTrue(true);

        }catch (Exception e){
            e.printStackTrace();
            fail();
        }
    }

    @Test
    void deleteMedicalDevice() {
        try {
            System.out.println("deleteMedicalDevice");
            MappedSuperClassService mappedSuperClassService = new MappedSuperClassService();

            mappedSuperClassService.deleteMedicalDevice(1);
            assertTrue(true);

        }catch (Exception e){
            e.printStackTrace();
            fail();
        }
    }

    @Test
    void addMedicine() {
        try {
            System.out.println("addMedicine");
            MappedSuperClassService mappedSuperClassService = new MappedSuperClassService();

            Medicine medicine = new Medicine();

            medicine.setNameOfPharmacyProduct("Activirovany ygol ");
            medicine.setDescription("helps you with stomach pain");
            medicine.setBarcode("sldfkjskldf1234");
            medicine.setPrice(550);
            medicine.setActiveSubstanceOfTheMeddicine("ygol");
            medicine.setCategoryOfTheMeddicine("For the stomach");

            mappedSuperClassService.addPharmacyProduct(medicine);

            assertTrue(true);

        }catch (Exception e){
            e.printStackTrace();
            fail();
        }
    }

    @Test
    void updateMedicine() {
        try {
            System.out.println("updateMedicine");
            MappedSuperClassService mappedSuperClassService = new MappedSuperClassService();

            Medicine medicine = new Medicine();

            medicine.setId(2);
            medicine.setNameOfPharmacyProduct("Activirovany ygol ");
            medicine.setDescription("helps you with stomach pain");
            medicine.setBarcode("sldfkjskldf1234");
            medicine.setPrice(340);
            medicine.setActiveSubstanceOfTheMeddicine("ygol");
            medicine.setCategoryOfTheMeddicine("For the stomach");

            mappedSuperClassService.updateMedicine(medicine);


            assertTrue(true);

        }catch (Exception e){
            e.printStackTrace();
            fail();
        }
    }

    @Test
    void getAllMedicine() {
        try {
            System.out.println("getAllMedicine");
            MappedSuperClassService mappedSuperClassService = new MappedSuperClassService();

            if (mappedSuperClassService.getAllMedicine().isPresent()){
                for (Medicine medicine : mappedSuperClassService.getAllMedicine().get()) {
                    System.out.println("\nName : " + medicine.getNameOfPharmacyProduct() + "\n"
                            + "Price : " + medicine.getPrice() + "\n"
                            + "Description : "  + medicine.getDescription() + "\n"
                            + "CategoryUnidirectional : " + medicine.getCategoryOfTheMeddicine() + "\n"
                            + "Active substance : " + medicine.getActiveSubstanceOfTheMeddicine());
                }
            }else {
                System.out.println("No such element!");
            }

            assertTrue(true);

        }catch (Exception e){
            e.printStackTrace();
            fail();
        }
    }

    @Test
    void getMedicine() {
        try {
            System.out.println("getMedicineBidirectional");
            MappedSuperClassService mappedSuperClassService = new MappedSuperClassService();

            Medicine medicine = mappedSuperClassService.getMedicine(2).get();

            System.out.println("\nName : " + medicine.getNameOfPharmacyProduct() + "\n"
                    + "Price : " + medicine.getPrice() + "\n"
                    + "Description : "  + medicine.getDescription() + "\n"
                    + "CategoryUnidirectional : " + medicine.getCategoryOfTheMeddicine() + "\n"
                    + "Active substance : " + medicine.getActiveSubstanceOfTheMeddicine());

            assertTrue(true);

        }catch (Exception e){
            e.printStackTrace();
            fail();
        }
    }

    @Test
    void deleteMedicine() {
        try {
            System.out.println("deleteMedicine");
            MappedSuperClassService mappedSuperClassService = new MappedSuperClassService();

            mappedSuperClassService.deleteMedicine(2);
            assertTrue(true);

        }catch (Exception e){
            e.printStackTrace();
            fail();
        }
    }
}