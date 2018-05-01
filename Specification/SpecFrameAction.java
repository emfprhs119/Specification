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
			/*
		case "����":
			
			break;
			*/
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
