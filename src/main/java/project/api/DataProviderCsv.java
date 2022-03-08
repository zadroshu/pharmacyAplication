package project.api;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import project.model.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import project.utils.Constant;
import project.utils.Result;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;


public class DataProviderCsv implements IDataProvider {

    private static Logger log = LogManager.getLogger(DataProviderCsv.class.getName());

    /**
     * Recording the state of an User object in Mongodb before its modification
     *
     * @param user
     * @param className
     * @param functionName
     * @param code
     * @return void
     */
    private static void takeHistoryUsers(User user, String className, String functionName, String code) {
        HistoryContent historyContent = new HistoryContent();
        historyContent.putUserHistory(user, className, functionName, code);
    }

    /**
     * reading users from a CSV file for Users
     *
     * @param CSV_FILE
     * @return List <code><</code>User<code>></code>
     */

    private static List<User> readCsv(String CSV_FILE) {

        List<User> userList = new ArrayList<>();
        log.info("readCsv: [1] {}");
        try {
            FileReader fr = new FileReader(CSV_FILE);
            CSVReader reader = new CSVReader(fr);

            CsvToBean<User> csvToBean = new CsvToBeanBuilder<User>(reader)
                    .withType(User.class)
                    .build();

            userList = csvToBean.parse();
        } catch (FileNotFoundException e) {
            log.error("readCsv: [2] {} ".concat(e.getMessage()));
            e.printStackTrace();
        }
        log.info("readCsv: [3] {}");
        return userList;
    }

    /**
     * Checking for duplicates and deleting them from the CSV file if available
     *
     * @param addedUserList
     * @param CSV_FILE
     * @return List <code><</code>User<code>></code>
     */

    private static List<User> stringingDuplicateId(List<User> addedUserList, String CSV_FILE) {

        log.info("stringingDuplicateId: [1] {}");
        DataProviderCsv dataProviderCsv = new DataProviderCsv();
        if (dataProviderCsv.getUsers(CSV_FILE).getData().isEmpty()) {
            return addedUserList;
        } else {
            List<User> redefinedList = dataProviderCsv.getUsers(CSV_FILE).getData();

            addedUserList.addAll(redefinedList);
            HashSet<Object> seen = new HashSet<>();
            addedUserList.removeIf(e -> !seen.add(e.getId()));
        }
        log.info("stringingDuplicateId: [2] {}");
        return addedUserList;
    }

    /**
     * Writing to a CSV file for Users
     *
     * @param userList
     * @param CSV_FILE
     * @return List <code><</code>User<code>></code>
     */
    private static List<User> writeCsv(List<User> userList, String CSV_FILE) {
        log.info("writeCsv: [1] {}");
        try {
            FileWriter writer = new FileWriter(CSV_FILE);
            CSVWriter csvWriter = new CSVWriter(writer);

            StatefulBeanToCsv<User> beanToCsv = new StatefulBeanToCsvBuilder<User>(csvWriter)
                    .withApplyQuotesToAll(false)
                    .build();
            beanToCsv.write(userList);
            csvWriter.close();
        } catch (IOException | CsvDataTypeMismatchException | CsvRequiredFieldEmptyException e) {
            log.error("writeCsv: [2] {} ".concat(e.getMessage()));
            e.printStackTrace();
        }

        log.info("writeCsv: [3] {}");
        return userList;
    }

    /**
     * Checking the presence of an element in a CSV file by ID
     *
     * @param id
     * @param CSV_FILE
     * @return boolean
     */

    private static boolean isIdInData(long id, String CSV_FILE) {
        log.info("isIdInData: [1] {}");
        DataProviderCsv dataProviderCsv = new DataProviderCsv();
        List<User> userList = dataProviderCsv.getUsers(CSV_FILE).getData();

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

    /**
     * Getting a List<code><</code>User<code>></code> from a CSV file
     *
     * @param CSV_FILE
     * @return Result <code><</code>List<code>></code>
     */

    @Override
    public Result<List> getUsers(String CSV_FILE) {
        log.info("getUsers: [1] {}");
        Result result = new Result();
        List<User> userList = new ArrayList<>();

        try {

            userList.addAll(readCsv(CSV_FILE));
            result.setCode(Constant.CODE_OK);

        } catch (Exception e) {
            result.setCode(Constant.CODE_ERROR);
            result.setMessage(e.getMessage());
            log.error("getUsers: [2] {} ".concat(e.getMessage()));
            e.printStackTrace();

        }

        result.setData(userList);
        log.info("getUsers: [3] {}");
        return result;
    }

    /**
     * Adding List<code><</code>User<code>></code> to a CSV file
     *
     * @param userList
     * @param CSV_FILE
     * @return Result <code><</code>List<code>></code>
     */
    @Override
    public Result<List> appendUsers(List<User> userList, String CSV_FILE) {

        log.info("appendUsers: [1] {}");
        Result result = new Result();
        try {
            userList = stringingDuplicateId(userList, CSV_FILE);

            writeCsv(userList, CSV_FILE);
            result.setCode(Constant.CODE_OK);

        } catch (Exception e) {
            log.error("appendUsers: [4] {} ".concat(e.getMessage()));
            e.printStackTrace();
        }

        result.setData(userList);

        log.info("appendUsers: [5] {}");
        return result;
    }

    /**
     * Deleting  of the User element from CSV file by ID
     *
     * @param id
     * @param CSV_FILE
     * @return Result <code><</code>List<code>></code>
     */
    @Override
    public Result<List> deleteUserById(long id, String CSV_FILE) {
        log.info("deleteUserById: [1] {}");
        DataProviderCsv dataProviderCsv = new DataProviderCsv();
        List<User> userList = dataProviderCsv.getUsers(CSV_FILE).getData();
        Result result = new Result();
        try {

            if (isIdInData(id, CSV_FILE)) {
                takeHistoryUsers(userList.stream()
                        .filter(u -> u.getId() == id)
                        .findFirst().get(), this.getClass().getSimpleName(), "deleteUserById", "DELETED");
                userList.removeIf(u -> u.getId() == id);
                writeCsv(userList, CSV_FILE);
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
     * Updating Users in the CSV file if an element with this ID is already present it is replaced otherwise a new one is added
     *
     * @param user
     * @param CSV_FILE
     * @return Result <code><</code>List<code>></code>
     */
    @Override
    public Result<List> updateUsers(User user, String CSV_FILE) {
        log.info("updateUsers: [1] {}");
        Result result = new Result();
        List<User> userList = new ArrayList<>();
        try {
            userList = getUsers(CSV_FILE).getData();

            if (isIdInData(user.getId(), CSV_FILE)) {
                takeHistoryUsers(userList.stream()
                        .filter(u -> u.getId() == user.getId())
                        .findFirst().get(), this.getClass().getSimpleName(), "deleteUserById", "UPDATED");
                userList.removeIf(u -> u.getId() == user.getId());
            }
            userList.add(user);
            appendUsers(userList, CSV_FILE);

            result.setCode(Constant.CODE_OK);
            result.setMessage("OK");

        } catch (Exception e) {
            log.info("updateUsers: [2] {} ".concat(e.getMessage()));
            result.setMessage(e.getMessage());
            result.setCode(Constant.CODE_ERROR);
            e.printStackTrace();
        }

        result.setData(userList);
        log.info("updateUsers: [3] {}");
        return result;
    }

    /**
     * Getting a User object from a CSV file by ID
     *
     * @param id
     * @param CSV_FILE
     * @return Result
     */
    @Override
    public Result getUserById(long id, String CSV_FILE) {
        log.info("getUserById: [1] {}");
        Result result = new Result();
        List<User> userList = getUsers(CSV_FILE).getData();
        try {

            if (isIdInData(id, CSV_FILE)) {
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
            log.info("getUserById: [3] {} ".concat(e.getMessage()));
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
     * reading orders from a CSV file for Users
     *
     * @param CSV_FILE
     * @return List <code><</code>Order<code>></code>
     */

    public static List<Order> readCsvOrder(String CSV_FILE) {

        List<Order> orderList = new ArrayList<>();
        log.info("readCsvOrder: [1] {}");
        try {
            FileReader fr = new FileReader(CSV_FILE);
            CSVReader reader = new CSVReader(fr);

            CsvToBean<Order> csvToBean = new CsvToBeanBuilder<Order>(reader)
                    .withType(Order.class)
                    .build();

            orderList = csvToBean.parse();
        } catch (FileNotFoundException e) {
            log.error("readCsvOrder: [2] {} ".concat(e.getMessage()));
            e.printStackTrace();
        }
        log.info("readCsvOrder: [3] {}");
        return orderList;
    }

    /**
     * Checking for duplicates and deleting them from the CSV file if available
     *
     * @param addedOrderList
     * @param CSV_FILE
     * @return List <code><</code>User<code>></code>
     */

    private static List<Order> stringingDuplicateIdOrder(List<Order> addedOrderList, String CSV_FILE) {

        log.info("stringingDuplicateIdOrder: [1] {}");
        DataProviderCsv dataProviderCsv = new DataProviderCsv();
        if (dataProviderCsv.getOrders(CSV_FILE).getData().isEmpty()) {
            return addedOrderList;
        } else {
            List<Order> redefinedList = dataProviderCsv.getOrders(CSV_FILE).getData();

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
     * @param CSV_FILE
     * @return List <code><</code>User<code>></code>
     */
    public static List<Order> writeCsvOrder(List<Order> orderList, String CSV_FILE) {
        log.info("writeCsvOrder: [1] {}");
        try {
            if (orderList != null && !orderList.isEmpty()) {
                FileWriter writer = new FileWriter(CSV_FILE);
                CSVWriter csvWriter = new CSVWriter(writer);

                StatefulBeanToCsv<Order> beanToCsv = new StatefulBeanToCsvBuilder<Order>(csvWriter)
                        .withApplyQuotesToAll(false)
                        .build();
                beanToCsv.write(orderList);
                csvWriter.close();
                log.info("writeCsvOrder: [2] {}");
            }
        } catch (IOException | CsvDataTypeMismatchException | CsvRequiredFieldEmptyException e) {
            log.error("writeCsvOrder: [3] {} ".concat(e.getMessage()));
            e.printStackTrace();
        }

        log.info("writeCsvOrder: [4] {}");
        return orderList;
    }

    /**
     * Checking the presence of an element in a CSV file by ID
     *
     * @param id
     * @param CSV_FILE
     * @return boolean
     */

    private static boolean isIdInDataOrder(long id, String CSV_FILE) {
        log.info("isIdInDataOrder: [1] {}");
        DataProviderCsv dataProviderCsv = new DataProviderCsv();
        List<Order> orderList = dataProviderCsv.getOrders(CSV_FILE).getData();

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

    public DataProviderCsv() {
    }

    /**
     * Getting a List<code><</code>Order<code>></code> from a CSV file
     *
     * @param CSV_FILE
     * @return Result <code><</code>List<code>></code>
     */


    public Result<List> getOrders(String CSV_FILE) {
        log.info("getOrders: [1] {}");
        Result result = new Result();
        List<Order> orderList = new ArrayList<>();

        try {

            orderList.addAll(readCsvOrder(CSV_FILE));
            result.setCode(Constant.CODE_OK);

        } catch (Exception e) {
            result.setCode(Constant.CODE_ERROR);
            result.setMessage(e.getMessage());
            log.error("getOrders: [2] {} ".concat(e.getMessage()));
            e.printStackTrace();

        }

        result.setData(orderList);
        log.info("getOrders: [3] {}");
        return result;
    }

    /**
     * Adding List<code><</code>Order<code>></code> to a CSV file
     *
     * @param orderList
     * @param CSV_FILE
     * @return Result <code><</code>List<code>></code>
     */

    public Result<List> appendOrders(List<Order> orderList, String CSV_FILE) {

        log.info("appendOrders: [1] {}");
        Result result = new Result();
        try {
            orderList = stringingDuplicateIdOrder(orderList, CSV_FILE);

            writeCsvOrder(orderList, CSV_FILE);
            result.setCode(Constant.CODE_OK);

        } catch (Exception e) {
            log.error("appendOrders: [4] {} ".concat(e.getMessage()));
            e.printStackTrace();
        }

        result.setData(orderList);

        log.info("appendOrders: [5] {}");
        return result;
    }

    /**
     * Deleting  of the Order element from CSV file by ID
     *
     * @param id
     * @param CSV_FILE
     * @return Result <code><</code>List<code>></code>
     */

    public Result<List> deleteOrderById(long id, String CSV_FILE) {
        log.info("deleteOrderById: [1] {}");
        DataProviderCsv dataProviderCsv = new DataProviderCsv();
        List<Order> orderList = dataProviderCsv.getOrders(CSV_FILE).getData();
        Result result = new Result();
        try {

            if (isIdInDataOrder(id, CSV_FILE)) {
                takeHistoryOrders(orderList.stream()
                        .filter(u -> u.getOrderId() == id)
                        .findFirst().get(), this.getClass().getSimpleName(), "deleteOrderById", "DELETED");
                orderList.removeIf(u -> u.getOrderId() == id);
                writeCsvOrder(orderList, CSV_FILE);
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
        log.info("deleteUserById: [3] {}");
        return result;
    }

    /**
     * Updating Order in the CSV file if an element with this ID is already present it is replaced otherwise a new one is added
     *
     * @param order
     * @param CSV_FILE
     * @return Result <code><</code>List<code>></code>
     */

    public Result<List> updateOrders(Order order, String CSV_FILE) {
        log.info("updateOrders: [1] {}");
        Result result = new Result();
        List<Order> orderList = new ArrayList<>();
        try {
            orderList = getOrders(CSV_FILE).getData();
            if (order != null) {
                if (isIdInDataOrder(order.getOrderId(), CSV_FILE)) {
                    takeHistoryOrders(orderList.stream()
                            .filter(u -> u.getOrderId() == order.getOrderId())
                            .findFirst().get(), this.getClass().getSimpleName(), "updateOrders", "UPDATED");
                    orderList.removeIf(u -> u.getOrderId() == order.getOrderId());
                }
                orderList.add(order);
                appendOrders(orderList, CSV_FILE);
            }
            result.setCode(Constant.CODE_OK);
            result.setMessage("OK");

        } catch (Exception e) {
            log.info("updateOrders: [2] {} ".concat(e.getMessage()));
            result.setMessage(e.getMessage());
            result.setCode(Constant.CODE_ERROR);
            e.printStackTrace();
        }

        result.setData(orderList);
        log.info("updateOrders: [3] {}");
        return result;
    }

    /**
     * Getting a Order object from a CSV file by ID
     *
     * @param id
     * @param CSV_FILE
     * @return Result
     */

    public Result getOrderById(long id, String CSV_FILE) {
        log.info("getOrderById: [1] {}");
        Result result = new Result();
        List<Order> orderList = getOrders(CSV_FILE).getData();
        try {

            if (isIdInDataOrder(id, CSV_FILE)) {
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
            log.info("getOrderById: [3] {} ".concat(e.getMessage()));
            e.printStackTrace();
        }

        log.info("getOrderById: [4] {}");
        return result;
    }

    /**
     * Creating order for user and updating order and user CSV files
     *
     * @param userId
     * @param pharmacyProductName
     * @return Order
     */
    public Order createOrder(long userId, String pharmacyProductName) {
        log.info("createOrder [1] {}");
        Order order = new Order();
        try {
            if (getUserById(userId, Constant.CSV_USER).getData() != null) {
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
     * adding pharmacy product to orders CSV file
     * @param order
     * @param pharmacyProductName
     */
    public void changePharmacyProduct(Order order, String pharmacyProductName){
        log.info("pharmacyProductName [1] {}");
        try {
            if (!searchPharmacyProductByName(pharmacyProductName).isEmpty()) {
                order.setPharmacyProductBarcode(searchPharmacyProductByName(pharmacyProductName).get(0).getBarcode());
                updateOrders(order, Constant.CSV_ORDERS);
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
    private static void takeHistoryMeddicine(Meddicine meddicine, String className, String functionName, String code) {
        HistoryContent historyContent = new HistoryContent();
        historyContent.putMeddicineHistory(meddicine, className, functionName, code);
    }

    /**
     * Writing to a CSV file for Meddicines
     *
     * @param meddicineList
     * @param CSV_FILE
     * @return Result <code><</code>List<code>></code>
     */
    public static List<Meddicine> writeCsvMedicine(List<Meddicine> meddicineList, String CSV_FILE) {
        log.info("writeCsvMedicine: [1] {}");
        try {
            FileWriter writer = new FileWriter(CSV_FILE);
            CSVWriter csvWriter = new CSVWriter(writer);

            StatefulBeanToCsv<Meddicine> beanToCsv = new StatefulBeanToCsvBuilder<Meddicine>(csvWriter)
                    .withApplyQuotesToAll(false)
                    .build();
            beanToCsv.write(meddicineList);
            csvWriter.close();
        } catch (IOException | CsvDataTypeMismatchException | CsvRequiredFieldEmptyException e) {
            log.error("writeCsvMedicine: [2] {} ".concat(e.getMessage()));
            e.printStackTrace();
        }

        log.info("writeCsvMedicine: [3] {}");
        return meddicineList;
    }

    /**
     * reading Medicines from a CSV file
     *
     * @param CSV_FILE
     * @return List <code><</code>Meddicine<code>></code>
     */

    public static List<Meddicine> readCsvMedicine(String CSV_FILE) {

        List<Meddicine> medicineList = new ArrayList<>();

        log.info("readCsvMedicine: [1] {}");
        try {
            FileReader fr = new FileReader(CSV_FILE);
            CSVReader reader = new CSVReader(fr);

            CsvToBean<Meddicine> csvToBean = new CsvToBeanBuilder<Meddicine>(reader)
                    .withType(Meddicine.class)
                    .build();

            medicineList = csvToBean.parse();
        } catch (FileNotFoundException e) {
            log.error("readCsvMedicine: [2] {} ".concat(e.getMessage()));
            e.printStackTrace();
        }
        log.info("readCsvMedicine: [3] {}");
        return medicineList;
    }

    /**
     * Checking for duplicates and deleting them from the CSV file if available
     *
     * @param addedMeddicineList
     * @param CSV_FILE
     * @return List <code><</code>Meddicine<code>></code>
     */
    private static List<Meddicine> stringingDuplicateBarcode(List<Meddicine> addedMeddicineList, String CSV_FILE) {

        log.info("stringingDuplicateId: [1] {}");
        DataProviderCsv dataProviderCsv = new DataProviderCsv();

        if (dataProviderCsv.getMeddicines(CSV_FILE).getData().isEmpty()) {
            return addedMeddicineList;
        } else {
            List<Meddicine> redefinedList = dataProviderCsv.getMeddicines(CSV_FILE).getData();

            addedMeddicineList.addAll(redefinedList);
            HashSet<Object> seen = new HashSet<>();
            addedMeddicineList.removeIf(e -> !seen.add(e.getBarcode()));
        }
        log.info("stringingDuplicateId: [2] {}");
        return addedMeddicineList;
    }

    /**
     * Checking the presence of an element in a CSV Medicine file by Barcode
     *
     * @param barcode
     * @param CSV_FILE
     * @return boolean
     */
    private static boolean isIdInDataMedicine(String barcode, String CSV_FILE) {
        log.info("isIdInDataMedicine: [1] {}");
        DataProviderCsv dataProviderCsv = new DataProviderCsv();
        List<Meddicine> meddicineList = dataProviderCsv.getMeddicines(CSV_FILE).getData();

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
     * Getting a list of meddicines from a CSV file
     *
     * @param CSV_FILE
     * @return Result <code><</code>List<code>></code>
     */
    @Override
    public Result<List> getMeddicines(String CSV_FILE) {
        log.info("getMeddicines: [1] {}");
        Result result = new Result();
        List<Meddicine> meddicineList = new ArrayList<>();

        try {
            meddicineList.addAll(readCsvMedicine(CSV_FILE));
            result.setCode(Constant.CODE_OK);

        } catch (Exception e) {
            result.setCode(Constant.CODE_ERROR);
            result.setMessage(e.getMessage());
            log.error("getMeddicines: [2] {} ".concat(e.getMessage()));
            e.printStackTrace();

        }

        result.setData(meddicineList);
        log.info("getMeddicines: [3] {}");
        return result;
    }

    /**
     * Adding list of meddicines to a Meddicinenes CSV file
     *
     * @param meddicinesList
     * @param CSV_FILE
     * @return Result <code><</code>List<code>></code>
     */
    @Override
    public Result<List> appendMeddicines(List<Meddicine> meddicinesList, String CSV_FILE) {
        log.info("appendUsers: [1] {}");
        Result result = new Result();
        try {
            meddicinesList = stringingDuplicateBarcode(meddicinesList, CSV_FILE);

            writeCsvMedicine(meddicinesList, CSV_FILE);
            result.setCode(Constant.CODE_OK);

        } catch (Exception e) {
            log.error("appendUsers: [4] {} ".concat(e.getMessage()));
            e.printStackTrace();
        }

        result.setData(meddicinesList);

        log.info("appendUsers: [5] {}");
        return result;
    }

    /**
     * Delete medicine object from medicine CSV file by Barcode
     *
     * @param barcode
     * @param CSV_FILE
     * @return Result <code><</code>List<code>></code>
     */

    @Override
    public Result<List> deleteMeddicineByBarcode(String barcode, String CSV_FILE) {
        log.info("deleteMeddicineByBarcode: [1] {}");
        DataProviderCsv dataProviderCsv = new DataProviderCsv();
        List<Meddicine> meddicineList = dataProviderCsv.getMeddicines(CSV_FILE).getData();
        Result result = new Result();
        try {

            if (isIdInDataMedicine(barcode, CSV_FILE)) {
                takeHistoryMeddicine(meddicineList.stream()
                        .filter(m -> m.getBarcode().equals(barcode))
                        .findFirst()
                        .get(), this.getClass().getSimpleName(), "deleteMeddicineByBarcode", "DELETED");
                meddicineList.removeIf(u -> u.getBarcode().equals(barcode));
                writeCsvMedicine(meddicineList, CSV_FILE);
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

        result.setData(meddicineList);
        log.info("deleteMeddicineByBarcode: [3] {}");
        return result;
    }

    /**
     * Updating medicine object in the CSV file if an element with this Barcode is already present it is replaced otherwise a new one is added
     *
     * @param meddicine
     * @param CSV_FILE
     * @return Result <code><</code>List<code>></code>
     */

    @Override
    public Result<List> updateMeddicine(Meddicine meddicine, String CSV_FILE) {
        log.info("updateMeddicine: [1] {}");
        Result result = new Result();
        List<Meddicine> meddicineList = new ArrayList<>();
        try {
            meddicineList = getMeddicines(CSV_FILE).getData();

            if (isIdInDataMedicine(meddicine.getBarcode(), CSV_FILE)) {
                takeHistoryMeddicine(meddicineList.stream()
                        .filter(m -> m.getBarcode().equals(meddicine.getBarcode()))
                        .findFirst()
                        .get(), this.getClass().getSimpleName(), "updateMeddicine", "UPDATED");
                meddicineList.removeIf(u -> u.getBarcode().equals(meddicine.getBarcode()));
            }
            meddicineList.add(meddicine);
            appendMeddicines(meddicineList, CSV_FILE);

            result.setCode(Constant.CODE_OK);
            result.setMessage("OK");

        } catch (Exception e) {
            log.info("updateMeddicine: [2] {} ".concat(e.getMessage()));
            result.setMessage(e.getMessage());
            result.setCode(Constant.CODE_ERROR);
            e.printStackTrace();
        }

        result.setData(meddicineList);
        log.info("updateMeddicine: [3] {}");
        return result;
    }

    /**
     * Getting a medicine object from a CSV file by barcode
     *
     * @param barcode
     * @param CSV_FILE
     * @return Result
     */

    @Override
    public Result getMeddicineByBarcode(String barcode, String CSV_FILE) {
        log.info("getMeddicineByBarcode: [1] {}");
        Result result = new Result();
        List<Meddicine> meddicineList = getMeddicines(CSV_FILE).getData();
        try {

            if (isIdInDataMedicine(barcode, CSV_FILE)) {
                result.setData(meddicineList.stream()
                        .filter(u -> u.getBarcode().equals(barcode))
                        .findAny()
                        .get());

                result.setCode(Constant.CODE_OK);
                result.setMessage("OK");
            } else {

                result.setCode(Constant.CODE_ERROR);
                log.info("getMeddicineByBarcode: [2] {}");
                result.setMessage("No Such Element id = " + barcode);
            }

        } catch (Exception e) {
            result.setCode(Constant.CODE_ERROR);
            result.setMessage(e.getMessage());
            log.info("getMeddicineByBarcode: [3] {} ".concat(e.getMessage()));
            e.printStackTrace();
        }

        log.info("getMeddicineByBarcode: [4] {}");
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

    private static void takeHistoryMedicalDevice(MedicalDevice medicalDevice, String className, String functionName, String code) {
        HistoryContent historyContent = new HistoryContent();
        historyContent.putMedicalDeviceHistory(medicalDevice, className, functionName, code);
    }

    /**
     * Writing to a MedicalDevice object to CSV file
     *
     * @param medicalDeviceList
     * @param CSV_FILE
     * @return List <code><</code>MedicalDevice<code>></code>
     */

    public static List<MedicalDevice> writeCsvMedicalDevice(List<MedicalDevice> medicalDeviceList, String CSV_FILE) {
        log.info("writeCsvMedicine: [1] {}");
        try {
            FileWriter writer = new FileWriter(CSV_FILE);
            CSVWriter csvWriter = new CSVWriter(writer);

            StatefulBeanToCsv<MedicalDevice> beanToCsv = new StatefulBeanToCsvBuilder<MedicalDevice>(csvWriter)
                    .withApplyQuotesToAll(false)
                    .build();
            beanToCsv.write(medicalDeviceList);
            csvWriter.close();
        } catch (IOException | CsvDataTypeMismatchException | CsvRequiredFieldEmptyException e) {
            log.error("writeCsvMedicine: [2] {} ".concat(e.getMessage()));
            e.printStackTrace();
        }

        log.info("writeCsvMedicine: [3] {}");
        return medicalDeviceList;
    }

    /**
     * Reading MedicalDevice from a CSV file
     *
     * @param CSV_FILE
     * @return List <code><</code>MedicalDevice<code>></code>
     */
    public static List<MedicalDevice> readCsvMedicalDevice(String CSV_FILE) {

        List<MedicalDevice> medicalDeviceList = new ArrayList<>();

        log.info("readCsvMedicalDevice: [1] {}");
        try {
            FileReader fr = new FileReader(CSV_FILE);
            CSVReader reader = new CSVReader(fr);

            CsvToBean<MedicalDevice> csvToBean = new CsvToBeanBuilder<MedicalDevice>(reader)
                    .withType(MedicalDevice.class)
                    .build();

            medicalDeviceList = csvToBean.parse();
        } catch (FileNotFoundException e) {
            log.error("readCsvMedicalDevice: [2] {} ".concat(e.getMessage()));
            e.printStackTrace();
        }
        log.info("readCsvMedicalDevice: [3] {}");
        return medicalDeviceList;
    }

    /**
     * Checking medicalDevice object for duplicates and deleting them from the  CSV file if available
     *
     * @param addedMedicalDeviceList
     * @param CSV_FILE
     * @return List <code><</code>MedicalDevice<code>></code>
     */
    private static List<MedicalDevice> stringingDuplicateBarcodeDevice(List<MedicalDevice> addedMedicalDeviceList, String CSV_FILE) {

        log.info("stringingDuplicateBarcodeDevice: [1] {}");
        DataProviderCsv dataProviderCsv = new DataProviderCsv();

        if (dataProviderCsv.getMedicalDevices(CSV_FILE).getData().isEmpty()) {
            return addedMedicalDeviceList;
        } else {
            List<MedicalDevice> redefinedList = dataProviderCsv.getMedicalDevices(CSV_FILE).getData();

            addedMedicalDeviceList.addAll(redefinedList);
            HashSet<Object> seen = new HashSet<>();
            addedMedicalDeviceList.removeIf(e -> !seen.add(e.getBarcode()));
        }
        log.info("stringingDuplicateBarcodeDevice: [2] {}");
        return addedMedicalDeviceList;
    }

    /**
     * Checking the presence of an medicalDevice object in a CSV  file by Barcode
     *
     * @param barcode
     * @param CSV_FILE
     * @return boolean
     */

    private static boolean isIdInDataMedicalDevice(String barcode, String CSV_FILE) {
        log.info("isIdInDataMedicalDevice: [1] {}");
        DataProviderCsv dataProviderCsv = new DataProviderCsv();
        List<MedicalDevice> meddicineList = dataProviderCsv.getMedicalDevices(CSV_FILE).getData();

        Optional<MedicalDevice> checkedMedicalDevice = meddicineList.stream()
                .filter(u -> u.getBarcode().equals(barcode))
                .findAny();
        if (checkedMedicalDevice.isPresent()) {
            log.info("isIdInDataMedicalDevice: [2] {} Element is found");
            return true;

        } else {
            log.info("isIdInDataMedicalDevice: [3] {} No Such Element");
            return false;
        }
    }

    /**
     * Getting a list of medicalDevice from a CSV file
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

            medicalDeviceList.addAll(readCsvMedicalDevice(FILE_PATH));
            result.setCode(Constant.CODE_OK);

        } catch (Exception e) {
            result.setCode(Constant.CODE_ERROR);
            result.setMessage(e.getMessage());
            log.error("getMedicalDevices: [2] {} ".concat(e.getMessage()));
            e.printStackTrace();

        }

        result.setData(medicalDeviceList);
        log.info("getMedicalDevices: [3] {}");
        return result;
    }

    /**
     * Adding list of medicalDevices to a CSV file
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
            medicalDeviceList = stringingDuplicateBarcodeDevice(medicalDeviceList, FILE_PATH);

            writeCsvMedicalDevice(medicalDeviceList, FILE_PATH);
            result.setCode(Constant.CODE_OK);

        } catch (Exception e) {
            log.error("appendMedicalDevices: [2] {} ".concat(e.getMessage()));
            e.printStackTrace();
        }

        result.setData(medicalDeviceList);

        log.info("appendMedicalDevices: [3] {}");
        return result;
    }

    /**
     * Delete medicalDevice object from CSV file by Barcode
     *
     * @param barcode
     * @param FILE_PATH
     * @return Result <code><</code>List<code>></code>
     */

    @Override
    public Result<List> deleteMedicalDeviceByBarcode(String barcode, String FILE_PATH) {
        log.info("deleteMedicalDeviceByBarcode: [1] {}");
        DataProviderCsv dataProviderCsv = new DataProviderCsv();
        List<MedicalDevice> medicalDeviceList = dataProviderCsv.getMedicalDevices(FILE_PATH).getData();
        Result result = new Result();
        try {

            if (isIdInDataMedicalDevice(barcode, FILE_PATH)) {
                takeHistoryMedicalDevice(medicalDeviceList.stream()
                        .filter(m -> m.getBarcode().equals(barcode))
                        .findFirst()
                        .get(), this.getClass().getSimpleName(), "deleteMedicalDeviceByBarcode", "DELETED");
                medicalDeviceList.removeIf(u -> u.getBarcode().equals(barcode));
                writeCsvMedicalDevice(medicalDeviceList, FILE_PATH);
                result.setCode(Constant.CODE_OK);
                result.setMessage("OK");
            } else {
                result.setCode(Constant.CODE_ERROR);
                result.setMessage("No Such Element");
            }

        } catch (Exception e) {
            result.setCode(Constant.CODE_ERROR);
            result.setMessage(e.getMessage());
            log.error("deleteMedicalDeviceByBarcode: [2] {} ".concat(e.getMessage()));
            e.printStackTrace();
        }

        result.setData(medicalDeviceList);
        log.info("deleteMedicalDeviceByBarcode: [3] {}");
        return result;
    }

    /**
     * Updating medicalDevice object in the CSV file if an element with this barcode is already present it is replaced otherwise a new one is added
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
            log.info("updateMedicalDevice: [2] {} ".concat(e.getMessage()));
            result.setMessage(e.getMessage());
            result.setCode(Constant.CODE_ERROR);
            e.printStackTrace();
        }

        result.setData(medicalDeviceList);
        log.info("updateMedicalDevice: [3] {}");
        return result;
    }

    /**
     * Getting a medicalDevice object from a CSV file by barcode
     *
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
                log.info("getMedicalDeviceByBarcode: [2] {}");
                result.setMessage("No Such Element id = " + barcode);
            }

        } catch (Exception e) {
            result.setCode(Constant.CODE_ERROR);
            result.setMessage(e.getMessage());
            log.info("getMedicalDeviceByBarcode: [3] {} ".concat(e.getMessage()));
            e.printStackTrace();
        }

        log.info("getMedicalDeviceByBarcode: [4] {}");
        return result;
    }


    /**
     * Searching Meddicines objects in the category from CSV file
     *
     * @param category
     * @return List <code><</code>Meddicine<code>></code>
     */
    public List<Meddicine> searchMeddicineByCategory(String category) {
        log.info("searchPharmacyProductByCategory [1] {}");
        List<Meddicine> findedMeddicines = new ArrayList<>();
        try {
            List<Meddicine> meddicineList = getMeddicines(Constant.CSV_MEDICINE).getData();
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
     * Searching PharmacyProduct objects in the category from CSV file
     *
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
                    log.info("searchPharmacyProductByCategory [3] {} This category is empty");
                } else {
                    log.info("searchPharmacyProductByCategory [4] {}");
                    for (MedicalDevice medicalDevice : findedMedicalDevices) {
                        findedObjects.add(new PharmacyProduct(medicalDevice.getNameOfPharmacyProduct(), medicalDevice.getPrice(), medicalDevice.getBarcode(), medicalDevice.getDescription()));
                    }
                }
            } else {
                log.info("searchPharmacyProductByCategory [5] {}");
                for (Meddicine meddicine : findedMeddicines) {
                    findedObjects.add(new PharmacyProduct(meddicine.getNameOfPharmacyProduct(), meddicine.getPrice(), meddicine.getBarcode(), meddicine.getDescription()));
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
     * Searching MedicalDevice objects in the category from CSV file
     *
     * @param category
     * @return List <code><</code>MedicalDevice<code>></code>
     */
    public List<MedicalDevice> searchMedicalDeviceByCategory(String category) {
        log.info("searchPharmacyMedicalDeviceByCategory [1] {}");
        List<MedicalDevice> findedMedicalDevice = new ArrayList<>();
        try {
            List<MedicalDevice> medicalDeviceList = getMedicalDevices(Constant.CSV_MEDICALDEVICES).getData();
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
     * Searching Meddicines objects from CSV file by name
     *
     * @param nameOfPharmacyProduct
     * @return List <code><</code>Meddicine<code>></code>
     */
    public List<Meddicine> searchMeddicineByName(String nameOfPharmacyProduct) {
        log.info("searchMeddicineByName [1] {}");
        List<Meddicine> findedMeddicines = new ArrayList<>();
        try {
            List<Meddicine> meddicineList = getMeddicines(Constant.CSV_MEDICINE).getData();
            findedMeddicines = meddicineList.stream()
                    .filter(m -> m.getNameOfPharmacyProduct().equals(nameOfPharmacyProduct))
                    .collect(Collectors.toList());
            log.info("searchMeddicineByName [2] {}");
        } catch (Exception e) {
            log.error("searchMeddicineByName [] {} ".concat(e.getMessage()));
            e.printStackTrace();
        } finally {
            return findedMeddicines;
        }
    }

    /**
     * Searching MedicalDevice objects from CSV file by name
     *
     * @param nameOfPharmacyProduct
     * @return List <code><</code>MedicalDevice<code>></code>
     */
    public List<MedicalDevice> searchMedicalDeviceByName(String nameOfPharmacyProduct) {
        log.info("searchMedicalDeviceByName [1] {}");
        List<MedicalDevice> findedMedicalDevices = new ArrayList<>();
        try {
            List<MedicalDevice> medicalDeviceList = getMedicalDevices(Constant.CSV_MEDICALDEVICES).getData();
            findedMedicalDevices = medicalDeviceList.stream()
                    .filter(m -> m.getNameOfPharmacyProduct().equals(nameOfPharmacyProduct))
                    .collect(Collectors.toList());
            log.info("searchMedicalDeviceByName [2] {}");
        } catch (Exception e) {
            log.error("searchMedicalDeviceByName [] {} ".concat(e.getMessage()));
            e.printStackTrace();
        } finally {
            return findedMedicalDevices;
        }
    }

    /**
     * Searching PharmacyProduct objects from CSV file by name
     *
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

            if (!findedMeddicines.isEmpty() || !findedMedicalDevices.isEmpty()) {
                for (Meddicine meddicine : findedMeddicines) {
                    findedObjects.add(new PharmacyProduct(meddicine.getNameOfPharmacyProduct(), meddicine.getPrice(), meddicine.getBarcode(), meddicine.getDescription()));
                }
                log.info("searchPharmacyProductByName [2] {}");
                for (MedicalDevice medicalDevice : findedMedicalDevices) {
                    findedObjects.add(new PharmacyProduct(medicalDevice.getNameOfPharmacyProduct(), medicalDevice.getPrice(), medicalDevice.getBarcode(), medicalDevice.getDescription()));
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
     * Getting the analogues of medicine from CSV file
     *
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
                meddicineList = getMeddicines(Constant.CSV_MEDICINE).getData();
                analoguesList = meddicineList.stream().filter(m -> m.getActiveSubstanceOfTheMeddicine().equals(meddicine.getActiveSubstanceOfTheMeddicine()))
                        .collect(Collectors.toList());
                log.info("getTheAnaloguesOfMedicine [2] {}");
            }else {
                log.info("getTheAnaloguesOfMedicine [3] {} No such meddicine");
                analoguesList = getTheAnaloguesByBarcode(meddicineName);
            }
        } catch (Exception e) {
            log.error("getTheAnaloguesOfMedicine [4] {} ".concat(e.getMessage()));
            e.printStackTrace();
        } finally {
            log.info("getTheAnaloguesOfMedicine [5] {}");
            return analoguesList;
        }
    }

    /**
     * Getting the analogues of medicine by barcode from CSV file
     *
     * @param barcode
     * @return List <code><</code>Meddicine<code>></code>
     */
    @Override
    public List<Meddicine> getTheAnaloguesByBarcode(String barcode) {
        log.info("getTheAnaloguesByBarcode [1] {}");
        List<Meddicine> analoguesList = new ArrayList<>();
        try {
            if (getMeddicineByBarcode(barcode,Constant.CSV_MEDICINE).getData() != null) {
                Meddicine meddicine = (Meddicine) getMeddicineByBarcode(barcode, Constant.CSV_MEDICINE).getData();
                analoguesList = getTheAnaloguesOfMedicine(meddicine.getNameOfPharmacyProduct());
                log.info("getTheAnaloguesByBarcode [2] {}");
            }else log.info("getTheAnaloguesByBarcode [3] {} No such meddicine");
        } catch (Exception e) {
            log.error("getTheAnaloguesByBarcode [4] {} ".concat(e.getMessage()));
            e.printStackTrace();
        } finally {
            return analoguesList;
        }
    }

    /**
     * Sorting List <code><</code>PharmacyProduct<code>></code> Min to Max
     *
     * @param pharmacyProductList
     * @return List <code><</code>PharmacyProduct<code>></code>
     */

    public static List<PharmacyProduct> sortPharmacyProductMinToMax(List<PharmacyProduct> pharmacyProductList) {
        log.info("sortPharmacyProductMinToMax [1] {}");
        try {
            Collections.sort(pharmacyProductList, new PharmacyProduct.PharmacyProductComparatorMintoMax());
            log.info("sortPharmacyProductMinToMax [2] {}");
        } catch (Exception e) {
            log.error("sortPharmacyProductMinToMax [3] {} ".concat(e.getMessage()));
            e.printStackTrace();
        } finally {
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
    /**
     * Sorting List <code><</code>PharmacyProduct<code>></code> Max to Min
     *
     * @param pharmacyProductList
     * @return List <code><</code>PharmacyProduct<code>></code>
     */
        public static List<PharmacyProduct> sortPharmacyProductMaxToMin(List<PharmacyProduct> pharmacyProductList) {
        log.info("sortPharmacyProductMaxToMin [1] {}");
        try {
            Collections.sort(pharmacyProductList, new PharmacyProduct.PharmacyProductComparatorMaxToMin());
            log.info("sortPharmacyProductMaxToMin [2] {}");
        } catch (Exception e) {
            log.error("sortPharmacyProductMaxToMin [3] {} ".concat(e.getMessage()));
            e.printStackTrace();
        } finally {
            return pharmacyProductList;
        }
    }
}

