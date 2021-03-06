package Main;
import java.awt.Color;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

import FrameComponent.MainFrame;


public class Main {
	public static int frameWidth=838;
	public static int frameHeight=790;
	public static String virsion = "1.1";
	public static String name = "거래명세서";
	public final static Color YELLOW = Color.getHSBColor((float) 0.160, (float) 0.42, (float) 0.98);
	public static String font = "Serif"; 	//default font
	public static int fontSize=17;			//default fontSize
	public static int tableSize[]={45,70,205,105,50,75,95,80}; //default tableSize
	public static int FrontRow=14; // 전면 리스트 행수
	public static int BackRow = 20; // 후면 리스트 행수
	public static DataReader dataReader;
	public static SimpleDateFormat fullDateFormat;
	public static SimpleDateFormat dateFormat;
	public static void main(String[] args) {
		dataReader = new DataReader();
		dataReader.open();
		fullDateFormat = new SimpleDateFormat("YYYY.MM.dd");
		dateFormat = new SimpleDateFormat("MM.dd");
		new MainFrame();
	}
	public void finalize() {
        //소멸
        dataReader.close();
	}
	// long to #,### transform
	public static String longToMoneyString(long num) {
		if (num == 0)
			return "0";
		DecimalFormat df = new DecimalFormat("#,###");
		return df.format(num);
	}

	public static String stringToDate(Object object) {
		if (object == null)
			return null;
		String num = (String) object;
		if (num.matches("[0-9]+[-/.][0-9]+")){
		num = num.replaceAll("[^-0-9./]", "");
		num = num.replaceAll("[^0-9]", ".");
		
		return dateFormat.format(num);
		}else
			return null;
	}
	// String to longString format transform
	public static String stringToLongString(Object object) {
		if (object == null)
			return null;
		String num = (String) object;
		num = num.replaceAll("[^-0-9]", "");
		if (num.matches("^[0-9\\-]+")) {
			return num;
		} else {
			return null;
		}
	}
	// String to long
	public static long stringToLong(Object obj) {
		if (obj == null)
			return 0;
		String num = (String) obj;
		num = num.replaceAll("[^-0-9]", "");
		if (num.matches("^[0-9\\-]+")) {
			return Long.parseLong(num);
		} else {
			return 0;
		}
	}
	// if null to '-'  if contain , to both side " " 
	public static String checkString(String str){
		if (str==null || str.replace(" ","").equals(""))
			return "-";
		else if (str.contains(","))
			return "\""+str+"\"";
		else
			return str;
	}
}
