package console.backend;

import java.util.ArrayList;

import weatherapi.myAPI.DailyForecast;
import weatherapi.myAPI.DailyTemperature;
import weatherapi.myAPI.Forecast;
import weatherapi.myAPI.HourlyForecast;


/*
 * Dhmiourgei Strings me to keimeno pou theloume na provlithei, analogws to request pou kaname.
 * Kathe methodos dhmiourgei diaforetiko string analoga me to ti typos forecast zhththike, 
 * kathws oi plhrofories gia to kathena mporei na diaferoun.
 * PROSOXI: auth h klash den typwnei tipota, apla dhmiourgei ta strings kai ta epistrefei.
 * Auto symvainei gia na parameinei diakriti h ennoia tou back end. To front end pou analmvanei
 * tis ektypwseis einai to App.java.
 */
public class ForecastViewBuilder {
	
	/*
	 * ftiaxnei to Forecast mhnyma gia to current weather 
	 */
	public String buildCurrentWeatherForecast(ArrayList<Forecast> forecastList) {
		//analoga to epilegmeno systhma monadwn metrhshs, orizei katallhla
		// tis monades gia thn thermokrasia kai thn taxythta tou anemou
		String units = Controller.getInstance().getcurrentUnits();
		String unitTempSymbol = "";
		String unitSpeedSymbol = "";
		if(units.equals("metric")) {
			unitTempSymbol = "C";
			unitSpeedSymbol = "meter/sec";
		}else {
			unitTempSymbol = "F";
			unitSpeedSymbol = "miles/hour";
		}
		
		//oi trexouses kairikes synthikes einai antikeimeno typou HourlyForecast apla gia thn
		// trexousa wra. synepws h lista forecastList periexei ena mono antikeimeno.
		HourlyForecast forecast = (HourlyForecast) forecastList.get(0);
		
		//dhmiourgia tou string pou tha ektypwthei sthn othonh, omorfa ftiagmeno.
		String forecastString = "-----------------------\n";		
		forecastString += "Forecast date: " + forecast.getTimestamp() + "\n";	
		forecastString += "Temperature: " + forecast.getTemp().getTemperatureDegrees() + " " + unitTempSymbol + "\n";	
		forecastString += "Weather: \n   Main: " + 
		forecast.getWeather().getMain() + "\n   Desc.:" + 
		forecast.getWeather().getDescription() + "\n";
		forecastString += "Humidity: " + forecast.getHumidity() + "% \n";
		forecastString += "Pressure: " + forecast.getPressure() + " hPa \n";		
		forecastString += "Wind: \n";
		forecastString += "   " + forecast.getSpeed() + " " + unitSpeedSymbol + " \n   " +
		forecast.getDeg() + " degrees \n";		
		forecastString += "-----------------------\n";
		
		//kai epistrofh tou string				
		return forecastString;
	}
	
	/*
	 * Ftiaxnei to string gia to wriaio weather
	 */
	public String buildHourlyWeatherForecast(ArrayList<Forecast> forecastList) {
		String units = Controller.getInstance().getcurrentUnits();
		String unitTempSymbol = "";
		if(units.equals("metric")) {
			unitTempSymbol = "C";
		}else {
			unitTempSymbol = "F";
		}
		String forecastString = "";
		double tempDegreesSum = 0;
		double averageTempDegrees;
		
		//h lista mas periexei tis prognwseis gia tis epomenes 48 wres, dhladh
		// o pinakas exei 48 theseis, apo 0 ews kai 47.
		// Opote gia kathe mia apo autes 
		// pairnei ta dedomena kai ypologizei thn mesh thermokrasia.
		//
		for(int i=0; i<forecastList.size(); i++) {
			HourlyForecast forecast = (HourlyForecast) forecastList.get(i);
			tempDegreesSum += forecast.getTemp().getTemperatureDegrees();			
			forecastString += "   " + forecast.getTimeOfForecast() + " - Temp:" + 
			forecast.getTemp().getTemperatureDegrees() + "" + unitTempSymbol + " - " +
			forecast.getWeather().getMain() + ", " + forecast.getWeather().getDescription() + 
			"\n";
			
		}
		averageTempDegrees = tempDegreesSum / 48;		
		forecastString = "Average temperature in the next 48 hours - " + 
		averageTempDegrees + 
		unitTempSymbol + "\n -----------------------\n" + 
		forecastString + "-----------------------\n";
						
		return forecastString;
	}
	
	/*
	 * Ftiaxnei to string gia to hmerhsio weather
	 */
	public String buildDailyWeatherForecast(ArrayList<Forecast> forecastList) {
		String units = Controller.getInstance().getcurrentUnits();
		String unitTempSymbol = "";
		String unitSpeedSymbol = "";
		if(units.equals("metric")) {
			unitTempSymbol = "C";
			unitSpeedSymbol = "meter/sec";
		}else {
			unitTempSymbol = "F";
			unitSpeedSymbol = "miles/hour";
		}
		String forecastString = "----------------------\n";
		//h lista periexei prognwsi gia tis epomenes 5 meres. Dhladh 5 antikeimena
		// stis theseis 0 ews 4. Gia kathe ena apo auta frontizei na typwsei tis 
		// plhrofories pou xreiazomaste alla kai na ta diaxwrisei swsta.
		for(int i=0; i<forecastList.size(); i++) {
			
			DailyForecast forecast = (DailyForecast) forecastList.get(i);
			
			DailyTemperature dailyTemp = (DailyTemperature) forecast.getTemp();
			
			forecastString += forecast.getDateOfForecast() + 
					"\n   Temperature: " + dailyTemp.getTemperatureDegrees() + unitTempSymbol + 
					"\n     Daily Minimum: " + dailyTemp.getMin() + unitTempSymbol + 
					"\n     Daily Maximum: " + dailyTemp.getMax() + unitTempSymbol +
					"\n   Weather: \n     Main: " + forecast.getWeather().getMain() + 
					"\n     Desc: " + forecast.getWeather().getDescription() + 
					"\n   Probability of Precipitation: " + forecast.getPop() + "%" + 
					"\n   Humidity: " + forecast.getHumidity() + "%" + 
					"\n   Wind: \n     Speed: " + forecast.getSpeed() + " " + unitSpeedSymbol + 
					"\n     Degrees: " + forecast.getDeg() + " degrees" + 
					"\n   Timestamp: " + forecast.getTimestamp() + "\n\n";
		}
		
		forecastString += "-----------------------\n";
						
		return forecastString;
	}
}
