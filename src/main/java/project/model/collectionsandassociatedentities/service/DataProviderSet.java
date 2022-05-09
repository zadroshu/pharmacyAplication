package project.model.collectionsandassociatedentities.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import project.model.HibernateUtil;
import project.model.collectionsandassociatedentities.set.Medicine;

import java.util.*;

public class DataProviderSet {
    private static Logger log = LogManager.getLogger(DataProviderSet.class.getName());
    public void addMedicineCategory(Medicine medicine, String category) {
        try {
            log.info("addMedicineCategory[1]{}");
            Session session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            medicine.getSetCategoryOfTheMeddicine().add(category);
            session.save(medicine);
            log.info("addMedicineCategory[2]{}");
            session.getTransaction().commit();
        }catch (Exception e){
            log.error("addMedicineCategory[3]{} " + e.getMessage());

        }
    }

    public void updateMedicineCategories(Medicine medicine, Set<String> categories) {
        try {
            log.info("updateMedicineCategories[1]{}");
            Session session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            medicine.setSetCategoryOfTheMeddicine(categories);
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
            log.info("getMedicineBidirectional[1]{}");
            Session session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            medicine = Optional.of((Medicine) session.get(Medicine.class, id));
            log.info("getMedicineBidirectional[2]{}");
            session.getTransaction().commit();
        }catch (Exception e){
            log.error("getMedicineBidirectional[3]{} " + e.getMessage());
        }
        return medicine;
    }

    public void deleteMedicineCategory(long id) {
        try {
            log.info("deleteMedicineCategory[1]{}");
            Session session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            Medicine medicine = getMedicine(id).get();
            medicine.setSetCategoryOfTheMeddicine(new HashSet<>());
            session.delete(medicine);
            log.info("deleteMedicine[2]{}");
            session.getTransaction().commit();
        }catch (Exception e){
            log.error("deleteMedicineCategory[3]{} " + e.getMessage());
        }
    }
}
