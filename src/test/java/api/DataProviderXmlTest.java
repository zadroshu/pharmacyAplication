package api;

import org.junit.jupiter.api.Test;
import project.api.DataProviderCsv;
import project.api.DataProviderXml;
import project.model.*;
import project.utils.Constant;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

class DataProviderXmlTest {
    @Test
    public void appendTestSuccess() {
        try {
            System.out.println("AppendUser test Success");
            List<User> userList = new ArrayList<User>();
            DataProviderXml dataProviderXml = new DataProviderXml();

            User user1 = new User();

            user1.setId(1);
            user1.setLogin("Name1");
            user1.setPassword("Password1");

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

            dataProviderXml.appendUsers(userList, Constant.XML_USER);

            System.out.println("AppendUser test Success");
            assertTrue(true);

        } catch (Exception e) {
            System.out.println("AppendUser test Success - Failed!");
            fail(e.getMessage());
        }
    }

    @Test
    void readXmlTest() {
        System.out.println("readXmlTest Test");
        try {
            DataProviderXml dataProviderXml = new DataProviderXml();

            System.out.println(dataProviderXml.readXml(Constant.XML_USER));

            assertTrue(true);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            fail();
        }
    }


    @Test
    void getUsersTest() {
        try {
            System.out.println("getUsersTest test Success");
            List<User> userList = new ArrayList<User>();
            DataProviderXml dataProviderXml = new DataProviderXml();

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

            dataProviderXml.appendUsers(userList, Constant.XML_USER);
            dataProviderXml.getUsers(Constant.XML_USER);

            System.out.println(dataProviderXml.getUsers(Constant.XML_USER));

            assertTrue(true);
        } catch (Exception e) {
            fail("getUsersTest - Fail!");
        }
    }

    @Test
    void deleteUserByIdTest() {
        try {
            System.out.println("deleteUserById test Success");
            DataProviderXml dataProviderXml = new DataProviderXml();
            dataProviderXml.deleteUserById(2, Constant.XML_USER);
            assertTrue(true);
        } catch (Exception e) {
            fail("deleteUserById - Failed!");
        }
    }

    @Test
    void getUserByIdTest() {
        try {
            System.out.println("getUserById test Success");
            DataProviderXml dataProviderXml = new DataProviderXml();

            System.out.println(dataProviderXml.getUserById(3, Constant.XML_USER));
            assertTrue(true);
        } catch (Exception e) {
            fail("getUserById - Failed!");
        }
    }

    @Test
    void updateUsersTest() {
        try {
            System.out.println("updateUsers test Success");
            DataProviderXml dataProviderXml = new DataProviderXml();

            User user2 = new User();

            user2.setId(2);
            user2.setLogin("Name2");
            user2.setPassword("Password2");

            dataProviderXml.updateUsers(user2, Constant.XML_USER);

            System.out.println(dataProviderXml.getUsers(Constant.XML_USER));
            assertTrue(true);
        } catch (Exception e) {
            fail("updateUsers - Failed!");
        }
    }

    @Test
    void voidTestTest() {
        DataProviderXml dataProviderXml = new DataProviderXml();
        User user = (User) dataProviderXml.getUserById(4, Constant.XML_USER).getData();
        user.setLogin("New Login");
        dataProviderXml.updateUsers(user, Constant.XML_USER);

    }

    @Test
    void writeXmlMedicineTest() {
        System.out.println("writeCsvMedicineTest test Success");
        try {
            String barcodeJPG2 = "C:\\Users\\zadro\\Ideadsfedu.searchOfMeddicine\\src\\main\\resources\\barcodes\\HVUbU.jpg";
            String PATHPROG = "C:\\Program Files (x86)\\ZBar\\bin\\zbarimg";

            DataProviderXml dataProviderXml = new DataProviderXml();

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

            List<Meddicine> meddicineList = new ArrayList<>();

            meddicineList.add(meddicine1);
            meddicineList.add(meddicine2);

            dataProviderXml.writeXmlMedicine(meddicineList, Constant.XML_MEDICINE);

            assertTrue(true);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            fail();
        }
    }

    @Test
    void readXmlMedicineTest() {
        System.out.println("readXmlMedicineTest Test");
        try {
            DataProviderXml dataProviderXml = new DataProviderXml();

            List<Meddicine> meddicineList = dataProviderXml.readXmlMedicine(Constant.XML_MEDICINE);
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
    void getMeddicinesTest() {
        try {
            System.out.println("getMeddicinesTest Test");
            DataProviderXml dataProviderXml = new DataProviderXml();
            List<Meddicine> meddicineList = dataProviderXml.getMeddicines(Constant.XML_MEDICINE).getData();
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
    void appendMeddicinesTest() {
        try {

            String barcodeJPG2 = "C:\\Users\\zadro\\Ideadsfedu.searchOfMeddicine\\src\\main\\resources\\barcodes\\HVUbU.jpg";
            String barcodeJPG3 = "C:\\Users\\zadro\\Ideadsfedu.searchOfMeddicine\\src\\main\\resources\\barcodes\\barcode-image.jpg-.jpg";
            String barcodeJPG4 = "C:\\Users\\zadro\\Ideadsfedu.searchOfMeddicine\\src\\main\\resources\\barcodes\\code128.jpg";
            String barcodeJPG5 = "C:\\Users\\zadro\\Ideadsfedu.searchOfMeddicine\\src\\main\\resources\\barcodes\\ean-13-sample-barcode.jpg";
            String PATHPROG = "C:\\Program Files (x86)\\ZBar\\bin\\zbarimg";

            System.out.println("appendMeddicinesTest");
            DataProviderXml dataProviderXml = new DataProviderXml();

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

            dataProviderXml.appendMeddicines(medicinesList, Constant.XML_MEDICINE);

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
            DataProviderXml dataProviderXml = new DataProviderXml();
            dataProviderXml.deleteMeddicineByBarcode("I2/5:1520152015201520", Constant.XML_MEDICINE);
            assertTrue(true);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            fail();
        }
    }

    @Test
    void updateMeddicineTest(){
        String barcodeJPG2 = "C:\\Users\\zadro\\Ideadsfedu.searchOfMeddicine\\src\\main\\resources\\barcodes\\HVUbU.jpg";
        String barcodeJPG3 = "C:\\Users\\zadro\\Ideadsfedu.searchOfMeddicine\\src\\main\\resources\\barcodes\\barcode-image.jpg-.jpg";
        String barcodeJPG4 = "C:\\Users\\zadro\\Ideadsfedu.searchOfMeddicine\\src\\main\\resources\\barcodes\\code128.jpg";
        String barcodeJPG5 = "C:\\Users\\zadro\\Ideadsfedu.searchOfMeddicine\\src\\main\\resources\\barcodes\\ean-13-sample-barcode.jpg";
        String PATHPROG = "C:\\Program Files (x86)\\ZBar\\bin\\zbarimg";
        try {
            System.out.println("updateMeddicineTest Test");

            Meddicine meddicine1 = new Meddicine();

            meddicine1.setNameOfPharmacyProduct("Name5");
            meddicine1.setActiveSubstanceOfTheMeddicine("something2");
            meddicine1.setCategoryOfTheMeddicine("antibiotics");
            meddicine1.setBarcode(new Barcode().readBarcode(PATHPROG, barcodeJPG2));
            meddicine1.setPrice(13333);
            meddicine1.setDescription("d22222222222jimeoiej");


            DataProviderXml dataProviderXml = new DataProviderXml();
            dataProviderXml.updateMeddicine(meddicine1, Constant.XML_MEDICINE);
            assertTrue(true);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            fail();
        }
    }

    @Test
    void getMeddicineByBarcodeTest(){
        try {
            System.out.println("getMeddicineByBarcodeTest Test");
            DataProviderXml dataProviderXml = new DataProviderXml();

            System.out.println(dataProviderXml.getMeddicineByBarcode("CODE-128:CODE128A", Constant.XML_MEDICINE));
            System.out.println(dataProviderXml.getMeddicineByBarcode("nothing", Constant.XML_MEDICINE));

            assertTrue(true);

        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            fail();
        }
    }

    @Test
    void writeXmlMedicalDeviceTest(){
        System.out.println("writeXmlMedicalDeviceTest test Success");
        try {
            String barcodeJPG2 = "C:\\Users\\zadro\\Ideadsfedu.searchOfMeddicine\\src\\main\\resources\\barcodes\\HVUbU.jpg";
            String PATHPROG = "C:\\Program Files (x86)\\ZBar\\bin\\zbarimg";

            DataProviderXml dataProviderXml = new DataProviderXml();

            MedicalDevice meddicine1 = new MedicalDevice();
            meddicine1.setNameOfPharmacyProduct("Name1");
            meddicine1.setCategoryOfPharmacyDevices("antibiotics");
            meddicine1.setBarcode(new Barcode().readBarcode(PATHPROG, barcodeJPG2));
            meddicine1.setPrice(1);
            meddicine1.setDescription("djkfjsdkfjskdfjkdsjfkaflk;jdkajfkjdfkjdakfjdasfkjdkajfkdjlkajkdjakjdfdajfkdjfkjrkrojimeoiej");

            MedicalDevice meddicine2 = new MedicalDevice();

            meddicine2.setNameOfPharmacyProduct("Name2");
            meddicine2.setCategoryOfPharmacyDevices("antibiotics");
            meddicine2.setBarcode(new Barcode().readBarcode(PATHPROG, barcodeJPG2));
            meddicine2.setPrice(1);
            meddicine2.setDescription("djkfjsdkfjskdfjkdsjfkaflk;jdkajfkjdfkjdakfjdasfkjdkajfkdjlkajkdjakjdfdajfkdjfkjrkrojimeoiej");

            List<MedicalDevice> meddicineList = new ArrayList<>();

            meddicineList.add(meddicine1);
            meddicineList.add(meddicine2);

            dataProviderXml.writeXmlMedicalDevice(meddicineList, Constant.XML_MEDICALDEVICES);

            assertTrue(true);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            fail();
        }
    }

    @Test
    void readXmlMedicalDeviceTest(){
        System.out.println("readXmlMedicalDeviceTest Test");
        try {
            DataProviderXml dataProviderXml = new DataProviderXml();

            List<MedicalDevice> medicalDeviceList = dataProviderXml.readXmlMedicalDevice(Constant.XML_MEDICALDEVICES);
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
    void getMedicalDevicesTest(){
        try {
            System.out.println("getMedicalDevicesTest Test");
            DataProviderXml dataProviderXml = new DataProviderXml();
            List<MedicalDevice> medicalDeviceList = dataProviderXml.getMedicalDevices(Constant.XML_MEDICALDEVICES).getData();
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
    void appendMedicalDevicesTest(){
        try {

            String barcodeJPG2 = "C:\\Users\\zadro\\Ideadsfedu.searchOfMeddicine\\src\\main\\resources\\barcodes\\HVUbU.jpg";
            String barcodeJPG3 = "C:\\Users\\zadro\\Ideadsfedu.searchOfMeddicine\\src\\main\\resources\\barcodes\\barcode-image.jpg-.jpg";
            String barcodeJPG4 = "C:\\Users\\zadro\\Ideadsfedu.searchOfMeddicine\\src\\main\\resources\\barcodes\\code128.jpg";
            String barcodeJPG5 = "C:\\Users\\zadro\\Ideadsfedu.searchOfMeddicine\\src\\main\\resources\\barcodes\\ean-13-sample-barcode.jpg";
            String PATHPROG = "C:\\Program Files (x86)\\ZBar\\bin\\zbarimg";

            System.out.println("appendMedicalDevicesTest");
            DataProviderXml dataProviderXml = new DataProviderXml();

            List<MedicalDevice> medicalDeviceList = new ArrayList<>();

            MedicalDevice meddicine1 = new MedicalDevice();

            meddicine1.setNameOfPharmacyProduct("Name1");
            meddicine1.setCategoryOfPharmacyDevices("termometrs");
            meddicine1.setBarcode(new Barcode().readBarcode(PATHPROG, barcodeJPG2));
            meddicine1.setPrice(1);
            meddicine1.setDescription("djkfjsdkfjskdfjkdsjfkaflk;jdkajfkjdfkjdakfjdasfkjdkajfkdjlkajkdjakjdfdajfkdjfkjrkrojimeoiej");

            MedicalDevice meddicine2 = new MedicalDevice();

            meddicine2.setNameOfPharmacyProduct("Name2");
            meddicine2.setCategoryOfPharmacyDevices("termometrs");
            meddicine2.setBarcode(new Barcode().readBarcode(PATHPROG, barcodeJPG3));
            meddicine2.setPrice(1);
            meddicine2.setDescription("djkfjsdkfjskdfjkdsjfkaflk;jdkajfkjdfkjdakfjdasfkjdkajfkdjlkajkdjakjdfdajfkdjfkjrkrojimeoiej");

            MedicalDevice meddicine3 = new MedicalDevice();

            meddicine3.setNameOfPharmacyProduct("Name3");
            meddicine3.setCategoryOfPharmacyDevices("termometrs");
            meddicine3.setBarcode(new Barcode().readBarcode(PATHPROG, barcodeJPG4));
            meddicine3.setPrice(3);
            meddicine3.setDescription("djkfjsdkfjskdfjkdsjfkaflk;jdkajfkjdfkjdakfjdasfkjdkajfkdjlkajkdjakjdfdajfkdjfkjrkrojimeoiej");

            medicalDeviceList.add(meddicine3);
            medicalDeviceList.add(meddicine1);
            medicalDeviceList.add(meddicine2);

            dataProviderXml.appendMedicalDevices(medicalDeviceList, Constant.XML_MEDICALDEVICES);

        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            fail();
        }
    }

    @Test
    void deleteMedicalDeviceByBarcodeTest(){
        try {
            System.out.println("deleteMedicalDeviceByBarcodeTest Test");
            DataProviderXml dataProviderXml = new DataProviderXml();
            dataProviderXml.deleteMedicalDeviceByBarcode("CODE-128:CODE128A", Constant.XML_MEDICALDEVICES);
            assertTrue(true);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            fail();
        }
    }

    @Test
    void updateMedicalDeviceTest(){
        String barcodeJPG2 = "C:\\Users\\zadro\\Ideadsfedu.searchOfMeddicine\\src\\main\\resources\\barcodes\\HVUbU.jpg";
        String barcodeJPG3 = "C:\\Users\\zadro\\Ideadsfedu.searchOfMeddicine\\src\\main\\resources\\barcodes\\barcode-image.jpg-.jpg";
        String barcodeJPG4 = "C:\\Users\\zadro\\Ideadsfedu.searchOfMeddicine\\src\\main\\resources\\barcodes\\code128.jpg";
        String barcodeJPG5 = "C:\\Users\\zadro\\Ideadsfedu.searchOfMeddicine\\src\\main\\resources\\barcodes\\ean-13-sample-barcode.jpg";
        String PATHPROG = "C:\\Program Files (x86)\\ZBar\\bin\\zbarimg";
        try {
            System.out.println("updateMeddicineTest Test");

            MedicalDevice meddicine1 = new MedicalDevice();

            meddicine1.setNameOfPharmacyProduct("Name4");
            meddicine1.setCategoryOfPharmacyDevices("pipetka");
            meddicine1.setBarcode(new Barcode().readBarcode(PATHPROG, barcodeJPG4));
            meddicine1.setPrice(223);
            meddicine1.setDescription("pipetka");


            DataProviderXml dataProviderXml = new DataProviderXml();
            dataProviderXml.updateMedicalDevice(meddicine1, Constant.XML_MEDICALDEVICES);
            assertTrue(true);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            fail();
        }
    }

    @Test
    void getMedicalDeviceByBarcodeTest(){
        try {
            System.out.println("getMedicalDeviceByBarcodeTest Test");
            DataProviderXml dataProviderXml = new DataProviderXml();

            System.out.println(dataProviderXml.getMedicalDeviceByBarcode("CODE-128:CODE128A", Constant.XML_MEDICALDEVICES));
            System.out.println(dataProviderXml.getMedicalDeviceByBarcode("nothing", Constant.XML_MEDICALDEVICES));

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
            DataProviderXml dataProviderXml = new DataProviderXml();

            System.out.println(dataProviderXml.getMedicalDeviceByBarcode("CODE-128:CODE128A", Constant.XML_MEDICALDEVICES));
            System.out.println(dataProviderXml.getMedicalDeviceByBarcode("nothing", Constant.XML_MEDICALDEVICES));

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

            DataProviderXml dataProviderXml = new DataProviderXml();

            List<Meddicine> meddicineList = dataProviderXml.searchMeddicineByCategory("antibiotics");
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

            DataProviderXml dataProviderXml = new DataProviderXml();

            List<Meddicine> meddicineList = dataProviderXml.searchMeddicineByCategory(null);
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

            DataProviderXml dataProviderXml = new DataProviderXml();

            List<MedicalDevice> meddicineList = dataProviderXml.searchMedicalDeviceByCategory("pipetka");
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

            DataProviderXml dataProviderXml = new DataProviderXml();

            List<MedicalDevice> meddicineList = dataProviderXml.searchMedicalDeviceByCategory(null);
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

            DataProviderXml dataProviderXml = new DataProviderXml();

            List<PharmacyProduct> pharmacyProductList = dataProviderXml.searchPharmacyProductByCategory("antibiotics");
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

            DataProviderXml dataProviderXml = new DataProviderXml();

            List<PharmacyProduct> pharmacyProductList = dataProviderXml.searchPharmacyProductByCategory(null);
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
    void searchMeddicineByNameTestSuccess(){
        try{
            System.out.println("searchMeddicineByNameTestSuccess Test");

            DataProviderXml dataProviderXml = new DataProviderXml();

            List<PharmacyProduct> pharmacyProductList = dataProviderXml.searchPharmacyProductByName("Name1");
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
    void searchMeddicineByNameTestFail(){
        try{
            System.out.println("searchMeddicineByNameTestFail Test");

            DataProviderXml dataProviderXml = new DataProviderXml();

            List<PharmacyProduct> pharmacyProductList = dataProviderXml.searchPharmacyProductByName(null);
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
            DataProviderXml dataProviderXml = new DataProviderXml();

            List<Meddicine> meddicineList = dataProviderXml.getTheAnaloguesOfMedicine("EAN-8:65833254");
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
            DataProviderXml dataProviderXml = new DataProviderXml();

            List<Meddicine> meddicineList = dataProviderXml.getTheAnaloguesOfMedicine(null);
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
            DataProviderXml dataProviderXml = new DataProviderXml();

            List<Meddicine> meddicineList = dataProviderXml.getTheAnaloguesByBarcode("I2/5:1520152015201520");
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
            DataProviderXml dataProviderXml = new DataProviderXml();

            List<Meddicine> meddicineList = dataProviderXml.getTheAnaloguesByBarcode(null);
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

            DataProviderXml dataProviderXml = new DataProviderXml();

            List<PharmacyProduct> pharmacyProductList = dataProviderXml.searchPharmacyProductByCategory("pipetka");

            System.out.println("Before sorted\n");
            for (PharmacyProduct pharmacyProduct: pharmacyProductList) {
                System.out.println(pharmacyProduct.getBarcode() + "\n" +
                        pharmacyProduct.getNameOfPharmacyProduct() + "\n" +
                        pharmacyProduct.getPrice()+ "\n");
            }
            dataProviderXml.sortPharmacyProductMinToMax(pharmacyProductList);
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

            DataProviderXml dataProviderXml = new DataProviderXml();

            List<PharmacyProduct> pharmacyProductList = dataProviderXml.searchPharmacyProductByCategory("pipetka");

            System.out.println("Before sorted\n");
            for (PharmacyProduct pharmacyProduct: pharmacyProductList) {
                System.out.println(pharmacyProduct.getBarcode() + "\n" +
                        pharmacyProduct.getNameOfPharmacyProduct() + "\n" +
                        pharmacyProduct.getPrice()+ "\n");
            }
            dataProviderXml.sortPharmacyProductMaxToMin(pharmacyProductList);
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
    void writeXMLOrderTestSuccess(){
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

            DataProviderXml dataProviderXml = new DataProviderXml();
            dataProviderXml.writeXmlOrder(orderList, Constant.XML_ORDERS);

        }catch (Exception e){
            System.out.println(e.getMessage());
            e.printStackTrace();
            fail();
        }
    }

    @Test
    void writeXMLOrderTestFail(){
        System.out.println("writeCsvOrderTestFail Test");
        try {
            List<Order> orderList = new ArrayList<>();

            DataProviderXml dataProviderXml = new DataProviderXml();
            dataProviderXml.writeXmlOrder(orderList, Constant.XML_ORDERS);

        }catch (Exception e){
            System.out.println(e.getMessage());
            e.printStackTrace();
            fail();
        }
    }

    @Test
    void readXmlOrderTestSuccess(){
        System.out.println("readCsvOrderTestSuccess Test");
        try {
            DataProviderXml dataProviderXml = new DataProviderXml();

            System.out.println(dataProviderXml.readXmlOrder(Constant.XML_ORDERS));

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
            DataProviderXml dataProviderXml = new DataProviderXml();

            System.out.println(dataProviderXml.readXmlOrder(Constant.XML_ORDERS));

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
            DataProviderXml dataProviderXml = new DataProviderXml();

            System.out.println(dataProviderXml.getOrders(Constant.XML_ORDERS));

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

            DataProviderXml dataProviderXml = new DataProviderXml();
            dataProviderXml.appendOrders(orderList, Constant.XML_ORDERS);

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

            DataProviderXml dataProviderXml = new DataProviderXml();
            dataProviderXml.appendOrders(orderList, Constant.XML_ORDERS);

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
            DataProviderXml dataProviderXml = new DataProviderXml();

            System.out.println(dataProviderXml.deleteOrderById(1,Constant.XML_ORDERS));

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
            DataProviderXml dataProviderXml = new DataProviderXml();

            System.out.println(dataProviderXml.deleteOrderById(0, Constant.XML_ORDERS));

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
            DataProviderXml dataProviderXml = new DataProviderXml();

            Order order = new Order();
            order.setOrderId(1);
            order.setPharmacyProductBarcode("ccccbbbb999");

            System.out.println(dataProviderXml.updateOrders(order,Constant.XML_ORDERS));

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
            DataProviderXml dataProviderXml = new DataProviderXml();

            Order order = new Order();
            order.setOrderId(1);
            order.setPharmacyProductBarcode("barcod");

            System.out.println(dataProviderXml.updateOrders(null,Constant.XML_ORDERS));

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
            DataProviderXml dataProviderXml = new DataProviderXml();

            System.out.println(dataProviderXml.getOrderById(1,Constant.XML_ORDERS));

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
            DataProviderXml dataProviderXml = new DataProviderXml();

            System.out.println(dataProviderXml.getOrderById(17,Constant.XML_ORDERS));

            assertTrue(true);
        }catch (Exception e){
            System.out.println(e.getMessage());
            e.printStackTrace();
            fail();
        }
    }

    @Test
    void  createOrderXmlTestSuccess(){
        System.out.println("createOrderCsvTestSuccess Test");
        try {
            DataProviderXml dataProviderXml = new DataProviderXml();

            System.out.println(dataProviderXml.createOrder(4,"Name1"));

            assertTrue(true);
        }catch (Exception e){
            System.out.println(e.getMessage());
            e.printStackTrace();
            fail();
        }
    }

}
