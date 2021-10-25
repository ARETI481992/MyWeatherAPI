package weatherapi.myAPI;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import kong.unirest.json.JSONArray;
import kong.unirest.json.JSONObject;

public class HourlyForecast extends Forecast {
	private Integer dt;
    private Temperature temp;
    private Integer pressure;
    private Integer humidity;
    private Weather weather;
    private Double speed;
    private Integer deg;
    private Integer clouds;
    private boolean rain; 
    private boolean snow;
    private Double pop;
    private String timestamp;
	
	public HourlyForecast(JSONObject forecast) {
    	setDt((Integer) forecast.get("dt"));
    	
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
    	
    	if(forecast.get("wind_speed").getClass().getSimpleName().equals("Integer")) {
			//integer
			Double d = convertToDouble((Integer) forecast.get("wind_speed"));
			setTemp(new HourlyTemperature(d));
		}else {
			//double
			setTemp(new HourlyTemperature((Double)forecast.get("wind_speed")));
		}
    	
    	setDeg((Integer) forecast.get("wind_deg"));
    	
    	setClouds((Integer) forecast.get("clouds"));
    	
    	JSONArray jsonArray = (JSONArray) forecast.get("weather");    	
    	weather = new Weather((JSONObject) jsonArray.get(0));
    	if(weather.getMain().equals("Rain")) {
    		setRain(true);
    	}else {
    		setRain(false);
    	}
    	if(weather.getMain().equals("Snow")) {
    		setSnow(true);
    	}else {
    		setSnow(false);
    	}
    	
    	if(forecast.get("pop").getClass().getSimpleName().equals("Integer")) {
    		Double d = convertToDouble((Integer)forecast.get("pop"));
    		setPop(d);
    	}else {
    		setPop((Double) forecast.get("pop"));
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

	public Integer getDt() {
		return dt;
	}

	public void setDt(Integer dt) {
		this.dt = dt;
	}

	public Integer getPressure() {
		return pressure;
	}

	public void setPressure(Integer pressure) {
		this.pressure = pressure;
	}

	public Temperature getTemp() {
		return temp;
	}

	public void setTemp(Temperature temp) {
		this.temp = temp;
	}

	public Integer getHumidity() {
		return humidity;
	}

	public void setHumidity(Integer humidity) {
		this.humidity = humidity;
	}

	public Double getSpeed() {
		return speed;
	}

	public void setSpeed(Double speed) {
		this.speed = speed;
	}

	public Integer getDeg() {
		return deg;
	}

	public void setDeg(Integer deg) {
		this.deg = deg;
	}

	public Integer getClouds() {
		return clouds;
	}

	public void setClouds(Integer clouds) {
		this.clouds = clouds;
	}

	public boolean isRain() {
		return rain;
	}

	public void setRain(boolean rain) {
		this.rain = rain;
	}

	public boolean isSnow() {
		return snow;
	}

	public void setSnow(boolean snow) {
		this.snow = snow;
	}
	
	private double convertToDouble(Integer num) {
    	int tem = (int) num;
		return (double) tem;
    }

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
}
