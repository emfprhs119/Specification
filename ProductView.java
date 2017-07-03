import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.DecimalFormat;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;

public class ProductView {
	static int FrontRow = 22; // ���� ����Ʈ ���
	static int BackRow = 33; // �ĸ� ����Ʈ ���
	static int maxPage = 1; // ��ü ������
	static int currPage = 1; // ���� ������

	ProductList productList; // ��ǰ ����Ʈ

	MyJTable frontTable; // ���� ���̺�
	MyJTable backTable; // �ĸ� ���̺�

	JTextField sumText, sumTextBottom; // ���� �հ�,�ϴ� �հ�
	JTextField sumLabelField, sumBlankField; // �ϴ� �հ� ���̺� �ʵ�� ��ĭ �ʵ�
	JLabel sumTextLabel; // ���� �հ�ݾ� ��

	int index = 0; // �������� ù �� ��ġ
	int selx = 0, sely = 0; // ������ ���
	long calcData = 0; // ���� �հ�ݾ�
	String copyString = null; // ���� �ٿ��ֱ⸦ ���� ���ڿ�

	// �˾� �޴� - �� �߰�,����

	ProductView(ProductList productList, JPanel front, JPanel back) {
		sumTextInit();
		this.productList = productList;
		Object frontRow[][] = new Object[FrontRow][8]; // ���� ���̺� ���
		Object backRow[][] = new Object[BackRow][8]; // �ĸ� ���̺� ���
		Object column[] = { "ǰ��", "�԰�", "�����", "������", "����", "�ܰ�", "���ް���", "���" };

		frontTable = new MyJTable((Object[][]) frontRow, column);
		backTable = new MyJTable((Object[][]) backRow, column);
		// ��� ��Ʈ
		frontTable.getTableHeader().setFont(new Font(Main.font, 0, Main.fontSize));
		backTable.getTableHeader().setFont(new Font(Main.font, 0, Main.fontSize));

		// ��ũ���� ����
		JScrollPane frontScroll = new JScrollPane(frontTable);
		frontScroll.setBackground(Color.WHITE);
		JScrollPane backScroll = new JScrollPane(backTable);
		backScroll.setBackground(Color.WHITE);
		frontScroll.setPreferredSize(new Dimension(720, 538));
		frontScroll.setBounds(0, 0, 720, 538);
		backScroll.setPreferredSize(new Dimension(720, 814));
		backScroll.setBounds(0, 0, 720, 814);
		// ���̺� �ʱ�ȭ
		tableInit(frontTable);
		tableInit(backTable);
		// �˾� �ʱ�ȭ
		popupInit(front, back);
		// �гο� �߰�
		front.add(frontScroll);
		back.add(backScroll);

		valueChangedUpdate(frontTable);
	}

	class MenuItemActionListener implements ActionListener {
		JTable table;

		MenuItemActionListener(JTable table) {
			this.table = table;
		}

		public void actionPerformed(ActionEvent e) {
			if (((MenuItem) e.getSource()).getLabel() == "�� �߰� (shift+a") {
				//productList.addRow(table.getSelectedRow());
				tableUpdate(frontTable);
				tableUpdate(backTable);
			} else if (((MenuItem) e.getSource()).getLabel() == "�� ���� (shift+d)") {
				//productList.removeRow(table.getSelectedRow());
				tableUpdate(frontTable);
				tableUpdate(backTable);
			} else if (((MenuItem) e.getSource()).getLabel() == "�� ���� (shift+c)") {
			} else if (((MenuItem) e.getSource()).getLabel() == "�� �߶󳻱� (shift+x)") {
			} else if (((MenuItem) e.getSource()).getLabel() == "�� �ٿ��ֱ� (shift+v)") {
			} else if (((MenuItem) e.getSource()).getLabel() == "�� ���� (ctrl+c)") {
				copyString = (String) table.getValueAt(table.getSelectedRow(), table.getSelectedColumn());
			} else if (((MenuItem) e.getSource()).getLabel() == "�� �߶󳻱� (ctrl+x)") {
				copyString = (String) table.getValueAt(table.getSelectedRow(), table.getSelectedColumn());
				table.setValueAt("", table.getSelectedRow(), table.getSelectedColumn());
				valueChangedUpdate(table);
			} else if (((MenuItem) e.getSource()).getLabel() == "�� �ٿ��ֱ� (ctrl+v)") {
				if (table.getSelectedColumn() != 5 && table.getSelectedColumn() != 6) {
					table.setValueAt(copyString, table.getSelectedRow(), table.getSelectedColumn());
					valueChangedUpdate(table);
				}
			}
		}
	}

	private void popupInit(JPanel frontPanel, JPanel backPanel) {
		MenuItem menuItem;
		PopupMenu frontPopup = new PopupMenu();
		PopupMenu backPopup = new PopupMenu();

		MenuItemActionListener frontMenu = new MenuItemActionListener(frontTable);
		MenuItemActionListener backMenu = new MenuItemActionListener(backTable);

		menuItem = new MenuItem("�� �߰�");
		menuItem.addActionListener(frontMenu);
		frontPopup.add(menuItem);
		menuItem = new MenuItem("�� ����");
		menuItem.addActionListener(frontMenu);
		frontPopup.add(menuItem);
		menuItem = new MenuItem("����");
		menuItem.addActionListener(frontMenu);
		frontPopup.add(menuItem);
		menuItem = new MenuItem("�߶󳻱�");
		menuItem.addActionListener(frontMenu);
		frontPopup.add(menuItem);
		menuItem = new MenuItem("�ٿ��ֱ�");
		menuItem.addActionListener(frontMenu);
		frontPopup.add(menuItem);
		menuItem = new MenuItem("�� �߰�");
		menuItem.addActionListener(backMenu);
		backPopup.add(menuItem);
		menuItem = new MenuItem("�� ����");
		menuItem.addActionListener(backMenu);
		backPopup.add(menuItem);
		menuItem = new MenuItem("����");
		menuItem.addActionListener(backMenu);
		backPopup.add(menuItem);
		menuItem = new MenuItem("�߶󳻱�");
		menuItem.addActionListener(backMenu);
		backPopup.add(menuItem);
		menuItem = new MenuItem("�ٿ��ֱ�");
		menuItem.addActionListener(backMenu);
		backPopup.add(menuItem);

		setPopup(frontPanel, backPanel, frontPopup, backPopup);
	}

	private void setPopup(JPanel frontPanel, JPanel backPanel, PopupMenu frontPopup, PopupMenu backPopup) {
		frontPanel.add(frontPopup);
		backPanel.add(backPopup);
		frontTable.addMouseListener(new MouseLis(frontPanel, frontTable, frontPopup));
		backTable.addMouseListener(new MouseLis(backPanel, backTable, backPopup));
	}

	private void sumTextInit() {
		sumTextLabel = new JLabel("�հ�ݾ� ");
		sumText = new JTextField(10);
		sumTextBottom = new JTextField(10);
		sumBlankField = new JTextField(10);
		sumLabelField = new JTextField(10);

		sumText.setEditable(false);
		sumTextBottom.setEditable(false);
		sumLabelField.setEditable(false);
		sumBlankField.setEditable(false);

		sumTextLabel.setBounds(15, 260, 400, 25);
		sumTextLabel.setFont(new Font(Main.font, Font.BOLD, 25));
		sumText.setFont(new Font(Main.font, Font.BOLD, 25));
		sumText.setHorizontalAlignment(JTextField.RIGHT);
		sumText.setBackground(Main.color);
		sumText.setBounds(132, 257, 220, 30);

		sumBlankField.setBackground(Color.white);
		sumBlankField.setBounds(50, 861, 719, 35);

		sumLabelField.setText("�հ�");
		sumLabelField.setFont(new Font(Main.font, Font.BOLD, Main.fontSize / 2 * 3));
		sumLabelField.setHorizontalAlignment(JTextField.CENTER);
		sumLabelField.setBackground(Color.white);
		sumLabelField.setBounds(50, 861, 301, 35);

		sumTextBottom.setFont(new Font(Main.font, Font.BOLD, Main.fontSize));
		sumTextBottom.setHorizontalAlignment(JTextField.RIGHT);
		sumTextBottom.setBackground(Main.color);
		sumTextBottom.setBounds(639, 861, 87, 35);
	}

	void tableUpdate(JTable table) {
		productList.isFull(index + table.getRowCount());
		// null�� ���̺� �ʱ�ȭ
		for (int i = 0; i < table.getRowCount(); i++) {
			for (int j = 0; j < table.getColumnCount(); j++) {
				table.setValueAt(null, i, j);
			}
		}
		// ���̺� ������ �Է�
		productList.dataToTable(table, index);
		valueChangedUpdate(table);
	}

	protected void valueChangedUpdate(JTable table) {
		// ������ ���濡 ���� ���̺� ������Ʈ
		long sumData = 0;
		long mulData = 0;
		int max = table.getRowCount();
		selx = table.getSelectedColumn();
		sely = table.getSelectedRow();

		// �߸��� �����͸� �ɷ����� �ܰ��� ���ް��� �Է�
		for (int i = 0; i < max; i++) {
			sumData = 0;
			mulData = 0;
			for (int j = 0; j < 8; j++) {
				if (table.getValueAt(i, j) != null) {
					if (!table.getValueAt(i, j).toString()
							.matches("[a-zA-Z0-9��-����-�Ӱ�-�R`~!@#$%^&*()-_=+|{};:',.<>/]+")) {
						table.setValueAt(null, i, j);
					}
					if (j >= 2 && j <= 4) {
						if (Main.toStrFormat(table.getValueAt(i, j)) == null)
							table.setValueAt(null, i, j);
						else {
							table.setValueAt(Main.toNumFormat(Main.toLongFormat(table.getValueAt(i, j))), i, j);
							if (j <= 3)
								sumData += Main.toLongFormat(table.getValueAt(i, j));
							else
								mulData = sumData * Main.toLongFormat(table.getValueAt(i, j));
						}
					}
				}
				if (j == 5) {
					if (sumData != 0)
						table.setValueAt(Main.toNumFormat(sumData), i, 5);
					else
						table.setValueAt(null, i, 5);
				} else if (j == 6) {
					if (mulData != 0)
						table.setValueAt(Main.toNumFormat(mulData), i, 6);
					else
						table.setValueAt(null, i, 6);
				}
			}
		}
		// �� �ݾ� ������Ʈ
		calcSumDataUpdate();
	}

	void calcSumDataUpdate() {
		// ������ ����Ʈ�� ���̺� ������ ����
		if (currPage == 1) {
			productList.tableToData(frontTable, index);
		} else
			productList.tableToData(backTable, index);
		// �հ� ���
		calcData = productList.getSumMoney();
		if (calcData>999999999999L){
			sumText.setText("NaN");
			sumTextBottom.setText("NaN");
			return;
		}
		// bottomTextField ����
		int len = Main.toNumFormat(calcData).length() > 10 ? (Main.toNumFormat(calcData).length() - 10) * 10 : 0;
		sumText.setText(Main.toNumFormat(calcData) + "��");
		sumTextBottom.setText(Main.toNumFormat(calcData));
		sumTextBottom.setBounds(639 - len, 861, 87 + len, 35);
	}

	

	private void tableInit(final MyJTable table) {
		// ���̺� ����ũ��
		table.getColumn("ǰ��").setPreferredWidth(Main.tableSize[0]);
		table.getColumn("�԰�").setPreferredWidth(Main.tableSize[1]);
		table.getColumn("�����").setPreferredWidth(Main.tableSize[2]);
		table.getColumn("������").setPreferredWidth(Main.tableSize[3]);
		table.getColumn("����").setPreferredWidth(Main.tableSize[4]);
		table.getColumn("�ܰ�").setPreferredWidth(Main.tableSize[5]);
		table.getColumn("���ް���").setPreferredWidth(Main.tableSize[6]);
		table.getColumn("���").setPreferredWidth(Main.tableSize[7]);
		// ���̺� ����ũ��
		table.setRowHeight(23);
		// �������� �ؽ�Ʈ ���Ĺ��
		OneCellRenderer centerRenderer = new OneCellRenderer(Main.font, Main.fontSize);
		OneCellRenderer leftRenderer = new OneCellRenderer(Main.font, Main.fontSize);
		OneCellRenderer rightRenderer = new OneCellRenderer(Main.font, Main.fontSize);
		leftRenderer.setHorizontalAlignment(JLabel.LEFT);
		rightRenderer.setHorizontalAlignment(JLabel.RIGHT);
		centerRenderer.setHorizontalAlignment(JLabel.CENTER);

		table.getColumnModel().getColumn(0).setCellRenderer(leftRenderer);
		table.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
		table.getColumnModel().getColumn(4).setCellRenderer(centerRenderer);
		table.getColumnModel().getColumn(2).setCellRenderer(rightRenderer);
		table.getColumnModel().getColumn(3).setCellRenderer(rightRenderer);
		table.getColumnModel().getColumn(5).setCellRenderer(rightRenderer);
		table.getColumnModel().getColumn(6).setCellRenderer(rightRenderer);
		table.getColumnModel().getColumn(7).setCellRenderer(rightRenderer);

		// table.setCellSelectionEnabled(true); //�� ���� ���
		table.getTableHeader().setReorderingAllowed(false); // ��� ���ġ �Ұ�
		table.setAutoResizeMode(JTable.AUTO_RESIZE_NEXT_COLUMN); // ���� ũ�� ���� ����
		ListSelectionModel cellSelectionModel = table.getSelectionModel(); // ��
																			// ��
		table.getInputMap().put(KeyStroke.getKeyStroke("ENTER"), "selectNextColumnCell");
		table.getInputMap().put(KeyStroke.getKeyStroke("RIGHT"), "selectNextColumnCell");
		table.addKeyListener(new KeyListener() {
			void copy() { // ����
				copyString = (String) table.getValueAt(table.getSelectedRow(), table.getSelectedColumn());
			}

			void paste() { // �ٿ��ֱ�
				if (table.getSelectedColumn() != 5 && table.getSelectedColumn() != 6)
					table.setValueAt(copyString, table.getSelectedRow(), table.getSelectedColumn());
			}
			// Ű ����
			public void keyPressed(KeyEvent e) {
				if ((e.getModifiers() & KeyEvent.CTRL_MASK) != 0) {
					if (e.getKeyCode() == KeyEvent.VK_X) { // ctrl + x �߶󳻱�
						copy();
						table.setValueAt("", table.getSelectedRow(), table.getSelectedColumn());
					}
					if (e.getKeyCode() == KeyEvent.VK_C) // ctrl + c ����
						copy();
					if (e.getKeyCode() == KeyEvent.VK_V) // ctrl + v �ٿ��ֱ�
						paste();
				}
				switch (e.getKeyCode()) {
				case KeyEvent.VK_DELETE:
					table.setValueAt("", table.getSelectedRow(), table.getSelectedColumn());
					break;
				case KeyEvent.VK_ENTER:
					if (table.getSelectedColumn() == 4) {
						table.setColumnSelectionInterval(7, 7);
						table.setRowSelectionInterval(table.getSelectedRow(), table.getSelectedRow());
					}
					break;
				case KeyEvent.VK_RIGHT:
					if (table.getSelectedColumn() == 4) {
						table.setColumnSelectionInterval(7, 7);
						table.setRowSelectionInterval(table.getSelectedRow(), table.getSelectedRow());
					}
				
				case KeyEvent.VK_UP:
				case KeyEvent.VK_DOWN:
				case KeyEvent.VK_LEFT:
					break;
				default:
				}
				valueChangedUpdate(table);
			}

			public void keyTyped(KeyEvent e) {
				Character c;
				c = e.getKeyChar();
				//��� ����
				if (c.toString().matches("[^a-zA-Z0-9��-����-�Ӱ�-�R`~!@#$%^&*()-_=+|{};:',.<>?]+")) {
					valueChangedUpdate(table);
					return;
				}
				else if (table.isSetCellEditable(table.getSelectedRow(), table.getSelectedColumn())) {
					keyPressed(e);
					if (!e.isControlDown()) {
						
						String s = (String) table.getValueAt(table.getSelectedRow(), table.getSelectedColumn());
						if (table.getSelectedColumn() == 5) {
							table.setColumnSelectionInterval(0, 0);
							table.setRowSelectionInterval(table.getSelectedRow() + 1, table.getSelectedRow() + 1);
						}
						if (table.getSelectedColumn() == 5 && (table.getSelectedRow() == table.getRowCount() - 1)) {
							table.setColumnSelectionInterval(0, 0);
							table.setRowSelectionInterval(table.getSelectedRow(), table.getSelectedRow());
						}
						if (s == null || s == "\r\n")
							s = "";
						table.setValueAt(s + e.getKeyChar(), table.getSelectedRow(), table.getSelectedColumn());
						Main.modify = true;
						
					}
				}
				valueChangedUpdate(table);
			}

			@Override
			public void keyReleased(KeyEvent e) {
			}
		});

		cellSelectionModel.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				valueChangedUpdate(table);
			}
		});
	}

	// ���̺� ũ�� ���� load data
	public void setTableSize(String[] stn) {
		int pos = 0;
		frontTable.getColumn("ǰ��").setPreferredWidth(Integer.parseInt(stn[pos++]));
		frontTable.getColumn("�԰�").setPreferredWidth(Integer.parseInt(stn[pos++]));
		frontTable.getColumn("�����").setPreferredWidth(Integer.parseInt(stn[pos++]));
		frontTable.getColumn("������").setPreferredWidth(Integer.parseInt(stn[pos++]));
		frontTable.getColumn("����").setPreferredWidth(Integer.parseInt(stn[pos++]));
		frontTable.getColumn("�ܰ�").setPreferredWidth(Integer.parseInt(stn[pos++]));
		frontTable.getColumn("���ް���").setPreferredWidth(Integer.parseInt(stn[pos++]));
		frontTable.getColumn("���").setPreferredWidth(Integer.parseInt(stn[pos++]));
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
		if (column == 6) { // ���ް���
			comp.setBackground(Main.color);
		}
		comp.setFont(new Font(font, Font.BOLD, size));
		return comp;
	}
}

class MouseLis extends MouseAdapter {
	JPanel panel;
	JTable table;
	PopupMenu popup;

	MouseLis(JPanel panel, JTable table, PopupMenu popup) {
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
			popup.show(panel, e.getX(), e.getY());// 13,41
		}
	}
}

class MyJTable extends JTable {
	public MyJTable(Object[][] row, Object[] column) {
		super(row, column);
	}

	public boolean isSetCellEditable(int row, int column) {
		// �ܰ� ���ް��� ���� �Ұ�
		if (column == 5 || column == 6) {
			return false;
		}
		return true;
	}

	public boolean isCellEditable(int row, int column) {
		// �ܰ� ���ް��� ���� �Ұ�
		if (column == 5 || column == 6) {
			return false;
		}
		return true;
	}
}