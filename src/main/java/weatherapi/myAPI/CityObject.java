package weatherapi.myAPI;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import kong.unirest.json.JSONObject;

/*
 * Periexei plhrofories gia tis poleis tou xrhsth.
 */
@SuppressWarnings("serial")
public class CityObject implements Serializable{
	//monadiko id pou orizei to OpenWeatherMap gia kathe polh
    private Integer id;
    //to onoma
    private String name;
    //gewgrafikes syntetagmenes
    private Coordination coord;
    //xwra
    private String country;
    //antikeimeno mesw tou opoiou mporoume na paroume merikes extra
    // plirofories gia thn polh opws px h xwra
    private Map<String,String> sys = new HashMap<>();
    
    /*
     * pairnei ena json object kai eksagei plirofories gia thn polh/topothesia
     */
    public void setCityDetails(JSONObject jsonResponse) {
    	coord = new Coordination();
		JSONObject jsonCoords = (JSONObject) jsonResponse.get("coord");
		
		coord.setCoordinates(jsonCoords);
				
		setName((String) jsonResponse.get("name"));
		setId((Integer) jsonResponse.get("id"));
		JSONObject sys = (JSONObject) jsonResponse.get("sys");
		setCountry((String) sys.get("country"));
    }
    
    /*
     * getters kai setters
     */

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
