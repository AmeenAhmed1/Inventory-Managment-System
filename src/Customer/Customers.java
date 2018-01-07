package Customer;

/**
 *
 * @author AmeenAhmed
 */
public class Customers {

    private int id;
    private String name, gender, phoneno, address, email, company;
    
    public Customers(){
        super();
    }
    
    
    public Customers(int id,String n, String g, String p , String a, String e, String c){
        super();
        this.id = id;
        this.name = n;
        this.gender = g;
        this.phoneno = p;
        this.address = a;
        this.email = e;
        this.company = c;
    }
    
    
    public void setID(int i) {
	this.id = i;
    }
    public int getID() {
	return id;
    }
    
    public void setName(String n) {
	this.name = n;
    }
    public String getName() {
	return name;
    }
    
    public void setGender(String g) {
	this.gender = g;
    }
    public String getGender() {
	return gender;
    }
    
    public void setPhone(String p) {
	this.phoneno = p;
    }
    public String getPhone() {
	return phoneno;
    }
    
    public void setAddress(String a) {
	this.address = a;
    }
    public String getAddress() {
	return address;
    }
    
    public void setEmail(String e){
        this.email = e;
    }
    public String getEmail(){
        return email;
    }
    
    public void setCompany(String c){
        this.company = c;
    }
    public String getCompany(){
        return company;
    }
}
