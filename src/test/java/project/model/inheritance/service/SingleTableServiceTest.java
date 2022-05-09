package project.model.inheritance.service;

import org.junit.jupiter.api.Test;
import project.model.inheritance.singletable.MedicalDevice;
import project.model.inheritance.singletable.Medicine;
import project.model.inheritance.singletable.PharmacyProduct;

import static org.junit.jupiter.api.Assertions.*;

class SingleTableServiceTest {
    @Test
    void addPharmacyProduct() {
        try {
            System.out.println("addPharmacyProduct");
            SingleTableService singleTableService = new SingleTableService();

            PharmacyProduct pharmacyProduct = new PharmacyProduct();

            pharmacyProduct.setNameOfPharmacyProduct("Pinicilin");
            pharmacyProduct.setDescription("kill all bacterias");
            pharmacyProduct.setBarcode("bar12387112");
            pharmacyProduct.setPrice(240);

            singleTableService.addPharmacyProduct(pharmacyProduct);

            assertTrue(true);

        } catch (Exception e) {
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
            SingleTableService singleTableService = new SingleTableService();


            if (singleTableService.getAllPharmacyProduct().isPresent()) {
                for (PharmacyProduct pharmacyProduct : singleTableService.getAllPharmacyProduct().get()) {
                    System.out.println("\nName : " + pharmacyProduct.getNameOfPharmacyProduct() + "\n"
                            + "Price" + pharmacyProduct.getPrice() + "\n"
                            + "Description " + pharmacyProduct.getDescription());
                }


            } else {
                System.out.println("No such element!");
            }

            assertTrue(true);

        } catch (Exception e) {
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
            SingleTableService singleTableService = new SingleTableService();

            MedicalDevice medicalDevice = new MedicalDevice();

            medicalDevice.setNameOfPharmacyProduct("clisma ");
            medicalDevice.setDescription("clisma");
            medicalDevice.setBarcode("clis123ma123");
            medicalDevice.setPrice(250);
            medicalDevice.setCategoryOfPharmacyDevices("discription of clisma");

            singleTableService.addPharmacyProduct(medicalDevice);

            assertTrue(true);

        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    void updateMedicalDevice() {
        try {
            System.out.println("updateMedicalDevice");
            SingleTableService singleTableService = new SingleTableService();

            MedicalDevice medicalDevice = new MedicalDevice();

            medicalDevice.setId(2);
            medicalDevice.setNameOfPharmacyProduct("pressure meter ");
            medicalDevice.setDescription("pressure meter");
            medicalDevice.setBarcode("bar12pressure meterrw112");
            medicalDevice.setPrice(1800);
            medicalDevice.setCategoryOfPharmacyDevices("pressure meter");

            singleTableService.updateMedicalDevice(medicalDevice);


            assertTrue(true);

        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }


    @Test
    void getAllMedicalDevice() {
        try {
            System.out.println("getAllMedicalDevice");
            SingleTableService singleTableService = new SingleTableService();


            if (singleTableService.getAllMedicalDevice().isPresent()) {
                for (MedicalDevice medicalDevice : singleTableService.getAllMedicalDevice().get()) {
                    System.out.println("\nName : " + medicalDevice.getNameOfPharmacyProduct() + "\n"
                            + "Price : " + medicalDevice.getPrice() + "\n"
                            + "Description : " + medicalDevice.getDescription() + "\n"
                            + "CategoryUnidirectional : " + medicalDevice.getCategoryOfPharmacyDevices());
                }


            } else {
                System.out.println("No such element!");
            }

            assertTrue(true);

        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    void getMedicalDevice() {
        try {
            System.out.println("updateMedicalDevice");
            SingleTableService singleTableService = new SingleTableService();

            MedicalDevice medicalDevice = singleTableService.getMedicalDevice(2).get();

            System.out.println("\nName : " + medicalDevice.getNameOfPharmacyProduct() + "\n"
                    + "Price : " + medicalDevice.getPrice() + "\n"
                    + "Description : " + medicalDevice.getDescription() + "\n"
                    + "CategoryUnidirectional : " + medicalDevice.getCategoryOfPharmacyDevices());

            assertTrue(true);

        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    void deleteMedicalDevice() {
        try {
            System.out.println("deleteMedicalDevice");
            SingleTableService singleTableService = new SingleTableService();

            singleTableService.deleteMedicalDevice(1);
            assertTrue(true);

        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    void addMedicine() {
        try {
            System.out.println("addMedicine");
            SingleTableService singleTableService = new SingleTableService();

            Medicine medicine = new Medicine();

            medicine.setNameOfPharmacyProduct("Activirovany ygol ");
            medicine.setDescription("helps you with stomach pain");
            medicine.setBarcode("sldfkjskldf1234");
            medicine.setPrice(550);
            medicine.setActiveSubstanceOfTheMeddicine("ygol");
            medicine.setCategoryOfTheMeddicine("For the stomach");

            singleTableService.addPharmacyProduct(medicine);

            assertTrue(true);

        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    void updateMedicine() {
        try {
            System.out.println("updateMedicine");
            SingleTableService singleTableService = new SingleTableService();

            Medicine medicine = new Medicine();

            medicine.setId(2);
            medicine.setNameOfPharmacyProduct("Activirovany ygol ");
            medicine.setDescription("helps you with stomach pain");
            medicine.setBarcode("sldfkjskldf1234");
            medicine.setPrice(340);
            medicine.setActiveSubstanceOfTheMeddicine("ygol");
            medicine.setCategoryOfTheMeddicine("For the stomach");

            singleTableService.updateMedicine(medicine);


            assertTrue(true);

        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    void getAllMedicine() {
        try {
            System.out.println("getAllMedicine");
            SingleTableService singleTableService = new SingleTableService();

            if (singleTableService.getAllMedicine().isPresent()) {
                for (Medicine medicine : singleTableService.getAllMedicine().get()) {
                    System.out.println("\nName : " + medicine.getNameOfPharmacyProduct() + "\n"
                            + "Price : " + medicine.getPrice() + "\n"
                            + "Description : " + medicine.getDescription() + "\n"
                            + "CategoryUnidirectional : " + medicine.getCategoryOfTheMeddicine() + "\n"
                            + "Active substance : " + medicine.getActiveSubstanceOfTheMeddicine());
                }
            } else {
                System.out.println("No such element!");
            }

            assertTrue(true);

        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    void getMedicine() {
        try {
            System.out.println("getMedicineBidirectional");
            SingleTableService singleTableService = new SingleTableService();

            Medicine medicine = singleTableService.getMedicine(2).get();

            System.out.println("\nName : " + medicine.getNameOfPharmacyProduct() + "\n"
                    + "Price : " + medicine.getPrice() + "\n"
                    + "Description : " + medicine.getDescription() + "\n"
                    + "CategoryUnidirectional : " + medicine.getCategoryOfTheMeddicine() + "\n"
                    + "Active substance : " + medicine.getActiveSubstanceOfTheMeddicine());

            assertTrue(true);

        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    void deleteMedicine() {
        try {
            System.out.println("deleteMedicine");
            SingleTableService singleTableService = new SingleTableService();

            singleTableService.deleteMedicine(2);
            assertTrue(true);

        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

}