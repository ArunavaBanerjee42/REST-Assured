package petConfig;

public class Order {
	private int petId;
	private int quantity;
	private String shipDate;
	private String status;
	private Boolean complete;
	public Order(int petId, int quantity, String shipDate, String status, Boolean complete) {
		setComplete(complete);
		setPetId(petId);
		setQuantity(quantity);
		setShipDate(shipDate);
		setStatus(status);
	}
	public int getPetId() {
		return petId;
	}
	public void setPetId(int petId) {
		this.petId = petId;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public String getShipDate() {
		return shipDate;
	}
	public void setShipDate(String shipDate) {
		this.shipDate = shipDate;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Boolean getComplete() {
		return complete;
	}
	public void setComplete(Boolean complete) {
		this.complete = complete;
	}
	
}
