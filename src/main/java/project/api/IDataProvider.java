package project.api;

import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import project.model.*;
import project.utils.Result;

import java.io.IOException;
import java.util.List;

public interface IDataProvider {
    /**
     * Getting a List<code><</code>User<code>></code> from datasource
     *
     * @param FILE_PATH
     * @return Result <code><</code>List<code>></code>
     */
    Result<List> getUsers(String FILE_PATH) throws IOException;
    /**
     * Adding List<code><</code>User<code>></code> to datasource
     *
     * @param userList
     * @param FILE_PATH
     * @return Result <code><</code>List<code>></code>
     */
    Result<List> appendUsers(List<User> userList, String FILE_PATH) throws IOException, CsvRequiredFieldEmptyException, CsvDataTypeMismatchException;
    /**
     * Deleting  of the User element from datasource by ID
     *
     * @param id
     * @param FILE_PATH
     * @return Result <code><</code>List<code>></code>
     */
    Result<List> deleteUserById(long id, String FILE_PATH);
    /**
     * Updating Users in the datasource if an element with this ID is already present it is replaced otherwise a new one is added
     *
     * @param user
     * @param FILE_PATH
     * @return Result <code><</code>List<code>></code>
     */
    Result<List> updateUsers(User user, String FILE_PATH);
    /**
     * Getting a User object from a datasource file by ID
     *
     * @param id
     * @param FILE_PATH
     * @return Result
     */
    Result getUserById(long id, String FILE_PATH);
    /**
     * Getting a List<code><</code>Order<code>></code> from a datasource
     *
     * @param FILE_PATH
     * @return Result <code><</code>List<code>></code>
     */
    Result<List> getOrders(String FILE_PATH) throws IOException;
    /**
     * Adding List<code><</code>Order<code>></code> to a datasource
     *
     * @param orderList
     * @param FILE_PATH
     * @return Result <code><</code>List<code>></code>
     */
    Result<List> appendOrders(List<Order> orderList, String FILE_PATH);
    /**
     * Deleting  of the Order element from datasource by ID
     *
     * @param id
     * @param FILE_PATH
     * @return Result <code><</code>List<code>></code>
     */
    Result<List> deleteOrderById(long id, String FILE_PATH);
    /**
     * Updating Order in the datasource if an element with this ID is already present it is replaced otherwise a new one is added
     *
     * @param order
     * @param FILE_PATH
     * @return Result <code><</code>List<code>></code>
     */
    Result<List> updateOrders(Order order, String FILE_PATH);
    /**
     * Getting a Order object from a datasource by ID
     *
     * @param id
     * @param FILE_PATH
     * @return Result
     */
    Result getOrderById(long id, String FILE_PATH);
    /**
     * Creating order for user and updating order and user in datasource
     *
     * @param userId
     * @param pharmacyProductName
     * @return Order
     */
    Order createOrder(long userId, String pharmacyProductName);
    /**
     * Getting a list of meddicines from a datasource
     *
     * @param FILE_PATH
     * @return Result <code><</code>List<code>></code>
     */
    Result<List> getMeddicines(String FILE_PATH);
    /**
     * Adding list of meddicines to a Meddicinenes datasource
     *
     * @param meddicinesList
     * @param FILE_PATH
     * @return Result <code><</code>List<code>></code>
     */
    Result<List> appendMeddicines(List<Meddicine> meddicinesList, String FILE_PATH);
    /**
     * Delete medicineBidirectional object from medicineBidirectional datasource by Barcode
     *
     * @param barcode
     * @param FILE_PATH
     * @return Result <code><</code>List<code>></code>
     */
    Result<List> deleteMeddicineByBarcode(String barcode, String FILE_PATH);
    /**
     * Updating medicineBidirectional object in datasource if an element with this Barcode is already present it is replaced otherwise a new one is added
     *
     * @param meddicine
     * @param FILE_PATH
     * @return Result <code><</code>List<code>></code>
     */
    Result<List> updateMeddicine(Meddicine meddicine, String FILE_PATH);
    /**
     * Getting a medicineBidirectional object from a datasource by barcode
     *
     * @param barcode
     * @param FILE_PATH
     * @return Result
     */
    Result getMeddicineByBarcode(String barcode, String FILE_PATH);
    /**
     * Getting a list of medicalDevice from a datasource
     *
     * @param FILE_PATH
     * @return Result <code><</code>List<code>></code>
     */
    Result<List> getMedicalDevices(String FILE_PATH);
    /**
     * Adding list of medicalDevices to a datasource
     *
     * @param medicalDeviceList
     * @param FILE_PATH
     * @return Result <code><</code>List<code>></code>
     */
    Result<List> appendMedicalDevices(List<MedicalDevice> medicalDeviceList, String FILE_PATH);
    /**
     * Delete medicalDevice object from datasource by Barcode
     *
     * @param barcode
     * @param FILE_PATH
     * @return Result <code><</code>List<code>></code>
     */
    Result<List> deleteMedicalDeviceByBarcode( String barcode, String FILE_PATH);
    /**
     * Updating medicalDevice object in the datasource if an element with this barcode is already present it is replaced otherwise a new one is added
     *
     * @param medicalDevice
     * @param FILE_PATH
     * @return Result <code><</code>List<code>></code>
     */
    Result<List> updateMedicalDevice(MedicalDevice medicalDevice, String FILE_PATH);
    /**
     * Getting a medicalDevice object from a datasource by barcode
     *
     * @param barcode
     * @param FILE_PATH
     * @return Result
     */
    Result getMedicalDeviceByBarcode(String barcode, String FILE_PATH);
    /**
     * Searching PharmacyProduct objects in the categoryUnidirectional from datasource
     *
     * @param category
     * @return List <code><</code>Meddicine<code>></code>
     */
    List<PharmacyProduct> searchPharmacyProductByCategory(String category);
    /**
     * Searching PharmacyProduct objects from datasource by name
     *
     * @param nameOfPharmacyProduct
     * @return List <code><</code>Meddicine<code>></code>
     */
    List<PharmacyProduct> searchPharmacyProductByName(String nameOfPharmacyProduct);
    /**
     * Getting the analogues of medicineBidirectional from datasource
     *
     * @param meddicineName
     * @return List <code><</code>Meddicine<code>></code>
     */
    List<Meddicine> getTheAnaloguesOfMedicine(String meddicineName);
    /**
     * Getting the analogues of medicineBidirectional by barcode from datasource
     *
     * @param barcode
     * @return List <code><</code>Meddicine<code>></code>
     */
    List<Meddicine> getTheAnaloguesByBarcode(String barcode);


}
