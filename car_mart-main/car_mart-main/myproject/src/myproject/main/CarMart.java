package myproject.main;

import myproject.controller.CarMartController;

public class CarMart {
    public static void main(String[] args) {
        System.out.println("Welcome to Car Mart!");
        CarMartController controller = new CarMartController();  
        controller.menu(); 
    }
}
