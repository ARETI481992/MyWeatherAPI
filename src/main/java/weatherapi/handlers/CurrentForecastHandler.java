package weatherapi.handlers;

import java.util.ArrayList;
import java.util.List;

import console.backend.Controller;
import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import kong.unirest.json.JSONObject;
import weatherapi.myAPI.CityObject;
import weatherapi.myAPI.Forecast;
import weatherapi.myAPI.HourlyForecast;

/*
 * Xeirizetai ta reuqests gia tis trexouses kairikes synthikes.
 */
public class CurrentForecastHandler extends RequestHandler{
	private List<Forecast> currentForecast;
	private int responseCode;
	
	public CurrentForecastHandler(String key) {
		setAPIKey(key);
		currentForecast = new ArrayList<Forecast>();
	}
	
	/*
	 * This is an empty method. 
	 * Epeidh pote den tha xreiastei na paroume trexouses kairikes synthikes.
	 * mesw tou onomatos polhs.
	 */
	@Override
	public ArrayList<Forecast> getResponse(String place) {
		return null;
	}

	/*
	 * Oi trexouses kairikes synthikes gia mia topothesia lamvanontai mesw
	 * twn gewgrafikwn syntetagmenwn, vasei tou pws to orizei to OpenWeatherMap.
	 * me auth th methodo pairnoume mia lista me tis prognwseis Forecast gia thn trexousa
	 * xronikh stigmh.
	 */
	@Override
	public ArrayList<Forecast> getResponse(Double lon, Double lat) {
		//prwta pairnoume city information, edw tha fanei an yparxei sto database
		// h polh pou theloume. 
		//Einai anagkaio gia na kataskeuasoume to CityObject mas, giati
		// an proxwrisoume apeutheias sthn lhpsh tou kairou, den tha exoume eparkh dedomena
		// gia thn polh.
		String query = "https://api.openweathermap.org/data/2.5/weather";
		query += "?lat=" + lat + "&" + "lon=" + lon;
		query += "&appid="+api_key;
		
		//opws eipame pairnoume to http response kai to metatrepoume se json object.
		HttpResponse<String> httpResponse = Unirest.get(query).asString();
		JSONObject jsonResponse = new JSONObject(httpResponse.getBody());
		
		if(jsonResponse.get("cod").getClass().getSimpleName() == Integer.class.getSimpleName()) {
			setResponseCode((int) jsonResponse.get("cod"));
		}else {
			setResponseCode((int) Integer.parseInt((String) jsonResponse.get("cod")));
		}
		
		//mono an phrame thetikh apanthsh proxwrame sthn lhpsh twn dedomenwn xrhsimopoiwntas 
		// tis syntetagmenes.
		if(responseCode == 200) {
			//afou lavame apanthsh, kataskeuazoume to city object me ta dedomena pou
			// phrame.
			CityObject city = new CityObject();
			city.setCityDetails(jsonResponse);
			
			//kai sthn synexeia zhtame thn prognwsh tou kairou symfwna me tis lon/lat coordinates
			query = "https://api.openweathermap.org/data/2.5/onecall";
			query += "?lat=" + city.getCoord().getLat() + "&lon=" + city.getCoord().getLon();
			//theloume na agnohsoume ta hourly kai daily apotelesmata,
			// kathws sthn prokeimenh mas noiazei mono to current
			query += "&exlude=hourly,daily,alert";
			//ta zhtame sthn monada metrhshs pou exei epilegmenh o xrhsths
			// auto mas to dinei o controller.
			query += "&units="+Controller.getInstance().getcurrentUnits();
			query += "&appid="+api_key;
			
			httpResponse = Unirest.get(query).asString();
			jsonResponse = new JSONObject(httpResponse.getBody());
			
			//an to json exei mono 2 stoixeia, auto mathame empeirika oti 
			// shmainei oti den petyxe h lhpsh dedomenwn.
			if(jsonResponse.length() == 2) {
				//request failed
				System.out.println("!Failed to retrieve data. This is OpenWatherMap's problem");
				return (ArrayList<Forecast>) currentForecast;
			}
			//alliws dhmiourgoume ena neo HourlyForecast object, pou periexei 
			// thn prognwsh tou kairou pou theloume.
			JSONObject current = (JSONObject) jsonResponse.get("current");			
			Forecast day = new HourlyForecast(current);
			//to vazoume sth lista kai epistrefoume ta dedomena.
			currentForecast.add(day);			
		}
		return (ArrayList<Forecast>) currentForecast;
	}

	
	/*
	 * getters kai setters
	 */
	public int getResponseCode() {
		return responseCode;
	}

	public void setResponseCode(int responseCode) {
		this.responseCode = responseCode;
	}


}
