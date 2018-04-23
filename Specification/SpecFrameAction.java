package Specification;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JOptionPane;

import FrameComponent.Function;

public class SpecFrameAction implements ActionListener {

	private Function function;
	private SpecificationView spec;
	public SpecFrameAction(SpecificationView spec, Function function) {
		this.function=function;
		this.spec=spec;
		// TODO Auto-generated constructor stub
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String str= ((JButton) e.getSource()).getToolTipText();
		switch (str) {
		case "�� ����":
			spec.prev();
			break;
		case "���� ��":
			spec.next();
			break;
		case "����":
			if (function.getViewManager().isModify()) {
				int choice = JOptionPane.showConfirmDialog(null, "���� ������ �����Ͻðڽ��ϱ�?", "����", JOptionPane.YES_NO_CANCEL_OPTION,
						JOptionPane.INFORMATION_MESSAGE);
				switch (choice) {
				case 0:
					function.save();
					function.modify();
					break;
				case 1:
					function.modify();
					break;
				}
			}
			else
			{
				function.modify();
			}
			spec.setVisible(false);
			break;
		case "�μ�":
			function.printOutPrinter(1);
			break;
		case "PDF":
			function.printOutPdf(1);
			break;
			
		case "�ݱ�":
			spec.setVisible(false);
			break;
		}
	}

}
