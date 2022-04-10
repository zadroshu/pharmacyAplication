package project.api;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import project.model.HibernateUtil;
import project.model.TestEntity;
import project.utils.Constant;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class HibernateDataProvider {

    private static Logger log = LogManager.getLogger(HibernateDataProvider.class.getName());


    public String getDatabaseVersion() {
        log.info("getDatabaseVersion[1]{}");
        Session session = HibernateUtil.getSessionFactory().openSession();
        String version = session.createSQLQuery(Constant.P_SELECT_DB_VERSION)
                .list()
                .get(0)
                .toString()
                .split(" ")[1];
        log.info("getDatabaseVersion[2]{}");
        session.getTransaction().commit();
        return version;
    }

    public List<Object[]> getDatabaseTables() {
        log.info("getDatabaseTables[1]{}");
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        List<Object[]> databaseTables = session.createNativeQuery(Constant.P_SELECT_TABLES).list();
        session.getTransaction().commit();
        log.info("getDatabaseTables[2]{}");
        return databaseTables;
    }

    public List<Object[]> getTableInfo() {
        log.info("getTableInfo[1]{}");
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        List<Object[]> tableInfoList = session.createSQLQuery(Constant.P_SELECT_TABLE_INFO).list();
        session.getTransaction().commit();
        log.info("getTableInfo[2]{}");
        return tableInfoList;
    }


    public List<TestEntity> testEntityWrite(List<TestEntity> testEntityList) {
        try {
            log.info("testEntityAdd[1]{}");
            Session session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();

            for (TestEntity testEntity: testEntityList) {
                session.save(testEntity);
            }
            log.info("testEntityAdd[2]{}");
            session.getTransaction().commit();

        } catch (Exception e) {
            log.error("testEntityAdd[1]{}".concat(e.getMessage()));
            e.printStackTrace();
        }

        return testEntityList;
    }

    public List<TestEntity> testEntitieRead(){
        List<TestEntity> testEntityList = new ArrayList<>();
        try {
            log.info("testEntitieRead[1]{}");
            Session session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            Criteria criteria = session.createCriteria(TestEntity.class);
            log.info("testEntitieRead[2]{}");
            testEntityList  = criteria.list();
            session.getTransaction().commit();

        }catch (Exception e){
            log.error("testEntitieRead[3]{} ".concat(e.getMessage()));
        }finally {
            return testEntityList;
        }

    }

    public List<TestEntity> testEntityUpdate(TestEntity testEntity){
        List<TestEntity> testEntityList = new ArrayList<>();
        try {
            log.info("testEntityUpdate[1]{}");
            Session session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.update(testEntity);
            log.info("testEntityUpdate[2]{}");
            session.getTransaction().commit();
            testEntityList = testEntitieRead();
        }catch (Exception e){
            log.error("testEntityUpdate[]{} ".concat(e.getMessage()));
        }finally {
            return testEntityList;
        }
    }

    public List<TestEntity> testEntityDelete(TestEntity testEntity){
        List<TestEntity> testEntityList = new ArrayList<>();
        try {
            log.info("testEntityDelete[1]{}");
            Session session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.delete(testEntity);
            log.info("testEntityDelete[2]{}");
            session.getTransaction().commit();
            testEntityList = testEntitieRead();
        }catch (Exception e){
            log.error("testEntityDelete[]{} ".concat(e.getMessage()));
        }finally {
            return testEntityList;
        }
    }

}
