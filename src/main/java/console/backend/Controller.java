package console.backend;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
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
	private ForecastViewBuilder fvBuilder;
	private CityObject currentCity;
	private String citySaveFileName;
	
	public Controller() {
		userCities = new ArrayList<String>();
		userCitiesObjects = new ArrayList<CityObject>();
		//default units is Celsius
		setcurrentUnits("metric");
		fvBuilder = new ForecastViewBuilder();
		
		citySaveFileName = "user-cities";
		try {
			loadUserList();
		} catch (ClassNotFoundException | IOException e) {
			System.err.println("User list file is empty. No cities to load. Continuing...");
		}
		//select current location as default city	
		selectCurrentCity();
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
	
	public List<CityObject> getUserCityList() {
		return userCitiesObjects;
	}
	
	public void addNewCity(CityObject obj) {
		if(userCities.contains(obj.getName())) {
			System.out.println(obj.getName()+" is already on the user list.");
		}else {
			userCities.add(obj.getName());
			userCitiesObjects.add(obj);
			
			try {
				saveUserList();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			System.out.println("Added " + obj.getName() + " to the user list.");
		}
	}
	
	public void deleteCity(String cityName) {
		if(userCities.contains(cityName)) {
			for(int i=0; i<userCitiesObjects.size(); i++) {
				if(userCitiesObjects.get(i).getName().equals(cityName)) {
					userCitiesObjects.remove(i);
					userCities.remove(cityName);
				}
			}			
			System.out.println("Removed " + cityName + " from the user list.");
			try {
				saveUserList();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else {
			System.out.println(cityName+" does not exist on the user list.");
		}
	}
	
	public void printCityList() {
		if(userCities.size() > 0) {
			System.out.println("Cities in the user's list:");
			for(int i=1; i<userCities.size()+1; i++) {
				System.out.println("   " + i + ". " + userCities.get(i-1));
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
		System.out.println("Select one of the following: ");
		printCityList();
		
		if(userCitiesObjects.size() > 0) {
			int option = selectOption(1,userCitiesObjects.size()) - 1;		
			currentCity = userCitiesObjects.get(option);
			System.out.println(currentCity.getName() + ", " + currentCity.getCountry() + " set as the current city.");
		}else {
			System.out.println("User city list is empty.");
		}
		
		
		
		
	}

	public ForecastViewBuilder getFvBuilder() {
		return fvBuilder;
	}

	public void setFvBuilder(ForecastViewBuilder fvBuilder) {
		this.fvBuilder = fvBuilder;
	}
	
	private void saveUserList() throws IOException {
		FileOutputStream fout = new FileOutputStream(getClass().getClassLoader().getResource(citySaveFileName).getFile());
		ObjectOutputStream oos = new ObjectOutputStream(fout);
		oos.writeObject(userCitiesObjects);
		
		oos.flush();
		oos.close();
		fout.close();
	}
	
	@SuppressWarnings("unchecked")
	private void loadUserList() throws IOException, ClassNotFoundException {
	   FileInputStream fin = new FileInputStream(getClass().getClassLoader().getResource(citySaveFileName).getFile());
	   ObjectInputStream ois = new ObjectInputStream(fin);
	   userCitiesObjects = (List<CityObject>) ois.readObject();
	   
	   for(int i=0; i<userCitiesObjects.size(); i++) {
		   userCities.add(userCitiesObjects.get(i).getName());
	   }
	   
	   ois.close();
	   fin.close();
	   
	   System.out.println("Loaded " + userCitiesObjects.size() + " cities from user list.");
	}
	
	private static int selectOption(int from, int to) {
		int opt = 0;
		
		BufferedReader input = new BufferedReader(new InputStreamReader(System.in));

		while(opt < from || opt > to) {
			try {
				opt = Integer.parseInt(input.readLine());
				if(opt < from || opt > to) {
					System.out.println("An integer number between " + from + " and " + to + " must be given.");
				}
			} catch (NumberFormatException e) {
				System.out.println("An integer number between " + from + " and " + to + " must be given.");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return opt;
	}
}
