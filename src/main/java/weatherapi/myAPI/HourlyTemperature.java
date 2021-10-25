package weatherapi.myAPI;

public class HourlyTemperature extends Temperature {
	private Double temperature;
	
	public HourlyTemperature(Double temp) {
		temperature = temp;
	}
	
	public Double getTemperatureDegrees() {
		return temperature;
	}
}
