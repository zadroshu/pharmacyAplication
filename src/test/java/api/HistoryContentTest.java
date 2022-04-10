package api;

import org.junit.jupiter.api.Test;
import project.api.DataProviderCsv;
import project.api.HibernateDataProvider;
import project.model.*;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class HistoryContentTest {

    @Test
    void putUserHistoryTestSuccess() {
        User user1 = new User();
        user1.setId(1);
        user1.setLogin("nameUser");
        user1.setPassword("Pass1");
        List<PharmacyProduct> pharmacyProductList = new ArrayList<>();
        pharmacyProductList.add(new PharmacyProduct("termometr", 123.11, "barcode123", "the simplest termometr"));
        pharmacyProductList.add(new PharmacyProduct("pipetka", 121.11, "IMKDMS 3234234", "the simplest pipetkar"));

        List<User> userList = new ArrayList<>();

        User user10 = new User();

        user10.setId(10);
        user10.setLogin("Name10");
        user10.setPassword("Pass10");


        userList.add(user10);
        userList.add(user1);

        HistoryContent historyContent = new HistoryContent();

        historyContent.putUserHistory(user1, Class.class.getSimpleName() , "putUserHistoryTestSuccess", "Test");
    }

    @Test
    void putMeddicineHistoryTestSuccess(){
        try {
            String barcodeJPG2 = "C:\\Users\\zadro\\IdeaProjects\\ru.sfedu.searchOfMeddicine\\src\\main\\resources\\barcodes\\HVUbU.jpg";
            String PATHPROG = "C:\\Program Files (x86)\\ZBar\\bin\\zbarimg";

            Meddicine meddicine1 = new Meddicine();

            meddicine1.setNameOfPharmacyProduct("Name1");
            meddicine1.setActiveSubstanceOfTheMeddicine("something1");
            meddicine1.setCategoryOfTheMeddicine("antibiotics");
            meddicine1.setBarcode(new Barcode().readBarcode(PATHPROG, barcodeJPG2));
            meddicine1.setPrice(1);
            meddicine1.setDescription("djkfjsdkfjskdfjkdsjfkaflk;jdkajfkjdfkjdakfjdasfkjdkajfkdjlkajkdjakjdfdajfkdjfkjrkrojimeoiej");

            HistoryContent historyContent = new HistoryContent();
            historyContent.putMeddicineHistory(meddicine1, Class.class.getSimpleName(), "putMeddicineHistoryTestSuccess", "Test");

            assertTrue(true);
        }catch (Exception e){
            e.printStackTrace();
            fail();
        }
    }

    @Test
    void putMedicalDeviceHistoryTestSuccess(){
        String barcodeJPG2 = "C:\\Users\\zadro\\IdeaProjects\\ru.sfedu.searchOfMeddicine\\src\\main\\resources\\barcodes\\HVUbU.jpg";
        String barcodeJPG3 = "C:\\Users\\zadro\\IdeaProjects\\ru.sfedu.searchOfMeddicine\\src\\main\\resources\\barcodes\\barcode-image.jpg-.jpg";
        String barcodeJPG4 = "C:\\Users\\zadro\\IdeaProjects\\ru.sfedu.searchOfMeddicine\\src\\main\\resources\\barcodes\\code128.jpg";
        String barcodeJPG5 = "C:\\Users\\zadro\\IdeaProjects\\ru.sfedu.searchOfMeddicine\\src\\main\\resources\\barcodes\\ean-13-sample-barcode.jpg";
        String PATHPROG = "C:\\Program Files (x86)\\ZBar\\bin\\zbarimg";

        try {
            System.out.println("putMedicalDeviceHistoryTestSuccess Test");

            MedicalDevice meddicine1 = new MedicalDevice();

            meddicine1.setNameOfPharmacyProduct("Name1");
            meddicine1.setCategoryOfPharmacyDevices("something1");
            meddicine1.setBarcode(new Barcode().readBarcode(PATHPROG, barcodeJPG2));
            meddicine1.setPrice(1);
            meddicine1.setDescription("djkfjsdkfjskdfjkdsjfkaflk;jdkajfkjdfkjdakfjdasfkjdkajfkdjlkajkdjakjdfdajfkdjfkjrkrojimeoiej");

            HistoryContent historyContent = new HistoryContent();
            historyContent.putMedicalDeviceHistory(meddicine1, Class.class.getSimpleName(), "putMedicalDeviceHistoryTestSuccess", "Test");
            assertTrue(true);
        }catch (Exception e){
            e.printStackTrace();
            fail();
        }
        }

}