package cars.domain;

public class Model {
    private String model;
    private int gasTank;        //in liters
    private String company;
    private String country;
    private int priceDay;       //price per rent day no delay

    public Model(String model, int gasTank, String company, String country, int priceDay) {
        this.model = model;
        this.gasTank = gasTank;
        this.company = company;
        this.country = country;
        this.priceDay = priceDay;
    }

    public String getModel() {
        return model;
    }

    public int getGasTank() {
        return gasTank;
    }

    public String getCompany() {
        return company;
    }

    public String getCountry() {
        return country;
    }

    public int getPriceDay() {
        return priceDay;
    }

    public void setPriceDay(int priceDay) {
        this.priceDay = priceDay;
    }

    @Override
    public String toString() {
        return "Model{" +
                "model='" + model + '\'' +
                ", gasTank=" + gasTank +
                ", company='" + company + '\'' +
                ", country='" + country + '\'' +
                ", priceDay=" + priceDay +
                '}';
    }
}
