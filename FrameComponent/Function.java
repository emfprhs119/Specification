package FrameComponent;

import Main.Main;
import Output.Print;
import Specification.SpecificationView;

// 외부 작동 버튼 함수
public class Function {
	ViewManager viewManager;
	FrameLabel frameLabel;
	Print print;

	public Function(ViewManager viewManager, FrameLabel frameLabel) {
		print = new Print(viewManager);
		viewManager.getSpecificationView().setFunction(this);
		this.viewManager = viewManager;
		this.frameLabel = frameLabel;
	}

	public void save() {
		if (frameLabel.getSpec() == null || !frameLabel.getSpec().getName().equals(viewManager.getDemandView().getDemand().getName())) {
			viewManager.saveAll();
		} else {
			viewManager.modifySave();
		}
		Main.modify = false;
	}

	public void load() {// 불러오기
		viewManager.getSpecificationView().getListManager().setVisible(true);
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

	// 출력
	public void printOut() {
		SpecificationView specView = viewManager.getSpecificationView();
		if (Main.modify) {
			save();
		}
		if (frameLabel.getSpec() != null) {
			specView.loadData(frameLabel.getSpec());
			print.printToPrinter(specView);
		} else if (specView.isVisible()) {
			print.printToPrinter(specView);
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
}
