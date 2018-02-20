package FrameComponent;

import Output.Print;
import Specification.SpecificationView;

// �ܺ� �۵� ��ư �Լ�
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
	}

	public void load() {// �ҷ�����
		viewManager.getSpecificationView().getListManager().setVisible(true);
	}
	public void modifySupply(){
		viewManager.getSupplyView().modify();
	}
	// ���� ������
	public void leftPage() {
		if (viewManager.getProductView().getCurrPage() > 1) {
			if (viewManager.getProductView().getCurrPage() == 2)
				viewManager.swapPanel("front");
			viewManager.getProductView().removeLastPage();
			viewManager.getProductView().setCurrPage(viewManager.getProductView().getCurrPage() - 1);

		}
		refresh();
	}

	// �� ������
	public void rightPage() {

		viewManager.getProductView().addLastPage();
		if (viewManager.getProductView().getCurrPage() < viewManager.getProductView().getMaxPage()) {
			if (viewManager.getProductView().getCurrPage() == 1)
				viewManager.swapPanel("back");
			viewManager.getProductView().setCurrPage(viewManager.getProductView().getCurrPage() + 1);
		}
		refresh();
	}

	// ���
	public void printOutPrinter() {
		SpecificationView specView = viewManager.getSpecificationView();
		if (viewManager.isModify()) {
			save();
		}
		if (frameLabel.getSpec() != null) {
			specView.loadData(frameLabel.getSpec());
			print.printToPrinter(specView);
		} else if (specView.isVisible()) {
			print.printToPrinter(specView);
		}
	}
	public void printOutPdf() {
		SpecificationView specView = viewManager.getSpecificationView();
		if (viewManager.isModify()) {
			save();
		}
		if (frameLabel.getSpec() != null) {
			specView.loadData(frameLabel.getSpec());
			print.printToPdf(specView);
		} else if (specView.isVisible()) {
			print.printToPdf(specView);
		}
	}
	

	public void modify() {
		viewManager.modifyLoad();

	}

	// ȭ�� ������Ʈ
	void refresh() {
		viewManager.getProductView().refresh();
		frameLabel.setPageText(viewManager.getProductView().getPageStr());
	}

	public ViewManager getViewManager() {
		// TODO Auto-generated method stub
		return viewManager;
	}
}
