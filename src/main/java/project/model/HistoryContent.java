package project.model;

import com.mongodb.*;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bson.Document;
import project.utils.Constant;

import java.util.Date;
import java.util.Map;
import java.util.Objects;


public class HistoryContent {

    private static Logger log = LogManager.getLogger(HistoryContent.class.getName());

    long id;
    String className;
    Date createDate;
    String actor;
    String methodName;
    String status;

    public boolean putUserHistory(User user, String className, String methodName, String status )
    {
        try {
            log.info("putUserHistory: [1] {}");

            MongoClient client = new MongoClient(new MongoClientURI(Constant.MONGO_URL));

            MongoDatabase db = client.getDatabase(Constant.MONGO_USER_DB_NAME);

            MongoCollection collection = db.getCollection(Constant.MONGO_USER_COLLECTION_NAME);

            log.info("putUserHistory: [2] {}");

            collection.insertOne(new Document("_id", id  =  (System.currentTimeMillis() & 0xfffffff))
                    .append("className", className)
                    .append("date", createDate = new Date())
                    .append("actor", actor = System.getProperty("user.name"))
                    .append("methodName", methodName)
                    .append("objectStatus", new BasicDBObject("id", user.getId())
                            .append("login" , user.getLogin())
                            .append("password", user.getPassword()))
                    .append("status", status));

        }catch (Exception e){
            log.error("putUserHistory: [3] {} ".concat(e.getMessage()));
            e.printStackTrace();
            return false;
        }

        return true;
    }

    public boolean putMeddicineHistory(Meddicine meddicine, String className, String methodName, String status )
    {
        try {
            log.info("putMeddicineHistory: [1] {}");

            MongoClient client = new MongoClient(new MongoClientURI(Constant.MONGO_URL));

            MongoDatabase db = client.getDatabase(Constant.MONGO_PHARMACYPRODUCT_DB_NAME);

            MongoCollection collection = db.getCollection(Constant.MONGO_PHARMACYPRODUCT_COLLECTION_NAME);

            log.info("putMeddicineHistory: [2] {}");

            collection.insertOne(new Document("_id", id  =  (System.currentTimeMillis() & 0xfffffff))
                    .append("className", className)
                    .append("date", createDate = new Date())
                    .append("actor", actor = System.getProperty("user.name"))
                    .append("methodName", methodName)
                    .append("objectStatus", new BasicDBObject("barcode", meddicine.getBarcode())
                            .append("NameOfPharmacyProduct" , meddicine.getNameOfPharmacyProduct())
                            .append("Price", meddicine.getPrice())
                            .append("Description", meddicine.getDescription())
                            .append("ActiveSubstanceOfTheMeddicine", meddicine.getActiveSubstanceOfTheMeddicine())
                            .append("CategoryOfTheMeddicine", meddicine.getCategoryOfTheMeddicine()))
                    .append("status", status));

        }catch (Exception e){
            log.error("putMeddicineHistory: [3] {} ".concat(e.getMessage()));
            e.printStackTrace();
            return false;
        }

        return true;
    }

    public boolean putMedicalDeviceHistory(MedicalDevice medicalDevice, String className, String methodName, String status )
    {
        try {
            log.info("putMedicalDeviceHistory: [1] {}");

            MongoClient client = new MongoClient(new MongoClientURI(Constant.MONGO_URL));

            MongoDatabase db = client.getDatabase(Constant.MONGO_PHARMACYPRODUCT_DB_NAME);

            MongoCollection collection = db.getCollection(Constant.MONGO_PHARMACYPRODUCT_COLLECTION_NAME);

            log.info("putMedicalDeviceHistory: [2] {}");

            collection.insertOne(new Document("_id", id  =  (System.currentTimeMillis() & 0xfffffff))
                    .append("className", className)
                    .append("date", createDate = new Date())
                    .append("actor", actor = System.getProperty("user.name"))
                    .append("methodName", methodName)
                    .append("objectStatus", new BasicDBObject("barcode", medicalDevice.getBarcode())
                            .append("NameOfPharmacyProduct" , medicalDevice.getNameOfPharmacyProduct())
                            .append("Price", medicalDevice.getPrice())
                            .append("Description", medicalDevice.getDescription())
                            .append("CategoryOfPharmacyDevices", medicalDevice.getCategoryOfPharmacyDevices()))
                    .append("status", status));

        }catch (Exception e){
            log.error("putMedicalDeviceHistory: [3] {} ".concat(e.getMessage()));
            e.printStackTrace();
            return false;
        }

        return true;
    }

    public boolean putOrderHistory(Order order, String className, String methodName, String status )
    {
        try {
            log.info("putOrderHistory: [1] {}");

            MongoClient client = new MongoClient(new MongoClientURI(Constant.MONGO_URL));

            MongoDatabase db = client.getDatabase(Constant.MONGO_USER_DB_NAME);

            MongoCollection collection = db.getCollection(Constant.MONGO_USER_ORDER_COLLECTION_NAME);

            log.info("putOrderHistory: [2] {}");

            collection.insertOne(new Document("_id", id  =  (System.currentTimeMillis() & 0xfffffff))
                    .append("className", className)
                    .append("date", createDate = new Date())
                    .append("actor", actor = System.getProperty("user.name"))
                    .append("methodName", methodName)
                    .append("objectStatus", new BasicDBObject("order", order.getOrderId())
                            .append("pharmacyProductBarcode", order.getPharmacyProductBarcode()))
                    .append("status", status));

        }catch (Exception e){
            log.error("putOrderHistory: [3] {} ".concat(e.getMessage()));
            e.printStackTrace();
            return false;
        }

        return true;
    }


    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getActor() {
        return actor;
    }

    public void setActor(String actor) {
        this.actor = actor;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }


    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HistoryContent that = (HistoryContent) o;
        return id == that.id && Objects.equals(className, that.className) && Objects.equals(createDate, that.createDate) && Objects.equals(actor, that.actor) && Objects.equals(methodName, that.methodName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, className, createDate, actor, methodName);
    }

    @Override
    public String toString() {
        return "HistoryContent{" +
                "id=" + id +
                ", className='" + className + '\'' +
                ", createDate=" + createDate +
                ", actor='" + actor + '\'' +
                ", methodName='" + methodName + '\'' +
                '}';
    }
}
