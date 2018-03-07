package FrameComponent;

import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;

public class FrameMenuBar {
	private MenuBar menuBar;
	public FrameMenuBar(MenuAction action) {
		menuBar = new MenuBar(); // �޴���
		MenuItem menuItems[];
		Menu menus[] =new Menu[2];
		// �ָ޴�
		menus[0] = new Menu("����"); 
		menus[1] = new Menu("����");
		//---------------------------------------
		menuItems = new MenuItem[7];
		menuItems[0] = new MenuItem("�� ������"); 
		menuItems[1] = new MenuItem("�ҷ�����");
		menuItems[2] = new MenuItem("�����ϱ�");
		menuItems[3] = new MenuItem("������ ����");
		menuItems[4] = new MenuItem("�μ��ϱ�");
		menuItems[5] = new MenuItem("PDF��������");
		menuItems[6] = new MenuItem("����");
		for(MenuItem menuItem:menuItems){
			menuItem.addActionListener(action);
			
		}
		menus[0].add(menuItems[0]);
		menus[0].addSeparator();
		menus[0].add(menuItems[1]);
		menus[0].add(menuItems[2]);
		menus[0].addSeparator();
		menus[0].add(menuItems[3]);
		menus[0].addSeparator();
		menus[0].add(menuItems[4]);
		menus[0].add(menuItems[5]);
		menus[0].addSeparator();
		menus[0].add(menuItems[6]);
		//---------------------------------------
		
		menuItems = new MenuItem[1];
		//menuItems[0] = new MenuItem("����");
		menuItems[0] = new MenuItem("About ����");
		for(MenuItem menuItem:menuItems){
			menuItem.addActionListener(action);
			menus[1].add(menuItem);
		}
		
		//---------------------------------------
		for(Menu menu:menus){
			menuBar.add(menu);
		}
	}
	public MenuBar getMenuBar() {
		return menuBar;
	}
	
}
