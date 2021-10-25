package weatherapi.myAPI;

import java.io.Serializable;
import kong.unirest.json.JSONObject;

/*
 * antikeimeno kairou.
 * Exei plirofories pou perigrafoun ton kairo.
 */
@SuppressWarnings("serial")
public class Weather implements Serializable{
	// to id tou kairou
	private Integer id;
	//kyria perigrafh p.x. rain
    private String main;
    //epipleon sxolia gia ton kairo p.x. light/heavy rain
    private String description;
    //eikonidio (gia web apps)
    private String icon; 

    /*
     * dhmiourgei to Weather object apo katallilo json object
     */
    public Weather(JSONObject jsonObj) {
    	setId((Integer) jsonObj.get("id"));
    	main = (String) jsonObj.get("main");
    	setDescription((String) jsonObj.get("description"));
    	icon = (String) jsonObj.get("icon");    	
    }
    
    
    /*
     * getters kai setters
     */
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
