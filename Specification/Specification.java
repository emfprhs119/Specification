package Specification;

import java.sql.ResultSet;
import java.sql.SQLException;

import Demand.Demand;
import Demand.DemandView;
import Inheritance.Model_Interface;
import Main.Main;

public class Specification implements Model_Interface {
	Demand demand;
	private String spec_id;
	private String date;
	private String name;
	private String no;
	
	public Specification(ResultSet rs) {
		try {
			spec_id=rs.getString("SPEC_ID");
			date=rs.getString("ISSUE_DATE");
			name=rs.getString("DEM_NAME");
			no=rs.getString("DAY_NUM");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public Specification(String date, String name,String no) {
		this.date = date;
		this.name = name;
		this.no = no;
	}
	public Specification(DemandView demandView) {
		this.date=demandView.getDate();
		this.name=demandView.getDemand().getName();
	}
	public String getDate() {
		return date;
	}

	public String getName() {
		return name;
	}

	public String getNo() {
		return no;
	}
	public String getString() {
		return "°ßÀû¼­_"+name+"_"+date+"_"+no;
	}

	@Override
	public String[] getLoadArr() {
		String stn[] = new String[3];
		stn[0] = date;
		stn[1] = name;
		stn[2] = no;
		return stn;
	}

	public void setNo(String no) {
		this.no=no;
		
	}
	/*
	public String getId() {
		return spec_id;
	}
	*/
	public String getIdQuery() {
		StringBuilder sb=new StringBuilder();
		sb.append("SELECT SPEC_ID FROM SPEC WHERE DEM_NAME LIKE '");
		sb.append(getName());
		sb.append("' AND ISSUE_DATE LIKE '");
		sb.append(getDate());
		sb.append("' AND DAY_NUM LIKE '");
		sb.append(getNo());
		sb.append("';");
		return Main.dataReader.getDataQuery(sb.toString());
	}
	/*
	public void setId(String spec_id) {
		this.spec_id=spec_id;
	}
	*/
}
