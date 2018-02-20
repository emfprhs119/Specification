package Specification;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import Demand.DemandView;
import FrameComponent.Function;
import Inheritance.ColumnManager;
import Inheritance.ListManager;
import Inheritance.View_Interface;
import Main.Main;
import Product.Product;
import Product.ProductList;
import Supply.Supply;

public class SpecificationView extends JFrame implements View_Interface<Specification>, Printable {
	private Specification spec;
	private Container contentPane;
	private Image printImage,stampImage;
	private ListManager<Specification, SpecificationList> listManager;
	private ProductList productList;
	private Supply supply;
	private String date;
	private String demand;
	private String no;
	private StringPlace sp[];
	long sumPrice, sumTax;
	private Font veryLargeFont = new Font(null, Font.BOLD, 23);
	private Font largeFont = new Font(null, Font.BOLD, 19);
	private Font font = new Font(null, Font.BOLD, 12);

	private Font veryLargePrintFont = new Font(null, Font.BOLD, 23);
	private Font largePrintFont = new Font(null, Font.PLAIN, 19);
	private Font printfont = new Font(null, Font.PLAIN, 12);
	private int page, maxPage;
	private boolean outline;

	public SpecificationView() {
		super("미리보기");
		setBounds(200, 300, 838, 650);
		setDefaultCloseOperation(HIDE_ON_CLOSE);
		try {
			printImage = ImageIO.read(getClass().getClassLoader().getResource("resources/print.png"));
			stampImage = ImageIO.read(new File("stamp.png"));
		} catch (IOException e1) {
			JOptionPane.showConfirmDialog(null, "stamp.png 가 없습니다.", "에러", JOptionPane.CLOSED_OPTION,
					JOptionPane.ERROR_MESSAGE);
			e1.printStackTrace();
		}
		initListManager();
		setResizable(false);
		contentPane = this.getContentPane();
		contentPane.setLayout(null);
		contentPane.setBackground(Color.WHITE);

		productList = new ProductList();
		supply = new Supply();
		outline = false;
		setVisible(false);
	}

	private void buttonInit(SpecFrameAction specAction) {
		Rectangle bSize = new Rectangle(45, 550, 120, 45);
		JButton button[] = new JButton[6];
		button[0] = new JButton("◀ 이전");
		button[1] = new JButton("다음 ▶");
		button[2] = new JButton("편집");
		button[3] = new JButton("인쇄");
		button[4] = new JButton("pdf");
		button[5] = new JButton("닫기");
		for (int i = 0; i < 6; i++) {
			button[i].setBounds(bSize);
			bSize.x += bSize.width + 3;
			button[i].setFont(new Font(Main.font, Font.BOLD, 22));
			button[i].setVisible(true);
			button[i].addActionListener(specAction);
			button[i].setToolTipText(button[i].getText());
			add(button[i]);
		}
		button[3].addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				int choice = JOptionPane.showConfirmDialog(null, "외곽선을 출력하시겠습니까?", "외곽선", JOptionPane.YES_NO_OPTION,
						JOptionPane.INFORMATION_MESSAGE);
				switch (choice) {
				case 0:
					outline=true;
					break;
				case 1:
					outline=false;
					break;
				}
			}
			
		});
	};

	private void initListManager() {
		ColumnManager column = new ColumnManager();
		String strArr[] = { "날짜", "상호", "no." };
		int intArr[] = { 40, 120, 10 };
		column.setColumn(strArr, intArr);
		listManager = new ListManager<Specification, SpecificationList>("명세서 불러오기", this, new SpecificationList(),
				column, false);
		listManager.setBounds(1038, 0, 380, 600);
	}

	public ListManager<Specification, SpecificationList> getListManager() {
		return listManager;
	}

	public boolean prev() {
		if (page > 1) {
			page--;
			placement();
			return true;
		}
		return false;
	}

	public boolean next() {
		if (page < maxPage) {
			page++;
			placement();
			return true;
		}
		return false;

	}

	@Override
	public void loadData(Specification spec) {
		this.spec = spec;
		date = spec.getDate();
		demand = spec.getName();
		no = String.valueOf(spec.getNo());
		productList.loadList(spec.getIdQuery());
		Main.dataReader.getQuery(supply, "SELECT * FROM SUPPLY");

		page = 1;
		maxPage = productList.size() / Main.FrontRow;
		// System.out.println(productList.size()+" "+Main.FrontRow);
		sumPrice = productList.getSumPrice();
		sumTax = productList.getSumTax();
		placement();

		this.setVisible(true);
	}

	private void placement() {
		Product product;
		int num;
		int x, y;
		num = 0;
		sp = new StringPlace[14 + Main.FrontRow * 8];
		// DEMAND
		sp[0] = new StringPlace(page + "  /  " + maxPage, 195, 82, ALIGN.CENTER);
		sp[1] = new StringPlace(date, 130, 112, ALIGN.LEFT);
		sp[2] = new StringPlace(demand, 130, 150, ALIGN.LEFT);
		// SUPPLY
		sp[3] = new StringPlace(supply.getNum(), 622, 82, ALIGN.CENTER);
		sp[4] = new StringPlace(supply.getName(), 508, 108, ALIGN.LEFT);
		sp[5] = new StringPlace(supply.getWho(), 690, 108, ALIGN.LEFT);
		sp[6] = new StringPlace(supply.getAddress(), 508, 139, ALIGN.LEFT);
		sp[7] = new StringPlace(supply.getWork(), 508, 168, ALIGN.LEFT);
		sp[8] = new StringPlace(supply.getWork2(), 638, 168, ALIGN.LEFT);
		sp[9] = new StringPlace(supply.getTel(), 508, 196, ALIGN.LEFT);
		sp[10] = new StringPlace(supply.getFax(), 655, 196, ALIGN.LEFT);
		// PRODUCT
		x = 77;
		y = 238;
		num = 14;
		for (int i = 0; i < Main.FrontRow; i++) {

			product = productList.get((page - 1) * Main.FrontRow + i);
			sp[num++] = new StringPlace(product.getDate(), x, y, ALIGN.LEFT);
			sp[num++] = new StringPlace(product.getCode(), x + 85, y, ALIGN.CENTER);
			sp[num++] = new StringPlace(product.getName(), x + 135, y, ALIGN.LEFT);
			sp[num++] = new StringPlace(product.getStandard(), x + 315, y, ALIGN.LEFT);
			sp[num++] = new StringPlace(product.getCount(), x + 416, y, ALIGN.CENTER);
			sp[num++] = new StringPlace(product.getCost(), x + 512, y, ALIGN.RIGHT);
			sp[num++] = new StringPlace(product.getPriceStr(), x + 613, y, ALIGN.RIGHT);
			sp[num++] = new StringPlace(product.getTaxStr(), x + 697, y, ALIGN.RIGHT);

			y += 19;
		}
		// SUM_PRICE
		sp[11] = new StringPlace(Main.longToMoneyString(sumPrice), x + 613, 507, ALIGN.RIGHT);
		sp[12] = new StringPlace(Main.longToMoneyString(sumTax), x + 697, 507, ALIGN.RIGHT);
		sp[13] = new StringPlace(Main.longToMoneyString(sumPrice + sumTax), 250, 193, ALIGN.LEFT);
		repaint();
	}

	public void paint(Graphics g) {
		super.paint(g);
		g.drawImage(printImage, 0, 20, null);

		g.setFont(veryLargeFont);
		g.drawString(sp[13].str, sp[13].x + sp[13].getAlignX(g), sp[13].y);
		g.setFont(largeFont);
		g.drawString(sp[3].str, sp[3].x + sp[3].getAlignX(g), sp[3].y);
		g.setFont(font);
		for (int i = 0; i < sp.length; i++) {
			if (sp[i] != null && sp[i].str != null && i != 3 && i != 13) {
				g.drawString(sp[i].str, sp[i].x + sp[i].getAlignX(g), sp[i].y);
			}
		}
	}

	@Override
	public int print(Graphics g, PageFormat pageFormat, int pageIndex) throws PrinterException {
		Graphics2D g2d = (Graphics2D) g;
		g2d.scale(0.73, 0.73);
		g2d.translate(7, -31);
		if (outline) {
			g.drawImage(printImage, 0, 20, null);
			g.drawString("(공급받는자 보관용)", 320, 128);
		}
		g2d.setFont(veryLargePrintFont);
		g2d.drawString(sp[13].str, (sp[13].x + sp[13].getAlignX(g)), (int) (sp[13].y));
		g2d.setFont(largePrintFont);
		g2d.drawString(sp[3].str, sp[3].x + sp[3].getAlignX(g), sp[3].y);
		g2d.setFont(printfont);
		for (int i = 0; i < sp.length; i++) {
			if (sp[i] != null && sp[i].str != null && i != 3 && i != 13) {
				g2d.drawString(sp[i].str, (int) ((sp[i].x + sp[i].getAlignX(g))), sp[i].y);
			}
		}
		g2d.drawImage(stampImage, sp[5].x+45, sp[5].y-20, null);
		g2d.translate(0, pageFormat.getImageableHeight() / 2 + 164);// pageFormat.getImageableY());
		if (outline) {
			g.drawImage(printImage, 0, 20, null);
			g.drawString("(공급자 보관용)", 333, 128);
			g2d.drawString(
					"-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------",
					0, 36);
		}

		g2d.setFont(veryLargePrintFont);
		g2d.drawString(sp[13].str, (sp[13].x + sp[13].getAlignX(g)), (int) (sp[13].y));
		g2d.setFont(largePrintFont);
		g2d.drawString(sp[3].str, sp[3].x + sp[3].getAlignX(g), sp[3].y);
		g2d.setFont(printfont);
		for (int i = 0; i < sp.length; i++) {
			if (sp[i] != null && sp[i].str != null && i != 3 && i != 13) {
				g2d.drawString(sp[i].str, (int) ((sp[i].x + sp[i].getAlignX(g))), sp[i].y);
			}
		}
		g2d.drawImage(stampImage, sp[5].x+45, sp[5].y-20, null);
		return 0;
	}

	public Specification saveCurrData(DemandView demandView) {
		Specification spec = new Specification(demandView);
		listManager.addItem(spec);
		return spec;
	}

	public void setFunction(Function function) {
		buttonInit(new SpecFrameAction(this, function));
	}

	public ProductList getProductList() {
		return productList;
	}

	public Specification getSpec() {
		return spec;
	}

	@Override
	public void loadDataId(String id) {
		// TODO Auto-generated method stub

	}

	public String getDate() {
		return date;
	}
	
	public String getNo() {
		return no;
	}
	
	public String getDemand() {
		// TODO Auto-generated method stub
		return demand;
	}

	public StringPlace[] getSp() {
		// TODO Auto-generated method stub
		return sp;
	}
}
