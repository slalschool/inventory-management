package pizzeria;

public class Pizza {
	
	private int id;
	private String name;
	private int pepperoni;
	private int sausage;
	private int bacon;
	private int quantity; 
	
	public Pizza(int id, String name, int pepperoni, int sausage, int bacon, int quantity) {
		this.id = id;
		this.name = name;
		this.pepperoni = pepperoni;
		this.sausage = sausage;
		this.bacon = bacon;
		this.quantity = quantity; 
	}
	
	public int getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public int getPepperoni() {
		return pepperoni; 
	}
	
	public void setPepperoni(int pepperoni) {
		this.pepperoni = pepperoni;
	}
	
	public int getSausage() {
		return sausage; 
	}
	
	public void setSausage(int sausage) {
		this.sausage = sausage;
	}
	
	public int getBacon() {
		return bacon; 
	}
	
	public void setBacon(int bacon) {
		this.bacon = bacon;
	}
	
	public int getQuantity() {
		return quantity;
	}
	
	public void setQuantity(int quantity) {
		this.quantity= quantity; 
	}
	
	public void purchaseMe() { 
		if (quantity > 0) {
			quantity--; 
		}
	}

}