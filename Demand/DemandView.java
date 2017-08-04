package Demand;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
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
/*
class UtilCalendarModel extends AbstractDateModel<java.util.Calendar> {
	
	public UtilCalendarModel() {
		this(null);
	}
	
	public UtilCalendarModel(Calendar value) {
		super();
		setValue(value);
	}

	@Override
	protected Calendar fromCalendar(Calendar from) {
		return (Calendar)from.clone();
	}

	@Override
	protected Calendar toCalendar(Calendar from) {
		return (Calendar)from.clone();
	}
	
}
*/
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
		setBounds(0, 94, 350, 320);
		initListManager();
		leftPanel = new WhitePanel();
		rightPanel = new WhitePanel();
		leftPanel.setBounds(45, 4, 350, 150);
		rightPanel.setBounds(15, 0, 115, 150);

		rightTextPanel = new JPanel[5];
		leftLabelPanel = new JPanel[5];
		rightTextField = new JTextField[6];
		for (int i = 0; i < 5; i++) {
			rightTextPanel[i] = new JPanel();
			leftLabelPanel[i] = new JPanel();
		}
		JLabel leftLabel[] = new JLabel[6];
		leftLabelPanel[0].add(leftLabel[0] = new JLabel("발행일자:"));
		leftLabelPanel[1].add(leftLabel[1] = new JLabel("등록번호:"));
		leftLabelPanel[2].add(leftLabel[2] = new JLabel("상      호:"));
		leftLabelPanel[3].add(leftLabel[3] = new JLabel("전화번호:"));
		leftLabelPanel[4].add(leftLabel[4] = new JLabel("담 당 자 :"));

		rightTextPanel[0].add(datePicker);
		rightTextPanel[1].add(rightTextField[1] = new JTextField(14));
		rightTextPanel[2].add(rightTextField[2] = new JTextField(14));
		rightTextPanel[3].add(rightTextField[3] = new JTextField(14));
		rightTextPanel[4].add(rightTextField[4] = new JTextField(14));
		
		demandLoadButton = new JButton("...");
		demandLoadButton.setBounds(181,1,28,25);
		demandLoadButton.setPreferredSize(new Dimension(28, 25));
		
		
		demandLoadButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				listManager.setVisible(true);
			}
		});
		rightTextPanel[1].add(demandLoadButton);
		model.setSelected(true);
		for (int i = 0; i < 5; i++) {
			
			rightTextPanel[i].setBounds(80, i*30+5, 210, 32);
			leftLabelPanel[i].setBounds(10, i * 30, 105, 35);
			rightTextPanel[i].setBackground(Color.WHITE);
			leftLabelPanel[i].setBackground(Color.WHITE);
			leftLabel[i].setFont(new Font(Main.font, Font.BOLD, 22));
			leftLabel[i].setHorizontalAlignment(JLabel.RIGHT);
			if (i != 0) {
				rightTextPanel[i].setLayout(null);
				rightTextField[i].setBounds(7, 1, 174, 25);
				rightTextField[i].setFont(new Font(Main.font, Font.BOLD, 15));
				rightTextField[i].setHorizontalAlignment(JTextField.LEFT);
				rightTextField[i].addKeyListener(new DemandListener(i,rightTextField));
			}
			rightPanel.add(leftLabelPanel[i]);
			leftPanel.add(rightTextPanel[4 - i]);
		}
		
		//위치 조정
		rightTextPanel[0].setBounds(15, 0, 346, 200);
		leftLabelPanel[1].setBounds(10 - 1, 1 * 30, 105, 35);
		leftLabelPanel[2].setBounds(10 - 3, 2 * 30, 105, 35);

		this.add(rightPanel);
		this.add(leftPanel);
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
		rightTextField[1].setText(demand.getRegNum());
		rightTextField[2].setText(demand.getName());
		rightTextField[3].setText(demand.getTel());
		rightTextField[4].setText(demand.getWho());
	}
	public String getDate(){
		return Main.fullDateFormat.format(model.getValue());//datePicker.get;
	}
	public void setDate(String date){
			String[] token = date.split("\\.");
			model.setDate(Integer.parseInt(token[0]), Integer.parseInt(token[1])-1, Integer.parseInt(token[2]));

	}
	public Demand getDemand() {
		Demand demand = new Demand();
		demand.setRegNum(rightTextField[1].getText());
		demand.setName(rightTextField[2].getText());
		demand.setTel(rightTextField[3].getText());
		demand.setWho(rightTextField[4].getText());
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
class DemandListener extends KeyAdapter{
	JTextField textField[];
	int key;
	DemandListener(int key,JTextField textField[]){
		this.key=key;
		this.textField=textField;
	}
		public void keyPressed(KeyEvent keyEvent) {
			if (keyEvent.getKeyCode() == KeyEvent.VK_ENTER) {
				if (key != 4)
					textField[key + 1].requestFocus();
			} else
				Main.modify = true;
		}
}