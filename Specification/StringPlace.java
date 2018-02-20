package Specification;

import java.awt.Graphics;
import java.io.IOException;

import org.apache.pdfbox.pdmodel.font.PDFont;

enum ALIGN {
	LEFT, CENTER, RIGHT
}

public class StringPlace {
	public StringPlace(String str, int x, int y, ALIGN align) {
		this.str = str;
		this.x = x;
		this.y = y;
		this.align = align;
	}

	public String str;
	public int x;
	public int y;
	public ALIGN align;
	
	public int getAlignX(Graphics g){
		 return getAlignX(g.getFontMetrics().stringWidth(str));
	}
	public int getAlignX(PDFont font){
		 int actual_width = 0;
		try {
			actual_width = (int) font.getStringWidth(str);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 return getAlignX(actual_width);
	}
	private int getAlignX(int actual_width){
		switch(align){
		 case RIGHT: return -actual_width;
		 case CENTER: return -actual_width/2;
		default:
			return 0;
		 }
	}
}
