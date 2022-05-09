package project.model.inheritance.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import project.model.HibernateUtil;
import project.model.inheritance.joined.MedicalDevice;
import project.model.inheritance.joined.Medicine;
import project.model.inheritance.joined.PharmacyProduct;
import project.utils.Constant;

import java.util.List;
import java.util.Optional;

public class JoinedService implements IServiceJoined{
    private static Logger log = LogManager.getLogger(MappedSuperClassService.class.getName());

    @Override
    public void addPharmacyProduct(PharmacyProduct pharmacyProduct) {
        try {
            log.info("addPharmacyProduct[1]{}");
            Session session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.save(pharmacyProduct);
            log.info("addPharmacyProduct[2]{}");
            session.getTransaction().commit();
        }catch (Exception e){
            log.error("addPharmacyProduct[3]{} " + e.getMessage());
        }
    }

    @Override
    public void updatePharmacyProduct(PharmacyProduct pharmacyProduct) {
        try {
            log.info("updatePharmacyProduct[1]{}");
            Session session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.update(pharmacyProduct);
            log.info("updatePharmacyProduct[2]{}");
            session.getTransaction().commit();
        }catch (Exception e){
            log.error("updatePharmacyProduct[3]{} " + e.getMessage());
        }
    }

    @Override
    public Optional<List<PharmacyProduct>> getAllPharmacyProduct() {
        Optional<List<PharmacyProduct>> pharmacyProductList = Optional.empty();
        try {
            log.info("getAllPharmacyProduct[1]{}");
            Session session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            pharmacyProductList = Optional.of(session.createQuery("SELECT a FROM PharmacyProduct a", PharmacyProduct.class).getResultList());
            log.info("getAllPharmacyProduct[2]{}");
            session.getTransaction().commit();
        }catch (Exception e){
            log.error("getAllPharmacyProduct[3]{} " + e.getMessage());
        }
        return pharmacyProductList;
    }

    @Override
    public Optional<PharmacyProduct> getPharmacyProduct(long id) {
        Optional<PharmacyProduct> pharmacyProduct = Optional.empty();
        try {
            log.info("getPharmacyProduct[1]{}");
            Session session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            pharmacyProduct = Optional.of((PharmacyProduct) session.createQuery(Constant.SELECT_PHARMACYPRODUCT_BY_ID + id, PharmacyProduct.class));
            log.info("getPharmacyProduct[2]{}");
            session.getTransaction().commit();
        }catch (Exception e){
            log.error("getPharmacyProduct[3]{} " + e.getMessage());
        }
        return pharmacyProduct;
    }

    @Override
    public void deletePharmacyProduct(long id) {
        try {
            log.info("deletePharmacyProduct[1]{}");
            Session session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            PharmacyProduct pharmacyProduct = getPharmacyProduct(id).get();
            session.delete(pharmacyProduct);
            log.info("deletePharmacyProduct[2]{}");
            session.getTransaction().commit();
        }catch (Exception e){
            log.error("deletePharmacyProduct[3]{} " + e.getMessage());
        }
    }


    @Override
    public void addMedicalDevice(MedicalDevice medicalDevice) {
        try {
            log.info("addMedicalDevice[1]{}");
            Session session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.save(medicalDevice);
            log.info("addMedicalDevice[2]{}");
            session.getTransaction().commit();
        }catch (Exception e){
            log.error("addMedicalDevice[3]{} " + e.getMessage());
        }
    }

    @Override
    public void updateMedicalDevice(MedicalDevice medicalDevice) {
        try {
            log.info("updateMedicalDevice[1]{}");
            Session session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.update(medicalDevice);
            log.info("updateMedicalDevice[2]{}");
            session.getTransaction().commit();
        }catch (Exception e){
            log.error("updateMedicalDevice[3]{} " + e.getMessage());
        }
    }

    @Override
    public Optional<List<MedicalDevice>> getAllMedicalDevice() {
        Optional<List<MedicalDevice>> medicalDevices = Optional.empty();
        try {
            log.info("getAllMedicalDevice[1]{}");
            Session session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            medicalDevices = Optional.of(session.createQuery(Constant.SECLECT_ALL_FROM_MAPPEDSUPERCLASSMEDICALDEVICE, MedicalDevice.class).getResultList());
            log.info("getAllMedicalDevice[2]{}");
            session.getTransaction().commit();
        }catch (Exception e){
            log.error("getAllMedicalDevice[3]{} " + e.getMessage());
        }
        return medicalDevices;
    }

    @Override
    public Optional<MedicalDevice> getMedicalDevice(long id) {
        Optional<MedicalDevice> medicalDevice = Optional.empty();
        try {
            log.info("getPharmacyProduct[1]{}");
            Session session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            medicalDevice = Optional.of((MedicalDevice)session.get(MedicalDevice.class, id));
            log.info("getPharmacyProduct[2]{}");
            session.getTransaction().commit();
        }catch (Exception e){
            log.error("getPharmacyProduct[3]{} " + e.getMessage());
        }
        return medicalDevice;
    }

    @Override
    public void deleteMedicalDevice(long id) {
        try {
            log.info("deleteMedicalDevice[1]{}");
            Session session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            MedicalDevice medicalDevice = getMedicalDevice(id).get();
            session.delete(medicalDevice);
            log.info("deleteMedicalDevice[2]{}");
            session.getTransaction().commit();
        }catch (Exception e){
            log.error("deletePharmacyProduct[3]{} " + e.getMessage());
        }
    }

    @Override
    public void addMedicine(Medicine medicine) {
        try {
            log.info("addMedicine[1]{}");
            Session session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.save(medicine);
            log.info("addMedicine[2]{}");
            session.getTransaction().commit();
        }catch (Exception e){
            log.error("addMedicine[3]{} " + e.getMessage());
        }
    }

    @Override
    public void updateMedicine(Medicine medicine) {
        try {
            log.info("updateMedicine[1]{}");
            Session session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.update(medicine);
            log.info("updateMedicine[2]{}");
            session.getTransaction().commit();
        }catch (Exception e){
            log.error("updateMedicine[3]{} " + e.getMessage());
        }
    }

    @Override
    public Optional<List<Medicine>> getAllMedicine() {
        return Optional.empty();
    }

    @Override
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

    @Override
    public void deleteMedicine(long id) {
        try {
            log.info("deleteMedicine[1]{}");
            Session session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            Medicine medicine = getMedicine(id).get();
            session.delete(medicine);
            log.info("deleteMedicine[2]{}");
            session.getTransaction().commit();
        }catch (Exception e){
            log.error("deleteMedicine[3]{} " + e.getMessage());
        }
    }
}
