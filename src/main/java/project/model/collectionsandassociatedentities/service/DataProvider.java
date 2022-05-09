package project.model.collectionsandassociatedentities.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import project.model.HibernateUtil;
import project.model.collectionsandassociatedentities.list.Medicine;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DataProvider {
    private static Logger log = LogManager.getLogger(DataProvider.class.getName());
    public void addMedicineCategory(Medicine medicine, String category) {
        try {
            log.info("addMedicineCategory[1]{}");
            Session session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            medicine.getListCategoryOfTheMeddicine().add(category);
            session.save(medicine);
            log.info("addMedicineCategory[2]{}");
            session.getTransaction().commit();
        }catch (Exception e){
            log.error("addMedicineCategory[3]{} " + e.getMessage());

        }
    }

    public void updateMedicineCategories(Medicine medicine, List<String> categories) {
        try {
            log.info("updateMedicineCategories[1]{}");
            Session session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            medicine.setListCategoryOfTheMeddicine(categories);
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
            medicine.setListCategoryOfTheMeddicine(new ArrayList<>());
            session.delete(medicine);
            log.info("deleteMedicine[2]{}");
            session.getTransaction().commit();
        }catch (Exception e){
            log.error("deleteMedicineCategory[3]{} " + e.getMessage());
        }
    }
}
