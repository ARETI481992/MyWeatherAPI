package weatherapi.myAPI;

import kong.unirest.json.JSONObject;

public class HourlyTemperature extends Temperature {
	private Double temperature;
	
	public HourlyTemperature(Double temp) {
		temperature = temp;
	}
	
	public HourlyTemperature(JSONObject jsonObject) {
		// TODO Auto-generated constructor stub
		System.out.println(jsonObject.toString());
	}

	public Double getTemperatureDegrees() {
		return temperature;
	}
}
