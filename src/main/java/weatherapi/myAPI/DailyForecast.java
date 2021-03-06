package weatherapi.myAPI;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import kong.unirest.json.JSONArray;
import kong.unirest.json.JSONObject;

/*
 * Hmerhsia provlepsh kairou. Dhmiourgeitai apo ena json object to opoio proilthe apo
 * request gia daily forecast.
 * Kanei extend thn klash Forecast h opoia periexei tis plhrofories pou exoun aneksarthtws typou
 * Forecasts.
 * Deite Forecast.java gia ta diafora pedia.
 */
@SuppressWarnings("serial")
public class DailyForecast extends Forecast{
	//pithanotita vroxoptwshs
	private Double pop;

    public DailyForecast(JSONObject forecast) {
    	setDt((Integer) forecast.get("dt"));
    	   
    	//h thermokrasia mporei na mas erthei eite ws Json object eite ws Integer
    	// frontizoume na to diaxeiristoume katallhla gia na einai to app stathero
    	if(forecast.get("temp").getClass().getSimpleName().equals("JSONObject")) {
    		//json obj
    		setTemp(new DailyTemperature((JSONObject) forecast.get("temp")));
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
    	//antistoixa h taxythta tou anemou erxetai ws integer h ws double polles fores
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
    	
    	if(forecast.get("pop").getClass().getSimpleName().equals("Integer")) {
    		Double d = convertToDouble((Integer)forecast.get("pop"));
    		setPop(d * 100);
    	}else {
    		setPop((Double) forecast.get("pop") * 100);
    	}
    	
    	DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");  
    	LocalDateTime now = LocalDateTime.now();  
    	setTimestamp(dtf.format(now));
    }
    
    public void setPop(Double pop) {
    	this.pop = pop;
    }
    
    public Double getPop() {
    	return pop;
    }

}
