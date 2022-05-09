package project.model.collectionsandassociatedentities.service;

import org.junit.jupiter.api.Test;
import project.model.collectionsandassociatedentities.set.Medicine;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

class SetDataProviderTest {

    @Test
    void writeTestSuccess(){
        try {
            System.out.println("testEntityWriteTestSuccess");
            Medicine medicine = new Medicine();
            DataProviderSet dataProvider = new DataProviderSet();
            medicine.setNameOfPharmacyProduct("Витамикс");
            medicine.setPrice(124);
            medicine.setBarcode("1234dslkfjskjd");
            medicine.setDescription("Здесь описание витамина");
            dataProvider.addMedicineCategory(medicine, "Витамины");


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
            Set<String> categories = new HashSet<>();
            categories.add("Антибактериальные");
            categories.add("Обезабливающие");
            DataProviderSet dataProvider = new DataProviderSet();

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
            DataProviderSet dataProvider = new DataProviderSet();

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
            DataProviderSet dataProvider = new DataProviderSet();

            dataProvider.deleteMedicineCategory(2);


            assertTrue(true);
        }catch (Exception e){
            e.printStackTrace();
            fail();
        }
    }

    }

