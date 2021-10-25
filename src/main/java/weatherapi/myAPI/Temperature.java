package weatherapi.myAPI;

import java.io.Serializable;

/*
 * geniko antikeimeno thermokrasias
 */
@SuppressWarnings("serial")
public abstract class Temperature implements Serializable {
	public abstract Double getTemperatureDegrees();
}
