package project.model.displayingrelationshipsbetweenentities.unidirectional;

import org.hibernate.Session;
import org.junit.jupiter.api.Test;
import project.model.HibernateUtil;

import static org.junit.jupiter.api.Assertions.*;

class MedicineUnidirectionalTest {

    @Test
    public void TestUnidirectionalWriteNewCategoryAndMedicine(){
        try{
            System.out.println("TestUnidirectional");

            CategoryUnidirectional categoryUnidirectional = new CategoryUnidirectional();
            categoryUnidirectional.setNameOfCategory("Hormonal drugs");
            categoryUnidirectional.setId(3L);

            MedicineUnidirectional medicineUnidirectional = new MedicineUnidirectional();
            medicineUnidirectional.setId(3L);
            medicineUnidirectional.setName("Agesta");
            medicineUnidirectional.setCoast(250);
            medicineUnidirectional.setCategoryUnidirectional(categoryUnidirectional);

            Session session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();

            session.save(categoryUnidirectional);
            session.save(medicineUnidirectional);

            session.getTransaction().commit();



            assertTrue(true);
        }catch (Exception e){
            e.printStackTrace();
            fail();

        }
    }

    @Test
    public void TestUnidirectionalAddMedicine(){
        try{
            System.out.println("TestUnidirectional");

            CategoryUnidirectional categoryUnidirectional = new CategoryUnidirectional();
            categoryUnidirectional.setNameOfCategory("Hormonal drugs");
            categoryUnidirectional.setId(3L);

            MedicineUnidirectional medicineUnidirectional = new MedicineUnidirectional();
            medicineUnidirectional.setId(4L);
            medicineUnidirectional.setName("Gistan");
            medicineUnidirectional.setCoast(280);
            medicineUnidirectional.setCategoryUnidirectional(categoryUnidirectional);

            Session session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();

            session.save(medicineUnidirectional);

            session.getTransaction().commit();



            assertTrue(true);
        }catch (Exception e){
            e.printStackTrace();
            fail();

        }
    }

}