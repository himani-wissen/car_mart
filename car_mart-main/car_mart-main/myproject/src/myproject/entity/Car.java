package myproject.entity;
public class Car {
    private int carId; 
    private String company;
    private String model;
    private int seater;
    private String fuelType;
    private String carType;
    private int price;
    private Boolean sold;


    public Car() {}


    public Car(String company, String model, int seater, String fuelType,String carType, int price, Boolean sold) {
        this.company = company;
        this.model = model;
        this.seater = seater;
        this.fuelType = fuelType;
        this.carType=carType;
        this.price = price;
        this.sold = sold;
    }


    public String getCarType() {
		return carType;
	}


	public void setCarType(String carType) {
		this.carType = carType;
	}


	public int getCarId() {
        return carId;
    }

    public void setCarId(int carId) { 
        this.carId = carId;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getSeater() {
        return seater;
    }

    public void setSeater(int seater) {
        this.seater = seater;
    }

    public String getFuelType() {
        return fuelType;
    }

    public void setFuelType(String fuelType) {
        this.fuelType = fuelType;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public Boolean getSold() {  
        return sold;
    }

    public void setSold(Boolean sold) {
        this.sold = sold;
    }


    @Override
    public String toString() {
        return "Car{" +
                "carId=" + carId +
                ", company='" + company + '\'' +
                ", model='" + model + '\'' +
                ", seater=" + seater +
                ", fuelType='" + fuelType + '\'' +
                ", price=" + price +
                ", sold=" + sold +
                '}';
    }
}
