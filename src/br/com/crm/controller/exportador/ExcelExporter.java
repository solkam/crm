package br.com.crm.controller.exportador;

import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.Date;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;

import br.com.crm.model.util.DateUtil;

public abstract class ExcelExporter {
	
	private static final String EXCEL_EXTENSION = ".xls"; 
	
	//semi-constantes (inicializadas sob-demanda)
	private HSSFCellStyle DESTAK_1_STYLE;
	private HSSFCellStyle DESTAK_2_STYLE;
	private HSSFCellStyle NORMAL_STYLE;
	private HSSFCellStyle CENTRALIZED_STYLE;
	private HSSFCellStyle RED_STYLE;
	private HSSFCellStyle BLUE_STYLE;
	
	
	public void export(String[] reportTitles, String filenameNoExtension) {
		try {
			//1.pre.processamento
			String filenameWithExtension = appendExtension(filenameNoExtension);
			HttpServletResponse response = HttpResponseConfigurator.configResponseForAttachmentDisposition(filenameWithExtension);
			
			//2.processamento
			HSSFWorkbook workbook = new HSSFWorkbook();
			HSSFSheet sheet = workbook.createSheet();
			
			addContentToWorkbook(sheet, reportTitles);
			
			//3.pos.processamento
			OutputStream os = response.getOutputStream(); 
			workbook.write(os);
			
			response.setContentType("application/vnd.ms-excel");
			
			os.flush();
			os.close();
			
		} catch (IOException e) {
			throw new RuntimeException( e );
		}
	}
	
	private String appendExtension(String filenameNoExtension) {
		return filenameNoExtension + EXCEL_EXTENSION;
	}
	
	/**
	 * 
	 * @param workbook
	 * @param reportTitles
	 */
	public abstract void addContentToWorkbook(HSSFSheet sheet, String[] reportTitles);
	
	
	/**
	 * Adiciona os titulos
	 * @param sheet
	 * @param reportTitles
	 */
	protected int addReportTitles(HSSFSheet sheet, int rowIndex, String[] reportTitles) {
		for (String title : reportTitles) {
			HSSFCell titleCell = sheet.createRow( rowIndex++ ).createCell( 0 );
			addDestak1Value(titleCell, title);
		}
		return rowIndex;
	}

	protected void addDestak1Value(HSSFCell cell, String txt) {
		cell.setCellValue( txt );
		addDestak1Style(cell);
	}
	
	protected void addDestak2Value(HSSFCell cell, String txt) {
		cell.setCellValue( txt );
		addDestak2Style(cell);
	}

	protected int addWhiteLine(HSSFSheet sheet, int rowIndex) {
		sheet.createRow(rowIndex++).createCell( 0 ).setCellValue("");
		return rowIndex;
	}

	
	protected void addContentValue(HSSFCell cell, String txt) {
		cell.setCellValue( txt==null ? "" : txt );
		addNormalStyle(cell);
	}

	protected void addContentValue(HSSFCell cell, Long longValue) {
		cell.setCellValue( longValue==null ? "" : longValue.toString() );
		addNormalStyle(cell);
	}

	protected void addContentValue(HSSFCell cell, Integer inteiro) {
		cell.setCellValue( inteiro==null ? "" : inteiro.toString() );
		addNormalStyle(cell);
	}

	protected void addContentValue(HSSFCell cell, Date date) {
		cell.setCellValue( DateUtil.toStringDate(date) );
		addNormalStyle(cell);
	}
	
	protected void addContentValue(HSSFCell cell, BigDecimal value) {
		cell.setCellValue( value.toString() );
		addNormalStyle(cell);
	}
	
	protected void addRedContentValue(HSSFCell cell, BigDecimal value) {
		cell.setCellValue( value.toString() );
		addRedStyle(cell);
	}
	
	protected void addBlueContentValue(HSSFCell cell, BigDecimal value) {
		cell.setCellValue( value.toString() );
		addBlueStyle(cell);
	}

	

	
	protected void addHeaderValue(HSSFCell headerCell, String headerTxt) {
		headerCell.setCellValue( headerTxt );
		addDestak2Style( headerCell );
	}

	
	protected void addRedStyle(HSSFCell cell) {
		cell.setCellStyle( getRedStyle(cell) );
	}

	private HSSFCellStyle getRedStyle(HSSFCell cell) {
		if (RED_STYLE==null) {
			HSSFWorkbook workbook = cell.getSheet().getWorkbook();
			RED_STYLE = workbook.createCellStyle();
			
			Font redFont = workbook.createFont();
			redFont.setFontHeightInPoints( (short)9 );
			redFont.setColor( HSSFColor.RED.index );
			RED_STYLE.setFont( redFont );
			RED_STYLE.setBorderTop(    CellStyle.BORDER_THIN );
			RED_STYLE.setBorderBottom( CellStyle.BORDER_THIN );
			RED_STYLE.setBorderRight(  CellStyle.BORDER_THIN );
			RED_STYLE.setBorderLeft(   CellStyle.BORDER_THIN );
		}
		return RED_STYLE;
	}
	
	protected void addBlueStyle(HSSFCell cell) {
		cell.setCellStyle( getBlueStyle(cell) );
	}

	private HSSFCellStyle getBlueStyle(HSSFCell cell) {
		if (BLUE_STYLE==null) {
			HSSFWorkbook workbook = cell.getSheet().getWorkbook();
			BLUE_STYLE = workbook.createCellStyle();
			
			Font blueFont = workbook.createFont();
			blueFont.setFontHeightInPoints( (short)9 );
			blueFont.setColor( HSSFColor.BLUE.index );
			BLUE_STYLE.setFont( blueFont );
			BLUE_STYLE.setBorderTop(    CellStyle.BORDER_THIN );
			BLUE_STYLE.setBorderBottom( CellStyle.BORDER_THIN );
			BLUE_STYLE.setBorderRight(  CellStyle.BORDER_THIN );
			BLUE_STYLE.setBorderLeft(   CellStyle.BORDER_THIN );
		}
		return BLUE_STYLE;
	}
	
	
	protected void addNormalStyle(HSSFCell cell) {
		cell.setCellStyle( getNormalStyle(cell) );
	}

	
	
	private HSSFCellStyle getNormalStyle(HSSFCell cell) {
		if (NORMAL_STYLE==null) {
			HSSFWorkbook workbook = cell.getSheet().getWorkbook();
			NORMAL_STYLE = workbook.createCellStyle();
			
			Font normallFont = workbook.createFont();
			normallFont.setFontHeightInPoints( (short)9 );
			NORMAL_STYLE.setFont( normallFont );
			NORMAL_STYLE.setBorderTop(    CellStyle.BORDER_THIN );
			NORMAL_STYLE.setBorderBottom( CellStyle.BORDER_THIN );
			NORMAL_STYLE.setBorderRight(  CellStyle.BORDER_THIN );
			NORMAL_STYLE.setBorderLeft(   CellStyle.BORDER_THIN );
		}
		return NORMAL_STYLE;
	}
	
	
	protected void addDestak1Style(HSSFCell cell) {
		cell.setCellStyle( getDestak1Style(cell) );
	}

	private HSSFCellStyle getDestak1Style(HSSFCell cell) {
		if (DESTAK_1_STYLE==null) {
			DESTAK_1_STYLE = cell.getSheet().getWorkbook().createCellStyle();
			Font boldFont = cell.getSheet().getWorkbook().createFont();
			boldFont.setBoldweight( HSSFFont.BOLDWEIGHT_BOLD );
			boldFont.setFontHeightInPoints( (short)12 );
			DESTAK_1_STYLE.setFont( boldFont );
			DESTAK_1_STYLE.setBorderTop(    CellStyle.BORDER_NONE );
			DESTAK_1_STYLE.setBorderBottom( CellStyle.BORDER_NONE );
			DESTAK_1_STYLE.setBorderRight(  CellStyle.BORDER_NONE );
			DESTAK_1_STYLE.setBorderLeft(   CellStyle.BORDER_NONE );
}
		return DESTAK_1_STYLE;
	}
	
	
	
	protected void addDestak2Style(HSSFCell cell) {
		cell.setCellStyle( getDestak2Style(cell) );
	}

	private HSSFCellStyle getDestak2Style(HSSFCell cell) {
		if (DESTAK_2_STYLE==null){
			DESTAK_2_STYLE = cell.getSheet().getWorkbook().createCellStyle();
			Font boldFont = cell.getSheet().getWorkbook().createFont();
			boldFont.setBoldweight( HSSFFont.BOLDWEIGHT_BOLD );
			boldFont.setFontHeightInPoints( (short)10 );
			DESTAK_2_STYLE.setFont( boldFont );
			DESTAK_2_STYLE.setBorderTop(    CellStyle.BORDER_MEDIUM );
			DESTAK_2_STYLE.setBorderBottom( CellStyle.BORDER_MEDIUM );
			DESTAK_2_STYLE.setBorderRight(  CellStyle.BORDER_MEDIUM );
			DESTAK_2_STYLE.setBorderLeft(   CellStyle.BORDER_MEDIUM );
		}
		return DESTAK_2_STYLE;
	}
	
	
	protected void addCentralizedStyle(HSSFCell cell) {
		cell.setCellStyle( getCentralizedStyle(cell) );
	}
	
	private HSSFCellStyle getCentralizedStyle(HSSFCell cell) {
		if (CENTRALIZED_STYLE==null) {
			HSSFWorkbook workbook = cell.getSheet().getWorkbook();
			CENTRALIZED_STYLE = workbook.createCellStyle();
			CENTRALIZED_STYLE.setAlignment( CellStyle.ALIGN_CENTER );
			CENTRALIZED_STYLE.setBorderTop(    CellStyle.BORDER_HAIR );
			CENTRALIZED_STYLE.setBorderBottom( CellStyle.BORDER_HAIR );
			CENTRALIZED_STYLE.setBorderRight(  CellStyle.BORDER_HAIR );
			CENTRALIZED_STYLE.setBorderLeft(   CellStyle.BORDER_HAIR );
		}
		return CENTRALIZED_STYLE;
	}
	
	
	
	

}
