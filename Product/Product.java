package Product;

import java.sql.ResultSet;
import java.sql.SQLException;

import Inheritance.Model_Interface;
import Main.Main;

//Ç°¸ñ
public class Product implements Model_Interface {
	private String date;
	private String code;
	private String name;
	private String standard;
	private long count;
	private long cost;
	public Product() {
		//this.code = new String();
		//this.name = new String();
		//this.standard = new String();
		this.count = 0;
		this.cost = 0;
	}
	public Product(ResultSet rs) {
		try {
			date = rs.getString("date");
			code = rs.getString("item_code");
			name = rs.getString("item_name");
			standard = rs.getString("item_standard");
			count = rs.getInt("count");
			cost = rs.getInt("item_cost");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public Product(String[] stn) {
		code = stn[5];
		name = stn[0];
		standard = stn[1];
		count = Long.parseLong(stn[4] == "" ? "0" : Main.stringToLongString(stn[4]));
		cost = Long.parseLong(stn[3] == "" ? "0" : Main.stringToLongString(stn[3]));
	}
	public boolean isNull() {
		if (name == null || name.equals("")) {
			return true;
		} else
			return false;
	}

	@Override
	public String[] getLoadArr() {
		// TODO Auto-generated method stub
		return null;
	}
/*
	public int getHashCode() {
		Item item = new Item(code, name, standard);
		return item.hashCode();
	}
	*/
	public String getId() {
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT ITEM_ID FROM ITEM WHERE ITEM_CODE LIKE '");
		sb.append(getCode());
		sb.append("' AND ITEM_NAME LIKE '");
		sb.append(getName());
		sb.append("' AND ITEM_STANDARD LIKE '");
		sb.append(getStandard());
		sb.append("';");
		return Main.dataReader.getDataQuery(sb.toString());

	}
/*
	public Product copy() {
		Product product = new Product();
		product.code=new String();
		product.name=new String();
		product.standard=new String();
		product.count;
		product.cost;
		return product;
	}
*/
	public boolean equals(Object o) {
		Product target = (Product) o;
		
		setNull();
		target.setNull();
		
		if (!(date==null && target.date==null)){
			if (date==null)
				return false;
			else if (!date.equals(target.date)){
				return false;
			}
		}
		if (!(code==null && target.code==null)){
			if (code==null)
				return false;
			else if (!code.equals(target.code)){
				return false;
			}
		}
		if (!(name==null && target.name==null)){
			if (name==null)
				return false;
			else if (!name.equals(target.name)){
				return false;
			}
		}
		if (!(standard==null && target.standard==null)){
			if (standard==null)
				return false;
			else if (!standard.equals(target.standard)){
				return false;
			}
		}
		if (count!=target.count || cost!=target.cost)
			return false;
		
		return true;
	}
	
	
	private void setNull() {
		if (date!=null){
			if (date.equals(""))
				date=null;
			else if (date.length()==4)
				date="0"+date;
		}
		if (code!=null)
			if (code.equals(""))
				code=null;
		if (name!=null)
			if (name.equals(""))
				name=null;
		if (standard!=null)
			if (standard.equals(""))
				standard=null;
	}
	//getter setter

	public void setProduct(Product product) {
		this.code = product.code;
		this.name = product.name;
		this.standard = product.standard;
		this.count = product.count;
		this.cost = product.cost;
	}
	public void print(){
		System.out.println(code+" "+name+" "+standard+" "+count+" "+cost);
	}

	public String getCost() {
		if (cost == 0)
			return "";
		else

			return Main.longToMoneyString(cost);
	}
	public long getCostOrigin() {
		return cost;
	}

	public long getSumMoney() {
		return cost * count;
	}

	public String getPriceStr() {
		if (cost * count == 0)
			return "";
		else
			return Main.longToMoneyString(cost * count);
	}

	public String getTaxStr() {
		if (cost * count == 0)
			return "";
		else
			return Main.longToMoneyString(cost * count / 10);
	}

	public Long getPrice() {
		return (cost * count);
	}

	public Long getTax() {
		return (cost * count / 10);
	}
	public String[] getStrings() {
		String stn[] = new String[6];
		stn[0] = name;
		stn[1] = standard;
		stn[3] = getCost();
		stn[4] = getCount();
		stn[5] = code;
		return stn;
	}

	public String getDate() {
		if (date == null)
			return "";
		if (date.length()==4)
			date="0"+date;
		return date;
	}

	public String getCode() {
		if (code == null)
			return "";
		return code;
	}

	public String getName() {
		if (name == null)
			return "";
		return name;
	}

	public String getStandard() {
		if (standard == null)
			return "";
		return standard;
	}

	public String getCount() {
		if (count == 0)
			return "";
		else
			return Main.longToMoneyString(count);
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setStandard(String standard) {
		this.standard = standard;
	}

	public void setCost(String cost) {
		if (cost != null)
			this.cost = Long.parseLong(cost.replace(",", ""));
		else{
			this.cost=0;
		}
	}

	public void setCount(String count) {
		if (count != null)
			this.count = Long.parseLong(count.replace(",", ""));
		else
			this.count=0;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public void setCode(String code) {
		this.code = code;
	}

}