package Inheritance;

import java.awt.Button;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;
import Main.Main;

public class ListManager<T extends Model_Interface,LIST extends List_Interface<T>> extends JFrame {
	View_Interface<T> view_Interface;
	JTable table;
	JPanel panel;
	JTextField searchField;
	JFrame addFrame;
	LIST list;
	String[] column;
	int[] columnWidth;
	boolean autoVisible;
	public ListManager(String managerName,View_Interface<T> view_Interface,LIST list,ColumnManager columnManager,boolean autoVisible) {
		super(managerName);
		this.view_Interface = view_Interface;
		this.column=columnManager.getColumn();
		this.columnWidth=columnManager.getColumnWidth();
		this.list= list;
		this.autoVisible=autoVisible;
		autoVisible=true;
		panel = new JPanel();
		searchField = new JTextField(14);
		Button searchButton = new Button("검색");
		Button resetButton = new Button("초기화");
		setBounds(200, 300, 380, 600);
		setLayout(null);
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setResizable(false);
		buttonInit();
		tableInit();
		searchField.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					tableUpdate();
				}
			}
		});
		searchButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tableUpdate();
			}
		});
		resetButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				searchField.setText("");
				tableUpdate();
			}
		});

		searchField.setBounds(12, 10, 150, 20);
		searchButton.setBounds(167, 10, 55, 20);
		resetButton.setBounds(232, 10, 55, 20);

		add(searchField);
		add(searchButton);
		add(resetButton);
		add(panel);
	}
	public void setAddFrame(JFrame addFrame){
		this.addFrame= addFrame;
	}

	public void buttonInit() {
		Button button[] = new Button[3];
		button[0] = new Button("추가");
		button[0].setBounds(75, 495, 80, 40);
		button[0].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				addFrame.setVisible(true);
			}
		});

		button[1] = new Button("제거");
		button[1].setBounds(170, 495, 80, 40);
		button[1].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int index = table.getSelectedRow();
				if (index == -1)
					return;
				list.removeQuery(index);// 제거
				tableUpdate();
			}
		});
		button[2] = new Button("불러오기");
		button[2].setBounds(265, 495, 80, 40);
		button[2].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int index = table.getSelectedRow();
				if (table == null) {
					JOptionPane.showMessageDialog(null, "불러올 수 없습니다.");
					return;
				}
				if (index == -1){
					JOptionPane.showMessageDialog(null, "선택 후 불러와 주세요.");
					return;
				}
				view_Interface.loadData(list.get(index));
				if (autoVisible)
					setVisible(false);
			}
		});
		if (autoVisible)
			add(button[0]);
		add(button[1]);
		add(button[2]);
	}

	void tableInit() {
		String [][]row = new String[0][column.length];
		DefaultTableModel model = new DefaultTableModel(row, column) {
			public boolean isCellEditable(int row, int col) {
				return false;
			}
		};
		table = new JTable(model);
		
		table.getTableHeader().setReorderingAllowed(false);

		table.setRowHeight(18);
		table.getTableHeader().setFont(new Font(Main.font, 0, 12));

		table.setDragEnabled(false);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); // 한 셀만 선택
		//불러오기
		table.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent event) {
				int index = table.getSelectedRow();
				if (event.getClickCount() == 2) {
					if (table == null) {
						JOptionPane.showMessageDialog(null, "불러올 수 없습니다.");
						return;
					}
					if (index == -1){
						JOptionPane.showMessageDialog(null, "선택 후 불러와 주세요.");
						return;
					}
					view_Interface.loadData(list.get(index));
					if (autoVisible)
						setVisible(false);
				}
			}
		});
		JScrollPane scroll = new JScrollPane(table);
		scroll.setPreferredSize(new Dimension(350, 440));
		panel.setBounds(7, 40, 350, 440);
		panel.add(scroll);
	}

	public void setVisible(boolean b) {
		if (b){
			searchField.setText("");
			tableUpdate();
		}
		super.setVisible(b);
	}

	public void tableUpdate() {
		list.loadList(searchField.getText());
		String []strArr;
		tableSet(list.size());
		for (int i = 0; i < list.size(); i++) {
			strArr=list.get(i).getLoadArr();
			for(int j=0;j<strArr.length;j++){
				table.setValueAt(strArr[j], i, j);
			}
		}
	}

	public void tableSet(int n) {
		String [][]row = new String[n][3];
		DefaultTableModel model = new DefaultTableModel((String[][]) row, column) {
			public boolean isCellEditable(int row, int col) {
				return false;
			}
		};
		table.setModel(model);
		for(int i=0;i<column.length;i++)
			table.getColumn(column[i]).setPreferredWidth(columnWidth[i]);
	}

	public boolean addItem(T item){
		return list.addQuery(item);
		
	}
	public void setAutoVisible(boolean b) {
		autoVisible=false;
	}
	public boolean isHas(T item) {
		return list.isHasQuery(item);
	}
	public T getObject(String id) {
		return list.loadObject(id);
	}
}
