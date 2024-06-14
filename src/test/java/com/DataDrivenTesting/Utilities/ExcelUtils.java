package com.DataDrivenTesting.Utilities;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import java.io.File;



public class ExcelUtils 
{
	  public static List<Object[]> getTestData(String filePath, String sheetName) {
	        List<Object[]> testData = new ArrayList<>();
	        FileInputStream fileInputStream = null;
	        Workbook workbook = null;

	        try {
	            fileInputStream = new FileInputStream(new File(filePath));
	            workbook = WorkbookFactory.create(fileInputStream);
	            Sheet sheet = workbook.getSheet(sheetName);
	            Iterator<Row> rowIterator = sheet.iterator();

	            // Skip the header row
	            if (rowIterator.hasNext()) rowIterator.next();

	            while (rowIterator.hasNext()) {
	                Row row = rowIterator.next();
	                String username = row.getCell(0).getStringCellValue();
	                String password = row.getCell(1).getStringCellValue();
	                boolean isValid = row.getCell(2).getBooleanCellValue();
	                testData.add(new Object[]{username, password, isValid});
	            }
	        } catch (IOException e) {
	            e.printStackTrace();
	        } finally {
	            if (workbook != null) {
	                try {
	                    workbook.close();
	                } catch (IOException e) {
	                    e.printStackTrace();
	                }
	            }
	            if (fileInputStream != null) {
	                try {
	                    fileInputStream.close();
	                } catch (IOException e) {
	                    e.printStackTrace();
	                }
	            }
	        }

	        return testData;
	    }
}
