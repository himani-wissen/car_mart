package myproject.repos;

import myproject.entity.Car;
import myproject.utils.DbConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CarMartDaoImpl implements CarMartDao {  

    @Override
    public void addCar(Car car) {
        String sql = "INSERT INTO cars (company, model, seater, fuelType,carType, price, sold) VALUES (?, ?, ?, ?, ? ,? , ?) RETURNING carId";

        try (Connection conn = DbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, car.getCompany());
            stmt.setString(2, car.getModel());
            stmt.setInt(3, car.getSeater());
            stmt.setString(4, car.getFuelType());
            stmt.setString(5, car.getCarType());
            stmt.setInt(6, car.getPrice());
            stmt.setBoolean(7, car.getSold());

            try (ResultSet rs = stmt.executeQuery()) {  
                if (rs.next()) {
                    car.setCarId(rs.getInt("carId"));  
                }
	        System.out.println("Car added successfully with ID: " + car.getCarId());
            }



        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Car> getAllUnsoldCars() {
        return getCarsByQuery("SELECT * FROM cars WHERE sold = false");
    }

    @Override
    public List<Car> getCarsByCompany(String company) {
        return getCarsByPreparedQuery("SELECT * FROM cars WHERE company = ?", company);
    }

    @Override
    public List<Car> getCarsByType(String type) {
        return getCarsByPreparedQuery("SELECT * FROM cars WHERE type = ?", type);
    }

    @Override
    public List<Car> getCarsByPriceRange(int minPrice, int maxPrice) {
        List<Car> carList = new ArrayList<>();
        String sql = "SELECT * FROM cars WHERE price BETWEEN ? AND ?";

        try (Connection conn = DbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, minPrice);
            stmt.setInt(2, maxPrice);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    carList.add(extractCarFromResultSet(rs));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return carList;
    }

    @Override
    public List<Car> getAllSoldCars() {
        return getCarsByQuery("SELECT * FROM cars WHERE sold = true");
    }

    @Override
    public void markCarAsSold(int carId) {
        String sql = "UPDATE cars SET sold = true WHERE carId = ?";

        try (Connection conn = DbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, carId);
            int rowsUpdated = stmt.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Status Updated");
            } else {
                System.out.println("No id found.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateCarPrice(int carId, int newPrice) {
        String sql = "UPDATE cars SET price = ? WHERE id = ?"; // Ensure column name matches DB

        try (Connection conn = DbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, newPrice);
            stmt.setInt(2, carId);
            int rowsUpdated = stmt.executeUpdate();

            if (rowsUpdated > 0) {
                System.out.println("✅ Car ID " + carId + " price updated to " + newPrice);
            } else {
                System.out.println("❌ Car ID " + carId + " not found. No update made.");
            }

        } catch (SQLException e) {
            System.err.println("⚠️ Error updating car price: " + e.getMessage());
            e.printStackTrace();
        }
}

    private List<Car> getCarsByQuery(String query) {
        List<Car> carList = new ArrayList<>();

        try (Connection conn = DbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                carList.add(extractCarFromResultSet(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return carList;
    }

    private List<Car> getCarsByPreparedQuery(String query, String param) {
        List<Car> carList = new ArrayList<>();

        try (Connection conn = DbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, param);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    carList.add(extractCarFromResultSet(rs));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return carList;
    }

    private Car extractCarFromResultSet(ResultSet rs) throws SQLException {
        Car car = new Car(
            rs.getString("company"),
            rs.getString("model"),
            rs.getInt("seater"),
            rs.getString("fuelType"),
            rs.getString("carType"),
            rs.getInt("price"),
            rs.getBoolean("sold")
        );
        car.setCarId(rs.getInt("carId"));
        return car;
    }
}
