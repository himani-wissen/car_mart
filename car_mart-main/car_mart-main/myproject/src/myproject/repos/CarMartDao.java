package myproject.repos;

import myproject.entity.Car;
import java.util.List;

public interface CarMartDao {
    
    void addCar(Car car);

    List<Car> getAllUnsoldCars();


    List<Car> getCarsByCompany(String company);


    List<Car> getCarsByType(String type);


    List<Car> getCarsByPriceRange(int minPrice, int maxPrice);

    List<Car> getAllSoldCars();


    void markCarAsSold(int carId);

    void updateCarPrice(int carId, int newPrice);
}
