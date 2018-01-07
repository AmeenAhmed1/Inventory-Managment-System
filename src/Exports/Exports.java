package Exports;

/**
 *
 * @author AmeenAhmed
 */
public class Exports {

    private String BarCode, Customer, Date;
    private int Quantity, id;

    public Exports() {
        super();
    }

    public Exports(int id, String BC, int Q, String C, String D) {
        super();
        this.id = id;
        this.BarCode = BC;
        this.Customer = C;
        this.Quantity = Q;
        this.Date = D;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }

    public void setCode(String Code) {
        this.BarCode = Code;
    }

    public String getCode() {
        return this.BarCode;
    }

    public void setCustomer(String Cu) {
        this.Customer = Cu;
    }

    public String getCustomer() {
        return this.Customer;
    }

    public void setQuantity(int Q) {
        this.Quantity = Q;
    }

    public int getQuantity() {
        return this.Quantity;
    }

    public void setDate(String D) {
        this.Date = D;
    }

    public String getDate() {
        return this.Date;
    }
}
