package springcloud.outh2.project.common.utils;


import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;

import java.io.IOException;

public class PdfHeaderFooter extends PdfPageEventHelper {
    public String header = "";
    public int presentFontSize = 12;
    public Rectangle pageSize;
    public PdfTemplate total;
    public BaseFont bf;
    public Font fontDetail;

    public void onStartPage(PdfWriter writer, Document document) {
        super.onStartPage(writer, document);
    }

    public PdfHeaderFooter() {
        this.pageSize = PageSize.A4;
        this.bf = null;
        this.fontDetail = null;
    }

    public PdfHeaderFooter(String yeMei, int presentFontSize, Rectangle pageSize) {
        this.pageSize = PageSize.A4;
        this.bf = null;
        this.fontDetail = null;
        this.header = yeMei;
        this.presentFontSize = presentFontSize;
        this.pageSize = pageSize;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public void setPresentFontSize(int presentFontSize) {
        this.presentFontSize = presentFontSize;
    }

    public void onOpenDocument(PdfWriter writer, Document document) {
        this.total = writer.getDirectContent().createTemplate(50.0F, 50.0F);
    }

    public void onEndPage(PdfWriter writer, Document document) {
        try {
            if (this.bf == null) {
                this.bf = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", false);
            }

            if (this.fontDetail == null) {
                this.fontDetail = new Font(this.bf, (float)this.presentFontSize, 0);
            }
        } catch (DocumentException var8) {
            var8.printStackTrace();
        } catch (IOException var9) {
            var9.printStackTrace();
        }

        ColumnText.showTextAligned(writer.getDirectContent(), 0, new Phrase(this.header, this.fontDetail), document.left(), document.top() + 20.0F, 0.0F);
        int pageS = writer.getPageNumber();
        String foot1 = "第 " + pageS + " 页 /共";
        Phrase footer = new Phrase(foot1, this.fontDetail);
        float len = this.bf.getWidthPoint(foot1, (float)this.presentFontSize);
        PdfContentByte cb = writer.getDirectContent();
        ColumnText.showTextAligned(cb, 1, footer, (document.rightMargin() + document.right() + document.leftMargin() - document.left() - len) / 2.0F + 20.0F, document.bottom() - 20.0F, 0.0F);
        cb.addTemplate(this.total, (document.rightMargin() + document.right() + document.leftMargin() - document.left()) / 2.0F + 20.0F, document.bottom() - 20.0F);
    }

    public void onCloseDocument(PdfWriter writer, Document document) {
        this.total.beginText();
        this.total.setFontAndSize(this.bf, (float)this.presentFontSize);
        String foot2 = " " + (writer.getPageNumber() - 1) + " 页";
        this.total.showText(foot2);
        this.total.endText();
        this.total.closePath();
    }
}
