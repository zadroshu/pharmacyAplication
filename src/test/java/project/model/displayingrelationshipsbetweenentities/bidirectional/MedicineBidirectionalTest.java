package project.model.displayingrelationshipsbetweenentities.bidirectional;

import org.hibernate.Session;
import org.junit.jupiter.api.Test;
import project.model.HibernateUtil;

import static org.junit.jupiter.api.Assertions.*;

class MedicineBidirectionalTest {
    @Test
    public void TestBidirectionalWriteNewCategoryAndMedicine(){
        try{
            System.out.println("TestBidirectional");

            CategoryBidirectional categoryBidirectional = new CategoryBidirectional();
            categoryBidirectional.setNameOfCategory("Hormonal drugs");
            categoryBidirectional.setId(3L);

            MedicineBidirectional medicineBidirectional = new MedicineBidirectional();
            medicineBidirectional.setId(3L);
            medicineBidirectional.setName("Agesta");
            medicineBidirectional.setCoast(250);
            medicineBidirectional.setCategoryBidirectional(categoryBidirectional);

            Session session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();

            session.save(categoryBidirectional);
            session.save(medicineBidirectional);

            session.getTransaction().commit();



            assertTrue(true);
        }catch (Exception e){
            e.printStackTrace();
            fail();

        }
    }

    @Test
    public void TestBidirectionalAddMedicine(){
        try{
            System.out.println("TestBidirectional");

            CategoryBidirectional categoryBidirectional = new CategoryBidirectional();
            categoryBidirectional.setNameOfCategory("Hormonal drugs");
            categoryBidirectional.setId(3L);

            MedicineBidirectional medicineBidirectional = new MedicineBidirectional();
            medicineBidirectional.setId(4L);
            medicineBidirectional.setName("Gistan");
            medicineBidirectional.setCoast(280);
            medicineBidirectional.setCategoryBidirectional(categoryBidirectional);

            Session session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();

            session.save(medicineBidirectional);

            session.getTransaction().commit();



            assertTrue(true);
        }catch (Exception e){
            e.printStackTrace();
            fail();

        }
    }

}