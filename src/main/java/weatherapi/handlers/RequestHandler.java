package weatherapi.handlers;

import java.util.ArrayList;
import java.util.List;

import weatherapi.myAPI.Forecast;

public abstract class RequestHandler {
	String api_key;
	public abstract ArrayList<Forecast> getResponse(String place);
	
	public abstract ArrayList<Forecast> getResponse(Double lon, Double lat);
	
	public void setAPIKey(String key) {
		api_key = key;
	}
	
}
