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
		// 버튼 일경우 버튼의 라벨 메뉴 일경우 메뉴의 라벨 
		if (isButton)
			str= ((JButton) e.getSource()).getToolTipText();
		else
			str = ((MenuItem) e.getSource()).getLabel();
		// 이름에 따른 함수 호출
		switch (str) {
		case "새 명세서":
			if (function.getViewManager().isModify()) {
				int choice = JOptionPane.showConfirmDialog(null, "변경 내용을 저장하시겠습니까?", "종료", JOptionPane.YES_NO_CANCEL_OPTION,
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
		case "불러오기":
			function.load();
			break;
		case "저장하기":
			function.save();
			break;
		case "공급자 수정":
			function.modifySupply();
		case "◀ 이전":
			function.leftPage();
			break;
		case "다음 ▶":
			function.rightPage();
			break;
		case "미리보기":
			function.preview();
			break;
		case "인쇄하기":
			function.printOutPrinter();
			break;
		case "PDF":
		case "PDF내보내기":
			function.printOutPdf();
			break;
		case "종료":
			if (function.getViewManager().isModify()) {
				int choice = JOptionPane.showConfirmDialog(null, "변경 내용을 저장하시겠습니까?", "종료", JOptionPane.YES_NO_CANCEL_OPTION,
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
		case "도움말":
			//((MainFrame) frame).helpPopup(true);
			break;
		case "About 명세서":
			JOptionPane.showMessageDialog(frame, "Version : v"+Main.virsion+"\nEmail : emfprhs119@gmail.com", "About 명세서",1);
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