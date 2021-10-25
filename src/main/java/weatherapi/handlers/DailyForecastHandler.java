package weatherapi.handlers;

import java.util.ArrayList;
import java.util.List;

import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import kong.unirest.json.JSONArray;
import kong.unirest.json.JSONObject;
import weatherapi.myAPI.CityObject;
import weatherapi.myAPI.DailyForecast;
import weatherapi.myAPI.Forecast;

public class DailyForecastHandler extends RequestHandler {	
	private List<Forecast> dailyForecast;
	private int responseCode;
	
	public DailyForecastHandler(String key) {
		setAPIKey(key);
		dailyForecast = new ArrayList<Forecast>();
	}
	
	@Override
	public ArrayList<Forecast> getResponse(String place) {
		int i;
		//get city information		
		String query = "https://api.openweathermap.org/data/2.5/weather";
		query += "?q=" + place;
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
			query += "&exlude=minutely,hourly,alert";
			query += "&units=metric";
			query += "&appid="+api_key;
			
			httpResponse = Unirest.get(query).asString();
			jsonResponse = new JSONObject(httpResponse.getBody());
			
			JSONArray daily = (JSONArray) jsonResponse.get("daily");
			
			for(i=1; i<6; i++) {
				JSONObject dayObj = (JSONObject) daily.get(i);
				Forecast day = new DailyForecast(dayObj);
				dailyForecast.add(day);
			}
		}
		
		return (ArrayList<Forecast>) dailyForecast;
	}

	@Override
	public ArrayList<Forecast> getResponse(Double lon, Double lat) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public int getResponseCode() {
		return responseCode;
	}
	
	public void setResponseCode(int responseCode) {
		this.responseCode = responseCode;
	}
	

}
