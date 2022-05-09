package project.model.displayingrelationshipsbetweenentities.sharedprimarykey;

import org.hibernate.Session;
import org.junit.jupiter.api.Test;
import project.model.HibernateUtil;

import static org.junit.jupiter.api.Assertions.*;

class MedicineSharedPKTest {
    @Test
    public void TestSharedPKWriteNewCategoryAndMedicine(){
        try{
            System.out.println("SharedPK");

            CategorySharedPK categorySharedPK = new CategorySharedPK();
            categorySharedPK.setNameOfCategory("Hormonal drugs");


            MedicineSharedPK medicineSharedPK = new MedicineSharedPK();
            medicineSharedPK.setName("Agesta");
            medicineSharedPK.setCoast(250);
            medicineSharedPK.setCategorySharedPK(categorySharedPK);

            Session session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();

            session.save(categorySharedPK);
            session.save(medicineSharedPK);

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

            System.out.println(session.get(MedicineSharedPK.class, 2L));

            session.getTransaction().commit();



            assertTrue(true);
        }catch (Exception e){
            e.printStackTrace();
            fail();

        }
    }

    @Test
    public void TestFailSharedPKAddMedicine(){
        try{
            System.out.println("SharedPK");

            CategorySharedPK categorySharedPK = new CategorySharedPK();
            categorySharedPK.setId(3L);

            MedicineSharedPK medicineSharedPK = new MedicineSharedPK();
            medicineSharedPK.setId(4L);
            medicineSharedPK.setName("Gistan");
            medicineSharedPK.setCoast(250);
            medicineSharedPK.setCategorySharedPK(categorySharedPK);

            Session session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();

            session.save(medicineSharedPK);

            session.getTransaction().commit();



            fail();
        }catch (Exception e){
            e.printStackTrace();
            assertTrue(true);

        }
    }
}