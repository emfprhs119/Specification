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
		case "◀ 이전":
			spec.prev();
			break;
		case "다음 ▶":
			spec.next();
			break;
			/*
		case "편집":
			
			break;
			*/
		case "인쇄":
			function.printOutPrinter(1);
			break;
		case "PDF":
			function.printOutPdf(1);
			break;
			
		case "닫기":
			spec.setVisible(false);
			break;
		}
	}

}
