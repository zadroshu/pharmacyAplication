package project.api;

import org.hibernate.SessionFactory;
import project.model.HibernateUtil;
import project.utils.Constant;

import java.util.List;

public class HibernateDataProvider {

    public String getDatabaseVersion(){
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        String version = sessionFactory.openSession().createSQLQuery(Constant.P_SELECT_DB_VERSION)
                .list()
                .get(0)
                .toString()
                .split(" ")[1];

        sessionFactory.close();
        return version;
    }

    public List<String> getDatabaseTables(){
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        List<String> databaseTables = sessionFactory.openSession().createSQLQuery("SELECT * FROM information_schema.tables WHERE table_schema = 'public';").list();
        sessionFactory.close();
        return databaseTables;
    }


}
