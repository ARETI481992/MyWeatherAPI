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

public class GeoLocator {
		private String IP;
		
		public GeoLocator() {
			loadIPAddress();
		}
		
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

		public CityObject fetchCoordinatesBasedOnIP(){
			try (WebServiceClient client = new WebServiceClient.Builder(621016, "4pawHpVJ2I3hcfZr").host("geolite.info").build()) {

			    InetAddress ipAddress = InetAddress.getByName(IP);

			    // You can also use `client.city` or `client.insights`
			    // `client.insights` is not available to GeoLite2 users
			    CountryResponse countryResponse = client.country(ipAddress);
			    CityResponse cityResponse = client.city(ipAddress);
			    			    
			    Location loc = cityResponse.getLocation();
			    City city = cityResponse.getCity();
			    Country country = countryResponse.getCountry();
			    
			    Coordination coord = new Coordination(loc.getLongitude(), loc.getLatitude());
			    CityObject cityObj = new CityObject();
			    cityObj.setCoord(coord);
			    cityObj.setName(city.getName());
			    cityObj.setCountry(country.getName());
			    
			    return cityObj;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				System.out.println("Error: I/O operation on response failed.");
				e.printStackTrace();
			} catch (GeoIp2Exception e) {
				// TODO Auto-generated catch block
				System.out.println("Error: Communication with MaxMind GeoIP API failed.");
				e.printStackTrace();
			}
			
			return null;
		}
}
