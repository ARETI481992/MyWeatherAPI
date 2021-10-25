package weatherapi.myAPI;

import weatherapi.handlers.CurrentForecastHandler;
import weatherapi.handlers.DailyForecastHandler;
import weatherapi.handlers.HourlyForecastHandler;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import console.backend.Controller;
import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import kong.unirest.json.JSONObject;


/* 
 * O communicator einai o kyrios diaulos epikoinwnias metaksy tou app kai tou WeatherAPI
 * Ola ta requests pernane prwta apo auton kathws diaxeirizetai to api-key mas gia 
 *  	to OpenWeatherMap, xwris to opoio den mporoume na aitithoume dedomena 
 * Kathe nea efarmogi pou epithymei na xrhsimopoihsei to API prepei na kanei initialize
 * 		ena kai mono ena neo antikeimeno ths klashs Communicator * 
 */
public class Communicator {
	private String api_key;
	private Controller controller;
	
	/* 
	 * Otan dhmiourgoume ena neo antikeimeno Communicator, auto pou kanei prwta apo ola
	 * einai na diavasei apo to arxeio 'api-key' to API key mas	 * 
	 */
	public Communicator(){
		//to arxeio vrisketai sta resources tou API jar
		String api_key_file = "api-key";
		InputStream is = Communicator.class.getClassLoader().getResourceAsStream(api_key_file);
		
		StringBuilder sb = new StringBuilder();
		try {
			for (int ch; (ch = is.read()) != -1; ) {
			    sb.append((char) ch);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("ERROR: Missing OpenWeatherMap API key file from resources.");
			e.printStackTrace();
		}
		api_key = sb.toString();
		
		/* 
		 * Sthn synexeia dhmiourgei antikeimeno Controller mesw tou opoiou
		 * tha apothikeuontai dedomena ths ekteleshs kai proswpikes epiloges tou xrhsth 
		 * Perissotera sxetika me ton Controller sthn klash Controller.java 
		 */
		
		//recorder singleton object		
		controller = Controller.getInstance();
	}
	
	/*
	 * mesw auths ths methodou o Communicator epistrefei mia lista apo antikeimena
	 * Forecast, dhladh prognwseis kairou kai pio sygkekrimena HMERHSIES prognwseis kairou
	 */	
	public ArrayList<Forecast> getDailyForecast() {
		/*
		 *  daily forecast gia thn trexousa epilegmenh polh tou xrhsth
		 *  arxika pairnei ap ton Controller to onoma ths polhs kai dhmiourgei to request pros to
		 *  OpenWeatherMap
		 */
		// to argument 0 sthn getForecastByCityName() prosdiorizei oti theloume hmerhsia prognwsh
		ArrayList<Forecast> dailyForecast =  getForecastByCityName(Controller.getInstance().getCurrentCity().getName(), 0);
		
		return dailyForecast;
	}
	
	/*
	 * To idio me thn apo panw alla kanei request gia WRIAIA prognwsh kairou
	 */
	public ArrayList<Forecast> getHourlyForecast() {
		// hourly forecast
		// to argument 1 sthn getForecastByCityName() prosdiorizei oti theloume wriaia prognwsh
		ArrayList<Forecast> hourlyForecast = getForecastByCityName(Controller.getInstance().getCurrentCity().getName(), 1);
			
		return hourlyForecast;
	}
	
	/*
	 * Mesw auths ths methodou o communicator tha aiththei tis trexouses kairikes synthikes
	 *  gia thn trexousa epilegmenh topothesia tou xrhsth
	 */
	public ArrayList<Forecast> getCurrentForecast() {
		// current forecast	
		// arxika lamvanei OLOKLHRO to CityObject ths topothesias kai oxi mono to onoma
		// ths polhs opws oi 2 parapanw methodoi.
		// Auto symvainei giati to API epistrefei tis trexouses kairikes synthikes vasei twn
		// Longitude kai Latitude coordinates ths topothesias anti gia to onoma
		CityObject cityObj = Controller.getInstance().getCurrentCity();
		
		//get the forecast for the city's coordinates
		ArrayList<Forecast> currentForecast = getForecastByCityCoordinates(cityObj.getCoord().getLon(), cityObj.getCoord().getLat(), 3);
		
		return currentForecast;
	}
	
	/*
	 * Dhmiourgei katallhlo RequestHandler antikeimeno, analogws to ti aithma zhthse o xrhsths/app
	 * Mesw twn diaforetikwn classes Handler, morfopoiountai katallhla ta dedomena analoga
	 * me tis anagkes kai to periexomeno.
	 * Oloi oi Handlers pou vriskontai sto paketo weatherapi.handlers tou API mas, 
	 * kanoun extend thn abstract klash RequestHandler.
	 * Se auth th methodo h prognwseis aitountai mesw tou onomatos ths polhs.
	 */
	private ArrayList<Forecast> getForecastByCityName(String place, int type) {
		if(type == 0) {
			// daily forecast
			DailyForecastHandler dailyForecast = new DailyForecastHandler(api_key);
			
			return(dailyForecast.getResponse(place));
		}else if(type == 1) {
			// hourly forecast
			HourlyForecastHandler hourlyForecast = new HourlyForecastHandler(api_key);
			
			return(hourlyForecast.getResponse(place));
		}
		return null;
    }
	
	/*
	 * Opws to apo panw, alla me th diafora oti ta dedomena aitountai dinontas ws parametro tis 
	 * syntetagmenes ths polhs.
	 * Exei ylopoiithei mono gia to current weather giati ta alla kalyptontai apo panw
	 */
	private ArrayList<Forecast> getForecastByCityCoordinates(Double longtitude, Double latitude, int type){
		if(type == 3) {
			//current weather
			CurrentForecastHandler currentForecast = new CurrentForecastHandler(api_key);
			
			return(currentForecast.getResponse(longtitude, latitude));
		}
		return null;
	}
	/*
	 * epistrefei to api key
	 */
	public String getAPIKey() {
		return api_key;
	}
	
	/*
	 * xrhsimopoieitai gia na orisei ston controller to systhma monadwn
	 */
	public void setUnits(String units) {
		controller.setcurrentUnits(units);
	}
	
	/*
	 * Auth h methodos xrhsimopoieitai gia na lavoume dedomena gia mia polh opws gewgrafikes
	 * syntetagmenes, xwra klp dinontas mono to onoma ths polhs.
	 * Yparxei gia na mas dhmiourgei to antikeimeno otan kataxwrei o xrhsths nees poleis sthn lista tou
	 * , etsi wste na exoume pantote apothikeumena oloklira antikeimena CityObject kai oxi mono
	 *  ta onomata twn polewn.
	 */
	public CityObject requestCity(String place) {
		//get city information
		int responseCode;
		//dhmiourgei to query pou tha ginei sto OpenWeatherMap
		String query = "https://api.openweathermap.org/data/2.5/weather";
		// gia thn polh 'place' 
		query += "?q=" + place;
		// me to api key mas
		query += "&appid="+api_key;
		
		//edo apothikeuetai to apotelesma tou request
		HttpResponse<String> httpResponse = Unirest.get(query).asString();
		// kai mesw autou ftiaxnoume to jsonObject response
		JSONObject jsonResponse = new JSONObject(httpResponse.getBody());
		
		//twra mporoume na elegksoume an htan epityxes to aithma, koitwntas to pedio 'cod'
		// tou jsonResponse.
		// An auto einai 200, shmainei oti h lhpsh twn dedomenwn htan epityxhs.
		//Alles fores mas epistrefei to 200 ws string kai alles ws Integer,
		// opote gia na mhn kremasei to programma elegxoume kathe fora ton typo ths apanthshs.
		//Einai pithano h polh place pou zhthsame na mhn yparxei sto database, h apla
		// na apetyxe h epikoinwnia
		if(jsonResponse.get("cod").getClass().getSimpleName() == Integer.class.getSimpleName()) {
			responseCode = (int) jsonResponse.get("cod");
		}else {
			responseCode = (int) Integer.parseInt((String) jsonResponse.get("cod"));
		}
		//an ola phgan kala, dhmiourgei antikeimeno CityObject me oles tis plirofories
		// kai to epistrefei sthn efarmogh.
		if(responseCode == 200) {
			CityObject city = new CityObject();
			city.setCityDetails(jsonResponse);			
			return city;
		}
		//Alliws epistrefei null, gia na kserei h efarmogh oti apetyxe to aithma
		return null;
	}

}
