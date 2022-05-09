package project.model.displayingrelationshipsbetweenentities.generatedexternalprimarykey;

import org.hibernate.Session;
import org.junit.jupiter.api.Test;
import project.model.HibernateUtil;
import project.model.displayingrelationshipsbetweenentities.generatedexternalprimarykey.CategoryGeneratedPK;
import project.model.displayingrelationshipsbetweenentities.generatedexternalprimarykey.MedicineGeneratedPK;

import static org.junit.jupiter.api.Assertions.*;

class MedicineGeneratedPKTest {
    @Test
    public void TestGeneratedPKWriteNewCategoryAndMedicine(){
        try{
            System.out.println("GeneratedPK");

            MedicineGeneratedPK medicineGeneratedPK = new MedicineGeneratedPK();
            medicineGeneratedPK.setName("Agesta");
            medicineGeneratedPK.setCoast(250);

            CategoryGeneratedPK categoryGeneratedPK = new CategoryGeneratedPK(medicineGeneratedPK);
            categoryGeneratedPK.setNameOfCategory("Hormonal drugs");

            Session session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();

            session.save(medicineGeneratedPK);
            session.save(categoryGeneratedPK);

            session.getTransaction().commit();

            assertTrue(true);
        }catch (Exception e){
            e.printStackTrace();
            fail();

        }
    }

    @Test
    public void TestGet(){
        try{
            System.out.println("TestGet");

            Session session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();

            System.out.println(session.get(MedicineGeneratedPK.class, 2L));

            session.getTransaction().commit();



            assertTrue(true);
        }catch (Exception e){
            e.printStackTrace();
            fail();

        }
    }

    @Test
    public void TestFailGeneratedPKAddMedicine(){
        try{
            System.out.println("GeneratedPK");

            CategoryGeneratedPK categoryGeneratedPK = new CategoryGeneratedPK();
            categoryGeneratedPK.setId(3L);

            MedicineGeneratedPK medicineGeneratedPK = new MedicineGeneratedPK();
            medicineGeneratedPK.setId(4L);
            medicineGeneratedPK.setName("Gistan");
            medicineGeneratedPK.setCoast(250);

            Session session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();

            session.save(medicineGeneratedPK);

            session.getTransaction().commit();



            fail();
        }catch (Exception e){
            e.printStackTrace();
            assertTrue(true);

        }
    }
}