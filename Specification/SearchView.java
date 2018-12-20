package Specification;

import java.awt.Dimension;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import Inheritance.ColumnManager;
import Inheritance.ListManager;
import Inheritance.List_Interface;
import Inheritance.Model_Interface;
import Inheritance.View_Interface;
import Main.Main;

public class SearchView implements View_Interface<Search> {
	ListManager<Search, SearchList> listManager;
	public SearchView() {
		initListManager();
	}
	private void initListManager() {
		ColumnManager column = new ColumnManager();
		String strArr[] = { "발행일자","거래처명","월일", "품목코드", "품목", "규격", "수량", "단가"};
		int intArr[] = {40,100,45,30,205,105,50,75};
		column.setColumn(strArr, intArr);
		listManager = new ListManager<Search, SearchList>("검색하기", this, new SearchList(),
				column, 2);
		listManager.scroll.setPreferredSize(new Dimension(900, 600));
		listManager.panel.setBounds(7, 40, 900, 600);
		listManager.setBounds(800, 60, 920, 600);
	}

	public void setVisible(boolean b) {
		listManager.setVisible(b);
	}
	@Override
	public void loadDataId(String id) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void loadData(Search o) {
		// TODO Auto-generated method stub
		
	}
}
class Search implements Model_Interface{

	
	String[] strings;

	public Search(String[] strings) {
		this.strings = strings;
	}

	@Override
	public String[] getLoadArr() {
		// TODO Auto-generated method stub
		return strings;
	}
	
}
class SearchList implements List_Interface<Search>{
	Vector<String[]> vec = new Vector<String[]>();
	@Override
	public void getRs(ResultSet rs) {
		try {
			String[] strArr = new String[8];
			int i = 0; 
			strArr[i++]=rs.getString("ISSUE_DATE");
			strArr[i++]=rs.getString("DEM_NAME");
			strArr[i++]=rs.getString("DATE");
			strArr[i++]=rs.getString("ITEM_CODE");
			strArr[i++]=rs.getString("ITEM_NAME");
			strArr[i++]=rs.getString("ITEM_STANDARD");
			strArr[i++]=rs.getString("COUNT");
			strArr[i++]=rs.getString("ITEM_COST");
			vec.add(strArr);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void getRsObject(ResultSet rs) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void loadList(String search) {
		vec.clear();
		StringBuilder sb=new StringBuilder();
		if (search.equals(""))
			return;
		else{
			sb.append("Select * from SPEC,PRODUCT WHERE SPEC.SPEC_ID == PRODUCT.SPEC_ID AND (ITEM_NAME = '");
			sb.append(search);
			sb.append("' OR ITEM_STANDARD = '");
			sb.append(search);
			sb.append("');");
			Main.dataReader.getQuery(this, sb.toString());
		}
	}

	@Override
	public boolean removeQuery(int i) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public int size() {
		// TODO Auto-generated method stub
		return vec.size();
	}
	@Override
	public boolean addQuery(Search o) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean isHasQuery(Search o) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public Search get(int i) {
		return new Search(vec.get(i));
	}
	@Override
	public Search loadObject(String id) {
		// TODO Auto-generated method stub
		return null;
	}
}