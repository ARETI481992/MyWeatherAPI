package weatherapi.myAPI;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

import org.junit.jupiter.api.Test;

class TestGetGeolocation {

	@Test
	void test() {
		GeoLocator geoloc = new GeoLocator();
		
	    assertNotNull(geoloc.fetchCoordinatesBasedOnIP(), "Geoloc returned null instead of CityObject object.");
	}

}
