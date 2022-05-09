package project.model.displayingrelationshipsbetweenentities.many_to_many_association;

import org.hibernate.Session;
import org.junit.jupiter.api.Test;
import project.model.HibernateUtil;
import project.model.displayingrelationshipsbetweenentities.many_to_many_association.CategoryMTM;
import project.model.displayingrelationshipsbetweenentities.many_to_many_association.MedicineMTM;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class MedicineMTMTest {
    @Test
    public void TestMTMWriteNewCategoryAndMedicine(){
        try{
            System.out.println("MTM");
            Set<CategoryMTM> categoryMTMSet = new HashSet<>();
            Set<MedicineMTM> medicineMTMSet = new HashSet<>();

            CategoryMTM categoryMTM1 = new CategoryMTM();
            categoryMTM1.setId(2L);
            categoryMTM1.setNameOfCategory("cat1");

            CategoryMTM categoryMTM2 = new CategoryMTM();
            categoryMTM2.setId(3L);
            categoryMTM2.setNameOfCategory("cat2");

            categoryMTMSet.add(categoryMTM1);
            categoryMTMSet.add(categoryMTM2);

            MedicineMTM medicineMTM = new MedicineMTM();
            medicineMTM.setName("testPrep");
            medicineMTM.setCoast(251);
            medicineMTM.setCategorys(categoryMTMSet);


            Session session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();

            session.save(categoryMTM1);
            session.save(medicineMTM);

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

            System.out.println(session.get(MedicineMTM.class, 1L));

            session.getTransaction().commit();



            assertTrue(true);
        }catch (Exception e){
            e.printStackTrace();
            fail();

        }
    }

    @Test
    public void TestFailMTMAddMedicine(){
        try{
            System.out.println("MTM");

            Set<CategoryMTM> categoryMTMSet = new HashSet<>();

            CategoryMTM categoryMTM = new CategoryMTM();
            categoryMTM.setId(3L);

            categoryMTMSet.add(categoryMTM);

            MedicineMTM medicineMTM = new MedicineMTM();
            medicineMTM.setId(4L);
            medicineMTM.setName("Gistan");
            medicineMTM.setCoast(250);
            medicineMTM.setCategorys(categoryMTMSet);

            Session session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();

            session.save(medicineMTM);

            session.getTransaction().commit();



            assertTrue(true);
        }catch (Exception e){
            e.printStackTrace();
            fail();

        }
    }
}