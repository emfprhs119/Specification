package Specification;

import java.awt.Graphics;

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

	String str;
	int x;
	int y;
	ALIGN align;

	int getAlignX(Graphics g){
		 int actual_width = g.getFontMetrics().stringWidth(str);
		 switch(align){
		 case RIGHT: return -actual_width;
		 case CENTER: return -actual_width/2;
		default:
			return 0;
		 }
	}
}
