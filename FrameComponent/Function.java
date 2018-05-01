package FrameComponent;

import javax.swing.JOptionPane;

import Output.Print;
import Specification.SpecificationView;

// 외부 작동 버튼 함수
public class Function {
	ViewManager viewManager;
	FrameLabel frameLabel;
	Print print;

	public Function(ViewManager viewManager, FrameLabel frameLabel) {
		print = new Print();
		viewManager.getSpecificationView().setFunction(this);
		this.viewManager = viewManager;
		this.frameLabel = frameLabel;
	}

	public void save() {
		if (!viewManager.getProductView().getProductList().isHasData()){
			JOptionPane.showMessageDialog(null, "데이터가 없습니다.");
			return;
		}
		if (frameLabel.getSpec() == null
				|| !frameLabel.getSpec().getName().equals(viewManager.getDemandView().getDemand().getName())) {
			if (viewManager.saveAll())
				JOptionPane.showMessageDialog(null, "저장되었습니다.");

		} else {
			if (viewManager.modifySave())
				JOptionPane.showMessageDialog(null, "저장되었습니다.");
		}
	}

	public void load() {// 불러오기
		viewManager.getSpecificationView().getListManager().setVisible(true);
		
	}

	public void modifySupply() {
		viewManager.getSupplyView().modify();
	}

	// 이전 페이지
	public void leftPage() {
		if (viewManager.getProductView().getCurrPage() > 1) {
			if (viewManager.getProductView().getCurrPage() == 2)
				viewManager.swapPanel("front");
			viewManager.getProductView().removeLastPage();
			viewManager.getProductView().setCurrPage(viewManager.getProductView().getCurrPage() - 1);

		}
		refresh();
	}

	// 뒷 페이지
	public void rightPage() {

		viewManager.getProductView().addLastPage();
		if (viewManager.getProductView().getCurrPage() < viewManager.getProductView().getMaxPage()) {
			if (viewManager.getProductView().getCurrPage() == 1)
				viewManager.swapPanel("back");
			viewManager.getProductView().setCurrPage(viewManager.getProductView().getCurrPage() + 1);
		}
		refresh();
	}
	// 미리보기
	public void preview(){
		if (!viewManager.getProductView().getProductList().isHasData()){
			JOptionPane.showMessageDialog(null, "데이터가 없습니다.");
			return;
		}
		SpecificationView specView = viewManager.getSpecificationView();
		if (viewManager.isModify()) {
			save();
		}
		if (frameLabel.getSpec() != null) {
			specView.loadData(frameLabel.getSpec());
			specView.setVisible(true);
		}
	}
	// 출력
	public void printOutPrinter(int flag) {
		
		if (!viewManager.getProductView().getProductList().isHasData()){
			JOptionPane.showMessageDialog(null, "데이터가 없습니다.");
			return;
		}
		
		SpecificationView specView = viewManager.getSpecificationView();
		if (viewManager.isModify()) {
			save();
		}
		if (frameLabel.getSpec() != null && flag==0) {
			specView.loadData(frameLabel.getSpec());
			print.printToPrinter(specView);
		} else if (specView.isVisible()) {
			print.printToPrinter(specView);
		}
	}

	public void printOutPdf(int flag) {
		if (!viewManager.getProductView().getProductList().isHasData()){
			JOptionPane.showMessageDialog(null, "데이터가 없습니다.");
			return;
		}
		SpecificationView specView = viewManager.getSpecificationView();
		if (viewManager.isModify()) {
			save();
		}
		if (frameLabel.getSpec() != null && flag==0) {
			specView.loadData(frameLabel.getSpec());
			print.printToPdf(specView);
		} else if (specView.isVisible()) {
			print.printToPdf(specView);
		}
	}

	public void modify() {
		viewManager.modifyLoad();

	}

	// 화면 업데이트
	void refresh() {
		viewManager.getProductView().refresh();
		frameLabel.setPageText(viewManager.getProductView().getPageStr());
	}

	public ViewManager getViewManager() {
		// TODO Auto-generated method stub
		return viewManager;
	}

	public void closeWindows() {
		viewManager.getSpecificationView().getListManager().setVisible(false);
	}
}
