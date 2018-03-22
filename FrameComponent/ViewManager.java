package FrameComponent;
import java.awt.CardLayout;
import java.awt.Container;

import javax.swing.JOptionPane;

import Demand.Demand;
import Demand.DemandView;
import Product.ProductView;
import Specification.Specification;
import Specification.SpecificationView;
import Supply.SupplyView;
enum COMPONENT{DEMAND,SUPPLY,PRODUCT}
public class ViewManager {
	public DemandView getDemandView() {
		return demandView;
	}
	protected SupplyView getSupplyView() {
		return supplyView;
	}
	public ProductView getProductView() {
		return productView;
	}
	public SpecificationView getSpecificationView() {
		return specificationView;
	}
	private DemandView demandView;
	private SupplyView supplyView;
	private ProductView productView;
	private SpecificationView specificationView;
	
	Container contentPane;
	private WhitePanel masterPanel;
	private CardLayout cardLayout;
	private FrameLabel frameLabel;
	public ViewManager(Container contentPane, WhitePanel masterPanel, FrameLabel frameLabel){
		this.contentPane=contentPane;
		cardLayout=new CardLayout();
		masterPanel.setLayout(cardLayout);
		masterPanel.setBounds(32, 30, 800, 1000);
		this.masterPanel=masterPanel;
		this.frameLabel=frameLabel;
		demandView = new DemandView();
		supplyView = new SupplyView(this);
		productView = new ProductView(this);
		specificationView = new SpecificationView();
	}
	public FrameLabel getFrameLabel(){
		return frameLabel;
	}
	public void setData(Demand demand) {
		demandView.setDemand(demand);
	}
	public void setTableWidth(int[] tableWidth) {
		productView.setTableWidth(tableWidth);
	}
	public void swapPanel(String str) {
		//front or back
		cardLayout.show(masterPanel, str);
		contentPane.repaint();
	}
	public boolean saveAll() {
		Demand demand;
		Specification specification;
		if (!productView.getProductList().isHasData()){
			JOptionPane.showMessageDialog(null, "데이터가 없습니다.");
			return false;
		}
		demand=demandView.getDemand();
		if (demand.getName() == null || demand.getName().trim().equals("")){
			JOptionPane.showMessageDialog(null, "상호가 비었습니다.");
			return false;
		}
		demand=demandView.saveCurrData();
		specification=specificationView.saveCurrData(demandView);
		productView.saveCurrData(specification.getIdQuery());
		frameLabel.setSpec(specification);
		modifyLoad();
		return true;
	}
	public void modifyLoad() {
		demandView.loadDataId(specificationView.getSpec().getName());
		demandView.setDate(specificationView.getSpec().getDate());
		productView.loadDataId(specificationView.getSpec().getId());
		frameLabel.setSpec(specificationView.getSpec());
	}
	public boolean modifySave() {
		productView.getProductList().removeQuery(frameLabel.getSpec().getId());
		productView.saveCurrData(frameLabel.getSpec().getId());
		modifyLoad();
		return true;
	}
	public boolean isModify() {
		return productView.isModify();
	}
}
