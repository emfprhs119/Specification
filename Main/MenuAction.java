package Main;

import java.awt.Button;
import java.awt.MenuItem;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import FrameComponent.MainFrame;

public class MenuAction implements ActionListener {
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
			str= ((Button) e.getSource()).getLabel();
		else
			str = ((MenuItem) e.getSource()).getLabel();
		// �̸��� ���� �Լ� ȣ��
		switch (str) {
		case "�� ������":
			if (Main.modify) {
				int choice = JOptionPane.showConfirmDialog(null, "���� ������ �����Ͻðڽ��ϱ�?", "����", JOptionPane.YES_NO_CANCEL_OPTION,
						JOptionPane.INFORMATION_MESSAGE);
				switch (choice) {
				case 0:
					function.save();
					new MainFrame();
					frame.setVisible(false);
				case 1:
					new MainFrame();
					frame.setVisible(false);
				case 2:
				}
			}
			else
			{
				new MainFrame();
				frame.setVisible(false);
			}
			break;
		case "�ҷ�����":
			function.load();
			break;
		case "�����ϱ�":
			function.save();
			break;
		case "��":
			function.leftPage();
			break;
		case "��":
			function.rightPage();
			break;
		case "Pdf ��������":
		case "��������":
			function.pdfSave();
			break;
		case "����":
			System.exit(0);
			break;
		default:
		}
	}
}