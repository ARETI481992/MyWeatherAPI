package weatherapi.myAPI;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class TestForecasts {
	Communicator comm = new Communicator();
	@Test
	void testDailyForecast() {
		assertNotNull(comm.getDailyForecast(), "Forecast returned is null");
	}
	
	@Test
	void testHourlyForecast() {
		assertNotNull(comm.getHourlyForecast(), "Forecast returned is null");
	}
	
	@Test
	void testCurrentForecast() {
		assertNotNull(comm.getCurrentForecast(), "Forecast returned is null");
	}

}
