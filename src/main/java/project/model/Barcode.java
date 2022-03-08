package project.model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import project.api.DataProviderXml;

import javax.sql.rowset.serial.SerialStruct;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

public class Barcode {
    private static Logger log = LogManager.getLogger(Barcode.class.getName());

    static String barcode;

    public Barcode() {
    }

    public static String readBarcode(String zBarPath, String barcodeJPGFilePath) {
        try {
            log.info("readBarcode[1] {}");
            Process p = Runtime.getRuntime().exec(zBarPath + " " + barcodeJPGFilePath);
            p.waitFor();
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(p.getInputStream())
            );
            log.info("readBarcode[2] {}");
            String line = reader.readLine();
            while (line != null) {
                barcode = line;
                line = reader.readLine();
            }
            log.info("readBarcode[3] {}");

        } catch (IOException e1) {
            log.info("readBarcode[4] {} ".concat(e1.getMessage()));
            e1.printStackTrace();
        } catch (InterruptedException e2) {
            log.info("readBarcode[5] {} ".concat(e2.getMessage()));
            e2.printStackTrace();
        }

        System.out.println(barcode);
        return barcode;
    }

    public static Logger getLog() {
        return log;
    }

    public static void setLog(Logger log) {
        Barcode.log = log;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Barcode barcode1 = (Barcode) o;
        return Objects.equals(barcode, barcode1.barcode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(barcode);
    }

    @Override
    public String
    toString() {
        return "Barcode{" +
                "barcode='" + barcode + '\'' +
                '}';
    }
}
