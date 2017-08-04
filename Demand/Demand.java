package Demand;

import java.sql.ResultSet;
import java.sql.SQLException;

import Inheritance.Model_Interface;

// 거래처
public class Demand implements Model_Interface{
	
	//private String date;	// 날짜
	private String regNum;	// 등록번호
	private String name;	// 상호
	private String tel;		// 전화번호
	private String who;		// 담당자

	public Demand() {
	}
	public Demand(String name,String regNum, String tel, String who) {
		this.regNum=regNum;
		this.name = name;
		this.tel = tel;
		this.who = who;
	}
/*
	public Demand(String[] stn) {
		int n=0;
		if (stn.length==5)
			this.date=stn[n++];
		this.regNum=stn[n++];
		this.name = stn[n++];
		this.tel = stn[n++];
		this.who = stn[n++];
	}
*/
	public Demand(ResultSet rs) {
		try {
			regNum=rs.getString("dem_reg_num");
			name=rs.getString("dem_name");
			who=rs.getString("dem_who");
			tel=rs.getString("dem_tel");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public boolean equals(Object obj) {
		if (name.equals(((Demand) obj).name) && tel.equals(((Demand) obj).tel) && who.equals(((Demand) obj).who))
			return true;
		else
			return false;
	}

	public void setDemand(Demand demand) {
		this.regNum = demand.regNum;
		this.name = demand.name;
		this.tel = demand.tel;
		this.who = demand.who;
	}

	public int compareTo(Demand demand, int flag) {
		switch(flag){
		case 0:return this.name.compareTo(demand.name);
		case 1:return this.who.compareTo(demand.who);
		case 2:return this.tel.compareTo(demand.tel);
		}
		
		return 0;
	}
/*
	public String[] getArr() {
		String stn[] = new String[4];
		stn[0] = date;
		stn[1] = name;
		stn[2] = tel;
		stn[3] = who;
		return stn;
	}
	*/
	public String getName() {
		return name;
	}

	public String getTel() {
		return tel;
	}

	public String getWho() {
		return who;
	}
	public String getRegNum() {
		return regNum;
	}

	public void setRegNum(String regNum) {
		this.regNum = regNum;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}

	public void setWho(String who) {
		this.who = who;
	}

	public void setName(String name) {
		this.name = name;
	}


	@Override
	public String[] getLoadArr() {
		String stn[] = new String[3];
		stn[0] = name;
		stn[1] = regNum;
		stn[2] = who;
		return stn;
	}

	

}