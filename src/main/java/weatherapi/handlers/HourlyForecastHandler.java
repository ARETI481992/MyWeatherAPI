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
/*
 * Xeirizetai ta reuqests gia tis wriaies kairikes synthikes.
 * H logikh ths getResponse() einai idia me to CurrentForecastHandler.java
 */
public class HourlyForecastHandler extends RequestHandler{
	private List<Forecast> hourlyForecast;
	private int responseCode;
	
	public HourlyForecastHandler(String key) {
		setAPIKey(key);
		hourlyForecast = new ArrayList<Forecast>();
	}
	/*
	 * Kanei forecast request vasei tou ONOMATOS ths polhs pou theloume.
	 */
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
			query += "?lat=" + city.getCoord().getLat() + "&lon=" + city.getCoord().getLon();
			query += "&exlude=minutely,daily,alert";
			query += "&units="+Controller.getInstance().getcurrentUnits();
			query += "&appid="+api_key;
			
			httpResponse = Unirest.get(query).asString();
			jsonResponse = new JSONObject(httpResponse.getBody());
			
			//returns a JSON array with 48 elements, one for each hour 
			JSONArray daily = (JSONArray) jsonResponse.get("hourly");
			
			for(i=0; i<daily.length(); i++) {
				JSONObject dayObj = (JSONObject) daily.get(i);
				Forecast day = new HourlyForecast(dayObj);
				hourlyForecast.add(day);
			}
		}
		return (ArrayList<Forecast>) hourlyForecast;
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
