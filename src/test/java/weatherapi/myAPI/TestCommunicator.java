package weatherapi.myAPI;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class TestCommunicator {

	@Test
	void test() {
		Communicator comm = new Communicator();
		assertEquals(comm.getAPIKey(), "81621cc350f9cfa42619664ea4377cb6", "Program failed to load the proper key.");
	}

}
