package FrameComponent;
import java.awt.Button;
import java.awt.Color;
import java.awt.Container;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import Main.Function;
import Main.Main;
import Main.MenuAction;

public class MainFrame extends JFrame {
	private Container contentPane;
	private Function function;
	protected void frameInit(){
		super.frameInit();
		setBounds(200, 0, 838, 1045);
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		// setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		contentPane = this.getContentPane();
		contentPane.setLayout(null);
		contentPane.setBackground(Color.WHITE);
	}
	public MainFrame() {
		super("������");
		//�̸�,����,������ ���̺� �ʱ�ȭ
		FrameLabel frameLabel = new FrameLabel(this);
		
		WhitePanel masterPanel = new WhitePanel();			//���ĸ� ���� �г�
		WhiteRectPanel frontPanel= new WhiteRectPanel();	//���� �г�
		WhiteRectPanel backPanel = new WhiteRectPanel();	//�ĸ� �г�
		
		ViewManager viewManager = new ViewManager(contentPane,masterPanel,frameLabel);	//�� ����
		//function Ŭ���� ����
		function = new Function(viewManager,frameLabel);
		MenuAction action;
		action = new MenuAction(this,function,true);
		FrameButton frameButton = new FrameButton(action);
		action = new MenuAction(this,function,false);
		FrameMenuBar menuBar =new FrameMenuBar(action);
		//-----���� �г�-------------------------------
		frontPanel.add(viewManager.getProductView().getSumTextLabel());	//���鿡�� �ִ� �հ�ݾ� ���̺�
		frontPanel.add(viewManager.getProductView().getSumText());	//���鿡�� �ִ� �հ�ݾ�
		frontPanel.add(viewManager.getProductView().getFrontTablePanel());// �������̺�
		frontPanel.add(viewManager.getDemandView());// ������
		frontPanel.add(viewManager.getSupplyView());// ������
		frontPanel.add(frameLabel.getTitle());// ������ �ؽ�Ʈ
		//-----�ĸ� �г�-------------------------------
		backPanel.add(viewManager.getProductView().getBackTablePanel());// �ĸ� ���̺�
		//-----������ �г�------------------------------
		masterPanel.add(frontPanel,"front");
		masterPanel.add(backPanel,"back");
		//-----������ �����̳�--------------------------
		//�ϴ� �հ� �߰�
		contentPane.add(viewManager.getProductView().getSumTextBottom());
		contentPane.add(viewManager.getProductView().getSumLabelField());
		contentPane.add(viewManager.getProductView().getSumBlankField());
		//��ư �߰�
		Button[] buttons=frameButton.getButtons();
		for(Button button:buttons){
			contentPane.add(button);
		}
		//���ϸ�,�������� ���̺� �߰�
		contentPane.add(frameLabel.getFileName());
		contentPane.add(frameLabel.getPage());
		//------------------------------------------
		contentPane.add(masterPanel);
		
		//�޴���
		setMenuBar(menuBar.getMenuBar()); // frame�� �޴��� ���
		//â �ݱ�� ���泻�� ���忩�� Ȯ��
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				if (Main.modify) {
					int choice = JOptionPane.showConfirmDialog(null, "���� ������ �����Ͻðڽ��ϱ�?", "����", JOptionPane.YES_NO_CANCEL_OPTION,
							JOptionPane.INFORMATION_MESSAGE);
					switch (choice) {
					case 0:
						function.save();
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
	
	
	
	
}



