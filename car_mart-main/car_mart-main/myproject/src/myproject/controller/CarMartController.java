package myproject.controller;

import myproject.entity.Car;
import myproject.repos.CarMartDao;
import myproject.repos.CarMartDaoImpl;
import myproject.utils.DbConnection;  
import java.util.List;
import java.util.Scanner;

public class CarMartController {  
    private CarMartDao carDao;
    private Scanner scanner;

    public CarMartController() {  
        this.carDao = new CarMartDaoImpl(); 
        this.scanner = new Scanner(System.in);
    }

    public void addCar() {
        System.out.println("Enter Company Name: ");
        String company = scanner.nextLine();

        System.out.println("Enter Model Name: ");
        String model = scanner.nextLine();

        System.out.println("Enter Seater Capacity: ");
        int seater = scanner.nextInt();
        scanner.nextLine(); 

        System.out.println("Enter Fuel Type (Petrol/Diesel/Electric): ");
        String fuelType = scanner.nextLine();
        
        System.out.println("Enter Car Type (Sedan/Hatchback/SUV): ");
        String carType = scanner.nextLine();

        System.out.println("Enter Price: ");
        int price = scanner.nextInt();

        Car car = new Car(company, model, seater, fuelType, carType, price, false);
        carDao.addCar(car);
        System.out.println("Car added successfully.");
    }

    public void search() {
        while (true) {
            System.out.println("\n2. Search");
            System.out.println("-----------");
            System.out.println("1. All Unsold Cars");
            System.out.println("2. All Cars of Specific Company");
            System.out.println("3. All Cars of Specific Type");
            System.out.println("4. All Cars in the Price Range (Min to Max)");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");

            int searchChoice = scanner.nextInt();
            scanner.nextLine(); 

            switch (searchChoice) {
                case 1 -> displayAllUnsoldCars();
                case 2 -> searchByCompany();
                case 3 -> searchByType();
                case 4 -> searchByPriceRange();
                case 5 -> { return; }
                default -> System.out.println("Invalid choice. Try again.");
            }
        }
    }

    public void displayAllUnsoldCars() {
        List<Car> cars = carDao.getAllUnsoldCars();
        if (cars.isEmpty()) {
            System.out.println("No unsold cars available.");
        } else {
            for (Car car : cars) {
                System.out.println(car);
            }
        }
    }

    public void searchByCompany() {
        System.out.print("Enter Company Name: ");
        String company = scanner.nextLine();
        List<Car> cars = carDao.getCarsByCompany(company);
        if (cars.isEmpty()) {
            System.out.println("No cars found for company: " + company);
        } else {
            for (Car car : cars) {
                System.out.println(car);
            }
        }
    }

    public void searchByType() {
        System.out.print("Enter Car Type (Sedan/Hatchback/SUV): ");
        String carType = scanner.nextLine();
        List<Car> cars = carDao.getCarsByType(carType);
        if (cars.isEmpty()) {
            System.out.println("No " + carType + " cars found.");
        } else {
            for (Car car : cars) {
                System.out.println(car);
            }
        }
    }

    public void searchByPriceRange() {
        System.out.print("Enter Minimum Price: ");
        int minPrice = scanner.nextInt();

        System.out.print("Enter Maximum Price: ");
        int maxPrice = scanner.nextInt();
        scanner.nextLine();

        List<Car> cars = carDao.getCarsByPriceRange(minPrice, maxPrice);
        if (cars.isEmpty()) {
            System.out.println("No cars found in the price range: " + minPrice + " - " + maxPrice);
        } else {
            for (Car car : cars) {
                System.out.println(car);
            }
        }
    }
    public void update() {
        System.out.print("Enter Car ID to update price: ");
        int carId = scanner.nextInt();

        System.out.print("Enter new price: ");
        int newPrice = scanner.nextInt();

        carDao.updateCarPrice(carId, newPrice);
    }

    void sold() {
        while (true) {
            System.out.println("\n4. Sold");
            System.out.println("------------");
            System.out.println("1. ALL Sold Cars");
            System.out.println("2. Update Stauts of Unsold Car");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");

            int soldChoice = scanner.nextInt();
            scanner.nextLine(); 

            switch (soldChoice) {
                case 1 -> displayAllSoldCars();
                case 2 -> markCarAsSold();
                case 3 -> { return; }
                default -> System.out.println("Invalid choice. Try again.");
            }
        }
    }

    public void displayAllSoldCars() {
        List<Car> cars = carDao.getAllSoldCars();
        if (cars.isEmpty()) {
            System.out.println("No sold cars available.");
        } else {
            for (Car car : cars) {
                System.out.println(car);
            }
        }
    }

    public void markCarAsSold() {
        System.out.print("Enter Car ID to mark as SOLD: ");
        int carId = scanner.nextInt();
        scanner.nextLine();

        carDao.markCarAsSold(carId);
    }


    public void menu() {
        while (true) {
            System.out.println("\n1. Add Car");
            System.out.println("2. Search");
            System.out.println("3. Update");
            System.out.println("4. Sold");
            System.out.println("5. Exit");
            System.out.print("Enter choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); 

            switch (choice) {
                case 1 -> addCar();
                case 2 -> search();
                case 3 -> update();
                case 4 -> sold();
                case 5 -> {
                    System.out.println("Closing database connection...");
                    DbConnection.closeConnection(); 
                    System.out.println("Exiting...");
                    scanner.close();
                    return;
                }
                default -> System.out.println("Invalid choice. Try again.");
            }
        }
    }
}
