package Estimate;
import Demand.Demand;
import Product.ProductList;
import Supply.Supply;
//������ ������
public class Estimate {
	private Supply supply;	//������
	private Demand demand;	//������
	private ProductList productList;	//��ǰ����Ʈ
	private int tableWidth[];	// ��ǰ ���̺� �� ����ũ��
	public Estimate(){
		supply = new Supply();
		demand = new Demand();
		productList = new ProductList();
	}
	public Supply getSupply() {
		return supply;
	}
	public void setSupply(Supply supply) {
		this.supply = supply;
	}
	public int[] getTableWidth() {
		return tableWidth;
	}
	public void setTableWidth(int[] tableWidth) {
		this.tableWidth = tableWidth;
	}
	public ProductList getProductList() {
		return productList;
	}
	public void setProductList(ProductList productList) {
		this.productList = productList;
	}
	public void setDemand(Demand demand) {
		this.demand=demand;
	}
	public Demand getDemand() {
		return demand;
	}
}

