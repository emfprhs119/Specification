package FrameComponent;

import java.awt.Font;
import java.awt.Rectangle;
import javax.swing.JButton;

import Main.Main;

public class FrameButton {
	private JButton button[];
	
	public FrameButton(MenuAction action) {
		Rectangle bSize=new Rectangle(10,925-184,132,45);
		button = new JButton[6];
		button[0] = new JButton("◀ 이전");
		button[1] = new JButton("다음 ▶");
		button[2] = new JButton("새 명세서");
		button[3] = new JButton("불러오기");
		button[4] = new JButton("저장하기");
		button[5] = new JButton("인쇄하기");
		/*
		button[6] = getQuestionIcon(30,30);

		button[6].addMouseListener(action);
		button[6].setBounds(794, 5, 30, 30);
		button[6].setVisible(true);
		button[6].addActionListener(action);
		*/
		for (int i = 0; i < 6; i++) {
			button[i].setBounds(bSize);
			bSize.x+=bSize.width+3;
			button[i].setFont(new Font(Main.font, Font.BOLD, 22));
			button[i].setVisible(true);
			button[i].addActionListener(action);
			button[i].setToolTipText(button[i].getText());
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
		jButton.setToolTipText("도움말");
		return jButton;
	}
	*/
}

