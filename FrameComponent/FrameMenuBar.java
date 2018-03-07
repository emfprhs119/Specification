package FrameComponent;

import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;

public class FrameMenuBar {
	private MenuBar menuBar;
	public FrameMenuBar(MenuAction action) {
		menuBar = new MenuBar(); // 메뉴바
		MenuItem menuItems[];
		Menu menus[] =new Menu[2];
		// 주메뉴
		menus[0] = new Menu("파일"); 
		menus[1] = new Menu("도움말");
		//---------------------------------------
		menuItems = new MenuItem[7];
		menuItems[0] = new MenuItem("새 견적서"); 
		menuItems[1] = new MenuItem("불러오기");
		menuItems[2] = new MenuItem("저장하기");
		menuItems[3] = new MenuItem("공급자 수정");
		menuItems[4] = new MenuItem("인쇄하기");
		menuItems[5] = new MenuItem("PDF내보내기");
		menuItems[6] = new MenuItem("종료");
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
		//menuItems[0] = new MenuItem("도움말");
		menuItems[0] = new MenuItem("About 명세서");
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
