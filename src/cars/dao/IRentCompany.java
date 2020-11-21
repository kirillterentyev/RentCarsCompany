package cars.dao;

import cars.domain.Car;
import cars.domain.Driver;
import cars.domain.Model;
import cars.domain.RentRecord;
import cars.dto.CarsReturnCode;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Stream;

public interface IRentCompany {
    CarsReturnCode addModel (Model model);
    CarsReturnCode addCar (Car car);
    CarsReturnCode addDriver (Driver driver);
    Model getModel (String modelName);
    Car getCar (long licenseId);
    CarsReturnCode rentCar (String regNumber, long licenseId, LocalDate rentDate, int rentDates);
    CarsReturnCode returnCar (String regNumber, long licenseId, LocalDate returnDate, int gasTankPercent, int damages); //damage up to 10% is GOOD, up to 30% is bad. After 30% - remove car
    CarsReturnCode removeCar (String regNumber); //removing car is setting flRemoved true
    List<Car> clear (LocalDate currentDate, int days);  //all cars for which the returnDate before currentDate
    // - days with flRemoved are deleted from an information model along with all related records. It returns list of the deleted cars
    List<Driver> getCarDrivers (String regNumber); //returns all drivers that have been renting cars
    List<Car> getDriverCars (long licenseId); //returns all cars that have been rented
    Stream<Car> getAllCars();
    Stream<Driver> getAllDrivers();
    Stream<RentRecord> getAllRecords();
    List<String> getMostPopularModelNames(); //can names used the most times
    double getModelProfit (String modelName); // returns amount of money received from the renting cars of certain model
    List<String> getMostProfitModelNames(); //returns list of the most profitable models
}
