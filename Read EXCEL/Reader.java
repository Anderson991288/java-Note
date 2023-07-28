import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.ss.usermodel.DateUtil;
import java.io.File;
import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.util.Iterator;

public class Reader {
    public static void main(String[] args) {
    	
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd"); // 設定日期格式
        try {
            FileInputStream file = new FileInputStream(new File("sample.xlsx")); // 載入指定路徑檔家
            XSSFWorkbook workbook = new XSSFWorkbook(file); // 載入Excel檔案
            Sheet sheet = workbook.getSheetAt(0); // 選擇要讀取的資料
            Iterator<Row> rowIterator = sheet.iterator(); // 取得「列」迭代器
            while (rowIterator.hasNext()) { // 如果有下一列有資料
                Row row = rowIterator.next(); // 取得下一個Row物件
                for (int i = 0; i < row.getLastCellNum(); i++) { // 對於每一行，都讀取固定數量的單元格
                    Cell cell = row.getCell(i, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK); // 如果單元格不存在，則創建一個空的單元格
                    if (cell.getCellType() == CellType.NUMERIC) { // cel是数字或日期型態
                        if (DateUtil.isCellDateFormatted(cell)) {
                            System.out.print(sdf.format(cell.getDateCellValue()) + "\t"); // 日期
                        } else {
                            System.out.print(cell.getNumericCellValue() + "\t"); // 數字
                        }
                    } else if (cell.getCellType() == CellType.STRING) { // cell是文字型態
                        System.out.print(cell.getStringCellValue() + "\t");
                    } else if (cell.getCellType() == CellType.FORMULA) { // cell有帶公式
                        // cell.getCachedFormulaResultType() 可判斷公式型態
                        System.out.print(cell.getNumericCellValue() + "\t"); // 本範例是数字型態
                    } else { // 如果單元格是空的
                        System.out.print("\t");
                    }
                }
                System.out.println();
            }
            file.close(); 
            workbook.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

    	/*
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd"); // 設定日期格式
        int columnLimit = 2; // 設定要讀取的列數限制為2列
        try {
            FileInputStream file = new FileInputStream(new File("PLTR Palantir Technologies Inc.xlsx")); // 載入指定路徑檔案
            XSSFWorkbook workbook = new XSSFWorkbook(file); // 載入Excel檔案
            Sheet sheet = workbook.getSheetAt(0); // 選擇要讀取的資料
            Iterator<Row> rowIterator = sheet.iterator(); // 取得「列」迭代器
            while (rowIterator.hasNext()) { // 如果有下一列有資料
                Row row = rowIterator.next(); // 取得下一個Row物件
                for (int i = 0; i < columnLimit; i++) { // 只讀取前兩列的資料
                    Cell cell = row.getCell(i, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK); // 如果單元格不存在，則創建一個空的單元格
                    if (cell.getCellType() == CellType.NUMERIC) { // cel是数字或日期型態
                        if (DateUtil.isCellDateFormatted(cell)) {
                            System.out.print(sdf.format(cell.getDateCellValue()) + "\t"); // 日期
                        } else {
                            System.out.print(cell.getNumericCellValue() + "\t"); // 數字
                        }
                    } else if (cell.getCellType() == CellType.STRING) { // cell是文字型態
                        System.out.print(cell.getStringCellValue() + "\t");
                    } else if (cell.getCellType() == CellType.FORMULA) { // cell有帶公式
                        // cell.getCachedFormulaResultType() 可判斷公式型態
                        System.out.print(cell.getNumericCellValue() + "\t"); // 本範例是数字型態
                    } else { // 如果單元格是空的
                        System.out.print("\t");
                    }
                }
                System.out.println();
            }
            file.close(); 
            workbook.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


*/
/*
 // Writer

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public class Reader {
    public static void main(String[] args) {
        try {
            FileInputStream file = new FileInputStream(new File("sample.xlsx"));
            XSSFWorkbook WB = new XSSFWorkbook(file);
            Sheet sheet = WB.getSheetAt(0); // 選擇要寫入的工作表

            Map<String, Object[]> data = new LinkedHashMap<>();
            data.put("1", new Object[]{"2023/6/4", "客運", 150});
            data.put("2", new Object[]{"2023/6/4", "宵夜", 70});
            data.put("3", new Object[]{"2023/6/4", "健身房", 100});
            int rownum = 10;
            
            //int rownum = sheet.getLastRowNum() + 1; // 從最後一行的下一行開始寫入新數據
            CellStyle style = WB.createCellStyle(); // 創建「儲存格樣式」物件
            Font font = WB.createFont(); // 創建「文字樣式」物件
            font.setColor(HSSFColor.HSSFColorPredefined.BLACK.getIndex()); // 設定藍色字
            font.setFontName("微軟正黑體"); // 設定字體
            style.setFont(font); // 文字設定加入「儲存格樣式」物件
            font.setFontHeightInPoints((short) 12);

            Set<String> keyset = data.keySet();
            for (String key : keyset) { // 依 Excel 欄位格式加入新資料
                Row row = sheet.createRow(rownum++);
                Object[] objArr = data.get(key);
                for (int i = 0; i < objArr.length; i++) {
                    Cell cell = row.createCell(i);
                    if (objArr[i] instanceof String) {
                        cell.setCellValue((String) objArr[i]);
                    } else if (objArr[i] instanceof Integer) {
                        cell.setCellValue((Integer) objArr[i]);
                    }
                    cell.setCellStyle(style); // 樣式設定加入指定 cell
                }
            }

            // 在每日總計金額的欄位中設定公式
            int lastRowNum = 12;
            Row row = sheet.getRow(lastRowNum);
            Cell cell = row.createCell(3);
            String formula = "SUM(C11:C13)";
            cell.setCellFormula(formula);

            file.close(); // 關閉檔案輸入流
            FileOutputStream outFile = new FileOutputStream(new File("sample.xlsx")); // 輸出結果到檔案
            WB.write(outFile);
            outFile.close(); // 關閉檔案輸出流
            WB.close();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
*/

