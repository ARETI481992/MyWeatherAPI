package weatherapi.backend;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import weatherapi.myAPI.CityObject;
import weatherapi.myAPI.GeoLocator;

//recorder is a singleton class
public class Controller {
	private static Controller instance = null;
	private String currentUnits;
	private List<String> userCities;
	private List<CityObject> userCitiesObjects;
	
	private CityObject currentCity;
	
	public Controller() {
		userCities = new ArrayList<String>();
		userCitiesObjects = new ArrayList<CityObject>();
		//default units is Celsius
		setcurrentUnits("metric");
	}
	
	public static Controller getInstance() {
		if(instance == null) {
			instance = new Controller();
		}
		
		return instance;
	}
	
	public String getcurrentUnits() {
		return currentUnits;
	}

	public void setcurrentUnits(String currentUnits) {
		this.currentUnits = currentUnits;
	}
	
	public void addNewCity(CityObject obj) {
		if(userCities.contains(obj.getName())) {
			System.out.println(obj.getName()+" is already on the user list.");
		}else {
			userCities.add(obj.getName());
			userCitiesObjects.add(obj);
			System.out.println("Added " + obj.getName() + " to the user list.");
		}
	}
	
	public void deleteCity(String cityName) {
		if(userCities.contains(cityName)) {
			userCities.remove(cityName);
			System.out.println("Removed " + cityName + " from the user list.");
		}else {
			System.out.println(cityName+" does not exist on the user list.");
		}
	}
	
	public void printCityList() {
		if(userCities.size() > 0) {
			System.out.println("Cities in the user's list:");
			for(int i=0; i<userCities.size(); i++) {
				System.out.println("   " + userCities.get(i));
			}
		}else {
			System.out.println("User city list is empty.");
		}
		
	}

	public CityObject getCurrentCity() {
		return currentCity;
	}

	public void setCurrentCity(CityObject currentCity) {
		this.currentCity = currentCity;
		System.out.println(currentCity.getName() + ", " + currentCity.getCountry() + " was set as the current city.");
	}
	
	public void selectCurrentCity() {
		GeoLocator geoloc = new GeoLocator();
		currentCity = geoloc.getCityBasedOnIP();
		System.out.println(currentCity.getName() + ", " + currentCity.getCountry() + " was selected as the current city based on the IP");
	}
	
	public void selectCityFromList() {
		printCityList();
		System.out.println("Type in the city name to set as current or '0' to cancel:");
		BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
		String userInput = null;
		try {
			userInput = input.readLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(userInput.equals("0")) {
			//cancel
			System.out.println("Canceled city entry.");
		}else if(!userCities.contains(userInput)){
			System.out.println(userInput + " does not exist in the city list.");
		}else {
			for(int i=0; i<userCitiesObjects.size(); i++) {
				if(userCitiesObjects.get(i).getName().equals(userInput)) {
					currentCity = userCitiesObjects.get(i);
				}
			}
			System.out.println(userInput + " set as the current city.");
		}
	}
}
