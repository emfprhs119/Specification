package Output;

import java.awt.print.Book;
import java.awt.print.PageFormat;
import java.awt.print.Paper;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;

import FrameComponent.ViewManager;
import Specification.SpecificationView;

public class Print {
	ViewManager viewManager;
	public Print(ViewManager viewManager) {
		this.viewManager=viewManager;
	}
	static private PageFormat getMinimumMarginPageFormat(PrinterJob printJob) {
	    PageFormat pf0 = printJob.defaultPage();
	    PageFormat pf1 = (PageFormat) pf0.clone();
	    Paper p = pf0.getPaper();
	    p.setImageableArea(0, 0,pf0.getWidth(), pf0.getHeight());
	    pf1.setPaper(p);
	    PageFormat pf2 = printJob.validatePage(pf1);
	    return pf2;     
	}
	public void printToPrinter(SpecificationView specificationView) {
		PrinterJob printerJob = PrinterJob.getPrinterJob();
        Book book = new Book();
        
        book.append(specificationView, getMinimumMarginPageFormat(printerJob));
        while(specificationView.next()){
        	book.append(specificationView, new PageFormat());
        }
        printerJob.setPageable(book);
 
        boolean doPrint = printerJob.printDialog();
 
        if (doPrint) {
            try {
            	printerJob.print();
            }  catch (PrinterException ex) {
                System.err.println("Error occurred while trying to Print: " + ex);
            }
        }
    
    }
}
