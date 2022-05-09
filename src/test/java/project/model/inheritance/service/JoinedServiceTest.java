package project.model.inheritance.service;

import org.junit.jupiter.api.Test;
import project.model.inheritance.joined.MedicalDevice;
import project.model.inheritance.joined.Medicine;
import project.model.inheritance.joined.PharmacyProduct;

import static org.junit.jupiter.api.Assertions.*;

class JoinedServiceTest {
    @Test
    void addPharmacyProduct() {
        try {
            System.out.println("addPharmacyProduct");
            JoinedService joinedService = new JoinedService();

            PharmacyProduct pharmacyProduct = new PharmacyProduct();

            pharmacyProduct.setNameOfPharmacyProduct("Pinicilin");
            pharmacyProduct.setDescription("kill all bacterias");
            pharmacyProduct.setBarcode("bar12387112");
            pharmacyProduct.setPrice(240);

            joinedService.addPharmacyProduct(pharmacyProduct);

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
            JoinedService joinedService = new JoinedService();



            if (joinedService.getAllPharmacyProduct().isPresent()){
                for (PharmacyProduct pharmacyProduct : joinedService.getAllPharmacyProduct().get()) {
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
            JoinedService joinedService = new JoinedService();

            MedicalDevice medicalDevice = new MedicalDevice();

            medicalDevice.setNameOfPharmacyProduct("clisma ");
            medicalDevice.setDescription("clisma");
            medicalDevice.setBarcode("clis123ma123");
            medicalDevice.setPrice(250);
            medicalDevice.setCategoryOfPharmacyDevices("discription of clisma");

            joinedService.addPharmacyProduct(medicalDevice);

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
            JoinedService joinedService = new JoinedService();

            MedicalDevice medicalDevice = new MedicalDevice();

            medicalDevice.setId(2);
            medicalDevice.setNameOfPharmacyProduct("pressure meter ");
            medicalDevice.setDescription("pressure meter");
            medicalDevice.setBarcode("bar12pressure meterrw112");
            medicalDevice.setPrice(1800);
            medicalDevice.setCategoryOfPharmacyDevices("pressure meter");

            joinedService.updateMedicalDevice(medicalDevice);


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
            JoinedService joinedService = new JoinedService();



            if (joinedService.getAllMedicalDevice().isPresent()){
                for (MedicalDevice medicalDevice : joinedService.getAllMedicalDevice().get()) {
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
            JoinedService joinedService = new JoinedService();

            MedicalDevice medicalDevice =joinedService.getMedicalDevice(2).get();

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
            JoinedService joinedService = new JoinedService();

            joinedService.deleteMedicalDevice(1);
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
            JoinedService joinedService = new JoinedService();

            Medicine medicine = new Medicine();

            medicine.setNameOfPharmacyProduct("Activirovany ygol ");
            medicine.setDescription("helps you with stomach pain");
            medicine.setBarcode("sldfkjskldf1234");
            medicine.setPrice(550);
            medicine.setActiveSubstanceOfTheMeddicine("ygol");
            medicine.setCategoryOfTheMeddicine("For the stomach");

            joinedService.addPharmacyProduct(medicine);

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
            JoinedService joinedService = new JoinedService();

            Medicine medicine = new Medicine();

            medicine.setId(2);
            medicine.setNameOfPharmacyProduct("Activirovany ygol ");
            medicine.setDescription("helps you with stomach pain");
            medicine.setBarcode("sldfkjskldf1234");
            medicine.setPrice(340);
            medicine.setActiveSubstanceOfTheMeddicine("ygol");
            medicine.setCategoryOfTheMeddicine("For the stomach");

            joinedService.updateMedicine(medicine);


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
            JoinedService joinedService = new JoinedService();

            if (joinedService.getAllMedicine().isPresent()){
                for (Medicine medicine : joinedService.getAllMedicine().get()) {
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
            JoinedService joinedService = new JoinedService();

            Medicine medicine = joinedService.getMedicine(2).get();

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
            JoinedService joinedService = new JoinedService();

            joinedService.deleteMedicine(2);
            assertTrue(true);

        }catch (Exception e){
            e.printStackTrace();
            fail();
        }
    }
}