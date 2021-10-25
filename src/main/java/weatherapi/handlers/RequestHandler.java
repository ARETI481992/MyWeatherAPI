package weatherapi.handlers;

import java.util.ArrayList;

import weatherapi.myAPI.Forecast;


/*
 * Abstract klash onomati ReuqestHandler thn opoia kanoun extend diafora requests pou
 * mporoume na kanoume. Yparxei se periptwsh pou xreiastei na omadopoihsoume polla
 * diaforetika requests sthn idia domh.
 */
public abstract class RequestHandler {
	String api_key;
	public abstract ArrayList<Forecast> getResponse(String place);
	
	public abstract ArrayList<Forecast> getResponse(Double lon, Double lat);
	
	public void setAPIKey(String key) {
		api_key = key;
	}
	
}
