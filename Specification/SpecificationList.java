package Specification;

import java.sql.ResultSet;
import java.util.ArrayList;

import Inheritance.List_Interface;
import Main.Main;

public class SpecificationList extends ArrayList<Specification> implements List_Interface<Specification> {

	@Override
	public void getRs(ResultSet rs) {
		add(new Specification(rs));
	}

	@Override
	public boolean addQuery(Specification spec) {
		int number = 0;
		spec.setNo(String.valueOf(number));
		while (isHasQuery(spec)) {
			number++;
			spec.setNo(String.valueOf(number));
		}
		StringBuilder sb=new StringBuilder();
		sb.append("INSERT INTO SPEC VALUES(");
		sb.append("(SELECT COUNT(*) FROM SPEC_ID_COUNT)");
		sb.append(",'");
		sb.append(spec.getName());
		sb.append("','");
		sb.append(spec.getDate());
		sb.append("','");
		sb.append(spec.getNo());
		sb.append("');");
		Main.dataReader.execute("INSERT INTO SPEC_ID_COUNT VALUES((SELECT COUNT(*) FROM SPEC_ID_COUNT))");
		return Main.dataReader.execute(sb.toString());
	}

	@Override
	public boolean isHasQuery(Specification spec) {
		StringBuilder sb=new StringBuilder();
		sb.append("SELECT * FROM SPEC WHERE DEM_NAME LIKE '");
		sb.append(spec.getName());
		sb.append("' AND ISSUE_DATE LIKE '");
		sb.append(spec.getDate());
		sb.append("' AND DAY_NUM LIKE '");
		sb.append(spec.getNo());
		sb.append("';");
		return Main.dataReader.isHasQuery(sb.toString());
	}
	@Override
	public boolean removeQuery(int i) {
		StringBuilder sb=new StringBuilder();
		sb.append("DELETE FROM SPEC_ITEM WHERE SPEC_ID='");
		sb.append(get(i).getIdQuery());
		sb.append("';");
		boolean flag = Main.dataReader.execute(sb.toString());
		if (flag){
			sb=new StringBuilder();
			sb.append("DELETE FROM SPEC WHERE SPEC_ID='");
			sb.append(get(i).getIdQuery());
			sb.append("';");
			flag=Main.dataReader.execute(sb.toString());
		}
		return flag;
	}

	@Override
	public void loadList(String search) {
		clear();
		StringBuilder sb = new StringBuilder();
		if (search.equals(""))
			Main.dataReader.getQuery(this, "Select * from SPEC;");
		else {
			sb.append("Select * from SPEC WHERE ");
			sb.append("ISSUE_DATE LIKE '%");
			sb.append(search);
			sb.append("%' OR ");
			sb.append("DEM_NAME LIKE '%");
			sb.append(search);
			sb.append("%' OR ");
			sb.append("DAY_NUM LIKE '%");
			sb.append(search);
			// ITEM SEARCH -st
			sb.append("%' OR ");
			sb.append("SPEC_ID IN (");
			sb.append("SELECT DISTINCT SPEC_ID FROM PRODUCT WHERE ITEM_CODE LIKE '%");
			sb.append(search);
			sb.append("%' OR ITEM_NAME LIKE '%");
			sb.append(search);
			sb.append("%' OR ITEM_STANDARD LIKE '%");
			sb.append(search);
			sb.append("%');");
			// ITEM SEARCH -ed
			Main.dataReader.getQuery(this, sb.toString());
		}
	}

	@Override
	public void getRsObject(ResultSet rs) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Specification loadObject(String id) {
		// TODO Auto-generated method stub
		return null;
	}

}
