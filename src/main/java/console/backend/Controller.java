package console.backend;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import weatherapi.myAPI.CityObject;
import weatherapi.myAPI.GeoLocator;


/*
 * O Controller apotelei thn raxokwkalia ths efarmoghs mas, h alliws to back end.
 * Einai Singleton Object, dhladh arxikopoieitai monaxa mesw ths getInstance() mia fora kai
 * kai meta h idia synarthsh epistrefei to monadiko instance tou antikeimenou.
 * Auto to kanoume giati theloume na yparxei ena monadiko isntance Controller 
 * se olh thn efarmogh, to opoio tha exei apothikeumenes tis epiloges tou xrhsth kai tis 
 * parametrous tou systhmatos.
 */
public class Controller {
	// to instance tou monadikou singleton object Controller mas
	private static Controller instance = null;
	//epilegmeno systhma monadwn metrhseis
	private String currentUnits;
	//ta onomata twn polewn sth lista tou xrhsth
	private List<String> userCities;
	//ta city objects twn polewn sth lista tou xrhsth
	private List<CityObject> userCitiesObjects;
	//o ForecastViewBuilder mesw tou opoiou dhmiourgountai ta apotelesma pros typwsh
	// perissotera sthn ForecastViewBuilder.java
	private ForecastViewBuilder fvBuilder;
	//trexousa epilegmenh polh tou xrhsth
	private CityObject currentCity;
	//to arxeio ston disko (resources) sto opoio apothikeuontai oi poleis tou xrhsth
	private String citySaveFileName;
	
	public Controller() {
		//arxikopoiei kenes listes
		userCities = new ArrayList<String>();
		userCitiesObjects = new ArrayList<CityObject>();
		//default monada metrhshs orizoume to metric system
		setcurrentUnits("metric");
		//arxikopoihsh output string builder
		fvBuilder = new ForecastViewBuilder();
		
		//dinei onoma sto save file
		citySaveFileName = "user-cities";
		//epixeirei na fortwsei dedomena apo to arxeio. An o xrhsths
		// exei ksanatreksei to app, tote tha fortwthoun oi epiloges tou
		//An oxi tote den peirazei, to app proxwraei.
		//An leipei to arxeio pali den peirazei, giati tha dhmiourgithei me to prwto save.
		try {
			loadUserList();
		} catch (ClassNotFoundException | IOException e) {
			System.err.println("User list file is empty. No cities to load. Continuing...");
		}
		//select current location as default city mesw ths IP
		selectCurrentCity();
	}
	
	//thn prwth fora dhmiourgei to singleton object Controller
	// enw apo thn deuterh kai meta epistrefei to instance tou
	public static Controller getInstance() {
		if(instance == null) {
			instance = new Controller();
		}
		return instance;
	}
	
	/*
	 * Getters kai Setters twn parametrwn
	 */
	
	public String getcurrentUnits() {
		return currentUnits;
	}

	public void setcurrentUnits(String currentUnits) {
		this.currentUnits = currentUnits;
	}
	
	public List<CityObject> getUserCityList() {
		return userCitiesObjects;
	}
	
	public CityObject getCurrentCity() {
		return currentCity;
	}

	public void setCurrentCity(CityObject currentCity) {
		this.currentCity = currentCity;
		System.out.println(currentCity.getName() + ", " + currentCity.getCountry() + " was set as the current city.");
	}
	
	public ForecastViewBuilder getFvBuilder() {
		return fvBuilder;
	}

	public void setFvBuilder(ForecastViewBuilder fvBuilder) {
		this.fvBuilder = fvBuilder;
	}
	
	
	/*
	 * Prosthetei nea polh sthn lista tou xrhsth
	 */
	public void addNewCity(CityObject obj) {
		//an yparxei hdh, den thn prosthetei
		if(userCities.contains(obj.getName())) {
			System.out.println(obj.getName()+" is already on the user list.");
		}else {
			//vazei kai to onoma ths polhs sthn userCities
			// alla kai to idio to antikeimeno sthn userCitiesObjects
			//Auto to kanoume gia na entopizoume grhgorotera poies poleis
			// yparxoun hdh sthn lista tou xrhsth mesw ths contains sthn userCities.
			userCities.add(obj.getName());
			userCitiesObjects.add(obj);
			
			//epixeirei na apothikeusei thn nea lista ston disko
			// kanontas overwrite ta proigoumena periexomena
			try {
				saveUserList();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			System.out.println("Added " + obj.getName() + " to the user list.");
		}
	}
	
	/*
	 * Svhnei mia polh apo thn lista tou xrhsth
	 */
	public void deleteCity(String cityName) {
		//an yparxei fysika
		if(userCities.contains(cityName)) {
			for(int i=0; i<userCitiesObjects.size(); i++) {
				if(userCitiesObjects.get(i).getName().equals(cityName)) {
					//frontizei na afairesei thn polh kai apo tis 2 listes
					userCitiesObjects.remove(i);
					userCities.remove(cityName);
				}
			}			
			System.out.println("Removed " + cityName + " from the user list.");
			// kai apothikeuei thn nea lista pleon, xwris to antikeimeno pou diegrafh
			try {
				saveUserList();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else {
			//an den yparxei, tote typwnei mhnyma kai synexizei
			System.out.println(cityName+" does not exist on the user list.");
		}
	}
	
	/*
	 * Typwnei tis idi yparxouses poleis sthn lista tou xrhsth, an yparxoun
	 */
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
	
	/*
	 * Epilegei ws trexousa polh, thn topothesia pou vrisketai o xrhsths mesw ths IP tou.
	 * Gia na ginei auto, dhmiourgei prwta ena antikeimeno GeoLocator ths vivliothikis maxmind
	 * (pou exoume frontisei na symperilavoume sta dependencies) mesw tou opoiou entopizei
	 * thn topothesia.
	 * Perissotera sthn klash GeoLocator.java
	 */
	public void selectCurrentCity() {
		GeoLocator geoloc = new GeoLocator();
		currentCity = geoloc.getCityBasedOnIP();
		System.out.println(currentCity.getName() + ", " + currentCity.getCountry() + " was selected as the current city based on the IP");
	}
	
	/*
	 * Epilegei ws trexousa polh mia apo tis idi kataxwrhmenes sthn lista
	 */	
	public void selectCityFromList() {
		System.out.println("Select one of the following: ");
		printCityList();
		//an h lista fysika den einai kenh
		if(userCitiesObjects.size() > 0) {
			//typwnei menu epiloghs polhs, gia megalyterh eukolia
			int option = selectOption(1,userCitiesObjects.size()) - 1;		
			currentCity = userCitiesObjects.get(option);
			System.out.println(currentCity.getName() + ", " + currentCity.getCountry() + " set as the current city.");
		}else {
			System.out.println("User city list is empty.");
		}		
	}
	
	/*
	 * Apothikeuei ston disko thn lista me tis poleis.
	 * Akrivws epeidh ta objects ArrayList einai Serializable, mporoume na grapsoume
	 * ston disko apeutheias thn lista xwris megalo mpleksimo.
	 */
	private void saveUserList() throws IOException {
		FileOutputStream fout = new FileOutputStream(getClass().getClassLoader().getResource(citySaveFileName).getFile());
		ObjectOutputStream oos = new ObjectOutputStream(fout);
		oos.writeObject(userCitiesObjects);
		
		oos.flush();
		oos.close();
		fout.close();
	}
	
	/*
	 * Fortwnei kata thn ekkinhsh sth mnhmh thn lista me tis poleis pou yparxoun sto 
	 * save file tou xrhsth.
	 */
	
	@SuppressWarnings("unchecked")
	private void loadUserList() throws IOException, ClassNotFoundException {
	   FileInputStream fin = new FileInputStream(getClass().getClassLoader().getResource(citySaveFileName).getFile());
	   ObjectInputStream ois = new ObjectInputStream(fin);
	   //fortwnei apeutheias sth mnhmh to Arraylist object pou eixe grapsei proigoumenws, an yparxei
	   userCitiesObjects = (List<CityObject>) ois.readObject();
	   
	   //sthn synexeia dhmiourgei kai thn userCities symfwna me ta onomata twn objects.
	   for(int i=0; i<userCitiesObjects.size(); i++) {
		   userCities.add(userCitiesObjects.get(i).getName());
	   }
	   
	   ois.close();
	   fin.close();
	   
	   System.out.println("Loaded " + userCitiesObjects.size() + " cities from user list.");
	}
	
	
	/*
	 * Menu epilogis me tous katallhlous elegxous, opws akrivws kai sthn App.java
	 */
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
