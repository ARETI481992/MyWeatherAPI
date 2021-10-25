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
    		day = convertToDouble((Integer) temp.get("day"));
    	}else {
    		day = (Double) temp.get("day");
    	}
    	
    	if(temp.get("min").getClass().getSimpleName().equals("Integer")) {    		
    		min = convertToDouble((Integer) temp.get("min"));
    	}else {
    		min = (Double) temp.get("min");
    	}
    	
    	if(temp.get("max").getClass().getSimpleName().equals("Integer")) {    		
    		max = convertToDouble((Integer) temp.get("max"));
    	}else {
    		max = (Double) temp.get("max");
    	}
    	
    	if(temp.get("night").getClass().getSimpleName().equals("Integer")) {    		
    		night = convertToDouble((Integer) temp.get("night"));
    	}else {
    		night = (Double) temp.get("night");
    	}
    	
    	if(temp.get("eve").getClass().getSimpleName().equals("Integer")) {    		
    		eve = convertToDouble((Integer) temp.get("eve"));
    	}else {
    		eve = (Double) temp.get("eve");
    	}
    	
    	if(temp.get("morn").getClass().getSimpleName().equals("Integer")) {    		
    		morn = convertToDouble((Integer) temp.get("morn"));
    	}else {
    		morn = (Double) temp.get("morn");
    	}
    	
    }
    
    private double convertToDouble(Integer num) {
    	int tem = (int) num;
		return (double) tem;
    }

	@Override
	public Double getTemperatureDegrees() {
		// TODO Auto-generated method stub
		return null;
	}

}
