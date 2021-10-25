package weatherapi.myAPI;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import kong.unirest.json.JSONObject;


public class CityObject implements Serializable{
    private Integer id;
    private String name;
    private Coordination coord;
    private String country;
    private Map<String,String> sys = new HashMap<>();
    
    public void setCityDetails(JSONObject jsonResponse) {
    	coord = new Coordination();
		JSONObject jsonCoords = (JSONObject) jsonResponse.get("coord");
		
		coord.setCoordinates(jsonCoords);
				
		setName((String) jsonResponse.get("name"));
		setId((Integer) jsonResponse.get("id"));
		JSONObject sys = (JSONObject) jsonResponse.get("sys");
		setCountry((String) sys.get("country"));
    }

    public Map<String, String> getSys() {
        return sys;
    }

    public void setSys(Map<String, String> sys) {
        this.sys = sys;
    }
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Coordination getCoord() {
        return coord;
    }

    public void setCoord(Coordination coord) {
        this.coord = coord;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
    
}
