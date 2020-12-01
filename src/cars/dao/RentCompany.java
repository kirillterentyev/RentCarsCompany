package cars.dao;

import cars.domain.Car;
import cars.domain.Driver;
import cars.domain.Model;
import cars.domain.RentRecord;
import cars.dto.CarsReturnCode;
import cars.dto.State;
import cars.dto.WrongArgumentException;

import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Stream;

public class RentCompany extends AbstractRentCompany {
    Map<Long, Driver> drivers = new HashMap<>();            //создаю мапу где номер прав - ключ и значение это водитель
    Map<String, Car> cars = new HashMap<>();                // ключ номер машины и значение это машина
    Map<String, Model> models = new HashMap<>();            // ключ это модель машины, а значение это данные о модели

    Map<String, List<RentRecord>> carRecords = new HashMap<>();                 // ключ это номер машины, значение лист записей этой машины
    Map<Long, List<RentRecord>> driverRecords = new HashMap<>();                 // ключ номер прав, значение лист записей этого водителя
    TreeMap<LocalDate, List<RentRecord>> returnedRecords = new TreeMap<>();     // ключ дата возврата, значение лист записей за эту дату


    @Override
    public CarsReturnCode addModel(Model model) {
        if (model == null) {
            throw new WrongArgumentException("model is null");
        }

        if (!models.containsKey(model.getModel())) {
            models.put(model.getModel(), model);
            return CarsReturnCode.OK;
        }
        return CarsReturnCode.MODEL_EXISTS;
    }

    @Override
    public CarsReturnCode addCar(Car car) {
        if (car == null) {
            throw new WrongArgumentException("car is null");
        }
        if (!models.containsKey(car.getModelName())) {
            return CarsReturnCode.NO_MODEL;
        }

        if (!cars.containsKey(car.getRegNumber())) {
            cars.put(car.getRegNumber(), car);
            return CarsReturnCode.OK;
        }
        return CarsReturnCode.CAR_EXISTS;
    }


    @Override
    public CarsReturnCode addDriver(Driver driver) {
        if (driver == null) {
            throw new WrongArgumentException("driver is null");
        }
        if (!drivers.containsKey(driver.getLicenseId())) {
            drivers.put(driver.getLicenseId(), driver);
            return CarsReturnCode.OK;
        }
        return CarsReturnCode.DRIVER_EXISTS;
    }


    @Override
    public Model getModel(String modelName) {
        return models.get(modelName);
    }

    @Override
    public Car getCar(long licenseId) {
        return cars.get(licenseId);
    }

    @Override
    public CarsReturnCode rentCar(String regNumber, long licenseId, LocalDate rentDate, int rentDays) {
        CarsReturnCode code = checkRentCar(regNumber, licenseId, rentDate);

        if (code != CarsReturnCode.OK) return code;

        RentRecord rentRecord = new RentRecord(licenseId, regNumber, rentDate, rentDays);

        addCarRecords(rentRecord);
        addDriverRecords(rentRecord);
        setInUse(regNumber);
        return CarsReturnCode.OK;
    }

    private CarsReturnCode checkRentCar(String regNumber, long licenseId, LocalDate rentDate) {
        if (rentDate == null) {
            throw new WrongArgumentException("rent date null");
        }
        if (!drivers.containsKey(licenseId)) {
            return CarsReturnCode.NO_DRIVER;
        }
        if (!cars.containsKey(regNumber)) {
            return CarsReturnCode.CAR_NOT_EXISTS;
        }
        if (cars.get(regNumber).isInUse()) {
            return CarsReturnCode.CAR_IN_USE;
        }
        return CarsReturnCode.OK;
    }

    public void setInUse (String regNumber){
        cars.get(regNumber).setInUse(true);
    }
    public void addCarRecords(RentRecord rentRecord){
        String regNumber = rentRecord.getRegNumber();
        if (!carRecords.containsKey(regNumber)) {
            carRecords.put(regNumber, new ArrayList<>());
        }
        carRecords.get(regNumber).add(rentRecord);
    }
    public void addDriverRecords(RentRecord rentRecord){
        long licenseId = rentRecord.getLicenseId();
        if (!driverRecords.containsKey(licenseId)) {
            driverRecords.put(licenseId, new ArrayList<>());
        }
        driverRecords.get(licenseId).add(rentRecord);
    }
        //todo сделать записи в carRecords и driverRecords разделить на три метода


//Map<Long, Driver> drivers = new HashMap<>();            //создаю мапу где номер прав - ключ и значение это водитель
    //    Map<String, Car> cars = new HashMap<>();                // ключ номер машины и значение это машина
    //    Map<String, Model> models = new HashMap<>();            // ключ это модель машины, а значение это данные о модели

    //Map<String, List<RentRecord>> carRecords = new HashMap<>();                 // ключ это номер машины, значение лист записей этой машины
    //Map<Long, List<RentRecord>> driverRecord = new HashMap<>();                 // ключ номер прав, значение лист записей этого водителя
    //TreeMap<LocalDate, List<RentRecord>> returnedRecords = new TreeMap<>();     // ключ дата возврата, значение лист записей за эту дату

    @Override
    public CarsReturnCode returnCar(String regNumber, long licenseId, LocalDate returnDate,
                                    int gasTankPercent, int damages) {
        RentRecord rentRecord = getRentRecord(regNumber, licenseId);
        CarsReturnCode code = checkReturnCar(licenseId, returnDate, rentRecord);
        if(code != CarsReturnCode.OK){
            return code;
        }

        //оценить состояние машины (damage, inUse)
        Car car = carSetting(regNumber, damages);
        //сделать записи в rentRecord (returnDate, damage, gas)
        rentRecord.setReturnDate(returnDate);
        rentRecord.setDamages(damages);
        rentRecord.setGasTankPercent(gasTankPercent);

        //Посчитать стоимость всей аренды (length, model, delay)
//        Car car = cars.get(regNumber);
//        String modelName = car.getModelName();
//        Model model = models.get(modelName);
//
//        calcCost(model, rentRecord);
        double cost = calcCost(models.get(car.getModelName()), rentRecord);
        rentRecord.setCost(cost);


    }

    private double calcCost (Model model, RentRecord rentRecord){
        int carRentBasicCost = rentRecord.getRentDays() * model.getPriceDay();
        int totalDaysRent = Period.between(rentRecord.getRentDate(), rentRecord.getReturnDate()).getDays();
//        int totalDaysRent = (int) ChronoUnit.DAYS.between(rentRecord.getRentDate(), rentRecord.getReturnDate());
        double penalty = (totalDaysRent - rentRecord.getRentDays()) * (model.getPriceDay()/100f) * (100 + getFinePercent());
        double gasSpent = (model.getGasTank()/100f) * (100 - rentRecord.getGasTankPercent()) * gasPrice;
        penalty = penalty > 0 ? penalty : 0;
        return carRentBasicCost + penalty + gasSpent;
    }

    private Car carSetting (String regNumber, int damages){
        Car car = cars.get(regNumber);
        car.setInUse(false);
        if (damages <= 10 && damages > 0){
            car.setState(State.GOOD);
        }
        if (damages > 10 && damages <= 30){
            car.setState(State.BAD);
        }
        if (damages > 30){
            car.setFlRemoved(true);
        }
        return car;
    }

    private CarsReturnCode checkReturnCar (long licenseId, LocalDate returnDate, RentRecord rentRecord){
        if (!drivers.containsKey(licenseId)){
            return CarsReturnCode.NO_DRIVER;
        }
//        if (!cars.containsKey(regNumber) || !cars.get(regNumber).isInUse() || rentRecord == null) //rentRecord null если первое или второе высказывание истинно
        if (rentRecord == null){
            return CarsReturnCode.CAR_NO_RENTED;
        }
        if (returnDate.isBefore(rentRecord.getRentDate())){
            return CarsReturnCode.RETURN_DATE_WRONG;
        }
        return CarsReturnCode.OK;
    }



    private RentRecord getRentRecord(String regNumber, long licenseId){
        Optional<RentRecord> record = carRecords.get(regNumber).stream()
                .filter(r -> r.getReturnDate() == null && r.getLicenseId() == licenseId)
                .findFirst();
//        if (record.isPresent()){
//            return record.get();
//        }
//        return null;
        return record.orElse(null);
    }

    @Override
    public CarsReturnCode removeCar(String regNumber) {
        return null;
        //todo!!!!
    }

    @Override
    public List<Car> clear(LocalDate currentDate, int days) {
        return null;
        //todo..?
    }

    @Override
    public List<Driver> getCarDrivers(String regNumber) {
        return null;
    }

    @Override
    public List<Car> getDriverCars(long licenseId) {
        return null;
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
