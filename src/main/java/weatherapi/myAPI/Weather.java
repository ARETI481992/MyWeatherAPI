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
    	setId((Integer) jsonObj.get("id"));
    	main = (String) jsonObj.get("main");
    	setDescription((String) jsonObj.get("description"));
    	icon = (String) jsonObj.get("icon");    	
    }
    
    public String getMain() {
    	return main;
    }

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

}
