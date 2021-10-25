package weatherapi.myAPI;


import kong.unirest.json.JSONObject;

public class DailyTemperature extends Temperature {
	private Double day; 
    private Double min; 
    private Double max; 
    private Double night; 
    private Double eve; 
    private Double morn; 
    
    public DailyTemperature(JSONObject temp) {
    	if(temp.get("day").getClass().getSimpleName().equals("Integer")) {    		
    		setDay(convertToDouble((Integer) temp.get("day")));
    	}else {
    		setDay((Double) temp.get("day"));
    	}
    	
    	if(temp.get("min").getClass().getSimpleName().equals("Integer")) {    		
    		setMin(convertToDouble((Integer) temp.get("min")));
    	}else {
    		setMin((Double) temp.get("min"));
    	}
    	
    	if(temp.get("max").getClass().getSimpleName().equals("Integer")) {    		
    		setMax(convertToDouble((Integer) temp.get("max")));
    	}else {
    		setMax((Double) temp.get("max"));
    	}
    	
    	if(temp.get("night").getClass().getSimpleName().equals("Integer")) {    		
    		setNight(convertToDouble((Integer) temp.get("night")));
    	}else {
    		setNight((Double) temp.get("night"));
    	}
    	
    	if(temp.get("eve").getClass().getSimpleName().equals("Integer")) {    		
    		setEve(convertToDouble((Integer) temp.get("eve")));
    	}else {
    		setEve((Double) temp.get("eve"));
    	}
    	
    	if(temp.get("morn").getClass().getSimpleName().equals("Integer")) {    		
    		setMorn(convertToDouble((Integer) temp.get("morn")));
    	}else {
    		setMorn((Double) temp.get("morn"));
    	}
    	
    }

	private double convertToDouble(Integer num) {
    	int tem = (int) num;
		return (double) tem;
    }

	@Override
	public Double getTemperatureDegrees() {
		return day;
	}

	public Double getDay() {
		return day;
	}

	public void setDay(Double day) {
		this.day = day;
	}

	public Double getMin() {
		return min;
	}

	public void setMin(Double min) {
		this.min = min;
	}

	public Double getMax() {
		return max;
	}

	public void setMax(Double max) {
		this.max = max;
	}

	public Double getNight() {
		return night;
	}

	public void setNight(Double night) {
		this.night = night;
	}

	public Double getEve() {
		return eve;
	}

	public void setEve(Double eve) {
		this.eve = eve;
	}

	public Double getMorn() {
		return morn;
	}

	public void setMorn(Double morn) {
		this.morn = morn;
	}

}
