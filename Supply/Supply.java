package Supply;

import java.sql.ResultSet;
import java.sql.SQLException;

import Inheritance.GetResultSet;

// 공급자
public class Supply implements GetResultSet{
	private String num;	// 사업자 등록번호
	private String name;	// 상호
	private String who;	// 대표
	private String address;	// 주소
	private String work;	// 업태
	private String work2;	// 종목
	private String tel;	// 전화
	private String fax;	// 팩스
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getWho() {
		return who;
	}
	public void setWho(String who) {
		this.who = who;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getWork() {
		return work;
	}
	public void setWork(String work) {
		this.work = work;
	}
	public String getWork2() {
		return work2;
	}
	public void setWork2(String work2) {
		this.work2 = work2;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getFax() {
		return fax;
	}
	public void setFax(String fax) {
		this.fax = fax;
	}
	public String getNum() {
		return num;
	}
	public void setNum(String num) {
		this.num = num;
	}
	@Override
	public void getRs(ResultSet rs) {
		try {
			num=rs.getString("SUP_REG_NUM");
			name=rs.getString("SUP_NAME");
			who=rs.getString("SUP_WHO");
			address=rs.getString("SUP_ADDR");
			work=rs.getString("SUP_WORK");
			work2=rs.getString("SUP_WORK2");
			tel=rs.getString("SUP_TEL");
			fax=rs.getString("SUP_FAX");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Override
	public void getRsObject(ResultSet rs) {
		// TODO Auto-generated method stub
		
	}
}
