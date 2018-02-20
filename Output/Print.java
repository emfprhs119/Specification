package Output;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.print.Book;
import java.awt.print.PageFormat;
import java.awt.print.Paper;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
import org.apache.pdfbox.pdmodel.graphics.image.LosslessFactory;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.apache.pdfbox.pdmodel.graphics.state.PDExtendedGraphicsState;
import FrameComponent.ViewManager;
import Specification.SpecificationView;
import Specification.StringPlace;
import Specification.specificationPrintView;

public class Print {
	enum Flag {
		PDF, PRINT
	};

	PDFont font = null;
	int diffY = 400;
	// ViewManager viewManager;

	public Print(ViewManager viewManager) {
		// this.viewManager = viewManager;
	}

	
	  static private PageFormat getMinimumMarginPageFormat(PrinterJob printJob)
	  { PageFormat pf0 = printJob.defaultPage(); PageFormat pf1 = (PageFormat)
	  pf0.clone(); Paper p = pf0.getPaper(); p.setImageableArea(0, 0,
	  pf0.getWidth(), pf0.getHeight()); pf1.setPaper(p); PageFormat pf2 =
	  printJob.validatePage(pf1); return pf2; }
	 	public void printToPrinter(SpecificationView specificationView) {

		PrinterJob printerJob = PrinterJob.getPrinterJob();
		Book book = new Book();
		do{
			book.append(new specificationPrintView(specificationView.getSp(),specificationView.getOutLine()), getMinimumMarginPageFormat(printerJob));
		}
	 	while(specificationView.next());
		printerJob.setPageable(book);
		if (printerJob.printDialog()) {
			try {
				printerJob.print();
			} catch (PrinterException ex) {
				System.err.println("Error occurred while trying to Print: " + ex);
			}
		}
		;
		/*
		  PDDocument doc = printDocument(specificationView, Flag.PRINT);
		  
		  PrinterJob job = PrinterJob.getPrinterJob(); job.setPageable(new
		  PDFPageable(doc)); if (job.printDialog()) { try { job.print(); }
		  catch (PrinterException e) { // TODO Auto-generated catch block
		  e.printStackTrace(); } }
		  */
		  while (specificationView.prev());
		 
	}

	public void printToPdf(SpecificationView specificationView) {
		PDDocument doc = printDocument(specificationView, Flag.PDF);
		String fileName = specificationView.getDate() + "_" + specificationView.getDemand() + "_"
				+ specificationView.getNo();

		try {
			doc.save("pdf\\" + fileName + ".pdf");
			doc.close();
			JOptionPane.showMessageDialog(null, "pdf파일로 저장되었습니다.");
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "pdf파일을 닫고 다시 시도해 주세요.");

			try {
				doc.close();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		while (specificationView.prev())
			;
	}

	@SuppressWarnings("deprecation")
	private PDDocument printDocument(SpecificationView specificationView, Flag flag) {
		PDDocument doc = new PDDocument();
		BufferedImage outLineImageDemand;
		BufferedImage outLineImageSupply;
		BufferedImage stampImage;
		PDImageXObject outLinePDImageDemand = null;
		PDImageXObject outLinePDImageSupply = null;
		PDImageXObject stampPDImage = null;
		PDPage bp;
		PDPageContentStream cs = null;
		StringPlace[] sp;
		try {
			outLineImageDemand = ImageIO.read(getClass().getClassLoader().getResource("resources/print_demand.png"));
			outLineImageSupply = ImageIO.read(getClass().getClassLoader().getResource("resources/print_supply.png"));

			stampImage = ImageIO.read(new File("stamp.png"));
			outLinePDImageDemand = LosslessFactory.createFromImage(doc, outLineImageDemand);
			outLinePDImageSupply = LosslessFactory.createFromImage(doc, outLineImageSupply);
			stampPDImage = LosslessFactory.createFromImage(doc, stampImage);
			font = PDType0Font.load(doc,
					getClass().getClassLoader().getResourceAsStream("resources/NanumMyeongjoBold.ttf"));
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		do {
			bp = new PDPage();
			sp = specificationView.getSp();
			doc.addPage(bp);

			try {
				cs = new PDPageContentStream(doc, bp);
				cs.drawImage(outLinePDImageDemand, 0, 380, outLinePDImageDemand.getWidth() * 0.75f,
						outLinePDImageDemand.getHeight() * 0.75f);
				cs.drawImage(stampPDImage, (sp[5].x + 45) * 0.75f, 718, stampPDImage.getWidth() * 0.75f,
						stampPDImage.getHeight() * 0.75f);

				if (flag == Flag.PRINT) {
					cs.drawImage(outLinePDImageSupply, 0, 380 - diffY, outLinePDImageSupply.getWidth() * 0.75f,
							outLinePDImageSupply.getHeight() * 0.75f);
					cs.drawImage(stampPDImage, (sp[5].x + 45) * 0.75f, 718 - diffY, stampPDImage.getWidth() * 0.75f,
							stampPDImage.getHeight() * 0.75f);
				}
				PDExtendedGraphicsState extendedGraphicsState = new PDExtendedGraphicsState();
				extendedGraphicsState.setNonStrokingAlphaConstant(0.15f);
				cs.setGraphicsStateParameters(extendedGraphicsState);

				cs.setNonStrokingColor(124, 153, 131);
				cs.setStrokingColor(124, 153, 131);
				drawCS(cs, sp, 0);
				if (flag == Flag.PRINT) {
					cs.setNonStrokingColor(164, 151, 142);
					cs.setStrokingColor(164, 151, 142);
					drawCS(cs, sp, -diffY);
				}
				cs.close();
			} catch (IOException e) {
				try {
					cs.close();
					doc.close();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				e.printStackTrace();
			}

		} while ((specificationView.next()));

		return doc;
	}

	@SuppressWarnings("deprecation")
	private void drawCS(PDPageContentStream cs, StringPlace[] sp, int addY) throws IOException {

		for (int i = 0; i < 13; i++) {
			if (i % 2 == 0) {
				cs.fillRect(55, 627 - 14.3f * i + addY, 583 - 55, 14.3f);
			}
		}
		cs.fillRect(55, 627 + 29f + addY, 299, 28f);
		cs.fillRect(55, 627 - 14.3f * 14 - 3 + addY, 583 - 55, 17.3f);

		// 가로 라인
		for (int i = 0; i < 13; i++) {
			cs.setNonStrokingColor(Color.black);
			cs.drawLine(55, 627 - 14.3f * i + addY, 583, 627 - 14.3f * i + addY);
		}
		// 세로 라인
		cs.drawLine(55 + (36 + 0.2f) * 0.75f, 656 + addY, 55 + (36 + 0.2f) * 0.75f, 441 + addY);
		cs.drawLine(55 + (134 + 0.5f) * 0.75f, 656 + addY, 55 + 134 * 0.75f, 441 + addY);
		cs.drawLine(55 + (315 + 0.3f) * 0.75f, 656 + addY, 55 + (315 + 0.3f) * 0.75f, 441 + addY);
		cs.drawLine(55 + 393 * 0.75f, 656 + addY, 55 + 393 * 0.75f, 441 + addY);
		cs.drawLine(55 + (442 + 0.5f) * 0.75f, 656 + addY, 55 + (442 + 0.5f) * 0.75f, 441 + addY);
		cs.drawLine(55 + (516 + 0.4f) * 0.75f, 656 + addY, 55 + (516 + 0.4f) * 0.75f, 441 + addY);
		cs.drawLine(55 + 619 * 0.75f, 656 + addY, 55 + 619 * 0.75f, 441 + addY);
		cs.setNonStrokingColor(Color.BLACK);
		for (int j = 0; j < 7; j++) {
			cs.beginText();
			cs.moveTextPositionByAmount(-2.5f, 809 + addY);
			cs.setFont(font, 20);
			cs.moveTextPositionByAmount((sp[13].x + sp[13].getAlignX(font) * 0.015f) * 0.75f, -sp[13].y * 0.75f);
			cs.drawString(sp[13].str);
			cs.moveTextPositionByAmount(-(sp[13].x + sp[13].getAlignX(font) * 0.015f) * 0.75f, +sp[13].y * 0.75f);
			cs.setFont(font, 17);
			cs.moveTextPositionByAmount((sp[3].x + sp[3].getAlignX(font) * 0.015f) * 0.75f, -sp[3].y * 0.75f);
			cs.drawString(sp[3].str);
			cs.moveTextPositionByAmount(-(sp[3].x + sp[3].getAlignX(font) * 0.015f) * 0.75f, +sp[3].y * 0.75f);
			cs.setFont(font, 11);

			for (int i = 0; i < sp.length; i++) {
				if (i != 3 && i != 13) {
					cs.moveTextPositionByAmount((sp[i].x + sp[i].getAlignX(font) * 0.015f) * 0.753f, -sp[i].y * 0.753f);
					cs.drawString(sp[i].str);
					cs.moveTextPositionByAmount(-(sp[i].x + sp[i].getAlignX(font) * 0.015f) * 0.753f,
							+sp[i].y * 0.753f);
				}
			}
			cs.endText();
		}
	}
}
