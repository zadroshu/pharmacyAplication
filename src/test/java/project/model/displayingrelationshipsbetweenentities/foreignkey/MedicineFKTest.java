package project.model.displayingrelationshipsbetweenentities.foreignkey;

import org.hibernate.Session;
import org.junit.jupiter.api.Test;
import project.model.HibernateUtil;
import project.model.displayingrelationshipsbetweenentities.foreignkey.CategoryFK;
import project.model.displayingrelationshipsbetweenentities.foreignkey.MedicineFK;

import static org.junit.jupiter.api.Assertions.*;

class MedicineFKTest {
    @Test
    public void TestFKWriteNewCategoryAndMedicine(){
        try{
            System.out.println("FK");

            CategoryFK categoryFK = new CategoryFK();
            categoryFK.setNameOfCategory("Hormonal drugs");


            MedicineFK medicineFK = new MedicineFK();
            medicineFK.setName("Agesta");
            medicineFK.setCoast(250);
            medicineFK.setCategoryFK(categoryFK);

            Session session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();

            session.save(categoryFK);
            session.save(medicineFK);

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

            System.out.println(session.get(MedicineFK.class, 1L));

            session.getTransaction().commit();



            assertTrue(true);
        }catch (Exception e){
            e.printStackTrace();
            fail();

        }
    }

    @Test
    public void TestFailFKAddMedicine(){
        try{
            System.out.println("FK");

            CategoryFK categoryFK = new CategoryFK();
            categoryFK.setId(3L);

            MedicineFK medicineFK = new MedicineFK();
            medicineFK.setId(4L);
            medicineFK.setName("Gistan");
            medicineFK.setCoast(250);
            medicineFK.setCategoryFK(categoryFK);

            Session session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();

            session.save(medicineFK);

            session.getTransaction().commit();



            fail();
        }catch (Exception e){
            e.printStackTrace();
            assertTrue(true);

        }
    }
}