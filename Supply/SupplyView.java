package Supply;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import javax.swing.JLabel;
import javax.swing.JTextField;

import FrameComponent.ViewManager;
import FrameComponent.WhitePanel;
import Main.Main;

public class SupplyView extends WhitePanel{
	JLabel sup[];
	WhitePanel pane;
	Font defaultFont;
	Supply supply;
	SupplyModify supplyModify;
	public SupplyView(ViewManager viewManager) {
		defaultFont=new Font(Main.font,Font.BOLD, 13);
		pane=this;
		sup = new JLabel[8];
		setBounds(358, 30, 380, 140);
		supply=new Supply();
		supplyModify=new SupplyModify(this);
		init();
		loadSupply();
	}
	public void init(){
		JLabel ������ = new JLabel(new String("<html>��<br>��<br>��</html>"));
		������.setFont(defaultFont.deriveFont(22.f));
		������.setBounds(16, 0, 200, 137);
		JLabel ��Ϲ�ȣ = new JLabel(new String("<html>���<br>��ȣ</html>"));
		��Ϲ�ȣ.setFont(defaultFont.deriveFont(10.5f));
		��Ϲ�ȣ.setBounds(50, 0, 40, 27);
		JLabel ��ȣ = new JLabel(new String("��ȣ"));
		��ȣ.setFont(defaultFont);
		��ȣ.setBounds(49, 35, 200, 13);
		JLabel ���� = new JLabel(new String("����"));
		����.setFont(defaultFont);
		����.setBounds(213, 35, 200, 13);
		JLabel �ּ� = new JLabel(new String("�ּ�"));
		�ּ�.setFont(defaultFont);
		�ּ�.setBounds(49, 62, 200, 13);
		JLabel ���� = new JLabel(new String("����"));
		����.setFont(defaultFont);
		����.setBounds(49, 89, 200, 13);
		JLabel ���� = new JLabel(new String("����"));
		����.setFont(defaultFont);
		����.setBounds(213, 89, 200, 13);
		JLabel ��ȭ = new JLabel(new String("��ȭ"));
		��ȭ.setFont(defaultFont);
		��ȭ.setBounds(49, 115, 200, 13);
		JLabel �ѽ� = new JLabel(new String("�ѽ�"));
		�ѽ�.setFont(defaultFont);
		�ѽ�.setBounds(213, 115, 200, 13);
		pane.add(������);
		pane.add(��Ϲ�ȣ);
		pane.add(��ȣ);
		pane.add(����);
		pane.add(�ּ�);
		pane.add(����);
		pane.add(����);
		pane.add(��ȭ);
		pane.add(�ѽ�);
		for (int i = 0; i < 8; i++) {
			sup[i] = new JLabel();
			sup[i].setFont(defaultFont);
			pane.add(sup[i]);
		}
		sup[0].setFont(defaultFont.deriveFont(22.f));
		sup[0].setHorizontalAlignment(JTextField.CENTER);
		sup[0].setBounds(90, 8, 250, 16);
		sup[1].setBounds(90, 35, 500, 13);
		sup[2].setBounds(253, 35, 500, 13);
		sup[3].setBounds(90, 62, 500, 13);
		sup[4].setBounds(90, 89, 500, 13);
		sup[5].setBounds(253, 89, 500, 13);
		sup[6].setBounds(90, 115, 500, 13);
		sup[7].setBounds(253, 115, 500, 13);
	}
	
	void loadSupply() {
		Main.dataReader.getQuery(supply, "SELECT * FROM SUPPLY");
		if (supply.getName()==null)
			return;
		else{
			setSupply(supply);
			repaint();//����
		}
		}
	public void paint(Graphics g) {
		super.paint(g);
		int sizeY=137;
		g.setColor(Color.black);
		g.drawRect(12, 0, 365, sizeY-2);
		//g.drawRect(42, 0, 42, sizeY/5);
		for (int i = 0; i < 5; i++) {
			g.drawRect(42, sizeY/5 * i, 42, sizeY/5);
		}
		
		for (int i = 0; i < 5; i++) {
			if (i != 2 && i!=0)
				g.drawRect(205, sizeY/5 * i, 42, sizeY/5);
		}
		
		for (int i = 0; i < 5; i++) {
			g.drawRect(84, sizeY/5 * (i), 293, sizeY/5);
		}
		
		
	}
	public void setSupply(Supply supply) {
		sup[0].setText(supply.getNum());
		sup[1].setText(supply.getName());
		sup[2].setText(supply.getWho());
		sup[3].setText(supply.getAddress());
		sup[4].setText(supply.getWork());
		sup[5].setText(supply.getWork2());
		sup[6].setText(supply.getTel());
		sup[7].setText(supply.getFax());
	}
	public Supply getSupply() {
		return supply;
	}
	public void modify() {
		supplyModify.setVisible(true);
	}
}


