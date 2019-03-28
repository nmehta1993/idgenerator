package org.demo.idgenerator;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class Files {
	public static List<Identity> parseFile(File file) {
		try {
			ArrayList<Identity> list = new ArrayList<Identity>();
            FileInputStream excelFile = new FileInputStream(file);
            Workbook workbook = new XSSFWorkbook(excelFile);
            Sheet datatypeSheet = workbook.getSheetAt(0);
            Iterator<Row> iterator = datatypeSheet.iterator();

            while (iterator.hasNext()) {

                Row currentRow = iterator.next();
                Iterator<Cell> cellIterator = currentRow.iterator();

                Identity i = new Identity();
                while (cellIterator.hasNext()) {

                    Cell currentCell = cellIterator.next();
                    currentCell.setCellType(Cell.CELL_TYPE_STRING);
                    
                    switch (currentCell.getColumnIndex()) {
					case 0:
						i.setId(currentCell.getStringCellValue());
						break;
					case 1:
						i.setName(currentCell.getStringCellValue());
						break;
					case 2:
						i.setAffilation(currentCell.getStringCellValue());
						break;
					case 3:
						i.setPlace(currentCell.getStringCellValue());
						break;
					case 5:
						i.setMobile(currentCell.getStringCellValue());
						break;
					case 6:
						i.setCountry(currentCell.getStringCellValue());
						break;
					default:
						break;
					}
                }
                list.add(i);

            }
            return list;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
		return null;
	}
}
