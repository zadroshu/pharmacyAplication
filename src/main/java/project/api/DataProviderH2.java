package project.api;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import project.model.*;
import project.utils.Constant;
import project.utils.Result;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.*;
import java.util.stream.Collectors;


public class DataProviderH2 implements IDataProvider {


    private static final Logger log = LogManager.getLogger(DataProviderH2.class.getName());

    /**
     * Recording the state of an object in Mongodb before its modification
     *
     * @param className
     * @param functionName
     * @param code
     * @param id
     * @return boolean
     */

    private static boolean takeHistoryUsers(String className, String functionName, String code, long id) {
        try {
            log.info("takeHistoryUsers [1] {}");
            DataProviderH2 dataProviderH2 = new DataProviderH2();
            if (dataProviderH2.isInDataUser(id, Constant.URL_H2)) {
                List<User> userList = dataProviderH2.getUsers(Constant.URL_H2).getData();
                User user = userList.stream()
                        .filter(u -> u.getId() == id)
                        .findAny()
                        .get();
                HistoryContent historyContent = new HistoryContent();
                historyContent.putUserHistory(user, className, functionName, code);
            }
        } catch (Exception e) {
            log.error("takeHistoryUsers: [2] {} ".concat(e.getMessage()));
            e.printStackTrace();
        }
        return true;
    }

    /**
     * Сhecking for the existence of user Id
     *
     * @param id
     * @param FILE_PATH
     * @return boolean
     */
    public static boolean isInDataUser(long id, String FILE_PATH) {
        try {
            log.info("isInDataUser [1] {}");
            DataProviderH2 dataProviderH2 = new DataProviderH2();
            List<User> userList = dataProviderH2.getUsers(Constant.URL_H2).getData();
            Optional<User> orderOp = userList.stream()
                    .filter(u -> u.getId() == id)
                    .findAny();
            if (!orderOp.isPresent()) {
                log.info("No such element");
                return false;
            }
        } catch (Exception e) {
            log.error("isInDataUser: [2] {} ".concat(e.getMessage()));
            e.printStackTrace();
        }
        return true;
    }


    /**
     * Connection to H2 table
     *
     * @param url
     * @param user
     * @param password
     * @param driver
     * @return Connection
     */

    public static Connection connection(String url, String user, String password, String driver) {
        log.info("connection: [1] {}");
        Connection connect = null;
        try {
            Class.forName(driver);
            connect = DriverManager
                    .getConnection(url, user, password);
            log.info("connection: [2] {}");
        } catch (Exception e) {
            log.error("connection: [3] {} ".concat(e.getMessage()));
            e.printStackTrace();
        } finally {
            log.info("connection: [4] {}");
            return connect;
        }
    }


    /**
     * Close connection to H2 table
     *
     * @param connection
     * @param statement
     */

    public static void closeConnection(Connection connection, Statement statement) {
        log.info("closeConnection: [1] {}");
        try {
            statement.close();
            connection.close();
        } catch (Exception e) {
            log.error("closeConnection: [2] {} ".concat(e.getMessage()));
            e.printStackTrace();
        }
    }

    /**
     * check for duplicate ID and if there are any, replace them in the H2 USER table
     *
     * @param addedUserList
     * @return
     */

    public static boolean stringingDuplicateIdDeleteUser(List<User> addedUserList) {
        log.info("stringingDuplicateIdDeleteUser: [1] {}");
        try {
            Connection connection = connection(Constant.URL_H2, Constant.USERNAME_H2, Constant.PASSWORD_H2, Constant.DRIVER_H2);
            Statement statement = connection.createStatement();
            for (User user : addedUserList) {
                String sql = Constant.DELETE_FROM_USER_BY_ID + user.getId();
                statement.executeUpdate(sql);
            }
            log.info("stringingDuplicateIdDeleteUser: [2] {}");
            closeConnection(connection, statement);
        } catch (Exception e) {
            log.error("stringingDuplicateIdDeleteUser: [3] {} ".concat(e.getMessage()));
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * Write Users to H2 USER table
     *
     * @param userList
     * @return List <code><</code>User<code>></code>
     */

    public static List<User> writeToH2User(List<User> userList) {
        log.info("writeToH2User: [1] {}");
        try {
            Connection connection = connection(Constant.URL_H2, Constant.USERNAME_H2, Constant.PASSWORD_H2, Constant.DRIVER_H2);
            Statement statement = connection.createStatement();
            for (User user : userList) {
                String sql = Constant.INSERT_INTO_USER + "VALUES (" + user.getId() + ", " + '\'' + user.getLogin() + '\'' + ", " + '\'' + user.getPassword() + '\'' + ")";
                statement.executeUpdate(sql);
            }
            log.info("writeToH2User: [2] {}");
            closeConnection(connection, statement);
        } catch (Exception e) {
            log.error("writeToH2User: [3] {} ".concat(e.getMessage()));
            e.printStackTrace();
        } finally {
            log.info("writeToH2User: [4] {}");
            return userList;
        }
    }


    /**
     * Converting data to User object
     *
     * @param rs
     * @return List <code><</code>User<code>></code>
     */

    public static List<User> h2ToUserList(ResultSet rs) {
        log.info("h2ToUserListUser: [1] {}");
        List<User> userList = new ArrayList<>();
        try {
            while (rs.next()) {
                userList.add(new User(rs.getInt("ID"), rs.getString("LOGIN"), rs.getString("PASSWORD")));
            }
            log.info("h2ToUserListUser: [2] {}");
        } catch (Exception e) {
            log.error("h2ToUserList [3] {} ".concat(e.getMessage()));
            e.printStackTrace();
        } finally {
            log.info("h2ToUserListUser: [4] {}");
            return userList;
        }
    }


    /**
     * Read Users from H2 USER table
     *
     * @return List <code><</code>User<code>></code>
     */

    public static List<User> readUsersFromH2() {
        log.info("readFromH2: [1] {}");
        ResultSet rs = null;
        List<User> userList = new ArrayList<>();
        try {
            Connection connection = connection(Constant.URL_H2, Constant.USERNAME_H2, Constant.PASSWORD_H2, Constant.DRIVER_H2);
            Statement statement = connection.createStatement();
            String sql = Constant.SELECT_ALL_FROM_USER;
            rs = statement.executeQuery(sql);
            userList = h2ToUserList(rs);
            closeConnection(connection, statement);
            log.info("readFromH2: [2] {}");
        } catch (Exception e) {
            log.error("readFromH2: [3] {} ".concat(e.getMessage()));
            e.printStackTrace();
        } finally {
            log.info("readFromH2: [4] {}");
            return userList;
        }
    }


    /**
     * Get a List<User> of all users from the H2 user table
     *
     * @param FILE_PATH
     * @return Result <code><</code>List<code>></code>
     * @throws IOException
     */

    @Override
    public Result<List> getUsers(String FILE_PATH) {
        log.info("getUsers: [1] {}");
        Result result = new Result();
        List<User> userList = new ArrayList<>();

        try {
            userList = readUsersFromH2();
            result.setCode(Constant.CODE_OK);
            result.setMessage("OK");
            log.info("getUsers: [2] {}");
        } catch (Exception e) {
            result.setCode(Constant.CODE_ERROR);
            result.setMessage(e.getMessage());
            log.error("getUsers: [3] {} ".concat(e.getMessage()));
            e.printStackTrace();

        } finally {
            result.setData(userList);
            log.info("getUsers: [4] {}");
            return result;
        }
    }

    /*
           Adding List<User> users to the H2 USER table
    */

    /**
     * Adding List<User> users to the H2 USER table
     *
     * @param userList
     * @param FILE_PATH
     * @return Result <code><</code>List<code>></code>
     */

    @Override
    public Result<List> appendUsers(List<User> userList, String FILE_PATH) {
        log.info("appendUsers: [1] {}");
        Result result = new Result();
        try {
            if (stringingDuplicateIdDeleteUser(userList)) {
                writeToH2User(userList);
                result.setCode(Constant.CODE_OK);
                result.setMessage("OK");
                log.info("appendUsers: [2] {}");
            } else {
                log.error("appendUsers: [3] {} Duplicates didn't delete");
            }

        } catch (Exception e) {
            log.error("appendUsers: [4] {} ".concat(e.getMessage()));
            result.setCode(Constant.CODE_ERROR);
            result.setMessage(e.getMessage());
            e.printStackTrace();
        } finally {
            result.setData(userList);
            log.info("appendUsers: [5] {}");
            return result;
        }
    }


    /**
     * Removing the element by ID from the H2 USER table
     *
     * @param id
     * @param FILE_PATH
     * @return Result <code><</code>List<code>></code>
     */

    @Override
    public Result<List> deleteUserById(long id, String FILE_PATH) {
        log.info("deleteUserById: [1] {}");
        Result result = new Result();
        try {
            Connection connection = connection(Constant.URL_H2, Constant.USERNAME_H2, Constant.PASSWORD_H2, Constant.DRIVER_H2);
            Statement statement = connection.createStatement();
            takeHistoryUsers(this.getClass().getSimpleName(), "deleteUserById", "DElETED", id);
            String sql = Constant.DELETE_FROM_USER_BY_ID + id;
            statement.executeUpdate(sql);
            closeConnection(connection, statement);
            result.setCode(Constant.CODE_OK);
            result.setMessage("OK");
        } catch (Exception e) {
            result.setCode(Constant.CODE_ERROR);
            result.setMessage(e.getMessage());
            log.error("deleteUserById: [3] {} " + e.getMessage());
            e.printStackTrace();
        } finally {
            log.info("deleteUserById: [4] {}");
            return result;
        }

    }


    /**
     * Updating data in the H2 user table
     *
     * @param user
     * @param FILE_PATH
     * @return Result <code><</code>List<code>></code>
     */

    @Override
    public Result<List> updateUsers(User user, String FILE_PATH) {
        Result<List> result = new Result<>();
        try {
            Connection connection = connection(Constant.URL_H2, Constant.USERNAME_H2, Constant.PASSWORD_H2, Constant.DRIVER_H2);
            Statement statement = connection.createStatement();
            if (isInDataUser(user.getId(), FILE_PATH)) {
                takeHistoryUsers(this.getClass().getSimpleName(), "deleteUserById", "UPDATED", user.getId());
                String sql = Constant.UPDATE_USER + "SET LOGIN = " + '\'' + user.getLogin() + '\'' + ", " + "PASSWORD = " + '\'' + user.getPassword() + '\'' + "WHERE id = " + user.getId();
                statement.executeUpdate(sql);
            } else {
                String sql = Constant.INSERT_INTO_USER + "VALUES (" + user.getId() + ", " + '\'' + user.getLogin() + '\'' + ", " + '\'' + user.getPassword() + '\'' + ")";
                statement.executeUpdate(sql);
            }
            closeConnection(connection, statement);
            result.setCode(Constant.CODE_OK);
            result.setMessage("OK");
            log.info("updateUsers: [2] {}");
        } catch (Exception e) {
            log.error("updateUsers: [3] {} ".concat(e.getMessage()));
            result.setMessage(e.getMessage());
            result.setCode(Constant.CODE_ERROR);
            e.printStackTrace();
        } finally {
            return result;
        }
    }

    /**
     * Get a user object by ID from the H2 user table
     *
     * @param id
     * @param FILE_PATH
     * @return Result
     */

    @Override
    public Result getUserById(long id, String FILE_PATH) {
        Result<List> result = new Result();
        log.info("getUserById: [1] {}");
        try {
            Connection connection = connection(Constant.URL_H2, Constant.USERNAME_H2, Constant.PASSWORD_H2, Constant.DRIVER_H2);
            Statement statement = connection.createStatement();
            String sql = Constant.SELECT_ALL_FROM_USER + "WHERE ID = " + id;
            ResultSet rs = statement.executeQuery(sql);
            result.setData(h2ToUserList(rs));

            closeConnection(connection, statement);
            result.setCode(Constant.CODE_OK);
            result.setMessage("OK");
            log.info("getUserById: [2] {}");
        } catch (Exception e) {
            log.error("getUserById: [3] {} ".concat(e.getMessage()));
            result.setCode(Constant.CODE_ERROR);
            result.setMessage(e.getMessage());
            e.printStackTrace();
        } finally {
            return result;
        }
    }

    /**
     * Parsing ResultSet to Order object
     *
     * @param rs
     * @return List <code><</code>Order<code>></code>
     */
    public static List<Order> h2ToOrderList(ResultSet rs) {
        log.info("h2ToOrderList: [1] {}");
        List<Order> orderList = new ArrayList<>();
        try {
            while (rs.next()) {
                orderList.add(new Order(rs.getInt("ORDERID"), rs.getString("pharmacyProductBarcode")));
            }
            log.info("h2ToOrderList: [2] {}");
        } catch (Exception e) {
            log.error("h2ToOrderList [3] {} ".concat(e.getMessage()));
            e.printStackTrace();
        } finally {
            log.info("h2ToOrderList: [4] {}");
            return orderList;
        }
    }


    /**
     * Recording the state of an User object in Mongodb before its modification
     *
     * @param className
     * @param functionName
     * @param code
     * @return void
     */
    private static boolean takeHistoryOrders(String className, String functionName, String code, long orderId) {
        try {
            log.info("takeHistoryOrders [1] {}");
            DataProviderH2 dataProviderH2 = new DataProviderH2();

            if (isInDataOrder(orderId, Constant.URL_H2)) {
                List<Order> orderList = dataProviderH2.getOrders(Constant.URL_H2).getData();
                Order order = orderList.stream()
                        .filter(o -> o.getOrderId() == orderId)
                        .findFirst()
                        .get();
                HistoryContent historyContent = new HistoryContent();
                historyContent.putOrderHistory(order, className, functionName, code);
            }
        } catch (Exception e) {
            log.error("takeHistoryOrders: [2] {} ".concat(e.getMessage()));
            e.printStackTrace();
        }
        return true;
    }

    public static boolean isInDataOrder(long orderId, String FILE_PATH) {
        try {
            log.info("isInDataOrder [1] {}");
            DataProviderH2 dataProviderH2 = new DataProviderH2();
            List<Order> orderList = dataProviderH2.getOrders(Constant.URL_H2).getData();
            Optional<Order> orderOp = orderList.stream()
                    .filter(u -> u.getOrderId() == orderId)
                    .findAny();
            if (!orderOp.isPresent()) {
                log.info("No such element");
                return false;
            }
        } catch (Exception e) {
            log.error("isInDataOrder: [2] {} ".concat(e.getMessage()));
            e.printStackTrace();
        }
        return true;
    }


    /**
     * reading orders from a H2 DB for Orders
     *
     * @param FILE_PATH
     * @return List <code><</code>Order<code>></code>
     */

    public static List<Order> readH2Order(String FILE_PATH) {

        log.info("readH2Order: [1] {}");
        ResultSet rs = null;
        List<Order> orderList = new ArrayList<>();
        try {
            Connection connection = connection(Constant.URL_H2, Constant.USERNAME_H2, Constant.PASSWORD_H2, Constant.DRIVER_H2);
            Statement statement = connection.createStatement();
            String sql = Constant.SELECT_ALL_FROM_ORDERS;
            rs = statement.executeQuery(sql);
            orderList = h2ToOrderList(rs);
            closeConnection(connection, statement);
            log.info("readH2Order: [2] {}");
        } catch (Exception e) {
            log.error("readH2Order: [3] {} ".concat(e.getMessage()));
            e.printStackTrace();
        } finally {
            log.info("readH2Order: [4] {}");
            return orderList;
        }
    }

    /**
     * Checking for duplicates and deleting them from the H2 DB if available
     *
     * @param addedOrderList
     * @param FILE_PATH
     * @return List <code><</code>Order<code>></code>
     */

    private static boolean stringingDuplicateIdOrder(List<Order> addedOrderList, String FILE_PATH) {
        log.info("stringingDuplicateIdOrder: [1] {}");
        try {
            Connection connection = connection(Constant.URL_H2, Constant.USERNAME_H2, Constant.PASSWORD_H2, Constant.DRIVER_H2);
            Statement statement = connection.createStatement();
            for (Order order : addedOrderList) {
                String sql = Constant.DELETE_FROM_ORDERS_BY_ORDERID + order.getOrderId();
                statement.executeUpdate(sql);
            }
            log.info("stringingDuplicateIdOrder: [2] {}");
            closeConnection(connection, statement);
        } catch (Exception e) {
            log.error("stringingDuplicateIdDeleteUser: [3] {} ".concat(e.getMessage()));
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * Writing to a H2 DB for Orders
     *
     * @param orderList
     * @param FILE_PATH
     * @return List <code><</code>Order<code>></code>
     */
    public static List<Order> writeH2Order(List<Order> orderList, String FILE_PATH) {
        log.info("writeH2Order: [1] {}");
        try {
            Connection connection = connection(Constant.URL_H2, Constant.USERNAME_H2, Constant.PASSWORD_H2, Constant.DRIVER_H2);
            Statement statement = connection.createStatement();
            for (Order order : orderList) {
                String sql = Constant.INSERT_INTO_ORDERS + "VALUES (" + order.getOrderId() + ", " + '\'' + order.getPharmacyProductBarcode() + '\'' + ")";
                statement.executeUpdate(sql);
            }
            log.info("writeH2Order: [2] {}");
            closeConnection(connection, statement);
        } catch (Exception e) {
            log.error("writeH2Order: [3] {} ".concat(e.getMessage()));
            e.printStackTrace();
        } finally {
            log.info("writeH2Order: [4] {}");
            return orderList;
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
            orderList = readH2Order(FILE_PATH);
            result.setCode(Constant.CODE_OK);
            result.setMessage("OK");
            log.info("getOrders: [2] {}");
        } catch (Exception e) {
            result.setCode(Constant.CODE_ERROR);
            result.setMessage(e.getMessage());
            log.error("getOrders: [3] {} ".concat(e.getMessage()));
            e.printStackTrace();

        } finally {
            result.setData(orderList);
            log.info("getOrders: [4] {}");
            return result;
        }
    }

    /**
     * Adding List<code><</code>Order<code>></code> to a H2 DB
     *
     * @param orderList
     * @param FILE_PATH
     * @return Result <code><</code>List<code>></code>
     */

    public Result<List> appendOrders(List<Order> orderList, String FILE_PATH) {
        log.info("appendOrders: [1] {}");
        Result result = new Result();
        try {
            if (stringingDuplicateIdOrder(orderList, FILE_PATH)) {
                writeH2Order(orderList, FILE_PATH);
                result.setCode(Constant.CODE_OK);
                result.setMessage("OK");
                log.info("appendOrders: [2] {}");
            } else {
                log.error("appendOrders: [3] {} Duplicates didn't delete");
            }

        } catch (Exception e) {
            log.error("appendOrders: [4] {} ".concat(e.getMessage()));
            result.setCode(Constant.CODE_ERROR);
            result.setMessage(e.getMessage());
            e.printStackTrace();
        } finally {
            result.setData(orderList);
            log.info("appendOrders: [5] {}");
            return result;
        }
    }

    /**
     * Deleting  of the Order element H2 DB file by ID
     *
     * @param id
     * @param FILE_PATH
     * @return Result <code><</code>List<code>></code>
     */

    public Result<List> deleteOrderById(long id, String FILE_PATH) {
        log.info("deleteOrderById: [1] {}");
        Result result = new Result();
        try {
            Connection connection = connection(Constant.URL_H2, Constant.USERNAME_H2, Constant.PASSWORD_H2, Constant.DRIVER_H2);
            Statement statement = connection.createStatement();
            takeHistoryOrders(this.getClass().getSimpleName(), "deleteOrderById", "DElETED", id);
            String sql = Constant.DELETE_FROM_ORDERS_BY_ORDERID + id;
            statement.executeUpdate(sql);
            closeConnection(connection, statement);
            result.setCode(Constant.CODE_OK);
            result.setMessage("OK");
        } catch (Exception e) {
            result.setCode(Constant.CODE_ERROR);
            result.setMessage(e.getMessage());
            log.error("deleteOrderById: [3] {} " + e.getMessage());
            e.printStackTrace();
        } finally {
            log.info("deleteUserById: [4] {}");
            return result;
        }
    }

    /**
     * Updating Order in the H2 DB if an element with this ID is already present it is replaced otherwise a new one is added
     *
     * @param order
     * @param FILE_PATH
     * @return Result <code><</code>List<code>></code>
     */

    public Result<List> updateOrders(Order order, String FILE_PATH) {
        log.info("updateOrders: [1] {}");
        Result<List> result = new Result<>();
        try {
            Connection connection = connection(Constant.URL_H2, Constant.USERNAME_H2, Constant.PASSWORD_H2, Constant.DRIVER_H2);
            Statement statement = connection.createStatement();

            if (isInDataOrder(order.getOrderId(), FILE_PATH)) {
                takeHistoryOrders(this.getClass().getSimpleName(), "deleteUserById", "UPDATED", order.getOrderId());
                String sql = Constant.UPDATE_ORDERS + "SET pharmacyProductBarcode = " + '\'' + order.getPharmacyProductBarcode() + '\'' + " WHERE ORDERID = " + order.getOrderId();
                statement.executeUpdate(sql);
                log.info("updateOrders: [3] {}");
            } else {
                String sql = Constant.INSERT_INTO_ORDERS + "VALUES (" + order.getOrderId() + ", " + '\'' + order.getPharmacyProductBarcode() + '\'' + ")";
                statement.executeUpdate(sql);
                log.info("updateOrders: [4] {}");
            }
            closeConnection(connection, statement);
            result.setCode(Constant.CODE_OK);
            result.setMessage("OK");
            log.info("updateOrders: [5] {}");
        } catch (Exception e) {
            log.error("updateOrders: [6] {} ".concat(e.getMessage()));
            result.setMessage(e.getMessage());
            result.setCode(Constant.CODE_ERROR);
            e.printStackTrace();
        } finally {
            return result;
        }
    }

    /**
     * Getting a Order object from a H2 DB by ID
     *
     * @param id
     * @param FILE_PATH
     * @return Result
     */

    public Result getOrderById(long id, String FILE_PATH) {
        Result<List> result = new Result();
        log.info("getUserById: [1] {}");
        try {
            Connection connection = connection(Constant.URL_H2, Constant.USERNAME_H2, Constant.PASSWORD_H2, Constant.DRIVER_H2);
            Statement statement = connection.createStatement();
            String sql = Constant.SELECT_ALL_FROM_ORDERS + "WHERE ORDERID = " + id;
            ResultSet rs = statement.executeQuery(sql);
            result.setData(h2ToOrderList(rs));

            closeConnection(connection, statement);
            result.setCode(Constant.CODE_OK);
            result.setMessage("OK");
            log.info("getUserById: [2] {}");
        } catch (Exception e) {
            log.error("getUserById: [3] {} ".concat(e.getMessage()));
            result.setCode(Constant.CODE_ERROR);
            result.setMessage(e.getMessage());
            e.printStackTrace();
        } finally {
            return result;
        }
    }

    /**
     * Creating order for user and updating order and user H2 files
     *
     * @param userId
     * @param pharmacyProductName
     * @return Order
     */
    public Order createOrder(long userId, String pharmacyProductName) {
        log.info("createOrder [1] {}");
        Order order = new Order();
        try {
            if (getUserById(userId, Constant.URL_H2).getData() != null) {
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
     * adding pharmacy product to orders H2 file
     * @param order
     * @param pharmacyProductName
     */
    public void changePharmacyProduct(Order order, String pharmacyProductName){
        log.info("pharmacyProductName [1] {}");
        try {
            if (!searchPharmacyProductByName(pharmacyProductName).isEmpty()) {
                order.setPharmacyProductBarcode(searchPharmacyProductByName(pharmacyProductName).get(0).getBarcode());
                updateOrders(order, Constant.URL_H2);
            } else {
                log.info("pharmacyProductName [2] {} No such pharmacy product");
            }
        }catch (Exception e){
            log.error("pharmacyProductName [3]".concat(e.getMessage()));
            e.printStackTrace();
        }
    }


    /**
     * Recording the state of an object in Mongodb before its modification
     *
     * @param className
     * @param functionName
     * @param code
     * @param barcode
     * @return boolean
     */

    private static boolean takeHistoryMeddicine(String className, String functionName, String code, String barcode) {
        try {
            log.info("takeHistoryMeddicine [1] {}");
            DataProviderH2 dataProviderH2 = new DataProviderH2();
            List<Meddicine> meddicineList = dataProviderH2.getMeddicines(Constant.URL_H2).getData();
            Meddicine meddicine = meddicineList.stream()
                    .filter(u -> u.getBarcode().equals(barcode))
                    .findAny()
                    .get();
            HistoryContent historyContent = new HistoryContent();
            historyContent.putMeddicineHistory(meddicine, className, functionName, code);
        } catch (Exception e) {
            log.error("takeHistoryMeddicine: [2] {} ".concat(e.getMessage()));
            e.printStackTrace();
        }
        return true;
    }


    /**
     * check for duplicate Barcodes and if there are any, replace them in the H2 USER table
     *
     * @param addedMeddicineList
     * @return boolean
     */

    public static boolean stringingDuplicateBarcodeDeleteMeddicine(List<Meddicine> addedMeddicineList) {
        log.info("stringingDuplicateIdDeleteMeddicine: [1] {}");
        try {
            Connection connection = connection(Constant.URL_H2, Constant.USERNAME_H2, Constant.PASSWORD_H2, Constant.DRIVER_H2);
            Statement statement = connection.createStatement();
            for (Meddicine meddicine : addedMeddicineList) {
                String sql = Constant.DELETE_FROM_MEDDICINE_BY_BARCODE + '\'' + meddicine.getBarcode() + '\'';
                statement.executeUpdate(sql);
            }
            log.info("stringingDuplicateIdDeleteMeddicine: [2] {}");
            closeConnection(connection, statement);
        } catch (Exception e) {
            log.error("stringingDuplicateIdDeleteMeddicine: [3] {} ".concat(e.getMessage()));
            e.printStackTrace();
            return false;
        }
        return true;
    }


    /**
     * Write Users to H2 Meddicine table
     *
     * @param meddicineList
     * @return List <code><</code>Meddicine<code>></code>
     */

    public static List<Meddicine> writeToH2Meddicine(List<Meddicine> meddicineList) {
        log.info("writeToH2Meddicine: [1] {}");
        try {
            Connection connection = connection(Constant.URL_H2, Constant.USERNAME_H2, Constant.PASSWORD_H2, Constant.DRIVER_H2);
            Statement statement = connection.createStatement();
            for (Meddicine meddicine : meddicineList) {
                String sql = Constant.INSERT_INTO_MEDDICINE + "VALUES (" + '\'' + meddicine.getBarcode() + '\'' + ", " + '\'' + meddicine.getNameOfPharmacyProduct() + '\'' + ", " + '\'' + meddicine.getDescription() + '\'' + ", " + meddicine.getPrice() + ", " + '\'' + meddicine.getActiveSubstanceOfTheMeddicine() + '\'' + ", " + '\'' + meddicine.getCategoryOfTheMeddicine() + '\'' + ")";
                statement.executeUpdate(sql);
            }
            log.info("writeToH2Meddicine: [2] {}");
            closeConnection(connection, statement);
        } catch (Exception e) {
            log.error("writeToH2Meddicine: [3] {} ".concat(e.getMessage()));
            e.printStackTrace();
        } finally {
            log.info("writeToH2Meddicine: [4] {}");
            return meddicineList;
        }
    }

    /**
     * Converting data to Meddicine object
     *
     * @param rs
     * @return List <code><</code>Meddicine<code>></code>
     */

    public static List<Meddicine> h2ToMeddicineList(ResultSet rs) {
        log.info("h2ToMeddicineList: [1] {}");
        List<Meddicine> meddicineList = new ArrayList<>();
        try {
            while (rs.next()) {
                meddicineList.add(new Meddicine(rs.getString("NAMEOFPHARMACYPRODUCT"), rs.getInt("PRICE"), rs.getString("BARCODE"), rs.getString("DESCRIPTION"), rs.getString("ACTIVESUBSTANCEOFTHEMEDDICINE"), rs.getString("CATEGORYOFTHEMEDDICINE")));
            }
            log.info("h2ToMeddicineList: [2] {}");
        } catch (Exception e) {
            log.error("h2ToMeddicineList [3] {} ".concat(e.getMessage()));
            e.printStackTrace();
        } finally {
            log.info("h2ToMeddicineList: [4] {}");
            return meddicineList;
        }
    }


    /**
     * Read Users from H2 Meddicine table
     *
     * @return List <code><</code>Meddicine<code>></code>
     */

    public static List<Meddicine> readMeddicinesFromH2M() {
        log.info("readMeddicinesFromH2M: [1] {}");
        ResultSet rs = null;
        List<Meddicine> meddicineList = new ArrayList<>();
        try {
            Connection connection = connection(Constant.URL_H2, Constant.USERNAME_H2, Constant.PASSWORD_H2, Constant.DRIVER_H2);
            Statement statement = connection.createStatement();
            String sql = Constant.SELECT_ALL_FROM_MEDDICINE;
            rs = statement.executeQuery(sql);
            meddicineList = h2ToMeddicineList(rs);
            closeConnection(connection, statement);
            log.info("readMeddicinesFromH2M: [2] {}");
        } catch (Exception e) {
            log.error("readMeddicinesFromH2M: [3] {} ".concat(e.getMessage()));
            e.printStackTrace();
        } finally {
            log.info("readFromH2: [4] {}");
            return meddicineList;
        }
    }


    /**
     * Get a List<Meddicine> of all meddicines from the H2 meddicine table
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
            meddicineList = readMeddicinesFromH2M();
            result.setCode(Constant.CODE_OK);
            result.setMessage("OK");
            log.info("getMeddicines: [2] {}");
        } catch (Exception e) {
            result.setCode(Constant.CODE_ERROR);
            result.setMessage(e.getMessage());
            log.error("getMeddicines: [3] {} ".concat(e.getMessage()));
            e.printStackTrace();

        } finally {
            result.setData(meddicineList);
            log.info("getMeddicines: [4] {}");
            return result;
        }
    }

    /**
     * Adding List<Meddicine> Meddicines to the H2 Meddicine table
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
            if (stringingDuplicateBarcodeDeleteMeddicine(meddicinesList)) {
                writeToH2Meddicine(meddicinesList);
                result.setCode(Constant.CODE_OK);
                result.setMessage("OK");
                log.info("appendMeddicines: [2] {}");
            } else {
                log.error("appendMeddicines: [3] {} Duplicates didn't delete");
            }

        } catch (Exception e) {
            log.error("appendUsers: [4] {} ".concat(e.getMessage()));
            result.setCode(Constant.CODE_ERROR);
            result.setMessage(e.getMessage());
            e.printStackTrace();
        } finally {
            result.setData(meddicinesList);
            log.info("appendMeddicines: [5] {}");
            return result;
        }
    }

    /**
     * Removing the element by Barcode from the H2 Meddicine table
     *
     * @param barcode
     * @param FILE_PATH
     * @return Result <code><</code>List<code>></code>
     */

    @Override
    public Result<List> deleteMeddicineByBarcode(String barcode, String FILE_PATH) {
        log.info("deleteMeddicineByBarcode: [1] {}");
        Result result = new Result();
        try {
            Connection connection = connection(Constant.URL_H2, Constant.USERNAME_H2, Constant.PASSWORD_H2, Constant.DRIVER_H2);
            Statement statement = connection.createStatement();
            takeHistoryMeddicine(this.getClass().getSimpleName(), "deleteMeddicineByBarcode", "DElETED", barcode);
            String sql = Constant.DELETE_FROM_MEDDICINE_BY_BARCODE + '\'' + barcode + '\'';
            statement.executeUpdate(sql);
            closeConnection(connection, statement);
            result.setCode(Constant.CODE_OK);
            result.setMessage("OK");
        } catch (Exception e) {
            result.setCode(Constant.CODE_ERROR);
            result.setMessage(e.getMessage());
            log.error("deleteMeddicineByBarcode: [3] {} " + e.getMessage());
            e.printStackTrace();
        } finally {
            log.info("deleteMeddicineByBarcode: [4] {}");
            return result;
        }
    }


    /**
     * Updating data in the H2 Meddicine table
     *
     * @param meddicine
     * @param FILE_PATH
     * @return Result <code><</code>List<code>></code>
     */

    @Override
    public Result<List> updateMeddicine(Meddicine meddicine, String FILE_PATH) {
        log.info("updateMeddicine [1] {}");
        Result<List> result = new Result<>();
        try {
            Connection connection = connection(Constant.URL_H2, Constant.USERNAME_H2, Constant.PASSWORD_H2, Constant.DRIVER_H2);
            Statement statement = connection.createStatement();
            takeHistoryMeddicine(this.getClass().getSimpleName(), "updateMeddicine", "UPDATED", meddicine.getBarcode());
            String sql = Constant.UPDATE_MEDDICINE + "SET NAMEOFPHARMACYPRODUCT = " + '\'' + meddicine.getNameOfPharmacyProduct() + '\'' + ", " + "DESCRIPTION = " + '\'' + meddicine.getDescription() + '\'' + ", " + "PRICE = " + meddicine.getPrice() + ", " + "ACTIVESUBSTANCEOFTHEMEDDICINE = " + '\'' + meddicine.getActiveSubstanceOfTheMeddicine() + '\'' + ", " + "CATEGORYOFTHEMEDDICINE = " + '\'' + meddicine.getCategoryOfTheMeddicine() + '\'' + " WHERE BARCODE = " + '\'' + meddicine.getBarcode() + '\'';
            statement.executeUpdate(sql);
            closeConnection(connection, statement);
            result.setCode(Constant.CODE_OK);
            result.setMessage("OK");
            log.info("updateMeddicine: [2] {}");
        } catch (Exception e) {
            log.error("updateMeddicine: [3] {} ".concat(e.getMessage()));
            result.setMessage(e.getMessage());
            result.setCode(Constant.CODE_ERROR);
            e.printStackTrace();
        } finally {
            log.info("updateMeddicine [4] {}");
            return result;
        }
    }


    /**
     * Get a Meddicine object by Barcode from the H2 Meddicine table
     *
     * @param barcode
     * @param FILE_PATH
     * @return Result
     */
    @Override
    public Result getMeddicineByBarcode(String barcode, String FILE_PATH) {
        Result<List> result = new Result();
        log.info("getMeddicineByBarcode: [1] {}");
        try {
            Connection connection = connection(Constant.URL_H2, Constant.USERNAME_H2, Constant.PASSWORD_H2, Constant.DRIVER_H2);
            Statement statement = connection.createStatement();
            String sql = Constant.SELECT_ALL_FROM_MEDDICINE + "WHERE BARCODE = " + '\'' + barcode + '\'';
            ResultSet rs = statement.executeQuery(sql);
            result.setData(h2ToMeddicineList(rs));

            closeConnection(connection, statement);
            result.setCode(Constant.CODE_OK);
            result.setMessage("OK");
            log.info("getMeddicineByBarcode: [2] {}");
        } catch (Exception e) {
            log.error("getMeddicineByBarcode: [3] {} ".concat(e.getMessage()));
            result.setCode(Constant.CODE_ERROR);
            result.setMessage(e.getMessage());
            e.printStackTrace();
        } finally {
            return result;
        }
    }


    /**
     * Recording the state of an object in Mongodb before its modification
     *
     * @param className
     * @param functionName
     * @param code
     * @param barcode
     * @return boolean
     */

    private static boolean takeHistoryMedicalDevice(String className, String functionName, String code, String
            barcode) {
        try {
            log.info("takeHistoryMedicalDevice [1] {}");
            DataProviderH2 dataProviderH2 = new DataProviderH2();
            List<MedicalDevice> medicalDeviceList = dataProviderH2.getMedicalDevices(Constant.URL_H2).getData();
            MedicalDevice medicalDevice = medicalDeviceList.stream()
                    .filter(u -> u.getBarcode().equals(barcode))
                    .findAny()
                    .get();
            HistoryContent historyContent = new HistoryContent();
            historyContent.putMedicalDeviceHistory(medicalDevice, className, functionName, code);
        } catch (Exception e) {
            log.error("takeHistoryMedicalDevice: [2] {} ".concat(e.getMessage()));
            e.printStackTrace();
        }
        return true;
    }


    /**
     * check for duplicate Barcodes and if there are any, replace them in the H2 MedicalDevice table
     *
     * @param addedMedicalDeviceList
     * @return boolean
     */

    public static boolean stringingDuplicateBarcodeDeleteMedicalDevice
            (List<MedicalDevice> addedMedicalDeviceList) {
        log.info("stringingDuplicateBarcodeDeleteMedicalDevice: [1] {}");
        try {
            Connection connection = connection(Constant.URL_H2, Constant.USERNAME_H2, Constant.PASSWORD_H2, Constant.DRIVER_H2);
            Statement statement = connection.createStatement();
            for (MedicalDevice medicalDevice : addedMedicalDeviceList) {
                String sql = Constant.DELETE_FROM_MEDICALDEVICES_BY_BARCODE + '\'' + medicalDevice.getBarcode() + '\'';
                statement.executeUpdate(sql);
            }
            log.info("stringingDuplicateBarcodeDeleteMedicalDevice: [2] {}");
            closeConnection(connection, statement);
        } catch (Exception e) {
            log.error("stringingDuplicateBarcodeDeleteMedicalDevice: [3] {} ".concat(e.getMessage()));
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * Write MedicalDevice to H2 MedicalDevices table
     *
     * @param medicalDeviceList
     * @return List <code><</code>MedicalDevice<code>></code>
     */

    public static List<MedicalDevice> writeToH2MedicalDevice(List<MedicalDevice> medicalDeviceList) {
        log.info("writeToH2MedicalDevice: [1] {}");
        try {
            Connection connection = connection(Constant.URL_H2, Constant.USERNAME_H2, Constant.PASSWORD_H2, Constant.DRIVER_H2);
            Statement statement = connection.createStatement();
            for (MedicalDevice medicalDevice : medicalDeviceList) {
                String sql = Constant.INSERT_INTO_MEDICALDEVICES + "VALUES (" + '\'' + medicalDevice.getBarcode() + '\'' + ", " + '\'' + medicalDevice.getNameOfPharmacyProduct() + '\'' + ", " + '\'' + medicalDevice.getDescription() + '\'' + ", " + medicalDevice.getPrice() + ", " + '\'' + medicalDevice.getCategoryOfPharmacyDevices() + '\'' + ")";
                statement.executeUpdate(sql);
            }
            log.info("writeToH2MedicalDevice: [2] {}");
            closeConnection(connection, statement);
        } catch (Exception e) {
            log.error("writeToH2MedicalDevice: [3] {} ".concat(e.getMessage()));
            e.printStackTrace();
        } finally {
            log.info("writeToH2MedicalDevice: [4] {}");
            return medicalDeviceList;
        }
    }

    /**
     * Converting data to MedicalDevice object
     *
     * @param rs
     * @return List <code><</code>MedicalDevice<code>></code>
     */

    public static List<MedicalDevice> h2ToMedicalDeviceList(ResultSet rs) {
        log.info("h2ToMedicalDeviceList: [1] {}");
        List<MedicalDevice> medicalDeviceList = new ArrayList<>();
        try {
            while (rs.next()) {
                medicalDeviceList.add(new MedicalDevice(rs.getString("NAMEOFPHARMACYPRODUCT"), rs.getInt("PRICE"), rs.getString("BARCODE"), rs.getString("DESCRIPTION"), rs.getString("CATEGORYOFPHARMACYDEVICES")));
            }
            log.info("h2ToMedicalDeviceList: [2] {}");
        } catch (Exception e) {
            log.error("h2ToMedicalDeviceList [3] {} ".concat(e.getMessage()));
            e.printStackTrace();
        } finally {
            log.info("h2ToMedicalDeviceList: [4] {}");
            return medicalDeviceList;
        }
    }

    /**
     * Read MedicalDevices from H2 MedicalDevices table
     *
     * @return List <code><</code>MedicalDevice<code>></code>
     */

    public static List<MedicalDevice> readMedicalDeviceFromH2M() {
        log.info("readMedicalDeviceFromH2M: [1] {}");
        ResultSet rs = null;
        List<MedicalDevice> medicalDeviceList = new ArrayList<>();
        try {
            Connection connection = connection(Constant.URL_H2, Constant.USERNAME_H2, Constant.PASSWORD_H2, Constant.DRIVER_H2);
            Statement statement = connection.createStatement();
            String sql = Constant.SELECT_ALL_FROM_MEDICALDEVICES;
            rs = statement.executeQuery(sql);
            medicalDeviceList = h2ToMedicalDeviceList(rs);
            closeConnection(connection, statement);
            log.info("readMedicalDeviceFromH2M: [2] {}");
        } catch (Exception e) {
            log.error("readMedicalDeviceFromH2M: [3] {} ".concat(e.getMessage()));
            e.printStackTrace();
        } finally {
            log.info("readMedicalDeviceFromH2M: [4] {}");
            return medicalDeviceList;
        }
    }

    /**
     *  Get a List<MedicalDevice> of all medicalDevices from the H2 MedicalDevices table
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
            medicalDeviceList = readMedicalDeviceFromH2M();
            result.setCode(Constant.CODE_OK);
            result.setMessage("OK");
            log.info("getMedicalDevices: [2] {}");
        } catch (Exception e) {
            result.setCode(Constant.CODE_ERROR);
            result.setMessage(e.getMessage());
            log.error("getMedicalDevices: [3] {} ".concat(e.getMessage()));
            e.printStackTrace();

        } finally {
            result.setData(medicalDeviceList);
            log.info("getMedicalDevices: [4] {}");
            return result;
        }
    }

    /**
     * Adding List<MedicalDevice> MedicalDevices to the H2 MedicalDevices table
     *
     * @param medicalDeviceList
     * @param FILE_PATH
     * @return Result <code><</code>List<code>></code>
     */
    @Override
    public Result<List> appendMedicalDevices(List<MedicalDevice> medicalDeviceList, String FILE_PATH) {
        log.info("appendMeddicines: [1] {}");
        Result result = new Result();
        try {
            if (stringingDuplicateBarcodeDeleteMedicalDevice(medicalDeviceList)) {
                writeToH2MedicalDevice(medicalDeviceList);
                result.setCode(Constant.CODE_OK);
                result.setMessage("OK");
                log.info("appendMedicalDevices: [2] {}");
            } else {
                log.error("appendMedicalDevices: [3] {} Duplicates didn't delete");
            }

        } catch (Exception e) {
            log.error("appendMedicalDevices: [4] {} ".concat(e.getMessage()));
            result.setCode(Constant.CODE_ERROR);
            result.setMessage(e.getMessage());
            e.printStackTrace();
        } finally {
            result.setData(medicalDeviceList);
            log.info("appendMedicalDevices: [5] {}");
            return result;
        }
    }

    /**
     *Removing the element by Barcode from the H2 MedicalDevices table
     *
     * @param barcode
     * @param FILE_PATH
     * @return Result <code><</code>List<code>></code>
     */


    @Override
    public Result<List> deleteMedicalDeviceByBarcode(String barcode, String FILE_PATH) {
        log.info("deleteMedicalDeviceByBarcode: [1] {}");
        Result result = new Result();
        try {
            Connection connection = connection(Constant.URL_H2, Constant.USERNAME_H2, Constant.PASSWORD_H2, Constant.DRIVER_H2);
            Statement statement = connection.createStatement();
            takeHistoryMedicalDevice(this.getClass().getSimpleName(), "deleteMedicalDeviceByBarcode", "DElETED", barcode);
            String sql = Constant.DELETE_FROM_MEDICALDEVICES_BY_BARCODE + '\'' + barcode + '\'';
            statement.executeUpdate(sql);
            log.info("deleteMedicalDeviceByBarcode: [2] {}");
            closeConnection(connection, statement);
            result.setCode(Constant.CODE_OK);
            result.setMessage("OK");
        } catch (Exception e) {
            result.setCode(Constant.CODE_ERROR);
            result.setMessage(e.getMessage());
            log.error("deleteMedicalDeviceByBarcode: [3] {} " + e.getMessage());
            e.printStackTrace();
        } finally {
            log.info("deleteMedicalDeviceByBarcode: [4] {}");
            return result;
        }
    }

    /**
     * Updating data in the H2 MedicalDevices table
     *
     * @param medicalDevice
     * @param FILE_PATH
     * @return Result <code><</code>List<code>></code>
     */

    @Override
    public Result<List> updateMedicalDevice(MedicalDevice medicalDevice, String FILE_PATH) {
        log.info("updateMedicalDevice [1] {}");
        Result<List> result = new Result<>();
        try {
            Connection connection = connection(Constant.URL_H2, Constant.USERNAME_H2, Constant.PASSWORD_H2, Constant.DRIVER_H2);
            Statement statement = connection.createStatement();
            takeHistoryMedicalDevice(this.getClass().getSimpleName(), "updateMedicalDevice", "UPDATED", medicalDevice.getBarcode());
            String sql = Constant.UPDATE_MEDICALDEVICES + "SET NAMEOFPHARMACYPRODUCT = " + '\'' + medicalDevice.getNameOfPharmacyProduct() + '\'' + ", " + "DESCRIPTION = " + '\'' + medicalDevice.getDescription() + '\'' + ", " + "PRICE = " + medicalDevice.getPrice() + ", " + "CATEGORYOFPHARMACYDEVICES = " + '\'' + medicalDevice.getCategoryOfPharmacyDevices() + '\'' + " WHERE BARCODE = " + '\'' + medicalDevice.getBarcode() + '\'';
            statement.executeUpdate(sql);
            closeConnection(connection, statement);
            result.setCode(Constant.CODE_OK);
            result.setMessage("OK");
            log.info("updateMedicalDevice: [2] {}");
        } catch (Exception e) {
            log.error("updateMedicalDevice: [3] {} ".concat(e.getMessage()));
            result.setMessage(e.getMessage());
            result.setCode(Constant.CODE_ERROR);
            e.printStackTrace();
        } finally {
            log.info("updateMedicalDevice [4] {}");
            return result;
        }
    }
    /**
     * Get a MedicalDevice object by Barcode from the H2 MedicalDevices table
     *
     * @param barcode
     * @param FILE_PATH
     * @return Result <code><</code>List<code>></code>
     */

    @Override
    public Result getMedicalDeviceByBarcode(String barcode, String FILE_PATH) {
        Result<List> result = new Result();
        log.info("getMedicalDeviceByBarcode: [1] {}");
        try {
            Connection connection = connection(Constant.URL_H2, Constant.USERNAME_H2, Constant.PASSWORD_H2, Constant.DRIVER_H2);
            Statement statement = connection.createStatement();
            String sql = Constant.SELECT_ALL_FROM_MEDICALDEVICES + "WHERE BARCODE = " + '\'' + barcode + '\'';
            ResultSet rs = statement.executeQuery(sql);
            result.setData(h2ToMedicalDeviceList(rs));

            closeConnection(connection, statement);
            result.setCode(Constant.CODE_OK);
            result.setMessage("OK");
            log.info("getMedicalDeviceByBarcode: [2] {}");
        } catch (Exception e) {
            log.error("getMedicalDeviceByBarcode: [3] {} ".concat(e.getMessage()));
            result.setCode(Constant.CODE_ERROR);
            result.setMessage(e.getMessage());
            e.printStackTrace();
        } finally {
            return result;
        }
    }


    /**
     * Searching Meddicines objects in the category from H2 DB
     *
     * @param category
     * @return List <code><</code>Meddicine<code>></code>
     */
    public List<Meddicine> searchMeddicineByCategory(String category) {
        log.info("searchPharmacyProductByCategory [1] {}");
        List<Meddicine> findedMeddicines = new ArrayList<>();
        try {
            List<Meddicine> meddicineList = getMeddicines(Constant.URL_H2).getData();
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
     * Searching PharmacyProduct objects in the category from H2 DB
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
     * Searching MedicalDevice objects in the category from H2 DB
     *
     * @param category
     * @return List <code><</code>MedicalDevice<code>></code>
     */
    public List<MedicalDevice> searchMedicalDeviceByCategory(String category) {
        log.info("searchPharmacyMedicalDeviceByCategory [1] {}");
        List<MedicalDevice> findedMedicalDevice = new ArrayList<>();
        try {
            List<MedicalDevice> medicalDeviceList = getMedicalDevices(Constant.URL_H2).getData();
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
     * Searching Meddicines objects from H2 DB by name
     *
     * @param nameOfPharmacyProduct
     * @return List <code><</code>Meddicine<code>></code>
     */
    public List<Meddicine> searchMeddicineByName(String nameOfPharmacyProduct) {
        log.info("searchMeddicineByName [1] {}");
        List<Meddicine> findedMeddicines = new ArrayList<>();
        try {
            List<Meddicine> meddicineList = getMeddicines(Constant.URL_H2).getData();
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
     * Searching MedicalDevice objects from H2 DB by name
     *
     * @param nameOfPharmacyProduct
     * @return List <code><</code>MedicalDevice<code>></code>
     */
    public List<MedicalDevice> searchMedicalDeviceByName(String nameOfPharmacyProduct) {
        log.info("searchMedicalDeviceByName [1] {}");
        List<MedicalDevice> findedMedicalDevices = new ArrayList<>();
        try {
            List<MedicalDevice> medicalDeviceList = getMedicalDevices(Constant.URL_H2).getData();
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
     * Searching PharmacyProduct objects from H2 DB by name
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
     * Getting the analogues of medicine from H2 DB
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
                meddicineList = getMeddicines(Constant.URL_H2).getData();
                analoguesList = meddicineList.stream().filter(m -> m.getActiveSubstanceOfTheMeddicine().equals(meddicine.getActiveSubstanceOfTheMeddicine()))
                        .collect(Collectors.toList());
                log.info("getTheAnaloguesOfMedicine [2] {}");
            } else {
                log.info("getTheAnaloguesOfMedicine [3] {} No such meddicine");
                analoguesList = getTheAnaloguesByBarcode(meddicineName);
            }
        } catch (Exception e) {
            log.error("getTheAnaloguesOfMedicine [3] {} ".concat(e.getMessage()));
            e.printStackTrace();
        } finally {
            log.info("getTheAnaloguesOfMedicine [4] {}");
            return analoguesList;
        }
    }

    /**
     * Getting the analogues of medicine by barcode from H2 DB
     *
     * @param barcode
     * @return List <code><</code>Meddicine<code>></code>
     */
    @Override
    public List<Meddicine> getTheAnaloguesByBarcode(String barcode) {
        log.info("getTheAnaloguesByBarcode [1] {}");
        List<Meddicine> analoguesList = new ArrayList<>();
        try {
            analoguesList = (List<Meddicine>) getMeddicineByBarcode(barcode, Constant.URL_H2).getData();
            if (!analoguesList.isEmpty()) {
                Meddicine meddicine = analoguesList.get(0);
                analoguesList = getTheAnaloguesOfMedicine(meddicine.getNameOfPharmacyProduct());
                log.info("getTheAnaloguesByBarcode [2] {}");
            } else log.info("getTheAnaloguesByBarcode [3] {} No such meddicine");
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
