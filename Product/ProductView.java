package Product;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.MenuItem;
import java.awt.Point;
import java.awt.PopupMenu;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Properties;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.ListSelectionModel;
import javax.swing.Popup;
import javax.swing.PopupFactory;
import javax.swing.table.DefaultTableCellRenderer;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.UtilDateModel;

import FrameComponent.ViewManager;
import FrameComponent.WhitePanel;
import Inheritance.ColumnManager;
import Inheritance.ListManager;
import Inheritance.View_Interface;
import Main.Main;

enum COLUM {
	¿ùÀÏ, Ç°¸ñÄÚµå, Ç°¸ñ, ±Ô°Ý, ¼ö·®, ´Ü°¡, °ø±Þ°¡¾×, ¼¼¾×
}

// Ç°¸ñ È­¸é
public class ProductView implements View_Interface<ProductList> {
	String[] columStr = { "¿ùÀÏ", "Ç°¸ñÄÚµå", "Ç°¸ñ", "±Ô°Ý", "¼ö·®", "´Ü°¡", "°ø±Þ°¡¾×", "¼¼¾×" };
	private int maxPage = 1; // ÀüÃ¼ ÆäÀÌÁö
	private int currPage = 1; // ÇöÀç ÆäÀÌÁö

	ProductList prevProductList; // »óÇ° ¸®½ºÆ®
	ProductList productList; // »óÇ° ¸®½ºÆ®

	WhitePanel frontTablePanel;
	WhitePanel backTablePanel;

	MyJTable frontTable; // Àü¸é Å×ÀÌºí
	MyJTable backTable; // ÈÄ¸é Å×ÀÌºí

	private JTextField sumText; // Àü¸é ÇÕ°è,ÇÏ´Ü ÇÕ°è
	private JTextField sumTextBottom;
	private JTextField sumTaxBottom;

	private JTextField sumLabelField; // ÇÏ´Ü ÇÕ°è ·¹ÀÌºí ÇÊµå¿Í ºóÄ­ ÇÊµå
	private JTextField sumBlankField;
	private JLabel sumTextLabel; // Àü¸é ÇÕ°è±Ý¾× ¶óº§

	int selx = 0, sely = 0; // ¼±ÅÃÇÑ Çà·Ä
	long calcData = 0; // °è»êµÈ ÇÕ°è±Ý¾×
	String copyString = null; // º¹»ç ºÙ¿©³Ö±â¸¦ À§ÇÑ ¹®ÀÚ¿­

	String funcStr[] = { "Çà Ãß°¡ (ctrl+shift+a)", "Çà Á¦°Å (ctrl+shift+d)", "Çà º¹»ç (ctrl+shift+c)", "Çà Àß¶ó³»±â (ctrl+shift+x)",
			"Çà ºÙ¿©³Ö±â (ctrl+shift+v)", "Çà ¿Ã¸®±â (ctrl+shift+up)", "Çà ³»¸®±â (ctrl+shift+down)", "¼¿ º¹»ç (ctrl+c)",
			"¼¿ Àß¶ó³»±â (ctrl+x)", "¼¿ ºÙ¿©³Ö±â (ctrl+v)" };
	String prevText;
	ViewManager viewManager;
	String today;

	
	UtilDateModel model;
	JDatePanelImpl datePanel;
	MyJTable currTable;
	int dateRow, dateCol;
	Popup popup;
	ListManager<Product, ProductList> listManager;
	boolean autocompleteFlag;
	
	public ProductView(ViewManager viewManager) {
		sumTextInit();
		this.viewManager = viewManager;
		productList = new ProductList();
		prevProductList=new ProductList();

		initListManager();
		Object frontRow[][] = new Object[Main.FrontRow][8]; // Àü¸é Å×ÀÌºí Çà·Ä
		Object backRow[][] = new Object[Main.BackRow][8]; // ÈÄ¸é Å×ÀÌºí Çà·Ä
		Object column[] = { "¿ùÀÏ", "Ç°¸ñÄÚµå", "Ç°¸ñ", "±Ô°Ý", "¼ö·®", "´Ü°¡", "°ø±Þ°¡¾×", "¼¼¾×" };

		// ³¯Â¥ ¼±ÅÃ
		model = new UtilDateModel();
		model.setSelected(true);
		Properties p = new Properties();
		p.put("text.today", "Today");
		p.put("text.month", "Month");
		p.put("text.year", "Year");
		datePanel = new JDatePanelImpl(model, p);
		datePanel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				currTable.setValueAt(Main.dateFormat.format(model.getValue()), dateRow, dateCol);
				showDatePopup(null, null, false);
			}
		});
		datePanel.setBackground(Color.black);

		
		// Å×ÀÌºí ÆÐ³Î
		frontTablePanel = new WhitePanel();
		backTablePanel = new WhitePanel();

		frontTablePanel.setBounds(18, 290, 720, 635);
		backTablePanel.setBounds(18, 37, 720, 840);
		// Å×ÀÌºí »ý¼º
		frontTable = new MyJTable((Object[][]) frontRow, column);
		backTable = new MyJTable((Object[][]) backRow, column);
		// Çì´õ ÆùÆ®
		frontTable.getTableHeader().setFont(new Font(Main.font, 0, Main.fontSize));
		backTable.getTableHeader().setFont(new Font(Main.font, 0, Main.fontSize));

		// ½ºÅ©·ÑÆÒ Á¶Á¤
		JScrollPane frontScroll = new JScrollPane(frontTable);
		frontScroll.setBackground(Color.WHITE);
		JScrollPane backScroll = new JScrollPane(backTable);
		backScroll.setBackground(Color.WHITE);
		frontScroll.setBounds(0, 0, 720, 354);
		backScroll.setBounds(0, 0, 720, 607);
		// Å×ÀÌºí ÃÊ±âÈ­
		tableInit(frontTable);
		tableInit(backTable);
		// ÆË¾÷ ÃÊ±âÈ­
		popupInit(frontTablePanel, backTablePanel);
		// ÆÐ³Î¿¡ Ãß°¡

		frontTablePanel.add(frontScroll);
		backTablePanel.add(backScroll);
		today = viewManager.getDemandView().getDate().substring(5);
		frontTable.setValueAt(today,0,0);
		prevProductList.tableToData(frontTable, 0);
		valueChangedUpdate(frontTable);

	}

	private void showDatePopup(WhitePanel tablePanel, Point loc, boolean b) {
		if (popup == null) {
			if (b) {
				PopupFactory fac = new PopupFactory();
				datePanel.setVisible(true);
				Point xy = tablePanel.getLocationOnScreen();

				xy.y += 30;
				popup = fac.getPopup(tablePanel, datePanel, (int) (xy.getX() + loc.getX()),
						(int) ((xy.getY() + loc.getY())));
				popup.show();
			}
		} else {
			popup.hide();
			popup = null;
		}
	}

	private void initListManager() {
		ColumnManager column = new ColumnManager();
		String strArr[] = { "»óÈ£", "µî·Ï¹øÈ£", "´ã´çÀÚ" };
		int intArr[] = { 120, 40, 10 };
		column.setColumn(strArr, intArr);
		listManager = new ListManager<Product, ProductList>("°Å·¡Ã³ ºÒ·¯¿À±â", null, productList, column, false);
	}

	private void popupInit(JPanel frontPanel, JPanel backPanel) {
		MenuItem menuItem;
		PopupMenu frontPopup = new PopupMenu();
		PopupMenu backPopup = new PopupMenu();

		MenuItemActionListener frontMenu = new MenuItemActionListener(this, frontTable);
		MenuItemActionListener backMenu = new MenuItemActionListener(this, backTable);

		for (int i = 0; i < funcStr.length; i++) {
			menuItem = new MenuItem(funcStr[i]);
			menuItem.addActionListener(frontMenu);
			frontPopup.add(menuItem);
			menuItem = new MenuItem(funcStr[i]);
			menuItem.addActionListener(backMenu);
			backPopup.add(menuItem);
		}

		frontPanel.add(frontPopup);
		backPanel.add(backPopup);
		frontTable.addMouseListener(new MousePopupListener(frontPanel, frontTable, frontPopup));
		backTable.addMouseListener(new MousePopupListener(backPanel, backTable, backPopup));
		// setPopup(frontPanel, backPanel, frontPopup, backPopup);
	}

	private void sumTextInit() {
		setSumTextLabel(new JLabel("ÇÕ°è±Ý¾× "));
		setSumText(new JTextField(10));
		setSumTextBottom(new JTextField(10));
		setSumTaxBottom(new JTextField(10));
		setSumBlankField(new JTextField(10));
		setSumLabelField(new JTextField(10));

		getSumText().setEditable(false);
		getSumTextBottom().setEditable(false);
		getSumTaxBottom().setEditable(false);
		getSumLabelField().setEditable(false);
		getSumBlankField().setEditable(false);

		getSumTextLabel().setBounds(15, 260, 400, 25);
		getSumTextLabel().setFont(new Font(Main.font, Font.BOLD, 25));
		getSumText().setFont(new Font(Main.font, Font.BOLD, 25));
		getSumText().setHorizontalAlignment(JTextField.RIGHT);
		getSumText().setBackground(Main.YELLOW);
		getSumText().setBounds(132, 257, 220, 30);

		getSumBlankField().setBackground(Color.white);
		getSumBlankField().setBounds(50, 861 - 184, 719, 35);

		getSumLabelField().setText("ÇÕ°è");
		getSumLabelField().setFont(new Font(Main.font, Font.BOLD, Main.fontSize / 2 * 3));
		getSumLabelField().setHorizontalAlignment(JTextField.CENTER);
		getSumLabelField().setBackground(Color.white);
		getSumLabelField().setBounds(50, 861 - 184, 301, 35);

		getSumTextBottom().setFont(new Font(Main.font, Font.BOLD, Main.fontSize));
		getSumTextBottom().setHorizontalAlignment(JTextField.RIGHT);
		getSumTextBottom().setBackground(Main.YELLOW);
		getSumTextBottom().setBounds(639, 861 - 184, 87, 35);

		getSumTaxBottom().setFont(new Font(Main.font, Font.BOLD, Main.fontSize));
		getSumTaxBottom().setHorizontalAlignment(JTextField.RIGHT);
		getSumTaxBottom().setBackground(Color.white);
		getSumTaxBottom().setBounds(639, 861 - 184, 87, 35);
	}

	void tableUpdate(JTable table) {
		// null·Î Å×ÀÌºí ÃÊ±âÈ­
		for (int i = 0; i < table.getRowCount(); i++) {
			for (int j = 0; j < table.getColumnCount(); j++) {
				table.setValueAt(null, i, j);
			}
		}
		// Å×ÀÌºí¿¡ µ¥ÀÌÅÍ ÀÔ·Â
		productList.dataToTable(table, getIndex());
		valueChangedUpdate(table);
	}

	private int getIndex() {
		int index = 0;
		if (currPage != 1)
			index += Main.FrontRow;
		if (currPage > 2)
			index += (currPage - 2) * Main.BackRow;
		return index;
	}

	protected void valueChangedUpdate(JTable table) {
		// µ¥ÀÌÅÍ º¯°æ¿¡ µû¸¥ Å×ÀÌºí ¾÷µ¥ÀÌÆ®
		long mulData = 0;
		int max = table.getRowCount();
		selx = table.getSelectedColumn();
		sely = table.getSelectedRow();

		//DefaultCellEditor singleclick = new DefaultCellEditor(new JTextField());
	    //singleclick.setClickCountToStart(1);

	    //set the editor as default on every column
		/*
	    for (int i = 0; i < table.getColumnCount(); i++) {
	        table.setDefaultEditor(table.getColumnClass(i), singleclick);
	    } 
	    */
		// Àß¸øµÈ µ¥ÀÌÅÍ¸¦ °É·¯³»°í ´Ü°¡¿Í °ø±Þ°¡¾× ÀÔ·Â
		for (int i = 0; i < max; i++) {
			mulData = 0;
			for (int j = 0; j < 8; j++) {
				if (table.getValueAt(i, j) != null) {
					if (table.getValueAt(i, j).toString()
							.matches("")) {
						table.setValueAt(null, i, j);
					}
					if (j >= 4) {
						if (Main.stringToLongString(table.getValueAt(i, j)) == null)
							table.setValueAt(null, i, j);
						else {
							table.setValueAt(Main.longToMoneyString(Main.stringToLong(table.getValueAt(i, j))), i, j);
							if (j == 5)
								mulData = Main.stringToLong(table.getValueAt(i, j - 1))
										* Main.stringToLong(table.getValueAt(i, j));
						}
					}
				}
				if (j == 6) {
					if (mulData != 0)
						table.setValueAt(Main.longToMoneyString(mulData), i, 6);
					else
						table.setValueAt(null, i, 6);
				} else if (j == 7) {
					if (mulData != 0)
						table.setValueAt(Main.longToMoneyString(mulData / 10), i, 7);
					else
						table.setValueAt(null, i, 7);
				}
			}
		}
		// ÃÑ ±Ý¾× ¾÷µ¥ÀÌÆ®
		calcSumDataUpdate(table);
	}

	void calcSumDataUpdate(JTable table) {
		// µ¥ÀÌÅÍ ¸®½ºÆ®¿¡ Å×ÀÌºí µ¥ÀÌÅÍ ÀúÀå
		productList.tableToData(table, getIndex());
		// ÇÕ°è °è»ê
		calcData = productList.getSumMoney();
		if (calcData > 999999999999L) {
			getSumText().setText("NaN");
			getSumTextBottom().setText("NaN");
			getSumTaxBottom().setText("NaN");
			return;
		}
		// bottomTextField Á¶Àý
		int len = Main.longToMoneyString(calcData).length() > 10 ? (Main.longToMoneyString(calcData).length() - 10) * 10
				: 0;
		getSumText().setText(Main.longToMoneyString(calcData + calcData / 10) + "¿ø");
		getSumTextBottom().setText(Main.longToMoneyString(calcData));
		getSumTextBottom().setBounds(594 - len, 861 - 184, 95 + len, 35);
		getSumTaxBottom().setText(Main.longToMoneyString(calcData / 10));
		getSumTaxBottom().setBounds(594 + 95, 861 - 184, 80, 35);
	}

	void clipboardCopy(JTable table) { // º¹»ç
		DefClipboard.copy((String) table.getValueAt(table.getSelectedRow(), table.getSelectedColumn()));
	}

	void clipboardPaste(JTable table) { // ºÙ¿©³Ö±â
		if (table.getSelectedColumn() != 0 && table.getSelectedColumn() != 6 && table.getSelectedColumn() != 7) {
			try {
				productList.setData(DefClipboard.pasteData(), table.getSelectedRow() + getIndex(), table.getSelectedColumn());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private void tableInit(final MyJTable table) {
		// Å×ÀÌºí °¡·ÎÅ©±â
		for (int i = 0; i < 8; i++) {
			table.getColumn(columStr[i]).setPreferredWidth(Main.tableSize[i]);
		}
		// Å×ÀÌºí ¼¼·ÎÅ©±â
		table.setRowHeight(23);
		// ·»´õ¸µ°ú ÅØ½ºÆ® Á¤·Ä¹æ½Ä
		OneCellRenderer centerRenderer = new OneCellRenderer(Main.font, Main.fontSize);
		OneCellRenderer leftRenderer = new OneCellRenderer(Main.font, Main.fontSize);
		OneCellRenderer rightRenderer = new OneCellRenderer(Main.font, Main.fontSize);
		leftRenderer.setHorizontalAlignment(JLabel.LEFT);
		rightRenderer.setHorizontalAlignment(JLabel.RIGHT);
		centerRenderer.setHorizontalAlignment(JLabel.CENTER);

		table.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
		table.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
		table.getColumnModel().getColumn(2).setCellRenderer(leftRenderer);
		table.getColumnModel().getColumn(3).setCellRenderer(leftRenderer);
		table.getColumnModel().getColumn(4).setCellRenderer(centerRenderer);
		table.getColumnModel().getColumn(5).setCellRenderer(rightRenderer);
		table.getColumnModel().getColumn(6).setCellRenderer(rightRenderer);
		table.getColumnModel().getColumn(7).setCellRenderer(rightRenderer);

		table.getTableHeader().setReorderingAllowed(false); // Çì´õ Àç¹èÄ¡ ºÒ°¡
		table.setAutoResizeMode(JTable.AUTO_RESIZE_NEXT_COLUMN); // °¡·Î Å©±â Á¶Àý °¡´É
		table.setDragEnabled(false);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); // ÇÑ ¼¿¸¸ ¼±ÅÃ

		KeyStroke ks = KeyStroke.getKeyStroke("control C");
		table.getInputMap().put(ks, "dummy");
		table.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(ks, "testAction");
		table.getInputMap().put(KeyStroke.getKeyStroke("ENTER"), "selectNextColumnCell");
		table.getInputMap().put(KeyStroke.getKeyStroke("RIGHT"), "selectNextColumnCell");
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				
				
				if (table.getSelectedColumn() == 0) {
					currTable = table;
					dateRow = table.getSelectedRow();
					dateCol = table.getSelectedColumn();
					//if (!table(row,0)==null)
					//model.setDate(year, month, day);
					if (table.getRowCount() == Main.FrontRow)
						showDatePopup(frontTablePanel, e.getPoint(), true);
					else
						showDatePopup(backTablePanel, e.getPoint(), true);
				} else{
					/*
					showDatePopup(null, null, false);
					if (table.getSelectedColumn() < 6 && table.getValueAt(table.getSelectedRow(), 0)==null){
						today = viewManager.getDemandView().getDate().substring(5);
						table.setValueAt(today,table.getSelectedRow(),0);
					}
					*/
					
				}
			}
		});
		table.addKeyListener(new KeyListener() {
			// Å° ¼¼ÆÃ
			public void keyPressed(KeyEvent e) {
				if ((e.getModifiers() & KeyEvent.CTRL_MASK) != 0) {
					if (e.getKeyCode() == KeyEvent.VK_X) { // ctrl + x Àß¶ó³»±â
						clipboardCopy(table);
						table.setValueAt(null, table.getSelectedRow(), table.getSelectedColumn());
					}
					if (e.getKeyCode() == KeyEvent.VK_C) { // ctrl + c º¹»ç
						clipboardCopy(table);
					}
					if (e.getKeyCode() == KeyEvent.VK_V) { // ctrl + v ºÙ¿©³Ö±â
						clipboardPaste(table);
						tableUpdate(table);

					}
					// ctrl + shift function
					if ((e.getModifiers() & KeyEvent.SHIFT_MASK) != 0) {
						if (e.getKeyCode() == KeyEvent.VK_A) {
							productList.addRow(table.getSelectedRow() + getIndex());
						}
						if (e.getKeyCode() == KeyEvent.VK_D) {
							productList.removeRow(table.getSelectedRow() + getIndex());
						}
						if (e.getKeyCode() == KeyEvent.VK_X) {
							productList.copyRow(table.getSelectedRow() + getIndex());
							productList.removeRow(table.getSelectedRow() + getIndex());
						}
						if (e.getKeyCode() == KeyEvent.VK_C) {
							productList.copyRow(table.getSelectedRow() + getIndex());
						}
						if (e.getKeyCode() == KeyEvent.VK_V) {
							productList.pasteRow(table.getSelectedRow() + getIndex());
						}
						if (e.getKeyCode() == KeyEvent.VK_UP)
							productList.shiftUpRow(table.getSelectedRow() + getIndex());
						if (e.getKeyCode() == KeyEvent.VK_DOWN)
							productList.shiftDownRow(table.getSelectedRow() + getIndex());
						tableUpdate(table);
						pageRefresh();
					}
				}
				switch (e.getKeyCode()) {
				case KeyEvent.VK_DELETE:
					table.setValueAt("", table.getSelectedRow(), table.getSelectedColumn());
					break;
				case KeyEvent.VK_ENTER:
				case KeyEvent.VK_RIGHT:
				case KeyEvent.VK_TAB:
					/*
					if (prevText!=table.getValueAt(table.getSelectedRow(), table.getSelectedColumn()-1)){

						System.out.println(prevText+" "+table.getValueAt(table.getSelectedRow(), table.getSelectedColumn()-1));
						if (prevText==null ||table.getValueAt(table.getSelectedRow(), table.getSelectedColumn()-1)==null)
							Main.modify=true;
						else
							if (!prevText.equals(table.getValueAt(table.getSelectedRow(), table.getSelectedColumn()-1)))
								Main.modify=true;
					}
					*/
					if (table.getSelectedColumn() == 5) {
						if (table.getRowCount() > table.getSelectedRow() + 1) {
							table.setColumnSelectionInterval(1, 1);
							table.setRowSelectionInterval(table.getSelectedRow() + 1, table.getSelectedRow() + 1);
							if (table.getValueAt(table.getSelectedRow(), 0) == null ||table.getValueAt(table.getSelectedRow(), 0) == "") {
								table.setValueAt(table.getValueAt(table.getSelectedRow() - 1, 0), table.getSelectedRow(), 0);
							}
						}
					}
					break;

				case KeyEvent.VK_UP:
				case KeyEvent.VK_DOWN:
				case KeyEvent.VK_LEFT:
					break;
				default:
				}
				valueChangedUpdate(table);
			}

			public void keyTyped(KeyEvent e) {
				Character c = e.getKeyChar();
				// Çã¿ë ¹®ÀÚ
				if (c.toString().matches("[^a-zA-Z0-9¤¡-¤¾¤¿-¤Ó°¡-ÆR`~!@#$%^&*()-_=+|{};:',.<>?]+..")) {
					valueChangedUpdate(table);
					return;
				} else if (table.isSetCellEditable(table.getSelectedRow(), table.getSelectedColumn())) {
					if (!e.isControlDown()) {
						String s = (String) table.getValueAt(table.getSelectedRow(), table.getSelectedColumn());
						if (s == null || s == "\r\n" ){
							s = "";
						}
						if (c.toString().matches("[a-zA-Z0-9¤¡-¤¾¤¿-¤Ó°¡-ÆR`~!@#$%^&*()-_=+|{};:',.<>?]+"))
								table.setValueAt(s + c, table.getSelectedRow(), table.getSelectedColumn());
					}
				}
				valueChangedUpdate(table);
			}
			@Override
			public void keyReleased(KeyEvent e) {
				valueChangedUpdate(table);
			}
		});
		
	}

	// Å×ÀÌºí Å©±â Á¶Á¤ load data
	public void setTableWidth(String[] stn) {
		for (int i = 0; i < 8; i++) {
			frontTable.getColumn(columStr[i]).setPreferredWidth(Integer.parseInt(stn[i]));
		}
	}

	public void setTableWidth(int[] tableWidth) {
		for (int i = 0; i < 8; i++) {
			frontTable.getColumn(columStr[i]).setPreferredWidth(tableWidth[i]);
		}
	}

	public int[] getTableWidth() {
		int width[] = new int[8];
		for (int i = 0; i < 8; i++) {
			width[i] = frontTable.getColumn(columStr[i]).getPreferredWidth();
		}
		return width;
	}

	public JLabel getSumTextLabel() {
		return sumTextLabel;
	}

	public void setSumTextLabel(JLabel sumTextLabel) {
		this.sumTextLabel = sumTextLabel;
	}

	public JTextField getSumText() {
		return sumText;
	}

	public void setSumText(JTextField sumText) {
		this.sumText = sumText;
	}

	public JTextField getSumTextBottom() {
		return sumTextBottom;
	}

	public void setSumTextBottom(JTextField sumTextBottom) {
		this.sumTextBottom = sumTextBottom;
	}

	public JTextField getSumLabelField() {
		return sumLabelField;
	}

	public void setSumLabelField(JTextField sumLabelField) {
		this.sumLabelField = sumLabelField;
	}

	public JTextField getSumBlankField() {
		return sumBlankField;
	}

	public void setSumBlankField(JTextField sumBlankField) {
		this.sumBlankField = sumBlankField;
	}

	public JTextField getSumTaxBottom() {
		return sumTaxBottom;
	}

	public void setSumTaxBottom(JTextField sumTaxBottom) {
		this.sumTaxBottom = sumTaxBottom;
	}

	// ÇöÀç ÆäÀÌÁö
	public int getCurrPage() {
		return currPage;
	}

	// ÆäÀÌÁö ÀÌµ¿
	public void setCurrPage(int currPage) {
		this.currPage = currPage;
	}

	// ÃÖ´ë ÆäÀÌÁö
	public int getMaxPage() {
		return maxPage;
	}

	// ÃÖ´ë ÆäÀÌÁö ¼³Á¤
	public void setMaxPage(int maxPage) {
		this.maxPage = maxPage;
	}

	public WhitePanel getFrontTablePanel() {
		return frontTablePanel;
	}

	public WhitePanel getBackTablePanel() {
		return backTablePanel;
	}

	// »õ·Î°íÄ§
	public void refresh() {
		if (currPage == 1)
			tableUpdate(frontTable);
		else
			tableUpdate(backTable);
		pageRefresh();
	}

	public void pageRefresh() {
		if (productList.size() > Main.FrontRow + (maxPage - 1) * Main.BackRow+1) {
			maxPage++;
			viewManager.getFrameLabel().setPageText(getPageStr());
		} else if (productList.size() < Main.FrontRow + (maxPage - 1) * Main.BackRow) {
			maxPage--;
			viewManager.getFrameLabel().setPageText(getPageStr());
		}
	}

	public ProductList getProductList() {
		return productList;
	}

	public void removeLastPage() {
		if (currPage == maxPage && currPage > 1 && productList.isBlankLastPage()) {
			productList.removePage();
			maxPage--;
		}
	}

	public void addLastPage() {
		if (currPage == maxPage) {
			productList.addPage();
			maxPage++;
		}
	}

	public String getPageStr() {
		return "page" + currPage + "/" + maxPage;
	}

	public void setProductList(ProductList productList) {
		this.productList = productList;
		productList.dataToTable(frontTable, 0);
		maxPage = (productList.size() - Main.FrontRow) / Main.BackRow ;
		currPage = 1;
	}

	@Override
	public void loadDataId(String specId) {
		productList.loadList(specId);
		prevProductList.loadList(specId);
		setCurrPage(1);
		viewManager.swapPanel("front");
		refresh();
	}

	public void saveCurrData(String specId) {
		if (specId == null) {
			return;
		}
		productList.setSpecId(specId);
		for (int i = 0; i < productList.size(); i++)
			listManager.addItem(productList.get(i));

		prevProductList.loadList(specId);
	}

	public void removeList(String specId) {
		if (specId == null)
			return;
		productList.removeQuery(specId);
	}

	@Override
	public void loadData(ProductList o) {
		// TODO Auto-generated method stub

	}

	public boolean isModify() {
		/*
		if (currPage==1)
			valueChangedUpdate(frontTable);
		else
			valueChangedUpdate(backTable);
			*/
		return !prevProductList.equals(productList);
	}

}

class OneCellRenderer extends DefaultTableCellRenderer {
	String font;
	int size;

	OneCellRenderer(String font, int size) {
		this.font = font;
		this.size = size;
	}

	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
			int row, int column) {
		Component comp = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
		comp.setBackground(Color.white);
		comp.setFont(new Font(font, Font.BOLD, size));
		if (column == 6) { // °ø±Þ°¡¾×
			comp.setBackground(Main.YELLOW);
		}
		comp.setFont(new Font(font, Font.BOLD, size));
		return comp;
	}
}

class MousePopupListener extends MouseAdapter {
	JPanel panel;
	JTable table;
	PopupMenu popup;

	MousePopupListener(JPanel panel, JTable table, PopupMenu popup) {
		this.panel = panel;
		this.table = table;
		this.popup = popup;
	}

	public void mouseClicked(MouseEvent e) {
		int row = table.rowAtPoint(e.getPoint());
		int col = table.columnAtPoint(e.getPoint());
		table.setRowSelectionInterval(row, row);
		table.setColumnSelectionInterval(col, col);
		if (e.getButton() == MouseEvent.BUTTON3) {
			popup.show(panel, e.getX(), e.getY() + 29);
		}
	}
}


class MyJTable extends JTable {
	public MyJTable(Object[][] row, Object[] column) {
		super(row, column);
	}

	public boolean isSetCellEditable(int row, int column) {
		// ³¯Â¥ °ø±Þ°¡¾× ¼¼¾× ÆíÁý ºÒ°¡
		if (column == 0 || column == 6 || column == 7) {
			return false;
		}
		return true;
	}

	public boolean isCellEditable(int row, int column) {
		// ³¯Â¥ °ø±Þ°¡¾× ¼¼¾× ÆíÁý ºÒ°¡
		if (column == 0 || column == 6 || column == 7) {
			return false;
		}
		return true;
	}
}