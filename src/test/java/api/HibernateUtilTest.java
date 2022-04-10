package api;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.junit.jupiter.api.Test;
import project.api.HibernateDataProvider;
import project.model.Address;
import project.model.HibernateUtil;
import project.model.TestEntity;

import java.lang.reflect.Array;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

public class HibernateUtilTest {

    private static Logger log = LogManager.getLogger(HibernateUtilTest.class.getName());

    @Test
    void ConnectionHibernateTest() {
        try {
            HibernateDataProvider hibernateDataProvider = new HibernateDataProvider();

            String version = hibernateDataProvider.getDatabaseVersion();

            log.info("actual datasource version  ".concat(version));


            System.out.println(version);


        } catch (Exception e) {
            e.printStackTrace();
            log.error("ConnectionHibernateTest[2]{} " + e.getMessage());
        }

    }

    @Test
    void getDatabaseTablesTest() {
        try {
            System.out.println("getDatabaseTablesTest ");
            HibernateDataProvider hibernateDataProvider = new HibernateDataProvider();
            for (Object[] table : hibernateDataProvider.getDatabaseTables()) {;
                System.out.println((String) table[1]);
            }
            assertTrue(true);
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    void getTableInfoTest() {
        try {
            System.out.println("getTableInfoTest ");
            HibernateDataProvider hibernateDataProvider = new HibernateDataProvider();
            for (Object[] table: hibernateDataProvider.getTableInfo()) {
                System.out.println((String) table[0] + " " + table[2] + " " + table[3] + table[7] + "\n");
            }

            assertTrue(true);
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    void testEntityWriteTestSuccess(){
        try {
            System.out.println("testEntityWriteTestSuccess");

            List<TestEntity> testEntityList = new ArrayList<TestEntity>();

            TestEntity testEntity1 = new TestEntity();
            testEntity1.setName("First");
            testEntity1.setDateCreated(new Date());
            testEntity1.setDescription("First added entity");
            testEntity1.setPass(true);

            TestEntity testEntity2 = new TestEntity();
            testEntity2.setName("Second");
            testEntity2.setDateCreated(new Date());
            testEntity2.setDescription("Second added entity");
            testEntity2.setPass((false));

            TestEntity testEntity3 = new TestEntity();
            testEntity3.setName("Third");
            testEntity3.setDateCreated(new Date());
            testEntity3.setDescription("Third added entity");
            testEntity3.setPass((false));


            testEntityList.add(testEntity1);
            testEntityList.add(testEntity2);
            testEntityList.add(testEntity3);


            HibernateDataProvider hibernateDataProvider = new HibernateDataProvider();
            hibernateDataProvider.testEntityWrite(testEntityList);


            assertTrue(true);
        }catch (Exception e){
            e.printStackTrace();
            fail();
        }
    }

    @Test
    void testEntitieReadTestSuccess(){
        try {
            System.out.println("testEntitieReadTestSuccess");

            HibernateDataProvider hibernateDataProvider = new HibernateDataProvider();

            for (TestEntity testEntitu: hibernateDataProvider.testEntitieRead()) {
                System.out.println(testEntitu);
            }

            assertTrue(true);
        }catch (Exception e){
            e.printStackTrace();
            fail();
        }
    }

    @Test
    void testEntitieUpdateTestSuccess(){
        try {
            System.out.println("testEntitieUpdateTestSuccess");

            TestEntity testEntity = new TestEntity();
            testEntity.setId(4L);
            testEntity.setName("Updated 4");
            testEntity.setDateCreated(new Date());
            testEntity.setDescription("Updated 4");
            testEntity.setPass(true);


            HibernateDataProvider hibernateDataProvider = new HibernateDataProvider();
            for (TestEntity testEntityI: hibernateDataProvider.testEntityUpdate(testEntity)) {
                System.out.println(testEntityI);
            }
            assertTrue(true);
        }catch (Exception e){
            e.printStackTrace();
            fail();
        }
    }

    @Test
    void testEntitieDeleteTestSuccess(){
        try {
            System.out.println("testEntitieDeleteTestSuccess");

            TestEntity testEntity = new TestEntity();
            testEntity.setId(22L);

            HibernateDataProvider hibernateDataProvider = new HibernateDataProvider();
            for (TestEntity testEntityI: hibernateDataProvider.testEntityDelete(testEntity)) {
                System.out.println(testEntityI);
            }
            assertTrue(true);
        }catch (Exception e){
            e.printStackTrace();
            fail();
        }
    }

    @Test
    void testEntitieEmbaddedTestSuccess(){
        try {
            System.out.println("testEntitieEmbaddedTestSuccess");

            Address address = new Address();
            address.setCity("Rostov-on-Don");
            address.setStreet("Zorge 28/2");
            address.setZipcode("344041");

            Address address2 = new Address();
            address2.setCity("Rostov-on-Don");
            address2.setStreet("Krasnoarmeyskaya 117");
            address2.setZipcode("344042");

            TestEntity testEntity = new TestEntity();
            testEntity.setId(4L);
            testEntity.setName("Updated 4");
            testEntity.setDateCreated(new Date());
            testEntity.setDescription("Updated 4");
            testEntity.setPass(true);
            testEntity.setHomeAddress(address);
            testEntity.setWorkAddress(address2);

            Session session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.save(testEntity);
            session.getTransaction().commit();


            assertTrue(true);
        }catch (Exception e){
            e.printStackTrace();
            fail();
        }
    }
}
