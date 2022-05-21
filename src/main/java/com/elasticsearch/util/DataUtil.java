package com.elasticsearch.util;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.elasticsearch.model.Product;

public class DataUtil {
	public static List<Product> getProductList() {
		List<Product> productList = new ArrayList<>();
		try {
			String excelFilePath = "C:\\\\SpringES\\\\elasticsearch\\\\src\\\\main\\\\resources\\products.xlsx";
			FileInputStream inputStream = new FileInputStream(new File(excelFilePath));
			Workbook workbook = new XSSFWorkbook(inputStream);
			Sheet firstSheet = workbook.getSheetAt(0);
			Iterator<Row> iterator = firstSheet.iterator();
			while (iterator.hasNext()) {
				Row nextRow = iterator.next();
				Iterator<Cell> cellIterator = nextRow.cellIterator();
				Product product = new Product();
				while (cellIterator.hasNext()) {
					Cell cell = cellIterator.next();
					int columnIndex = cell.getColumnIndex();
					switch (columnIndex) {
					case 1:
						product.setName((String) getCellValue(cell));
						break;
					case 2:
						product.setDescription((String) getCellValue(cell));
						break;
					case 3:
						product.setManufacturer((String) getCellValue(cell));
						break;
					}

				}
				System.out.println(product.toString());
				productList.add(product);
				System.out.println();
			}
			workbook.close();
			inputStream.close();
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}

		return productList;
	}

	static Object getCellValue(Cell cell) {
		switch (cell.getCellType()) {
		case STRING:
			return cell.getStringCellValue();
		case BOOLEAN:
			return cell.getBooleanCellValue();
		case NUMERIC:
			return cell.getNumericCellValue();
		}

		return null;
	}
}
