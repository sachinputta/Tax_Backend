package com.example.main.util;

import com.example.main.entity.CustomerRegistration;
import com.example.main.entity.ProformaInvoice;
import com.example.main.entity.Quote;
import com.itextpdf.text.BaseColor;
import com.lowagie.text.*;
import com.lowagie.text.pdf.*;
import com.lowagie.text.pdf.draw.LineSeparator;

import java.io.ByteArrayOutputStream;

public class ProformaInvoicePdfGenerator {
	public static byte[] generateQuotePdf(Quote quote) {
	    Document document = new Document(PageSize.A4, 50, 50, 50, 50);
	    ByteArrayOutputStream out = new ByteArrayOutputStream();

	    try {
	        PdfWriter.getInstance(document, out);
	        document.open();

	        // Fonts
	        Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 22);
	        Font headerFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12);
	        Font textFont = FontFactory.getFont(FontFactory.HELVETICA, 11);

	        // Title - Centered with Bottom Border
	        Paragraph title = new Paragraph("QUOTE", titleFont);
	        title.setAlignment(Element.ALIGN_CENTER);
	        title.setSpacingAfter(10);
	        document.add(title);

	        // Line separator (simulate bottom border)
	        LineSeparator ls = new LineSeparator();
	        document.add(ls);

	        document.add(Chunk.NEWLINE);

	        // Top Details - Company info & Quote meta
	        PdfPTable topTable = new PdfPTable(2);
	        topTable.setWidthPercentage(100);
	        topTable.setWidths(new float[]{60, 40});

	        // Left - Company info
	        PdfPCell leftCell = new PdfPCell();
	        leftCell.setBorder(Rectangle.NO_BORDER);
	        if (quote.getCustomer() != null) {
//	            Customer customer = quote.getCustomer();

	            Paragraph companyInfo = new Paragraph();
	            companyInfo.setFont(textFont);
	            companyInfo.add(new Chunk(quote.getCustomerName() + "\n", headerFont));

	            leftCell.addElement(companyInfo);
	        }
	        topTable.addCell(leftCell);

	        // Right - Quote details
	        PdfPCell rightCell = new PdfPCell();
	        rightCell.setBorder(Rectangle.NO_BORDER);
	        rightCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
	        Paragraph rightInfo = new Paragraph();
	        rightInfo.setFont(textFont);
	        rightInfo.add("QID #: " + quote.getQuoteCode() + "\n");
	        rightInfo.add("Date: " + quote.getDate());
	        rightCell.addElement(rightInfo);

	        topTable.addCell(rightCell);
	        document.add(topTable);

	        document.add(Chunk.NEWLINE);

	        // Billing & Shipping Table
	        PdfPTable addressTable = new PdfPTable(2);
	        addressTable.setWidthPercentage(100);
	        addressTable.setWidths(new float[]{50, 50});

	        PdfPCell billingHeader = new PdfPCell(new Phrase("Billing To", headerFont));
//	        billingHeader.setBackgroundColor(BaseColor.LIGHT_GRAY);
	        billingHeader.setPadding(10);
	        addressTable.addCell(billingHeader);

	        PdfPCell shippingHeader = new PdfPCell(new Phrase("Shipping To", headerFont));
//	        shippingHeader.setBackgroundColor(BaseColor.LIGHT_GRAY);
	        shippingHeader.setPadding(10);
	        addressTable.addCell(shippingHeader);

	        // Billing Content
	        CustomerRegistration  c = quote.getCustomer();
	        PdfPCell billingCell = new PdfPCell(new Phrase(
	                quote.getCustomerName() + "\n" +
	                        c.getBillingStreet() + "\n" +
	                        c.getBillingCity() + ", " + c.getBillingState() + "\n" +
	                        c.getBillingCountry() + " - " + c.getBillingZip(),
	                textFont));
	        billingCell.setPadding(10);
	        addressTable.addCell(billingCell);

	        // Shipping Content
	        PdfPCell shippingCell = new PdfPCell(new Phrase(
	                quote.getCustomerName() + "\n" +
	                        c.getShippingStreet() + "\n" +
	                        c.getShippingCity() + ", " + c.getShippingState() + "\n" +
	                        c.getShippingCountry() + " - " + c.getShippingZip(),
	                textFont));
	        shippingCell.setPadding(10);
	        addressTable.addCell(shippingCell);

	        document.add(addressTable);

	        document.add(Chunk.NEWLINE);

	        // Items Table
	        PdfPTable itemTable = new PdfPTable(5);
	        itemTable.setWidthPercentage(100);
	        itemTable.setWidths(new float[]{5, 40, 10, 15, 15});

	        String[] headers = {"#", "Item & Description", "Qty", "Rate", "Amount"};
	        for (String h : headers) {
	            PdfPCell header = new PdfPCell(new Phrase(h, headerFont));
//	            header.setBackgroundColor(BaseColor.LIGHT_GRAY);
	            header.setHorizontalAlignment(Element.ALIGN_CENTER);
	            header.setPadding(8);
	            itemTable.addCell(header);
	        }

	        int i = 1;
	        double total = 0;
	        for (var item : quote.getItems()) {
	            itemTable.addCell(getCell(String.valueOf(i++), textFont));
	            itemTable.addCell(getCell(item.getDescription(), textFont));
	            itemTable.addCell(getCell(String.valueOf(item.getQuantity()), textFont, Element.ALIGN_CENTER));
	            itemTable.addCell(getCell(String.format("₹ %.2f", item.getRate()), textFont, Element.ALIGN_RIGHT));
	            itemTable.addCell(getCell(String.format("₹ %.2f", item.getAmount()), textFont, Element.ALIGN_RIGHT));
	            total += item.getAmount();
	        }

	        // Total Row
	        PdfPCell totalLabel = new PdfPCell(new Phrase("Total", headerFont));
	        totalLabel.setColspan(4);
	        totalLabel.setHorizontalAlignment(Element.ALIGN_RIGHT);
	        totalLabel.setPadding(10);
	        itemTable.addCell(totalLabel);

	        PdfPCell totalValue = new PdfPCell(new Phrase("₹ " + String.format("%.2f", total), headerFont));
	        totalValue.setHorizontalAlignment(Element.ALIGN_RIGHT);
	        totalValue.setPadding(10);
	        itemTable.addCell(totalValue);

	        document.add(itemTable);

	        document.add(Chunk.NEWLINE);
	        document.add(Chunk.NEWLINE);

	        // Signature
	        Paragraph sign = new Paragraph();
	        sign.setAlignment(Element.ALIGN_RIGHT);
	        sign.setFont(textFont);
	        sign.add("Digitally signed by " + quote.getCustomerId() + "\n");
	        sign.add("Authorised Signatory");

	        document.add(sign);

	        document.close();
	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	    return out.toByteArray();
	}

	// Utility to create a table cell
	private static PdfPCell getCell(String text, Font font) {
	    return getCell(text, font, Element.ALIGN_LEFT);
	}

	private static PdfPCell getCell(String text, Font font, int alignment) {
	    PdfPCell cell = new PdfPCell(new Phrase(text, font));
	    cell.setPadding(8);
	    cell.setHorizontalAlignment(alignment);
	    return cell;
	}

}