public class Estimate {
	Supply supply;//������
	Demand demand;//������
	int tableWidth[]; 
	ProductList productList;//��ǰ����Ʈ
	Estimate(){
		supply = new Supply();
		demand = new Demand();
		productList = new ProductList();
	}
	public void setDemand(Demand demand) {
		this.demand=demand;
	}
}

