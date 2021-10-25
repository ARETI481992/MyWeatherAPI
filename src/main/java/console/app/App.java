package console.app;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;

import weatherapi.backend.Controller;
import weatherapi.myAPI.CityObject;
import weatherapi.myAPI.Communicator;


public class App {
	public static Communicator comm;
	
	public static void main(String[] args) throws IOException {
		comm = new Communicator();
			
		printWelcomeMessage();
		while(true) {
			printMenu();
			int option = selectOption(1,5);
						
			if(option == 1) {
				//System.out.println("Selected option 1");
				manageCities();
			}else if(option == 2) {
				selectCity();
			}else if(option == 3) {
				weatherForecast();
			}else if(option == 4) {
				selectTemperatureScale();
			}else if(option == 5) {
				//System.out.println("Selected option 5");
				exitOption();
				break;
			}
		}
	}
	
	private static void manageCities() {
		int option = 0;
		
		while(option != 3) {
			Controller.getInstance().printCityList();
			System.out.println("Select one of the following: ");		
			printCityManagementMenu();
			
			option = selectOption(1,3);
			if(option == 1) {
				System.out.println("Type in the city name to ADD or '0' to cancel:");
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
				}else {
					//request city and then add
					CityObject obj = comm.requestCity(userInput);
					if(obj != null) {
						Controller.getInstance().addNewCity(obj);
					}else {
						System.out.println(userInput + " does not exist in the database. Unable to retrieve info.");
					}
				}			
			}else if(option == 2) {
				System.out.println("Type in the city name to DELETE or '0' to cancel:");
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
				}else {
					Controller.getInstance().deleteCity(userInput);
				}
			}
		}
	}
	
	private static void selectCity() {
		System.out.println("Select one of the following: ");
		
		printCitySelectionMenu();
		
		int option = selectOption(1,4);
		
		if(option == 1) {
			// current location
			Controller.getInstance().selectCurrentCity();			
		}else if(option == 2) {
			// from user list
			Controller.getInstance().selectCityFromList();
		}else if(option == 3) {
			// search city (does not add to user list)
			System.out.println("Type in the city name to search in the database or '0' to cancel:");
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
				System.out.println("Canceled city search.");
			}else {
				//request city and then set as current
				CityObject obj = comm.requestCity(userInput);
				if(obj != null) {
					Controller.getInstance().setCurrentCity(obj);
				}else {
					System.out.println(userInput + " does not exist in the database. Unable to retrieve info.");
				}
			}
		}
	}
	
	private static void weatherForecast() {
		System.out.println("Select one of the following: ");
		
		printWeatherForecastMenu();
		
		int option = selectOption(1,4);
	}
	
	private static void selectTemperatureScale() {
		System.out.println("Select one of the following: ");		
		printTemperatureScaleMenu();
		
		int option = selectOption(1,3);		
		if(option == 1) {
			//set scale to Celsius (metric)
			comm.setUnits("metric");
			System.out.println("Temperature units set to metric (Celsius).");
		}else if(option == 2) {
			//set scale to Fahrenheit (imperial)
			comm.setUnits("imperial");
			System.out.println("Temperature units set to imperial (Fahrenheit).");
		}
	}
	
	
	
	
	
	
	
	
	private static void printTemperatureScaleMenu() {
		System.out.println("   1) Celsius\n   2) Fahrenheit\n   3) Back...\n");
	}
	
	private static void printWeatherForecastMenu() {
		System.out.println("   1) Current Weather\n   2) Hourly Forecast(48 hours)\n   3) Daily Forecast(5 days)\n   4) Back...\n");
	}
	
	private static void printCitySelectionMenu() {
		System.out.println("   1) Current Location\n   2) From List\n   3) Search City\n   4) Back...\n");
	}
	
	private static void printCityManagementMenu() {
		System.out.println("   1) Add new city\n   2) Delete city\n   3) Back...");
	}
	
	private static void printMenu() {
		System.out.println("   1. Manage Cities \n   2. Select City \n   3. Weather Forecast \n   4. Select Scale of Temperature (C/F) \n   5. Exit \n");
	}
	
	private static void printWelcomeMessage() {
		System.out.println("Welcome to my WeatherApp! Please select an option from the following:");
	}
	
	private static void printTerminationMessage() {
		System.out.println("Program has terminated.");
	}
	
	private static void exitOption() {
		System.out.println("Bye-Bye!");
		printTerminationMessage();
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
