package tmpContainers;

import com.google.gson.annotations.SerializedName;

public class ProdNameIDContainer
{
	@SerializedName("product_id")
	private short product_id;
	@SerializedName("product_name")
	private String product_name;
	
	public short getProduct_id() {
		return product_id;
	}

	public void setProduct_id(short product_id) {
		this.product_id = product_id;
	}

	public String getProduct_name() {
		return product_name;
	}

	public void setProduct_name(String product_name) {
		this.product_name = product_name;
	}
	
	public ProdNameIDContainer(String i_product_name, short i_product_id)
	{
		product_name=i_product_name;
		product_id=i_product_id;
	}
}
