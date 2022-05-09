package project.model.collectionsandassociatedentities.service;

import org.junit.jupiter.api.Test;
import project.model.collectionsandassociatedentities.map.Medicine;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class DataProviderMapTest {
    @Test
    void writeTestSuccess(){
        try {
            System.out.println("testEntityWriteTestSuccess");
            Medicine medicine = new Medicine();
            DataProviderMap dataProvider = new DataProviderMap();
            medicine.setNameOfPharmacyProduct("Витамикс");
            medicine.setPrice(124);
            medicine.setBarcode("1234dslkfjskjd");
            medicine.setDescription("Здесь описание витамина");
            dataProvider.addMedicineCategory(medicine, "Витамины", "Витамикс");


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
            medicine.setId(1);
            Map<String, String> categories = new HashMap<>();
            categories.put("Антибактериальные", "Биотрикс");
            categories.put("Обезабливающие", "Анальгин");
            DataProviderMap dataProvider = new DataProviderMap();

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
            DataProviderMap dataProvider = new DataProviderMap();

            System.out.println(dataProvider.getMedicine(2).get());


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
            DataProviderMap dataProvider = new DataProviderMap();

            dataProvider.deleteMedicineCategory(1);


            assertTrue(true);
        }catch (Exception e){
            e.printStackTrace();
            fail();
        }
    }
}