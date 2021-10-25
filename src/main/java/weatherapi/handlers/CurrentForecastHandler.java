package weatherapi.handlers;

import java.util.ArrayList;
import java.util.List;

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
			query += "?lat=" + city.getCoord().getLon() + "&lon=" + city.getCoord().getLat();
			query += "&exlude=minutely,daily,alert";
			query += "&units=metric";
			query += "&appid="+api_key;
			
			httpResponse = Unirest.get(query).asString();
			jsonResponse = new JSONObject(httpResponse.getBody());
			
			JSONArray daily = (JSONArray) jsonResponse.get("daily");
			
			//current hour
			JSONObject dayObj = (JSONObject) daily.get(0);
			Forecast day = new HourlyForecast(dayObj);
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
