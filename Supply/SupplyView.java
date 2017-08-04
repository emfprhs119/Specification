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
	public SupplyView(ViewManager viewManager) {
		defaultFont=new Font(Main.font,Font.BOLD, 15);
		pane=this;
		sup = new JLabel[8];
		setBounds(358, 90, 500, 200);
		supply=new Supply();
		init();
		loadSupply();
	}
	public void init(){
		JLabel 공급자 = new JLabel(new String("<html>공<br>급<br>자</html>"));
		공급자.setFont(defaultFont.deriveFont(25.f));
		공급자.setBounds(14, -45, 200, 300);
		JLabel 등록번호 = new JLabel(new String("<html>등록<br>번호</html>"));
		등록번호.setFont(defaultFont);
		등록번호.setBounds(48, -119, 200, 300);
		JLabel 상호 = new JLabel(new String("상호"));
		상호.setFont(defaultFont);
		상호.setBounds(48, -77, 200, 300);
		JLabel 성명 = new JLabel(new String("성명"));
		성명.setFont(defaultFont);
		성명.setBounds(212, -77, 200, 300);
		JLabel 주소 = new JLabel(new String("주소"));
		주소.setFont(defaultFont);
		주소.setBounds(48, -40, 200, 300);
		JLabel 업태 = new JLabel(new String("업태"));
		업태.setFont(defaultFont);
		업태.setBounds(48, -5, 200, 300);
		JLabel 종목 = new JLabel(new String("종목"));
		종목.setFont(defaultFont);
		종목.setBounds(212, -5, 200, 300);
		JLabel 전화 = new JLabel(new String("전화"));
		전화.setFont(defaultFont);
		전화.setBounds(48, 31, 200, 300);
		JLabel 팩스 = new JLabel(new String("팩스"));
		팩스.setFont(defaultFont);
		팩스.setBounds(212, 31, 200, 300);
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
		sup[0].setFont(defaultFont.deriveFont(28.f));
		sup[0].setHorizontalAlignment(JTextField.CENTER);
		sup[0].setBounds(90, -67, 270, 200);
		sup[1].setBounds(90, -27, 500, 200);
		sup[2].setBounds(253, -27, 500, 200);
		sup[3].setBounds(90, 10, 500, 200);
		sup[4].setBounds(90, 45, 500, 200);
		sup[5].setBounds(253, 45, 500, 200);
		sup[6].setBounds(90, 81, 500, 200);
		sup[7].setBounds(253, 81, 500, 200);
	}
	
	void loadSupply() {
		Main.dataReader.getQuery(supply, "SELECT * FROM SUPPLY");
		setSupply(supply);
		}
	public void paint(Graphics g) {
		super.paint(g);
		g.setColor(Color.black);
		g.drawRect(12, 9, 365, 187);
		g.drawRect(42, 9, 42, 43);
		for (int i = 0; i < 4; i++) {
			g.drawRect(42, 52 + 36 * i, 42, 36);
		}
		for (int i = 0; i < 4; i++) {
			if (i != 1)
				g.drawRect(205, 52 + 36 * i, 42, 36);
		}
		for (int i = 0; i < 4; i++) {
			g.drawRect(84, 52 + 36 * i, 365 - 72, 36);
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
}


