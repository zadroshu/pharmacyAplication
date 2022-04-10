package project.utils;

public class Constant {

    public static int CODE_ERROR= 500;
    public static int CODE_OK = 200;

    public static final String PATHPROG = "C:\\Program Files (x86)\\ZBar\\bin\\zbarimg";

    public static final String CSV_USER = "C:\\Users\\zadro\\IdeaProjects\\ru.sfedu.searchOfMeddicine\\src\\main\\resources\\data_csv\\user.csv";
    public static final String XML_USER = "C:\\Users\\zadro\\IdeaProjects\\ru.sfedu.searchOfMeddicine\\src\\main\\resources\\data_xml\\user.xml";
    public static final String CSV_MEDICINE = "C:\\Users\\zadro\\IdeaProjects\\ru.sfedu.searchOfMeddicine\\src\\main\\resources\\data_csv\\medicine.csv";
    public static final String XML_MEDICINE = "C:\\Users\\zadro\\IdeaProjects\\ru.sfedu.searchOfMeddicine\\src\\main\\resources\\data_xml\\medicine.xml";
    public static final String CSV_MEDICALDEVICES = "C:\\Users\\zadro\\IdeaProjects\\ru.sfedu.searchOfMeddicine\\src\\main\\resources\\data_csv\\medicalDevice.csv";
    public static final String XML_MEDICALDEVICES = "C:\\Users\\zadro\\IdeaProjects\\ru.sfedu.searchOfMeddicine\\src\\main\\resources\\data_xml\\medicalDevice.xml";
    public static final String CSV_ORDERS = "C:\\Users\\zadro\\IdeaProjects\\ru.sfedu.searchOfMeddicine\\src\\main\\resources\\data_csv\\orders.csv";
    public static final String XML_ORDERS = "C:\\Users\\zadro\\IdeaProjects\\ru.sfedu.searchOfMeddicine\\src\\main\\resources\\data_xml\\orders.xml";

    public static final String DRIVER_H2 = "org.h2.Driver";
    public static final String URL_H2 = "jdbc:h2:C:\\Users\\zadro\\IdeaProjects\\ru.sfedu.searchOfMeddicine\\src\\main\\resources\\H2.mv.db\\pharmacyAplication";
    public static final String USERNAME_H2 = "edlenko";
    public static final String PASSWORD_H2 = "edlenko";

    public static final String INSERT_INTO_USER = "INSERT INTO USER ";
    public static final String SELECT_ALL_FROM_USER = "SELECT * FROM USER ";
    public static final String UPDATE_USER = "UPDATE USER ";
    public static final String DELETE_FROM_USER_BY_ID = "DELETE FROM USER WHERE ID = ";

    public static final String DELETE_FROM_MEDDICINE_BY_BARCODE = "DELETE FROM MEDDICINE WHERE BARCODE = ";
    public static final String INSERT_INTO_MEDDICINE = "INSERT INTO MEDDICINE ";
    public static final String SELECT_ALL_FROM_MEDDICINE = "SELECT * FROM MEDDICINE ";
    public static final String UPDATE_MEDDICINE = "UPDATE MEDDICINE ";

    public static final String DELETE_FROM_MEDICALDEVICES_BY_BARCODE = "DELETE FROM MEDICALDEVICES WHERE BARCODE = ";
    public static final String INSERT_INTO_MEDICALDEVICES = "INSERT INTO MEDICALDEVICES ";
    public static final String SELECT_ALL_FROM_MEDICALDEVICES = "SELECT * FROM MEDICALDEVICES ";
    public static final String UPDATE_MEDICALDEVICES = "UPDATE MEDICALDEVICES ";

    public static final String SELECT_ALL_FROM_ORDERS = "SELECT * FROM ORDERS ";
    public static final String DELETE_FROM_ORDERS_BY_ORDERID = "DELETE FROM ORDERS WHERE ORDERID = ";
    public static final String INSERT_INTO_ORDERS = "INSERT INTO ORDERS ";
    public static final String UPDATE_ORDERS = "UPDATE ORDERS ";

    public static final String MONGO_URL = "mongodb+srv://edlenko:edlenko@cluster0.p4jyy.mongodb.net/myFirstDatabase?retryWrites=true&w=majority";
    public static final String MONGO_USER_DB_NAME = "userHistory";
    public static final String MONGO_USER_COLLECTION_NAME = "userHistoryCollection";
    public static final String MONGO_PHARMACYPRODUCT_DB_NAME = "pharmacyProduct";
    public static final String MONGO_PHARMACYPRODUCT_COLLECTION_NAME = "pharmacyProductCollection";
    public static final String MONGO_USER_ORDER_COLLECTION_NAME = "userOrderCollection";

    public static final String CLI_HELP = "h";
    public static final String CLI_CREATE_ORDER = "o";
    public static final String CLI_GET_ANALAGUES_MEDDICINE = "g";
    public static final String CLI_GET_ANALAGUES_MEDDICINE_BY_BARCODE = "b";
    public static final String CLI_SEARCH_PHARMACY_PRODUCT_BY_CATEGORY = "c";
    public static final String CLI_SEARCH_PHARMACY_PRODUCT_BY_NAME = "n";

    public static final String P_SELECT_DB_VERSION = "SELECT version();";
    public static final String P_SELECT_TABLES = "SELECT *\n" +
            "FROM pg_catalog.pg_tables\n" +
            "WHERE schemaname != 'pg_catalog' AND \n" +
            "    schemaname != 'information_schema';";

    public static final String P_SELECT_TABLE_INFO = "select * from information_schema.columns\n" +
            "where table_schema = 'public';";

    public static final String SECLECT_ALL_FROM_MAPPEDSUPERCLASSPHARMACYPRODUCT = "SELECT a FROM PharmacyProduct a";
    public static final String SELECT_PHARMACYPRODUCT_BY_ID = "SELECT a FROM PharmacyProduct WHERE id = ";
    public static final String SELECT_MEDICALDEVICE_BY_ID = "SELECT a FROM MedicalDevice WHERE id = ";
    public static final String SECLECT_ALL_FROM_MAPPEDSUPERCLASSMEDICALDEVICE = "SELECT a FROM MedicalDevice a";

}
