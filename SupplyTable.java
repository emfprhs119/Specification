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

public class SupplyTable {
	static int FrontRow = 22; // 전면 리스트 행수
	static int BackRow = 33; // 후면 리스트 행수
	static int maxPage = 1; // 전체 페이지
	static int currPage = 1; // 현재 페이지

	TableList tableList; // 테이블 리스트
	String strList[][]; // 테이블 문자열

	MyJTable frontTable; // 전면 테이블
	MyJTable backTable; // 후면 테이블

	JTextField sumText, sumTextBottom; // 전면 합계,하단 합계
	JTextField sumLabelField, sumBlankField; // 하단 합계 레이블 필드와 빈칸 필드
	JLabel sumTextLabel; // 전면 합계금액 라벨

	int index = 0; // 페이지별 첫 행 위치
	int selx = 0, sely = 0; // 선택한 행렬
	int calcData = 0; // 계산된 합계금액
	String copyString = null; // 복사 붙여넣기를 위한 문자열

	// 팝업 메뉴 - 행 추가,제거

	SupplyTable(JPanel front, JPanel back) {

		tableList = new TableList(this);
		strList = tableList.init(); // string 배열 생성
		sumTextInit();

		Object frontRow[][] = new Object[FrontRow][8]; // 전면 테이블 행렬
		Object backRow[][] = new Object[BackRow][8]; // 후면 테이블 행렬
		Object column[] = { "품목", "규격", "자재비", "가공비", "수량", "단가", "공급가액", "비고" };

		frontTable = new MyJTable((Object[][]) frontRow, column);
		backTable = new MyJTable((Object[][]) backRow, column);
		// 헤더 폰트
		frontTable.getTableHeader().setFont(new Font(Main.font, 0, Main.fontSize));
		backTable.getTableHeader().setFont(new Font(Main.font, 0, Main.fontSize));

		// 스크롤팬 조정
		JScrollPane frontScroll = new JScrollPane(frontTable);
		frontScroll.setBackground(Color.WHITE);
		JScrollPane backScroll = new JScrollPane(backTable);
		backScroll.setBackground(Color.WHITE);
		frontScroll.setPreferredSize(new Dimension(720, 538));
		frontScroll.setBounds(0, 0, 720, 538);
		backScroll.setPreferredSize(new Dimension(720, 814));
		backScroll.setBounds(0, 0, 720, 814);
		// 테이블 초기화
		tableInit(frontTable);
		tableInit(backTable);
		// 팝업 초기화
		popupInit(front, back);
		// 패널에 추가
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
			if (((MenuItem) e.getSource()).getLabel() == "행 추가") {
				tableList.addColumn(table.getSelectedRow());
				tableUpdate(frontTable);
				tableUpdate(backTable);
			} else if (((MenuItem) e.getSource()).getLabel() == "행 제거") {
				tableList.removeColumn(table.getSelectedRow());
				tableUpdate(frontTable);
				tableUpdate(backTable);
			} else if (((MenuItem) e.getSource()).getLabel() == "복사") {
				copyString = (String) table.getValueAt(table.getSelectedRow(), table.getSelectedColumn());
			} else if (((MenuItem) e.getSource()).getLabel() == "잘라내기") {
				copyString = (String) table.getValueAt(table.getSelectedRow(), table.getSelectedColumn());
				table.setValueAt("", table.getSelectedRow(), table.getSelectedColumn());
				valueChangedUpdate(table);
			} else if (((MenuItem) e.getSource()).getLabel() == "붙여넣기") {
				if (table.getSelectedColumn() != 5 && table.getSelectedColumn() != 6)
					table.setValueAt(copyString, table.getSelectedRow(), table.getSelectedColumn());
			}
		}
	}

	private void popupInit(JPanel frontPanel, JPanel backPanel) {
		MenuItem menuItem;
		PopupMenu frontPopup = new PopupMenu();
		PopupMenu backPopup = new PopupMenu();

		MenuItemActionListener frontMenu = new MenuItemActionListener(frontTable);
		MenuItemActionListener backMenu = new MenuItemActionListener(backTable);

		menuItem = new MenuItem("행 추가");
		menuItem.addActionListener(frontMenu);
		frontPopup.add(menuItem);
		menuItem = new MenuItem("행 제거");
		menuItem.addActionListener(frontMenu);
		frontPopup.add(menuItem);
		menuItem = new MenuItem("복사");
		menuItem.addActionListener(frontMenu);
		frontPopup.add(menuItem);
		menuItem = new MenuItem("잘라내기");
		menuItem.addActionListener(frontMenu);
		frontPopup.add(menuItem);
		menuItem = new MenuItem("붙여넣기");
		menuItem.addActionListener(frontMenu);
		frontPopup.add(menuItem);
		menuItem = new MenuItem("행 추가");
		menuItem.addActionListener(backMenu);
		backPopup.add(menuItem);
		menuItem = new MenuItem("행 제거");
		menuItem.addActionListener(backMenu);
		backPopup.add(menuItem);
		menuItem = new MenuItem("복사");
		menuItem.addActionListener(backMenu);
		backPopup.add(menuItem);
		menuItem = new MenuItem("잘라내기");
		menuItem.addActionListener(backMenu);
		backPopup.add(menuItem);
		menuItem = new MenuItem("붙여넣기");
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
		sumTextLabel = new JLabel("합계금액 ");
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

		sumLabelField.setText("합계");
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
		tableList.isFull(index + table.getRowCount());
		//null로 테이블 초기화
		for (int i = 0; i < table.getRowCount(); i++) {
			for (int j = 0; j < table.getColumnCount(); j++) {
				table.setValueAt(null, i, j);
			}
		}
		//테이블에 데이터 입력
		for (int i = index; i < index + table.getRowCount(); i++) {
			for (int j = 0; j < 5; j++) {
				table.setValueAt(strList[i][j], i - index, j);
			}
			table.setValueAt(strList[i][5], i - index, 7);
		}
		valueChangedUpdate(table);
	}

	protected void valueChangedUpdate(JTable table) {
		//데이터 변경에 따른 테이블 업데이트
		int sumData = 0;
		int mulData = 0;
		int max = table.getRowCount();
		selx = table.getSelectedColumn();
		sely = table.getSelectedRow();

		// 잘못된 데이터를 걸러내고 단가와 공급가액 입력
		for (int i = 0; i < max; i++) {
			sumData = 0;
			mulData = 0;
			for (int j = 0; j < 8; j++) {
				if (table.getValueAt(i, j) != null) {
					if (!table.getValueAt(i, j).toString()
							.matches("[a-zA-Z0-9ㄱ-ㅎㅏ-ㅣ가-힣`~!@#$%^&*()-_=+|{};:',.<>/]+")) {
						table.setValueAt(null, i, j);
					}
					if (j >= 2 && j <= 4) {
						if (toStrFormat(table.getValueAt(i, j)) == null)
							table.setValueAt(null, i, j);
						else {
							table.setValueAt(toNumFormat(toIntFormat(table.getValueAt(i, j))), i, j);
							if (j <= 3)
								sumData += toIntFormat(table.getValueAt(i, j));
							else
								mulData = sumData * toIntFormat(table.getValueAt(i, j));
						}
					}
				}
				if (j == 5) {
					if (sumData != 0)
						table.setValueAt(toNumFormat(sumData), i, 5);
					else
						table.setValueAt(null, i, 5);
				} else if (j == 6) {
					if (mulData != 0)
						table.setValueAt(toNumFormat(mulData), i, 6);
					else
						table.setValueAt(null, i, 6);
				}
			}
		}
		//총 금액 업데이트
		calcSumDataUpdate();
	}

	void calcSumDataUpdate() {
		int sumData;
		int temp = calcData;
		// 데이터 리스트에 테이블 데이터 저장
		if (currPage == 1) {
			tableList.saveList(frontTable, 0, FrontRow);
		} else
			tableList.saveList(backTable, index, BackRow);

		// 합계 계산
		calcData = 0;
		for (int i = 0; i < tableList.maxSize; i++) {
			sumData = toIntFormat(strList[i][2]) + toIntFormat(strList[i][3]);
			calcData += sumData * toIntFormat(strList[i][4]);
		}

		// 테이블에 잘못된 데이터가 있을 경우 이전데이터 사용
		if (toNumFormat(calcData) == null) {
			sumText.setText(toNumFormat(temp) + "원");
			sumTextBottom.setText(toNumFormat(temp));
			sumTextBottom.setBounds(639, 861, 87, 35);
			calcData = temp;
		} else {
			// bottomTextField 조절
			int len = toNumFormat(temp).length() > 10 ? (toNumFormat(temp).length() - 10) * 10 : 0;
			sumText.setText(toNumFormat(calcData) + "원");
			sumTextBottom.setText(toNumFormat(calcData));
			sumTextBottom.setBounds(639 - len, 861, 87 + len, 35);
		}
	}

	final public static String toNumFormat(int num) {
		if (num == 0)
			return "0";
		DecimalFormat df = new DecimalFormat("#,###");
		return df.format(num);
	}

	final static public String toStrFormat(Object object) {
		if (object == null)
			return null;
		String num = (String) object;
		num = num.replaceAll("[^-0-9]", "");
		if (num.matches("^[0-9\\-]+")) {
			return num;
		} else {
			return null;
		}
	}

	final static public int toIntFormat(Object obj) {
		if (obj == null)
			return 0;
		String num = (String) obj;
		num = num.replaceAll("[^-0-9]", "");
		if (num.matches("^[0-9\\-]+")) {
			return Integer.parseInt(num);
		} else {
			return 0;
		}
	}

	private JTable tableInit(final MyJTable table) {
		// 테이블 가로크기
		table.getColumn("품목").setPreferredWidth(Main.tableSize[0]);
		table.getColumn("규격").setPreferredWidth(Main.tableSize[1]);
		table.getColumn("자재비").setPreferredWidth(Main.tableSize[2]);
		table.getColumn("가공비").setPreferredWidth(Main.tableSize[3]);
		table.getColumn("수량").setPreferredWidth(Main.tableSize[4]);
		table.getColumn("단가").setPreferredWidth(Main.tableSize[5]);
		table.getColumn("공급가액").setPreferredWidth(Main.tableSize[6]);
		table.getColumn("비고").setPreferredWidth(Main.tableSize[7]);
		// 테이블 세로크기
		table.setRowHeight(23);
		// 렌더링과 텍스트 정렬방식
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

		// table.setCellSelectionEnabled(true); //셀 선택 허용
		table.getTableHeader().setReorderingAllowed(false); // 헤더 재배치 불가
		table.setAutoResizeMode(JTable.AUTO_RESIZE_NEXT_COLUMN); // 가로 크기 조절 가능
		ListSelectionModel cellSelectionModel = table.getSelectionModel(); // 셀
																			// 모델
		table.getInputMap().put(KeyStroke.getKeyStroke("ENTER"), "selectNextColumnCell");
		table.getInputMap().put(KeyStroke.getKeyStroke("RIGHT"), "selectNextColumnCell");
		table.addKeyListener(new KeyListener() {
			void copy() { // 복사
				copyString = (String) table.getValueAt(table.getSelectedRow(), table.getSelectedColumn());
			}

			void paste() { // 붙여넣기
				if (table.getSelectedColumn() != 5 && table.getSelectedColumn() != 6)
					table.setValueAt(copyString, table.getSelectedRow(), table.getSelectedColumn());
			}

			// 키 세팅
			public void keyPressed(KeyEvent e) {
				if ((e.getModifiers() & KeyEvent.CTRL_MASK) != 0) {
					if (e.getKeyCode() == KeyEvent.VK_X) { // ctrl + x 잘라내기
						copy();
						table.setValueAt("", table.getSelectedRow(), table.getSelectedColumn());
					}
					if (e.getKeyCode() == KeyEvent.VK_C) // ctrl + c 복사
						copy();
					if (e.getKeyCode() == KeyEvent.VK_V) // ctrl + v 붙여넣기
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
				if (c.toString().matches("[^a-zA-Z0-9ㄱ-ㅎㅏ-ㅣ가-힣`~!@#$%^&*()-_=+|{};:',.<>/??]+")) {
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
		return table;
	}

	// 테이블 크기 조정 load data
	public void setTableSize(String[] stn) {
		int pos = 0;
		frontTable.getColumn("품목").setPreferredWidth(Integer.parseInt(stn[pos++]));
		frontTable.getColumn("규격").setPreferredWidth(Integer.parseInt(stn[pos++]));
		frontTable.getColumn("자재비").setPreferredWidth(Integer.parseInt(stn[pos++]));
		frontTable.getColumn("가공비").setPreferredWidth(Integer.parseInt(stn[pos++]));
		frontTable.getColumn("수량").setPreferredWidth(Integer.parseInt(stn[pos++]));
		frontTable.getColumn("단가").setPreferredWidth(Integer.parseInt(stn[pos++]));
		frontTable.getColumn("공급가액").setPreferredWidth(Integer.parseInt(stn[pos++]));
		frontTable.getColumn("비고").setPreferredWidth(Integer.parseInt(stn[pos++]));
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
		if (column == 6) { // 공급가액
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
		// 단가 공급가액 편집 불가
		if (column == 5 || column == 6) {
			return false;
		}
		return true;
	}

	public boolean isCellEditable(int row, int column) {
		// 단가 공급가액 편집 불가
		if (column == 5 || column == 6) {
			return false;
		}
		return true;
	}
}