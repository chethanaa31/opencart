/*
 * Apache poi library - dependencies should be added to pom.xml file.

 * Excel files --> Workbook--> Sheets ---> Rows ---> Cells.
 		FileInputStream - reading
 		FileOutputStream - writing
 		
 		XSSFWorkbook --> Workbook
 		XSSFSheet --> sheet
 		XSSFRow --> row
 		XSSFCell --> cell
 		
 1) reading data from excel file
 		
 */


package utilities;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUtility {
	
	public FileInputStream file;
	public FileOutputStream fo;
	public XSSFWorkbook workbook;
	public XSSFSheet sheet;
	public XSSFRow row;
	public XSSFCell cell;
	public CellStyle style;
	String path;
	

	public ExcelUtility(String path) {
		this.path=path;
	}
	
	
	public int getRowCount(String Sheetname) throws IOException{
		
		file = new FileInputStream(path);
		workbook = new XSSFWorkbook(file);
		sheet=workbook.getSheet(Sheetname); //XSSFSheet sheet=workbook.getSheetAt(0);
		int rowcount=sheet.getLastRowNum();
		workbook.close();
		file.close();
		return rowcount;
	}
		
	public int getCellCount(String Sheetname, int rownum) throws IOException{
		
		file = new FileInputStream(path);
		workbook = new XSSFWorkbook(file);
		sheet=workbook.getSheet(Sheetname); //XSSFSheet sheet=workbook.getSheetAt(0);
		row=sheet.getRow(rownum);
		int cellcount=row.getLastCellNum();
		workbook.close();
		file.close();
		return cellcount;
	}	
	
	public String getCellData(String Sheetname, int rownum, int column) throws IOException{
		
		file = new FileInputStream(path);
		workbook = new XSSFWorkbook(file);
		sheet=workbook.getSheet(Sheetname); //XSSFSheet sheet=workbook.getSheetAt(0);
		row=sheet.getRow(rownum);
		cell=row.getCell(column);
		
		DataFormatter formatter = new DataFormatter();
		String data;
		try {
			data=formatter.formatCellValue(cell);
		}
		catch(Exception e) {
			data="";
		}
		workbook.close();
		file.close();
		return data;
	}

}
