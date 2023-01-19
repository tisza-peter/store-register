package hu.lechnerkozpont.bootcamp.storage.entity;

public class StoreItem {
	private String name;
	private int quantity;

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public StoreItem(String name, int quantity) {
		this.name = name;
		this.quantity = quantity;
	}

	public String getName()
	{
		return name;
	}
	
	public void setName(String name)
	{
		this.name = name;
	}
}

