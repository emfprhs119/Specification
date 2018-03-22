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
		JLabel 공급자 = new JLabel(new String("<html>공<br>급<br>자</html>"));
		공급자.setFont(defaultFont.deriveFont(22.f));
		공급자.setBounds(16, 0, 200, 137);
		JLabel 등록번호 = new JLabel(new String("<html>등록<br>번호</html>"));
		등록번호.setFont(defaultFont.deriveFont(10.5f));
		등록번호.setBounds(50, 0, 40, 27);
		JLabel 상호 = new JLabel(new String("상호"));
		상호.setFont(defaultFont);
		상호.setBounds(49, 35, 200, 13);
		JLabel 성명 = new JLabel(new String("성명"));
		성명.setFont(defaultFont);
		성명.setBounds(213, 35, 200, 13);
		JLabel 주소 = new JLabel(new String("주소"));
		주소.setFont(defaultFont);
		주소.setBounds(49, 62, 200, 13);
		JLabel 업태 = new JLabel(new String("업태"));
		업태.setFont(defaultFont);
		업태.setBounds(49, 89, 200, 13);
		JLabel 종목 = new JLabel(new String("종목"));
		종목.setFont(defaultFont);
		종목.setBounds(213, 89, 200, 13);
		JLabel 전화 = new JLabel(new String("전화"));
		전화.setFont(defaultFont);
		전화.setBounds(49, 115, 200, 13);
		JLabel 팩스 = new JLabel(new String("팩스"));
		팩스.setFont(defaultFont);
		팩스.setBounds(213, 115, 200, 13);
		pane.add(공급자);
		pane.add(등록번호);
		pane.add(상호);
		pane.add(성명);
		pane.add(주소);
		pane.add(업태);
		pane.add(종목);
		pane.add(전화);
		pane.add(팩스);
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
			repaint();//문제
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


