package project.model.collectionsandassociatedentities.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import project.model.HibernateUtil;
import project.model.collectionsandassociatedentities.map.Medicine;

import java.util.*;

public class DataProviderMap {
    private static Logger log = LogManager.getLogger(DataProviderMap.class.getName());
    public void addMedicineCategory(Medicine medicine, String category, String med) {
        try {
            log.info("addMedicineCategory[1]{}");
            Session session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            medicine.getMapCategoryOfTheMeddicine().put(category, med);
            session.save(medicine);
            log.info("addMedicineCategory[2]{}");
            session.getTransaction().commit();
        }catch (Exception e){
            log.error("addMedicineCategory[3]{} " + e.getMessage());

        }
    }

    public void updateMedicineCategories(Medicine medicine, Map<String, String> categories) {
        try {
            log.info("updateMedicineCategories[1]{}");
            Session session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            medicine.setMapCategoryOfTheMeddicine(categories);
            session.update(medicine);
            log.info("updateMedicineCategories[2]{}");
            session.getTransaction().commit();
        }catch (Exception e){
            log.error("updateMedicineCategories[3]{} " + e.getMessage());
        }
    }


    public Optional<Medicine> getMedicine(long id) {
        Optional<Medicine> medicine = Optional.empty();
        try {
            log.info("getMedicine[1]{}");
            Session session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            medicine = Optional.of((Medicine) session.get(Medicine.class, id));
            log.info("getMedicine[2]{}");
            session.getTransaction().commit();
        }catch (Exception e){
            log.error("getMedicine[3]{} " + e.getMessage());
        }
        return medicine;
    }

    public void deleteMedicineCategory(long id) {
        try {
            log.info("deleteMedicineCategory[1]{}");
            Session session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            Medicine medicine = getMedicine(id).get();
            medicine.setMapCategoryOfTheMeddicine(new HashMap<>());
            session.delete(medicine);
            log.info("deleteMedicine[2]{}");
            session.getTransaction().commit();
        }catch (Exception e){
            log.error("deleteMedicineCategory[3]{} " + e.getMessage());
        }
    }
}
