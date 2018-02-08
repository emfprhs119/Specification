package Main;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import org.sqlite.SQLiteConfig;

import Inheritance.GetResultSet;

public class DataReader {
	private Connection connection;
	private String dbFileName="data.db";
	private boolean isOpened = false;
	static {
		try {
			Class.forName("org.sqlite.JDBC");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public DataReader() {
		File file=new File(dbFileName);
		if (!file.exists()){
			JOptionPane.showConfirmDialog(null, "data.db 파일이 필요합니다.", "오류", JOptionPane.CLOSED_OPTION,
					JOptionPane.ERROR_MESSAGE);
			System.exit(0);
		}
	}

	public boolean open() {
		try {
			SQLiteConfig config = new SQLiteConfig();
			this.connection = DriverManager.getConnection("jdbc:sqlite:.\\"+ this.dbFileName, config.toProperties());
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		isOpened = true;
		return true;
	}

	public boolean close() {
		if (this.isOpened == false) {
			return true;
		}
		try {
			this.connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	public void getQuery(GetResultSet dataSet,String query){
		try {
			PreparedStatement prep = this.connection.prepareStatement(query);
			ResultSet rs = prep.executeQuery();
			
			while(rs.next()){
			dataSet.getRs(rs);
			}
			rs.close();
			prep.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public String getDataQuery(String query) {
		String str = null;
		try {
			PreparedStatement prep = this.connection.prepareStatement(query);
			ResultSet rs = prep.executeQuery();
			if (rs.next())
				str=rs.getString(1);
			rs.close();
			prep.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return str;
	}
	
	public boolean isHasQuery(String query) {
		boolean flag = false;
		try {
			PreparedStatement prep = this.connection.prepareStatement(query);
			ResultSet rs = prep.executeQuery();
			flag=rs.next();
			rs.close();
			prep.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return flag;
	}
	public boolean execute(String query){
		if (this.isOpened == false) {
			return false;
		}
		boolean result = false;
		try {
			PreparedStatement prep = this.connection.prepareStatement(query);
			prep.execute();
			result = true;
			prep.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			if (e.getErrorCode()==19)
				JOptionPane.showMessageDialog(null, "중복에러!!.");
			
			e.printStackTrace();
		}		
		return result;
	}

	public void getObject(GetResultSet dataSet,String query) {
		try {
			PreparedStatement prep = this.connection.prepareStatement(query);
			ResultSet rs = prep.executeQuery();
			while(rs.next()){
			dataSet.getRsObject(rs);
			}
			rs.close();
			prep.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	

}
