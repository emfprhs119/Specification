package Specification;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JOptionPane;

import FrameComponent.Function;
import Main.Main;

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
		case "편집":
			if (Main.modify) {
				int choice = JOptionPane.showConfirmDialog(null, "변경 내용을 저장하시겠습니까?", "편집", JOptionPane.YES_NO_CANCEL_OPTION,
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
		case "인쇄":
			function.printOut();
			break;
		case "닫기":
			spec.setVisible(false);
			break;
		}
	}

}
