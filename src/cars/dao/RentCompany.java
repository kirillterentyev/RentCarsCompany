package cars.dao;

import cars.domain.Car;
import cars.domain.Driver;
import cars.domain.Model;
import cars.domain.RentRecord;
import cars.dto.CarsReturnCode;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public class RentCompany extends AbstractRentCompany{
    Map<Long, Driver> drivers = new HashMap<>();            //создаю мапу где номер прав - ключ и значение это водитель
    Map<String, Car> cars = new HashMap<>();                // ключ номер машины и значение это машина
    Map<String, Model> models = new HashMap<>();            // ключ это модель машины, а значение это данные о модели

    @Override
    public CarsReturnCode addModel(Model model) {
        if(model == null){
            throw new IllegalArgumentException("model is null");
        }

        if (!models.containsKey(model.getModel())) {
            models.put(model.getModel(), model);
            return CarsReturnCode.OK;
        }
        return CarsReturnCode.MODEL_EXISTS;
    }

    @Override
    public CarsReturnCode addCar(Car car) {
        return null;
        //TODO DZ
    }

    @Override
    public CarsReturnCode addDriver(Driver driver) {
        return null;
        //TODO DZ
    }

    @Override
    public Model getModel(String modelName) {
        return null;
        //TODO DZ
    }

    @Override
    public Car getCar(long licenseId) {
        return null;
        //TODO DZ
    }

    @Override
    public CarsReturnCode rentCar(String regNumber, long licenseId, LocalDate rentDate, int rentDates) {
        return null;
    }

    @Override
    public CarsReturnCode returnCar(String regNumber, long licenseId, LocalDate returnDate, int gasTankPercent, int damages) {
        return null;
    }

    @Override
    public CarsReturnCode removeCar(String regNumber) {
        return null;
    }

    @Override
    public List<Car> clear(LocalDate currentDate, int days) {
        return null;
    }

    @Override
    public List<Driver> getCarDrivers(String regNumber) {
        return null;
        //TODO DZ
    }

    @Override
    public List<Car> getDriverCars(long licenseId) {
        return null;
        //TODO DZ
    }

    @Override
    public Stream<Car> getAllCars() {
        return null;

    }

    @Override
    public Stream<Driver> getAllDrivers() {
        return null;

    }

    @Override
    public Stream<RentRecord> getAllRecords() {
        return null;
    }

    @Override
    public List<String> getMostPopularModelNames() {
        return null;
    }

    @Override
    public double getModelProfit(String modelName) {
        return 0;
    }

    @Override
    public List<String> getMostProfitModelNames() {
        return null;
    }
}
