package com.springframework.csvtodbapp.helpers;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.web.multipart.MultipartFile;
import com.springframework.csvtodbapp.domain.InventoryData;


public class CSVHelper {
    public static String TYPE = "text/csv";

    public static boolean hasCSVFormat(MultipartFile file) {

        if (!TYPE.equals(file.getContentType())) {
            return false;
        }
        return true;
    }

    public static List<InventoryData> csvToInventories(InputStream is) {
        try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
             CSVParser csvParser = new CSVParser(fileReader,
                     CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());) {

            List<InventoryData> inventoryDataList = new ArrayList<InventoryData>();
            Iterable<CSVRecord> csvRecords = csvParser.getRecords();
            for (CSVRecord csvRecord : csvRecords) {
                InventoryData inventoryData = new InventoryData();

                inventoryData.setCode(csvRecord.get(0));
                inventoryData.setName(csvRecord.get("name"));
                inventoryData.setBatch(csvRecord.get("batch"));
                inventoryData.setStock(Integer.parseInt(csvRecord.get("stock")));
                inventoryData.setDeal(Integer.parseInt(csvRecord.get("deal")));
                inventoryData.setFree(Integer.parseInt(csvRecord.get("free")));
                inventoryData.setMrp(Float.parseFloat(csvRecord.get("mrp")));
                inventoryData.setRate(Float.parseFloat(csvRecord.get("rate")));
                if(csvRecord.get("exp").matches("([0-9]{2})-([0-9]{2})-([0-9]{4})")) {
                    inventoryData.setExpiry(new SimpleDateFormat("dd-MM-yyyy").parse(csvRecord.get("exp")));
                }
                inventoryData.setCompany(csvRecord.get("company"));
                inventoryData.setSupplier(csvRecord.get("supplier"));

                inventoryDataList.add(inventoryData);
            }
            return inventoryDataList;
        } catch (IOException e) {
            throw new RuntimeException("fail to parse CSV file: " + e.getMessage());
        }
        catch(ParseException e){
            throw new RuntimeException();
        }
    }

}

