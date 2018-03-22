package FrameComponent;

import java.awt.MenuItem;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JOptionPane;

import Main.Main;

public class MenuAction extends MouseAdapter implements ActionListener {
	JFrame frame;
	Function function;
	boolean isButton;
	public MenuAction(JFrame frame,Function function,boolean isButton){
		this.frame=frame;
		this.function=function;
		this.isButton=isButton;
	}
	public void actionPerformed(ActionEvent e) {
		String str;
		// ��ư �ϰ�� ��ư�� �� �޴� �ϰ�� �޴��� �� 
		if (isButton)
			str= ((JButton) e.getSource()).getToolTipText();
		else
			str = ((MenuItem) e.getSource()).getLabel();
		// �̸��� ���� �Լ� ȣ��
		switch (str) {
		case "�� ����":
			if (function.getViewManager().isModify()) {
				int choice = JOptionPane.showConfirmDialog(null, "���� ������ �����Ͻðڽ��ϱ�?", "����", JOptionPane.YES_NO_CANCEL_OPTION,
						JOptionPane.INFORMATION_MESSAGE);
				switch (choice) {
				case 0:
					function.save();
					new MainFrame(frame.getX(),frame.getY());
					frame.setVisible(false);
				case 1:
					new MainFrame(frame.getX(),frame.getY());
					frame.setVisible(false);
				case 2:
				}
			}
			else
			{
				new MainFrame(frame.getX(),frame.getY());
				frame.setVisible(false);
			}
			break;
		case "�ҷ�����":
			function.load();
			break;
		case "�����ϱ�":
			function.save();
			break;
		case "������ ����":
			function.modifySupply();
		case "�� ����":
			function.leftPage();
			break;
		case "���� ��":
			function.rightPage();
			break;
		case "�̸�����":
			function.preview();
			break;
		case "�μ��ϱ�":
			function.printOutPrinter();
			break;
		case "PDF":
		case "PDF��������":
			function.printOutPdf();
			break;
		case "����":
			if (function.getViewManager().isModify()) {
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
			{
				System.exit(0);
			}
			break;
		case "����":
			//((MainFrame) frame).helpPopup(true);
			break;
		case "About ����":
			JOptionPane.showMessageDialog(frame, "Version : v"+Main.virsion+"\nEmail : emfprhs119@gmail.com", "About ����",1);
			break;
		default:
		}
	}
	/*
	@Override
	public void mouseExited(MouseEvent e) {
		((MainFrame) frame).helpPopup(false);
	}
	@Override
	public void mouseEntered(MouseEvent e) {
		((MainFrame) frame).helpPopup(true);
	}
	*/
}