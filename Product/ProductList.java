package Product;

import java.sql.ResultSet;
import java.util.ArrayList;

import javax.swing.JTable;

import Inheritance.List_Interface;
import Main.Main;

//품목 리스트
public class ProductList extends ArrayList<Product> implements List_Interface<Product> {
	String specID;
	private Product copyProduct;

	public ProductList() {
		resize(Main.FrontRow + 1);
	}

	// table to data
	void tableToData(JTable table, int index) {
		for (int i = index; i < index + table.getRowCount(); i++) {
			get(i).setDate((String) table.getValueAt(i - index, 0));
			get(i).setCode((String) table.getValueAt(i - index, 1));
			get(i).setName((String) table.getValueAt(i - index, 2));
			get(i).setStandard((String) table.getValueAt(i - index, 3));
			get(i).setCount((String) table.getValueAt(i - index, 4));
			get(i).setCost((String) table.getValueAt(i - index, 5));
		}
	}

	// data to table
	void dataToTable(JTable table, int index) {
		for (int i = 0; i < table.getRowCount(); i++) {
			table.setValueAt(get(i + index).getDate(), i, 0);
			table.setValueAt(get(i + index).getCode(), i, 1);
			table.setValueAt(get(i + index).getName(), i, 2);
			table.setValueAt(get(i + index).getStandard(), i, 3);
			table.setValueAt(get(i + index).getCount(), i, 4);
			table.setValueAt(get(i + index).getCost(), i, 5);
		}
	}

	// 합계 금액
	public long getSumMoney() {
		long sumMoney = 0;
		for (int i = 0; i < size(); i++) {
			sumMoney += get(i).getSumMoney();
		}
		return sumMoney;
	}
	// ----------------------------------------
	// --------리스트 조작-----------------------
	// ----------------------------------------

	private void resize(int maxSize) {
		while (size() < maxSize)
			add(new Product());
		while (size() > maxSize)
			remove(size() - 1);
	}

	public void addPage() {
		resize(size() + Main.BackRow);
	}

	public void removePage() {
		resize(size() - Main.BackRow);
	}
	public boolean isHasData(){
		boolean flag = false;
		for(int i=0;i<size();i++)
			if (get(i).getName()!=null && !get(i).getName().equals(""))
				flag=true;
		return flag;
	}
	// 데이터가 비어있을경우 true
	public boolean isBlankLastPage() {
		boolean flag = true;
		if (size() == Main.FrontRow+1)
			return false;
		for (int i = size() - 1; i >= size() - Main.BackRow; i--) {
			if (!get(i).isNull()) {
				flag = false;
			}
		}
		return flag;
	}

	// 행 추가 (마지막 페이지의 마지막행에 데이터가 있을경우 addPage())
	public void addRow(int index) {

		if (!get(size() - 2).isNull())
			addPage();
		add(index, new Product());
		remove(size() - 1);
	}

	// 행 제거 (마지막 페이지에 데이터가 없을경우 removePage())
	public void removeRow(int index) {
		remove(index);
		add(new Product());
		if (isBlankLastPage()) {
			removePage();
		}
	}

	// 행 복사
	public void copyRow(int index) {
		this.copyProduct = get(index);
	}

	// 행 붙여넣기
	public void pasteRow(int index) {
		addRow(index);
		get(index).setProduct(copyProduct);
	}

	// 행 위로 이동
	public void shiftUpRow(int index) {
		if (index > 0)
			swapProductRow(index - 1, index);
	}

	// 행 아래로 이동
	public void shiftDownRow(int index) {
		if (index < size() - 2)
			swapProductRow(index, index + 1);
		else {
			addPage();
			swapProductRow(index, index + 1);
		}
	}

	// 행 간 교환
	private void swapProductRow(int index1, int index2) {
		Product product = get(index1);
		set(index1, get(index2));
		set(index2, product);
	}

	// ----------------------------------------

	public Product getProduct(int index) {
		if (get(index) == null)
			return new Product();
		return get(index);
	}

	public void setData(String paste, int selectedRow, int selectedColumn) {
		switch (selectedColumn) {
		case 0:
			get(selectedRow).setDate(paste);
			break;
		case 1:
			get(selectedRow).setCode(paste);
			break;
		case 2:
			get(selectedRow).setName(paste);
			break;
		case 3:
			get(selectedRow).setStandard(paste);
			break;
		case 4:
			get(selectedRow).setCount(paste);
			break;
		case 5:
			get(selectedRow).setCost(paste);
			break;
		}
	}

	@Override
	public void getRs(ResultSet rs) {
		add(new Product(rs));
	}

	@Override
	public boolean addQuery(Product product) {
		// 아이템 등록 임시관리장소
		if (product.getName() == null || product.getName().equals("")) 
			return false;
		if(!isHasQuery(product)){
			StringBuilder sbi = new StringBuilder();
			sbi.append("INSERT INTO ITEM VALUES(");
			sbi.append("(SELECT COUNT(*) FROM ITEM)");
			sbi.append(",'");
			sbi.append(product.getCode());
			sbi.append("','");
			sbi.append(product.getName());
			sbi.append("','");
			sbi.append(product.getStandard());
			sbi.append("');");
			Main.dataReader.execute(sbi.toString());
		}
		// 명세서 아이템
		StringBuilder sb = new StringBuilder();
		sb.append("INSERT INTO SPEC_ITEM VALUES('");
		sb.append(specID);
		sb.append("','");
		sb.append(product.getDate());
		sb.append("','");
		sb.append(product.getId());
		sb.append("','");
		sb.append(product.getCount());
		sb.append("','");
		sb.append(product.getCostOrigin());
		sb.append("');");
		return Main.dataReader.execute(sb.toString());
	}

	@Override
	public boolean isHasQuery(Product product) {
		StringBuilder sb=new StringBuilder();
		sb.append("SELECT ITEM_ID FROM ITEM WHERE ITEM_CODE LIKE '");
		sb.append(product.getCode());
		sb.append("' AND ITEM_NAME LIKE '");
		sb.append(product.getName());
		sb.append("' AND ITEM_STANDARD LIKE '");
		sb.append(product.getStandard());
		sb.append("';");
		return Main.dataReader.isHasQuery(sb.toString());
	}

	
	public boolean removeQuery(String specId) {
		StringBuilder sb=new StringBuilder();
		sb.append("DELETE FROM SPEC_ITEM WHERE SPEC_ID='");
		sb.append(specId);
		sb.append("';");
		boolean flag = Main.dataReader.execute(sb.toString());
		return flag;
	}
/////////////////////////////////////////////////
	@Override
	public void loadList(String id) {
		clear();
		StringBuilder sb=new StringBuilder();
		if (id.equals(""))
			return;
		else{
			int count=Main.FrontRow+1;
			int tCount=Integer.parseInt(Main.dataReader.getDataQuery("Select count(*) from PRODUCT WHERE SPEC_ID = '"+id+"';"));
			while (!(tCount<count))
				count+=Main.BackRow;
			sb.append("Select * from PRODUCT WHERE SPEC_ID = '");
			sb.append(id);
			sb.append("';");
			Main.dataReader.getQuery(this, sb.toString());
			resize(count);
		}
	}

	public void setSpecId(String specID) {
		this.specID = specID;
	}

	@Override
	public void getRsObject(ResultSet rs) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Product loadObject(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean removeQuery(int i) {
		// TODO Auto-generated method stub
		return false;
	}

	public long getSumPrice() {
		long price=0;
		for(int i=0;i<size();i++){
			price+=get(i).getPrice();
		}
		return price;
	}

	public long getSumTax() {
		long tax=0;
		for(int i=0;i<size();i++){
			tax+=get(i).getTax();
		}
		return tax;
	}
	/*
	@Override
	public Object clone(){
		System.out.println("wh");
		ProductList list=new ProductList();
		for(int i=0;i<size();i++){
			list.set(i,this.get(i).copy());
		}
		return this;
	}
	*/
	@Override
	public boolean equals(Object o){
		ProductList target=(ProductList)o;
		
		if (size()!=target.size())
			return false;
		else
			for(int i=0;i<size();i++){
				if (!((Product)target.get(i)).equals(get(i))){
					return false;
				}
			}
		return true;
	}

}
