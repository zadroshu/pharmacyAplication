package project.model.inheritance.service;

import org.junit.jupiter.api.Test;
import project.model.inheritance.tableperclass.MedicalDevice;
import project.model.inheritance.tableperclass.Medicine;
import project.model.inheritance.tableperclass.PharmacyProduct;

import static org.junit.jupiter.api.Assertions.*;

class TablePerClassServiceTest {

    @Test
    void addPharmacyProduct() {
        try {
            System.out.println("addPharmacyProduct");
            TablePerClassService tablePerClassService = new TablePerClassService();

            PharmacyProduct pharmacyProduct = new PharmacyProduct();

            pharmacyProduct.setNameOfPharmacyProduct("pharmacyProduct");
            pharmacyProduct.setDescription("some product");
            pharmacyProduct.setBarcode("asd4421");
            pharmacyProduct.setPrice(11110);

            tablePerClassService.addPharmacyProduct(pharmacyProduct);

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
            TablePerClassService tablePerClassService = new TablePerClassService();



            if (tablePerClassService.getAllPharmacyProduct().isPresent()){
                for (PharmacyProduct pharmacyProduct : tablePerClassService.getAllPharmacyProduct().get()) {
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
            TablePerClassService tablePerClassService = new TablePerClassService();

            MedicalDevice medicalDevice = new MedicalDevice();

            medicalDevice.setNameOfPharmacyProduct("clisma ");
            medicalDevice.setDescription("clisma");
            medicalDevice.setBarcode("clis123ma123");
            medicalDevice.setPrice(250);
            medicalDevice.setCategoryOfPharmacyDevices("discription of clisma");

            tablePerClassService.addPharmacyProduct(medicalDevice);

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
            TablePerClassService tablePerClassService = new TablePerClassService();

            MedicalDevice medicalDevice = new MedicalDevice();

            medicalDevice.setId(2);
            medicalDevice.setNameOfPharmacyProduct("pressure meter ");
            medicalDevice.setDescription("pressure meter");
            medicalDevice.setBarcode("bar12pressure meterrw112");
            medicalDevice.setPrice(1800);
            medicalDevice.setCategoryOfPharmacyDevices("pressure meter");

            tablePerClassService.updateMedicalDevice(medicalDevice);


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
            TablePerClassService tablePerClassService = new TablePerClassService();



            if (tablePerClassService.getAllMedicalDevice().isPresent()){
                for (MedicalDevice medicalDevice : tablePerClassService.getAllMedicalDevice().get()) {
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
            TablePerClassService tablePerClassService = new TablePerClassService();

            MedicalDevice medicalDevice =tablePerClassService.getMedicalDevice(2).get();

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
            TablePerClassService tablePerClassService = new TablePerClassService();

            tablePerClassService.deleteMedicalDevice(1);
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
            TablePerClassService tablePerClassService = new TablePerClassService();

            Medicine medicine = new Medicine();

            medicine.setNameOfPharmacyProduct("Antibiotik ");
            medicine.setDescription("Antibiotik");
            medicine.setBarcode("asd12444");
            medicine.setPrice(550);
            medicine.setActiveSubstanceOfTheMeddicine("pinicilin");
            medicine.setCategoryOfTheMeddicine("Antibiotics");

            tablePerClassService.addPharmacyProduct(medicine);

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
            TablePerClassService tablePerClassService = new TablePerClassService();

            Medicine medicine = new Medicine();

            medicine.setId(2);
            medicine.setNameOfPharmacyProduct("Activirovany ygol ");
            medicine.setDescription("helps you with stomach pain");
            medicine.setBarcode("sldfkjskldf1234");
            medicine.setPrice(340);
            medicine.setActiveSubstanceOfTheMeddicine("ygol");
            medicine.setCategoryOfTheMeddicine("For the stomach");

            tablePerClassService.updateMedicine(medicine);


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
            TablePerClassService tablePerClassService = new TablePerClassService();

            if (tablePerClassService.getAllMedicine().isPresent()){
                for (Medicine medicine : tablePerClassService.getAllMedicine().get()) {
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
            TablePerClassService tablePerClassService = new TablePerClassService();

            Medicine medicine = tablePerClassService.getMedicine(2).get();

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
            TablePerClassService tablePerClassService = new TablePerClassService();

            tablePerClassService.deleteMedicine(2);
            assertTrue(true);

        }catch (Exception e){
            e.printStackTrace();
            fail();
        }
    }
}