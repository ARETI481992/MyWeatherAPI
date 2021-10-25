package weatherapi.myAPI;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public abstract class Forecast implements Serializable {
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
    private String timestamp;
    

	public void setTemp(Temperature temp) {
		this.temp = temp;
	}
	
	public Temperature getTemp() {
		return temp;
	}

	public void setDt(Integer dt) {
		this.dt = dt;
	}
	
	public Integer getDt() {
		return dt;
	}

	public Integer getPressure() {
		return pressure;
	}

	public void setPressure(Integer pressure) {
		this.pressure = pressure;
	}

	public Integer getHumidity() {
		return humidity;
	}

	public void setHumidity(Integer humidity) {
		this.humidity = humidity;
	}

	public Weather getWeather() {
		return weather;
	}

	public void setWeather(Weather weather) {
		this.weather = weather;
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

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	protected double convertToDouble(Integer num) {
    	int tem = (int) num;
		return (double) tem;
    }
	
	public String getTimeOfForecast() {
		Date time=new Date((long)getDt()*1000);
		
		String pattern = "dd/MM|HH:mm";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

		String timeToReturn = simpleDateFormat.format(time);

    	return timeToReturn;
	}
	
	public String getDateOfForecast() {
		Date time=new Date((long)getDt()*1000);
		
		String pattern = "EEEE - dd/MM/yyyy";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

		String timeToReturn = simpleDateFormat.format(time);

    	return timeToReturn;
	}
}
