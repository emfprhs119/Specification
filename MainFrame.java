import java.awt.Button;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

class MainFrame extends JFrame {
	private Container contentPane;
	private Func func;
	protected void frameInit(){
		super.frameInit();
		setBounds(200, 0, 838, 1045);
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		// setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		contentPane = this.getContentPane();
		contentPane.setLayout(null);
		contentPane.setBackground(Color.WHITE);
	}
	public MainFrame(Estimate est) {
		super("������");
		
		DemandView demandView;
		SupplyView supplyView;
		ProductView productView;
		CardLayout cardlayout;
		
		//�̸�,����,������ ���̺� �ʱ�ȭ
		JLabel name=nameInit();
		JLabel file=fileInit();
		JLabel page=pageInit();
		
		WhitePanel masterPane = new WhitePanel();			//���ĸ� ���� �г�
		WhiteRectPanel frontPanel= new WhiteRectPanel();	//���� �г�
		WhiteRectPanel backPanel = new WhiteRectPanel();	//�ĸ� �г�
		
		
		WhitePanel frontTablePane = new WhitePanel();	//���� ���̺� �г�
		WhitePanel backTablePane = new WhitePanel();	//�ĸ� ���̺� �г�
		
		demandView = new DemandView(est.demand);
		supplyView = new SupplyView(est.supply);
		productView = new ProductView(est.productList,frontTablePane,backTablePane);
		
		//���̾ƿ� ����
		cardlayout=new CardLayout();
		masterPane.setLayout(cardlayout);
		masterPane.setBounds(32, 30, 800, 1000);
		frontTablePane.setBounds(18, 290, 720, 635);
		backTablePane.setBounds(18, 37, 720, 840);
		
		//function Ŭ���� ����
		//func = new Func(supply, demand, masterPane,supplyTable,cardlayout,file,page);
		
		//-----���� �г�-------------------------------
		frontPanel.add(productView.sumTextLabel);	//���鿡�� �ִ� �հ�ݾ� ���̺�
		frontPanel.add(productView.sumText);	//���鿡�� �ִ� �հ�ݾ�
		frontPanel.add(frontTablePane);// �������̺�
		frontPanel.add(demandView);// ������
		frontPanel.add(supplyView);// ������
		frontPanel.add(name);// ������ �ؽ�Ʈ
		//-----�ĸ� �г�-------------------------------
		backPanel.add(backTablePane);// �ĸ� ���̺�
		//-----������ �г�------------------------------
		masterPane.add(frontPanel);
		masterPane.add(backPanel);
		//-----������ �����̳�--------------------------
		//�ϴ� �հ� �߰�
		contentPane.add(productView.sumTextBottom);
		contentPane.add(productView.sumLabelField);
		contentPane.add(productView.sumBlankField);
		//��ư �߰�
		addButton(contentPane,func);
		//���ϸ�,�������� ���̺� �߰�
		contentPane.add(file);
		contentPane.add(page);
		//------------------------------------------
		contentPane.add(masterPane);
		
		//�޴���
		menubarLayout();
		
		//â �ݱ�� ���泻�� ���忩�� Ȯ��
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				if (Main.modify) {
					int choice = JOptionPane.showConfirmDialog(null, "���� ������ �����Ͻðڽ��ϱ�?", "����", JOptionPane.YES_NO_CANCEL_OPTION,
							JOptionPane.INFORMATION_MESSAGE);
					switch (choice) {
					case 0:
						func.dataSave();
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
	private JLabel nameInit() {
		JLabel name = new JLabel("������");
		name.setFont(new Font(Main.font, Font.BOLD, 60));
		name.setBounds(283, 0, 270, 100);
		return name;
	}
	private JLabel fileInit() {
		JLabel file = new JLabel("New Document");
		file.setFont(new Font(Main.font, Font.BOLD, 15));
		file.setBounds(419 - file.getText().length() * 6, 5, 300, 20);
		return file;
	}
	private JLabel pageInit() {
		JLabel page = new JLabel("page1/1");
		page.setFont(new Font("����", 0, 15));
		page.setBounds(43, 30, 80, 30);
		return page;
	}
	private void menubarLayout() {
		ManuAction menuAction = new ManuAction(func);
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
		menuBar.add(mnuFile); // �޴��ٿ� �ָ޴� ���
		menuBar.add(mnuModif); // �޴��ٿ� �ָ޴� ���
		// menuBar.add(mnuFont); // �޴��ٿ� �ָ޴� ���

		this.setMenuBar(menuBar); // frame�� �޴��� ���
	}
	
	private void addButton(Container container,Func func) {
		Button button[] = new Button[7];
		ActionButton action = new ActionButton(func);
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
		for (int i = 0; i < 7; i++) {
			button[i].setFont(new Font(Main.font, Font.BOLD, 25));
			button[i].setVisible(true);
			button[i].addActionListener(action);
			container.add(button[i]);
		}
	}
	class ManuAction implements ActionListener {
		Func func;
		ManuAction(Func func){
			this.func=func;
		}
		public void actionPerformed(ActionEvent e) {
			String str = ((MenuItem) e.getSource()).getLabel();
			switch (str) {
			case "�� ������":
				if (Main.modify) {
					int choice = JOptionPane.showConfirmDialog(null, "���� ������ �����Ͻðڽ��ϱ�?", "����", JOptionPane.YES_NO_CANCEL_OPTION,
							JOptionPane.INFORMATION_MESSAGE);
					switch (choice) {
					case 0:
						func.dataSave();
						new MainFrame(new Estimate());
						setVisible(false);
					case 1:
						new MainFrame(new Estimate());
						setVisible(false);
					case 2:
					}
				}
				else
				{
					new MainFrame(new Estimate());
					setVisible(false);
				}
				break;
			case "�ҷ�����":
				func.load();
				break;
			case "�����ϱ�":
				func.dataSave();
				break;
			case "Pdf ��������":
				func.pdfSave();
				break;
			case "����":
				setVisible(false);
			case "�ŷ�ó ��� �ҷ�����":
				func.loadDemand();
				break;
			default:
			}
			func.after();
		}
	}
}

class ActionButton implements ActionListener {
	Func func;
	ActionButton(Func func){
		this.func=func;
	}
	public void actionPerformed(ActionEvent e) {
		if (((Button) e.getSource()).getLabel().equals("����")) {
			func.dataSave();
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
			//func.sort();
		}
		if (((Button) e.getSource()).getLabel().equals("���")) {
			func.loadDemand();
		}
		func.after();

	}
}

//����� �Ͼ���̰� layout�� null�� Container �ܰ��� draw
class WhiteRectPanel extends WhitePanel {
	private static final long serialVersionUID = 6805380713240246261L;
	public void paint(Graphics g) {
		super.paint(g);
		g.drawRect(0, 0, 755,875);
	}
	
}

//����� �Ͼ���̰� layout�� null�� panel
class WhitePanel extends JPanel {
	private static final long serialVersionUID = 5640668174921441140L;
	WhitePanel(){
		setBackground(Color.WHITE);
		setLayout(null);
	}
}

