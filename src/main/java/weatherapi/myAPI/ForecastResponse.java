package weatherapi.myAPI;

import java.util.ArrayList;

public class ForecastResponse {
	private String cod; 
    private Double message;
    private CityObject city; 
    private Integer cnt; 
    private java.util.List<Forecast> list = new ArrayList<>(); 

    public String getCod() {
        return cod;
    }
    
    public void setCod(String cod) {
        this.cod = cod;
    }

    public Double getMessage() {
        return message;
    }

    public void setMessage(Double message) {
        this.message = message;
    }

    public CityObject getCity() {
        return city;
    }
    
    public void setCity(CityObject city) {
        this.city = city;
    }
    
    public Integer getCnt() {
        return cnt;
    }

    public void setCnt(Integer cnt) {
        this.cnt = cnt;
    }

    public java.util.List<Forecast> getList() {
        return list;
    }

    public void setList(java.util.List<Forecast> list) {
        this.list = list;
    }
}
