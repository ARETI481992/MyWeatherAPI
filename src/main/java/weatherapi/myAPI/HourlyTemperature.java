package weatherapi.myAPI;

import kong.unirest.json.JSONObject;


/*
 * Antikeimeno wriaias thermokrasias.
 * Se auto mas noiazei mono h thermokrasia se vathmous kai tipote allo, se
 * antithesi me to DailyTemperature pou exei perissoteres plirofories.
 */
@SuppressWarnings("serial")
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
