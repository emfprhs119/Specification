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
import Specification.SpecificationPrintView;
import Specification.SpecificationView;
import Specification.SpecificationView.PAGESET;
import Specification.StringPlace;

public class Print {
	enum Flag {
		PDF, PRINT
	};

	PDFont font = null;
	int diffY = 400;
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
		while (specificationView.prev());
		PrinterJob printerJob = PrinterJob.getPrinterJob();
		Book book = new Book();
		do {
			book.append(new SpecificationPrintView(specificationView),
					getMinimumMarginPageFormat(printerJob));
		} while (specificationView.next());
		printerJob.setPageable(book);
		if (printerJob.printDialog()) {
			try {
				printerJob.print();
			} catch (PrinterException ex) {
				System.err.println("Error occurred while trying to Print: " + ex);
			}
		}
		while (specificationView.prev());

	}

	public void printToPdf(SpecificationView specificationView) {
		while (specificationView.prev());
		File file;
		file = new File("pdf");
		file.mkdir();
		PDDocument doc = printDocument(specificationView);
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
		while (specificationView.prev());
	}

	@SuppressWarnings("deprecation")
	private PDDocument printDocument(SpecificationView specificationView) {
		PDDocument doc = new PDDocument();
		BufferedImage printDemandImage;
		BufferedImage frontDemandImage;
		BufferedImage backDemandImage;
		BufferedImage stampImage;
		PDImageXObject printDemandPDImage = null;
		PDImageXObject frontDemandPDImage = null;
		PDImageXObject backDemandPDImage = null;
		PDImageXObject stampPDImage = null;
		PDPage bp;
		PDPageContentStream cs = null;
		StringPlace[] sp;
		try {
			printDemandImage = ImageIO.read(getClass().getClassLoader().getResource("resources/print_demand.png"));
			frontDemandImage = ImageIO.read(getClass().getClassLoader().getResource("resources/printFront_demand.png"));
			backDemandImage = ImageIO.read(getClass().getClassLoader().getResource("resources/printBack_demand.png"));

			stampImage = ImageIO.read(new File("stamp.png"));
			printDemandPDImage = LosslessFactory.createFromImage(doc, printDemandImage);
			frontDemandPDImage = LosslessFactory.createFromImage(doc, frontDemandImage);
			backDemandPDImage = LosslessFactory.createFromImage(doc, backDemandImage);
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

				switch (specificationView.getPageSet()) {
				case BASIC:
					cs.drawImage(printDemandPDImage, 0, 380, printDemandPDImage.getWidth() * 0.75f,
							printDemandPDImage.getHeight() * 0.75f);
					break;
				case FRONT:
					cs.drawImage(frontDemandPDImage, 0, 380, frontDemandPDImage.getWidth() * 0.75f,
							frontDemandPDImage.getHeight() * 0.75f);
					break;
				case BACK:
					cs.drawImage(backDemandPDImage, 0, 380, backDemandPDImage.getWidth() * 0.75f,
							backDemandPDImage.getHeight() * 0.75f);
					break;
				}
				if (specificationView.getPageSet() != PAGESET.BACK)
					cs.drawImage(stampPDImage, (sp[5].x + 45) * 0.75f, 718, stampPDImage.getWidth() * 0.75f,
							stampPDImage.getHeight() * 0.75f);
				PDExtendedGraphicsState extendedGraphicsState = new PDExtendedGraphicsState();
				extendedGraphicsState.setNonStrokingAlphaConstant(0.15f);
				cs.setGraphicsStateParameters(extendedGraphicsState);

				cs.setNonStrokingColor(124, 153, 131);
				cs.setStrokingColor(124, 153, 131);
				drawCS(cs, sp, specificationView.getPageSet());
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
	private void drawCS(PDPageContentStream cs, StringPlace[] sp, PAGESET pageSet) throws IOException {
		switch (pageSet) {
		case BASIC:
			cs.fillRect(55, 627 + 29f, 299, 28f);
			cs.fillRect(55, 627 - 14.3f * 14 - 3, 528, 17.3f);
			break;
		case FRONT:
			cs.fillRect(55, 627 + 29f, 299, 28f);
			break;
		case BACK:
			cs.fillRect(55, 627 - 14.3f * 14 - 3, 528, 17.3f);
			break;
		}
		for (int i = (pageSet == PAGESET.BACK ? 0 : 3); i < 10; i++) {
			cs.fillRect(55, 712.8f - 28.6f * i, 528, 14.3f);
		}

		// 가로 라인
		for (int i = (pageSet == PAGESET.BACK ? 0 : 6); i < 19; i++) {
			cs.setNonStrokingColor(Color.black);
			cs.drawLine(55, 712.8f - 14.3f * i, 583, 712.8f - 14.3f * i);
		}
		// 세로 라인
		float yLines[] = { 36.2f, 134.5f, 315.3f, 393f, 442.5f, 516.4f, 619f };
		for (float yLine : yLines) {
			cs.drawLine(55 + yLine * 0.75f, (pageSet == PAGESET.BACK ? 731 : 656), 55 + yLine * 0.75f, 441);
		}

		PDExtendedGraphicsState extendedGraphicsState = new PDExtendedGraphicsState();
		extendedGraphicsState.setNonStrokingAlphaConstant(1f);
		cs.setGraphicsStateParameters(extendedGraphicsState);
		cs.setNonStrokingColor(Color.BLACK);
		cs.beginText();
		cs.moveTextPositionByAmount(-2.5f, 809);
		if (pageSet != PAGESET.BACK) {
			cs.setFont(font, 20);
			cs.moveTextPositionByAmount((sp[13].x + sp[13].getAlignX(font) * 0.015f) * 0.75f, -sp[13].y * 0.75f);
			cs.drawString(sp[13].str);
			cs.moveTextPositionByAmount(-(sp[13].x + sp[13].getAlignX(font) * 0.015f) * 0.75f, +sp[13].y * 0.75f);
			cs.setFont(font, 17);
			cs.moveTextPositionByAmount((sp[3].x + sp[3].getAlignX(font) * 0.015f) * 0.75f, -sp[3].y * 0.75f);
			cs.drawString(sp[3].str);
			cs.moveTextPositionByAmount(-(sp[3].x + sp[3].getAlignX(font) * 0.015f) * 0.75f, +sp[3].y * 0.75f);

		}
		cs.setFont(font, 11);

		for (int i = 0; i < sp.length; i++) {
			if (i != 3 && i != 13) {
				if (pageSet == PAGESET.BACK && i > 0 && i < 11)
					continue;
				if (pageSet == PAGESET.FRONT && (i == 11 || i == 12))
					continue;
				cs.moveTextPositionByAmount((sp[i].x + sp[i].getAlignX(font) * 0.015f) * 0.753f, -sp[i].y * 0.753f);
				cs.drawString(sp[i].str);
				cs.moveTextPositionByAmount(-(sp[i].x + sp[i].getAlignX(font) * 0.015f) * 0.753f, +sp[i].y * 0.753f);
			}
		}
		cs.endText();
	}

}
