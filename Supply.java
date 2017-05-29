import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.JLabel;
import javax.swing.JTextField;

class SupplyF{
	String num;
	String company;
	String name;
	String address;
	String work;
	String work2;
	String tel;
	String fax;
}

public class Supply extends MyPanel {

	JLabel sup[] = new JLabel[8];
	MyPanel pane=this;
	
	Supply() {
		supplyInit();
		init();
	}
	public void supplyInit(){
		JLabel ������ = new JLabel(new String("<html>��<br>��<br>��</html>"));
		������.setFont(new Font("�Ÿ���", Font.BOLD, 25));
		������.setBounds(14, -45, 200, 300);
		JLabel ��Ϲ�ȣ = new JLabel(new String("<html>���<br>��ȣ</html>"));
		��Ϲ�ȣ.setFont(new Font("�Ÿ���", Font.BOLD, 15));
		��Ϲ�ȣ.setBounds(48, -119, 200, 300);
		JLabel ��ȣ = new JLabel(new String("��ȣ"));
		��ȣ.setFont(new Font("�Ÿ���", Font.BOLD, 15));
		��ȣ.setBounds(48, -77, 200, 300);
		JLabel ���� = new JLabel(new String("����"));
		����.setFont(new Font("�Ÿ���", Font.BOLD, 15));
		����.setBounds(212, -77, 200, 300);
		JLabel �ּ� = new JLabel(new String("�ּ�"));
		�ּ�.setFont(new Font("�Ÿ���", Font.BOLD, 15));
		�ּ�.setBounds(48, -40, 200, 300);
		JLabel ���� = new JLabel(new String("����"));
		����.setFont(new Font("�Ÿ���", Font.BOLD, 15));
		����.setBounds(48, -5, 200, 300);
		JLabel ���� = new JLabel(new String("����"));
		����.setFont(new Font("�Ÿ���", Font.BOLD, 15));
		����.setBounds(212, -5, 200, 300);
		JLabel ��ȭ = new JLabel(new String("��ȭ"));
		��ȭ.setFont(new Font("�Ÿ���", Font.BOLD, 15));
		��ȭ.setBounds(48, 31, 200, 300);
		JLabel �ѽ� = new JLabel(new String("�ѽ�"));
		�ѽ�.setFont(new Font("�Ÿ���", Font.BOLD, 15));
		�ѽ�.setBounds(212, 31, 200, 300);
		pane.add(������);
		pane.add(��Ϲ�ȣ);
		pane.add(��ȣ);
		pane.add(����);
		pane.add(�ּ�);
		pane.add(����);
		pane.add(����);
		pane.add(��ȭ);
		pane.add(�ѽ�);
	}
	
	void init() {
		BufferedReader fr = null;
		boolean out = false;
		String st;
		for (int i = 0; i < 8; i++) {
			sup[i] = new JLabel();
			sup[i].setFont(new Font(Main.font, Font.BOLD, 15));
			pane.add(sup[i]);
		}
		sup[0].setFont(new Font(Main.font, Font.BOLD, 28));
		sup[0].setHorizontalAlignment(JTextField.CENTER);
		sup[0].setBounds(90, -67, 270, 200);
		sup[1].setBounds(90, -27, 500, 200);
		sup[2].setBounds(253, -27, 500, 200);
		sup[3].setBounds(90, 10, 500, 200);
		sup[4].setBounds(90, 45, 500, 200);
		sup[5].setBounds(253, 45, 500, 200);
		sup[6].setBounds(90, 81, 500, 200);
		sup[7].setBounds(253, 81, 500, 200);
		try {
			fr = new BufferedReader(new FileReader("init.txt"));
				for (int z = 0; z < 8; z++) {
					st = fr.readLine();
					String stn[] = st.split(":");
					sup[z].setText(stn[1]);
				}
			} catch (IOException e2) {
				e2.printStackTrace();
			}
		}
	public SupplyF getSupply(){
		SupplyF supply = new SupplyF();
	/*if (sup[0].getText()==null)
			retu*/
		supply.num=sup[0].getText();
		supply.company=sup[1].getText();
		supply.name=sup[2].getText();
		supply.address=sup[3].getText();
		supply.work=sup[4].getText();
		supply.work2=sup[5].getText();
		supply.tel=sup[6].getText();
		supply.fax=sup[7].getText();
		return supply;
		
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
}


