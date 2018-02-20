package Product;

import java.awt.MenuItem;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTable;

class MenuItemActionListener implements ActionListener {
	JTable table;
	ProductView productView;
	ProductList productList;
	DefClipboard clipboard;

	MenuItemActionListener(ProductView productView, JTable currTable) {
		this.productView = productView;
		this.productList = productView.productList;
		this.table = currTable;

	}

	public void actionPerformed(ActionEvent e) {
		if (((MenuItem) e.getSource()).getLabel() == "�� �߰� (ctrl+shift+a)") {
			productList.addRow(table.getSelectedRow());
		} else if (((MenuItem) e.getSource()).getLabel() == "�� ���� (ctrl+shift+d)") {
			productList.removeRow(table.getSelectedRow());
		} else if (((MenuItem) e.getSource()).getLabel() == "�� ���� (ctrl+shift+c)") {
			productList.copyRow(table.getSelectedRow());
		} else if (((MenuItem) e.getSource()).getLabel() == "�� �߶󳻱� (ctrl+shift+x)") {
			productList.copyRow(table.getSelectedRow());
			productList.removeRow(table.getSelectedRow());
		} else if (((MenuItem) e.getSource()).getLabel() == "�� �ٿ��ֱ� (ctrl+shift+v)") {
			productList.pasteRow(table.getSelectedRow());
		} else if (((MenuItem) e.getSource()).getLabel() == "�� �ø��� (ctrl+shift+up)") {
			productList.shiftUpRow(table.getSelectedRow());
		} else if (((MenuItem) e.getSource()).getLabel() == "�� ������ (ctrl+shift+down)") {
			productList.shiftDownRow(table.getSelectedRow());
		} else if (((MenuItem) e.getSource()).getLabel() == "�� ���� (ctrl+c)") {
			productView.clipboardCopy(table);
		} else if (((MenuItem) e.getSource()).getLabel() == "�� �߶󳻱� (ctrl+x)") {
			productView.clipboardCopy(table);
			if (table.getSelectedColumn() != 5 && table.getSelectedColumn() != 6) {
				table.setValueAt(null, table.getSelectedRow(), table.getSelectedColumn());
				productView.valueChangedUpdate(table);
			}
		} else if (((MenuItem) e.getSource()).getLabel() == "�� �ٿ��ֱ� (ctrl+v)") {
			productView.clipboardPaste(table);
		}
		productView.tableUpdate(table);
	}
}