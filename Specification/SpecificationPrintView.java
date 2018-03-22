package Specification;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import Specification.SpecificationView.PAGESET;

public class SpecificationPrintView implements Printable {

	private Image stampImage;
	private Image printDemandImage;
	private Image frontDemandImage;
	private Image backDemandImage;
	private Image printSupplyImage;
	private Image frontSupplyImage;
	private Image backSupplyImage;
	private StringPlace sp[];
	PAGESET pageSet;
	SpecificationView specView;
	public SpecificationPrintView(SpecificationView specView) {
		try {
			printDemandImage = ImageIO.read(getClass().getClassLoader().getResource("resources/print_demand.png"));
			frontDemandImage = ImageIO.read(getClass().getClassLoader().getResource("resources/printFront_demand.png"));
			backDemandImage = ImageIO.read(getClass().getClassLoader().getResource("resources/printBack_demand.png"));
			
			printSupplyImage = ImageIO.read(getClass().getClassLoader().getResource("resources/print_supply.png"));
			frontSupplyImage = ImageIO.read(getClass().getClassLoader().getResource("resources/printFront_supply.png"));
			backSupplyImage = ImageIO.read(getClass().getClassLoader().getResource("resources/printBack_supply.png"));
			
			stampImage = ImageIO.read(new File("stamp.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.sp = specView.getSp();
		this.pageSet = specView.getPageSet();
		this.specView = specView;
	}

	@Override
	public int print(Graphics g, PageFormat pageFormat, int pageIndex) throws PrinterException {
		Graphics2D g2d = (Graphics2D) g;
		g2d.scale(0.73, 0.73);
		g2d.translate(7, -31);

		switch(pageSet){
		case BASIC:g.drawImage(printDemandImage, 0, 20, null);break;
		case FRONT:g.drawImage(frontDemandImage, 0, 20, null);break;
		case BACK:g.drawImage(backDemandImage, 0, 20, null);break;
		}
		if (pageSet!=PAGESET.BACK)
			g.drawImage(stampImage, sp[5].x+45, sp[5].y-20, null);
		
		specView.drawSpecification(g,new Color(124 / 255f, 153 / 255f, 131 / 255f, 0.3f),sp,pageSet);
		
		g2d.translate(0, pageFormat.getImageableHeight() / 2 + 164);// pageFormat.getImageableY());

		switch(pageSet){
		case BASIC:g.drawImage(printSupplyImage, 0, 20, null);break;
		case FRONT:g.drawImage(frontSupplyImage, 0, 20, null);break;
		case BACK:g.drawImage(backSupplyImage, 0, 20, null);break;
		}
		if (pageSet!=PAGESET.BACK)
			g.drawImage(stampImage, sp[5].x+45, sp[5].y-20, null);
		
		g.drawString(
				"-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------",
				0, 36);
		specView.drawSpecification(g,new Color(164 / 255f, 151 / 255f, 142 / 255f, 0.3f),sp,pageSet);
		return 0;
	}
	
}
