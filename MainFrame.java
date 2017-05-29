import java.awt.Button;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

class MainFrame extends JFrame {
	Est est;
	Func func;
	ActionButton action;
	Container contentPane;
	ContainerRect contentsPane;
	ContainerRect contentAddPane;
	MyPanel paneName, supplyPane, demandPane, backG, currPane;
	JLabel name, file;
	JLabel page;
	JPanel paneAdd;
	Demand demand;
	SupTable supTable;
	public static int curPage, flag;
	CardLayout card = new CardLayout();
	String displayFont, tableFont;
	int tableFontSize;
	Button button[] = new Button[9];

	public MainFrame() {
		super("������");
		setBounds(200, 0, 838, 1045);
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		// setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBackground(Color.WHITE);
		contentPane = this.getContentPane();
		contentPane.setLayout(null);

		buttonInit();
		nameInit();
		contentsPane = new ContainerRect();
		contentAddPane = new ContainerRect();
		// contentsPane.setBounds(32, 30, 800, 1000);
		// contentAddPane.setBounds(32, 10000, 800, 1000);

		backG = new MyPanel();
		currPane = new MyPanel();
		currPane.setLayout(card);
		currPane.setBounds(32, 30, 800, 1000);

		Supply supply = new Supply();

		demand = new Demand();
		paneAdd = new JPanel();
		paneAdd.setBackground(Color.WHITE);
		supTable = new SupTable(paneAdd);
		Scanner scan;
		String str = null;
		func = new Func(this, supply, demand, supTable);
		action.func = func;
		demand.setFunc(func);

		backG.setBounds(0, 0, 2000, 2000);
		supTable.setBounds(18, 290, 720, 635);
		paneAdd.setBounds(18, 37, 720, 840);
		supply.setBounds(358, 90, 500, 200);
		demand.setBounds(0, 124, 350, 290);

		contentsPane.add(supTable.sum);
		contentsPane.add(supTable.sumT);
		/*contentsPane.add(supTable.sumF2);
		contentsPane.add(supTable.sumT2);
		contentsPane.add(supTable.sumR2);*/
		contentsPane.add(supTable);// ���̺�
		contentsPane.add(demand);// ������
		contentsPane.add(supply);// ������
		contentsPane.add(paneName);// ������ �ؽ�Ʈ

		contentAddPane.add(paneAdd);// 2�� ���� ���̺�

		supTable.setPopup(supTable,paneAdd);
		
		add(supTable.sumF2);
		add(supTable.sumT2);
		add(supTable.sumR2);

		currPane.add(contentsPane);
		currPane.add(contentAddPane);

		contentPane.add(file);
		contentPane.add(page);
		contentPane.add(currPane);
		// contentPane.add(contentAddPane);
		add(backG);
		menuLayout();
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				if (Main.modify) {
					int choice = JOptionPane.showConfirmDialog(null, "���� ������ �����Ͻðڽ��ϱ�?", "����", JOptionPane.YES_NO_CANCEL_OPTION,
							JOptionPane.INFORMATION_MESSAGE);
					switch (choice) {
					case 0:
						func.save();
						System.exit(0);
					case 1:
						System.exit(0);
					case 2:
					}
				}
				else
					System.exit(0);
			}
		});
		setVisible(true);

	}

	private void menuLayout() {
		ManuAction menuAction = new ManuAction();
		MenuBar menuBar = new MenuBar(); // �޴���
		Menu mnuFile = new Menu("����"); // �ָ޴�
		MenuItem mnuNew = new MenuItem("�� ������"); // �θ޴�..
		MenuItem mnuOpen = new MenuItem("�ҷ�����");
		MenuItem mnuSave = new MenuItem("�����ϱ�");
		MenuItem mnuSup = new MenuItem("������ ����");
		MenuItem mnuExport = new MenuItem("Pdf ��������");
		MenuItem mnuExit = new MenuItem("����");
		mnuNew.addActionListener(menuAction);
		mnuSave.addActionListener(menuAction);
		mnuSup.addActionListener(menuAction);
		mnuOpen.addActionListener(menuAction);
		mnuExport.addActionListener(menuAction);
		mnuExit.addActionListener(menuAction);
		mnuFile.add(mnuNew);
		mnuFile.add(mnuOpen);
		mnuFile.add(mnuSave);
		// mnuFile.add(mnuSup);//////////////////////���� �߰�
		mnuFile.add(mnuExport);
		mnuFile.addSeparator(); // ���м�
		mnuFile.add(mnuExit);

		Menu mnuModif = new Menu("����");
		MenuItem mnuGetDemend = new MenuItem("�ŷ�ó ��� �ҷ�����");
		MenuItem mnuTableSort = new MenuItem("���̺� �����ϱ�");
		mnuGetDemend.addActionListener(menuAction);
		mnuTableSort.addActionListener(menuAction);
		mnuModif.add(mnuGetDemend);
		//mnuModif.add(mnuTableSort);
		
		/*
		 * Menu mnuFont = new Menu("����"); Menu mnuViewFont = new Menu("ȭ�� �۲�"); Menu mnuTableFont = new
		 * Menu("���̺� �۲�"); Menu mnuTableFontSize = new Menu("���̺� �۲� ũ��");
		 * 
		 * GraphicsEnvironment ge = null; ge = GraphicsEnvironment.getLocalGraphicsEnvironment(); Font[]
		 * fonts = ge.getAllFonts(); String fontName; MenuItem item;
		 * 
		 * for(int i=0; i<fonts.length;i++){ if(fonts[i].canDisplay('��')){ item = new
		 * MenuItem(fonts[i].getFontName()); item.addActionListener(menuAction); mnuViewFont.add(item);
		 * item = new MenuItem(fonts[i].getFontName()); item.addActionListener(menuAction);
		 * mnuTableFont.add(item); } } for(int i=10; i<31;i++){ item = new MenuItem(String.valueOf(i));
		 * item.addActionListener(menuAction); mnuTableFontSize.add(item);
		 * 
		 * } mnuFont.add(mnuViewFont); mnuFont.add(mnuTableFont); mnuFont.add(mnuTableFontSize);
		 */
		menuBar.add(mnuFile); // �޴��ٿ� �ָ޴� ���
		menuBar.add(mnuModif); // �޴��ٿ� �ָ޴� ���
		// menuBar.add(mnuFont); // �޴��ٿ� �ָ޴� ���

		this.setMenuBar(menuBar); // frame�� �޴��� ���
	}

	class ManuAction implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			String str = ((MenuItem) e.getSource()).getLabel();
			switch (str) {
			case "�� ������":
				if (Main.modify) {
					int choice = JOptionPane.showConfirmDialog(null, "���� ������ �����Ͻðڽ��ϱ�?", "����", JOptionPane.YES_NO_CANCEL_OPTION,
							JOptionPane.INFORMATION_MESSAGE);
					switch (choice) {
					case 0:
						func.save();
						new MainFrame();
						setVisible(false);
					case 1:
						new MainFrame();
						setVisible(false);
					case 2:
					}
				}
				else
				{
					new MainFrame();
					setVisible(false);
				}
				break;
			case "�ҷ�����":
				func.load();
				break;
			case "�����ϱ�":
				func.save();
				break;
			case "Pdf ��������":
				func.pdfSave();
				break;
			case "����":
				setVisible(false);
			case "�ŷ�ó ��� �ҷ�����":
				func.loadDemand();
				break;
			case "���̺� �����ϱ�":
				func.sort();
				break;
			default:
				if (((Menu) ((MenuItem) e.getSource()).getParent()).getLabel().equals("ȭ�� �۲�")) {
					displayFont = str;
					setDisplayFont();
				} else if (((Menu) ((MenuItem) e.getSource()).getParent()).getLabel().equals("���̺� �۲�")) {
					tableFont = str;
					setTableFont(tableFont, tableFontSize);
				} else if (((Menu) ((MenuItem) e.getSource()).getParent()).getLabel().equals("���̺� �۲� ũ��")) {
					tableFontSize = Integer.parseInt(str);
					setTableFont(tableFont, tableFontSize);
				}
			}
			func.after();
		}
	}

	public void setDisplayFont() {
		name.setFont(new Font(displayFont, 0, 60));
		file.setFont(new Font(displayFont, 0, 15));
		contentsPane.repaint();
		repaint();
	}

	public void setTableFont(String font, int size) {
		supTable.setFont(font, size);
		contentsPane.repaint();
		repaint();
	}

	public void nameInit() {
		name = new JLabel("������");
		name.setFont(new Font(Main.font, Font.BOLD, 60));
		name.setBounds(275, 0, 270, 100);
		file = new JLabel("New Document");
		file.setFont(new Font(Main.font, Font.BOLD, 15));
		file.setBounds(419 - file.getText().length() * 6, 5, 300, 20);
		page = new JLabel("page1/1");
		page.setFont(new Font("����", 0, 15));
		page.setBounds(43, 30, 80, 30);
		paneName = new MyPanel();
		paneName.setBounds(8, 0, 500, 290);
		paneName.add(name);
	}

	public void buttonInit() {
		action = new ActionButton();

		button[0] = new Button("��������");
		button[0].setBounds(620, 925, 140, 45);
		button[1] = new Button("����");
		button[1].setBounds(480, 925, 120, 45);
		button[2] = new Button("�ҷ�����");
		button[2].setBounds(310, 925, 150, 45);
		button[3] = new Button("��");
		button[3].setBounds(60, 925, 70, 45);
		button[4] = new Button("��");
		button[4].setBounds(150, 925, 70, 45);
		button[5] = new Button("�߰�");
		button[5].setBounds(230, 925, 70, 20);
		button[6] = new Button("����");
		button[6].setBounds(230, 925 + 30, 70, 20);
		/*
		 * button[7] = new Button("����"); button[7].setBounds(5, 330, 35, 27); button[8] = new
		 * Button("���"); button[8].setBounds(5, 190, 35, 27);
		 */
		for (int i = 0; i < 7; i++) {
			button[i].setFont(new Font(Main.font, Font.BOLD, 25));
			button[i].setVisible(true);
			button[i].addActionListener(action);
			add(button[i]);
			// Adding ActionListener on the Button
		}
		/*
		 * button[7].setFont(new Font(Main.font, Font.BOLD, 15)); button[8].setFont(new Font(Main.font,
		 * Font.BOLD, 15));
		 */
	}
}

class ActionButton implements ActionListener {
	Func func;

	public void actionPerformed(ActionEvent e) {
		if (((Button) e.getSource()).getLabel().equals("����")) {
			func.save();
		}
		if (((Button) e.getSource()).getLabel().equals("��������")) {
			func.pdfSave();
		}
		if (((Button) e.getSource()).getLabel().equals("�ҷ�����")) {
			func.load();
		}
		if (((Button) e.getSource()).getLabel().equals("��")) {
			func.leftPage();
		}
		if (((Button) e.getSource()).getLabel().equals("��")) {
			func.rightPage();
		}
		if (((Button) e.getSource()).getLabel().equals("�߰�")) {
			func.addPage();
		}
		if (((Button) e.getSource()).getLabel().equals("����")) {
			func.removePage();
		}
		if (((Button) e.getSource()).getLabel().equals("����")) {
			func.sort();
		}
		if (((Button) e.getSource()).getLabel().equals("���")) {
			func.loadDemand();
		}
		func.after();

	}
}
