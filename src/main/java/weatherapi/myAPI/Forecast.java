package weatherapi.myAPI;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

/*
 * Ena geniko antikeimeno Forecast to opoio periexei plhrofories
 * gia diaforous typous prognwshs kairou.
 * To kanoun extend ta DailyForecast kai HourlyForecast.
 * Yparxei wste na mporoun na topothetountai se koines listes 
 * typou Forecast ta parapanw antikeimena.
 */

@SuppressWarnings("serial")
public abstract class Forecast implements Serializable {
	//xronikh stigmh
	private Integer dt;
	//thermokrasia
    private Temperature temp;
    //piesh
    private Integer pressure;
    //ygrasia
    private Integer humidity;
    //plhrofories sxetika me ton kairo
    private Weather weather;
    //taxythta anemou
    private Double speed;
    //gwnia anemou
    private Integer deg;
    //synnefa
    private Integer clouds;
    //vroxh (nai/oxi)
    private boolean rain; 
    //xioni (nai/oxi)
    private boolean snow;  
    //xronikh stigmh pou lavame thn trexousa prognwsh
    private String timestamp;
    
    /*
     * Setters kai Getters
     */

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

	/*
	 * metatroph Integer wrapper class se double
	 */
	protected double convertToDouble(Integer num) {
    	int tem = (int) num;
		return (double) tem;
    }
	
	/*
	 * epistrefei thn wra ths prognwshs katallhla morfopoihmenh se string
	 */
	public String getTimeOfForecast() {
		Date time=new Date((long)getDt()*1000);
		
		String pattern = "dd/MM|HH:mm";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

		String timeToReturn = simpleDateFormat.format(time);

    	return timeToReturn;
	}
	
	/*
	 * epistrefei thn hmeromhnia ths prognwshs katallhla morfopoihmenh se string
	 */
	public String getDateOfForecast() {
		Date time=new Date((long)getDt()*1000);
		
		String pattern = "EEEE - dd/MM/yyyy";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

		String timeToReturn = simpleDateFormat.format(time);

    	return timeToReturn;
	}
}
