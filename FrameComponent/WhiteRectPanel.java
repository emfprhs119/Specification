package FrameComponent;

import java.awt.Graphics;

//����� �Ͼ���̰� layout�� null�� Container �ܰ��� draw
public class WhiteRectPanel extends WhitePanel {
	public void paint(Graphics g) {
		super.paint(g);
		g.drawRect(0, 0, 755,875);
	}
	
}