package Demand;
import java.sql.ResultSet;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import Inheritance.List_Interface;
import Main.Main;
//거리처 리스트
class DemandList extends ArrayList<Demand> implements List_Interface<Demand> {
	Demand demand;
	@Override
	public void getRs(ResultSet rs) {
		add(new Demand(rs));
	}

	@Override
	public boolean removeQuery(int i) {
			StringBuilder sb=new StringBuilder();
			if (Main.dataReader.isHasQuery("SELECT * FROM SPEC WHERE DEM_REG_NUM='"+get(i).getRegNum()+"';")){
				JOptionPane.showMessageDialog(null, "참조하는 명세서가 있어 삭제할 수 없습니다.");
				return false;
			}else{
			sb.append("DELETE FROM DEMAND WHERE DEM_REG_NUM='");
			sb.append(get(i).getRegNum());
			sb.append("';");
			boolean flag = Main.dataReader.execute(sb.toString());
			
			return flag;
			}
	}

	@Override
	public boolean isHasQuery(Demand demand) {
		StringBuilder sb=new StringBuilder();
		sb.append("SELECT * FROM DEMAND WHERE DEM_NAME='");
		sb.append(demand.getName());
		sb.append("';");
		return Main.dataReader.isHasQuery(sb.toString());
	}
	@Override
	public boolean addQuery(Demand demand) {
		if (demand.getName()==null|| demand.getName().trim().equals("")){
			JOptionPane.showMessageDialog(null, "상호가 비었습니다.");
			return false;
		}
		if (isHasQuery(demand))
			return false;
		StringBuilder sb=new StringBuilder();
		sb.append("INSERT INTO DEMAND VALUES('");
		sb.append(demand.getRegNum());
		sb.append("','");
		sb.append(demand.getName());
		sb.append("','");
		sb.append(demand.getWho());
		sb.append("','");
		sb.append(demand.getTel());
		sb.append("');");
		return Main.dataReader.execute(sb.toString());
	}

	@Override
	public void loadList(String search) {
		clear();
		StringBuilder sb=new StringBuilder();
		if (search.equals(""))
			Main.dataReader.getQuery(this, "Select * from DEMAND;");
		else{
			sb.append("Select * from DEMAND WHERE ");
			sb.append("DEM_NAME LIKE '%");
			sb.append(search);
			sb.append("%' OR ");
			sb.append("DEM_REG_NUM LIKE '%");
			sb.append(search);
			sb.append("%' OR ");
			sb.append("DEM_WHO LIKE '%");
			sb.append(search);
			sb.append("%';");
			Main.dataReader.getQuery(this, sb.toString());
		}
	}

	@Override
	public Demand loadObject(String id) {
		Main.dataReader.getObject(this, "Select * from DEMAND WHERE DEM_NAME = '"+id+"';");
		return demand;
	}
	@Override
	public void getRsObject(ResultSet rs) {
		demand=new Demand(rs);
		
	}
}