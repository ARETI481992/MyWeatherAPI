package weatherapi.myAPI;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;

import com.maxmind.geoip2.WebServiceClient;
import com.maxmind.geoip2.exception.GeoIp2Exception;
import com.maxmind.geoip2.model.CityResponse;
import com.maxmind.geoip2.model.CountryResponse;
import com.maxmind.geoip2.record.Country;
import com.maxmind.geoip2.record.City;
import com.maxmind.geoip2.record.Location;

import console.app.App;

/*
 * H klash GeoLocator orizei to antikeimeno pou einai ypeuthino gia thn
 * anagnwrish ths topothesias tou xrhsth mesw ths IP tou.
 * Arxika epixeirhthike na anagnwrizete automata mesw ths kartas diktiou, proekypsan provlimata
 * giati gia na anagnwristei h external IP tou xrhsth prepei na einai syndedemenos mesw ethernet
 * , enw an einai mesw topikou router kai dromologhth, auto kathistatai diskolo kai prepei na 
 * exei oristei apo ton ypeuthino diktyou.
 * Gia paradeigma apo ena router sto spiti einai efikto mesw ths eth0 thiras ethernet,
 * enw se ena panepisthmio prepei na exei static IP kai na exei oristei mesw ths ekswterikhs pylhs
 * diktiou tou idrymatos.
 * 
 * Gi auto, o GeoLocator diavazei apo to arxeio ip-address, thn ip pou egrapse o xrhsths 
 * kai xrhsimopoiei authn gia na entopisei thn topothesia.
 */
public class GeoLocator {
		//h diefthinsi IP ws string
		private String IP;
		
		//otan arxikopoieitai o geolocoator, fortwnei ap to arxeio thn IP sth mnhmh
		public GeoLocator() {
			loadIPAddress();
		}
		
		//auth h methodos einai ipefthini gia to diavasma tou arxeiou kai diaxeirizetai ta 
		// exceptions.
		private void loadIPAddress() {
			String ip_address_file_name = "ip-address";
			InputStream is = App.class.getClassLoader().getResourceAsStream(ip_address_file_name);
			
			StringBuilder sb = new StringBuilder();
			try {
				for (int ch; (ch = is.read()) != -1; ) {
				    sb.append((char) ch);
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				System.out.println("Error: Missing IP address file.");
				e.printStackTrace();				
			}			
			IP = sb.toString();
		}

		//auth h methodos epistrefei city object pou antiproswpeuei thn topothesia tou xrhsth
		public CityObject getCityBasedOnIP(){
			//epixeirei na ftiaksei webservice client gia na aiththei apo thn vivliothiki tou
			// MaxMind thn topothesia vasei ths IP.
			//pairnei san orisma to kleidi pou exoume kanei generate sto API tou MaxMind
			try (WebServiceClient client = new WebServiceClient.Builder(621016, "4pawHpVJ2I3hcfZr").host("geolite.info").build()) {

				//dhmiourgei antikeimeno InetAddress mesw tou string IP mas
			    InetAddress ipAddress = InetAddress.getByName(IP);

			    //lamvanei apanthsh(response) sxetika me thn polh kai thn xwra.
			    // To maxmind API epistrefei ksexwrista antikeimena gia kathe periptwsh
			    CountryResponse countryResponse = client.country(ipAddress);
			    CityResponse cityResponse = client.city(ipAddress);		
			    //ftiaxnei ta geoip maxmind objects gia kathe antikeimeno
			    Location loc = cityResponse.getLocation();
			    City city = cityResponse.getCity();
			    Country country = countryResponse.getCountry();
			    //alla emeis ta theloume ws POJO, dhlah ta dika mas antikeimena
			    // opote paragoume POJOs symfwna me ta stoixeia pou lavame
			    Coordination coord = new Coordination(loc.getLongitude(), loc.getLatitude());
			    CityObject cityObj = new CityObject();
			    cityObj.setCoord(coord);
			    cityObj.setName(city.getName());
			    cityObj.setCountry(country.getName());
			    //telos epistrefei to antikeimeno
			    return cityObj;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				System.out.println("Error: I/O operation on response failed.");
				//den katafere na diavasei thn ip apo to arxeio
				e.printStackTrace();
			} catch (GeoIp2Exception e) {
				// TODO Auto-generated catch block
				//apetyxe h epikoinwnia me to maxmind geoIP API. Sinithws diko tous provlima
				System.out.println("Error: Communication with MaxMind GeoIP API failed.");
				e.printStackTrace();
			}
			
			return null;
		}
}
