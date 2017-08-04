package FrameComponent;

import java.awt.Button;
import java.awt.Font;
import java.awt.Rectangle;

import Main.Main;
import Main.MenuAction;

public class FrameButton {
	private Button button[];
	
	public FrameButton(MenuAction action) {
		Rectangle bSize=new Rectangle(25,925,120,45);
		button = new Button[6];
		button[0] = new Button("��");
		button[1] = new Button("��");
		button[2] = new Button("�� ������");
		button[3] = new Button("�ҷ�����");
		button[4] = new Button("�����ϱ�");
		button[5] = new Button("��������");
		
		for (int i = 0; i < 6; i++) {
			button[i].setBounds(bSize);
			bSize.x+=bSize.width+10;
			button[i].setFont(new Font(Main.font, Font.BOLD, 22));
			button[i].setVisible(true);
			button[i].addActionListener(action);
		}
	}
	public Button[] getButtons() {
		return button;
	}
}
