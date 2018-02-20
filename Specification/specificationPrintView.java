package Specification;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class specificationPrintView implements Printable {

	private Font veryLargePrintFont = new Font(null, Font.BOLD, 23);
	private Font largePrintFont = new Font(null, Font.PLAIN, 19);
	private Font printfont = new Font(null, Font.PLAIN, 12);
	private Image printImageDemand,printImageSupply,stampImage;
	private StringPlace sp[];
	private boolean outline;
	public specificationPrintView(StringPlace sp[],boolean outline){
		try {
		printImageDemand = ImageIO.read(getClass().getClassLoader().getResource("resources/print_demand.png"));
		stampImage = ImageIO.read(new File("stamp.png"));
			printImageSupply = ImageIO.read(getClass().getClassLoader().getResource("resources/print_supply.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.sp=sp;
		this.outline=outline;
	}
	@Override
	public int print(Graphics g, PageFormat pageFormat, int pageIndex) throws PrinterException {
		Graphics2D g2d = (Graphics2D) g;
		g2d.scale(0.73, 0.73);
		g2d.translate(7, -31);
		if (outline) {
			g.drawImage(printImageDemand, 0, 20, null);
			g.setColor(new Color(124/255f, 153/255f, 131/255f,0.3f));
			g.fillRect(74, 166, 398, 38);
			for(int i=0;i<7;i++){
				g.fillRect(74,224+i*38,705, 19);
			}
			g.fillRect(74,492,705, 24);
			g.setColor(new Color(124/255f, 153/255f, 131/255f,1f));
			for(int i=0;i<14;i++){
				g.drawLine(74,224+i*19,775, 224+i*19);
			}
			g.drawLine(110,224,110,492);
			g.drawLine(208,224,208,492);
			g.drawLine(389,224,389,492);
			g.drawLine(467,224,467,492);
			g.drawLine(516,224,516,492);
			g.drawLine(590,224,590,492);
			g.drawLine(692,224,692,492);

			g.setColor(Color.black);
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
			g.drawImage(printImageSupply, 0, 20, null);
			g2d.drawString(
					"-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------",
					0, 36);
			g.setColor(new Color(164/255f, 151/255f, 142/255f,0.3f));
			g.fillRect(74, 166, 398, 38);
			for(int i=0;i<7;i++){
				g.fillRect(74,224+i*38,705, 19);
			}
			g.fillRect(74,492,705, 24);
			g.setColor(new Color(164/255f, 151/255f, 142/255f,1f));
			for(int i=0;i<14;i++){
				g.drawLine(74,224+i*19,775, 224+i*19);
			}
			g.drawLine(110,224,110,492);
			g.drawLine(208,224,208,492);
			g.drawLine(389,224,389,492);
			g.drawLine(467,224,467,492);
			g.drawLine(516,224,516,492);
			g.drawLine(590,224,590,492);
			g.drawLine(692,224,692,492);

			g.setColor(Color.black);
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


}
