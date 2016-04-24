package backend.dataReader;

import android.util.Log;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.Date;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.JsonValue;

import backend.configuration.ConfigurationManager;
import backend.data.RealTimeEventsDataObject;
import backend.data.ZapDataObject;

/*
 * For Gradle, add this to your build.gradle
 * compile 'javax.json:javax.json-api:1.0'
 * 
 * For desktop Eclipse,
 * http://www.java2s.com/Code/Jar/j/Downloadjavaxjson102jar.htm
 * 
 * 
 * THIS CODE *MIGHT* NEED TO BE REWRITTEN
 * TO MAKE USE OF ANDROID LIBRARIES ?????
 * */


//http://www.java2s.com/Tutorials/Java/JSON/0100__JSON_Java.htm

/**
 * Class to obtain data from the Zap location webservice
 * and populate an array list of ZapDataObjects.
 */
public class ZapReader {
    String jsonData = "";
    private static int field_id = 0;
    private static int field_name = 1;
    private static int field_description = 2;
    private static int field_civic_number = 3;
    private static int field_street_name = 4;
    private static int field_city = 5;
    private static int field_province = 6;
    private static int field_country = 7;
    private static int field_postal_code = 8;
    private static int field_public_phone_number = 9;
    private static int field_public_email = 10;
    private static int field_latitude = 11;
    private static int field_longitude = 12;
    private static final String TAG = "";
    /**
     * This method obtains the data from the
     * Zap location webservice, in the form
     * of a csv in a String and then
     * places it in an array list of ZapDataObjects
     * <p/>
     * receives:	String csvData
     * returns:		ArrayList<ZapDataObject>
     */
    public ArrayList<ZapDataObject> getZapDataObjectList(String csvData) {
        ArrayList<ZapDataObject> zdoList = new ArrayList<ZapDataObject>();

        String[] csvLines = csvData.split("\n");
        Log.d(TAG, "CsvLines Length: " + csvLines.length);
        for (int i = 1; i < csvLines.length - 1; i++) {
            String[] fields = csvLines[i].split(",");
            ZapDataObject zdo = new ZapDataObject();
            zdo.setId(fields[field_id]);
            zdo.setName(fields[field_name]);

            zdo.setDescription(fields[field_description]);
            zdo.setCivic_number(fields[field_civic_number]);

            zdo.setStreet_name(fields[field_street_name]);
            zdo.setCity(fields[field_city]);
            zdo.setProvince(fields[field_province]);

            zdo.setCountry(fields[field_country]);

            zdo.setPostal_code(fields[field_postal_code]);
            zdo.setPublic_phone_number(fields[field_public_phone_number]);
            zdo.setPublic_email(fields[field_public_email]);


            zdo.setLatitude(fields[field_latitude]);
            zdo.setLongitude(fields[field_longitude]);

            zdoList.add(zdo);
        }

        return zdoList;
    }


}
