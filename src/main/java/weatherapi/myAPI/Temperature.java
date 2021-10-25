package weatherapi.myAPI;

import java.io.Serializable;

public abstract class Temperature implements Serializable {
	public abstract Double getTemperatureDegrees();
}
