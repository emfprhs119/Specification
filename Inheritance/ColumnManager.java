package Inheritance;

public class ColumnManager {
	
	private String[] column;
	private int[] columnWidth;
	public String[] getColumn() {
		return column;
	}
	public void setColumn(String[] strArr, int[] intArr) {
		this.column = strArr;
		this.columnWidth=intArr;
	}
	public int[] getColumnWidth() {
		return columnWidth;
	}
	
}
