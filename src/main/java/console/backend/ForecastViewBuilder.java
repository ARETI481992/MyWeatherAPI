package console.backend;

import java.util.ArrayList;

import weatherapi.myAPI.DailyForecast;
import weatherapi.myAPI.DailyTemperature;
import weatherapi.myAPI.Forecast;
import weatherapi.myAPI.HourlyForecast;

public class ForecastViewBuilder {
	
	public String buildCurrentWeatherForecast(ArrayList<Forecast> forecastList) {
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
		
		HourlyForecast forecast = (HourlyForecast) forecastList.get(0);
		
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
		
		
		//System.out.println(forecastString);
				
		return forecastString;
	}
	
	public String buildHourlyWeatherForecast(ArrayList<Forecast> forecastList) {
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
		
		String forecastString = "";
		double tempDegreesSum = 0;
		double averageTempDegrees;
		
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
		
		//day iterator
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
