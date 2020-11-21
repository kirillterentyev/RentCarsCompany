package cars.tests;

import cars.dao.RentCompany;
import cars.domain.Model;
import cars.dto.CarsReturnCode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


class RentCompanyTest {
    RentCompany rentCompany;

    @BeforeEach
    void setUp() {
        rentCompany =new RentCompany();
    }

    @Test
    void addModel() {
        Model model = new Model("granta", 40, "lada", "rus", 1000);
        CarsReturnCode actual = rentCompany.addModel(model);
        assertEquals(CarsReturnCode.OK, actual);
        rentCompany.addModel(null);

    }

    @Test
    void addCar() {
    }

    @Test
    void addDriver() {
    }

    @Test
    void getModel() {
    }

    @Test
    void getCar() {
    }

    @Test
    void rentCar() {
    }

    @Test
    void returnCar() {
    }

    @Test
    void removeCar() {
    }

    @Test
    void clear() {
    }

    @Test
    void getCarDrivers() {
    }

    @Test
    void getDriverCars() {
    }

    @Test
    void getAllCars() {
    }

    @Test
    void getAllDrivers() {
    }

    @Test
    void getAllRecords() {
    }

    @Test
    void getMostPopularModelNames() {
    }

    @Test
    void getModelProfit() {
    }

    @Test
    void getMostProfitModelNames() {
    }
}