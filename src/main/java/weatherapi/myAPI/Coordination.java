package weatherapi.myAPI;

import java.io.Serializable;

import kong.unirest.json.JSONObject;

public class Coordination implements Serializable{
	private Double lon;
	private Double lat;
	
	public Coordination(Double lon, Double lat) {
		this.lon = lon;
		this.lat = lat;
	}
	
	public Coordination() {
	}
	
	public void setCoordinates(JSONObject jsonObj) {
		lon = (Double) jsonObj.get("lon");
		lat = (Double) jsonObj.get("lat");
	}
	
	public Double getLon() {
		return lon;
	}
	
	public Double getLat() {
		return lat;
	}
}
