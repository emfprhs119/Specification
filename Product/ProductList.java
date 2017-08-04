package Product;

import javax.swing.JTable;

import Main.Main;
//ǰ�� ����Ʈ
public class ProductList {
	// ���̺� ����Ʈ ����
	private int maxSize;
	Product[] productArr;
	private int size = 0;
	private Product copyProduct;
	// �ʱ�ȭ
	public ProductList() {
		maxSize = Main.FrontRow;// front page count
		productArr = new Product[maxSize];
		for (int i = 0; i < maxSize; i++) {
			productArr[i] = new Product();
		}
	}

	// ��������
	void resize(int size) {
		int prevSize = maxSize;
		maxSize = size;
		Product temp[] = new Product[maxSize];
		for (int i = 0; i < maxSize; i++) {
			if (i < (maxSize < prevSize ? maxSize-1 : prevSize))
				temp[i] = productArr[i];
			else
				temp[i] = new Product();
		}
		productArr = temp;
	}

	// table to list
	void tableToData(JTable table, int index) {
		for (int i = index; i < index + table.getRowCount(); i++) {
			productArr[i].setName((String) table.getValueAt(i - index, 0));
			productArr[i].setStandard((String) table.getValueAt(i - index, 1));
			productArr[i].setMaterialCost((String) table.getValueAt(i - index, 2));
			productArr[i].setProcessedCost((String)table.getValueAt(i - index, 3));
			productArr[i].setCount((String)table.getValueAt(i - index, 4));
			productArr[i].setEtc((String) table.getValueAt(i - index, 7));
		}
	}

	// data to table
	void dataToTable(JTable table, int index) {
		for (int i = 0; i < table.getRowCount(); i++) {
			table.setValueAt(productArr[i + index].getName(), i, 0);
			table.setValueAt(productArr[i + index].getStandard(), i, 1);
			table.setValueAt(productArr[i + index].getMaterialCost(), i, 2);
			table.setValueAt(productArr[i + index].getProcessedCost(), i, 3);
			table.setValueAt(productArr[i + index].getCount(), i, 4);
			table.setValueAt(productArr[i + index].getEtc(), i, 7);
		}
	}

	// �հ� �ݾ�
	public long getSumMoney() {
		long sumMoney = 0;
		for (int i = 0; i < maxSize; i++) {
			sumMoney += productArr[i].getSumMoney();
		}
		return sumMoney;
	}
	//----------------------------------------
	// --------����Ʈ ����-----------------------
	//----------------------------------------
	public void addPage() {
		resize(maxSize + Main.BackRow);
	}
	public void removePage(){
		resize(maxSize - Main.BackRow);
	}
	// �����Ͱ� ���������� true
	public boolean isBlankLastPage() {
		boolean flag=true;
		if (maxSize==Main.FrontRow)
			return false;
		for (int i = maxSize-1; i >= maxSize-Main.BackRow-1; i--) {
			if (!productArr[i].isNull()){
				flag=false;
			}
		}
		return flag;
	}
	// �� �߰� (������ �������� �������࿡ �����Ͱ� ������� addPage())
	public void addRow(int index) {
		for (int i = maxSize - 1; i > index; i--) {
			if (i==maxSize-1 && !productArr[i].isNull()){
				addPage();
				productArr[i+1] = productArr[i];
			}
			productArr[i] = productArr[i - 1];
		}
		productArr[index] = new Product();
	}
	// �� ���� (������ �������� �����Ͱ� ������� removePage())
	public void removeRow(int index) {
		for (int i = index; i < maxSize - 2; i++) {
			productArr[i] = productArr[i + 1];
		}
		productArr[maxSize-1]=new Product();
		if (isBlankLastPage()){
			removePage();
		}
	}
	// �� ����
	public void copyRow(int index) {
		this.copyProduct=productArr[index];
	}
	// �� �ٿ��ֱ�
	public void pasteRow(int index) {
		addRow(index);
		productArr[index].setProduct(copyProduct);
	}
	// �� ���� �̵�
	public void shiftUpRow(int index){
		if(index>0)
			swapProductRow(index-1,index);
	}
	// �� �Ʒ��� �̵�
	public void shiftDownRow(int index){
		if(index<maxSize-1)
			swapProductRow(index,index+1);
		else{
			addPage();
			swapProductRow(index,index+1);
		}
	}
	// �� �� ��ȯ
	private void swapProductRow(int index1, int index2) {
		Product product=productArr[index1];
		productArr[index1]=productArr[index2];
		productArr[index2]=product;
	}

	// ----------------------------------------
	// ��ǰ �߰�
	public void addProduct(String[] stn) {
		if (size>=maxSize){
			resize(maxSize+Main.BackRow);
		}
		productArr[size]=new Product(stn);
		
		size++;
	}

	public int getMaxSize() {
		return maxSize;
	}
	public Product getProduct(int index){
		if (productArr[index]==null)
			return new Product();
		return productArr[index];
	}

	public void setData(String paste, int selectedRow, int selectedColumn) {
		switch(selectedColumn){
		case 0:productArr[selectedRow].setName(paste);break;
		case 1:productArr[selectedRow].setStandard(paste);break;
		case 2:productArr[selectedRow].setMaterialCost(paste);break;
		case 3:productArr[selectedRow].setProcessedCost(paste);break;
		case 4:productArr[selectedRow].setCount(paste);break;
		case 7:productArr[selectedRow].setEtc(paste);break;
		}
	}

	
}
