package Demand;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import FrameComponent.WhitePanel;
import Inheritance.ListManager;
import Main.Main;
//거래처 추가
class DemandAdd extends JFrame  {
	JTextField textField[];
	DemandAdd(ListManager<Demand,DemandList> listManager) {
		super("거래처 추가");
		setLayout(null);
		setBounds(500, 500, 235, 200);
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setResizable(false);
		listManager.setAddFrame(this);
		WhitePanel leftPanel = new WhitePanel();
		WhitePanel rightPanel = new WhitePanel();
		leftPanel.setLayout(null);
		leftPanel.setBounds(65, 0, 180, 180);
		rightPanel.setBounds(0, 0, 65, 180);

		JPanel[] rightTextPanel = new JPanel[4];
		JPanel[] leftLabelPanel = new JPanel[4];
		textField = new JTextField[4];
		for (int i = 0; i < 4; i++) {
			rightTextPanel[i] = new JPanel();
			leftLabelPanel[i] = new JPanel();
		}
		JLabel label[] = new JLabel[4];
		leftLabelPanel[0].add(label[0] = new JLabel("등록번호 :"));
		leftLabelPanel[1].add(label[1] = new JLabel("상      호 :"));
		leftLabelPanel[2].add(label[2] = new JLabel("전화번호:"));
		leftLabelPanel[3].add(label[3] = new JLabel("담 당 자 :"));

		rightTextPanel[0].add(textField[0] = new JTextField(11));
		rightTextPanel[1].add(textField[1] = new JTextField(11));
		rightTextPanel[2].add(textField[2] = new JTextField(11));
		rightTextPanel[3].add(textField[3] = new JTextField(11));
		for (int i = 0; i < 4; i++) {
			rightTextPanel[i].setBounds(0, i * 30, 150, 35);
			leftLabelPanel[i].setBounds(-10, i * 30, 90, 35);
			rightTextPanel[i].setBackground(Color.WHITE);
			leftLabelPanel[i].setBackground(Color.WHITE);
			label[i].setFont(new Font(Main.font, Font.BOLD, 13));
			label[i].setHorizontalAlignment(JLabel.RIGHT);
			textField[i].setFont(new Font(Main.font, Font.BOLD, 13));
			textField[i].setHorizontalAlignment(JTextField.LEFT);
			textField[i].addKeyListener(new DemandAddListener(i,listManager, this));
			rightPanel.add(leftLabelPanel[i]);
			leftPanel.add(rightTextPanel[3 - i]);
		}
		JButton button = new JButton("추가");
		button.setFont(new Font(Main.font, 0, 12));
		button.setBounds(80, 130, 60, 20);
		button.addActionListener(new DemandAddListener(0,listManager, this));
		add(button);
		button = new JButton("닫기");
		button.setFont(new Font(Main.font, 0, 12));
		button.setBounds(150, 130, 60, 20);
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
			}
		});
		add(button);
		add(rightPanel);
		add(leftPanel);
	}

	Demand getDemand() {
		Demand demand = new Demand();
		demand.setRegNum(textField[0].getText());
		demand.setName(textField[1].getText());
		demand.setTel(textField[2].getText());
		demand.setWho(textField[3].getText());
		return demand;
	}

	void removeField() {
		for (int i = 0; i < 4; i++) {
			textField[i].setText("");
		}
		textField[0].requestFocus();
	}

}
class DemandAddListener extends KeyAdapter implements ActionListener {
	int key;
	DemandAdd demandAdd;
	ListManager<Demand,DemandList> listManager;
	DemandAddListener(int key,ListManager<Demand,DemandList> listManager, DemandAdd demandAdd) {
		this.key = key;
		this.demandAdd =demandAdd;
		this.listManager=listManager;
	}
	public void actionPerformed(ActionEvent e) {
		if (listManager.addItem(demandAdd.getDemand())){
			listManager.tableUpdate();
			demandAdd.removeField();
		}
	}
	public void keyPressed(KeyEvent keyEvent) {
		if (keyEvent.getKeyCode() == KeyEvent.VK_ENTER) {
			if (key != 3)
				demandAdd.textField[key + 1].requestFocus();
			else{
				if (listManager.addItem(demandAdd.getDemand())){
					listManager.tableUpdate();
					demandAdd.removeField();
				}
			}
		}
	}
}