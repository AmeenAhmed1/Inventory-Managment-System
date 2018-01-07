package Report;

/**
 *
 * @author AmeenAhmed
 */
public class Report {

    int id, Quantity;
    String BarCode;
    
    public Report(){
        super();
    }
    
    public Report(int id, String Code, int Q){
        super();
        this.id = id;
        this.Quantity = Q;
        this.BarCode = Code;
    }
    
    public void setID(int id){
        this.id = id;
    }
    
    public int getID(){
        return this.id;
    }
    
    public void setBarCode(String B){
        this.BarCode = B;
    }
    
    public String getBarCode(){
        return this.BarCode;
    }
    
    public void setQuantity(int Q){
        this.Quantity = Q;
    }
    
    public int getQuantity(){
        return this.Quantity;
    }
}
