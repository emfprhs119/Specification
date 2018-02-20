package Output;

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

import FrameComponent.ViewManager;
import Specification.SpecificationView;
import Specification.StringPlace;

public class Print {
	ViewManager viewManager;
	BufferedImage outLineImage;
	BufferedImage stampImage;
	PDImageXObject outLinePDImage = null;
	PDImageXObject stampPDImage = null;

	public Print(ViewManager viewManager) {
		this.viewManager = viewManager;
		try {
			outLineImage = ImageIO.read(getClass().getClassLoader().getResource("resources/print.png"));
			stampImage = ImageIO.read(new File("stamp.png"));
			outLinePDImage = LosslessFactory.createFromImage(new PDDocument(), outLineImage);
			stampPDImage = LosslessFactory.createFromImage(new PDDocument(), stampImage);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	static private PageFormat getMinimumMarginPageFormat(PrinterJob printJob) {
		PageFormat pf0 = printJob.defaultPage();
		PageFormat pf1 = (PageFormat) pf0.clone();
		Paper p = pf0.getPaper();
		p.setImageableArea(0, 0, pf0.getWidth(), pf0.getHeight());
		pf1.setPaper(p);
		PageFormat pf2 = printJob.validatePage(pf1);
		return pf2;
	}

	public void printToPrinter(SpecificationView specificationView) {
		PrinterJob printerJob = PrinterJob.getPrinterJob();
		Book book = new Book();

		book.append(specificationView, getMinimumMarginPageFormat(printerJob));
		while (specificationView.next()) {
			book.append(specificationView, new PageFormat());
		}
		printerJob.setPageable(book);

		boolean doPrint = printerJob.printDialog();

		if (doPrint) {
			try {
				printerJob.print();
			} catch (PrinterException ex) {
				System.err.println("Error occurred while trying to Print: " + ex);
			}
		}

	}

	@SuppressWarnings("deprecation")
	public void printToPdf(SpecificationView specificationView) {
		PDDocument doc = new PDDocument();
		String fileName = specificationView.getDate() + "_" + specificationView.getDemand() + "_"
				+ specificationView.getNo();
		PDFont font = null;
		StringPlace[] sp;
		PDPage bp;
		PDPageContentStream cs;
		try {
			font = PDType0Font.load(doc,
					getClass().getClassLoader().getResourceAsStream("resources/NanumMyeongjoBold.ttf"));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		do {
			bp = new PDPage();
			sp = specificationView.getSp();
			doc.addPage(bp);
			try {
				cs = new PDPageContentStream(doc, bp);
				cs.drawImage(outLinePDImage, 0, 380, outLinePDImage.getWidth() * 0.75f,
						outLinePDImage.getHeight() * 0.75f);
				cs.drawImage(stampPDImage, (sp[5].x + 45) * 0.75f, 718, stampPDImage.getWidth() * 0.75f,
						stampPDImage.getHeight() * 0.75f);
				cs.beginText();
				cs.moveTextPositionByAmount(-2.5f, 809);
				cs.setFont(font, 20);
				cs.moveTextPositionByAmount((sp[13].x + sp[13].getAlignX(font) * 0.015f) * 0.75f, -sp[13].y * 0.75f);
				cs.drawString(sp[13].str);
				cs.moveTextPositionByAmount(-(sp[13].x + sp[13].getAlignX(font) * 0.015f) * 0.75f, +sp[13].y * 0.75f);
				cs.setFont(font, 17);
				cs.moveTextPositionByAmount((sp[3].x + sp[3].getAlignX(font) * 0.015f) * 0.75f, -sp[3].y * 0.75f);
				cs.drawString(sp[3].str);
				cs.moveTextPositionByAmount(-(sp[3].x + sp[3].getAlignX(font) * 0.015f) * 0.75f, +sp[3].y * 0.75f);
				cs.setFont(font, 11);
				cs.moveTextPositionByAmount(235, -95);
				cs.drawString("(공급받는자 보관용)");
				cs.moveTextPositionByAmount(-235, +95);

				for (int i = 0; i < sp.length; i++) {
					if (i != 3 && i != 13) {
						cs.moveTextPositionByAmount((sp[i].x + sp[i].getAlignX(font) * 0.015f) * 0.753f,
								-sp[i].y * 0.753f);
						cs.drawString(sp[i].str);
						cs.moveTextPositionByAmount(-(sp[i].x + sp[i].getAlignX(font) * 0.015f) * 0.753f,
								+sp[i].y * 0.753f);
					}
				}

				cs.endText();
				for (int i = 0; i < 13; i++) {
					cs.drawLine(55, 627 - 14.3f * i, 583, 627 - 14.3f * i);
				}
				cs.drawLine(55 + (36 + 0.2f) * 0.75f, 656, 55 + (36 + 0.2f) * 0.75f, 441);
				cs.drawLine(55 + (134 + 0.5f) * 0.75f, 656, 55 + 134 * 0.75f, 441);
				cs.drawLine(55 + (315 + 0.3f) * 0.75f, 656, 55 + (315 + 0.3f) * 0.75f, 441);
				cs.drawLine(55 + 393 * 0.75f, 656, 55 + 393 * 0.75f, 441);
				cs.drawLine(55 + (442 + 0.5f) * 0.75f, 656, 55 + (442 + 0.5f) * 0.75f, 441);
				cs.drawLine(55 + (516 + 0.4f) * 0.75f, 656, 55 + (516 + 0.4f) * 0.75f, 441);
				cs.drawLine(55 + 619 * 0.75f, 656, 55 + 619 * 0.75f, 441);

				cs.close();
			} catch (IOException e) {
				JOptionPane.showMessageDialog(null, "pdf파일을 닫고 다시 시도해 주세요.");
				try {
					doc.close();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				e.printStackTrace();
			}

		} while ((specificationView.next()));
		try {
			doc.save("pdf\\" + fileName + ".pdf");
			doc.close();
			JOptionPane.showMessageDialog(null, "pdf파일로 저장되었습니다.");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
