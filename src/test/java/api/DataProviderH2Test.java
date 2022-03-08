package api;

import org.junit.jupiter.api.Test;

import project.api.DataProviderCsv;
import project.api.DataProviderH2;
import project.model.*;
import project.utils.Constant;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static project.api.DataProviderH2.*;
import static project.utils.Constant.URL_H2;

class DataProviderH2Test {

    @Test
    public void writeToH2TestSuccess() {
        try {
            System.out.println("writeToH2TestSuccess");
            List<User> userList = new ArrayList<User>();
            DataProviderH2 DataProviderH2 = new DataProviderH2();


            User user7 = new User();

            user7.setId(10);
            user7.setLogin("Name10");
            user7.setPassword("Pass10");

            User user6 = new User();

            user6.setId(11);
            user6.setLogin("Name11");
            user6.setPassword("Pass11");

            userList.add(user7);
            userList.add(user6);

            writeToH2User(userList);

            System.out.println("writeToH2TestSuccess test Success");
            assertTrue(true);

        } catch (Exception e) {
            System.out.println("writeToH2TestSuccess - Failed!");
            fail(e.getMessage());
        }
    }

    @Test
    public void connectTestSuccess() {
        try {
            Connection connection = connection(URL_H2, Constant.USERNAME_H2, Constant.PASSWORD_H2, Constant.DRIVER_H2);
            Statement statement = connection.createStatement();
            String sql = Constant.SELECT_ALL_FROM_USER;
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()) {
                System.out.println(rs.getInt("ID") + " " + rs.getString("LOGIN") + " " + rs.getString("PASSWORD"));
            }
            statement.close();
            connection.close();
            System.out.println("connectTestSuccess - Success!");
            assertTrue(true);
        } catch (Exception e) {
            System.out.println("connectTestSuccess - Failed! ");
            e.printStackTrace();
            fail(e.getMessage());
        }
    }

    @Test
    void stringingDuplicateIdDeleteTestSuccess() {
        List<User> userList = new ArrayList<User>();
        DataProviderH2 DataProviderH2 = new DataProviderH2();
        try {

            User user7 = new User();

            user7.setId(10);
            user7.setLogin("newName10");

            User user6 = new User();

            user6.setId(11);
            user6.setLogin("newName11");

            userList.add(user7);
            userList.add(user6);

            DataProviderH2.stringingDuplicateIdDeleteUser(userList);
            writeToH2User(userList);
            System.out.println("stringingDuplicateIdDeleteTest - Success!");
            assertTrue(true);
        } catch (Exception e) {
            System.out.println("stringingDuplicateIdDeleteTest - Failed! ");
            e.printStackTrace();
            fail(e.getMessage());
        }

    }

    @Test
    void readFromH2TestSuccess() {
        try {
            System.out.println("readFromH2Test Success");
            System.out.println(readUsersFromH2());
            System.out.println("readFromH2Test - Success!");
            assertTrue(true);
        } catch (Exception e) {
            System.out.println("readFromH2Test - Failed! ");
            e.printStackTrace();
            fail(e.getMessage());
        }
    }

    @Test
    void getUsersTestSuccess() {
        System.out.println("getUsersTestSuccess");
        try {
            DataProviderH2 DataProviderH2 = new DataProviderH2();
            List<User> userList = DataProviderH2.getUsers(URL_H2).getData();
            System.out.println(userList);
            System.out.println("getUsersTest - Success!");
            assertTrue(true);
        } catch (Exception e) {
            System.out.println("getUsersTest - Failed! ");
            e.printStackTrace();
            fail(e.getMessage());
        }
    }


    @Test
    void appendUsersTestSuccess() {
        System.out.println("appendUsersTestSuccess");
        try {
            DataProviderH2 DataProviderH2 = new DataProviderH2();
            List<User> userList = new ArrayList<>();

            User user1 = new User(10, "Name10", "Password112q32");
            User user2 = new User(21, "Name21", "Password2qsdqad");
            User user4 = new User(43, "Name43", "Passwordqwewqe3");
            User user5 = new User(54, "Name54", "Password4324sad4");
            User user10 = new User(60, "Name60", "Password5asf34");

            userList.add(user1);
            userList.add(user2);
            userList.add(user4);
            userList.add(user5);
            userList.add(user10);

            DataProviderH2.appendUsers(userList, URL_H2);
            System.out.println("appendUsersTestSuccess - Success!");
            assertTrue(true);
        } catch (Exception e) {
            System.out.println("appendUsersTestSuccess - Failed! ".concat(e.getMessage()));
            e.printStackTrace();
            fail(e.getMessage());
        }
    }

    @Test
    void deleteUserByIdTestSuccess() {
        System.out.println("deleteUserByIdTestSuccess");
        try {
            DataProviderH2 DataProviderH2 = new DataProviderH2();
            DataProviderH2.deleteUserById(1, URL_H2);
            System.out.println("deleteUserByIdTestSuccess - Success!");
            assertTrue(true);
        } catch (Exception e) {
            System.out.println("deleteUserByIdTestSuccess test - Fail!");
            e.printStackTrace();
            fail(e.getMessage());
        }
    }

    @Test
    void updateUsersTestSuccess() {
        System.out.println("updateUsersTestSuccess");
        try {
            User user1 = new User(4, "user4", "Passwordasdasd4");
            DataProviderH2 DataProviderH2 = new DataProviderH2();
            DataProviderH2.updateUsers(user1, URL_H2);
            System.out.println("updateUsersTestSuccess - Success!");
            assertTrue(true);
        } catch (Exception e) {
            System.out.println("updateUsersTestSuccess - Failed! ".compareTo(e.getMessage()));
            e.printStackTrace();
        }
    }

    @Test
    void getUserByIdTestSuccess() {
        System.out.println("getUserByIdTestSuccess");
        try {
            DataProviderH2 DataProviderH2 = new DataProviderH2();
            System.out.println(DataProviderH2.getUserById(4, URL_H2));
            System.out.println("getUserByIdTestSuccess - Success!");
            assertTrue(true);
        } catch (Exception e) {
            System.out.println("getUserByIdTestSuccess - Failed! ".compareTo(e.getMessage()));
            e.printStackTrace();
        }
    }


    @Test
    void stringingDuplicateIdDeleteMeddicineTestSuccess() {
        List<User> userList = new ArrayList<User>();
        DataProviderH2 DataProviderH2 = new DataProviderH2();
        try {

            User user7 = new User();

            user7.setId(10);
            user7.setLogin("newName10");

            User user6 = new User();

            user6.setId(11);
            user6.setLogin("newName11");

            userList.add(user7);
            userList.add(user6);

            DataProviderH2.stringingDuplicateIdDeleteUser(userList);
            writeToH2User(userList);
            System.out.println("stringingDuplicateIdDeleteTest - Success!");
            assertTrue(true);
        } catch (Exception e) {
            System.out.println("stringingDuplicateIdDeleteTest - Failed! ");
            e.printStackTrace();
            fail(e.getMessage());
        }

    }

    @Test
    void writeToH2MeddicineTestSuccess() {
        try {
            System.out.println("writeToH2MeddicineTestSuccess Test");
            String barcodeJPG2 = "C:\\Users\\zadro\\IdeaProjects\\ru.sfedu.searchOfMeddicine\\src\\main\\resources\\barcodes\\HVUbU.jpg";
            String PATHPROG = "C:\\Program Files (x86)\\ZBar\\bin\\zbarimg";

            List<Meddicine> medicinesList = new ArrayList<>();

            Meddicine meddicine1 = new Meddicine();

            meddicine1.setNameOfPharmacyProduct("Name1");
            meddicine1.setActiveSubstanceOfTheMeddicine("something1");
            meddicine1.setCategoryOfTheMeddicine("antibiotics");
            meddicine1.setBarcode(new Barcode().readBarcode(PATHPROG, barcodeJPG2));
            meddicine1.setPrice(1);
            meddicine1.setDescription("djkfjsdkfjskdfjkdsjfkaflk;jdkajfkjdfkjdakfjdasfkjdkajfkdjlkajkdjakjdfdajfkdjfkjrkrojimeoiej");

            Meddicine meddicine2 = new Meddicine();

            meddicine2.setNameOfPharmacyProduct("Name2");
            meddicine2.setActiveSubstanceOfTheMeddicine("something2");
            meddicine2.setCategoryOfTheMeddicine("antibiotics");
            meddicine2.setBarcode(new Barcode().readBarcode(PATHPROG, barcodeJPG2));
            meddicine2.setPrice(1);
            meddicine2.setDescription("djkfjsdkfjskdfjkdsjfkaflk;jdkajfkjdfkjdakfjdasfkjdkajfkdjlkajkdjakjdfdajfkdjfkjrkrojimeoiej");

            medicinesList.add(meddicine1);
            medicinesList.add(meddicine2);

            writeToH2Meddicine(medicinesList);


            System.out.println("writeToH2MeddicineTestSuccess - Success!");
            assertTrue(true);
        } catch (Exception e) {
            System.out.println("writeToH2MeddicineTestSuccess - Failed! ");
            e.printStackTrace();
            fail(e.getMessage());
        }
    }

    @Test
    void readMeddicinesFromH2MTestSuccess() {
        try {
            System.out.println("readMeddicinesFromH2MTestSuccess Test");

            System.out.println(readMeddicinesFromH2M());

        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    @Test
    void getMeddicines() {
        try {
            System.out.println("getMeddicines Test");
            DataProviderH2 DataProviderH2 = new DataProviderH2();
            List<Meddicine> meddicineList = DataProviderH2.getMeddicines(URL_H2).getData();
            for (Meddicine medicine : meddicineList) {
                System.out.println(medicine.getNameOfPharmacyProduct() + "\n"
                        + medicine.getBarcode() + "\n"
                        + medicine.getCategoryOfTheMeddicine() + "\n"
                        + medicine.getPrice() + "\n"
                        + medicine.getActiveSubstanceOfTheMeddicine() + "\n"
                        + medicine.getDescription() + "\n \n");
            }
            assertTrue(true);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            fail();
        }
    }

    @Test
    void appendMeddicinesTestSuccess() {
        try {

            String barcodeJPG2 = "C:\\Users\\zadro\\IdeaProjects\\ru.sfedu.searchOfMeddicine\\src\\main\\resources\\barcodes\\HVUbU.jpg";
            String barcodeJPG3 = "C:\\Users\\zadro\\IdeaProjects\\ru.sfedu.searchOfMeddicine\\src\\main\\resources\\barcodes\\barcode-image.jpg-.jpg";
            String barcodeJPG4 = "C:\\Users\\zadro\\IdeaProjects\\ru.sfedu.searchOfMeddicine\\src\\main\\resources\\barcodes\\code128.jpg";
            String barcodeJPG5 = "C:\\Users\\zadro\\IdeaProjects\\ru.sfedu.searchOfMeddicine\\src\\main\\resources\\barcodes\\ean-13-sample-barcode.jpg";
            String PATHPROG = "C:\\Program Files (x86)\\ZBar\\bin\\zbarimg";

            System.out.println("appendMeddicinesTestSuccess - Test");

            List<Meddicine> medicinesList = new ArrayList<>();

            Meddicine meddicine1 = new Meddicine();

            meddicine1.setNameOfPharmacyProduct("Name1");
            meddicine1.setActiveSubstanceOfTheMeddicine("something1");
            meddicine1.setCategoryOfTheMeddicine("antibiotics");
            meddicine1.setBarcode(new Barcode().readBarcode(PATHPROG, barcodeJPG2));
            meddicine1.setPrice(1);
            meddicine1.setDescription("djkfjsdkfjskdfjkdsjfkaflk;jdkajfkjdfkjdakfjdasfkjdkajfkdjlkajkdjakjdfdajfkdjfkjrkrojimeoiej");

            Meddicine meddicine2 = new Meddicine();

            meddicine2.setNameOfPharmacyProduct("Name2");
            meddicine2.setActiveSubstanceOfTheMeddicine("something2");
            meddicine2.setCategoryOfTheMeddicine("antibiotics");
            meddicine2.setBarcode(new Barcode().readBarcode(PATHPROG, barcodeJPG3));
            meddicine2.setPrice(1);
            meddicine2.setDescription("djkfjsdkfjskdfjkdsjfkaflk;jdkajfkjdfkjdakfjdasfkjdkajfkdjlkajkdjakjdfdajfkdjfkjrkrojimeoiej");

            Meddicine meddicine3 = new Meddicine();

            meddicine3.setNameOfPharmacyProduct("Name3");
            meddicine3.setActiveSubstanceOfTheMeddicine("something3");
            meddicine3.setCategoryOfTheMeddicine("antibiotics");
            meddicine3.setBarcode(new Barcode().readBarcode(PATHPROG, barcodeJPG4));
            meddicine3.setPrice(3);
            meddicine3.setDescription("djkfjsdkfjskdfjkdsjfkaflk;jdkajfkjdfkjdakfjdasfkjdkajfkdjlkajkdjakjdfdajfkdjfkjrkrojimeoiej");

            medicinesList.add(meddicine3);
            medicinesList.add(meddicine1);
            medicinesList.add(meddicine2);

            DataProviderH2 DataProviderH2 = new DataProviderH2();
            DataProviderH2.appendMeddicines(medicinesList, URL_H2);

            System.out.println("appendMeddicinesTestSuccess - Success!");
            assertTrue(true);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            fail();
        }
    }

    @Test
    void deleteMeddicineByBarcodeTestSuccess() {
        try {
            System.out.println("deleteMeddicineByBarcodeTestSuccess Test");
            DataProviderH2 DataProviderH2 = new DataProviderH2();
            DataProviderH2.deleteMeddicineByBarcode("I2/5:1520152015201520", URL_H2);
            assertTrue(true);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            fail();
        }
    }

    @Test
    void updateMeddicineTestSuccess() {
        String barcodeJPG2 = "C:\\Users\\zadro\\IdeaProjects\\ru.sfedu.searchOfMeddicine\\src\\main\\resources\\barcodes\\HVUbU.jpg";
        String barcodeJPG3 = "C:\\Users\\zadro\\IdeaProjects\\ru.sfedu.searchOfMeddicine\\src\\main\\resources\\barcodes\\barcode-image.jpg-.jpg";
        String barcodeJPG4 = "C:\\Users\\zadro\\IdeaProjects\\ru.sfedu.searchOfMeddicine\\src\\main\\resources\\barcodes\\code128.jpg";
        String barcodeJPG5 = "C:\\Users\\zadro\\IdeaProjects\\ru.sfedu.searchOfMeddicine\\src\\main\\resources\\barcodes\\ean-13-sample-barcode.jpg";
        String PATHPROG = "C:\\Program Files (x86)\\ZBar\\bin\\zbarimg";
        try {
            System.out.println("updateMeddicineTestSuccess Test");

            Meddicine meddicine1 = new Meddicine();

            meddicine1.setNameOfPharmacyProduct("Name1");
            meddicine1.setActiveSubstanceOfTheMeddicine("something1");
            meddicine1.setCategoryOfTheMeddicine("antibiotics");
            meddicine1.setBarcode("I2/5:1520152015201520");
            meddicine1.setPrice(133);
            meddicine1.setDescription("d22222222222jimeoiej");


            DataProviderH2 DataProviderH2 = new DataProviderH2();
            DataProviderH2.updateMeddicine(meddicine1, URL_H2);
            System.out.println("updateMeddicineTestSuccess - Success");
            assertTrue(true);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            fail();
        }
    }

    @Test
    void getMeddicineByBarcodeTestSuccess() {
        try {
            System.out.println("getMeddicineByBarcodeTestSuccess Test");
            DataProviderH2 DataProviderH2 = new DataProviderH2();

            System.out.println(DataProviderH2.getMeddicineByBarcode("CODE-128:CODE128A", URL_H2));
            System.out.println(DataProviderH2.getMeddicineByBarcode("nothing", URL_H2));

            assertTrue(true);

        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            fail();
        }
    }

    @Test
    void getMedicalDevices() {
        try {
            System.out.println("getMeddicines Test");
            DataProviderH2 DataProviderH2 = new DataProviderH2();
            List<MedicalDevice> medicalDeviceList = DataProviderH2.getMedicalDevices(URL_H2).getData();
            for (MedicalDevice medicalDevice : medicalDeviceList) {
                System.out.println(medicalDevice.getNameOfPharmacyProduct() + "\n"
                        + medicalDevice.getBarcode() + "\n"
                        + medicalDevice.getCategoryOfPharmacyDevices() + "\n"
                        + medicalDevice.getPrice() + "\n"
                        + medicalDevice.getDescription() + "\n \n");
            }
            assertTrue(true);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            fail();
        }
    }

    @Test
    void appendMedicalDevicesTestSuccess() {
        try {

            String barcodeJPG2 = "C:\\Users\\zadro\\IdeaProjects\\ru.sfedu.searchOfMeddicine\\src\\main\\resources\\barcodes\\HVUbU.jpg";
            String barcodeJPG3 = "C:\\Users\\zadro\\IdeaProjects\\ru.sfedu.searchOfMeddicine\\src\\main\\resources\\barcodes\\barcode-image.jpg-.jpg";
            String barcodeJPG4 = "C:\\Users\\zadro\\IdeaProjects\\ru.sfedu.searchOfMeddicine\\src\\main\\resources\\barcodes\\code128.jpg";
            String barcodeJPG5 = "C:\\Users\\zadro\\IdeaProjects\\ru.sfedu.searchOfMeddicine\\src\\main\\resources\\barcodes\\ean-13-sample-barcode.jpg";
            String PATHPROG = "C:\\Program Files (x86)\\ZBar\\bin\\zbarimg";

            System.out.println("appendMedicalDevicesTestSuccess - Test");

            List<MedicalDevice> medicalDeviceList = new ArrayList<>();

            MedicalDevice meddicine1 = new MedicalDevice();

            meddicine1.setNameOfPharmacyProduct("Name1");
            meddicine1.setCategoryOfPharmacyDevices("something1");
            meddicine1.setBarcode(new Barcode().readBarcode(PATHPROG, barcodeJPG2));
            meddicine1.setPrice(1);
            meddicine1.setDescription("djkfjsdkfjskdfjkdsjfkaflk;jdkajfkjdfkjdakfjdasfkjdkajfkdjlkajkdjakjdfdajfkdjfkjrkrojimeoiej");

            MedicalDevice meddicine2 = new MedicalDevice();

            meddicine2.setNameOfPharmacyProduct("Name2");
            meddicine2.setCategoryOfPharmacyDevices("something2");
            meddicine2.setBarcode(new Barcode().readBarcode(PATHPROG, barcodeJPG3));
            meddicine2.setPrice(1);
            meddicine2.setDescription("djkfjsdkfjskdfjkdsjfkaflk;jdkajfkjdfkjdakfjdasfkjdkajfkdjlkajkdjakjdfdajfkdjfkjrkrojimeoiej");

            MedicalDevice meddicine3 = new MedicalDevice();

            meddicine3.setNameOfPharmacyProduct("Name3");
            meddicine3.setCategoryOfPharmacyDevices("something3");
            meddicine3.setBarcode(new Barcode().readBarcode(PATHPROG, barcodeJPG4));
            meddicine3.setPrice(3);
            meddicine3.setDescription("djkfjsdkfjskdfjkdsjfkaflk;jdkajfkjdfkjdakfjdasfkjdkajfkdjlkajkdjakjdfdajfkdjfkjrkrojimeoiej");

            medicalDeviceList.add(meddicine3);
            medicalDeviceList.add(meddicine1);
            medicalDeviceList.add(meddicine2);

            DataProviderH2 DataProviderH2 = new DataProviderH2();
            DataProviderH2.appendMedicalDevices(medicalDeviceList, URL_H2);

            System.out.println("appendMedicalDevicesTestSuccess - Success!");
            assertTrue(true);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            fail();
        }
    }

    @Test
    void deleteMedicalDeviceByBarcodeTestSuccess() {
        try {
            System.out.println("deleteMedicalDeviceByBarcodeTestSuccess Test");
            DataProviderH2 DataProviderH2 = new DataProviderH2();
            DataProviderH2.deleteMedicalDeviceByBarcode("I2/5:1520152015201520", URL_H2);
            assertTrue(true);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            fail();
        }
    }

    @Test
    void updateMedicalDeviceTestSuccess() {
        String barcodeJPG2 = "C:\\Users\\zadro\\IdeaProjects\\ru.sfedu.searchOfMeddicine\\src\\main\\resources\\barcodes\\HVUbU.jpg";
        String barcodeJPG3 = "C:\\Users\\zadro\\IdeaProjects\\ru.sfedu.searchOfMeddicine\\src\\main\\resources\\barcodes\\barcode-image.jpg-.jpg";
        String barcodeJPG4 = "C:\\Users\\zadro\\IdeaProjects\\ru.sfedu.searchOfMeddicine\\src\\main\\resources\\barcodes\\code128.jpg";
        String barcodeJPG5 = "C:\\Users\\zadro\\IdeaProjects\\ru.sfedu.searchOfMeddicine\\src\\main\\resources\\barcodes\\ean-13-sample-barcode.jpg";
        String PATHPROG = "C:\\Program Files (x86)\\ZBar\\bin\\zbarimg";
        try {
            System.out.println("updateMedicalDeviceTestSuccess Test");

            MedicalDevice meddicine1 = new MedicalDevice();

            meddicine1.setNameOfPharmacyProduct("Name3");
            meddicine1.setCategoryOfPharmacyDevices("category");
            meddicine1.setBarcode("I2/5:1520152015201520");
            meddicine1.setPrice(1050);
            meddicine1.setDescription("d22222222222jimeoiej");


            MedicalDevice meddicine2 = new MedicalDevice();

            meddicine2.setNameOfPharmacyProduct("Name1");
            meddicine2.setCategoryOfPharmacyDevices("category");
            meddicine2.setBarcode("EAN-8:65833254");
            meddicine2.setPrice(133);
            meddicine2.setDescription("d22222222222jimeoiej");


            MedicalDevice meddicine3 = new MedicalDevice();

            meddicine3.setNameOfPharmacyProduct("Name2");
            meddicine3.setCategoryOfPharmacyDevices("category");
            meddicine3.setBarcode("CODE-128:CODE128A");
            meddicine3.setPrice(278);
            meddicine3.setDescription("d22222222222jimeoiej");


            DataProviderH2 DataProviderH2 = new DataProviderH2();
            DataProviderH2.updateMedicalDevice(meddicine1, URL_H2);
            DataProviderH2.updateMedicalDevice(meddicine2, URL_H2);
            DataProviderH2.updateMedicalDevice(meddicine3, URL_H2);
            System.out.println("updateMedicalDeviceTestSuccess - Success");
            assertTrue(true);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            fail();
        }
    }

    @Test
    void getMedicalDeviceByBarcodeTest() {
        try {
            System.out.println("getMedicalDeviceByNameTest Test");
            DataProviderH2 DataProviderH2 = new DataProviderH2();

            System.out.println(DataProviderH2.getMedicalDeviceByBarcode("CODE-128:CODE128A", URL_H2));
            System.out.println(DataProviderH2.getMedicalDeviceByBarcode("Name1", Constant.URL_H2));

            assertTrue(true);

        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            fail();
        }
    }

    @Test
    void searchMeddicineByCategoryTestSuccess() {
        try {
            System.out.println("searchMeddicineByCategoryTestSuccess Test");

            DataProviderH2 DataProviderH2 = new DataProviderH2();

            List<Meddicine> meddicineList = DataProviderH2.searchMeddicineByCategory("antibiotics");
            for (Meddicine meddicine : meddicineList) {
                System.out.println(meddicine.getBarcode() + "\n" +
                        meddicine.getNameOfPharmacyProduct() + "\n" +
                        meddicine.getPrice() + "\n" +
                        meddicine.getCategoryOfTheMeddicine() + "\n");
            }
            assertTrue(true);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            fail();
        }
    }

    @Test
    void searchMeddicineByCategoryTestFail() {
        try {
            System.out.println("searchMeddicineByCategoryTestFail Test");

            DataProviderH2 DataProviderH2 = new DataProviderH2();

            List<Meddicine> meddicineList = DataProviderH2.searchMeddicineByCategory(null);
            for (Meddicine meddicine : meddicineList) {
                System.out.println(meddicine.getBarcode() + "\n" +
                        meddicine.getNameOfPharmacyProduct() + "\n" +
                        meddicine.getPrice() + "\n" +
                        meddicine.getCategoryOfTheMeddicine() + "\n");
            }
            assertTrue(true);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            fail();
        }
    }

    @Test
    void searchMedicalDeviceByCategoryTestSuccess() {
        try {
            System.out.println("searchMedicalDeviceByCategoryTestSuccess Test");

            DataProviderH2 DataProviderH2 = new DataProviderH2();

            List<MedicalDevice> meddicineList = DataProviderH2.searchMedicalDeviceByCategory("category");
            for (MedicalDevice meddicine : meddicineList) {
                System.out.println(meddicine.getBarcode() + "\n" +
                        meddicine.getNameOfPharmacyProduct() + "\n" +
                        meddicine.getPrice() + "\n" +
                        meddicine.getCategoryOfPharmacyDevices() + "\n");
            }
            assertTrue(true);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            fail();
        }
    }

    @Test
    void searchMedicalDeviceByCategoryTestFail() {
        try {
            System.out.println("searchMedicalDeviceByCategoryTestFail Test");

            DataProviderH2 DataProviderH2 = new DataProviderH2();

            List<MedicalDevice> meddicineList = DataProviderH2.searchMedicalDeviceByCategory(null);
            for (MedicalDevice meddicine : meddicineList) {
                System.out.println(meddicine.getBarcode() + "\n" +
                        meddicine.getNameOfPharmacyProduct() + "\n" +
                        meddicine.getPrice() + "\n" +
                        meddicine.getCategoryOfPharmacyDevices() + "\n");
            }
            assertTrue(true);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            fail();
        }
    }

    @Test
    void searchPharmacyProductByCategoryTestSuccess() {
        try {
            System.out.println("searchPharmacyProductByCategoryTestSuccess Test");

            DataProviderH2 DataProviderH2 = new DataProviderH2();

            List<PharmacyProduct> pharmacyProductList = DataProviderH2.searchPharmacyProductByCategory("category");
            for (PharmacyProduct pharmacyProduct : pharmacyProductList) {
                System.out.println(pharmacyProduct.getBarcode() + "\n" +
                        pharmacyProduct.getNameOfPharmacyProduct() + "\n" +
                        pharmacyProduct.getPrice() + "\n");
            }
            assertTrue(true);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            fail();
        }
    }

    @Test
    void searchPharmacyProductByCategoryTestFail() {
        try {
            System.out.println("searchPharmacyProductByCategoryTestFail Test");

            DataProviderH2 DataProviderH2 = new DataProviderH2();

            List<PharmacyProduct> pharmacyProductList = DataProviderH2.searchPharmacyProductByCategory(null);
            for (PharmacyProduct pharmacyProduct : pharmacyProductList) {
                System.out.println(pharmacyProduct.getBarcode() + "\n" +
                        pharmacyProduct.getNameOfPharmacyProduct() + "\n" +
                        pharmacyProduct.getPrice() + "\n");
            }
            assertTrue(true);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            fail();
        }
    }

    @Test
    void searchMeddicineByNameTestSuccess() {
        try {
            System.out.println("searchMeddicineByNameTestSuccess Test");

            DataProviderH2 DataProviderH2 = new DataProviderH2();

            List<PharmacyProduct> pharmacyProductList = DataProviderH2.searchPharmacyProductByName("Name1");
            for (PharmacyProduct pharmacyProduct : pharmacyProductList) {
                System.out.println(pharmacyProduct.getBarcode() + "\n" +
                        pharmacyProduct.getNameOfPharmacyProduct() + "\n" +
                        pharmacyProduct.getPrice() + "\n");
            }
            assertTrue(true);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            fail();
        }
    }

    @Test
    void searchMeddicineByNameTestFail() {
        try {
            System.out.println("searchMeddicineByNameTestFail Test");

            DataProviderH2 DataProviderH2 = new DataProviderH2();

            List<PharmacyProduct> pharmacyProductList = DataProviderH2.searchPharmacyProductByName(null);
            for (PharmacyProduct pharmacyProduct : pharmacyProductList) {
                System.out.println(pharmacyProduct.getBarcode() + "\n" +
                        pharmacyProduct.getNameOfPharmacyProduct() + "\n" +
                        pharmacyProduct.getPrice() + "\n");
            }
            assertTrue(true);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            fail();
        }
    }

    @Test
    void getTheAnaloguesOfMedicineTestSuccess() {
        System.out.println("getTheAnaloguesOfMedicineTestSuccess Test");
        try {
            DataProviderH2 DataProviderH2 = new DataProviderH2();

            List<Meddicine> meddicineList = DataProviderH2.getTheAnaloguesOfMedicine("I2/5:1520152015201520");
            for (Meddicine meddicine : meddicineList) {
                System.out.println(meddicine.getBarcode() + "\n" +
                        meddicine.getNameOfPharmacyProduct() + "\n" +
                        meddicine.getPrice() + "\n" +
                        meddicine.getCategoryOfTheMeddicine() + "\n" +
                        meddicine.getActiveSubstanceOfTheMeddicine() + "\n");
            }

            assertTrue(true);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            fail();
        }
    }

    @Test
    void getTheAnaloguesOfMedicineTestFail() {
        System.out.println("getTheAnaloguesOfMedicineTestFail Test");
        try {
            DataProviderH2 DataProviderH2 = new DataProviderH2();

            List<Meddicine> meddicineList = DataProviderH2.getTheAnaloguesOfMedicine(null);
            for (Meddicine meddicine : meddicineList) {
                System.out.println(meddicine.getBarcode() + "\n" +
                        meddicine.getNameOfPharmacyProduct() + "\n" +
                        meddicine.getPrice() + "\n" +
                        meddicine.getCategoryOfTheMeddicine() + "\n" +
                        meddicine.getActiveSubstanceOfTheMeddicine() + "\n");
            }

            assertTrue(true);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            fail();
        }
    }

    @Test
    void getTheAnaloguesByBarcodeTestSuccess() {
        System.out.println("getTheAnaloguesByBarcodeTestSuccess Test");
        try {
            DataProviderH2 DataProviderH2 = new DataProviderH2();

            List<Meddicine> meddicineList = DataProviderH2.getTheAnaloguesByBarcode("I2/5:1520152015201520");
            for (Meddicine meddicine : meddicineList) {
                System.out.println(meddicine.getBarcode() + "\n" +
                        meddicine.getNameOfPharmacyProduct() + "\n" +
                        meddicine.getPrice() + "\n" +
                        meddicine.getCategoryOfTheMeddicine() + "\n" +
                        meddicine.getActiveSubstanceOfTheMeddicine() + "\n");
            }

            assertTrue(true);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            fail();
        }
    }

    @Test
    void getTheAnaloguesByBarcodeTestFail() {
        System.out.println("getTheAnaloguesByBarcodeTestFail Test");
        try {
            DataProviderH2 DataProviderH2 = new DataProviderH2();

            List<Meddicine> meddicineList = DataProviderH2.getTheAnaloguesByBarcode(null);
            for (Meddicine meddicine : meddicineList) {
                System.out.println(meddicine.getBarcode() + "\n" +
                        meddicine.getNameOfPharmacyProduct() + "\n" +
                        meddicine.getPrice() + "\n" +
                        meddicine.getCategoryOfTheMeddicine() + "\n" +
                        meddicine.getActiveSubstanceOfTheMeddicine() + "\n");
            }

            assertTrue(true);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            fail();
        }
    }

    @Test
    void sortPharmacyProductMinToMaxTestSuccess() {
        try {
            System.out.println("sortPharmacyProductMinToMaxTestSuccess Test");

            DataProviderH2 DataProviderH2 = new DataProviderH2();

            List<PharmacyProduct> pharmacyProductList = DataProviderH2.searchPharmacyProductByCategory("category");

            System.out.println("Before sorted\n");
            for (PharmacyProduct pharmacyProduct : pharmacyProductList) {
                System.out.println(pharmacyProduct.getBarcode() + "\n" +
                        pharmacyProduct.getNameOfPharmacyProduct() + "\n" +
                        pharmacyProduct.getPrice() + "\n");
            }
            DataProviderH2.sortPharmacyProductMinToMax(pharmacyProductList);
            System.out.println("After sorted\n");
            for (PharmacyProduct pharmacyProduct : pharmacyProductList) {
                System.out.println(pharmacyProduct.getBarcode() + "\n" +
                        pharmacyProduct.getNameOfPharmacyProduct() + "\n" +
                        pharmacyProduct.getPrice() + "\n");
            }

            assertTrue(true);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            fail();
        }
    }

    @Test
    void sortPharmacyProductMaxToMinTestSuccess() {
        try {
            System.out.println("sortPharmacyProductMaxToMinTestSuccess Test");

            DataProviderH2 DataProviderH2 = new DataProviderH2();

            List<PharmacyProduct> pharmacyProductList = DataProviderH2.searchPharmacyProductByCategory("category");

            System.out.println("Before sorted\n");
            for (PharmacyProduct pharmacyProduct : pharmacyProductList) {
                System.out.println(pharmacyProduct.getBarcode() + "\n" +
                        pharmacyProduct.getNameOfPharmacyProduct() + "\n" +
                        pharmacyProduct.getPrice() + "\n");
            }
            DataProviderH2.sortPharmacyProductMaxToMin(pharmacyProductList);
            System.out.println("After sorted\n");
            for (PharmacyProduct pharmacyProduct : pharmacyProductList) {
                System.out.println(pharmacyProduct.getBarcode() + "\n" +
                        pharmacyProduct.getNameOfPharmacyProduct() + "\n" +
                        pharmacyProduct.getPrice() + "\n");
            }

            assertTrue(true);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            fail();
        }
    }


    @Test
    void writeH2OrderTestSuccess() {
        System.out.println("writeCsvOrderTestSuccess Test");
        try {
            List<Order> orderList = new ArrayList<>();
            Order order1 = new Order();
            order1.setOrderId(1);
            order1.setPharmacyProductBarcode("CODE-128:CODE128A");

            Order order2 = new Order();
            order2.setOrderId(2);
            order2.setPharmacyProductBarcode("CODE-128:CODE128A");

            orderList.add(order1);
            orderList.add(order2);

            DataProviderH2 dataProviderH2 = new DataProviderH2();
            dataProviderH2.writeH2Order(orderList, Constant.URL_H2);

        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            fail();
        }
    }

    @Test
    void writeH2OrderTestFail() {
        System.out.println("writeCsvOrderTestFail Test");
        try {
            List<Order> orderList = new ArrayList<>();

            DataProviderH2 dataProviderH2 = new DataProviderH2();
            dataProviderH2.writeH2Order(orderList, Constant.URL_H2);

        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            fail();
        }
    }

    @Test
    void readH2OrderTestSuccess() {
        System.out.println("readCsvOrderTestSuccess Test");
        try {
            DataProviderH2 dataProviderH2 = new DataProviderH2();

            System.out.println(dataProviderH2.readH2Order(Constant.URL_H2));

            assertTrue(true);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            fail();
        }
    }

    @Test
    void readH2OrderTestFail() {
        System.out.println("readCsvOrderTestFail Test");
        try {
            DataProviderH2 dataProviderH2 = new DataProviderH2();

            System.out.println(dataProviderH2.readH2Order(Constant.URL_H2));

            assertTrue(true);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            fail();
        }
    }

    @Test
    void getOrdersTestSuccess() {
        System.out.println("getOrdersTestSuccess Test");
        try {
            DataProviderH2 dataProviderH2 = new DataProviderH2();

            System.out.println(dataProviderH2.getOrders(Constant.URL_H2));

            assertTrue(true);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            fail();
        }
    }

    @Test
    void appendOrdersTestSuccess() {
        System.out.println("appendOrdersTestSuccess Test");
        try {
            List<Order> orderList = new ArrayList<>();
            Order order1 = new Order();
            order1.setOrderId(1);
            order1.setPharmacyProductBarcode("vdvvv7879");

            Order order2 = new Order();
            order2.setOrderId(5);
            order2.setPharmacyProductBarcode("I2/5:1520152015201520");

            orderList.add(order1);
            orderList.add(order2);

            DataProviderH2 dataProviderH2 = new DataProviderH2();
            dataProviderH2.appendOrders(orderList, Constant.URL_H2);

        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            fail();
        }
    }

    @Test
    void appendOrdersTestFail() {
        System.out.println("appendOrdersTestSuccess Test");
        try {
            List<Order> orderList = new ArrayList<>();

            DataProviderH2 dataProviderH2 = new DataProviderH2();
            dataProviderH2.appendOrders(orderList, Constant.URL_H2);

        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            fail();
        }
    }

    @Test
    void deleteOrderByIdTestSuccess() {
        System.out.println("deleteOrderByIdTestSuccess Test");
        try {
            DataProviderH2 dataProviderH2 = new DataProviderH2();

            System.out.println(dataProviderH2.deleteOrderById(1, Constant.URL_H2));

            assertTrue(true);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            fail();
        }
    }

    @Test
    void deleteOrderByIdTestFail() {
        System.out.println("deleteOrderByIdTestFail Test");
        try {
            DataProviderH2 dataProviderH2 = new DataProviderH2();

            System.out.println(dataProviderH2.deleteOrderById(0, Constant.URL_H2));

            assertTrue(true);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            fail();
        }
    }

    @Test
    void updateOrdersTestSuccess() {
        System.out.println("updateOrdersTestSuccess Test");
        try {
            DataProviderH2 dataProviderH2 = new DataProviderH2();

            Order order = new Order();
            order.setOrderId(109);
            order.setPharmacyProductBarcode("ccccbbbb777");

            System.out.println(dataProviderH2.updateOrders(order, URL_H2));

            assertTrue(true);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            fail();
        }
    }

    @Test
    void updateOrdersTestFail() {
        System.out.println("updateOrdersTestFail Test");
        try {
            DataProviderH2 dataProviderH2 = new DataProviderH2();

            Order order = new Order();
            order.setOrderId(1);
            order.setPharmacyProductBarcode("barcod");

            System.out.println(dataProviderH2.updateOrders(null, URL_H2));

            assertTrue(true);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            fail();
        }
    }

    @Test
    void getOrderByIdTestSuccess() {
        System.out.println("getOrderByIdTestSuccess Test");
        try {
            DataProviderH2 dataProviderH2 = new DataProviderH2();

            System.out.println(dataProviderH2.getOrderById(2, Constant.XML_ORDERS));

            assertTrue(true);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            fail();
        }
    }

    @Test
    void getOrderByIdTestFail() {
        System.out.println("getOrderByIdTestFail Test");
        try {
            DataProviderH2 dataProviderH2 = new DataProviderH2();

            System.out.println(dataProviderH2.getOrderById(17, Constant.XML_ORDERS));

            assertTrue(true);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            fail();
        }
    }

    @Test
    void createOrderH2TestSuccess() {
        System.out.println("createOrderCsvTestSuccess Test");
        try {
            DataProviderH2 dataProviderH2 = new DataProviderH2();

            System.out.println(dataProviderH2.createOrder(4, "Name3"));


            assertTrue(true);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            fail();
        }
    }
}