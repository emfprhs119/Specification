package FrameComponent;

import java.awt.Font;
import java.awt.Rectangle;
import javax.swing.JButton;

import Main.Main;

public class FrameButton {
	private JButton button[];
	
	public FrameButton(MenuAction action) {
		Rectangle bSize=new Rectangle(60,620,132,45);
		button = new JButton[10];
		button[0] = new JButton("�� ����");
		button[1] = new JButton("���� ��");
		button[2] = new JButton("�� ����");
		button[3] = new JButton("�ҷ�����");
		button[4] = new JButton("�����ϱ�");
		button[5] = new JButton("�̸�����");
		button[6] = new JButton("�μ��ϱ�");
		button[7] = new JButton("PDF");
		button[8] = new JButton("�˻��ϱ�");
		button[9] = new JButton("����");
		/*
		button[6] = getQuestionIcon(30,30);
		button[6].addMouseListener(action);
		button[6].setBounds(794, 5, 30, 30);
		button[6].setVisible(true);
		button[6].addActionListener(action);
		*/
		for (int i = 0; i < button.length; i++) {
			button[i].setBounds(bSize);
			bSize.x+=bSize.width+10;
			button[i].setFont(new Font(Main.font, Font.BOLD, 22));
			button[i].setVisible(true);
			button[i].addActionListener(action);
			button[i].setToolTipText(button[i].getText());
			if (i==4){
				bSize.y+=bSize.height+10;
				bSize.x-=(bSize.width+10)*5;
			}
		}
	}
	public JButton[] getButtons() {
		return button;
	}
	/*
	private JButton getQuestionIcon(int width,int height){
		BufferedImage img = null;
		try {
			
			img =ImageIO.read (getClass (). getClassLoader (). getResource ( "resources/question.png"));
		} catch (IOException e) {
		    e.printStackTrace();
		}
		Image dimg = img.getScaledInstance(width,height,Image.SCALE_SMOOTH);
		JButton jButton = new JButton();
		jButton.setIcon(new ImageIcon(dimg));
		jButton.setBorderPainted(false);
		jButton.setContentAreaFilled(false);
		jButton.setToolTipText("����");
		return jButton;
	}
	*/
}

