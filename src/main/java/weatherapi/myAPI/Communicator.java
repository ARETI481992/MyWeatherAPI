package weatherapi.myAPI;

import weatherapi.handlers.CurrentForecastHandler;
import weatherapi.handlers.DailyForecastHandler;
import weatherapi.handlers.HourlyForecastHandler;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import console.backend.Controller;
import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import kong.unirest.json.JSONArray;
import kong.unirest.json.JSONObject;

public class Communicator {
	private String api_key;
	private Controller recorder;
	

	public Communicator(){
		String api_key_file = "api-key";
		InputStream is = Communicator.class.getClassLoader().getResourceAsStream(api_key_file);
		
		StringBuilder sb = new StringBuilder();
		try {
			for (int ch; (ch = is.read()) != -1; ) {
			    sb.append((char) ch);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("ERROR: Missing OpenWeatherMap API key file from resources.");
			e.printStackTrace();
		}
		api_key = sb.toString();
		
		//recorder signleton object
		recorder = Controller.getInstance();
	}
	
	public ArrayList<Forecast> getDailyForecast() {
		// daily forecast
		ArrayList<Forecast> dailyForecast =  getForecastByCityName(Controller.getInstance().getCurrentCity().getName(), 0);
		for(int i=0; i<dailyForecast.size(); i++) {
			int dt = dailyForecast.get(i).getDt();
			
			java.util.Date time=new java.util.Date((long)dt*1000);
		}
		
		return dailyForecast;
	}
	
	public ArrayList<Forecast> getHourlyForecast() {
		// hourly forecast
		ArrayList<Forecast> hourlyForecast = getForecastByCityName(Controller.getInstance().getCurrentCity().getName(), 1);
		for(int i=0; i<hourlyForecast.size(); i++) {
			int dt = hourlyForecast.get(i).getDt();			
			java.util.Date time=new java.util.Date((long)dt*1000);
		}
		
		return hourlyForecast;
	}
	
	public ArrayList<Forecast> getCurrentForecast() {
		// current forecast
		// get the city coordinates based on the machine's ip		
		CityObject cityObj = Controller.getInstance().getCurrentCity();
		
		//get the forecast for the city's coordinates
		ArrayList<Forecast> currentForecast = getForecastByCityCoordinates(cityObj.getCoord().getLon(), cityObj.getCoord().getLat(), 3);
		
		return currentForecast;
	}
	

	private ArrayList<Forecast> getForecastByCityName(String place, int type) {
		if(type == 0) {
			// daily forecast
			DailyForecastHandler dailyForecast = new DailyForecastHandler(api_key);
			
			return(dailyForecast.getResponse(place));
		}else if(type == 1) {
			// hourly forecast
			HourlyForecastHandler hourlyForecast = new HourlyForecastHandler(api_key);
			
			return(hourlyForecast.getResponse(place));
		}
		
		return null;
    }
	
	private ArrayList<Forecast> getForecastByCityCoordinates(Double longtitude, Double latitude, int type){
		if(type == 3) {
			//current weather
			CurrentForecastHandler currentForecast = new CurrentForecastHandler(api_key);
			
			return(currentForecast.getResponse(longtitude, latitude));
		}
		return null;
	}
	
	public String getAPIKey() {
		return api_key;
	}
	
	public void setUnits(String units) {
		recorder.setcurrentUnits(units);
	}
	
	public CityObject requestCity(String place) {
		//get city information
		int responseCode;
		String query = "https://api.openweathermap.org/data/2.5/weather";
		query += "?q=" + place;
		query += "&appid="+api_key;
				
		HttpResponse<String> httpResponse = Unirest.get(query).asString();
		JSONObject jsonResponse = new JSONObject(httpResponse.getBody());
		
		if(jsonResponse.get("cod").getClass().getSimpleName() == Integer.class.getSimpleName()) {
			responseCode = (int) jsonResponse.get("cod");
		}else {
			responseCode = (int) Integer.parseInt((String) jsonResponse.get("cod"));
		}
		
		if(responseCode == 200) {
			CityObject city = new CityObject();
			city.setCityDetails(jsonResponse);			
			return city;
		}

		return null;
	}

}
