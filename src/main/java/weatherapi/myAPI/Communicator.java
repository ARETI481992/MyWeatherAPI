package weatherapi.myAPI;

import weatherapi.handlers.CurrentForecastHandler;
import weatherapi.handlers.DailyForecastHandler;
import weatherapi.handlers.HourlyForecastHandler;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;


import com.maxmind.geoip2.exception.GeoIp2Exception;

public class Communicator {
	private String api_key;

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
	}
	
	public ArrayList<Forecast> getDailyForecast() {
		// daily forecast
		ArrayList<Forecast> dailyForecast =  getForecastByCityName("London", 0);
		for(int i=0; i<dailyForecast.size(); i++) {
			int dt = dailyForecast.get(i).getDt();
			
			java.util.Date time=new java.util.Date((long)dt*1000);
			
			//System.out.println(time);
			
			//TODO Print results
		}
		
		return dailyForecast;
	}
	
	public ArrayList<Forecast> getHourlyForecast() {
		// hourly forecast
		ArrayList<Forecast> hourlyForecast = getForecastByCityName("London", 1);
		for(int i=0; i<hourlyForecast.size(); i++) {
			int dt = hourlyForecast.get(i).getDt();
			
			java.util.Date time=new java.util.Date((long)dt*1000);
			
			//System.out.println(time);
			
			//TODO Print results
		}
		
		return hourlyForecast;
	}
	
	public ArrayList<Forecast> getCurrentForecast() {
		// current forecast
		// get the city coordinates based on the machine's ip
		GeoLocator geoloc = new GeoLocator();
		CityObject cityObj = geoloc.fetchCoordinatesBasedOnIP();
		
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

}