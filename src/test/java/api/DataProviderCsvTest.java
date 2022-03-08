package api;

import project.api.DataProviderCsv;
import project.model.*;
import org.junit.jupiter.api.Test;
import project.utils.Constant;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static project.api.DataProviderCsv.readCsvMedicine;
import static project.api.DataProviderCsv.writeCsvMedicine;

class DataProviderCsvTest {


    @Test
    public void appendTestSuccess() {
        try {
            System.out.println("AppendUser test Success");
            List<User> userList = new ArrayList<User>();
            DataProviderCsv dataProviderCsv = new DataProviderCsv();

            User user1 = new User();

            user1.setId(1);
            user1.setLogin("Name1");
            user1.setPassword("Password1");
            List<PharmacyProduct> pharmacyProductList = new ArrayList<>();
            pharmacyProductList.add(new PharmacyProduct("termometr", 123.11, "barcode123", "the simplest termometr"));
            pharmacyProductList.add(new PharmacyProduct("pipetka", 121.11, "IMKDMS 3234234", "the simplest pipetkar"));

            User user10 = new User();

            user10.setId(10);
            user10.setLogin("Name10");
            user10.setPassword("Password10");

            User user9 = new User();

            user9.setId(9);
            user9.setLogin("Name9");
            user9.setPassword("Password9");

            userList.add(user10);
            userList.add(user9);
            userList.add(user1);

            dataProviderCsv.appendUsers(userList, Constant.CSV_USER);

            System.out.println("AppendUser test Success");
            assertTrue(true);

        } catch (Exception e) {
            System.out.println("AppendUser test Success - Failed!");
            fail(e.getMessage());
        }
    }


    @Test
    void getUsersTest() {
        try {
            System.out.println("getUsersTest test Success");
            List<User> userList = new ArrayList<User>();
            DataProviderCsv dataProviderCsv = new DataProviderCsv();

            User user1 = new User();

            user1.setId(1);
            user1.setLogin("Name1");
            user1.setPassword("Password1");

            User user2 = new User();
            user2.setId(2);
            user2.setLogin("Name2");
            user2.setPassword("Password2");

            User user3 = new User();

            user3.setId(3);
            user3.setLogin("Name3");
            user3.setPassword("Password3");

            User user4 = new User();

            user4.setId(4);
            user4.setLogin("Name4");
            user4.setPassword("Password4");

            userList.add(user1);
            userList.add(user2);
            userList.add(user3);
            userList.add(user4);

            dataProviderCsv.appendUsers(userList, Constant.CSV_USER);
            dataProviderCsv.getUsers(Constant.CSV_USER);

            System.out.println(dataProviderCsv.getUsers(Constant.CSV_USER));

            assertTrue(true);
        } catch (Exception e) {
            fail("getUsersTest - Fail!");
        }
    }

    @Test
    void deleteUserByIdTest() {
        try {
            System.out.println("deleteUserById test Success");
            DataProviderCsv dataProviderCsv = new DataProviderCsv();
            dataProviderCsv.deleteUserById(2, Constant.CSV_USER);
            assertTrue(true);
        } catch (Exception e) {
            fail("deleteUserById - Failed!");
        }
    }

    @Test
    void getUserByIdTest() {
        try {
            System.out.println("getUserById test Success");
            DataProviderCsv dataProviderCsv = new DataProviderCsv();

            System.out.println(dataProviderCsv.getUserById(3, Constant.CSV_USER));
            assertTrue(true);
        } catch (Exception e) {
            fail("getUserById - Failed!");
        }
    }

    @Test
    void updateUsersTest() {
        try {
            System.out.println("updateUsers test Success");
            DataProviderCsv dataProviderCsv = new DataProviderCsv();

            User user2 = new User();

            user2.setId(2);
            user2.setLogin("Name2");
            user2.setPassword("Password2");

            dataProviderCsv.updateUsers(user2, Constant.CSV_USER);

            System.out.println(dataProviderCsv.getUsers(Constant.CSV_USER));
            assertTrue(true);
        } catch (Exception e) {
            fail("updateUsers - Failed!");
        }
    }


    @Test
    void getMeddicines() {
        try {
            System.out.println("getMeddicines Test");
            DataProviderCsv dataProviderCsv = new DataProviderCsv();
            List<Meddicine> meddicineList = dataProviderCsv.getMeddicines(Constant.CSV_MEDICINE).getData();
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
    void writeCsvMedicineTest() {
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

        writeCsvMedicine(medicinesList, Constant.CSV_MEDICINE);
    }

    @Test
    void readCsvMedicineTest() {
        try {
            System.out.println("readCsvMedicineTest - Test");
            System.out.println(readCsvMedicine(Constant.CSV_MEDICINE));
            assertTrue(true);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            fail();
        }
    }

    @Test
    void appendMeddicinesTest() {
        try {

            String barcodeJPG2 = "C:\\Users\\zadro\\IdeaProjects\\ru.sfedu.searchOfMeddicine\\src\\main\\resources\\barcodes\\HVUbU.jpg";
            String barcodeJPG3 = "C:\\Users\\zadro\\IdeaProjects\\ru.sfedu.searchOfMeddicine\\src\\main\\resources\\barcodes\\barcode-image.jpg-.jpg";
            String barcodeJPG4 = "C:\\Users\\zadro\\IdeaProjects\\ru.sfedu.searchOfMeddicine\\src\\main\\resources\\barcodes\\code128.jpg";
            String barcodeJPG5 = "C:\\Users\\zadro\\IdeaProjects\\ru.sfedu.searchOfMeddicine\\src\\main\\resources\\barcodes\\ean-13-sample-barcode.jpg";
            String PATHPROG = "C:\\Program Files (x86)\\ZBar\\bin\\zbarimg";

            System.out.println("appendMeddicinesTest");
            DataProviderCsv dataProviderCsv = new DataProviderCsv();

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

            dataProviderCsv.appendMeddicines(medicinesList, Constant.CSV_MEDICINE);

        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            fail();
        }

    }

    @Test
    void deleteMeddicineByBarcodeTest() {
        try {
            System.out.println("deleteMeddicineByNameTest Test");
            DataProviderCsv dataProviderCsv = new DataProviderCsv();
            dataProviderCsv.deleteMeddicineByBarcode("I2/5:1520152015201520", Constant.CSV_MEDICINE);
            assertTrue(true);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            fail();
        }
    }

    @Test
    void updateMeddicineTest() {

        String barcodeJPG2 = "C:\\Users\\zadro\\IdeaProjects\\ru.sfedu.searchOfMeddicine\\src\\main\\resources\\barcodes\\HVUbU.jpg";
        String barcodeJPG3 = "C:\\Users\\zadro\\IdeaProjects\\ru.sfedu.searchOfMeddicine\\src\\main\\resources\\barcodes\\barcode-image.jpg-.jpg";
        String barcodeJPG4 = "C:\\Users\\zadro\\IdeaProjects\\ru.sfedu.searchOfMeddicine\\src\\main\\resources\\barcodes\\code128.jpg";
        String barcodeJPG5 = "C:\\Users\\zadro\\IdeaProjects\\ru.sfedu.searchOfMeddicine\\src\\main\\resources\\barcodes\\ean-13-sample-barcode.jpg";
        String PATHPROG = "C:\\Program Files (x86)\\ZBar\\bin\\zbarimg";
        try {
            System.out.println("updateMeddicineTest Test");

            Meddicine meddicine1 = new Meddicine();

            meddicine1.setNameOfPharmacyProduct("Name2");
            meddicine1.setActiveSubstanceOfTheMeddicine("something2");
            meddicine1.setCategoryOfTheMeddicine("antibiotics");
            meddicine1.setBarcode(new Barcode().readBarcode(PATHPROG, barcodeJPG2));
            meddicine1.setPrice(13333);
            meddicine1.setDescription("d22222222222jimeoiej");


            DataProviderCsv dataProviderCsv = new DataProviderCsv();
            dataProviderCsv.updateMeddicine(meddicine1, Constant.CSV_MEDICINE);
            assertTrue(true);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            fail();
        }
    }

    @Test
    void getMeddicineByBarcodeTest() {
        try {
            System.out.println("getMeddicineByBarcodeTest Test");
            DataProviderCsv dataProviderCsv = new DataProviderCsv();

            System.out.println(dataProviderCsv.getMeddicineByBarcode("CODE-128:CODE128A", Constant.CSV_MEDICINE));
            System.out.println(dataProviderCsv.getMeddicineByBarcode("nothing", Constant.CSV_MEDICINE));

            assertTrue(true);

        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            fail();
        }
    }

    @Test
    void getMedicalDevicesTest() {
        try {
            System.out.println("getMedicalDevicesTest Test");

            DataProviderCsv dataProviderCsv = new DataProviderCsv();
            List<MedicalDevice> meddicineList = dataProviderCsv.getMedicalDevices(Constant.CSV_MEDICALDEVICES).getData();
            for (MedicalDevice medicalDevice : meddicineList) {
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
    void appendMedicalDevicesTest() {
        String barcodeJPG2 = "C:\\Users\\zadro\\IdeaProjects\\ru.sfedu.searchOfMeddicine\\src\\main\\resources\\barcodes\\HVUbU.jpg";
        String barcodeJPG3 = "C:\\Users\\zadro\\IdeaProjects\\ru.sfedu.searchOfMeddicine\\src\\main\\resources\\barcodes\\barcode-image.jpg-.jpg";
        String barcodeJPG4 = "C:\\Users\\zadro\\IdeaProjects\\ru.sfedu.searchOfMeddicine\\src\\main\\resources\\barcodes\\code128.jpg";
        String barcodeJPG5 = "C:\\Users\\zadro\\IdeaProjects\\ru.sfedu.searchOfMeddicine\\src\\main\\resources\\barcodes\\ean-13-sample-barcode.jpg";
        String PATHPROG = "C:\\Program Files (x86)\\ZBar\\bin\\zbarimg";

        try {
            System.out.println("appendMeddicinesTest");
            DataProviderCsv dataProviderCsv = new DataProviderCsv();

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

            dataProviderCsv.appendMedicalDevices(medicalDeviceList, Constant.CSV_MEDICALDEVICES);

        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            fail();
        }
    }

    @Test
    void deleteMedicalDeviceByBarcodeTest() {
        try {
            System.out.println("deleteMedicalDeviceByBarcodeTest Test");
            DataProviderCsv dataProviderCsv = new DataProviderCsv();
            dataProviderCsv.deleteMedicalDeviceByBarcode("I2/5:1520152015201520", Constant.CSV_MEDICALDEVICES);
            dataProviderCsv.deleteMedicalDeviceByBarcode("nothing", Constant.CSV_MEDICALDEVICES);
            assertTrue(true);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            fail();
        }
    }

    @Test
    void updateMedicalDeviceTest() {
        String barcodeJPG2 = "C:\\Users\\zadro\\IdeaProjects\\ru.sfedu.searchOfMeddicine\\src\\main\\resources\\barcodes\\HVUbU.jpg";
        String barcodeJPG3 = "C:\\Users\\zadro\\IdeaProjects\\ru.sfedu.searchOfMeddicine\\src\\main\\resources\\barcodes\\barcode-image.jpg-.jpg";
        String barcodeJPG4 = "C:\\Users\\zadro\\IdeaProjects\\ru.sfedu.searchOfMeddicine\\src\\main\\resources\\barcodes\\code128.jpg";
        String barcodeJPG5 = "C:\\Users\\zadro\\IdeaProjects\\ru.sfedu.searchOfMeddicine\\src\\main\\resources\\barcodes\\ean-13-sample-barcode.jpg";
        String PATHPROG = "C:\\Program Files (x86)\\ZBar\\bin\\zbarimg";
        try {
            System.out.println("updateMeddicineTest Test");

            MedicalDevice meddicine1 = new MedicalDevice();

            meddicine1.setNameOfPharmacyProduct("Name3");
            meddicine1.setCategoryOfPharmacyDevices("something2");
            meddicine1.setBarcode(new Barcode().readBarcode(PATHPROG, barcodeJPG4));
            meddicine1.setPrice(13333);
            meddicine1.setDescription("d22222222222jsdfdsfdsfdimeoiej");


            DataProviderCsv dataProviderCsv = new DataProviderCsv();
            dataProviderCsv.updateMedicalDevice(meddicine1, Constant.CSV_MEDICALDEVICES);
            assertTrue(true);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            fail();
        }
    }

    @Test
    void getMedicalDeviceByNameTest() {
        try {
            System.out.println("getMedicalDeviceByNameTest Test");
            DataProviderCsv dataProviderCsv = new DataProviderCsv();

            System.out.println(dataProviderCsv.getMedicalDeviceByBarcode("CODE-128:CODE128A", Constant.CSV_MEDICALDEVICES));
            System.out.println(dataProviderCsv.getMedicalDeviceByBarcode("nothing", Constant.CSV_MEDICALDEVICES));

            assertTrue(true);

        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            fail();
        }
    }

    @Test
    void searchMeddicineByCategoryTestSuccess(){
        try{
            System.out.println("searchMeddicineByCategoryTestSuccess Test");

            DataProviderCsv dataProviderCsv = new DataProviderCsv();

            List<Meddicine> meddicineList = dataProviderCsv.searchMeddicineByCategory("antibiotics");
            for (Meddicine meddicine: meddicineList) {
                System.out.println(meddicine.getBarcode() + "\n" +
                        meddicine.getNameOfPharmacyProduct() + "\n" +
                        meddicine.getPrice()+ "\n" +
                        meddicine.getCategoryOfTheMeddicine()+ "\n");
            }
            assertTrue(true);
        }catch (Exception e){
            System.out.println(e.getMessage());
            e.printStackTrace();
            fail();
        }
    }
    @Test
    void searchMeddicineByCategoryTestFail(){
        try{
            System.out.println("searchMeddicineByCategoryTestFail Test");

            DataProviderCsv dataProviderCsv = new DataProviderCsv();

            List<Meddicine> meddicineList = dataProviderCsv.searchMeddicineByCategory(null);
            for (Meddicine meddicine: meddicineList) {
                System.out.println(meddicine.getBarcode() + "\n" +
                        meddicine.getNameOfPharmacyProduct() + "\n" +
                        meddicine.getPrice()+ "\n" +
                        meddicine.getCategoryOfTheMeddicine()+ "\n");
            }
            assertTrue(true);
        }catch (Exception e){
            System.out.println(e.getMessage());
            e.printStackTrace();
            fail();
        }
    }

    @Test
    void searchMedicalDeviceByCategoryTestSuccess(){
        try{
            System.out.println("searchMedicalDeviceByCategoryTestSuccess Test");

            DataProviderCsv dataProviderCsv = new DataProviderCsv();

            List<MedicalDevice> meddicineList = dataProviderCsv.searchMedicalDeviceByCategory("something2");
            for (MedicalDevice meddicine: meddicineList) {
                System.out.println(meddicine.getBarcode() + "\n" +
                        meddicine.getNameOfPharmacyProduct() + "\n" +
                        meddicine.getPrice()+ "\n" +
                        meddicine.getCategoryOfPharmacyDevices()+ "\n");
            }
            assertTrue(true);
        }catch (Exception e){
            System.out.println(e.getMessage());
            e.printStackTrace();
            fail();
        }
    }

    @Test
    void searchMedicalDeviceByCategoryTestFail(){
        try{
            System.out.println("searchMedicalDeviceByCategoryTestFail Test");

            DataProviderCsv dataProviderCsv = new DataProviderCsv();

            List<MedicalDevice> meddicineList = dataProviderCsv.searchMedicalDeviceByCategory(null);
            for (MedicalDevice meddicine: meddicineList) {
                System.out.println(meddicine.getBarcode() + "\n" +
                        meddicine.getNameOfPharmacyProduct() + "\n" +
                        meddicine.getPrice()+ "\n" +
                        meddicine.getCategoryOfPharmacyDevices()+ "\n");
            }
            assertTrue(true);
        }catch (Exception e){
            System.out.println(e.getMessage());
            e.printStackTrace();
            fail();
        }
    }

    @Test
    void searchPharmacyProductByCategoryTestSuccess(){
        try{
            System.out.println("searchPharmacyProductByCategoryTestSuccess Test");

            DataProviderCsv dataProviderCsv = new DataProviderCsv();

            List<PharmacyProduct> pharmacyProductList = dataProviderCsv.searchPharmacyProductByCategory("antibiotics");
            for (PharmacyProduct pharmacyProduct: pharmacyProductList) {
                System.out.println(pharmacyProduct.getBarcode() + "\n" +
                        pharmacyProduct.getNameOfPharmacyProduct() + "\n" +
                        pharmacyProduct.getPrice()+ "\n");
            }
            assertTrue(true);
        }catch (Exception e){
            System.out.println(e.getMessage());
            e.printStackTrace();
            fail();
        }
    }

    @Test
    void searchPharmacyProductByCategoryTestFail(){
        try{
            System.out.println("searchPharmacyProductByCategoryTestFail Test");

            DataProviderCsv dataProviderCsv = new DataProviderCsv();

            List<PharmacyProduct> pharmacyProductList = dataProviderCsv.searchPharmacyProductByCategory(null);
            for (PharmacyProduct pharmacyProduct: pharmacyProductList) {
                System.out.println(pharmacyProduct.getBarcode() + "\n" +
                        pharmacyProduct.getNameOfPharmacyProduct() + "\n" +
                        pharmacyProduct.getPrice()+ "\n");
            }
            assertTrue(true);
        }catch (Exception e){
            System.out.println(e.getMessage());
            e.printStackTrace();
            fail();
        }
    }

    @Test
    void searchPharmacyProductByNameTestSuccess(){
        try{
            System.out.println("searchMeddicineByNameTestSuccess Test");

            DataProviderCsv dataProviderCsv = new DataProviderCsv();

            List<PharmacyProduct> pharmacyProductList = dataProviderCsv.searchPharmacyProductByName("Name1");
            for (PharmacyProduct pharmacyProduct: pharmacyProductList) {
                System.out.println(pharmacyProduct.getBarcode() + "\n" +
                        pharmacyProduct.getNameOfPharmacyProduct() + "\n" +
                        pharmacyProduct.getPrice()+ "\n");
            }
            assertTrue(true);
        }catch (Exception e){
            System.out.println(e.getMessage());
            e.printStackTrace();
            fail();
        }
    }

    @Test
    void searchPharmacyProductByNameTestFail(){
        try{
            System.out.println("searchMeddicineByNameTestFail Test");

            DataProviderCsv dataProviderCsv = new DataProviderCsv();

            List<PharmacyProduct> pharmacyProductList = dataProviderCsv.searchPharmacyProductByName(null);
            for (PharmacyProduct pharmacyProduct: pharmacyProductList) {
                System.out.println(pharmacyProduct.getBarcode() + "\n" +
                        pharmacyProduct.getNameOfPharmacyProduct() + "\n" +
                        pharmacyProduct.getPrice()+ "\n");
            }
            assertTrue(true);
        }catch (Exception e){
            System.out.println(e.getMessage());
            e.printStackTrace();
            fail();
        }
    }

    @Test
    void getTheAnaloguesOfMedicineTestSuccess(){
        System.out.println("getTheAnaloguesOfMedicineTestSuccess Test");
        try{
            DataProviderCsv dataProviderCsv = new DataProviderCsv();

            List<Meddicine> meddicineList = dataProviderCsv.getTheAnaloguesOfMedicine("Name1");
            for (Meddicine meddicine: meddicineList) {
                System.out.println(meddicine.getBarcode() + "\n" +
                        meddicine.getNameOfPharmacyProduct() + "\n" +
                        meddicine.getPrice()+ "\n" +
                        meddicine.getCategoryOfTheMeddicine()+ "\n" +
                        meddicine.getActiveSubstanceOfTheMeddicine() + "\n");
            }

            assertTrue(true);
        }catch (Exception e){
            System.out.println(e.getMessage());
            e.printStackTrace();
            fail();
        }
    }

    @Test
    void getTheAnaloguesOfMedicineTestFail(){
        System.out.println("getTheAnaloguesOfMedicineTestFail Test");
        try{
            DataProviderCsv dataProviderCsv = new DataProviderCsv();

            List<Meddicine> meddicineList = dataProviderCsv.getTheAnaloguesOfMedicine(null);
            for (Meddicine meddicine: meddicineList) {
                System.out.println(meddicine.getBarcode() + "\n" +
                        meddicine.getNameOfPharmacyProduct() + "\n" +
                        meddicine.getPrice()+ "\n" +
                        meddicine.getCategoryOfTheMeddicine()+ "\n" +
                        meddicine.getActiveSubstanceOfTheMeddicine() + "\n");
            }

            assertTrue(true);
        }catch (Exception e){
            System.out.println(e.getMessage());
            e.printStackTrace();
            fail();
        }
    }

    @Test
    void getTheAnaloguesByBarcodeTestSuccess(){
        System.out.println("getTheAnaloguesByBarcodeTestSuccess Test");
        try{
            DataProviderCsv dataProviderCsv = new DataProviderCsv();

            List<Meddicine> meddicineList = dataProviderCsv.getTheAnaloguesByBarcode("I2/5:1520152015201520");
            for (Meddicine meddicine: meddicineList) {
                System.out.println(meddicine.getBarcode() + "\n" +
                        meddicine.getNameOfPharmacyProduct() + "\n" +
                        meddicine.getPrice()+ "\n" +
                        meddicine.getCategoryOfTheMeddicine()+ "\n" +
                        meddicine.getActiveSubstanceOfTheMeddicine() + "\n");
            }

            assertTrue(true);
        }catch (Exception e){
            System.out.println(e.getMessage());
            e.printStackTrace();
            fail();
        }
    }

    @Test
    void getTheAnaloguesByBarcodeTestFail(){
        System.out.println("getTheAnaloguesByBarcodeTestFail Test");
        try{
            DataProviderCsv dataProviderCsv = new DataProviderCsv();

            List<Meddicine> meddicineList = dataProviderCsv.getTheAnaloguesByBarcode(null);
            for (Meddicine meddicine: meddicineList) {
                System.out.println(meddicine.getBarcode() + "\n" +
                        meddicine.getNameOfPharmacyProduct() + "\n" +
                        meddicine.getPrice()+ "\n" +
                        meddicine.getCategoryOfTheMeddicine()+ "\n" +
                        meddicine.getActiveSubstanceOfTheMeddicine() + "\n");
            }

            assertTrue(true);
        }catch (Exception e){
            System.out.println(e.getMessage());
            e.printStackTrace();
            fail();
        }
    }

    @Test
    void sortPharmacyProductMinToMaxTestSuccess(){
        try{
            System.out.println("sortPharmacyProductMinToMaxTestSuccess Test");

            DataProviderCsv dataProviderCsv = new DataProviderCsv();

            List<PharmacyProduct> pharmacyProductList = dataProviderCsv.searchPharmacyProductByCategory("something2");

            System.out.println("Before sorted\n");
            for (PharmacyProduct pharmacyProduct: pharmacyProductList) {
                System.out.println(pharmacyProduct.getBarcode() + "\n" +
                        pharmacyProduct.getNameOfPharmacyProduct() + "\n" +
                        pharmacyProduct.getPrice()+ "\n");
            }
            dataProviderCsv.sortPharmacyProductMinToMax(pharmacyProductList);
            System.out.println("After sorted\n");
            for (PharmacyProduct pharmacyProduct: pharmacyProductList) {
                System.out.println(pharmacyProduct.getBarcode() + "\n" +
                        pharmacyProduct.getNameOfPharmacyProduct() + "\n" +
                        pharmacyProduct.getPrice()+ "\n");
            }

            assertTrue(true);
        }catch (Exception e){
            System.out.println(e.getMessage());
            e.printStackTrace();
            fail();
        }
    }

    @Test
    void sortPharmacyProductMinToMaxTestFail(){
        try{
            System.out.println("sortPharmacyProductMinToMaxTestFail Test");

            DataProviderCsv dataProviderCsv = new DataProviderCsv();

            List<PharmacyProduct> pharmacyProductList = dataProviderCsv.searchPharmacyProductByCategory("--");

            System.out.println("Before sorted\n");
            for (PharmacyProduct pharmacyProduct: pharmacyProductList) {
                System.out.println(pharmacyProduct.getBarcode() + "\n" +
                        pharmacyProduct.getNameOfPharmacyProduct() + "\n" +
                        pharmacyProduct.getPrice()+ "\n");
            }
            dataProviderCsv.sortPharmacyProductMinToMax(pharmacyProductList);
            System.out.println("After sorted\n");
            for (PharmacyProduct pharmacyProduct: pharmacyProductList) {
                System.out.println(pharmacyProduct.getBarcode() + "\n" +
                        pharmacyProduct.getNameOfPharmacyProduct() + "\n" +
                        pharmacyProduct.getPrice()+ "\n");
            }

            assertTrue(true);
        }catch (Exception e){
            System.out.println(e.getMessage());
            e.printStackTrace();
            fail();
        }
    }

    @Test
    void sortPharmacyProductMaxToMinTestSuccess(){
        try{
            System.out.println("sortPharmacyProductMaxToMinTestSuccess Test");

            DataProviderCsv dataProviderCsv = new DataProviderCsv();

            List<PharmacyProduct> pharmacyProductList = dataProviderCsv.searchPharmacyProductByCategory("something2");

            System.out.println("Before sorted\n");
            for (PharmacyProduct pharmacyProduct: pharmacyProductList) {
                System.out.println(pharmacyProduct.getBarcode() + "\n" +
                        pharmacyProduct.getNameOfPharmacyProduct() + "\n" +
                        pharmacyProduct.getPrice()+ "\n");
            }
            dataProviderCsv.sortPharmacyProductMaxToMin(pharmacyProductList);
            System.out.println("After sorted\n");
            for (PharmacyProduct pharmacyProduct: pharmacyProductList) {
                System.out.println(pharmacyProduct.getBarcode() + "\n" +
                        pharmacyProduct.getNameOfPharmacyProduct() + "\n" +
                        pharmacyProduct.getPrice()+ "\n");
            }

            assertTrue(true);
        }catch (Exception e){
            System.out.println(e.getMessage());
            e.printStackTrace();
            fail();
        }
    }
    @Test
    void sortPharmacyProductMaxToMinTestFail(){
        try{
            System.out.println("sortPharmacyProductMaxToMinTestFail Test");

            DataProviderCsv dataProviderCsv = new DataProviderCsv();

            List<PharmacyProduct> pharmacyProductList = dataProviderCsv.searchPharmacyProductByCategory("--");

            System.out.println("Before sorted\n");
            for (PharmacyProduct pharmacyProduct: pharmacyProductList) {
                System.out.println(pharmacyProduct.getBarcode() + "\n" +
                        pharmacyProduct.getNameOfPharmacyProduct() + "\n" +
                        pharmacyProduct.getPrice()+ "\n");
            }
            dataProviderCsv.sortPharmacyProductMaxToMin(pharmacyProductList);
            System.out.println("After sorted\n");
            for (PharmacyProduct pharmacyProduct: pharmacyProductList) {
                System.out.println(pharmacyProduct.getBarcode() + "\n" +
                        pharmacyProduct.getNameOfPharmacyProduct() + "\n" +
                        pharmacyProduct.getPrice()+ "\n");
            }

            assertTrue(true);
        }catch (Exception e){
            System.out.println(e.getMessage());
            e.printStackTrace();
            fail();
        }
    }

    @Test
    void writeCsvOrderTestSuccess(){
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

            DataProviderCsv dataProviderCsv = new DataProviderCsv();
            dataProviderCsv.writeCsvOrder(orderList, Constant.CSV_ORDERS);

        }catch (Exception e){
            System.out.println(e.getMessage());
            e.printStackTrace();
            fail();
        }
    }

    @Test
    void writeCsvOrderTestFail(){
        System.out.println("writeCsvOrderTestFail Test");
        try {
            List<Order> orderList = new ArrayList<>();

            DataProviderCsv dataProviderCsv = new DataProviderCsv();
            dataProviderCsv.writeCsvOrder(orderList, Constant.CSV_ORDERS);

        }catch (Exception e){
            System.out.println(e.getMessage());
            e.printStackTrace();
            fail();
        }
    }

    @Test
    void readCsvOrderTestSuccess(){
        System.out.println("readCsvOrderTestSuccess Test");
        try {
            DataProviderCsv dataProviderCsv = new DataProviderCsv();

            System.out.println(dataProviderCsv.readCsvOrder(Constant.CSV_ORDERS));

            assertTrue(true);
        }catch (Exception e){
            System.out.println(e.getMessage());
            e.printStackTrace();
            fail();
        }
    }

    @Test
    void readCsvOrderTestFail(){
        System.out.println("readCsvOrderTestFail Test");
        try {
            DataProviderCsv dataProviderCsv = new DataProviderCsv();

            System.out.println(dataProviderCsv.readCsvOrder(Constant.CSV_USER));

            assertTrue(true);
        }catch (Exception e){
            System.out.println(e.getMessage());
            e.printStackTrace();
            fail();
        }
    }

    @Test
    void getOrdersTestSuccess(){
        System.out.println("getOrdersTestSuccess Test");
        try {
            DataProviderCsv dataProviderCsv = new DataProviderCsv();

            System.out.println(dataProviderCsv.getOrders(Constant.CSV_ORDERS));

            assertTrue(true);
        }catch (Exception e){
            System.out.println(e.getMessage());
            e.printStackTrace();
            fail();
        }
    }

    @Test
    void getOrdersTestFail(){
        System.out.println("getOrdersTestFail Test");
        try {
            DataProviderCsv dataProviderCsv = new DataProviderCsv();

            System.out.println(dataProviderCsv.getOrders(Constant.CSV_MEDICINE));

            assertTrue(true);
        }catch (Exception e){
            System.out.println(e.getMessage());
            e.printStackTrace();
            fail();
        }
    }

    @Test
    void appendOrdersTestSuccess(){
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

            DataProviderCsv dataProviderCsv = new DataProviderCsv();
            dataProviderCsv.appendOrders(orderList, Constant.CSV_ORDERS);

        }catch (Exception e){
            System.out.println(e.getMessage());
            e.printStackTrace();
            fail();
        }
    }

    @Test
    void appendOrdersTestFail(){
        System.out.println("appendOrdersTestSuccess Test");
        try {
            List<Order> orderList = new ArrayList<>();

            DataProviderCsv dataProviderCsv = new DataProviderCsv();
            dataProviderCsv.appendOrders(orderList, Constant.CSV_ORDERS);

        }catch (Exception e){
            System.out.println(e.getMessage());
            e.printStackTrace();
            fail();
        }
    }

    @Test
    void deleteOrderByIdTestSuccess(){
        System.out.println("deleteOrderByIdTestSuccess Test");
        try {
            DataProviderCsv dataProviderCsv = new DataProviderCsv();

            System.out.println(dataProviderCsv.deleteOrderById(1,Constant.CSV_ORDERS));

            assertTrue(true);
        }catch (Exception e){
            System.out.println(e.getMessage());
            e.printStackTrace();
            fail();
        }
    }

    @Test
    void deleteOrderByIdTestFail(){
        System.out.println("deleteOrderByIdTestFail Test");
        try {
            DataProviderCsv dataProviderCsv = new DataProviderCsv();

            System.out.println(dataProviderCsv.deleteOrderById(0,Constant.CSV_MEDICINE));

            assertTrue(true);
        }catch (Exception e){
            System.out.println(e.getMessage());
            e.printStackTrace();
            fail();
        }
    }

    @Test
    void updateOrdersTestSuccess(){
        System.out.println("updateOrdersTestSuccess Test");
        try {
            DataProviderCsv dataProviderCsv = new DataProviderCsv();

            Order order = new Order();
            order.setOrderId(1);
            order.setPharmacyProductBarcode("barcod");

            System.out.println(dataProviderCsv.updateOrders(order,Constant.CSV_ORDERS));

            assertTrue(true);
        }catch (Exception e){
            System.out.println(e.getMessage());
            e.printStackTrace();
            fail();
        }
    }

    @Test
    void updateOrdersTestFail(){
        System.out.println("updateOrdersTestFail Test");
        try {
            DataProviderCsv dataProviderCsv = new DataProviderCsv();

            Order order = new Order();
            order.setOrderId(1);
            order.setPharmacyProductBarcode("barcod");

            System.out.println(dataProviderCsv.updateOrders(null,Constant.CSV_ORDERS));

            assertTrue(true);
        }catch (Exception e){
            System.out.println(e.getMessage());
            e.printStackTrace();
            fail();
        }
    }

    @Test
    void getOrderByIdTestSuccess(){
        System.out.println("getOrderByIdTestSuccess Test");
        try {
            DataProviderCsv dataProviderCsv = new DataProviderCsv();

            System.out.println(dataProviderCsv.getOrderById(1,Constant.CSV_ORDERS));

            assertTrue(true);
        }catch (Exception e){
            System.out.println(e.getMessage());
            e.printStackTrace();
            fail();
        }
    }

    @Test
    void getOrderByIdTestFail(){
        System.out.println("getOrderByIdTestFail Test");
        try {
            DataProviderCsv dataProviderCsv = new DataProviderCsv();

            System.out.println(dataProviderCsv.getOrderById(17,Constant.CSV_ORDERS));

            assertTrue(true);
        }catch (Exception e){
            System.out.println(e.getMessage());
            e.printStackTrace();
            fail();
        }
    }

    @Test
    void  createOrderCsvTestSuccess(){
        System.out.println("createOrderCsvTestSuccess Test");
        try {
            DataProviderCsv dataProviderCsv = new DataProviderCsv();

            System.out.println(dataProviderCsv.createOrder(10,"Name1"));

            assertTrue(true);
        }catch (Exception e){
            System.out.println(e.getMessage());
            e.printStackTrace();
            fail();
        }
    }

    @Test
    void  createOrderCsvTestFail(){
        System.out.println("createOrderCsvTestFail Test");
        try {
            DataProviderCsv dataProviderCsv = new DataProviderCsv();
            User user = new User();
            user.setId(1);
            user.setPassword("Pass133");
            user.setLogin("Shipychka");

            System.out.println(dataProviderCsv.createOrder(900,"Name3new"));

            dataProviderCsv.updateUsers(user, Constant.CSV_USER);
            assertTrue(true);
        }catch (Exception e){
            System.out.println(e.getMessage());
            e.printStackTrace();
            fail();
        }
    }

    @Test
    void sortPricePharmacyProductTestSuccess(){
        System.out.println("sortPricePharmacyProductTestSuccess Test");
        try {
            DataProviderCsv dataProviderCsv = new DataProviderCsv();

            List<PharmacyProduct> pharmacyProductList = dataProviderCsv.searchPharmacyProductByCategory("antibiotics");
            pharmacyProductList = dataProviderCsv.sortPharmacyProduct(pharmacyProductList,"d");

            for (PharmacyProduct pharmacyProduct: pharmacyProductList) {
                System.out.println(pharmacyProduct.getBarcode() + "\n" +
                        pharmacyProduct.getNameOfPharmacyProduct() + "\n" +
                        pharmacyProduct.getPrice() + "\n");
            }

            assertTrue(true);
        }catch (Exception e){
            System.out.println(e.getMessage());
            e.printStackTrace();
            fail();
        }
    }

    @Test
    void sortPricePharmacyProductTestFail(){
        System.out.println("sortPricePharmacyProductTestFail Test");
        try {
            DataProviderCsv dataProviderCsv = new DataProviderCsv();

            List<PharmacyProduct> pharmacyProductList = dataProviderCsv.searchPharmacyProductByCategory("--ску");
            pharmacyProductList = dataProviderCsv.sortPharmacyProduct(pharmacyProductList,"d");

            for (PharmacyProduct pharmacyProduct: pharmacyProductList) {
                System.out.println(pharmacyProduct.getBarcode() + "\n" +
                        pharmacyProduct.getNameOfPharmacyProduct() + "\n" +
                        pharmacyProduct.getPrice() + "\n");
            }

            assertTrue(true);
        }catch (Exception e){
            System.out.println(e.getMessage());
            e.printStackTrace();
            fail();
        }
    }

    @Test
    void changePharmacyProductTestSuccess(){
        System.out.println("changePharmacyProductTestSuccess Test");
        try {
            DataProviderCsv dataProviderCsv = new DataProviderCsv();

            Order order = new Order();

            dataProviderCsv.changePharmacyProduct(order, "Name2");

            assertTrue(true);
        }catch (Exception e){
            System.out.println(e.getMessage());
            e.printStackTrace();
            fail();
        }
    }

    @Test
    void changePharmacyProductTestFail(){
        System.out.println("changePharmacyProductTestFail Test");
        try {
            DataProviderCsv dataProviderCsv = new DataProviderCsv();

            Order order = new Order();

            dataProviderCsv.changePharmacyProduct(order, "Name");

            assertTrue(true);
        }catch (Exception e){
            System.out.println(e.getMessage());
            e.printStackTrace();
            fail();
        }
    }
}