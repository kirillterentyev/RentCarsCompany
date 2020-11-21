package cars.domain;

import java.time.LocalDate;

public class RentRecord {
    private long licenseId;
    private String regNumber;
    private LocalDate rentDate;
    private LocalDate returnDate;
    private int gasTankPercent;         //percent of full tank at return
    private int rentDays;               //rent period with prepayment
    private double cost;
    private int damages;                //percent of damages

    public RentRecord(long licenseId, String regNumber, LocalDate rentDate, int rentDays) {
        this.licenseId = licenseId;
        this.regNumber = regNumber;
        this.rentDate = rentDate;
        this.rentDays = rentDays;
    }

    public long getLicenseId() {
        return licenseId;
    }

    public String getRegNumber() {
        return regNumber;
    }

    public LocalDate getRentDate() {
        return rentDate;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public int getGasTankPercent() {
        return gasTankPercent;
    }

    public int getRentDays() {
        return rentDays;
    }

    public double getCost() {
        return cost;
    }

    public int getDamages() {
        return damages;
    }

    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }

    public void setGasTankPercent(int gasTankPercent) {
        this.gasTankPercent = gasTankPercent;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public void setDamages(int damages) {
        this.damages = damages;
    }

    @Override
    public String toString() {
        return "RentRecord{" +
                "licenseId=" + licenseId +
                ", regNumber='" + regNumber + '\'' +
                ", rentDate=" + rentDate +
                ", returnDate=" + returnDate +
                ", gasTankPercent=" + gasTankPercent +
                ", rentDays=" + rentDays +
                ", cost=" + cost +
                ", damages=" + damages +
                '}';
    }
}
