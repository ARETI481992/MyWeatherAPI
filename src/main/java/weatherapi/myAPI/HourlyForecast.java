package weatherapi.myAPI;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import kong.unirest.json.JSONArray;
import kong.unirest.json.JSONObject;

public class HourlyForecast extends Forecast {
	
	public HourlyForecast(JSONObject forecast) {
    	setDt((Integer) forecast.get("dt"));    	
    	if(forecast.get("temp").getClass().getSimpleName().equals("JSONObject")) {
    		//json obj
    		setTemp(new HourlyTemperature((JSONObject) forecast.get("temp")));
    	}else {
    		if(forecast.get("temp").getClass().getSimpleName().equals("Integer")) {
    			//integer
    			Double d = convertToDouble((Integer) forecast.get("temp"));
    			setTemp(new HourlyTemperature(d));
    		}else {
    			//double
    			setTemp(new HourlyTemperature((Double)forecast.get("temp")));
    		}    		
    	}    	
    	
    	setPressure((Integer) forecast.get("pressure"));
    	
    	setHumidity((Integer) forecast.get("humidity"));
    	
    	if(forecast.get("wind_speed").getClass().getSimpleName().equals("Integer")) {
			//integer
			Double d = convertToDouble((Integer) forecast.get("wind_speed"));
			setSpeed(d);
		}else {
			//double
			setSpeed((Double)forecast.get("wind_speed"));
		}
    	
    	setDeg((Integer) forecast.get("wind_deg"));
    	
    	setClouds((Integer) forecast.get("clouds"));
    	
    	JSONArray jsonArray = (JSONArray) forecast.get("weather");    	
    	setWeather(new Weather((JSONObject) jsonArray.get(0)));
    	if(getWeather().getMain().equals("Rain")) {
    		setRain(true);
    	}else {
    		setRain(false);
    	}
    	if(getWeather().getMain().equals("Snow")) {
    		setSnow(true);
    	}else {
    		setSnow(false);
    	}
    	DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyy HH:mm:ss");  
    	LocalDateTime now = LocalDateTime.now();  
    	setTimestamp(dtf.format(now));
    	
    	//System.out.println(forecast.toString());
    }
	
	
}
