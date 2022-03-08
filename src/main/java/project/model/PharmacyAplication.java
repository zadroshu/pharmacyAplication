package project.model;

import org.apache.commons.cli.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import project.api.DataProviderCsv;
import project.api.DataProviderH2;
import project.api.DataProviderXml;
import project.utils.Constant;

import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.List;

import static project.model.Barcode.readBarcode;


public class PharmacyAplication {
    private static Logger log = LogManager.getLogger(PharmacyAplication.class.getName());

    public static void main(String[] args) {

        CommandLineParser parser = new DefaultParser();
        Options options = new Options();

        Option help = new Option("h", "help", false, "Help message");
        Option createOrder = new Option("o", "createOrder", true, "Create new order");

        createOrder.setArgs(3);
        createOrder.setOptionalArg(false);
        createOrder.setArgName("userId> <pharmacyProductName> <dataProvider");

        Option getAnaloguesMeddicine = new Option("g", "getAnaloguesMeddicine", true, "Getting the analogues of meddicine");
        getAnaloguesMeddicine.setArgs(3);
        getAnaloguesMeddicine.setOptionalArg(false);
        getAnaloguesMeddicine.setArgName("meddicineName/barcode> <dataProvider> <sort");

        Option getAnaloguesMeddicineBybarcode = new Option("b", "getAnaloguesMeddicineBybarcode", true, "Getting the analogues of meddicine by barcode, 'a' - sort max to min 'b' - sort min to max 'any other symbol - no sort'" );
        getAnaloguesMeddicineBybarcode.setArgs(3);
        getAnaloguesMeddicineBybarcode.setOptionalArg(false);
        getAnaloguesMeddicineBybarcode.setArgName("filePathBarcodeImg> <dataProvider> <sort");

        Option searchPharmacyProductByCategory = new Option("c", "searchPharmacyProductByCategory", true, "Searching pharmacy product by category, 'a' - sort max to min 'b' - sort min to max 'any other symbol - no sort");
        searchPharmacyProductByCategory.setArgs(3);
        searchPharmacyProductByCategory.setOptionalArg(false);
        searchPharmacyProductByCategory.setArgName("category> <dataProvider> <sort");

        Option searchPharmacyProductByName = new Option("n", "searchPharmacyProductByName", true, "Searching pharmacy product by name, 'a' - sort max to min 'b' - sort min to max 'any other symbol - no sort");
        searchPharmacyProductByName.setArgs(3);
        searchPharmacyProductByName.setOptionalArg(false);
        searchPharmacyProductByName.setArgName("nameOfPharmacyProduct> <dataProvider> <sort");

        options.addOption(help);
        options.addOption(createOrder);
        options.addOption(getAnaloguesMeddicine);
        options.addOption(getAnaloguesMeddicine);
        options.addOption(getAnaloguesMeddicineBybarcode);
        options.addOption(searchPharmacyProductByCategory);
        options.addOption(searchPharmacyProductByName);

        try {
            DataProviderCsv dataProviderCsv = new DataProviderCsv();
            DataProviderXml dataProviderXml = new DataProviderXml();
            DataProviderH2 dataProviderH2 = new DataProviderH2();

            CommandLine cmd = parser.parse(options, args);

            if (cmd.hasOption(Constant.CLI_HELP)) {
                printHelp(
                        options,
                        150,
                        "Options",
                        "----------------------------------HELP---------------------------------------",
                        3,
                        5,
                        true,
                        System.out
                );
            }

            if (cmd.hasOption(Constant.CLI_GET_ANALAGUES_MEDDICINE)) {
                String[] arguments = cmd.getOptionValues(Constant.CLI_GET_ANALAGUES_MEDDICINE);

                if (arguments[1].equals("CSV")) {
                    List<Meddicine> meddicineList = dataProviderCsv.getTheAnaloguesOfMedicine(arguments[0]);
                    if (!meddicineList.isEmpty()) {
                        if (arguments[2].equals("a")) {
                            Collections.sort(meddicineList, new SortMeddicineMaxToMin());
                        } else if (arguments[2].equals("d")) {
                            Collections.sort(meddicineList, new SortMeddicineMinToMax());
                        }
                        for (Meddicine meddicine : meddicineList) {
                            log.info("\nBarcode: " + meddicine.getBarcode() + "\n" +
                                    "Name: " + meddicine.getNameOfPharmacyProduct() + "\n" +
                                    "Price: " + meddicine.getPrice() + "\n" +
                                    "Category of the medicine: " + meddicine.getCategoryOfTheMeddicine() + "\n" +
                                    "Active substance: " + meddicine.getActiveSubstanceOfTheMeddicine() + "\n" +
                                    "Description: " + meddicine.getDescription() + "\n");
                        }
                    } else log.info("No such medicine");
                } else if (arguments[1].equals("XML")) {
                    List<Meddicine> meddicineList = dataProviderXml.getTheAnaloguesOfMedicine(arguments[0]);
                    if (!meddicineList.isEmpty()) {
                        if (arguments[2].equals("a")) {
                            Collections.sort(meddicineList, new SortMeddicineMaxToMin());
                        } else if (arguments[2].equals("d")) {
                            Collections.sort(meddicineList, new SortMeddicineMinToMax());
                        }
                        for (Meddicine meddicine : meddicineList) {
                            log.info("\nBarcode: " + meddicine.getBarcode() + "\n" +
                                    "Name: " + meddicine.getNameOfPharmacyProduct() + "\n" +
                                    "Price: " + meddicine.getPrice() + "\n" +
                                    "Category of the medicine: " + meddicine.getCategoryOfTheMeddicine() + "\n" +
                                    "Active substance: " + meddicine.getActiveSubstanceOfTheMeddicine() + "\n" +
                                    "Description: " + meddicine.getDescription() + "\n");
                        }
                    } else log.info("No such medicine");
                } else if (arguments[1].equals("H2")) {
                    List<Meddicine> meddicineList = dataProviderH2.getTheAnaloguesOfMedicine(arguments[0]);
                    if (!meddicineList.isEmpty()) {
                        if (arguments[2].equals("a")) {
                            Collections.sort(meddicineList, new SortMeddicineMaxToMin());
                        } else if (arguments[2].equals("d")) {
                            Collections.sort(meddicineList, new SortMeddicineMinToMax());
                        }
                        for (Meddicine meddicine : meddicineList) {
                            log.info("\nBarcode: " + meddicine.getBarcode() + "\n" +
                                    "Name: " + meddicine.getNameOfPharmacyProduct() + "\n" +
                                    "Price: " + meddicine.getPrice() + "\n" +
                                    "Category of the medicine: " + meddicine.getCategoryOfTheMeddicine() + "\n" +
                                    "Active substance: " + meddicine.getActiveSubstanceOfTheMeddicine() + "\n" +
                                    "Description: " + meddicine.getDescription() + "\n");
                        }
                    } else log.info("No such medicine");
                } else {
                    log.info("Incorrect DataProvider");
                }
            } else if (cmd.hasOption(Constant.CLI_GET_ANALAGUES_MEDDICINE_BY_BARCODE)) {
                String[] arguments = cmd.getOptionValues(Constant.CLI_GET_ANALAGUES_MEDDICINE_BY_BARCODE);
                String barcode = readBarcode(Constant.PATHPROG, arguments[0]);

                if (arguments[1].equals("CSV")) {
                    List<Meddicine> meddicineList = dataProviderCsv.getTheAnaloguesByBarcode(barcode);
                    if (!meddicineList.isEmpty()) {
                        if (arguments[2].equals("a")) {
                            Collections.sort(meddicineList, new SortMeddicineMaxToMin());
                        } else if (arguments[2].equals("d")) {
                            Collections.sort(meddicineList, new SortMeddicineMinToMax());
                        }
                        for (Meddicine meddicine : meddicineList) {
                            log.info("\nBarcode: " + meddicine.getBarcode() + "\n" +
                                    "Name: " + meddicine.getNameOfPharmacyProduct() + "\n" +
                                    "Price: " + meddicine.getPrice() + "\n" +
                                    "Category of the medicine: " + meddicine.getCategoryOfTheMeddicine() + "\n" +
                                    "Active substance: " + meddicine.getActiveSubstanceOfTheMeddicine() + "\n" +
                                    "Description: " + meddicine.getDescription() + "\n");
                        }
                    } else log.info("No such medicine");
                } else if (arguments[1].equals("XML")) {
                    List<Meddicine> meddicineList = dataProviderXml.getTheAnaloguesByBarcode(barcode);
                    if (!meddicineList.isEmpty()) {
                        if (arguments[2].equals("a")) {
                            Collections.sort(meddicineList, new SortMeddicineMaxToMin());
                        } else if (arguments[2].equals("d")) {
                            Collections.sort(meddicineList, new SortMeddicineMinToMax());
                        }
                        for (Meddicine meddicine : meddicineList) {
                            log.info("\nBarcode: " + meddicine.getBarcode() + "\n" +
                                    "Name: " + meddicine.getNameOfPharmacyProduct() + "\n" +
                                    "Price: " + meddicine.getPrice() + "\n" +
                                    "Category of the medicine: " + meddicine.getCategoryOfTheMeddicine() + "\n" +
                                    "Active substance: " + meddicine.getActiveSubstanceOfTheMeddicine() + "\n" +
                                    "Description: " + meddicine.getDescription() + "\n");
                        }
                    } else log.info("No such medicine");
                } else if (arguments[1].equals("H2")) {
                    List<Meddicine> meddicineList = dataProviderH2.getTheAnaloguesByBarcode(barcode);
                    if (!meddicineList.isEmpty()) {
                        if (arguments[2].equals("a")) {
                            Collections.sort(meddicineList, new SortMeddicineMaxToMin());
                        } else if (arguments[2].equals("d")) {
                            Collections.sort(meddicineList, new SortMeddicineMinToMax());
                        }
                        for (Meddicine meddicine : meddicineList) {
                            log.info("\nBarcode: " + meddicine.getBarcode() + "\n" +
                                    "Name: " + meddicine.getNameOfPharmacyProduct() + "\n" +
                                    "Price: " + meddicine.getPrice() + "\n" +
                                    "Category of the medicine: " + meddicine.getCategoryOfTheMeddicine() + "\n" +
                                    "Active substance: " + meddicine.getActiveSubstanceOfTheMeddicine() + "\n" +
                                    "Description: " + meddicine.getDescription() + "\n");
                        }
                    } else log.info("No such medicine");
                } else {
                    log.info("Incorrect DataProvider");
                }
            } else if (cmd.hasOption(Constant.CLI_SEARCH_PHARMACY_PRODUCT_BY_CATEGORY)) {
                String[] arguments = cmd.getOptionValues(Constant.CLI_SEARCH_PHARMACY_PRODUCT_BY_CATEGORY);

                if (arguments[1].equals("CSV")) {
                    List<PharmacyProduct> pharmacyProductList = dataProviderCsv.searchPharmacyProductByCategory(arguments[0]);
                    if (!pharmacyProductList.isEmpty()) {
                       pharmacyProductList = dataProviderCsv.sortPharmacyProduct(pharmacyProductList, arguments[2]);
                        for (PharmacyProduct pharmacyProduct : pharmacyProductList) {
                            log.info("\nBarcode: " + pharmacyProduct.getBarcode() + "\n" +
                                    "Name: " + pharmacyProduct.getNameOfPharmacyProduct() + "\n" +
                                    "Price: " + pharmacyProduct.getPrice() + "\n" +
                                    "Description: " + pharmacyProduct.getDescription() + "\n");
                        }
                    } else {
                        log.info("No such element");
                    }
                } else if (arguments[1].equals("XML")) {
                    List<PharmacyProduct> pharmacyProductList = dataProviderXml.searchPharmacyProductByCategory(arguments[0]);
                    if (!pharmacyProductList.isEmpty()) {
                        pharmacyProductList = dataProviderXml.sortPharmacyProduct(pharmacyProductList, arguments[2]);
                        for (PharmacyProduct pharmacyProduct : pharmacyProductList) {
                            log.info("\nBarcode: " + pharmacyProduct.getBarcode() + "\n" +
                                    "Name: " + pharmacyProduct.getNameOfPharmacyProduct() + "\n" +
                                    "Price: " + pharmacyProduct.getPrice() + "\n" +
                                    "Description: " + pharmacyProduct.getDescription() + "\n");
                        }
                    } else {
                        log.info("No such element");
                    }
                } else if (arguments[1].equals("H2")) {
                    List<PharmacyProduct> pharmacyProductList = dataProviderH2.searchPharmacyProductByCategory(arguments[0]);
                    if (!pharmacyProductList.isEmpty()) {
                        pharmacyProductList = dataProviderH2.sortPharmacyProduct(pharmacyProductList, arguments[2]);
                        for (PharmacyProduct pharmacyProduct : pharmacyProductList) {
                            log.info("\nBarcode: " + pharmacyProduct.getBarcode() + "\n" +
                                    "Name: " + pharmacyProduct.getNameOfPharmacyProduct() + "\n" +
                                    "Price: " + pharmacyProduct.getPrice() + "\n" +
                                    "Description: " + pharmacyProduct.getDescription() + "\n");
                        }
                    } else {
                        log.info("No such element");
                    }
                } else {
                    log.info("Incorrect DataProvider");
                }
            } else if (cmd.hasOption(Constant.CLI_SEARCH_PHARMACY_PRODUCT_BY_NAME)) {
                String[] arguments = cmd.getOptionValues(Constant.CLI_SEARCH_PHARMACY_PRODUCT_BY_NAME);

                if (arguments[1].equals("CSV")) {
                    List<PharmacyProduct> pharmacyProductList = dataProviderCsv.searchPharmacyProductByName(arguments[0]);
                    if (!pharmacyProductList.isEmpty()) {
                        pharmacyProductList = dataProviderCsv.sortPharmacyProduct(pharmacyProductList, arguments[2]);
                        for (PharmacyProduct pharmacyProduct : pharmacyProductList) {
                            log.info("\nBarcode: " + pharmacyProduct.getBarcode() + "\n" +
                                    "Name: " + pharmacyProduct.getNameOfPharmacyProduct() + "\n" +
                                    "Price: " + pharmacyProduct.getPrice() + "\n" +
                                    "Description: " + pharmacyProduct.getDescription() + "\n");
                        }
                    } else {
                        log.info("No such element");
                    }
                } else if (arguments[1].equals("XML")) {
                    List<PharmacyProduct> pharmacyProductList = dataProviderXml.searchPharmacyProductByName(arguments[0]);
                    if (!pharmacyProductList.isEmpty()) {
                        pharmacyProductList = dataProviderXml.sortPharmacyProduct(pharmacyProductList, arguments[2]);
                        for (PharmacyProduct pharmacyProduct : pharmacyProductList) {
                            log.info("\nBarcode: " + pharmacyProduct.getBarcode() + "\n" +
                                    "Name: " + pharmacyProduct.getNameOfPharmacyProduct() + "\n" +
                                    "Price: " + pharmacyProduct.getPrice() + "\n" +
                                    "Description: " + pharmacyProduct.getDescription() + "\n");
                        }
                    } else {
                        log.info("No such element");
                    }
                } else if (arguments[1].equals("H2")) {
                    List<PharmacyProduct> pharmacyProductList = dataProviderH2.searchPharmacyProductByName(arguments[0]);
                    if (!pharmacyProductList.isEmpty()) {
                        pharmacyProductList = dataProviderH2.sortPharmacyProduct(pharmacyProductList, arguments[2]);
                        for (PharmacyProduct pharmacyProduct : pharmacyProductList) {
                            log.info("\nBarcode: " + pharmacyProduct.getBarcode() + "\n" +
                                    "Name: " + pharmacyProduct.getNameOfPharmacyProduct() + "\n" +
                                    "Price: " + pharmacyProduct.getPrice() + "\n" +
                                    "Description: " + pharmacyProduct.getDescription() + "\n");
                        }
                    } else {
                        log.info("No such element");
                    }
                } else {
                    log.info("Incorrect DataProvider");
                }
            }else if (cmd.hasOption(Constant.CLI_CREATE_ORDER)){
                String[] arguments = cmd.getOptionValues(Constant.CLI_CREATE_ORDER);

                if (arguments[2].equals("CSV")){
                    log.info(dataProviderCsv.createOrder(Long.parseLong(arguments[0]), arguments[1]));
                }
                else if (arguments[2].equals("XML")){
                    log.info(dataProviderXml.createOrder(Long.parseLong(arguments[0]), arguments[1]));
                }
                else if (arguments[2].equals("H2")){
                    log.info(dataProviderH2.createOrder(Long.parseLong(arguments[0]), arguments[1]));
                }else {
                    log.info("Incorrect DataProvider");
                }
            }


        } catch (Exception e) {
            log.error("Exception received {} ".concat(e.getMessage()));
            printHelp(
                    options,
                    120,
                    "Options",
                    "----------------------------------HELP---------------------------------------",
                    3,
                    5,
                    true,
                    System.out
            );
            e.printStackTrace();
        }


    }

    public static void printHelp(
            final Options options,
            final int printedRowWidth,
            final String header,
            final String footer,
            final int spacesBeforeOption,
            final int spacesBeforeOptionDescription,
            final boolean displayUsage,
            final OutputStream out) {
        final String commandLineSyntax = "java test.jar";//подсказка по запуску самой программы
        final PrintWriter writer = new PrintWriter(out);// куда печатаем help
        final HelpFormatter helpFormatter = new HelpFormatter();// создаем объект для вывода help`а
        helpFormatter.printHelp(
                writer,
                printedRowWidth,
                commandLineSyntax,
                header,
                options,
                spacesBeforeOption,
                spacesBeforeOptionDescription,
                footer,
                displayUsage);//формирование справки
        writer.flush(); // вывод
    }
}
