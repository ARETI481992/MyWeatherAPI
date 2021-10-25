package weatherapi.myAPI;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import kong.unirest.json.JSONException;
import weatherapi.handlers.CurrentForecastHandler;
import weatherapi.handlers.DailyForecastHandler;
import weatherapi.handlers.HourlyForecastHandler;

class TestRequests {
	Communicator comm = new Communicator();
	
	@Test
	void dailyHandlerTest() {
		DailyForecastHandler dfh = new DailyForecastHandler(comm.getAPIKey());
		dfh.getResponse("London");
		assertTrue(dfh.getResponseCode() == 200, "Response returned is different than 200 and thus indicating an error has occured");
	}
	
	@Test
	void dailyHandlerTestException() {
		DailyForecastHandler dfh = new DailyForecastHandler(comm.getAPIKey());
		dfh.getResponse("");
		assertTrue(dfh.getResponseCode() == 400, "Response contains data despite passing an empty string as the location");
	}
	
	@Test
	void hourlyHandlerTest() {
		HourlyForecastHandler hfh = new HourlyForecastHandler(comm.getAPIKey());
		hfh.getResponse("London");
		assertTrue(hfh.getResponseCode() == 200, "Response returned is different than 200 and thus indicating an error has occured");
	}
	
	@Test
	void hourlyHandlerTestException() {
		HourlyForecastHandler hfh = new HourlyForecastHandler(comm.getAPIKey());
		hfh.getResponse("");
		assertTrue(hfh.getResponseCode() == 400, "Response contains data despite passing an empty string as the location");
	}
	
	@Test
	void currentHandlerTest() {
		CurrentForecastHandler cfh = new CurrentForecastHandler(comm.getAPIKey());
		//pass london's coordinates
		cfh.getResponse(-0.1257, 51.5085);
		assertTrue(cfh.getResponseCode() == 200, "Response returned is different than 200 and thus indicating an error has occured");
	}
	
	@Test
	void currentHandlerTestException() {
		CurrentForecastHandler cfh = new CurrentForecastHandler(comm.getAPIKey());
		//pass non existent coordinates
		cfh.getResponse(1000000.0, 1000000.0);
		assertTrue(cfh.getResponseCode() == 400, "Response contains data despite passing an empty string as the location");
	}
}
