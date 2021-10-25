package weatherapi.myAPI;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import kong.unirest.json.JSONArray;
import kong.unirest.json.JSONObject;

public abstract class Forecast implements Serializable {

	protected abstract Temperature getTemp();

	protected abstract Integer getDt();
	

}
