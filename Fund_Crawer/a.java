

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.apache.poi.openxml4j.util.ZipSecureFile;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFFormulaEvaluator;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.encoder.PatternLayoutEncoder;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.FileAppender;

public class a {
	private static ch.qos.logback.classic.Logger logbackLogger;
	private static final Logger log = (Logger) LoggerFactory.getLogger(a.class);
	private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
	
    @SuppressWarnings("deprecation")
	public static void main(String[] args) throws Exception {
    	setupLogger();
    	ZipSecureFile.setMinInflateRatio(0.00005);

        List < String > urls = readUrlsFromExcel(System.getProperty("user.dir")+"\\基金.xlsx");
        List < String > dates = new ArrayList < > ();
        List < Double > netValues = new ArrayList < > ();
        List < String > name = readNameFromExcel(System.getProperty("user.dir")+"\\基金.xlsx");
        for (int k = 0; k < urls.size(); k++) {
            String url1 = urls.get(k);
            String names = name.get(k);
        
            if (url1.contains("moneydj.com")) {
                Document doc = null;
                try {
                    doc = Jsoup.connect(url1).validateTLSCertificates(false).get();
                } catch (IOException e) {
                    e.printStackTrace();
                    return;
                }
                //Elements title = doc.select("table").select("td.t100");
                //System.out.println(title.text());
                SimpleDateFormat inputDateFormat = new SimpleDateFormat("MM/dd");
                SimpleDateFormat outputDateFormat = new SimpleDateFormat("yyyy/MM/dd");
                try {
                    Elements tables = doc.select("table.t01");
                    for (Element table: tables) {
                        Elements rows = table.select("tr");
                        for (Element row: rows) {
                            Elements data = row.select("td");
                            if (data.size() == 2) {
                                String date = data.get(0).text();
                                String netValue = data.get(1).text().replace(",", "");
                                if (!date.equals("日期") && !netValue.equals("淨值")) {
                                    Date parsedDate = inputDateFormat.parse(date);
                                    Calendar cal = Calendar.getInstance();
                                    cal.setTime(parsedDate);
                                    int currentYear = Calendar.getInstance().get(Calendar.YEAR);
                                    if (cal.get(Calendar.MONTH) > Calendar.getInstance().get(Calendar.MONTH)) {
                                        cal.set(Calendar.YEAR, currentYear - 1);
                                    } else {
                                        cal.set(Calendar.YEAR, currentYear);
                                    }
                                    dates.add(outputDateFormat.format(cal.getTime()));
                                    netValues.add(Double.parseDouble(netValue));
                                }
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (url1.contains("yahoo.com")) {
                Document doc = null;
            
                try {
                	doc = Jsoup.connect(url1).validateTLSCertificates(false).get();
                } catch (IOException e) {
                    e.printStackTrace();
                    return;
                }
                //System.out.println(doc.select("h1").text());
                Element table = doc.selectFirst("table[data-test=historical-prices]");
                Elements tabs = table.select("tbody").select("tr");
                for (Element tab: tabs) {
                    Elements dds = tab.select("td");
                    if (dds.size() == 7) {
                        String date = dds.get(0).select("span").text();
                        Double netValue = Double.parseDouble(dds.get(4).select("span").text().replace(",", " "));
                        dates.add(date); //日期
                        netValues.add(netValue); //淨值  
                        
                    }
                    
                }

                for (int i = 0; i < dates.size(); i++) {
                    String dateStr = dates.get(i);
                    String[] dateParts = dateStr.split(" ");
                    if (dateParts.length < 3) {
                        //處理日期格式異常則跳過
                        continue;
                    }
                    String monthNum = getMonthNumber(dateParts[0]);
                    String day = dateParts[1].replace(",", "");
                    String year = dateParts[2];
                    String formattedDate = year + "/" + monthNum + "/" + day;
                    dates.set(i, formattedDate);
                }
            } /*else {
            	log.info("未知網頁，無法處理");
            }*/
            
           // System.getProperty("user.dir")+"\\xlsx\\基金.xlsx"
            FileInputStream file = new FileInputStream(new File(System.getProperty("user.dir")+"\\xlsx\\" + names + ".xlsx"));
      
            XSSFWorkbook workbook = new XSSFWorkbook(file);
            Sheet sheet = (Sheet) workbook.getSheetAt(0);
            int lastRowIndex = -1; // 初始化為 -1，表示尚未找到非空儲存格。
            // 搜尋最後一個非空儲存格的索引
            for (Row r: sheet) {
                Cell cell = r.getCell(0); // 取得每一列的第一個儲存格
                // 檢查儲存格是否為空或儲存格內容為空字串
                if (cell != null && !cell.toString().isEmpty()) {
                    lastRowIndex = r.getRowNum(); // 找到非空儲存格時更新 lastRowIndex
                }
            }
            // 讀取最後一列儲存格的日期值
            Row newRow = sheet.getRow(lastRowIndex);
            Cell newDateCell = newRow.getCell(0);
            String newDate = null;
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
            Date excelDate = null; // 初始化 excelDate 為 null
            //檢查儲存格是否為空
            //檢查儲存格類型
            if (newDateCell != null) {
                if (newDateCell.getCellType() == CellType.STRING) {
                    newDate = newDateCell.getStringCellValue(); //如果它是一個字串，則程式會將其值賦給變數newDate
                } else if (newDateCell.getCellType() == CellType.NUMERIC) { //如果儲存格是數值，則檢查它是否代表一個日期
                    if (DateUtil.isCellDateFormatted(newDateCell)) {
                        excelDate = newDateCell.getDateCellValue(); // 如果是日期格式，直接取得 Date 值
                    } else {
                        // 處理儲存格為數值但不是日期的錯誤
                    	//log.info("儲存格 " + newDateCell.getAddress() + " 是數值但不是日期格式");
                    }
                }
            }
            if (excelDate == null && newDate != null) { // 如果 excelDate 是 null，且 newDate 不是 null
                excelDate = dateFormat.parse(newDate); // 則解析 newDate
            }
            
            workbook.close();
            
            Date newestScrapedDate = sdf.parse(dates.get(0));
            Date oldestScrapedDate = sdf.parse(dates.get(dates.size() - 1));

            if (excelDate.equals(newestScrapedDate)) {
            	logbackLogger.info(names+"此基金已經更新過");
            } else if (excelDate.before(oldestScrapedDate)) {
            	logbackLogger.error(names+"資料太舊!");
            	logbackLogger.error("請將此基金更新到" + sdf.format(oldestScrapedDate));
            } else if (excelDate.after(newestScrapedDate)) {
            	logbackLogger.info(names+"完成寫入!");
            } else {
               writeToExcel(dates, netValues, System.getProperty("user.dir")+"\\xlsx\\" + names + ".xlsx", excelDate, oldestScrapedDate, names);
               logbackLogger.info(names+"完成寫入!");
            }
            dates.clear();
            netValues.clear();
        }
    }
    private static String getMonthNumber(String monthName) {
        switch (monthName) {
            case "Jan":
                return "01";
            case "Feb":
                return "02";
            case "Mar":
                return "03";
            case "Apr":
                return "04";
            case "May":
                return "05";
            case "Jun":
                return "06";
            case "Jul":
                return "07";
            case "Aug":
                return "08";
            case "Sep":
                return "09";
            case "Oct":
                return "10";
            case "Nov":
                return "11";
            case "Dec":
                return "12";
            default:
                return "00";
        }
    }
    private static List < String > readNameFromExcel(String filePath) {
        List < String > names = new ArrayList < > ();
        try {
            FileInputStream file = new FileInputStream(new File(filePath));
            Workbook workbook = new XSSFWorkbook(file);
            Sheet sheet = workbook.getSheetAt(0);
            for (Row row: sheet) {
                Cell cell = row.getCell(2);
                if (cell != null && !cell.toString().trim().isEmpty()) {
                    names.add(cell.toString().trim());
                }
            }
            file.close();
            workbook.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return names;
    }
    private static List < String > readUrlsFromExcel(String filePath) {
        List < String > urls = new ArrayList < > ();
        try {
            FileInputStream file = new FileInputStream(new File(filePath));
            Workbook workbook = new XSSFWorkbook(file);
            Sheet sheet = workbook.getSheetAt(0);
            for (Row row: sheet) {
                Cell cell = row.getCell(1);
                if (cell != null && !cell.toString().trim().isEmpty()) {
                    urls.add(cell.toString().trim());
                }
            }
            file.close();
            workbook.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return urls;
    }

    private static void writeToExcel(List < String > dates, List < Double > netValues, String filePath, Date excelDate, Date oldestScrapedDate, String names) {
        try {
            FileInputStream file = new FileInputStream(new File(filePath));
            Workbook workbook = new XSSFWorkbook(file);
            Sheet sheet = workbook.getSheetAt(0);
            int lastRowIndex = 0;
            for (Row r: sheet) {
                if (r.getCell(0) != null && !r.getCell(0).toString().isEmpty()) {
                    lastRowIndex = r.getRowNum();
                }
            }
            Collections.reverse(dates); // Reverse the dates list
            Collections.reverse(netValues); // Reverse the netValues list
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
            Calendar cal = Calendar.getInstance();
            cal.setTime(excelDate);
            cal.add(Calendar.DATE, 1); // Move to the next day after excelDate
            Date startingDate = cal.getTime(); // The date we start writing new data from
            int iterations = Math.min(dates.size(), netValues.size());
            
            CellStyle dateCellStyle = workbook.createCellStyle();
            dateCellStyle.setVerticalAlignment(VerticalAlignment.BOTTOM);
            dateCellStyle.setAlignment(HorizontalAlignment.RIGHT);
            
            for (int i = 0; i < iterations; i++) {
                Date currentDate = sdf.parse(dates.get(i));
             // Only write data for dates that are after the startingDate
                if (!currentDate.before(startingDate)) {
                    int rowIndex = ++lastRowIndex; // Increment the lastRowIndex for new data
                    Row excelRow = sheet.getRow(rowIndex);
                    if (excelRow == null) {
                        excelRow = sheet.createRow(rowIndex);
                    }
                    
                 // Setting the alignment for the date cell
                    Cell dateCell = excelRow.createCell(0);
                    dateCell.setCellValue(dates.get(i));
                    dateCell.setCellStyle(dateCellStyle);
                    excelRow.createCell(1).setCellValue(netValues.get(i));
                    for (int j = 2; j <= 11; j++) {
                        Cell cell = excelRow.createCell(j);
                        CellStyle style = workbook.createCellStyle();
                        Font font = workbook.createFont();
                        font.setFontName("新細明體");
                        font.setFontHeightInPoints((short) 12);
                        style.setAlignment(HorizontalAlignment.CENTER);
                        int rownum = lastRowIndex;
                        String formula = null;
                        switch (j) {
                            case 2:
                                formula = "AVERAGE(B" + (rownum - 9) + ":B" + rownum + ")";
                                break;
                            case 3:
                                formula = "AVERAGE(B" + (rownum - 19) + ":B" + rownum + ")";
                                break;
                            case 4:
                                formula = "AVERAGE(B" + (rownum - 29) + ":B" + rownum + ")";
                                break;
                            case 5:
                                font.setColor((short) 12);
                                formula = "IF((AND(B" + rownum + ">= C" + rownum + ",B" + rownum + ">= D" + rownum + ",C" + rownum + ">= D" + rownum + ",D" + rownum + ">= E" + rownum + ",C" + rownum + ">= C" + (rownum - 1) + ",D" + rownum + ">= D" + (rownum - 1) + ",E" + rownum + ">= E" + (rownum - 1) + ")),\"進場\",\"---\")";
                                break;
                            case 6:
                                formula = "AVERAGE(B" + (rownum - 39) + ":B" + rownum + ")";
                                break;
                            case 7:
                                font.setColor((short) 10);
                                formula = "IF((AND(B" + rownum + "<= C" + rownum + ",B" + rownum + "<= D" + rownum + ",C" + rownum + "<= D" + rownum + ",D" + rownum + "<= G" + rownum + ",C" + rownum + "<= C" + (rownum - 1) + ",D" + rownum + "<= D" + (rownum - 1) + ",E" + rownum + "<= E" + (rownum - 1) + ")),\"出場 \",\"---\")";
                                break;
                            case 8:
                                formula = "IF((AND(B" + (rownum - 2) + ">=C" + (rownum - 2) + ",B" + (rownum - 2) + ">=D" + (rownum - 2) + ",B" + (rownum - 2) + ">=E" + (rownum - 2) + ",B" + (rownum - 1) + ">=C" + (rownum - 1) + ",B" + (rownum - 1) + ">=D" + (rownum - 1) + ",B" + (rownum - 1) + ">=E" + (rownum - 1) + ",B" + rownum + ">=C" + rownum + ",B" + rownum + ">=D" + rownum + ",B" + rownum + ">=E" + rownum + ",B" + rownum + ">=C" + rownum + "*1.01,B" + rownum + ">=D" + rownum + "*1.015,B" + rownum + ">=E" + rownum + "*1.02,C" + rownum + ">=D" + rownum + ",D" + rownum + ">=E" + rownum + ",E" + rownum + ">=E" + (rownum - 1) + ")),1,0)";
                                break;
                            case 9:
                                formula = "IF(AND(I" + (rownum - 2) + "=1,I" + (rownum - 1) + "=1,I" + rownum + "=1),B" + rownum + ",\"0\")";
                                break;
                            case 10:
                                font.setColor((short) 17);
                                formula = "(J" + rownum + "*0.97)";
                                break;
                            case 11:
                                formula = "IF(D" + rownum + ">=D" + (rownum - 1) + ",\"多頭 \",\"空\")";
                                font.setColor((short) 12);
                                style.setBorderBottom(BorderStyle.THIN);
                                style.setBorderRight(BorderStyle.THIN);
                                style.setFillForegroundColor((short) 13);
                                style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                                break;
                        }
                        style.setFont(font);
                        cell.setCellStyle(style);
                        cell.setCellFormula(formula);
                    }
                }
            }
            XSSFFormulaEvaluator.evaluateAllFormulaCells(workbook);
            file.close(); //關閉檔案輸入流
            FileOutputStream outFile = new FileOutputStream(System.getProperty("user.dir")+"\\xlsx\\" + names + ".xlsx"); //輸出結果到檔案
            workbook.write(outFile);
            outFile.close(); //關閉檔案輸出流  

        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    private static void setupLogger() {
        // 取得 LoggerContext 物件，用於設定日誌紀錄器
        LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();
        // 設定 PatternLayoutEncoder 格式
        PatternLayoutEncoder encoder = new PatternLayoutEncoder();
        encoder.setPattern("[%date{yyyy-MM-dd HH:mm:ss}] [%level] %msg%n");
        encoder.setContext(loggerContext);
        encoder.start();
      
        // 設定 FileAppender 並指定日誌檔案路徑
        FileAppender < ILoggingEvent > fileAppender = new FileAppender < > ();
        fileAppender.setFile("./log/log_file.log"); // 更改成所需的日誌檔案路徑
        fileAppender.setEncoder(encoder);
        fileAppender.setContext(loggerContext);
        fileAppender.start();
        // 取得 Logger 並設定日誌紀錄等級為 ERROR
        logbackLogger = (ch.qos.logback.classic.Logger) log;
        logbackLogger.addAppender(fileAppender);
        logbackLogger.setLevel(Level.INFO);
    }
}


    