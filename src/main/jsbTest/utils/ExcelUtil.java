import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ExcelUtil {

    public static Object[][] getTestData(String excelFilePath, String sheetName) throws IOException, FileNotFoundException {
        File file = new File(excelFilePath);
        FileInputStream fileInputStream = new FileInputStream(file);
        Workbook workBook = null;
        //
        String fileExtensionName = excelFilePath.substring(excelFilePath.indexOf("."));
        if(fileExtensionName.equals(".xlsx")){
            workBook = new XSSFWorkbook(fileInputStream);
        }else if(fileExtensionName.equals(".xls")){
            workBook = new HSSFWorkbook(fileInputStream);
        }

        Sheet sheet = workBook.getSheet(sheetName);

        //得到最后一行的行标，（比实际行数小1）
        int rowCount = sheet.getLastRowNum();//15

        //定义一个一位数组list（测试数据行数可变）
        List<Object[]> records = new ArrayList<Object[]>();

        for(int i =1; i<= rowCount; i++ ){ //i从一开始（去掉表头一行）
            Row row = sheet.getRow(i);

            //定义一个一位数组，大小是列数-1（因为首例是序号，非有效测试数据）
            String fields[] = new String[row.getLastCellNum()-1];

            if(row.getCell(row.getLastCellNum()-1).getStringCellValue().equals("y")){ //判断第二列的“是否执行”标识，y即执行，n为不执行
                for(int j = 0; j<row.getLastCellNum()-1; j++){  //，getLastCellNum得到有效列数，去掉最后一列标识位
                    //判断单元格内是否为字符，如果不是字符通过连接空串的方式转化为String
                    fields[j] = (String) (row.getCell(j).getCellType() == CellType.STRING ?
                            row.getCell(j).getStringCellValue() : ""+row.getCell(j).getNumericCellValue());
                }
                records.add(fields);
            }
        }

        //定义一个二维数组，一维大小为list的大小
        Object[][] results = new Object[records.size()][];
        for(int i = 0 ; i<records.size();i++){
            results[i] = records.get(i);
        }
        workBook.close();
        return results;

    }
}
