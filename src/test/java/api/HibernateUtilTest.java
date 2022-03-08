package api;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.jupiter.api.Test;
import project.api.HibernateDataProvider;
import project.model.HibernateUtil;
import static project.model.HibernateUtil.getSessionFactory;

public class HibernateUtilTest {

    private static Logger log = LogManager.getLogger(HibernateUtilTest.class.getName());

    @Test
    void ConnectionHibernateTest(){
    try {
//        Configuration configuration = new Configuration().configure();
//        configuration.addAnnotatedClass(project.model.User.class);
//        StandardServiceRegistryBuilder builder =
//                new StandardServiceRegistryBuilder().applySettings(configuration.getProperties());
//        SessionFactory factory = configuration.buildSessionFactory(builder.build());

        HibernateDataProvider hibernateDataProvider = new HibernateDataProvider();

        String version = hibernateDataProvider.getDatabaseVersion();

        log.info("actual datasource version  ". concat(version));


        System.out.println(version);


    }catch (Exception e){
        e.printStackTrace();
        log.error("ConnectionHibernateTest[2]{} " + e.getMessage());
    }

    }
}
