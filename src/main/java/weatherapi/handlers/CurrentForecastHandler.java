package weatherapi.handlers;

import java.util.ArrayList;
import java.util.List;

import console.backend.Controller;
import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import kong.unirest.json.JSONArray;
import kong.unirest.json.JSONObject;
import weatherapi.myAPI.CityObject;
import weatherapi.myAPI.Forecast;
import weatherapi.myAPI.HourlyForecast;

public class CurrentForecastHandler extends RequestHandler{
	private List<Forecast> currentForecast;
	private int responseCode;
	
	public CurrentForecastHandler(String key) {
		setAPIKey(key);
		currentForecast = new ArrayList<Forecast>();
	}
	
	@Override
	public ArrayList<Forecast> getResponse(String place) {
		return null;
		
		
	}

	@Override
	public ArrayList<Forecast> getResponse(Double lon, Double lat) {
		int i;
		//get city information		
		String query = "https://api.openweathermap.org/data/2.5/weather";
		query += "?lat=" + lat + "&" + "lon=" + lon;
		query += "&appid="+api_key;
		
		HttpResponse<String> httpResponse = Unirest.get(query).asString();
		JSONObject jsonResponse = new JSONObject(httpResponse.getBody());
		
		if(jsonResponse.get("cod").getClass().getSimpleName() == Integer.class.getSimpleName()) {
			setResponseCode((int) jsonResponse.get("cod"));
		}else {
			setResponseCode((int) Integer.parseInt((String) jsonResponse.get("cod")));
		}
		
		if(responseCode == 200) {
			CityObject city = new CityObject();
			city.setCityDetails(jsonResponse);
			
			//get daily forecast of city based on lon/lat coordinates
			query = "https://api.openweathermap.org/data/2.5/onecall";
			query += "?lat=" + city.getCoord().getLat() + "&lon=" + city.getCoord().getLon();
			query += "&exlude=hourly,daily,alert";
			query += "&units="+Controller.getInstance().getcurrentUnits();
			query += "&appid="+api_key;
			
			httpResponse = Unirest.get(query).asString();
			jsonResponse = new JSONObject(httpResponse.getBody());
						
			if(jsonResponse.length() == 2) {
				//request failed
				System.out.println("!Failed to retrieve data. This is OpenWatherMap's problem");
				return (ArrayList<Forecast>) currentForecast;
			}
			
			JSONObject current = (JSONObject) jsonResponse.get("current");			
			Forecast day = new HourlyForecast(current);
			currentForecast.add(day);			
		}
		return (ArrayList<Forecast>) currentForecast;
	}

	public int getResponseCode() {
		return responseCode;
	}

	public void setResponseCode(int responseCode) {
		this.responseCode = responseCode;
	}


}
