package backend.dataReader;

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

public class RealTimeEventsReader {
    String jsonData = "";

    public ArrayList<RealTimeEventsDataObject> getRealTimeEventsDataObjectList(String jsonData) {
        ArrayList<RealTimeEventsDataObject> rtedoList = new ArrayList<RealTimeEventsDataObject>();

        //String jsonData = WebServiceReader.getStringFromURL(ConfigurationManager.getUrl("realTimeEvents"));

        JsonReader reader = Json.createReader(new StringReader(jsonData));
        JsonObject jsonObject = reader.readObject();
        reader.close();


        JsonObject evts = jsonObject.getJsonObject("EVTS");
        JsonArray evt = evts.getJsonArray("EVT");

        for (JsonValue jsonValue : evt) {
            JsonReader reader2 = Json.createReader(new StringReader(jsonValue.toString()));
            JsonObject jsonObject2 = reader2.readObject();
            reader.close();
            if (jsonObject2 != null) {
                rtedoList.add(generateRealTimeEventDataObject(jsonObject2));
            } else {
                System.out.println("Whis is jsonObject2 null???");

            }
            //System.out.println(jsonObject2.get("MUNID").toString());
        }

        return rtedoList;
    }


    RealTimeEventsDataObject generateRealTimeEventDataObject(JsonObject jo) {
        RealTimeEventsDataObject rteo = new RealTimeEventsDataObject();
        try {
            rteo.setAD_GEN(jo.get("AD_GEN").toString());
            rteo.setAD_LIEN(jo.get("AD_LIEN").toString());
            rteo.setAD_NO(jo.get("AD_NO").toString());
            rteo.setAD_MUN(jo.get("AD_MUN").toString());


            rteo.setCATEG(jo.get("CATEG").toString());
            rteo.setCO(jo.get("CO").toString());
            rteo.setCODEID(jo.get("CODEID").toString());

            rteo.setDESCRIP(jo.get("DESCRIP").toString());
            rteo.setDT01(jo.get("DT01").toString());
            rteo.setDT02(jo.get("DT02").toString());
            rteo.setHR01(jo.get("HR01").toString());
            rteo.setHR02(jo.get("HR02").toString());

            rteo.setLOC(jo.get("LOC").toString());

            rteo.setMUNID(jo.get("MUNID").toString());
            rteo.setTEL1(jo.get("TEL1").toString());
            rteo.setTITRE(jo.get("TITRE").toString());
            rteo.setURL(jo.get("URL").toString());
            rteo.setCATEG(jo.get("CATEG").toString());

            rteo.setDownloadDate(new Date());
            rteo.setSourceURL(ConfigurationManager.getUrl("realTimeEvents"));
        } catch (Exception e) {
            // TODO Auto-generated catch block
            //e.printStackTrace();
            //System.out.println ("skipping");
        }

        return rteo;
    }


}
