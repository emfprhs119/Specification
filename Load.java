import java.awt.Button;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

public class Load extends JFrame {
	JTable table;
	JPanel panel;
	
	SupTable supTable;
	Demand demand;
	JLabel file;
	JLabel page;
	//MainFrame mainFrame;
	ManageList manageList;
	Object row[][];

	JLabel searchLabel;

	JTextField searchCompField;
	JLabel searchCompLabel;
	JTextField searchItemField;
	JLabel searchItemLabel;
	ActionLis actionLis;
	JButton searchButton;

	UtilDateModel model = new UtilDateModel();
	JDatePanelImpl datePanel = new JDatePanelImpl(model, new Properties());
	JDatePickerImpl datePicker = new JDatePickerImpl(datePanel, new DateFormatter());
	UtilDateModel model2 = new UtilDateModel();
	JDatePanelImpl datePanel2 = new JDatePanelImpl(model2, new Properties());
	JDatePickerImpl datePicker2 = new JDatePickerImpl(datePanel2, new DateFormatter());

	Load(SupTable subTable,Demand demand,JLabel file,JLabel page) {
		this.supTable=subTable;
		this.demand=demand;
		this.file=file;
		this.page=page;
		
		manageList = new ManageList();
		actionLis = new ActionLis(manageList, this);
		//this.mainFrame = mainFrame;
		panel = new JPanel();
		setBounds(200+838, 200, 350, 700);
		setLayout(null);
		setDefaultCloseOperation(this.HIDE_ON_CLOSE);
		searchInit();
		buttonInit();

		add(panel);
	}

	void tableInit() {
		/*
		if (manageList.getMatchCount() == 0)
			return;
		*/
		tableSet(manageList.getMatchCount());
		for (int i = 0; i < manageList.mattop; i++) {
			table.setValueAt(manageList.saveListMatch[i].date, i, 0);
			table.setValueAt(manageList.saveListMatch[i].name, i, 1);
			table.setValueAt(manageList.saveListMatch[i].no, i, 2);
		}
	}

	public void tableSet(int n) {
		row = new Object[n][3];
		Object column[] = { "������", "��ȣ", "No." };
		//table = new JTable((Object[][]) row, column);
		
		DefaultTableModel model = new DefaultTableModel((Object[][]) row, column){
	    public boolean isCellEditable(int row, int col) {
	     return false;
	    }};
	 
	    table = new JTable(model);
	    table.getTableHeader().setReorderingAllowed(false);
		table.getColumn("������").setPreferredWidth(80);
		table.getColumn("��ȣ").setPreferredWidth(180);
		table.getColumn("No.").setPreferredWidth(1);
		table.setRowHeight(18);
		table.getTableHeader().setFont(new Font(Main.font, 0, 12));
		
		table.setFocusable(false);
		table.setAutoCreateRowSorter(true);
		TableRowSorter sorter = new TableRowSorter(table.getModel());
		table.setRowSorter(sorter);
		table.addMouseListener(new MouseAdapter() {
			int sel = -1;

			public void mouseReleased(MouseEvent arg0) {
				if (sel == table.getSelectedRow()) {
					if (sel == -1)
						return;
					loadFile(new String("������_" + manageList.saveListMatch[sel].name + "_" + manageList.saveListMatch[sel].date + "_"
							+ manageList.saveListMatch[sel].no + ".save"));
					file.setText(new String("������_" + manageList.saveListMatch[sel].name + "_"
							+ manageList.saveListMatch[sel].date + "_" + manageList.saveListMatch[sel].no));
					file.setBounds(419 - file.getText().length() * 5, 5, 300, 20);
					//setVisible(false);
					Main.modify = false;
					page.setText(new String("page" + supTable.curPage + "/" + supTable.flag));
					repaint();
					sel = -1;
				}
				sel = table.getSelectedRow();
			}
		});

		JScrollPane scroll = new JScrollPane(table);
		scroll.setPreferredSize(new Dimension(330, 500));
		panel.setBounds(3, 100, 330, 800);
		panel.removeAll();
		panel.add(scroll);
		setVisible(true);
	}

	private void searchInit() {
		int x = 140;
		int y = 15;
		datePicker.setBounds(3, 25, 133, 30);
		datePicker2.setBounds(3, 70, 133, 30);
		add(datePicker);
		add(datePicker2);
		searchLabel = new JLabel("�Ⱓ");
		searchLabel.setBounds(40, 0, 40, 30);
		add(searchLabel);
		searchButton = new JButton("reset");
		searchButton.setFont(new Font(Main.font, 0, 10));
		searchButton.setBounds(90, 5, 30, 18);
		add(searchButton);
		searchButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				model.setValue(null);
				model2.setValue(null);
				manageList.setDate(model.getValue(), model2.getValue());
				tableInit();
			}
		});

		searchLabel = new JLabel("~");
		searchLabel.setBounds(50, 50, 20, 20);
		add(searchLabel);
		datePicker.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				manageList.setDate(model.getValue(), model2.getValue());
				tableInit();
			}
		});
		datePicker2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				manageList.setDate(model.getValue(), model2.getValue());
				tableInit();
			}
		});
		searchCompField = new JTextField(14);
		searchItemField = new JTextField(14);

		searchCompLabel = new JLabel("��ȣ");
		searchItemLabel = new JLabel("��ǰ");

		searchButton = new JButton("�˻�");
		searchButton.setFont(new Font(Main.font, 0, 10));

		actionLis.searchCompField = searchCompField;
		actionLis.searchItemField = searchItemField;

		searchCompField.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					actionLis.search();
				}
			}
		});
		searchItemField.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					actionLis.search();
				}
			}
		});
		searchButton.addActionListener(actionLis);

		searchCompLabel.setBounds(x + 8, y + 5, 30, 20);
		searchItemLabel.setBounds(x + 8, y + 25, 30, 20);
		searchCompField.setBounds(x + 40, y + 5, 150, 20);
		searchItemField.setBounds(x + 40, y + 25, 150, 20);

		searchButton.setBounds(x + 129, y + 50, 60, 25);
		add(searchLabel);
		add(searchCompLabel);
		add(searchItemLabel);
		add(searchCompField);
		add(searchItemField);
		add(searchButton);
	}

	public void buttonInit() {
		Button button[] = new Button[2];
		button[0] = new Button("����");
		button[0].setBounds(115, 610, 100, 40);
		button[0].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int sel = table.getSelectedRow();
				if (sel == -1)
					return;
				removeList(new String("������_" + manageList.saveListMatch[sel].name + "_" + manageList.saveListMatch[sel].date + "_"
						+ manageList.saveListMatch[sel].no + ".save"));
			}
		});
		button[1] = new Button("�ҷ�����");
		button[1].setBounds(225, 610, 100, 40);
		button[1].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int sel = table.getSelectedRow();
				if (sel == -1)
					return;
				loadFile(new String("������_" + manageList.saveListMatch[sel].name + "_" + manageList.saveListMatch[sel].date + "_"
						+ manageList.saveListMatch[sel].no + ".save"));
				file.setText(new String("������_" + manageList.saveListMatch[sel].name + "_" + manageList.saveListMatch[sel].date
						+ "_" + manageList.saveListMatch[sel].no));
				file.setBounds(419 - file.getText().length() * 5, 5, 300, 20);
				//setVisible(false);
				page.setText(new String("page" + supTable.curPage + "/" + supTable.flag));
				repaint();
			}
		});
		add(button[0]);
		add(button[1]);
	}

	public void loadList() {
		File dirFile = new File("save/");
		File[] fileList = dirFile.listFiles();
		manageList = new ManageList();
		actionLis.manageList = manageList;
		for (File tempFile : fileList) {
			if (tempFile.isFile()) {
				String tmpName = tempFile.getName();
				String stn[] = tmpName.split("_");
				if (stn.length < 4)
					continue;
				if (!stn[0].equals("������"))
					continue;
				if (stn[2].matches(".*\\..*"))
					continue;
				String date = stn[2].replace(".", "-");
				String name = stn[1];
				String no = stn[3].replaceAll(".save", "");

				manageList.addList("save/" + tmpName, date, name, no);
			}
		}
	}

	/*
	 * void tableInit() { tableSet(manageList.top); for (int i = 0; i < manageList.top; i++) {
	 * table.setValueAt(manageList.saveList[i].date, i, 0);
	 * table.setValueAt(manageList.saveList[i].name, i, 1);
	 * table.setValueAt(manageList.saveList[i].no, i, 2); } }
	 */
	public void loadFile(String file) {
		Est est = null;
		DemandF demandF = new DemandF();
		BufferedReader fr = null;
		String list[][] = supTable.listC.list;
		String st;
		int i = 0, top = 0;
		try {
			fr = new BufferedReader(new FileReader("save/" + file));
			st = fr.readLine();
			st = st.replace("/", "!@#$/");
			st = st.replace(" ", "");
			String stn[] = st.split("\\$\\/");
			demandF.date = stn[0].replaceAll("!@#", "");
			demandF.name = stn[1].replaceAll("!@#", "");
			demandF.tel = stn[2].replaceAll("!@#", "");
			demandF.who = stn[3].replaceAll("!@#", "");
			est = new Est(demandF);
			est.flag = Integer.parseInt(stn[4].replaceAll("!@#", ""));
			if (stn.length > 5) {
				int pos = 5;
				supTable.table.getColumn("ǰ��").setPreferredWidth(Integer.parseInt(stn[pos++].replaceAll("!@#", "")));
				supTable.table.getColumn("�԰�").setPreferredWidth(Integer.parseInt(stn[pos++].replaceAll("!@#", "")));
				supTable.table.getColumn("�����").setPreferredWidth(Integer.parseInt(stn[pos++].replaceAll("!@#", "")));
				supTable.table.getColumn("������").setPreferredWidth(Integer.parseInt(stn[pos++].replaceAll("!@#", "")));
				supTable.table.getColumn("����").setPreferredWidth(Integer.parseInt(stn[pos++].replaceAll("!@#", "")));
				supTable.table.getColumn("�ܰ�").setPreferredWidth(Integer.parseInt(stn[pos++].replaceAll("!@#", "")));
				supTable.table.getColumn("���ް���").setPreferredWidth(Integer.parseInt(stn[pos++].replaceAll("!@#", "")));
				supTable.table.getColumn("���").setPreferredWidth(Integer.parseInt(stn[pos++].replaceAll("!@#", "")));
			} else {
				supTable.table.getColumn("ǰ��").setPreferredWidth(Main.tableSize[0]);
				supTable.table.getColumn("�԰�").setPreferredWidth(Main.tableSize[1]);
				supTable.table.getColumn("�����").setPreferredWidth(Main.tableSize[2]);
				supTable.table.getColumn("������").setPreferredWidth(Main.tableSize[3]);
				supTable.table.getColumn("����").setPreferredWidth(Main.tableSize[4]);
				supTable.table.getColumn("�ܰ�").setPreferredWidth(Main.tableSize[5]);
				supTable.table.getColumn("���ް���").setPreferredWidth(Main.tableSize[6]);
				supTable.table.getColumn("���").setPreferredWidth(Main.tableSize[7]);
			}
			String tmp = "";
			while (null != (st = fr.readLine())) {
				st = st.replace("/", "!@#$/");
				st = st.replace(" ", "");
				stn = st.split("\\$\\/");
				if (stn.length != 6) {
					tmp = tmp + st;
					stn = tmp.split("\\$\\/");
					if (stn.length != 6)
						continue;
				}
				if (i == list.length) {
					supTable.listC.resize();
					list = supTable.listC.list;
				}
				list[i][0] = stn[0].replaceAll("!@#", "");
				list[i][1] = stn[1].replaceAll("!@#", "");
				list[i][2] = stn[2].replaceAll("!@#", "");
				list[i][3] = stn[3].replaceAll("!@#", "");
				list[i][4] = stn[4].replaceAll("!@#", "");
				list[i][5] = stn[5].replaceAll("!@#", "");
				i++;
			}
			fr.close();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		supTable.flag = est.flag;
		supTable.curPage = 1;
		//cardlayout.first(masterPane);
		supTable.listC.listRe(supTable.table);
		supTable.valueChangedSet(supTable.table, supTable.Row);
		demand.setDemand(est.demand);
	}

	/*
	 * public void addSaveList(DemandF demandF, int sumDatas, int number) { loadList(); File a = new
	 * File("save"); if (a.exists() == false) { a.mkdirs(); } String fileName; fileName = new
	 * String("save\\saveAll.txt"); try { BufferedWriter fw; fw = new BufferedWriter(new
	 * FileWriter(fileName)); fw.write((manageList.num+1)+"\r\n"); for (int i = 0; i < manageList.top;
	 * i++) { fw.write(manageList.saveList[i].date + "/" + manageList.saveList[i].name + "/" +
	 * manageList.saveList[i].money + "/" + manageList.saveList[i].no + "/"); fw.write("\r\n"); }
	 * fw.write(demandF.date + "/" + demandF.name + "/" + sumDatas + " /" + number + " /");
	 * fw.write("\r\n"); fw.flush();
	 * 
	 * fw.close(); } catch (Exception e3) { e3.printStackTrace(); } }
	 */
	public void removeList(String str) {
		File f = new File("save\\" + str);
		f.delete();
		loadList();
		tableInit();
	}
	/*
	 * public void removeList(int num) { loadList(); String fileName; fileName = new
	 * String("save\\saveAll.txt"); try { BufferedWriter fw; fw = new BufferedWriter(new
	 * FileWriter(fileName)); fw.write((manageList.num-1)+"\r\n"); for (int i = 0; i < manageList.top;
	 * i++) { if (i==num) continue; fw.write(manageList.saveList[i].date + "/" +
	 * manageList.saveList[i].name + "/" + manageList.saveList[i].money + "/" +
	 * manageList.saveList[i].no + "/"); fw.write("\r\n"); } fw.flush(); fw.close(); } catch
	 * (Exception e3) { e3.printStackTrace(); } loadList(); tableInit(); }
	 */
}

class ManageList {
	SaveList[] saveList;
	int maxSize;
	int top;
	int mattop;
	String comp, item;
	SaveList[] saveListMatch;
	Date after, curr, before;
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

	// int num;

	ManageList() {
		maxSize = 100;
		saveList = new SaveList[maxSize];
		saveListMatch = new SaveList[maxSize];
		top = 0;
		comp = "";
		item = "";
		mattop = 0;
	}

	public void setDate(Date before, Date after) {
		this.before = before;
		this.after = after;
	}

	public void setMatchStr(String comp, String item) {
		this.comp = comp;
		this.item = item;
		System.out.println(comp + " " + item + "aa");
	}

	void addList(String ref, String date, String name, String no) {
		if (!name.equals("")) {
			if (top == maxSize)
				resize();
			saveList[top++] = new SaveList(ref, date, name, no);
		}
	}

	void resize() {
		maxSize *= 2;
		SaveList temp[] = new SaveList[maxSize];
		saveListMatch = new SaveList[maxSize];
		for (int i = 0; i < maxSize / 2; i++)
			temp[i] = saveList[i];
		saveList = temp;
	}

	public int getMatchCount() {
		mattop = 0;
		String reg;
		for (int i = 0; i < top; i++) {
			if (saveList[i].name.matches(".*" + comp + ".*")) {
				try {
					curr = sdf.parse(saveList[i].date);
				} catch (ParseException e) {
					continue;
				}
				if ((before == null || (curr.after(before))) && ((after == null) || curr.before(after))) {
					if (item.isEmpty())
						addMatch(saveList[i]);
					else {
						try {
							reg=item.replace("*", "\\*");
							reg=reg.replace(".", "\\.");
							System.out.println(reg);
							if (readContentFrom(saveList[i].ref).matches(".*"+reg+".*")) {
								System.out.println(readContentFrom(saveList[i].ref));
								System.out.println(item);
								addMatch(saveList[i]);
							}
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			}
		}
		return mattop;
	}

	private void addMatch(SaveList saveList) {
		saveListMatch[mattop++] = saveList;
	}

	public String readContentFrom(String textFileName) throws IOException {
		BufferedReader bufferedTextFileReader = new BufferedReader(new FileReader(textFileName));
		StringBuilder contentReceiver = new StringBuilder();
		char[] buf = new char[1024];
		while (bufferedTextFileReader.read(buf) > 0) {
			contentReceiver.append(buf);
		}
		return contentReceiver.toString().replaceAll("\r\n", "");
	}
}

class ActionLis implements ActionListener {
	ManageList manageList;
	Load load;
	JTextField searchCompField;
	JTextField searchItemField;

	ActionLis(ManageList manageList, Load load) {
		this.manageList = manageList;
		this.load = load;
	}

	public void actionPerformed(ActionEvent e) {
		search();
	}

	public void search() {
		manageList.setMatchStr(searchCompField.getText(), searchItemField.getText());
		load.tableInit();
	}
}

class SaveList {
	String ref;
	String date;
	String name;
	String no;

	SaveList(String ref, String date, String name, String no) {
		this.ref = ref;
		this.date = date;
		this.name = name;
		this.no = no;
	}
}