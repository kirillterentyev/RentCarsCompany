package cars.dao;

public abstract class AbstractRentCompany implements IRentCompany {
    int finePercent;
    int gasPrice;

    public AbstractRentCompany() {
        this.finePercent = 15;
        this.gasPrice = 10;         //dollars
    }

    public int getFinePercent() {
        return finePercent;
    }

    public void setFinePercent(int finePercent) {
        this.finePercent = finePercent;
    }

    public int getGasPrice() {
        return gasPrice;
    }

    public void setGasPrice(int gasPrice) {
        this.gasPrice = gasPrice;
    }
}
