package project.model.collectionsandassociatedentities.service;

import org.junit.jupiter.api.Test;
import project.model.collectionsandassociatedentities.list.Medicine;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ListDataProviderTest {

    @Test
    void writeTestSuccess(){
        try {
            System.out.println("testEntityWriteTestSuccess");
            Medicine medicine = new Medicine();
            DataProvider dataProvider = new DataProvider();
            medicine.setNameOfPharmacyProduct("Антибиотикс");
            medicine.setPrice(124);
            medicine.setBarcode("134123dsfsdafsdfsd");
            medicine.setDescription("Здесь описание антибиотика");
            dataProvider.addMedicineCategory(medicine, "Антибиотики");


            assertTrue(true);
        }catch (Exception e){
            e.printStackTrace();
            fail();
        }
    }

    @Test
    void updateTestSuccess() {
        try {
            System.out.println("testEntityUpdateTestSuccess");
            Medicine medicine = new Medicine();
            medicine.setId(2);
            List<String> categories = new ArrayList<>();
            categories.add("Антибактериальные");
            categories.add("Абезабливающие");
            DataProvider dataProvider = new DataProvider();

            dataProvider.updateMedicineCategories(medicine, categories);


            assertTrue(true);
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }
    @Test
    void getTestSuccess(){
        try {
            System.out.println("getTestSuccess");
            DataProvider dataProvider = new DataProvider();

            System.out.println(dataProvider.getMedicine(4).get());


            assertTrue(true);
        }catch (Exception e){
            e.printStackTrace();
            fail();
        }
    }

    @Test
    void deleteTestSuccess(){
        try {
            System.out.println("deleteTestSuccess");
            DataProvider dataProvider = new DataProvider();

            dataProvider.deleteMedicineCategory(2);


            assertTrue(true);
        }catch (Exception e){
            e.printStackTrace();
            fail();
        }
    }

    }

