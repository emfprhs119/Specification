package FrameComponent;

import java.awt.Font;

import javax.swing.JLabel;

import Main.Main;
import Specification.Specification;

public class FrameLabel {
	public JLabel getPage() {
		return page;
	}
	public Specification getSpec() {
		return spec;
	}
	public void setSpec(Specification spec) {
		if (spec==null)
			return;
		document.setText(spec.getDate()+"_"+spec.getName()+"_"+spec.getNo());
		this.spec = spec;
	}
	public void setPageText(String str) {
		this.page.setText(str);
	}
	public JLabel getDocument() {
		return document;
	}
	public void setDocument(JLabel document) {
		this.document = document;
	}
	public JLabel getTitle() {
		return title;
	}
	public void setTitle(JLabel title) {
		this.title = title;
	}

	private JLabel document;
	private Specification spec;
	private JLabel title;
	private JLabel page;
	//private MainFrame mainFrame;
	FrameLabel(){
		title=nameInit();
		document=documentInit();
		page=pageInit();
	}
	private JLabel nameInit() {
		JLabel title = new JLabel(Main.name);
		title.setFont(new Font(Main.font, Font.BOLD, 60));
		title.setBounds(230, 0, 500, 100);
		return title;
	}
	
	private JLabel documentInit() {
		JLabel document = new JLabel("New Document");
		document.setFont(new Font(Main.font, Font.BOLD, 15));
		document.setBounds(419 - document.getText().length() * 6, 5, 300, 20);
		return document;
	}
	
	private JLabel pageInit() {
		JLabel page = new JLabel("page1/1");
		page.setFont(new Font("µ¸¿ò", 0, 15));
		page.setBounds(43, 30, 80, 30);
		return page;
	}
}
