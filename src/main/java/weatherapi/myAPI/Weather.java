package weatherapi.myAPI;

import java.io.Serializable;

import kong.unirest.json.JSONArray;
import kong.unirest.json.JSONObject;

public class Weather implements Serializable{
	private Integer id;
    private String main; 
    private String description;
    private String icon; 

    public Weather(JSONObject jsonObj) {
    	id = (Integer) jsonObj.get("id");
    	main = (String) jsonObj.get("main");
    	description = (String) jsonObj.get("description");
    	icon = (String) jsonObj.get("icon");    	
    }
    
    public String getMain() {
    	return main;
    }

}
