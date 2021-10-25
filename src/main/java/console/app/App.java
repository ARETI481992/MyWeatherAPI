package console.app;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import console.backend.Controller;
import weatherapi.myAPI.CityObject;
import weatherapi.myAPI.Communicator;
import weatherapi.myAPI.Forecast;

/* Auth einai h vasikh klash ths efarmoghs mas.
 * 
 * Einai console-based efarmogh, dhladh o xrhsths dinei eisodo xrhsimopoiwntas to termatiko
 *    kai ta apotelesmata typwnontai episis sto termatiko tou Eclipse. 
 * Brisketai sto paketo console, to opoio periexei tis classes ths efarmoghs 
 *    kai OXI tou WeatherAPI mas
 */
public class App {
	/*
	 * Prwta apo ola arxikoiei ton Communicator o opoios einai o diaulos epikoinwnias 
	 *    ths efarmoghs me to API mas.
	 */
	public static Communicator comm;
	
	public static void main(String[] args) throws IOException {
		//Arxikopoihsh tou Communicator gia na mporoume na kaloume tis 
		//  methodous pou ftiaksame sto API
		comm = new Communicator();				
		
		//O vroxos epanalipsis tou vasikou menu kai tis efarmogis mas
		while(true) {
			//mhnyma kalwsorismatos
			printWelcomeMessage();
			//typwnei thn polh thn opoia exei epileksei o xrhsths ws trexousa
			printCurrentLocation();
			//typwnei to vasiko menu epilogwn
			printMenu();
			
			//krataei thn epilogh tou xrhsth gia ypo-menu
			int option = selectOption(1,5);
						
			if(option == 1) {
				//an epelekse thn epilogh 1, tote pame sto ypo-menu diaxeirisis polewn
				manageCities();
			}else if(option == 2) {
				//an epelekse thn epilogh 2, tote pame sto ypo-menu epiloghs topothesias
				selectCity();
			}else if(option == 3) {
				//an epelekse thn epilogh 3, tote pame sto ypo-menu prognwsis kairou
				weatherForecast();
			}else if(option == 4) {
				//an epelekse thn epilogh 4, tote pame sto ypo-menu epiloghs klimakas
				selectTemperatureScale();
			}else if(option == 5) {
				//an epelekse thn epilogh 5, tote to programma termatizei
				exitOption();
				break;
			}
		}
	}
	
	//ypo-menu diaxeirisis polewn
	private static void manageCities() {
		int option = 0;
		
		//auto to loop krataei ton xrhsth se auto to ypo-menu, 
		//  se periptwsh pou thelei na kataxwrhsei h na svhsei polles poleis
		while(option != 3) {
			//typwnei tis hdh yparxouses poleis sthn lista tou xrhsth
			Controller.getInstance().printCityList();
			System.out.println("Select one of the following: ");	
			
			//typwnei to ypo-menu
			printCityManagementMenu();
			
			option = selectOption(1,3);
			if(option == 1) {
				//epilogh 1 = eisagwgh neas polhs sth lista tou xrhsth
				System.out.println("Type in the city name to ADD or '0' to cancel:");
				BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
				String userInput = null;
				try {
					userInput = input.readLine();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				//an dwsei to 0, tote akyrwnei thn eisagwgh polhs
				if(userInput.equals("0")) {
					//cancel
					System.out.println("Canceled city entry.");
				}else {
					//dhmiourgei kataxwrhsh gia thn polh pou epelekse o xrhsths
					//   MONO efoson auth yparxei sto database tou OpenWeatherMap
					//   epeidh den exei nohma na exoume poleis sth lista gia tis opoies
					//   den mporoume na paroume prognwsh kairou.
					//   opote kanei request, ki an auto petyxei thn kataxwrei sthn lista
					//   xwris na kanei kati ta dedomena
					CityObject obj = comm.requestCity(userInput);
					if(obj != null) {
						Controller.getInstance().addNewCity(obj);
					}else {
						System.out.println(userInput + " does not exist in the database. Unable to retrieve info.");
					}
				}			
			}else if(option == 2) {
				//epilogh 2 = diagrafh polhs apo th lista tou xrhsth
				Controller.getInstance().printCityList();
				if(Controller.getInstance().getUserCityList().size() == 0) {
					//an h lista einai adeia tote den yparxei kati na diagrapsei, ara epistrofh
					System.out.println("User list is empty, no cities to delete.");
				}else {
					// to systhma zhtaei apo ton xrhsth na plhktrologhsei to ONOMA ths polhs
					// pou thelei na diagrapsei anti na valei apla arithmo,
					//  ws mhxanismo asfaleias
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
						//an plhktrologhse onoma polhs tote to systhma paei na diagrapsei thn polh
						Controller.getInstance().deleteCity(userInput);
					}
				}				
			}
		}
	}
	
	//ypo-menu epilogis topothesias
	private static void selectCity() {
		int option = 0;
		
		while(option == 0) {
			System.out.println("Select one of the following: ");
			printCitySelectionMenu();
			option = selectOption(1,4);
			
			if(option == 1) {
				// o xristis dialekse auth thn epilogh gia na orisei automata thn topothesia tou
				//    vasei ths IP tou
				Controller.getInstance().selectCurrentCity();			
			}else if(option == 2) {
				// o xristis dialekse auth thn epilogh gia na dialeksei ws trexousa topothesia
				// mia ap tis poleis pou exei kataxwrhsei sth lista tou
				if(Controller.getInstance().getUserCityList().size() > 0) {
					Controller.getInstance().selectCityFromList();
				}else {
					// an h lista einai adeia tote to systhma to prospernaei
					System.out.println("User list is empty.");
					option = 0;
				}
			}else if(option == 3) {
				// o xristis dialekse auth thn epilogh giati thelei na orisei ws trexousa topothesia
				//     mia topothesia pou den yparxei hdh sth lista tou.
				// Molis thn plhktrologisei swsta, tha oristei ws trexousa polh,
				//    ALLA PROSOXI, DEN THA PROSTETHEI STH LISTA TOU
				// An thelei na akyrwsei, mporei klasika na grapsei 0
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
		
		
	}
	
	// ypo-menu prognwshs kairou
	private static void weatherForecast() {
		System.out.println("Select one of the following: ");
		
		printWeatherForecastMenu();
		
		int option = selectOption(1,4);
		
		if(option == 1) {
			//me auth thn epilogh, o xrhsths lamvanei ton kairo TWRA gia thn topothesia pou 
			//   exei epilegmenh
			//typwnetai ena mhnyma pou enhmerwnei ton xrhsth gia poia topothesia kai syntetagmenes
			//   lamvanetai prognwsi kairou
			System.out.println("!Getting the current forecast for " + Controller.getInstance().getCurrentCity().getName() +
					", " + Controller.getInstance().getCurrentCity().getCountry() + 
					" at lon/lat (" + Controller.getInstance().getCurrentCity().getCoord().getLon() + 
					", " + Controller.getInstance().getCurrentCity().getCoord().getLat() + ")");
			
			String forecastString = null;
			//o communicator diaxeirizetai olh thn epikoinwnia kai morfopoihsh twn dedomenwn
			//   se POJO
			ArrayList<Forecast> forecastList = comm.getCurrentForecast();
			//opote an h lista pou epistrefei den einai kenh (dhladh periexei prognwsh)
			//   tote to systhma typwnei ta stoixeia
			if(forecastList.size() > 0) {
				//mesw tou controller pairnoume etoimo to mhnyma eksodou me thn prognwsh tou kairou
				forecastString = Controller.getInstance().getFvBuilder().buildCurrentWeatherForecast(forecastList);
			}
			//kai ta typwnei sthn konsola
			printForecast(forecastString);
		}else if(option == 2) {
			// me auth thn epilogh o xrhsths zhtaei wriaia prognwsh kairou gia thn
			//   topothesia pou exei epilegmenh
			System.out.println("!Getting the hourly forecast (the next 48 hours) for " + Controller.getInstance().getCurrentCity().getName() +
					", " + Controller.getInstance().getCurrentCity().getCountry() + 
					" at lon/lat (" + Controller.getInstance().getCurrentCity().getCoord().getLon() + 
					", " + Controller.getInstance().getCurrentCity().getCoord().getLat() + ")");
			//me ton idio tropo lamvanei kai typwnei ta apotelesmata opws parapanw
			String forecastString = null;
			ArrayList<Forecast> forecastList = comm.getHourlyForecast();
			if(forecastList.size() > 0) {
				forecastString = Controller.getInstance().getFvBuilder().buildHourlyWeatherForecast(forecastList);
			}
			
			printForecast(forecastString);
		}else if(option == 3) {
			// me auth thn epilogh o xrhsths zhtaei hmerhsia prognwsh kairou gia thn
			//   topothesia pou exei epilegmenh
			System.out.println("!Getting the daily forecast (the next 5 days) for " + Controller.getInstance().getCurrentCity().getName() +
					", " + Controller.getInstance().getCurrentCity().getCountry() + 
					" at lon/lat (" + Controller.getInstance().getCurrentCity().getCoord().getLon() + 
					", " + Controller.getInstance().getCurrentCity().getCoord().getLat() + ")");
			//me ton idio tropo lamvanei kai typwnei ta apotelesmata opws parapanw
			String forecastString = null;
			ArrayList<Forecast> forecastList = comm.getDailyForecast();
			if(forecastList.size() > 0) {
				forecastString = Controller.getInstance().getFvBuilder().buildDailyWeatherForecast(forecastList);
			}
			
			printForecast(forecastString);
		}else {
			//an epelekse thn epilogh 4, dhladh Back, gyrnaei pisw
			return;
		}
		
		// wait acknowledgment before returning to the main menu
		// edw to systhma zhtaei apo ton xrhsth na pathsei enter prin epistrepsei sto vasiko menu
		// auto to valame giati theloume na dwsoume xrono ston xrhsth na dei me thn hsyxia tou thn provlepsi
		// prin typwthei ksana to vasiko menu
		System.out.println("Press enter to continue...");
		int opt = 0;
		while(opt == 0) {
			BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
			try {
				input.readLine();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			opt = 1;
		}
		
	}
	
	//ypo-menu epiloghs klimakas
	private static void selectTemperatureScale() {
		System.out.println("Select one of the following: ");		
		printTemperatureScaleMenu();
		
		int option = selectOption(1,3);		
		if(option == 1) {
			//orizoume thn klimaka se vathmous Celsius (h alliws metric system, opws ta ksexwrizei to OpenWeatherMap)
			comm.setUnits("metric");
			System.out.println("Temperature units set to metric (Celsius).");
		}else if(option == 2) {
			//orizoume thn klimaka se vathmous Fahrenheit (h alliws imperial system, opws ta ksexwrizei to OpenWeatherMap)
			comm.setUnits("imperial");
			System.out.println("Temperature units set to imperial (Fahrenheit).");
		}
	}
	
	
	
	
	
	/*
	 * OI PARAKATW METHODOI TYPWNOUN MHNYMATA STHN KONSOLA, 
	 * ANALOGWS TO MENU POU THELOUME NA TYPWTHEI
	 * H KAPOIO MHNYMA
	 */
	
	
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
	
	
	/* Auth h synarthsh diaxeirizetai thn eisodo tou xrhsth.
	 * kathe fora pou thn kaloume orizoume to megisto plithos epilogwn,
	 *   analogws to kathe ypo-menu, kai auth frontizei na dothei to swsto input.
	 *   Telos epistrefi thn epilogh tou xrhsth.
	 */
	private static int selectOption(int from, int to) {
		int opt = 0;
		
		//input operation
		BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
		
		//oso den dinei epilogh mesa sta oria, to programma tha zhtaei ksana eisodo
		while(opt < from || opt > to) {
			// frontizoume ta exceptions
			try {
				//pairnoume to input ws akeraio arithmo
				opt = Integer.parseInt(input.readLine());
				if(opt < from || opt > to) {
					System.out.println("An integer number between " + from + " and " + to + " must be given.");
				}
			} catch (NumberFormatException e) {
				//se auto to exception shmainei oti o xristis den edwse arithmo, alla grammata
				System.out.println("An integer number between " + from + " and " + to + " must be given.");
			} catch (IOException e) {
				//edw einai to klasiko exception ths java sxetika me ta I/O operations
				e.printStackTrace();
			}
		}
		//epistrefei thn epilogh tou xrhsth
		return opt;
	}
	
	//typwnei thn epilgemenh topothesia
	private static void printCurrentLocation() {
		CityObject obj = Controller.getInstance().getCurrentCity();
		if(obj != null) {
			System.out.println("<Current location: " + obj.getName() + ", " + obj.getCountry() + ">");
		}else {
			System.out.println("<Current location: Not Set>");
		}
		
	}
	
	// typwnei to mhnyma ths prognwsis tou kairou
	private static void printForecast(String forecastString) {
		if(forecastString != null) {
			System.out.println(forecastString);
		}		
	}
	
}
