package Supply;

import java.awt.Font;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import FrameComponent.WhitePanel;
import Main.Main;
public class SupplyModify extends JFrame {
	JTextField textField[];
	SupplyView supplyView;
	final int size=8;
	SupplyModify(final SupplyView supplyView) {
		super("공급자 변경");
		this.supplyView=supplyView;
		setLayout(null);
		setBounds(400, 150, 360, 270);
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setResizable(false);
		WhitePanel leftPanel = new WhitePanel();
		WhitePanel rightPanel = new WhitePanel();
		//leftPanel.setLayout(null);
		leftPanel.setBounds(0, 0, 120,205);
		rightPanel.setBounds(110, 0,240, 205);

		JPanel[] rightTextPanel = new JPanel[size];
		JPanel[] leftLabelPanel = new JPanel[size];
		textField = new JTextField[size];
		for (int i = 0; i < size; i++) {
			rightTextPanel[i] = new JPanel();
			leftLabelPanel[i] = new JPanel();
		}
		JLabel label[] = new JLabel[size];
		leftLabelPanel[0].add(label[0] = new JLabel("사업자등록번호 :"));
		leftLabelPanel[1].add(label[1] = new JLabel("상 호 :"));
		leftLabelPanel[2].add(label[2] = new JLabel("대 표 :"));
		leftLabelPanel[3].add(label[3] = new JLabel("주 소 :"));
		leftLabelPanel[4].add(label[4] = new JLabel("업 태 :"));
		leftLabelPanel[5].add(label[5] = new JLabel("종 목 :"));
		leftLabelPanel[6].add(label[6] = new JLabel("전 화 :"));
		leftLabelPanel[7].add(label[7] = new JLabel("팩 스 :"));

		for(int i=0;i<size;i++){
			
			label[i].setHorizontalAlignment(Label.RIGHT);;
			leftLabelPanel[i].setBounds(0, i*25, 120, 30);
			rightTextPanel[i].add(textField[i] = new JTextField(20));
			rightTextPanel[i].setBounds(0, i*25, 250, 30);
			leftPanel.add(leftLabelPanel[i]);
			rightPanel.add(rightTextPanel[i]);
			
		}
		JButton button = new JButton("확인");
		button.setFont(new Font(Main.font, 0, 12));
		button.setBounds(200, 210, 60, 20);
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (Main.dataReader.isHasQuery("SELECT * FROM SUPPLY")){
					Main.dataReader.execute("DELETE FROM SUPPLY");
				}
				/////
				StringBuilder sb=new StringBuilder();
				sb.append("INSERT INTO SUPPLY VALUES('");
				for(int i=0;i<7;i++){
					sb.append(textField[i].getText());
					sb.append("','");
				}
				sb.append(textField[7].getText());
				sb.append("');");
				Main.dataReader.execute(sb.toString());
				setVisible(false);
				supplyView.loadSupply();
			}
		});
		add(button);
		button = new JButton("취소");
		button.setFont(new Font(Main.font, 0, 12));
		button.setBounds(270, 210, 60, 20);
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
			}
		});
		add(button);
		add(rightPanel);
		add(leftPanel);
	}

	void removeField() {
		for (int i = 0; i < 4; i++) {
			textField[i].setText("");
		}
		textField[0].requestFocus();
	}

}
/*
class DemandAddListener extends KeyAdapter implements ActionListener {
	int key;
	DemandAdd demandAdd;
	ListManager<Demand, DemandList> listManager;

	DemandAddListener(int key, ListManager<Demand, DemandList> listManager, DemandAdd demandAdd) {
		this.key = key;
		this.demandAdd = demandAdd;
		this.listManager = listManager;
	}

	public void actionPerformed(ActionEvent e) {
		if (listManager.addItem(demandAdd.getDemand())) {
			listManager.tableUpdate();
			demandAdd.removeField();
		}
	}

	public void keyPressed(KeyEvent keyEvent) {
		if (keyEvent.getKeyCode() == KeyEvent.VK_ENTER) {
			if (key != 3)
				demandAdd.textField[key + 1].requestFocus();
			else {
				if (listManager.addItem(demandAdd.getDemand())) {
					listManager.tableUpdate();
					demandAdd.removeField();
				}
			}
		}
	}

}
*/