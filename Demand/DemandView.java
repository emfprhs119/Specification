package Demand;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Properties;

import javax.swing.JButton;
import javax.swing.JFormattedTextField.AbstractFormatter;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import FrameComponent.WhitePanel;
import Inheritance.ColumnManager;
import Inheritance.ListManager;
import Inheritance.View_Interface;
import Main.Main;
//거래처 view
public class DemandView extends WhitePanel implements View_Interface<Demand> {
	WhitePanel leftPanel, rightPanel;
	JPanel rightTextPanel[];
	JPanel leftLabelPanel[];
	JTextField rightTextField[];
	ListManager<Demand,DemandList> listManager;
	UtilDateModel model;
	JButton demandLoadButton; 

	
	public DemandView() {
		model = new UtilDateModel();
		Properties p = new Properties();
		p.put("text.today", "Today");
		p.put("text.month", "Month");
		p.put("text.year", "Year");
		JDatePanelImpl datePanel = new JDatePanelImpl(model, p);
		JDatePickerImpl datePicker = new JDatePickerImpl(datePanel, new DateFormatter());
		setBounds(11, 65, 330, 130);
		initListManager();
		leftPanel = new WhitePanel();
		rightPanel = new WhitePanel();
		leftPanel.setBounds(10, 0, 110, 63);
		rightPanel.setBounds(120, 0, 203, 63);

		rightTextPanel = new JPanel[2];
		leftLabelPanel = new JPanel[2];
		rightTextField = new JTextField[2];
		for (int i = 0; i <2; i++) {
			rightTextPanel[i] = new JPanel();
			leftLabelPanel[i] = new JPanel();
		}
		JLabel leftLabel[] = new JLabel[2];
		leftLabelPanel[0].add(leftLabel[0] = new JLabel("발행일자"));
		leftLabelPanel[1].add(leftLabel[1] = new JLabel("거래처명"));
		rightPanel.add(datePicker);
		rightTextPanel[0].add(datePicker);
		rightTextPanel[1].add(rightTextField[1] = new JTextField(14));

		rightTextPanel[0].setBounds(0,0,203, 34);

		rightTextPanel[1].setLayout(null);
		rightTextPanel[1].setBounds(1, 38, 210, 25);
		
		rightTextField[1].setBounds(0, 1, 174, 25);
		rightTextField[1].setFont(new Font(Main.font, Font.BOLD, 15));
		rightTextField[1].setHorizontalAlignment(JTextField.LEFT);
		
		demandLoadButton = new JButton("...");
		rightTextPanel[1].add(demandLoadButton);
		demandLoadButton.setBounds(174,0,28,25);
		
		demandLoadButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				listManager.setVisible(true);
			}
		});
		model.setSelected(true);
		for (int i = 0; i < 2; i++) {
			
			leftLabelPanel[i].setBounds(10, i * 30, 105, 35);
			leftLabelPanel[i].setBackground(Color.WHITE);
			leftLabel[i].setFont(new Font(Main.font, Font.BOLD, 22));
			leftLabel[i].setHorizontalAlignment(JLabel.RIGHT);
			rightTextPanel[i].setBackground(Color.WHITE);
			leftPanel.add(leftLabelPanel[i]);
			rightPanel.add(rightTextPanel[i]);
		}
		this.add(leftPanel);
		this.add(rightPanel);
	}

	private void initListManager() {
		ColumnManager column=new ColumnManager();
		String strArr[] = { "상호", "등록번호", "담당자" };
		int intArr[] = {120,40,10};
		column.setColumn(strArr,intArr);
		listManager = new ListManager<Demand,DemandList>("거래처 불러오기",this,new DemandList(),column,true);
		new DemandAdd(listManager);
	}

	public void setDemand(Demand demand) {
		rightTextField[1].setText(demand.getName());
	}
	public String getDate(){
		return Main.fullDateFormat.format(model.getValue());
	}
	public void setDate(String date){
			String[] token = date.split("\\.");
			model.setDate(Integer.parseInt(token[0]), Integer.parseInt(token[1])-1, Integer.parseInt(token[2]));

	}
	public Demand getDemand() {
		Demand demand = new Demand();
		demand.setName(rightTextField[1].getText());
		return demand;
	}

	public void refresh() {
		this.repaint();
	}

	public Demand saveCurrData(){
		Demand demand=getDemand();
		if (!listManager.isHas(demand))
			listManager.addItem(demand);

		if (demand.getName()==null || demand.getName().equals(""))
			return null;
		else
			return demand;
	}

	@Override
	public void loadData(Demand demand) {
		setDemand(demand);
	}
	public void loadDataId(String name) {
		setDemand(listManager.getObject(name));
	}

}
class DateFormatter extends AbstractFormatter {

	@Override
	public Object stringToValue(String text) throws ParseException {
		return Main.fullDateFormat.parseObject(text);
	}

	@Override
	public String valueToString(Object value) throws ParseException {
		if (value != null) {
			Calendar cal = (Calendar) value;
			return Main.fullDateFormat.format(cal.getTime());
		}
		return "";
	}
}