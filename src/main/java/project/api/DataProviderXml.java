package project.api;

import project.model.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;
import project.utils.Constant;
import project.utils.Result;
import project.utils.Wrapper;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class DataProviderXml implements IDataProvider {

    private static Logger log = LogManager.getLogger(DataProviderXml.class.getName());
    /**
     * Recording the state of an User object in Mongodb before its modification
     *
     * @param user
     * @param className
     * @param functionName
     * @param code
     * @return void
     */
    private static boolean takeHistoryUsers(User user, String className, String functionName, String code){
        HistoryContent historyContent = new HistoryContent();
        historyContent.putUserHistory(user, className, functionName, code);
        return true;
    }
    /**
     * reading users from a XML file for Users
     *
     * @param XML_FILE
     * @return List <code><</code>User<code>></code>
     */
    public static List<User> readXml(String XML_FILE) {
        log.info("readXml: [1] {}");
        List<User> userList = new ArrayList<>();
        Wrapper wrapper = new Wrapper();
        try {
            Serializer serializer = new Persister();
            File source = new File(XML_FILE);

            wrapper = serializer.read(Wrapper.class, source);
            userList = wrapper.getUserList();

        } catch (Exception e) {
            log.error("readXml: [2] {}" + e.getMessage());
            e.printStackTrace();
        }
        log.info("readXml: [3] {}");
        return userList;
    }
    /**
     * Checking for duplicates and deleting them from the XML file if available
     *
     * @param addedUserList
     * @param XML_FILE
     * @return List <code><</code>User<code>></code>
     */
    private static List<User> stringingDuplicateId(List<User> addedUserList, String XML_FILE) {

        log.info("stringingDuplicateId: [1] {}");
        DataProviderXml dataProviderXml = new DataProviderXml();
        if (dataProviderXml.getUsers(XML_FILE).getData().isEmpty()) {
            return addedUserList;
        }else {
            List<User> redefinedList = dataProviderXml.getUsers(XML_FILE).getData();

            addedUserList.addAll(redefinedList);
            HashSet<Object> seen = new HashSet<>();
            addedUserList.removeIf(e -> !seen.add(e.getId()));
        }
        log.info("stringingDuplicateId: [2] {}");
        return addedUserList;
    }
    /**
     * Writing to a XML file for Users
     *
     * @param userList
     * @param XML_FILE
     * @return List <code><</code>User<code>></code>
     */
    private static List<User> writeXml(List<User> userList, String XML_FILE) {
        log.info("writeXml: [1] {}");
        Wrapper wrapper = new Wrapper();
        try {
            wrapper.setUserList(userList);
            Serializer serializer = new Persister();
            serializer.write(wrapper, new File(XML_FILE));

        } catch (Exception e) {
            log.error("writeXml: [2] {} " + e.getMessage());
            e.printStackTrace();
        }

        log.info("writeXml: [3] {}");
        return userList;
    }
    /**
     * Checking the presence of an element in a XML file by ID
     *
     * @param id
     * @param XML_FILE
     * @return boolean
     */
    private static boolean isIdInData(long id, String XML_FILE) {
        log.info("isIdInData: [1] {}");
        DataProviderXml dataProviderXml = new DataProviderXml();
        List<User> userList = dataProviderXml.getUsers(XML_FILE).getData();

        Optional<User> checkedUsers = userList.stream()
                .filter(u -> u.getId() == id)
                .findAny();
        if (checkedUsers.isPresent()) {
            log.info("isIdInData: [2] {} Element is found");
            return true;

        } else {
            log.info("isIdInData: [3] {} No Such Element");
            return false;
        }
    }

    public DataProviderXml() {
    }
    /**
     * Getting a List<code><</code>User<code>></code> from a XML file
     *
     * @param XML_FILE
     * @return Result <code><</code>List<code>></code>
     */
    @Override
    public Result<List> getUsers(String XML_FILE) {
        log.info("getUsers: [1] {}");
        Result result = new Result();
        List<User> userList = new ArrayList<>();

        try {

            userList.addAll(readXml(XML_FILE));
            result.setCode(Constant.CODE_OK);

        } catch (Exception e) {
            result.setCode(Constant.CODE_ERROR);
            result.setMessage(e.getMessage());
            log.error("getUsers: [2] {} " + e.getMessage());
            e.printStackTrace();

        }

        result.setData(userList);
        log.info("getUsers: [3] {}");
        return result;
    }

    /**
     * Adding List<code><</code>User<code>></code> to a XML file
     *
     * @param userList
     * @param XML_FILE
     * @return Result <code><</code>List<code>></code>
     */
    @Override
    public Result<List> appendUsers(List<User> userList, String XML_FILE) {

        log.info("appendUsers: [1] {}");
        Result result = new Result();
        try {
            userList = stringingDuplicateId(userList, XML_FILE);

            writeXml(userList, XML_FILE);
            result.setCode(Constant.CODE_OK);

        } catch (Exception e) {
            log.error("appendUsers: [2] {} " + e.getMessage());
            e.printStackTrace();
        }

        result.setData(userList);

        log.info("appendUsers: [3] {}");
        return result;
    }
    /**
     * Deleting  of the User element from XML file by ID
     *
     * @param id
     * @param XML_FILE
     * @return Result <code><</code>List<code>></code>
     */
    @Override
    public Result<List> deleteUserById(long id, String XML_FILE) {
        log.info("deleteUserById: [1] {}");
        DataProviderXml dataProviderXml = new DataProviderXml();
        List<User> userList = dataProviderXml.getUsers(XML_FILE).getData();
        Result result = new Result();
        try {

            if (isIdInData(id, XML_FILE)) {
                takeHistoryUsers(userList.stream()
                        .filter(u -> u.getId() == id)
                        .findFirst()
                        .get(), this.getClass().getSimpleName(), "deleteUserById", "DELETED");
                userList.removeIf(u -> u.getId() == id);
                writeXml(userList, XML_FILE);
                result.setCode(Constant.CODE_OK);
                result.setMessage("OK");
            } else {
                result.setCode(Constant.CODE_ERROR);
                result.setMessage("No Such Element");
            }

        } catch (Exception e) {
            result.setCode(Constant.CODE_ERROR);
            result.setMessage(e.getMessage());
            log.error("deleteUserById: [2] {} ".concat(e.getMessage()));
            e.printStackTrace();
        }

        result.setData(userList);
        log.info("deleteUserById: [3] {}");
        return result;
    }
    /**
     * Updating Users in the XML file if an element with this ID is already present it is replaced otherwise a new one is added
     *
     * @param user
     * @param XML_FILE
     * @return Result <code><</code>List<code>></code>
     */
    @Override
    public Result<List> updateUsers(User user, String XML_FILE) {
        log.info("updateUsers: [1] {}");
        Result result = new Result();
        List<User> userList = new ArrayList<>();
        try {
            userList = getUsers(XML_FILE).getData();

            if (isIdInData(user.getId(), XML_FILE)) {
                takeHistoryUsers(userList.stream()
                        .filter(u -> u.getId() == user.getId())
                        .findFirst()
                        .get(), this.getClass().getSimpleName(), "updateUsers", "UPDATED");
                userList.removeIf(u -> u.getId() == user.getId());
            }
            userList.add(user);
            appendUsers(userList, XML_FILE);

            result.setCode(Constant.CODE_OK);
            result.setMessage("OK");

        } catch (Exception e) {
            log.info("updateUsers: [2] {} " + e.getMessage());
            result.setMessage(e.getMessage());
            result.setCode(Constant.CODE_ERROR);
            e.printStackTrace();
        }

        result.setData(userList);
        log.info("updateUsers: [3] {}");
        return result;
    }
    /**
     * Getting a User object from a XML file by ID
     *
     * @param id
     * @param XML_FILE
     * @return Result
     */
    @Override
    public Result getUserById(long id, String XML_FILE) {
        log.info("getUserById: [1] {}");
        Result result = new Result();
        List<User> userList = getUsers(XML_FILE).getData();
        try {

            if (isIdInData(id, XML_FILE)) {
                result.setData(userList.stream()
                        .filter(u -> u.getId() == id)
                        .findAny()
                        .get());

                result.setCode(Constant.CODE_OK);
                result.setMessage("OK");
            } else {

                result.setCode(Constant.CODE_ERROR);
                log.info("getUserById: [2] {} No Such Element id = " + id);
                result.setMessage("No Such Element id = " + id);
            }

        } catch (Exception e) {
            result.setCode(Constant.CODE_ERROR);
            result.setMessage(e.getMessage());
            log.info("getUserById: [3] {} " + e.getMessage());
            e.printStackTrace();
        }

        log.info("getUserById: [4] {}");
        return result;
    }

    /**
     * Recording the state of an User object in Mongodb before its modification
     *
     * @param order
     * @param className
     * @param functionName
     * @param code
     * @return void
     */
    private static void takeHistoryOrders(Order order, String className, String functionName, String code) {
        HistoryContent historyContent = new HistoryContent();
        historyContent.putOrderHistory(order, className, functionName, code);
    }


    /**
     * reading orders from a XML file for Users
     *
     * @param FILE_PATH
     * @return List <code><</code>Order<code>></code>
     */

    public static List<Order> readXmlOrder(String FILE_PATH) {

        log.info("readXmlOrder: [1] {}");
        List<Order> orderList = new ArrayList<>();
        Wrapper wrapper = new Wrapper();
        try {
            Serializer serializer = new Persister();
            File source = new File(FILE_PATH);

            wrapper = serializer.read(Wrapper.class, source);
            orderList = wrapper.getOrderList();

        } catch (Exception e) {
            log.error("readXmlOrder: [2] {}" + e.getMessage());
            e.printStackTrace();
        }
        log.info("readXmlOrder: [3] {}");
        return orderList;
    }

    /**
     * Checking for duplicates and deleting them from the XML file if available
     *
     * @param addedOrderList
     * @param FILE_PATH
     * @return List <code><</code>Order<code>></code>
     */

    private static List<Order> stringingDuplicateIdOrder(List<Order> addedOrderList, String FILE_PATH) {

        log.info("stringingDuplicateIdOrder: [1] {}");
        DataProviderXml dataProviderXml = new DataProviderXml();
        if (dataProviderXml.getOrders(FILE_PATH).getData().isEmpty()) {
            return addedOrderList;
        }else {
            List<Order> redefinedList = dataProviderXml.getOrders(FILE_PATH).getData();

            addedOrderList.addAll(redefinedList);
            HashSet<Object> seen = new HashSet<>();
            addedOrderList.removeIf(e -> !seen.add(e.getOrderId()));
        }
        log.info("stringingDuplicateIdOrder: [2] {}");
        return addedOrderList;
    }

    /**
     * Writing to a CSV file for Orders
     *
     * @param orderList
     * @param FILE_PATH
     * @return List <code><</code>Order<code>></code>
     */
    public static List<Order> writeXmlOrder(List<Order> orderList, String FILE_PATH) {
        log.info("writeXmlOrder: [1] {}");
        Wrapper wrapper = new Wrapper();
        try {
            if (orderList != null || !(orderList.isEmpty())) {
                wrapper.setOrderList(orderList);
                Serializer serializer = new Persister();
                serializer.write(wrapper, new File(FILE_PATH));
            }
        } catch (Exception e) {
            log.error("writeXmlOrder: [2] {} " + e.getMessage());
            e.printStackTrace();
        }

        log.info("writeXmlOrder: [3] {}");
        return orderList;
    }

    /**
     * Checking the presence of an element in a XML file by ID
     *
     * @param id
     * @param FILE_PATH
     * @return boolean
     */

    private static boolean isIdInDataOrder(long id, String FILE_PATH) {
        log.info("isIdInDataOrder: [1] {}");
        DataProviderXml dataProviderXml = new DataProviderXml();
        List<Order> orderList = dataProviderXml.getOrders(FILE_PATH).getData();

        Optional<Order> checkedOrders = orderList.stream()
                .filter(u -> u.getOrderId() == id)
                .findAny();
        if (checkedOrders.isPresent()) {
            log.info("isIdInDataOrder: [2] {} Element is found");
            return true;

        } else {
            log.info("isIdInDataOrder: [3] {} No Such Element");
            return false;
        }
    }


    /**
     * Getting a List<code><</code>Order<code>></code> from a XML file
     *
     * @param FILE_PATH
     * @return Result <code><</code>List<code>></code>
     */


    public Result<List> getOrders(String FILE_PATH) {
        log.info("getOrders: [1] {}");
        Result result = new Result();
        List<Order> orderList = new ArrayList<>();

        try {

            orderList.addAll(readXmlOrder(FILE_PATH));
            result.setCode(Constant.CODE_OK);

        } catch (Exception e) {
            result.setCode(Constant.CODE_ERROR);
            result.setMessage(e.getMessage());
            log.error("getOrders: [2] {} " + e.getMessage());
            e.printStackTrace();

        }

        result.setData(orderList);
        log.info("getOrders: [3] {}");
        return result;
    }

    /**
     * Adding List<code><</code>Order<code>></code> to a XML file
     *
     * @param orderList
     * @param FILE_PATH
     * @return Result <code><</code>List<code>></code>
     */

    public Result<List> appendOrders(List<Order> orderList, String FILE_PATH) {

        log.info("appendOrders: [1] {}");
        Result result = new Result();
        try {
            orderList = stringingDuplicateIdOrder(orderList, FILE_PATH);

            writeXmlOrder(orderList, FILE_PATH);
            result.setCode(Constant.CODE_OK);

        } catch (Exception e) {
            log.error("appendOrders: [2] {} " + e.getMessage());
            e.printStackTrace();
        }

        result.setData(orderList);

        log.info("appendOrders: [3] {}");
        return result;
    }

    /**
     * Deleting  of the Order element from XML file by ID
     *
     * @param id
     * @param FILE_PATH
     * @return Result <code><</code>List<code>></code>
     */

    public Result<List> deleteOrderById(long id, String FILE_PATH) {
        log.info("deleteOrderById: [1] {}");
        DataProviderXml dataProviderXml = new DataProviderXml();
        List<Order> orderList = dataProviderXml.getOrders(FILE_PATH).getData();
        Result result = new Result();
        try {

            if (isIdInDataOrder(id, FILE_PATH)) {
                takeHistoryOrders(orderList.stream()
                        .filter(u -> u.getOrderId() == id)
                        .findFirst()
                        .get(), this.getClass().getSimpleName(), "deleteOrderById", "DELETED");
                orderList.removeIf(u -> u.getOrderId() == id);
                writeXmlOrder(orderList, FILE_PATH);
                result.setCode(Constant.CODE_OK);
                result.setMessage("OK");
            } else {
                result.setCode(Constant.CODE_ERROR);
                result.setMessage("No Such Element");
            }

        } catch (Exception e) {
            result.setCode(Constant.CODE_ERROR);
            result.setMessage(e.getMessage());
            log.error("deleteOrderById: [2] {} ".concat(e.getMessage()));
            e.printStackTrace();
        }

        result.setData(orderList);
        log.info("deleteOrderById: [3] {}");
        return result;
    }

    /**
     * Updating Order in the XML file if an element with this ID is already present it is replaced otherwise a new one is added
     *
     * @param order
     * @param FILE_PATH
     * @return Result <code><</code>List<code>></code>
     */

    public Result<List> updateOrders(Order order, String FILE_PATH) {
        log.info("updateOrders: [1] {}");
        Result result = new Result();
        List<Order> orderList = new ArrayList<>();
        try {
            orderList = getOrders(FILE_PATH).getData();

            if (isIdInDataOrder(order.getOrderId(), FILE_PATH)) {
                takeHistoryOrders(orderList.stream()
                        .filter(u -> u.getOrderId() == order.getOrderId())
                        .findFirst()
                        .get(), this.getClass().getSimpleName(), "updateOrders", "UPDATED");
                orderList.removeIf(u -> u.getOrderId() == order.getOrderId());
            }
            orderList.add(order);
            appendOrders(orderList, FILE_PATH);

            result.setCode(Constant.CODE_OK);
            result.setMessage("OK");

        } catch (Exception e) {
            log.info("updateOrders: [2] {} " + e.getMessage());
            result.setMessage(e.getMessage());
            result.setCode(Constant.CODE_ERROR);
            e.printStackTrace();
        }

        result.setData(orderList);
        log.info("updateOrders: [3] {}");
        return result;
    }

    /**
     * Getting a Order object from a XML file by ID
     *
     * @param id
     * @param FILE_PATH
     * @return Result
     */

    public Result getOrderById(long id, String FILE_PATH) {
        log.info("getOrderById: [1] {}");
        Result result = new Result();
        List<Order> orderList = getOrders(FILE_PATH).getData();
        try {

            if (isIdInDataOrder(id, FILE_PATH)) {
                result.setData(orderList.stream()
                        .filter(u -> u.getOrderId() == id)
                        .findAny()
                        .get());

                result.setCode(Constant.CODE_OK);
                result.setMessage("OK");
            } else {

                result.setCode(Constant.CODE_ERROR);
                log.info("getOrderById: [2] {} No Such Element id = " + id);
                result.setMessage("No Such Element id = " + id);
            }

        } catch (Exception e) {
            result.setCode(Constant.CODE_ERROR);
            result.setMessage(e.getMessage());
            log.info("getOrderById: [3] {} " + e.getMessage());
            e.printStackTrace();
        }

        log.info("getOrderById: [4] {}");
        return result;
    }

    /**
     * Creating order for user and updating order and user XML files
     * @param userId
     * @param pharmacyProductName
     * @return Order
     */
    public Order createOrder(long userId, String pharmacyProductName) {
        log.info("createOrder [1] {}");
        Order order = new Order();
        try {
            if (getUserById(userId, Constant.XML_USER).getData() != null) {
                order.setOrderId(userId);
                changePharmacyProduct(order, pharmacyProductName);
            } else {
                log.info("createOrder [2] {} No such user");
            }
            log.info("createOrder [3] {}");
        } catch (Exception e) {
            log.error("createOrder [4] {} ".concat(e.getMessage()));
            e.printStackTrace();
        }
        return order;
    }

    /**
     * adding pharmacy product to orders XML file
     * @param order
     * @param pharmacyProductName
     */
    public void changePharmacyProduct(Order order, String pharmacyProductName){
        log.info("pharmacyProductName [1] {}");
        try {
            if (!searchPharmacyProductByName(pharmacyProductName).isEmpty()) {
                order.setPharmacyProductBarcode(searchPharmacyProductByName(pharmacyProductName).get(0).getBarcode());
                updateOrders(order, Constant.XML_ORDERS);
            } else {
                log.info("pharmacyProductName [2] {} No such pharmacy product");
            }
        }catch (Exception e){
            log.error("pharmacyProductName [3]".concat(e.getMessage()));
            e.printStackTrace();
        }
    }
    /**
     * Recording the state of an Meddicine object in Mongodb before its modification
     *
     * @param meddicine
     * @param className
     * @param functionName
     * @param code
     */

    private static boolean takeHistoryMeddicine(Meddicine meddicine, String className, String functionName, String code){
        HistoryContent historyContent = new HistoryContent();
        historyContent.putMeddicineHistory(meddicine, className, functionName, code);
        return true;
    }

    /**
     * Writing to a XML file for Meddicines
     *
     * @param meddicineList
     * @param XML_FILE
     * @return Result <code><</code>List<code>></code>
     */

    public static List<Meddicine> writeXmlMedicine(List<Meddicine> meddicineList, String XML_FILE) {
        log.info("writeCsvMedicine: [1] {}");
        Wrapper wrapper = new Wrapper();
        try {
            wrapper.setMeddicineList(meddicineList);
            Serializer serializer = new Persister();
            serializer.write(wrapper, new File(XML_FILE));
        } catch (Exception e) {
            log.error("writeCsvMedicine: [2] {} ".concat(e.getMessage()));
            e.printStackTrace();
        }

        log.info("writeCsvMedicine: [3] {}");
        return meddicineList;
    }
    /**
     * reading Medicines from a XML file
     *
     * @param XML_FILE
     * @return List <code><</code>Meddicine<code>></code>
     */

    public static List<Meddicine> readXmlMedicine(String XML_FILE) {

        log.info("readXmlMedicine: [1] {}");
        List<Meddicine> meddicineList = new ArrayList<>();
        Wrapper wrapper = new Wrapper();
        try {
            Serializer serializer = new Persister();
            File source = new File(XML_FILE);

            wrapper = serializer.read(Wrapper.class, source);
            meddicineList = wrapper.getMeddicineList();

        } catch (Exception e) {
            log.error("readXmlMedicine: [2] {}" + e.getMessage());
            e.printStackTrace();
        }
        log.info("readXmlMedicine: [3] {}");
        return meddicineList;
    }
    /**
     * Checking for duplicates and deleting them from the XML file if available
     *
     * @param addedMeddicineList
     * @param XML_FILE
     * @return List <code><</code>Meddicine<code>></code>
     */
    private static List<Meddicine> stringingDuplicateBarcode(List<Meddicine> addedMeddicineList, String XML_FILE) {

        log.info("stringingDuplicateBarcode: [1] {}");
        DataProviderXml dataProviderXml = new DataProviderXml();
        if (dataProviderXml.getMeddicines(XML_FILE).getData().isEmpty()) {
            return addedMeddicineList;
        }else {
            List<Meddicine> redefinedList = dataProviderXml.getMeddicines(XML_FILE).getData();

            addedMeddicineList.addAll(redefinedList);
            HashSet<Object> seen = new HashSet<>();
            addedMeddicineList.removeIf(e -> !seen.add(e.getBarcode()));
        }
        log.info("stringingDuplicateBarcode: [2] {}");
        return addedMeddicineList;
    }

    /**
     * Checking the presence of an element in a XML MedicineUnidirectional file by Barcode
     *
     * @param barcode
     * @param XML_FILE
     * @return boolean
     */
    private static boolean isIdInDataMedicine(String barcode, String XML_FILE) {
        log.info("isIdInDataMedicine: [1] {}");
        DataProviderXml dataProviderXml = new DataProviderXml();
        List<Meddicine> meddicineList = dataProviderXml.getMeddicines(XML_FILE).getData();

        Optional<Meddicine> checkedMedicine = meddicineList.stream()
                .filter(u -> u.getBarcode().equals(barcode))
                .findAny();
        if (checkedMedicine.isPresent()) {
            log.info("isIdInDataMedicine: [2] {} Element is found");
            return true;

        } else {
            log.info("isIdInDataMedicine: [3] {} No Such Element");
            return false;
        }
    }

    /**
     * Getting a list of meddicines from a XML file
     *
     * @param FILE_PATH
     * @return Result <code><</code>List<code>></code>
     */

    @Override
    public Result<List> getMeddicines(String FILE_PATH) {
        log.info("getMeddicines: [1] {}");
        Result result = new Result();
        List<Meddicine> meddicineList = new ArrayList<>();

        try {

            meddicineList.addAll(readXmlMedicine(FILE_PATH));
            result.setCode(Constant.CODE_OK);

        } catch (Exception e) {
            result.setCode(Constant.CODE_ERROR);
            result.setMessage(e.getMessage());
            log.error("getMeddicines: [2] {} " + e.getMessage());
            e.printStackTrace();

        }

        result.setData(meddicineList);
        log.info("getMeddicines: [3] {}");
        return result;
    }

    /**
     * Adding list of meddicines to a Meddicinenes XML file
     *
     * @param meddicinesList
     * @param FILE_PATH
     * @return Result <code><</code>List<code>></code>
     */

    @Override
    public Result<List> appendMeddicines(List<Meddicine> meddicinesList, String FILE_PATH) {
        log.info("appendMeddicines: [1] {}");
        Result result = new Result();
        try {
            meddicinesList = stringingDuplicateBarcode(meddicinesList, FILE_PATH);

            writeXmlMedicine(meddicinesList, FILE_PATH);
            result.setCode(Constant.CODE_OK);

        } catch (Exception e) {
            log.error("appendMeddicines: [2] {} " + e.getMessage());
            e.printStackTrace();
        }

        result.setData(meddicinesList);

        log.info("appendMeddicines: [3] {}");
        return result;
    }

    /**
     * Delete medicineBidirectional object from medicineBidirectional XML file by Barcode
     *
     * @param barcode
     * @param FILE_PATH
     * @return Result <code><</code>List<code>></code>
     */

    @Override
    public Result<List> deleteMeddicineByBarcode(String barcode, String FILE_PATH) {
        log.info("deleteMeddicineByBarcode: [1] {}");
        DataProviderXml dataProviderXml = new DataProviderXml();
        List<Meddicine> meddicinesList = dataProviderXml.getMeddicines(FILE_PATH).getData();
        Result result = new Result();
        try {

            if (isIdInDataMedicine(barcode, FILE_PATH)) {
                takeHistoryMeddicine(meddicinesList.stream()
                        .filter(m -> m.getBarcode().equals(barcode))
                        .findFirst()
                        .get(), this.getClass().getSimpleName(), "deleteMeddicineByBarcode", "DELETED");
                meddicinesList.removeIf(u -> u.getBarcode().equals(barcode));
                writeXmlMedicine(meddicinesList, FILE_PATH);
                result.setCode(Constant.CODE_OK);
                result.setMessage("OK");
            } else {
                result.setCode(Constant.CODE_ERROR);
                result.setMessage("No Such Element");
            }

        } catch (Exception e) {
            result.setCode(Constant.CODE_ERROR);
            result.setMessage(e.getMessage());
            log.error("deleteMeddicineByBarcode: [2] {} ".concat(e.getMessage()));
            e.printStackTrace();
        }

        result.setData(meddicinesList);
        log.info("deleteMeddicineByBarcode: [3] {}");
        return result;
    }

    /**
     * Updating medicineBidirectional object in the XML file if an element with this Barcode is already present it is replaced otherwise a new one is added
     *
     * @param meddicine
     * @param FILE_PATH
     * @return Result <code><</code>List<code>></code>
     */

    @Override
    public Result<List> updateMeddicine(Meddicine meddicine, String FILE_PATH) {
        log.info("updateMeddicine: [1] {}");
        Result result = new Result();
        List<Meddicine> meddicineList = new ArrayList<>();
        try {
            meddicineList = getMeddicines(FILE_PATH).getData();

            if (isIdInDataMedicine(meddicine.getBarcode(), FILE_PATH)) {
                takeHistoryMeddicine(meddicineList.stream()
                        .filter(m -> m.getBarcode().equals(meddicine.getBarcode()))
                        .findFirst()
                        .get(), this.getClass().getSimpleName(), "updateMeddicine", "UPDATED");
                meddicineList.removeIf(u -> u.getBarcode().equals(meddicine.getBarcode()));
            }
            meddicineList.add(meddicine);
            appendMeddicines(meddicineList, FILE_PATH);

            result.setCode(Constant.CODE_OK);
            result.setMessage("OK");

        } catch (Exception e) {
            log.info("updateMeddicine: [2] {} " + e.getMessage());
            result.setMessage(e.getMessage());
            result.setCode(Constant.CODE_ERROR);
            e.printStackTrace();
        }

        result.setData(meddicineList);
        log.info("updateMeddicine: [3] {}");
        return result;
    }

    /**
     * Getting a medicineBidirectional object from a XML file by barcode
     *
     * @param barcode
     * @param FILE_PATH
     * @return Result
     */

    @Override
    public Result getMeddicineByBarcode(String barcode, String FILE_PATH) {
        log.info("getMeddicineByBarcode: [1] {}");
        Result result = new Result();
        List<Meddicine> meddicineList = getMeddicines(FILE_PATH).getData();
        try {

            if (isIdInDataMedicine(barcode, FILE_PATH)) {
                result.setData(meddicineList.stream()
                        .filter(u -> u.getBarcode().equals(barcode))
                        .findAny()
                        .get());

                result.setCode(Constant.CODE_OK);
                result.setMessage("OK");
            } else {

                result.setCode(Constant.CODE_ERROR);
                log.info("getMeddicineByBarcode: [2] {} No Such Element");
                result.setMessage("No Such Element ");
            }

        } catch (Exception e) {
            result.setCode(Constant.CODE_ERROR);
            result.setMessage(e.getMessage());
            log.info("getMeddicineByBarcode: [3] {} " + e.getMessage());
            e.printStackTrace();
        }

        log.info("getUserById: [4] {}");
        return result;
    }
    /**
     * Recording the state of an MedicalDevice object in Mongodb before its modification
     *
     * @param medicalDevice
     * @param className
     * @param functionName
     * @param code
     */

    private static boolean takeHistoryMedicalDevice(MedicalDevice medicalDevice, String className, String functionName, String code){
        HistoryContent historyContent = new HistoryContent();
        historyContent.putMedicalDeviceHistory(medicalDevice, className, functionName, code);
        return true;
    }

    /**
     * Writing to a MedicalDevice object to XML file
     *
     * @param medicalDeviceList
     * @param XML_FILE
     * @return List <code><</code>MedicalDevice<code>></code>
     */
    public static List<MedicalDevice> writeXmlMedicalDevice(List<MedicalDevice> medicalDeviceList, String XML_FILE) {
        log.info("writeXmlMedicalDevice: [1] {}");
        Wrapper wrapper = new Wrapper();
        try {
            wrapper.setMedicalDeviceList(medicalDeviceList);
            Serializer serializer = new Persister();
            serializer.write(wrapper, new File(XML_FILE));
        } catch (Exception e) {
            log.error("writeXmlMedicalDevice: [2] {} ".concat(e.getMessage()));
            e.printStackTrace();
        }

        log.info("writeCsvMedicine: [3] {}");
        return medicalDeviceList;
    }
    /**
     * Reading MedicalDevice from a XML file
     *
     * @param XML_FILE
     * @return List <code><</code>MedicalDevice<code>></code>
     */
    public static List<MedicalDevice> readXmlMedicalDevice(String XML_FILE) {

        log.info("readXmlMedicalDevice: [1] {}");
        List<MedicalDevice> medicalDeviceList = new ArrayList<>();
        Wrapper wrapper = new Wrapper();
        try {
            Serializer serializer = new Persister();
            File source = new File(XML_FILE);

            wrapper = serializer.read(Wrapper.class, source);
            medicalDeviceList = wrapper.getMedicalDeviceList();

        } catch (Exception e) {
            log.error("readXmlMedicalDevice: [2] {}" + e.getMessage());
            e.printStackTrace();
        }
        log.info("readXmlMedicine: [3] {}");
        return medicalDeviceList;
    }

    /**
     * Checking medicalDevice object for duplicates and deleting them from the XML file if available
     *
     * @param addedMedicalDeviceList
     * @param XML_FILE
     * @return List <code><</code>MedicalDevice<code>></code>
     */
    private static List<MedicalDevice> stringingDuplicateBarcodeMedicalDevice(List<MedicalDevice> addedMedicalDeviceList, String XML_FILE) {

        log.info("stringingDuplicateBarcodeMedicalDevice: [1] {}");
        DataProviderXml dataProviderXml = new DataProviderXml();
        if (dataProviderXml.getMedicalDevices(XML_FILE).getData().isEmpty()) {
            return addedMedicalDeviceList;
        }else {
            List<MedicalDevice> redefinedList = dataProviderXml.getMedicalDevices(XML_FILE).getData();

            addedMedicalDeviceList.addAll(redefinedList);
            HashSet<Object> seen = new HashSet<>();
            addedMedicalDeviceList.removeIf(e -> !seen.add(e.getBarcode()));
        }
        log.info("stringingDuplicateBarcodeMedicalDevice: [2] {}");
        return addedMedicalDeviceList;
    }

    /**
     * Checking the presence of an medicalDevice object in a XML  file by Barcode
     *
     * @param barcode
     * @param XML_FILE
     * @return boolean
     */
    private static boolean isIdInDataMedicalDevice(String barcode, String XML_FILE) {
        log.info("isIdInDataMedicalDevice: [1] {}");
        DataProviderXml dataProviderXml = new DataProviderXml();
        List<MedicalDevice> medicalDeviceList = dataProviderXml.getMedicalDevices(XML_FILE).getData();

        Optional<MedicalDevice> checkedMedicineDevice = medicalDeviceList.stream()
                .filter(u -> u.getBarcode().equals(barcode))
                .findAny();
        if (checkedMedicineDevice.isPresent()) {
            log.info("isIdInDataMedicalDevice: [2] {} Element is found");
            return true;

        } else {
            log.info("isIdInDataMedicalDevice: [3] {} No Such Element");
            return false;
        }
    }

    /**
     * Getting a list of medicalDevice from a XML file
     *
     * @param FILE_PATH
     * @return Result <code><</code>List<code>></code>
     */
    @Override
    public Result<List> getMedicalDevices(String FILE_PATH) {
        log.info("getMedicalDevices: [1] {}");
        Result result = new Result();
        List<MedicalDevice> medicalDeviceList = new ArrayList<>();

        try {

            medicalDeviceList.addAll(readXmlMedicalDevice(FILE_PATH));
            result.setCode(Constant.CODE_OK);

        } catch (Exception e) {
            result.setCode(Constant.CODE_ERROR);
            result.setMessage(e.getMessage());
            log.error("getMedicalDevices: [2] {} " + e.getMessage());
            e.printStackTrace();

        }

        result.setData(medicalDeviceList);
        log.info("getMeddicines: [3] {}");
        return result;
    }

    /**
     * Adding list of medicalDevices to a XML file
     *
     * @param medicalDeviceList
     * @param FILE_PATH
     * @return Result <code><</code>List<code>></code>
     */

    @Override
    public Result<List> appendMedicalDevices(List<MedicalDevice> medicalDeviceList, String FILE_PATH) {
        log.info("appendMedicalDevices: [1] {}");
        Result result = new Result();
        try {
            medicalDeviceList = stringingDuplicateBarcodeMedicalDevice(medicalDeviceList, FILE_PATH);

            writeXmlMedicalDevice(medicalDeviceList, FILE_PATH);
            result.setCode(Constant.CODE_OK);

        } catch (Exception e) {
            log.error("appendMedicalDevices: [2] {} " + e.getMessage());
            e.printStackTrace();
        }

        result.setData(medicalDeviceList);

        log.info("appendMedicalDevices: [3] {}");
        return result;
    }

    /**
     * Delete medicalDevice object from XML file by Barcode
     *
     * @param barcode
     * @param FILE_PATH
     * @return Result <code><</code>List<code>></code>
     */
    @Override
    public Result<List> deleteMedicalDeviceByBarcode(String barcode, String FILE_PATH) {
        log.info("deleteMedicalDeviceByBarcode: [1] {}");
        DataProviderXml dataProviderXml = new DataProviderXml();
        List<MedicalDevice> medicalDeviceList = dataProviderXml.getMedicalDevices(FILE_PATH).getData();
        Result result = new Result();
        try {

            if (isIdInDataMedicalDevice(barcode, FILE_PATH)) {
                takeHistoryMedicalDevice(medicalDeviceList.stream()
                        .filter(m -> m.getBarcode().equals(barcode))
                        .findFirst()
                        .get(), this.getClass().getSimpleName(), "deleteMedicalDeviceByBarcode", "DELETED");
                medicalDeviceList.removeIf(u -> u.getBarcode().equals(barcode));
                writeXmlMedicalDevice(medicalDeviceList, FILE_PATH);
                result.setCode(Constant.CODE_OK);
                result.setMessage("OK");
            } else {
                result.setCode(Constant.CODE_ERROR);
                result.setMessage("No Such Element");
            }

        } catch (Exception e) {
            result.setCode(Constant.CODE_ERROR);
            result.setMessage(e.getMessage());
            log.error("deleteMeddicineByBarcode: [2] {} ".concat(e.getMessage()));
            e.printStackTrace();
        }

        result.setData(medicalDeviceList);
        log.info("deleteMeddicineByBarcode: [3] {}");
        return result;
    }

    /**
     * Updating medicalDevice object in the XML file if an element with this barcode is already present it is replaced otherwise a new one is added
     *
     * @param medicalDevice
     * @param FILE_PATH
     * @return Result <code><</code>List<code>></code>
     */

    @Override
    public Result<List> updateMedicalDevice(MedicalDevice medicalDevice, String FILE_PATH) {
        log.info("updateMedicalDevice: [1] {}");
        Result result = new Result();
        List<MedicalDevice> medicalDeviceList = new ArrayList<>();
        try {
            medicalDeviceList = getMedicalDevices(FILE_PATH).getData();

            if (isIdInDataMedicalDevice(medicalDevice.getBarcode(), FILE_PATH)) {
                takeHistoryMedicalDevice(medicalDeviceList.stream()
                        .filter(m -> m.getBarcode().equals(medicalDevice.getBarcode()))
                        .findFirst()
                        .get(), this.getClass().getSimpleName(), "updateMedicalDevice", "UPDATED");
                medicalDeviceList.removeIf(u -> u.getBarcode().equals(medicalDevice.getBarcode()));
            }
            medicalDeviceList.add(medicalDevice);
            appendMedicalDevices(medicalDeviceList, FILE_PATH);

            result.setCode(Constant.CODE_OK);
            result.setMessage("OK");

        } catch (Exception e) {
            log.info("updateMedicalDevice: [2] {} " + e.getMessage());
            result.setMessage(e.getMessage());
            result.setCode(Constant.CODE_ERROR);
            e.printStackTrace();
        }

        result.setData(medicalDeviceList);
        log.info("updateMedicalDevice: [3] {}");
        return result;
    }
    /**
     * Getting a medicalDevice object from a XML file by barcode
     * @param barcode
     * @param FILE_PATH
     * @return Result
     */

    @Override
    public Result getMedicalDeviceByBarcode(String barcode, String FILE_PATH) {
        log.info("getMedicalDeviceByBarcode: [1] {}");
        Result result = new Result();
        List<MedicalDevice> medicalDeviceList = getMedicalDevices(FILE_PATH).getData();
        try {

            if (isIdInDataMedicalDevice(barcode, FILE_PATH)) {
                result.setData(medicalDeviceList.stream()
                        .filter(u -> u.getBarcode().equals(barcode))
                        .findAny()
                        .get());

                result.setCode(Constant.CODE_OK);
                result.setMessage("OK");
            } else {

                result.setCode(Constant.CODE_ERROR);
                log.info("getMedicalDeviceByBarcode: [2] {} No Such Element");
                result.setMessage("No Such Element ");
            }

        } catch (Exception e) {
            result.setCode(Constant.CODE_ERROR);
            result.setMessage(e.getMessage());
            log.info("getMedicalDeviceByBarcode: [3] {} " + e.getMessage());
            e.printStackTrace();
        }

        log.info("getUserById: [4] {}");
        return result;
    }


    /**
     * Searching Meddicines objects in the categoryUnidirectional from XML file
     * @param category
     * @return List <code><</code>Meddicine<code>></code>
     */
    public List<Meddicine> searchMeddicineByCategory(String category) {
        log.info("searchPharmacyProductByCategory [1] {}");
        List<Meddicine> findedMeddicines = new ArrayList<>();
        try {
            List<Meddicine> meddicineList = getMeddicines(Constant.XML_MEDICINE).getData();
            findedMeddicines = meddicineList.stream()
                    .filter(m -> m.getCategoryOfTheMeddicine().equals(category))
                    .collect(Collectors.toList());
            log.info("searchPharmacyProductByCategory [2] {}");
        } catch (Exception e) {
            log.error("searchPharmacyProductByCategory [3] {} ".concat(e.getMessage()));
            e.printStackTrace();
        } finally {
            return findedMeddicines;
        }
    }

    /**
     * Searching PharmacyProduct objects in the categoryUnidirectional from XML file
     * @param category
     * @return List <code><</code>PharmacyProduct<code>></code>
     */

    @Override
    public List<PharmacyProduct> searchPharmacyProductByCategory(String category) {
        log.info("searchPharmacyProductByCategory [1] {}");
        List<PharmacyProduct> findedObjects = new ArrayList<>();
        try {
            List<Meddicine> findedMeddicines = searchMeddicineByCategory(category);
            if (findedMeddicines.isEmpty()) {
                log.info("searchPharmacyProductByCategory [2] {}");
                List<MedicalDevice> findedMedicalDevices = searchMedicalDeviceByCategory(category);
                if (findedMedicalDevices.isEmpty()) {
                    log.info("searchPharmacyProductByCategory [3] {} This categoryUnidirectional is empty");
                } else {
                    log.info("searchPharmacyProductByCategory [4] {}");
                    for (MedicalDevice medicalDevice: findedMedicalDevices) {
                        findedObjects.add(new PharmacyProduct(medicalDevice.getNameOfPharmacyProduct(),medicalDevice.getPrice(), medicalDevice.getBarcode(), medicalDevice.getDescription()));
                    }
                }
            }else{
                log.info("searchPharmacyProductByCategory [5] {}");
                for (Meddicine meddicine: findedMeddicines) {
                    findedObjects.add(new PharmacyProduct(meddicine.getNameOfPharmacyProduct(),meddicine.getPrice(), meddicine.getBarcode(), meddicine.getDescription()));
                }
            }
        } catch (Exception e) {
            log.error("searchPharmacyProductByCategory [6] {} ".concat(e.getMessage()));
            e.printStackTrace();
        } finally {
            log.info("searchPharmacyProductByCategory [7] {}");
            return findedObjects;
        }
    }


    /**
     * Searching MedicalDevice objects in the categoryUnidirectional from XML file
     * @param category
     * @return List <code><</code>MedicalDevice<code>></code>
     */
    public List<MedicalDevice> searchMedicalDeviceByCategory(String category) {
        log.info("searchPharmacyMedicalDeviceByCategory [1] {}");
        List<MedicalDevice> findedMedicalDevice = new ArrayList<>();
        try {
            List<MedicalDevice> medicalDeviceList = getMedicalDevices(Constant.XML_MEDICALDEVICES).getData();
            findedMedicalDevice = medicalDeviceList.stream()
                    .filter(m -> m.getCategoryOfPharmacyDevices().equals(category))
                    .collect(Collectors.toList());
            log.info("searchPharmacyMedicalDeviceByCategory [2] {}");
        } catch (Exception e) {
            log.error("searchPharmacyMedicalDeviceByCategory [3] {} ".concat(e.getMessage()));
            e.printStackTrace();
        } finally {
            return findedMedicalDevice;
        }
    }

    /**
     * Searching Meddicines objects from XML file by name
     * @param nameOfPharmacyProduct
     * @return List <code><</code>Meddicine<code>></code>
     */
    public List<Meddicine> searchMeddicineByName(String nameOfPharmacyProduct) {
        log.info("searchMeddicineByName [1] {}");
        List<Meddicine> findedMeddicines = new ArrayList<>();
        try {
            List<Meddicine> meddicineList = getMeddicines(Constant.XML_MEDICINE).getData();
            findedMeddicines = meddicineList.stream()
                    .filter(m -> m.getNameOfPharmacyProduct().equals(nameOfPharmacyProduct))
                    .collect(Collectors.toList());
            log.info("searchMeddicineByName [2] {}");
        }catch (Exception e){
            log.error("searchMeddicineByName [] {} ".concat(e.getMessage()));
            e.printStackTrace();
        }finally {
            return findedMeddicines;
        }
    }

    /**
     * Searching MedicalDevice objects from XML file by name
     * @param nameOfPharmacyProduct
     * @return List <code><</code>MedicalDevice<code>></code>
     */
    public List<MedicalDevice> searchMedicalDeviceByName(String nameOfPharmacyProduct) {
        log.info("searchMedicalDeviceByName [1] {}");
        List<MedicalDevice> findedMedicalDevices = new ArrayList<>();
        try {
            List<MedicalDevice> medicalDeviceList = getMedicalDevices(Constant.XML_MEDICALDEVICES).getData();
            findedMedicalDevices = medicalDeviceList.stream()
                    .filter(m -> m.getNameOfPharmacyProduct().equals(nameOfPharmacyProduct))
                    .collect(Collectors.toList());
            log.info("searchMedicalDeviceByName [2] {}");
        }catch (Exception e){
            log.error("searchMedicalDeviceByName [] {} ".concat(e.getMessage()));
            e.printStackTrace();
        }finally {
            return findedMedicalDevices;
        }
    }

    /**
     * Searching PharmacyProduct objects from XML file by name
     * @param nameOfPharmacyProduct
     * @return List <code><</code>PharmacyProduct<code>></code>
     */

    @Override
    public List<PharmacyProduct> searchPharmacyProductByName(String nameOfPharmacyProduct) {
        log.info("searchPharmacyProductByName [1] {}");
        List<PharmacyProduct> findedObjects = new ArrayList<>();
        try {
            List<Meddicine> findedMeddicines = searchMeddicineByName(nameOfPharmacyProduct);
            List<MedicalDevice> findedMedicalDevices = searchMedicalDeviceByName(nameOfPharmacyProduct);

            if (!findedMeddicines.isEmpty() || !findedMedicalDevices.isEmpty()){
                for (Meddicine meddicine: findedMeddicines) {
                    findedObjects.add(new PharmacyProduct(meddicine.getNameOfPharmacyProduct(),meddicine.getPrice(), meddicine.getBarcode(), meddicine.getDescription()));
                }
                log.info("searchPharmacyProductByName [2] {}");
                for (MedicalDevice medicalDevice: findedMedicalDevices) {
                    findedObjects.add(new PharmacyProduct(medicalDevice.getNameOfPharmacyProduct(),medicalDevice.getPrice(), medicalDevice.getBarcode(), medicalDevice.getDescription()));
                }
                log.info("searchPharmacyProductByName [3] {}");
            }
        } catch (Exception e) {
            log.error("searchPharmacyProductByName [4] {} ".concat(e.getMessage()));
            e.printStackTrace();
        } finally {
            log.info("searchPharmacyProductByName [5] {}");
            return findedObjects;
        }
    }

    /**
     * Getting the analogues of medicineBidirectional from XML file
     * @param meddicineName
     * @return List <code><</code>Meddicine<code>></code>
     */
    @Override
    public List<Meddicine> getTheAnaloguesOfMedicine(String meddicineName) {
        log.info("getTheAnaloguesOfMedicine [1] {}");
        List<Meddicine> analoguesList = new ArrayList<>();
        try {
            if (!searchMeddicineByName(meddicineName).isEmpty()) {
                List<Meddicine> meddicineList = searchMeddicineByName(meddicineName);
                Meddicine meddicine = meddicineList.get(0);
                meddicineList = getMeddicines(Constant.XML_MEDICINE).getData();
                analoguesList = meddicineList.stream().filter(m -> m.getActiveSubstanceOfTheMeddicine().equals(meddicine.getActiveSubstanceOfTheMeddicine()))
                        .collect(Collectors.toList());
                log.info("getTheAnaloguesOfMedicine [2] {}");
            }else {
                log.info("getTheAnaloguesOfMedicine [3] {} No such meddicine");
                analoguesList = getTheAnaloguesByBarcode(meddicineName);
            }
        }catch (Exception e){
            log.error("getTheAnaloguesOfMedicine [3] {} ".concat(e.getMessage()));
            e.printStackTrace();
        }finally {
            log.info("getTheAnaloguesOfMedicine [4] {}");
            return analoguesList;
        }
    }

    /**
     * Getting the analogues of medicineBidirectional by barcode from XML file
     * @param barcode
     * @return List <code><</code>Meddicine<code>></code>
     */
    @Override
    public List<Meddicine> getTheAnaloguesByBarcode(String barcode) {
        log.info("getTheAnaloguesByBarcode [1] {}");
        List<Meddicine> analoguesList = new ArrayList<>();
        try {
            if (getMeddicineByBarcode(barcode,Constant.XML_MEDICINE).getData() != null) {
                Meddicine meddicine = (Meddicine) getMeddicineByBarcode(barcode, Constant.XML_MEDICINE).getData();
                analoguesList = getTheAnaloguesOfMedicine(meddicine.getNameOfPharmacyProduct());
                log.info("getTheAnaloguesByBarcode [2] {}");
            }else log.info("getTheAnaloguesByBarcode [3] {} No such meddicine");
        }catch (Exception e){
            log.error("getTheAnaloguesByBarcode [4] {} ".concat(e.getMessage()));
            e.printStackTrace();
        }finally {
            return analoguesList;
        }
    }

    /**
     * Sorting List <code><</code>PharmacyProduct<code>></code> Min to Max
     * @param pharmacyProductList
     * @return List <code><</code>PharmacyProduct<code>></code>
     */

    public static List<PharmacyProduct> sortPharmacyProductMinToMax(List<PharmacyProduct> pharmacyProductList) {
        log.info("sortPharmacyProductMinToMax [1] {}");
        try{
            Collections.sort(pharmacyProductList, new PharmacyProduct.PharmacyProductComparatorMintoMax());
            log.info("sortPharmacyProductMinToMax [2] {}");
        }catch (Exception e){
            log.error("sortPharmacyProductMinToMax [3] {} ".concat(e.getMessage()));
            e.printStackTrace();
        }finally {
            return pharmacyProductList;
        }
    }

    /**
     * Sorting List <code><</code>PharmacyProduct<code>></code> Max to Min
     * @param pharmacyProductList
     * @return List <code><</code>PharmacyProduct<code>></code>
     */

    public static List<PharmacyProduct> sortPharmacyProductMaxToMin(List<PharmacyProduct> pharmacyProductList) {
        log.info("sortPharmacyProductMaxToMin [1] {}");
        try{
            Collections.sort(pharmacyProductList, new PharmacyProduct.PharmacyProductComparatorMaxToMin());
            log.info("sortPharmacyProductMaxToMin [2] {}");
        }catch (Exception e){
            log.error("sortPharmacyProductMaxToMin [3] {} ".concat(e.getMessage()));
            e.printStackTrace();
        }finally {
            return pharmacyProductList;
        }
    }
    /**
     * Sorting Pharmacy Product. If argument sort is "a" - max to min sorting if argument sort is "d" then min to max sorting
     * @param pharmacyProductList
     * @param sort
     * @return
     */

    public List<PharmacyProduct>sortPharmacyProduct(List<PharmacyProduct> pharmacyProductList, String sort){
        if (sort.equals("a")) {
            pharmacyProductList = sortPharmacyProductMaxToMin(pharmacyProductList);
        } else if (sort.equals("d")) {
            pharmacyProductList = sortPharmacyProductMinToMax(pharmacyProductList);
        }
        return pharmacyProductList;
    }

}
